package com.cablevision.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.cablevision.dao.IUsuarioPortalDao;
import com.cablevision.service.IUsuarioPortalService;
import com.cablevision.vo.CvContrasenaHistorial;
import com.cablevision.vo.CvUsuarioPortal;

/**
 * The service class for the entities: CvContrasenaHistorial, CvUsuarioPortal.
 */
public class UsuarioPortalSpringService implements IUsuarioPortalService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IUsuarioPortalDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "UsuarioPortalService";
	
	public UsuarioPortalSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>IUsuarioPortalService</code> instance.
	 */
	public static IUsuarioPortalService getInstance(ApplicationContext context) {
		return (IUsuarioPortalService)context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvContrasenaHistorial findCvContrasenaHistorialById(Long id) throws Exception {
		try {
			return getDao().findCvContrasenaHistorialById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvContrasenaHistorialById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvContrasenaHistorial</code> entity.
	 */
	public List<CvContrasenaHistorial> findAllCvContrasenaHistorials() throws Exception {
		try {
			return getDao().findAllCvContrasenaHistorials();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvContrasenaHistorials failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvContrasenaHistorial> findCvContrasenaHistorialsByExample(CvContrasenaHistorial cvContrasenaHistorial) throws Exception {
		try {
			return getDao().findByExample(cvContrasenaHistorial);
		} catch (RuntimeException e) {
			throw new Exception("findCvContrasenaHistorialsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvContrasenaHistorial(CvContrasenaHistorial cvContrasenaHistorial) throws Exception {
		try {
			getDao().persistCvContrasenaHistorial(cvContrasenaHistorial);
		} catch (RuntimeException e) {
			throw new Exception("persistCvContrasenaHistorial failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvContrasenaHistorial(CvContrasenaHistorial cvContrasenaHistorial) throws Exception {
		try {
			getDao().removeCvContrasenaHistorial(cvContrasenaHistorial);
		} catch (RuntimeException e) {
			throw new Exception("removeCvContrasenaHistorial failed: " + e.getMessage());
		}
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvUsuarioPortal findCvUsuarioPortalById(String id) throws Exception {
		try {
			return getDao().findCvUsuarioPortalById(id);
		} catch (RuntimeException e) {
			return null;
			//throw new Exception("findCvUsuarioPortalById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvUsuarioPortal</code> entity.
	 */
	public List<CvUsuarioPortal> findAllCvUsuarioPortals() throws Exception {
		try {
			return getDao().findAllCvUsuarioPortals();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvUsuarioPortals failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvUsuarioPortal> findCvUsuarioPortalsByExample(CvUsuarioPortal cvUsuarioPortal) throws Exception {
		try {
			return getDao().findByExample(cvUsuarioPortal);
		} catch (RuntimeException e) {
			throw new Exception("findCvUsuarioPortalsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvUsuarioPortal(CvUsuarioPortal cvUsuarioPortal) throws Exception {
		try {
			getDao().persistCvUsuarioPortal(cvUsuarioPortal);
		} catch (RuntimeException e) {
			throw new Exception("persistCvUsuarioPortal failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvUsuarioPortal(CvUsuarioPortal cvUsuarioPortal) throws Exception {
		try {
			getDao().removeCvUsuarioPortal(cvUsuarioPortal);
		} catch (RuntimeException e) {
			throw new Exception("removeCvUsuarioPortal failed: " + e.getMessage());
		}
	}
	
	public boolean updateCvUsuarioPortal(String idUsuario, String foto) {
		return getDao().updateCvUsuarioPortal(idUsuario, foto);
	}
	
	/**
	 * Return all persistent instances of the <code>CvContrasenaHistorial</code> entity by user id.
	 * @param idUser
	 * @return
	 */
	public List<CvContrasenaHistorial> findCvContrasenaHistorialByIdUser(String idUser) throws Exception{
		try {
			return getDao().findCvContrasenaHistorialByIdUser(idUser);
		} catch (RuntimeException e) {
			throw new Exception("findCvContrasenaHistorialByIdUser failed: " + e.getMessage());
		}
	}

	public void removeSessionIds() throws Exception{
		try{
			getDao().removeSessionIds();
		}catch(RuntimeException e){
			
		}
	}
	
	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(IUsuarioPortalDao dao) {
		this.dao = dao;
	}
	public IUsuarioPortalDao getDao() {
		return this.dao;
	}
}