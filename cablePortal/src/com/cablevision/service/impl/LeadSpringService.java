package com.cablevision.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.context.ApplicationContext;

import com.cablevision.dao.ILeadDao;
import com.cablevision.service.ILeadService;
import com.cablevision.util.ConfigurationHelper;
import com.cablevision.util.MailUtil;
import com.cablevision.vo.CvField;
import com.cablevision.vo.CvLead;
import com.cablevision.vo.CvLeadField;
import com.cablevision.vo.CvLeadStatus;
import com.cablevision.vo.CvLeadType;
import com.cablevision.vo.CvLeadValuefield;
import com.cablevision.vo.ErrorField;

/**
 * The service class for the CvLead entity.
 */
public class LeadSpringService implements ILeadService {
	
	/**
	 * The dao instance injected by Spring.
	 */
	private ILeadDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "LeadService";
	
	public LeadSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>ILeadService</code> instance.
	 */
	public static ILeadService getInstance(ApplicationContext context) {
		return (ILeadService)context.getBean(SERVICE_BEAN_ID);
	}
	
	public List<CvLeadType> findAllLeadTypes()throws Exception{
		return getDao().findAllLeadTypes();
	}
	
	public List<String> validateFields(Map<String,String> map)throws Exception {
		return new ArrayList<String>(getValidatedFields(map).values());
	}
	
	public List<ErrorField> validateField(Map<String,String> map) throws Exception {
		List<ErrorField> errores = new ArrayList<ErrorField>();
		Map<String, String> erroresMap = getValidatedFields(map);
		
		for( Iterator it = erroresMap.keySet().iterator(); it.hasNext();) { 
			String s = (String)it.next();
			if(s.equalsIgnoreCase("Estado") || s.equalsIgnoreCase("EstadoFact")){
				ErrorField error = new ErrorField(s, StringEscapeUtils.escapeHtml(erroresMap.get(s)));
				errores.add(error);
			}else{
				ErrorField error = new ErrorField(s, erroresMap.get(s));
				errores.add(error);
			}
		}
		return errores;
	}
	
	public Map<String,String> getValidatedFields(Map<String,String> mapLead)throws Exception{
		Map<String, String> map = new HashMap<String, String>(mapLead);
		List<CvLeadField> fields = this.findLeadFields(map);
		List<String> errors = new ArrayList<String>();
		
		for(CvLeadField leadField : fields){
			CvField field = leadField.getId().getField();
			
			String name = field.getCfsNamefield();
			String value = map.get(name);
			String mask = field.getCfsMaskfield();
			if("si".equals(field.getCfsIsrequiredfield().toLowerCase())){
				if(StringUtils.isBlank(value)){
					errors.add("El campo "+field.getCfsNamealias()+" es requerido");
					map.put(name, "El campo "+field.getCfsNamealias()+" es requerido");
				}else if(mask != null){
					Pattern pattern = Pattern.compile(mask);
					Matcher fit = pattern.matcher(value);
					if(!fit.matches()){
						errors.add(field.getCfsErrormsgfield());
						map.put(name, field.getCfsErrormsgfield());
					}else 
						map.remove(name);
				} else
					map.remove(name);
			} else {
				map.remove(name);
			}
		}
		
		Integer number = this.findNumberOfTicketsInDay(map.get("Email"));
		if(number>0){
			//errors.add("Por el momento no puedes enviar m&aacute;s tickets");
			map.put("nomore", "Por el momento no puedes enviar m&aacute;s tickets");
		}
		map.remove("leadType");map.remove("IdLeadType");
		map.remove("idStatus");
		return map;
//		return errors;
	}
	
	public String generateId()throws Exception{
		String id = "";
		Double seed = Math.random()*1000;

	    MessageDigest m = MessageDigest.getInstance("MD5");
	    m.update(seed.toString().getBytes(),0,seed.toString().length());			    
	    id = new BigInteger(1,m.digest()).toString(16).toString();
		
		return id;
	}
	
