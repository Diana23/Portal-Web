package com.cablevision.carga;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.solr.client.solrj.SolrServerException;

import com.cablevision.carga.transformador.Transformador;
import com.cablevision.util.SolrHelper;

public class CargaExcel {
	Properties props = new Properties();

	public CargaExcel() {
		InputStream is = CargaExcel.class.getResourceAsStream("carga.properties");
		try {
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generarXML(String tipoCarga, InputStream is) {
		String string = props.getProperty("carga."+ tipoCarga +".columnas");
		String[] columnas = StringUtils.split(string, ',');
		List<Map<String, String>> entryList = null;

		try { 
			entryList = getMaps(is, tipoCarga, true);

			SolrHelper.borrarByQuery("tipo:" + tipoCarga);

			StringBuilder sb = new StringBuilder("<CableXML indexar=\"true\" tipo=\"" + tipoCarga + "\">");
			for(Map<String, String> map : entryList) {
				for(int i=0; i<columnas.length; i++) {
					String columna = columnas[i];
					String val = map.get(columna);
					sb.append("<"+ columna +" tipo=\"texto\" label=\"" + columna + "\">" + 
							"<![CDATA[" + val + "]]>"+
							"</"+ columna +">");
				}
				sb.append("<tipo tipo=\"texto\" label=\"tipo\"><![CDATA[" + tipoCarga + "]]></tipo>");
				sb.append("</CableXML>");
				SolrHelper.indexarContenido(map, sb.toString(),null);

				sb = new StringBuilder("<CableXML indexar=\"true\" tipo=\"" + tipoCarga + "\">");
			}
			SolrHelper.getSolrServer().commit();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			try {
				SolrHelper.getSolrServer().rollback();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (SolrServerException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} 
	}

	/**
	 * Metodo que obtiene una coleccion de mapas de un archivo de Excel.
	 * Cada mapa contiene los datos de un renglon de una hoja de Excel; 
	 * una entrada por columna 
	 * @param is es el stream con el contenido del archivo
	 * @param isOOXMLFormat true | false en caso de ser un archivo de Excel 2007 en adelante
	 * @return la coleccion de mapas
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public List<Map<String, String> > getMaps(InputStream is, String tipoCarga, boolean isOOXMLFormat) throws IOException, InvalidFormatException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		List<Map<String, String>> entryList = new ArrayList<Map<String, String>>();
		PushbackInputStream pbis = new PushbackInputStream(is);

		String string = props.getProperty("carga."+ tipoCarga +".rowInicio");
		//Restar uno por que los rows empiezan de cero
		int rowInicio = Integer.parseInt(string)-1;

		string = props.getProperty("carga."+ tipoCarga +".columnas");
		String[] columnas = StringUtils.split(string, ',');

		string = props.getProperty("carga."+ tipoCarga +".transformador");
		Transformador transformador = null;
		if(StringUtils.isNotBlank(string)) {
			Class<?> clazzTransformador = Class.forName(string);
			transformador = (Transformador)clazzTransformador.newInstance();
		}

		if(isOOXMLFormat) {
			// En caso de ser un archivo de Office 2007+
			Workbook wb = WorkbookFactory.create(pbis);
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> it = sheet.rowIterator();
			int index = 1;
			// Recorre todos los renglones de la hoja
			while(it.hasNext()) {
				Row row = it.next();

				// Comenzara desde el renglon definido por rowInicio
				if(row.getRowNum() >= rowInicio) {
					Map<String, String> map = new HashMap<String, String>();

					// Recorre el arreglo con los nombres de las columnas
					// y obtiene la llave con la posicion de la columna en el renglon actual
					for(int i=0; i<columnas.length; i++) {
						
						String nombreColumna = columnas[i];
						string = props.getProperty("carga."+ tipoCarga +".columna." + nombreColumna);
						int indexColumna = new Integer(string); 
						
						

						//Restar uno por que empiezan de 0
						Cell content = row.getCell(indexColumna-1);
						
						//La primera columna debe tener un  dato, no puede estar vacía
						if(indexColumna==1&&(content==null||"".equals(content.toString()))){
							continue;
						}
						
						if(content!=null){
							content.setCellType(Cell.CELL_TYPE_STRING);
							if(!"".equals(content.toString()))
								map.put(nombreColumna, content.toString());
						}
					}
					map.put("tipo", tipoCarga);
					if(transformador != null)
						transformador.transforma(map,index);
					entryList.add(map);
					index++;
				}
			}
		} else {
			// En caso de ser un archivo inferior a Office 2007
			POIFSFileSystem fs = new POIFSFileSystem(is);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			Iterator<Row> it = sheet.rowIterator();
			int index = 1;
			// Recorre todos los renglones
			while(it.hasNext()) {
				HSSFRow row = (HSSFRow)it.next();

				// Comenzara desde el renglon definido por rowInicio
				if(row.getRowNum() >= rowInicio) {
					Map<String, String> map = new HashMap<String, String>();

					// Recorre el arreglo con los nombres de las columnas
					// y obtiene la llave con la posicion de la columna en el renglon actual
					for(int i=0; i<columnas.length; i++) {
						String nombreColumna = columnas[i];
						string = props.getProperty("carga."+ tipoCarga +".columna." + nombreColumna);
						int indexColumna = new Integer(string); 

						Cell content = row.getCell(indexColumna);
						
						//La primera columna debe tener un  dato, no puede estar vacía
						if(indexColumna==1&&content==null||"".equals(content.toString())){
							continue;
						}
						
						map.put(nombreColumna, content.getStringCellValue());
					}
					map.put("tipo", tipoCarga);
					if(transformador != null)
						transformador.transforma(map,index);
					entryList.add(map);
					index++;
				}
			}
		}

		return entryList;
	}

}
