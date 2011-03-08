package com.cablevision.service.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import com.cablevision.dao.IPaperlessDao;
import com.cablevision.service.IPaperlessService;
import com.cablevision.vo.CvPaperless;
import com.cablevision.vo.CvPaperlessHistorial;

/**
 * The service class for the entities: CvPaperless, CvPaperlessHistorial.
 */
public class PaperlessSpringService implements IPaperlessService {
	protected final Log log = LogFactory.getLog(PaperlessSpringService.class);

	private static final String OK = "OK";
	private static final String ERROR = "Error!";
	private static final String STATUS_ALTA = "A";
	private static final String STATUS_UPDATE = "U";
	private static final String STATUS_BAJA = "B";
	
	/**
	 * The dao instance injected by Spring.
	 */
	private IPaperlessDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "PaperlessService";
	
	public PaperlessSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>IPaperlessService</code> instance.
	 */
	public static IPaperlessService getInstance(ApplicationContext context) {
		return (IPaperlessService)context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvPaperless findCvPaperlessById(String id) throws Exception {
		try {
			return getDao().findCvPaperlessById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvPaperlessById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvPaperless</code> entity.
	 */
	public List<CvPaperless> findAllCvPaperlesses() throws Exception {
		try {
			return getDao().findAllCvPaperlesses();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvPaperlesses failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvPaperless> findCvPaperlessesByExample(CvPaperless cvPaperless) throws Exception {
		try {
			return getDao().findByExample(cvPaperless);
		} catch (RuntimeException e) {
			throw new Exception("findCvPaperlessesByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvPaperless(CvPaperless cvPaperless) throws Exception {
		try {
			getDao().persistCvPaperless(cvPaperless);
		} catch (RuntimeException e) {
			throw new Exception("persistCvPaperless failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvPaperless(CvPaperless cvPaperless) throws Exception {
		try {
			getDao().removeCvPaperless(cvPaperless);
		} catch (RuntimeException e) {
			throw new Exception("removeCvPaperless failed: " + e.getMessage());
		}
	}
	public String savePaperless(String contrato,  String nombre,String idUsuario,String email,Date fecha) {
		try {
			if(fecha==null){
				fecha = new Date();
			}
			CvPaperless paperless = new CvPaperless();
			paperless.setPplContrato(contrato);
			paperless.setPplNombre(nombre);
			paperless.setPplIdusuario(idUsuario);
			paperless.setPplEmail(email);
			paperless.setPplEstatus(STATUS_ALTA);
			paperless.setPplLastUpdate(new java.sql.Date(fecha.getTime()));
			paperless.setPplFechaIngreso(paperless.getPplLastUpdate());
			getDao().persistCvPaperless(paperless);
			
			saveCvPaperlessHistorial(contrato, STATUS_ALTA,fecha,idUsuario,nombre,email);
			return OK;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return ERROR;
		}
	}
	public String updatePaperless(String contrato,  String nombre,String idUsuario,String newEmail,Date fecha) {
		CvPaperless paperless;
		try {
			if(fecha==null){
				fecha = new Date();
			}
			paperless = findCvPaperlessById(contrato);
			
			if(paperless==null){
				savePaperless(contrato, nombre, idUsuario, newEmail, fecha);
			}else{
				paperless.setPplEmail(newEmail);
				paperless.setPplLastUpdate(new java.sql.Date(fecha.getTime()));
				paperless.setPplNombre(nombre);
				getDao().persistCvPaperless(paperless);

				saveCvPaperlessHistorial(contrato, STATUS_UPDATE,fecha,idUsuario,nombre,newEmail);
			}
			return OK;
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	public String removePaperless(String contrato,String nombre,Date fecha, String idUsuario) {
		try {
			if(fecha==null){
				fecha = new Date();
			}
			
			CvPaperless paperless = findCvPaperlessById(contrato);
			if(paperless != null) {
				getDao().removeCvPaperless(paperless);
			}
			saveCvPaperlessHistorial(contrato, STATUS_BAJA,fecha,idUsuario,nombre,null);
			return OK;
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	public void saveCvPaperlessHistorial(String idPaperless, String status,Date fecha,
			String usuario, String nombre,String email) {
		CvPaperlessHistorial historial = new CvPaperlessHistorial();
		historial.setPhIdHistorial(Long.valueOf(getDao().getCvPaperlessHistoryNextSeqValue()));
		historial.setPhFechaModificacion(new java.sql.Date(fecha.getTime()));
		historial.setPhContrato(idPaperless);
		historial.setPhTipoCambio(status);
		historial.setPhUser(usuario);
		historial.setPhNombre(nombre);
		historial.setPhEmail(email);
		getDao().persistCvPaperlessHistorial(historial);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvPaperlessHistorial findCvPaperlessHistorialById(Long id) throws Exception {
		try {
			return getDao().findCvPaperlessHistorialById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvPaperlessHistorialById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvPaperlessHistorial</code> entity.
	 */
	public List<CvPaperlessHistorial> findAllCvPaperlessHistorials() throws Exception {
		try {
			return getDao().findAllCvPaperlessHistorials();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvPaperlessHistorials failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvPaperlessHistorial> findCvPaperlessHistorialsByExample(CvPaperlessHistorial cvPaperlessHistorial) throws Exception {
		try {
			return getDao().findByExample(cvPaperlessHistorial);
		} catch (RuntimeException e) {
			throw new Exception("findCvPaperlessHistorialsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvPaperlessHistorial(CvPaperlessHistorial cvPaperlessHistorial) throws Exception {
		try {
			getDao().persistCvPaperlessHistorial(cvPaperlessHistorial);
		} catch (RuntimeException e) {
			throw new Exception("persistCvPaperlessHistorial failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvPaperlessHistorial(CvPaperlessHistorial cvPaperlessHistorial) throws Exception {
		try {
			getDao().removeCvPaperlessHistorial(cvPaperlessHistorial);
		} catch (RuntimeException e) {
			throw new Exception("removeCvPaperlessHistorial failed: " + e.getMessage());
		}
	}

	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(IPaperlessDao dao) {
		this.dao = dao;
	}
	public IPaperlessDao getDao() {
		return this.dao;
	}
	
	public String getCvPaperlessHistoryNextSeqValue() {
		return getDao().getCvPaperlessHistoryNextSeqValue();
	}
	
}