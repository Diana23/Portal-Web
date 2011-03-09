package com.cablevision.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jwm.jdom.CDATA;
import jwm.jdom.Document;
import jwm.jdom.Element;
import jwm.jdom.input.SAXBuilder;
import jwm.jdom.output.XMLOutputter;

import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import com.bea.p13n.cache.Cache;
import com.bea.p13n.cache.CacheFactory;
import com.bea.portlet.GenericURL;
import com.bea.portlet.PageURL;
import com.cablevision.carga.transformador.Transformador;
import com.cablevision.controller.contenido.ContenidoEstructuradoController.PaginaFormBean;
import com.cablevision.vo.CvContenido;
import com.cablevision.vo.CvTemplate;

import freemarker.cache.StringTemplateLoader;
import freemarker.ext.dom.NodeModel;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author daniela
 *
 */
public class ContenidoHelper {
	static Logger log = Logger.getLogger(ConfigurationHelper.class);
	
    public static CvContenido saveToFile(PaginaFormBean form, HttpServletRequest request){
    	CvContenido newContent = new CvContenido();
        try{
           String xmlToSave = getContent(form.getContenido(), request);
           xmlToSave = xmlToSave.replace(request.getContextPath()+"/servlet/content/lastversion/","/contentserver/");
           newContent =  UcmUtil.saveContentFile(form.getContenidoId(), xmlToSave, form.getEstructuraId(), form.getFolderId(), 
        		   								 form.getNombre(), request);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return newContent;
    }
    
    public static void deleteFile(PaginaFormBean form, HttpServletRequest request){
    	try{
    		//borrado de solr borrarByQuery
            String xml = UcmUtil.getContentByName(form.getContenidoId(), true);
        	Map<String,String> mapToIndex = getMapToIndex(xml);
        	   
        	if ( mapToIndex.size()>0 ) {
    		   SolrHelper.borrarByQuery("id:"+form.getContenidoId());
    		   SolrHelper.getSolrServer().commit();
        	}
		
        	UcmUtil.removeContentFile(form.getContenidoId(), request);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public static String getContent(String xml, HttpServletRequest request){
    	String xmlToSave = "";
    	try{
            SAXBuilder builder;
            Document root = null;
            try {
                builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
                root = builder.build(new StringReader(xml));
            }catch(Exception ee){ 
                System.out.println("Exception building Document : " + ee);
            }
            
            if(root!=null && root.getRootElement().getChildren()!= null){
            	List lista = root.getRootElement().getChildren();
                for (int i=0;i<lista.size(); i++){
                    getData((Element)lista.get(i), request, "");
                }    
                StringWriter sw = new StringWriter();
                new XMLOutputter().output(root, sw);
                xmlToSave = sw.toString();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return xmlToSave;
    }
    
    @SuppressWarnings("unchecked")
    private static void getData(Element elemento, HttpServletRequest request, String prefix){
        String strRep = elemento.getAttributeValue("repetir");
        String tipo = elemento.getAttributeValue("tipo");
        if (strRep==null) strRep="no";
        if (tipo==null) tipo = "";
        boolean repetir = false;
        if (strRep.equals("si")) repetir=true;
        String httpName = prefix+elemento.getName()+"_"+elemento.getAttribute("index").getValue();
        if (elemento.getChildren()!=null && elemento.getChildren().size()>0){
            List lista = elemento.getChildren();
            for (int j=0;j<lista.size(); j++){
                getData((Element)lista.get(j), request, httpName+"_");
            }
        }else{
            String valor = request.getParameter(httpName);
            elemento.getContent().clear();
            elemento.addContent(new CDATA(valor));
        }
    }
    
    public static String readFromFile(String path){
    	StringBuffer buffer = new StringBuffer();
    	try{
    		// Open the file that is the first 
    		// command line parameter
    		FileInputStream fstream = new FileInputStream(path);
    		// Get the object of DataInputStream
    		DataInputStream in = new DataInputStream(fstream);
    		BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF8"));
    		String strLine;
    		
    		//Read File Line By Line
    		while ((strLine = br.readLine()) != null){
    			buffer.append(strLine);
    		}
    		//Close the input stream
    		in.close();
    	}catch (Exception e){//Catch exception if any
    		System.err.println("Error: " + e.getMessage());
    	}
    	return buffer.toString();
    }
    
    public static CvContenido renderizeContent(HttpServletRequest request, HttpServletResponse response, 
    										String contenidoId, String estructuraId, String templateId,
    										String nombre, boolean ultima, String xmlPreview, boolean canEdit) throws Exception{
        String html = null;
        CvContenido contenido = new CvContenido();
        try {
        	if(!ultima&&!canEdit){
        		html = getContentFromCache(request, contenidoId+templateId, ultima);
        	}
        	
			if (StringUtils.isEmpty(html)){
	            Configuration cfg = getTemplatesEnCache();
	            //TODO descomentar cache y metodo que busca en disco para hacer pruebas
	            if(cfg==null){   //comentar esta linea
	            	setTemplatesEnCache(UcmUtil.getTemplates());
	            	cfg = getTemplatesEnCache();
	            } //comentar esta linea
	            Template temp = cfg.getTemplate(templateId); 
	            temp.setCustomAttribute("contextPath", request.getContextPath());
	            StringWriter sw = new StringWriter();
	            String xml = "";
	            if(StringUtils.isNotEmpty(xmlPreview)){
	            	xml = xmlPreview;
	            }else{
	            	xml = getContentById(contenidoId, templateId, ultima, canEdit, request);
	            	if(StringUtils.isEmpty(xml))
	                    xml = getContentById(contenidoId, templateId, true, canEdit, request);
	            }
	            //Si el xml viene vacío, el contenido es nuevo y se basa en la estructura para construirlo
	            //TODO: esta parte esta comentada para no crear un archivo nuevo cuando no existe 06/01/11
	            /*if (StringUtils.isEmpty(xml)){
	            	 if (regeneraContenido(estructuraId, canEdit, request)){
	            		 String folderId = (String)request.getAttribute("folderId");
	            		 xml = getContentById(estructuraId, templateId, true, canEdit, request);
	            		 contenido.setDDocName(UcmUtil.saveContentFile("", xml, estructuraId, folderId, nombre, request).getDDocName());
	            		 UcmUtil.workFlowApprove(contenido.getDDocName(), request);
	            	 }
	            }*/
	            if(StringUtils.isNotEmpty(xml)){
	            	
	            	//reemplazar los links guardados con pageLabel por su equivalente de link completo
	            	SAXBuilder builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
	                StringReader sr = new StringReader(xml);
	                Document rootLink = builder.build(sr);
	                Element rootElement = rootLink.getRootElement();
	                
	            	List<Element> elementosLink = getElementosByTipo(rootElement, "link");
	                for (int i=0;i<elementosLink.size();i++){
	                    Element elemento = (Element)elementosLink.get(i);
	                	
	                    String srcLink = elemento.getText();
	                    
	                    if(srcLink!=null&&srcLink.startsWith("_pageLabel=")){
	                    	srcLink = srcLink.replace("_pageLabel=", "");
		                	
		                	String linkSinParams = StringUtils.substringBefore(srcLink, "?");
		                	String strParams = StringUtils.substringAfter(srcLink,"?");
		                	
		                	PageURL url = PageURL.createPageURL(request, response, linkSinParams);  
		                	url.setTemplate("defaultDesktop");
		                	url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		    				url.setForcedAmpForm(false);
		    				
		                	//el link trae pararmetros
		                	if(StringUtils.isNotEmpty(strParams)){
		                		String[] params = strParams.split("&");
			                	for(int j=0; j<params.length; j++){
			                		if( StringUtils.isNotEmpty(StringUtils.substringBefore(params[j], "=")) &&
			                			StringUtils.isNotEmpty(StringUtils.substringAfter(params[j], "=")) )
			                			url.addParameter(StringUtils.substringBefore(params[j], "="), StringUtils.substringAfter(params[j], "="));
			                	}
		                	}
		                	
		    				elemento.setText(url.toString());
	                    }
	                	
	                } 
	                
	                //remplazar las ligas dentro del contenido libre a mostrar por su equivalente completo
	                List<Element> elementosLibre = getElementosByTipo(rootElement,"libre");
	                for (int i=0;i<elementosLibre.size();i++){
	                	Element elemento = (Element)elementosLibre.get(i);
	                	String contenidoLibre = elemento.getText();
	                	contenidoLibre = UcmUtil.sustituyePageUrl(contenidoLibre, request, response);
	                	elemento.setText(contenidoLibre);
	                }
	               
	                
	                StringWriter swLink = new StringWriter();
	                new XMLOutputter().output(rootLink, swLink);
	                xml = swLink.toString();
	            	
	            	NodeModel nodeModel = NodeModel.parse(new InputSource(new StringReader(xml)));
	            	
	            	org.w3c.dom.Document root = (org.w3c.dom.Document) nodeModel.getNode();
	                org.w3c.dom.Element cp = root.createElement("contextPath");
	                Text tx = root.createTextNode(request.getContextPath());
	                cp.appendChild(tx);
	                
	                root.getDocumentElement().appendChild(cp); 
	                temp.process(nodeModel.getChildNodes(), sw);
	                html = sw.toString();
	            }
	           setContenidoEnCache(request, html, contenidoId+templateId, ultima);
			}
			contenido.setContent(html);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return contenido;
    }
    
    @SuppressWarnings("unchecked")
    public static Element getElemento(String nombreABuscar, Element root, String prefix){
        Element e = null;
        List lista = root.getChildren();
        for (int i=0;i<lista.size();i++){
            Element elemento = (Element)lista.get(i);
            String index = elemento.getAttributeValue("index");
            String nombre = prefix + elemento.getName() + "_" + index;
            if (nombreABuscar.equals(nombre)){
                e = elemento;
                break;
            }
            if (elemento.getChildren()!= null && elemento.getChildren().size()>0){
                e = getElemento(nombreABuscar, elemento, nombre+"_");
                if (e != null) break;
            }
        }
        return e;
    }
   
    @SuppressWarnings("unchecked")
    public static List<Element> getElementosByTipo(Element root, String tipoABuscar){
        Element e = null;
        List<Element> listaElementos = new ArrayList();
        List lista = root.getChildren();
        for (int i=0;i<lista.size();i++){
            Element elemento = (Element)lista.get(i);
            String tipo = elemento.getAttributeValue("tipo");
            if (tipoABuscar.equals(tipo)){
                e = elemento;
                listaElementos.add(e);
            }
            if (elemento.getChildren()!= null && elemento.getChildren().size()>0){
            	List<Element> listaElementosHijos = getElementosByTipo(elemento, tipoABuscar);
                if (listaElementosHijos != null) listaElementos.addAll(listaElementosHijos);
            }
        }
        return listaElementos;
    }
    
    
    @SuppressWarnings("unchecked")
    public static int getLastIndexOfName(Element element, String nameOfChilds){
        int lastIndex = -1;
        List lista = element.getContent();
        for (int i=0;i<lista.size();i++){
            if (lista.get(i) instanceof Element){
                Element hijo = (Element) lista.get(i);
                if (hijo.getName().equals(nameOfChilds)) lastIndex = i;
            }
        }
        return lastIndex;
    }
    
    @SuppressWarnings("unchecked")
    public static Element getNextElement(Element elemento){
        Element next = null;
        List lista = elemento.getParentElement().getChildren(elemento.getName());
        for (int i=0;i<lista.size();i++){
            Element hermano = (Element) lista.get(i);
            if (hermano.equals(elemento)){
                if (i<lista.size()-1) next = (Element) lista.get(i+1);
                break;
            }
        }
        return next;
    }
    
    @SuppressWarnings("unchecked")
    public static Element getPreviousElement(Element elemento){
        Element previous = null;
        List lista = elemento.getParentElement().getChildren(elemento.getName());
        for (int i=0;i<lista.size();i++){
            Element hermano = (Element) lista.get(i);
            if (hermano.equals(elemento)){
                if (i>0) previous = (Element) lista.get(i-1);
                break;
            }
        }
        return previous;
    }
    
    public static int getNumberOfElements(Element elemento){
        return elemento.getParentElement().getChildren(elemento.getName()).size();
    }
    
    @SuppressWarnings("unchecked")
    public static String getHTMLfromXML(String xml, HttpServletRequest request, StringWriter xmlWriterOut, List editores){
        SAXBuilder builder;
        Document root = null;
        String html = "";
        try {
            builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
            StringReader sr = new StringReader(xml);
            root = builder.build(sr);
            Element rootElement = root.getRootElement();
            if(request!=null){
	            String comando = request.getParameter("__Comando__");
	            if (comando != null){
	                String nombreDelElemento = request.getParameter("__Elemento__");            
	                if (nombreDelElemento != null){
	                    Element elemento = getElemento(nombreDelElemento, rootElement,"");
	                    if (comando.equals("nuevo")){
	                        Element nuevoElemento = (Element) elemento.clone();
	                        nuevoElemento.removeAttribute("index");
	                        int index = getLastIndexOfName(elemento.getParentElement(), elemento.getName());
	                        elemento.getParentElement().addContent(index + 1, nuevoElemento);
	                    }else  if (comando.equals("eliminar")){
	                        if (getNumberOfElements(elemento)>1)
	                            elemento.getParentElement().removeContent(elemento);
	                    }else  if (comando.equals("moverArriba")){
	                        Element previous = getPreviousElement(elemento);
	                        if (previous != null){
	                            int index = elemento.getAttribute("index").getIntValue();
	                            elemento.setAttribute("index",String.valueOf(index - 1));
	                            previous.setAttribute("index",String.valueOf(index));
	                        }
	                    }else  if (comando.equals("moverAbajo")){
	                        Element next = getNextElement(elemento);
	                        if (next != null){
	                            int index = elemento.getAttribute("index").getIntValue();
	                            elemento.setAttribute("index",String.valueOf(index + 1));
	                            next.setAttribute("index",String.valueOf(index));
	                        }
	                    }
	
	                }
	            }
            }
            MultiValueMap nombres = new MultiValueMap();        
            html = "<table width='100%'>";
            List lista = rootElement.getChildren();
            for (int i=0;i<lista.size();i++){
                Element elemento = (Element)lista.get(i);
                html += getHTML(elemento, request, "", "", nombres, editores);
            }
            html += "</table>";
            
            html = html.replace("/contentserver/", request.getContextPath()+"/servlet/content/lastversion/");
            
            new XMLOutputter().output(root, xmlWriterOut);
        
        }catch (Exception e){
           e.printStackTrace();
        }
        return html;
    }
    
    public static String boton(String pathImagen, String title, String onclick, String onmouseover, String onmouseout){
        String html = null;
        html =  "<div style='width: 23px; float: left;'>"+
                    "<a href='javascript:void(0)' class='button' title='"+title+"' onclick=\""+onclick+"\" onmouseover=\""+onmouseover+"\" onmouseout=\""+onmouseout+"\">"+
                        "<span unselectable='on' class='norm' onmouseover=\"className='over'\" onmouseout=\"className='norm'\" onmousedown=\"className='push'\" onmouseup=\"className='over'\">"+
                            "<img class='button' src='"+pathImagen+"'></img>"+
                        "</span>"+
                    "</a>"+
                "</div>";
        return html;
    }
    public static String botonConLabel(String pathImagen, String label, String onclick, String onmouseover, String onmouseout){
        String html = null;
        html =  "<div style='float: left;'>"+
                    "<a href='javascript:void(0)' class='button' onclick=\""+onclick+"\" onmouseover=\""+onmouseover+"\" onmouseout=\""+onmouseout+"\">"+
                        "<span class='norm' onmouseover=\"className='over'\" onmouseout=\"className='norm'\" onmousedown=\"className='push'\" onmouseup=\"className='over'\">"+
                            "<table><tr><td>"+
                                "<img class='button' src='"+pathImagen+"'></img>"+
                            "</td><td>"+label+"</td><td style='width:5px;'></td></tr></table>"+
                        "</span>"+
                    "</a>"+
                "</div>";
        return html;
    }
    public static String repetible(String nombre, Element elemento, HttpServletRequest request){
        String btnNuevo = boton(request.getContextPath()+"/resources/images/new.png", "","exeComando('nuevo')","","");
        String btnMoveDown = boton(request.getContextPath()+"/resources/images/move_down.png", "","exeComando('moverAbajo')","","");
        String btnMoveUp = boton(request.getContextPath()+"/resources/images/move_up.png", "","exeComando('moverArriba')","","");
        String btnDelete = boton(request.getContextPath()+"/resources/images/deletecontent.png", "","exeComando('eliminar')","","");
        if (getNumberOfElements(elemento)==1){
            btnMoveDown = "<img src='"+request.getContextPath()+"/resources/images/move_down_in.png'></img>";
            btnMoveUp = "<img src='"+request.getContextPath()+"/resources/images/move_up_in.png'></img>";
            btnDelete = "<img src='"+request.getContextPath()+"/resources/images/deletecontent_in.png'></img>";
        }else{
            if (getPreviousElement(elemento)==null)
                btnMoveUp = "<img src='"+request.getContextPath()+"/resources/images/move_up_in.png'></img>";
            if (getNextElement(elemento)==null)
                btnMoveDown = "<img src='"+request.getContextPath()+"/resources/images/move_down_in.png'></img>";
        }
        String html="<td style='vertical-align: top; width: 25px;' id='bRep"+nombre+"'>"+
                        boton(request.getContextPath()+"/resources/images/xmledit_add.png", "","","showToolBarXml(this, true,'"+nombre+"'); setTimeToolBarXml(false);","setTimeToolBarXml(true);")+
                        "<div id='div"+nombre+"' style='display:none'>"+
                            "<table onclick='showToolBarXml(null,false)' onmouseover='setTimeToolBarXml(false)' onmouseout='setTimeToolBarXml(true)' cellpadding='0' cellspacing='0'>" +
                                "<tr>"+
                                    "<td>"+btnDelete+"</td>"+
                                    "<td>"+btnMoveUp+"</td>"+
                                    "<td>"+btnMoveDown+"</td>"+
                                    "<td>"+btnNuevo+"</td>"+
                                "</tr>"+
                            "</table>"+
                        "</div>"+
                    "</td>";
        return html;
    }
    
    @SuppressWarnings("unchecked")
    public static int getIndexOfElement(String nombre, MultiValueMap nombres){
        int index = 0;
        if (nombres.containsKey(nombre)){
            List lista = (List) nombres.get(nombre);
            index = lista.size();
        }
        nombres.put(nombre, "");
        return index;
    }
    
    @SuppressWarnings("unchecked")
    public static String getHTML(Element elemento, HttpServletRequest request, String prefix, String oldPrefix, 
    							 MultiValueMap nombres, List editores){
        String texto    = elemento.getText();
        String label    = elemento.getAttributeValue("label");
        String strRep   = elemento.getAttributeValue("repetir");
        String tipo     = elemento.getAttributeValue("tipo");
        String oldIndex = elemento.getAttributeValue("index");
        String values = elemento.getAttributeValue("values");
        
        boolean repetir = false;
        if (strRep==null) strRep="no";
        if (label==null) label = elemento.getName();
        if (oldIndex == null) oldIndex = "";
        if (strRep.equals("si")) repetir=true;
        if (tipo == null) tipo = "";
        if (values == null) values = "";
        
        String html = "";
        int index = getIndexOfElement(elemento.getName(), nombres);
        String nombre = prefix + elemento.getName() + "_" + index;
        String oldNombre = oldPrefix + elemento.getName() + "_" + oldIndex;
        String valor = request.getParameter(oldNombre);
        
        if (valor!=null){
            if (!tipo.equals("libre"))
                texto = HTMLEncode(valor);
            else 
                texto = valor;
        }
        
        //obtener url del archivo referenciado en UCM
        String idArchivo  = "";
        if(texto.contains("/cv") && texto.length()>8){
        	idArchivo = texto.substring(texto.indexOf("/cv"), texto.indexOf("/cv")+9);
        	idArchivo = idArchivo.replace("/", "");
        	idArchivo = StringUtils.upperCase(idArchivo);
        }
        
        elemento.setAttribute("index", String.valueOf(index));
        html = "<tr>";
        html+="<td width='10%' align='right' nowrap>"+label+":</td>";
        List hijos = elemento.getChildren();
        if (hijos!=null && hijos.size()>0){
            MultiValueMap nombresHijos = new MultiValueMap();
            html+="<td>"+
                    "<input type='hidden' name='"+nombre+"'>"+
                    "<table width='100%' style='border: 1px #AAAAAA solid'>";
            for (int i=0;i<hijos.size();i++){
                Element hijo = (Element)hijos.get(i);
                html+=getHTML(hijo,request, nombre+"_", oldNombre+"_", nombresHijos, editores);
            }
            html += "</table>"+
                  "</td>";
        }else{
            elemento.setText("");
            if (tipo==null) tipo = "";
            if (tipo.equals("texto")){
                html += "<td><textarea class='textXML' name='"+nombre+"'>"+texto+"</textarea></td>";
            }else if (tipo.equals("imagen")){
                html += "<td>"+
                            "<table width='100%'>"+
                                "<tr>"+
                                    "<td width='100%'><textarea class='textXML' name='"+nombre+"' id='"+nombre+"'>"+texto+"</textarea></td>"+
                                    "<td>"+boton(request.getContextPath()+"/resources/images/preview.png", "","openExplorer('"+nombre+"','imagen','"+idArchivo+"')","","")+"</td>"+
                                "</tr>"+
                            "</table>"+
                        "</td>";
            }else if (tipo.equals("libre")){
                editores.add(nombre);
                html +="<td>"+
			                "<table width='100%'>"+
			                "<tr>"+
			                    "<td width='100%'><textarea class='textXML' name='"+nombre+"' id='"+nombre+"'>"+texto+"</textarea></td>"+
			                    "<td></td>"+
			                "</tr>"+
			            "</table>"+
			            "<script type='text/javascript'> "+
		            		"crearEditor('"+nombre+"'); "+
		                "</script> "+
			        "</td>";
            }else if (tipo.equals("flash")){
                html += "<td>"+
                "<table width='100%'>"+
                    "<tr>"+
                        "<td width='100%'><textarea class='textXML' name='"+nombre+"' id='"+nombre+"'>"+texto+"</textarea></td>"+
                        "<td>"+boton(request.getContextPath()+"/resources/images/preview.png", "","openExplorer('"+nombre+"','flash','"+idArchivo+"')","","")+"</td>"+
                    "</tr>"+
                "</table>"+
            "</td>";
			}else if (tipo.equals("general")){
                html += "<td>"+
                "<table width='100%'>"+
                    "<tr>"+
                        "<td width='100%'><textarea class='textXML' name='"+nombre+"' id='"+nombre+"'>"+texto+"</textarea></td>"+
                        "<td>"+boton(request.getContextPath()+"/resources/images/preview.png", "","openExplorer('"+nombre+"','todos','"+idArchivo+"')","","")+"</td>"+
                    "</tr>"+
                "</table>"+
            "</td>";
			}else if (tipo.equals("link")){
                html += "<td>"+
                "<table width='100%'>"+
                    "<tr>"+
                        "<td width='100%'><textarea class='textXML' name='"+nombre+"' id='"+nombre+"'>"+texto+"</textarea></td>"+
                        "<td>"+boton(request.getContextPath()+"/resources/images/preview.png", "","openLinkExplorer('"+nombre+"','todos','"+idArchivo+"')","","")+"</td>"+
                    "</tr>"+
                "</table>"+
            "</td>";
			}else if (tipo.equals("boolean")){
				if(texto.equals("true")){
					html += "<td>" +
					"Si <input type='radio' id='"+nombre+"' name='"+nombre+"' value='true' checked='checked' /> " +
					"No <input type='radio' id='"+nombre+"' name='"+nombre+"' value='false'/>" +
					"</td>";
				}else{
					html += "<td>" +
					"Si <input type='radio' id='"+nombre+"' name='"+nombre+"' value='true'/> " +
					"No <input type='radio' id='"+nombre+"' name='"+nombre+"' value='false' checked='checked'/>" +
					"</td>";
				}
				
			}else if (tipo.equals("multivalue")){
				html += "<td>" +
				"<select name='"+nombre+"' id='"+nombre+"'>";
				if(StringUtils.isNotEmpty(values)){
					String[] opciones = values.split(",");
					for(int i=0; i<opciones.length; i++){
						if(texto.equalsIgnoreCase(opciones[i])){
							html += "<option selected='true'>"+opciones[i]+"</option>";
						}else{
							html += "<option>"+opciones[i]+"</option>";
						}
					}
				}
				html += "</select>" + 
				"</td>";
			}
        }
        if (repetir) html += repetible(nombre, elemento, request);
        html += "</tr>";
        return html;
    }
    
    public static String getContentById(String idContent, String idTemplate, boolean ultima, boolean canEdit, 
    									HttpServletRequest request) throws Exception{
        String contenido = null;
        try{
        	return UcmUtil.getContentByName(idContent, ultima);
        }catch(Exception e){
            System.out.println("Error al obtener el contenido en getContentById() "+idContent+": "+e.getMessage());
        }
        return contenido;
    }
    
    public static String HTMLEncode(String txt) {
		return StringEscapeUtils.escapeHtml(txt);
	}
    public static String listToString(List lista) {
        String str="";
        for (int i=0;i<lista.size(); i++){
            if (i>0) str+=",";
            str += (String)lista.get(i);
        }
        return str;
    }
    public static boolean regeneraContenido(String id, boolean canEdit, HttpServletRequest request){
        boolean ok = false;
        String xml = "";
        try{
        	xml = UcmUtil.getContentByName(id, true);
            ok = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return ok;
    }
    
    
    public static String menuContextualImg(String label, String srcImg, String funcion){
        String html = "<div onclick="+funcion+" class='menu' onmouseover=\"this.className='menuHover'\" onmouseout=\"this.className='menu'\">"+
                "    <table cellpadding='0' cellspacing='0'>"+
                "        <tr>"+
                "            <td width='26'>&nbsp;<img src='"+srcImg+"'></img></td>"+
                "            <td>"+label+"</td>"+
                "        </tr>"+
                "    </table>"+
                "</div>";
        return html;
    }
    
    public static String menuSeparador(){
        return "<div class='separadorMenu'></div>";
    }
	
    @SuppressWarnings("unchecked")
    public static String getContentInclude(String content, HttpServletRequest request) throws Exception{
    	String contentInclude = "";
    	
    	if(content.contains("include")){
        	SAXBuilder builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
        	StringReader sr = new StringReader(content);
        	Document root = builder.build(sr);
        	Element rootElement = root.getRootElement();
        	List lista = rootElement.getChildren();
        	for (int i=0;i<lista.size();i++){
        	    Element elemento = (Element)lista.get(i);
        	    String texto = elemento.getText();
	            String tipo = elemento.getAttributeValue("tipo");
	            if("include".equals(tipo)){
        	    	String xmlIclude = getContentById(texto, "", true, true, request);
        	    	
        	    	SAXBuilder builderInclude = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
                	StringReader srInclude = new StringReader(xmlIclude);
                	Document rootInclude = builderInclude.build(srInclude);
                	Element rootElementInclude = rootInclude.getRootElement();
                	List listaInclude = rootElementInclude.getChildren();
                	List listaClone = new ArrayList();
                	int index = getLastIndexOfName(elemento.getParentElement(), elemento.getName());
                	for(int j=0; j<listaInclude.size();j++){
                		Element elementoData = (Element)listaInclude.get(j);
                		Element elementoAIncluir = (Element)elementoData.clone();
                		listaClone.add(elementoAIncluir);
                	}
                	elemento.getParentElement().setContent(index+1, listaClone);
                	elemento.getParentElement().removeContent(elemento);
        	    }
        	}
        	StringWriter sw = new StringWriter();
            new XMLOutputter().output(root, sw);
            contentInclude = sw.toString();
            return contentInclude;
    	}
    	return content;
    }
    
    
    public static PaginaFormBean getContenidoConEstructura(String contenidoId, String estructuraId, 
    											   HttpServletRequest request) throws Exception {
    	
    	String xmlEstructura ="";
    	String contenidoNuevo = "";
    	String estructuraIdDoc = "";
    	String xmlContenido =  UcmUtil.getContentByName(contenidoId, true);
    	PaginaFormBean contenidoBean = new PaginaFormBean();
    	
    	if(StringUtils.isNotEmpty(xmlContenido)){
    		Document rootBase = null;
        	SAXBuilder builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
        	rootBase = builder.build(new StringReader(xmlContenido));
        	Element base = rootBase.getRootElement();
        	estructuraIdDoc = base.getAttributeValue("estructuraid");
    	}
    	
    	if(StringUtils.isNotEmpty(estructuraIdDoc)){
    		xmlEstructura = UcmUtil.getContentByName(estructuraIdDoc, true);
    		xmlEstructura = ContenidoHelper.getContentInclude(xmlEstructura, request);
    		contenidoNuevo = ContenidoHelper.matchXmlData(xmlEstructura, xmlContenido);
    		contenidoBean.setEstructuraId(estructuraIdDoc);
    	}else if(StringUtils.isNotEmpty(estructuraId)){
    		xmlEstructura = UcmUtil.getContentByName(estructuraId, true);
    		xmlEstructura = ContenidoHelper.getContentInclude(xmlEstructura, request);
    		contenidoNuevo = ContenidoHelper.matchXmlData(xmlEstructura, xmlContenido);
    		contenidoBean.setEstructuraId(estructuraId);
    	}else{
    		contenidoNuevo = xmlEstructura;
    	}
    	
    	contenidoBean.setContenido(contenidoNuevo);
    	
    	return contenidoBean;
    }
    
    public static String matchXmlData(String xmlBase, String xmlData){
        String newXml = null;
        try {
        	if(StringUtils.isNotBlank(xmlBase) && StringUtils.isNotBlank(xmlData)){
        		Document rootBase = null, rootData=null;
                SAXBuilder builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
                rootBase = builder.build(new StringReader(xmlBase));
                rootData = builder.build(new StringReader(xmlData));
                Element base = rootBase.getRootElement();
                Element data = rootData.getRootElement();
                match(base, data); 
                StringWriter xmlWriterOut = new StringWriter();
                new XMLOutputter().output(rootBase, xmlWriterOut);
                newXml = xmlWriterOut.toString();
        	}else if(StringUtils.isNotEmpty(xmlBase)){
        		return xmlBase;
        	}else{
        		return xmlData;
        	}
        }catch (Exception e){
            e.printStackTrace();
        }
        return newXml;
    }
    public static void match(Element base, Element data){
        Object lista[] = (Object []) base.getChildren().toArray();
        for (int i = 0; i<lista.length; i++){
            Element nodoBase = (Element) lista[i];
            Object nodosData[] = (Object []) data.getChildren(nodoBase.getName()).toArray();
            for (int j=0;j<nodosData.length; j++){
                Element nodoData = (Element) nodosData[j];
                Element nodoAdd = null;
                if (nodoBase.getChildren() != null && nodoBase.getChildren().size()>0){
                    nodoAdd = (Element) nodoBase.clone();
                    match(nodoAdd, nodoData);
                }else{
                    nodoAdd = (Element) nodoData.clone();
                }
                int index = getLastIndexOfName(base, nodoBase.getName())+1;
                if (j==0){
                    base.removeChild(nodoBase.getName());
                    index--;
                }
                
                if("multivalue".equalsIgnoreCase(nodoAdd.getAttributeValue("tipo")) && "multivalue".equalsIgnoreCase(nodoBase.getAttributeValue("tipo"))){
                	nodoAdd.setAttribute("values", nodoBase.getAttributeValue("values"));
                }
                
                base.addContent(index, nodoAdd);
            }
        }
    }
	
    public static void indexarContenido(String contenidoId, boolean isApproved) throws Exception{
    	indexarContenido(contenidoId, isApproved, null, false);
    }
    public static void indexarContenido(String contenidoId, boolean isApproved, List<String> tipos,boolean borrarTipo) throws Exception{
    	String xml = UcmUtil.getContentByName(contenidoId, true);
    	Map<String,String> mapToIndex = getMapToIndex(xml);
    	
    	if(mapToIndex.size()==0){
    		return;
    	}
    	
    	mapToIndex.put("id", contenidoId);
    	
    	/*if(isApproved){
    		mapToIndex.put("id", contenidoId);
    	}else{
    		mapToIndex.put("id", "NA_"+contenidoId);
    	}
    	
    	mapToIndex.remove(mapToIndex.get("tipo")+"_id");*/
    	
    	/*if(!isApproved){
    		mapToIndex.put("tipo", "noAprobado_"+mapToIndex.get("tipo"));
    	}*/
    	
    	if(borrarTipo){
    		if(tipos != null && StringUtils.isNotBlank(mapToIndex.get("tipo")) && !tipos.contains(mapToIndex.get("tipo"))){
    			tipos.add(mapToIndex.get("tipo"));
    			SolrHelper.borrarByQuery("tipo:"+mapToIndex.get("tipo"));
    		}
    	}
    	
    	//Si el tipo es de Newsletter o noticia entra al trasformador, para cambiar campos de fechas
    	String prop = ConfigurationHelper.getPropiedad("carga."+mapToIndex.get("tipo")+".transformador");
    	Transformador transformador = null;
		if(StringUtils.isNotBlank(prop)) {
			Class<?> clazzTransformador = Class.forName(prop);
			transformador = (Transformador)clazzTransformador.newInstance();
			
			if (transformador!=null)
				transformador.transforma(mapToIndex, 0);
		}
		
		SolrHelper.indexarContenido(mapToIndex, isApproved);
		log.info("indexando contenido con ID="+contenidoId);
    }
    
    /*Metodo para obtener el mapa a idexar a partir de un xml*/
    @SuppressWarnings("unchecked")
    public static Map<String, String> getMapToIndex(String xml){
    	Map<String, String> mapToIndex = new LinkedHashMap<String,String>();
    	try{
            SAXBuilder builder;
            Document root = null;
            try {
                builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
                root = builder.build(new StringReader(xml));
            }catch(Exception ee){ 
                System.out.println("Exception building Document in getMapToIndex(): " + ee);
            }
            
            if(root!=null && root.getRootElement().getChildren()!= null){
            	String indexar = root.getRootElement().getAttributeValue("indexar")!=null?root.getRootElement().getAttributeValue("indexar"):"false";
            	String tipo = root.getRootElement().getAttributeValue("tipo")!=null?root.getRootElement().getAttributeValue("tipo"):"";
            	if("true".equalsIgnoreCase(indexar) && StringUtils.isNotEmpty(tipo)){
            		List lista = root.getRootElement().getChildren();
                	mapToIndex.put("tipo", tipo);
                    for (int i=0;i<lista.size(); i++){
                    	getDataToMap((Element)lista.get(i), mapToIndex, "",tipo, false);
                    }
            	}
            }
            
            /*if(mapToIndex.size()>0 && StringUtils.isNotEmpty(mapToIndex.get("id"))){
            	String id = mapToIndex.get("id");
                mapToIndex.remove(id);
                mapToIndex.put("id", mapToIndex.get("tipo")+"_"+id);
            }*/
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return mapToIndex;
    }
    
    @SuppressWarnings("unchecked")
    private static Map<String, String> getDataToMap(Element elemento, Map<String, String> mapToIndex, 
    												String prefix, String tipoXml, boolean esRepetible){
        String tipo = elemento.getAttributeValue("tipo");
        String indexarElemento = elemento.getAttributeValue("indexar");
        String strRepetir = elemento.getAttributeValue("repetir");
        
    	if (strRepetir==null) strRepetir="no";
        if (tipo==null) tipo = "";
        if(StringUtils.isEmpty(indexarElemento)) indexarElemento="true";
       
        String httpName = prefix+elemento.getName();
        if (elemento.getChildren()!=null && elemento.getChildren().size()>0 && "true".equalsIgnoreCase(indexarElemento) && esRepetible==false){
            List lista = elemento.getChildren();
            for (int j=0;j<lista.size(); j++){
            	Element elementoHijo = (Element)lista.get(j);
            	String strRep = elementoHijo.getAttributeValue("repetir");
            	if (strRep==null) strRep="no";
            	boolean repetir = false;
            	if (strRep.equals("si")) repetir=true;
                
            	if(repetir){
            		getDataToMap(elementoHijo, mapToIndex, httpName+"_", tipoXml, true);
            	}else{
            		getDataToMap(elementoHijo, mapToIndex, httpName+"_", tipoXml, false);
            	}
            	
            }
        }else if ("true".equalsIgnoreCase(indexarElemento) && "no".equalsIgnoreCase(strRepetir) || "id".equalsIgnoreCase(elemento.getName())){
        	mapToIndex.put(tipoXml+"_"+httpName, elemento.getValue().trim());
        }
        return mapToIndex;
    }
    
    public static String getContentFromCache(HttpServletRequest request, String id, boolean ultima) throws Exception{
		Object results = null;
		Cache cache = CacheFactory.getCache(Constantes.CABLEVISION_CONTENIDO);
		String key = Constantes.CONTENIDO_CACHE_KEY+id+"."+ultima;
		results = cache.get(key);
		if(results!=null){
			if(results instanceof String){
				return (String)results;
			}
		}
		return null;
	}
	
    public static String setEstructuraIdAlContenido(String xmlContenido, String estructuraId) throws Exception {
    	Document rootBase = null;
    	SAXBuilder builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser");
    	rootBase = builder.build(new StringReader(xmlContenido));
    	Element base = rootBase.getRootElement();
    	base.setAttribute("estructuraid", estructuraId.toUpperCase());
    	
    	StringWriter sw = new StringWriter();
	    new XMLOutputter().output(rootBase, sw);
	    String contentInclude = sw.toString();
        
	    return contentInclude;
    }
    
    
	public static void setContenidoEnCache(HttpServletRequest request, String contenido, String id, 
										   boolean ultima) throws Exception{
		Cache cache = CacheFactory.getCache(Constantes.CABLEVISION_CONTENIDO);
		String key = Constantes.CONTENIDO_CACHE_KEY+id+"."+ultima;
		cache.put(key, contenido);
	}
    
	public static Configuration getTemplatesEnCache() throws Exception{
		Object results = null;
		Cache cache = CacheFactory.getCache(Constantes.CABLEVISION_CONTENIDO);
		results = cache.get(Constantes.TEMPLETES_CACHE_KEY);
		if(results instanceof Configuration){
			return (Configuration)results;
		}
		return null;
	}
	
	public static void setTemplatesEnCache(Map<String, CvTemplate> templates) throws Exception{
		Cache cache = CacheFactory.getCache(Constantes.CABLEVISION_CONTENIDO);
		Configuration cfg = new Configuration();
		StringTemplateLoader stl = new StringTemplateLoader();
		Iterator<Map.Entry<String,CvTemplate>> it = templates.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,CvTemplate> e = (Map.Entry<String,CvTemplate>) it.next();
			stl.putTemplate(e.getValue().getId(), e.getValue().getContent());
            cfg.setTemplateLoader(stl); 	
		}
		
		cache.put(Constantes.TEMPLETES_CACHE_KEY, cfg);
	}
	
	//TODO método para obtener los templates de disco
	/*
	public static void setTemplatesEnCache(Map<String, CvTemplate> templates) throws Exception{
		Cache cache = CacheFactory.getCache(Constantes.CABLEVISION_CONTENIDO);
		StringTemplateLoader stl = new StringTemplateLoader();
		Configuration cfg =  new Configuration();
		File f = new File("contenido/templates");
		File[] listaTemplates = f.listFiles();
		if(listaTemplates!=null){
			for (int i=0; i<listaTemplates.length; i++ ){
				File template = listaTemplates[i];
				String name = template.getName().replace(".fm", "");
				name = template.getName().replace(".ftl", "");
				stl.putTemplate(name.toUpperCase(), readFromFile(f.getPath()+"/"+template.getName())); 
	            cfg.setTemplateLoader(stl); 
			}
		}
		cache.put(Constantes.TEMPLETES_CACHE_KEY, cfg);
	}*/
	
	/**
	 * Método para indexar todo el contenido de un folder en ucm
	 * si el folderid viene vacío indexará todo el contenido de ucm
	*/
	public static void indexarContenidoUcm() throws Exception{
		List<String> contenidoUCM = UcmUtil.getIdsContenido();
		List<String> tipos = new ArrayList<String>();
		if(contenidoUCM.size()>0){
			Iterator<String> it = contenidoUCM.iterator();
			while(it.hasNext()){
				String idContenido = (String)it.next();
				indexarContenido(idContenido, true, tipos, true);
			}
		}
		log.info("termino de indexar contenido");
	}
	
}