	public Object saveLead(Map<String,String> map)throws Exception{
		String id = this.generateId();
		
		CvLeadType type = this.findLeadTypeById(new Integer(map.get("leadType")!=null?map.get("leadType"):map.get("IdLeadType")));
		CvLeadStatus status = this.findLeadStatusById(new Integer(map.get("idStatus")!=null?map.get("idStatus"):"0"));
		
		CvLead lead = new CvLead(id);
		lead.setLeadType(new CvLeadType(new Long(map.get("leadType")!=null?map.get("leadType"):map.get("IdLeadType")))); 
		lead.setCleDatecreation(new Timestamp(new Date().getTime()));
		lead.setCleDatelead(new Timestamp(new Date().getTime())); //setDateLead(new Date());
		lead.setLeadStatus(status);
		lead.setLeadType(type);
		lead.setCleValid(Boolean.TRUE);
		//lead.setCleIdusuario(0l);	// <--------------------------------------Checar esto, esta incompleto!
		
		this.persistCvLead(lead);
		
		//insertar los valores CvLeadValue
		List<CvLeadField> fields = this.findLeadFields(map);
		for(CvLeadField leadField : fields){
			CvField field = leadField.getId().getField();
			
			CvLeadValuefield value = new CvLeadValuefield();
			value.setField(field);
			value.setLead(lead);
			value.setLvfValue(map.get(field.getCfsNamefield()));
			
			this.persistCvLeadValue(value);
		}
		
		if(map.containsKey("Email")){
			Map<String, String> values = new HashMap<String, String>();
			values.put("nombre", map.get("Nombre"));
			String idLead36 = Integer.toString(lead.getCleIdshowaccount().intValue(),36).toUpperCase();
			
			for(int i=idLead36.length();i<4;i++){
				idLead36 = "0"+idLead36;
			}
			
			values.put("idLead", idLead36);
			

			
			MailUtil.sendMail(
					/*subject*/ ConfigurationHelper.getPropiedad("correo.leads"+type.getCltIdLeadtype()+".subject",ConfigurationHelper.getPropiedad("correo.leads.subject",null )), 
					/*to*/ map.get("Email"), 
					/*from*/ ConfigurationHelper.getPropiedad("correo.leads"+type.getCltIdLeadtype()+".from",ConfigurationHelper.getPropiedad("correo.leads.from",null)), 
					/*template*/ ConfigurationHelper.getPropiedad("correo.leads"+type.getCltIdLeadtype()+".templateId",ConfigurationHelper.getPropiedad("correo.leads.templateId",null)), 
					/*map_values*/ values);
		}
		return id;
	}
	
	public List<CvLeadField> findLeadFields(Map<String,String> form) throws Exception{
		CvLeadType type = new CvLeadType(new Long(form.get("leadType")!=null?form.get("leadType"):form.get("IdLeadType")));
		return getDao().findLeadFields(type);
	}
	
	public Integer findNumberOfTicketsInDay(String email) throws Exception{
		return getDao().findNumberOfTicketsInDay(email);
	}
	
	public CvLeadType findLeadTypeById(Integer idLeadType) throws Exception{
		CvLeadType leadType = new CvLeadType(idLeadType.longValue());
		return getDao().findLeadTypeById(leadType);
	}
	
	public CvLeadStatus findLeadStatusById(Integer idLeadStatus) throws Exception{
		CvLeadStatus status = new CvLeadStatus(idLeadStatus.longValue());
		return getDao().findLeadStatusById(status);
	}
	
	public void persistCvLeadValue(CvLeadValuefield value)throws Exception{
		getDao().persistCvLeadValuefield(value);
	}
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvLead findCvLeadById(Integer id) throws Exception {
		try {
			return getDao().findCvLeadById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvLeadById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvLead</code> entity.
	 */
	public List<CvLead> findAllCvLeads() throws Exception {
		try {
			return getDao().findAllCvLeads();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvLeads failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvLead> findCvLeadsByExample(CvLead cvLead) throws Exception {
		try {
			return getDao().findByExample(cvLead);
		} catch (RuntimeException e) {
			throw new Exception("findCvLeadsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvLead(CvLead cvLead) throws Exception {
		try {
			getDao().persistCvLead(cvLead);
		} catch (RuntimeException e) {
			throw new Exception("persistCvLead failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvLead(CvLead cvLead) throws Exception {
		try {
			getDao().removeCvLead(cvLead);
		} catch (RuntimeException e) {
			throw new Exception("removeCvLead failed: " + e.getMessage());
		}
	}

	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(ILeadDao dao) {
		this.dao = dao;
	}
	public ILeadDao getDao() {
		return this.dao;
	}
}