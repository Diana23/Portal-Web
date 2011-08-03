package com.cablevision.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import com.cablevision.vo.CvEserviceN1;
import com.cablevision.vo.CvEserviceN2;
import com.cablevision.vo.CvEserviceN3;
import com.cablevision.service.ICombosService;
import com.cablevision.dao.ICombosDao;
import com.cablevision.vo.CvBanco;
import com.cablevision.vo.CvTipoTarjeta;

/**
 * The service class for the entities: CvEserviceN1, CvEserviceN2, CvEserviceN3.
 */
public class CombosSpringService implements ICombosService {
	/**
	 * The dao instance injected by Spring.
	 */
	private ICombosDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "CombosService";
	
	public CombosSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>ICombosService</code> instance.
	 */
	public static ICombosService getInstance(ApplicationContext context) {
		return (ICombosService)context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN1 findCvEserviceN1ById(Long id) throws Exception {
		try {
			return getDao().findCvEserviceN1ById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvEserviceN1ById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvEserviceN1</code> entity.
	 */
	public List<CvEserviceN1> findAllCvEserviceN1s() throws Exception {
		try {
			return getDao().findAllCvEserviceN1s();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvEserviceN1s failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvEserviceN1> findCvEserviceN1sByExample(CvEserviceN1 cvEserviceN1) throws Exception {
		try {
			return getDao().findByExample(cvEserviceN1);
		} catch (RuntimeException e) {
			throw new Exception("findCvEserviceN1sByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvEserviceN1(CvEserviceN1 cvEserviceN1) throws Exception {
		try {
			getDao().persistCvEserviceN1(cvEserviceN1);
		} catch (RuntimeException e) {
			throw new Exception("persistCvEserviceN1 failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvEserviceN1(CvEserviceN1 cvEserviceN1) throws Exception {
		try {
			getDao().removeCvEserviceN1(cvEserviceN1);
		} catch (RuntimeException e) {
			throw new Exception("removeCvEserviceN1 failed: " + e.getMessage());
		}
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN2 findCvEserviceN2ById(Long id) throws Exception {
		try {
			return getDao().findCvEserviceN2ById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvEserviceN2ById failed with the id " + id + ": " + e.getMessage());
		}
	}
	
	/**
	 * Find an entity by its name
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN2 findCvEserviceN2IdByName(String name) throws Exception{
		try{
			return getDao().findCvEserviceN2IdByName(name);
		}catch(RuntimeException e){
			throw new Exception("findCvEserviceN2IdByName failed with the name "+ name + ": "+e.getMessage());
		}
	}
	
	/**
	 * Return all persistent instances of the <code>CvEserviceN2</code> entity.
	 */
	public List<CvEserviceN2> findAllCvEserviceN2s() throws Exception {
		try {
			return getDao().findAllCvEserviceN2s();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvEserviceN2s failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvEserviceN2> findCvEserviceN2sByExample(CvEserviceN2 cvEserviceN2) throws Exception {
		try {
			return getDao().findByExample(cvEserviceN2);
		} catch (RuntimeException e) {
			throw new Exception("findCvEserviceN2sByExample failed: " + e.getMessage());
		}
	}
	
	/**
	 * Find a list entity by its En1Id
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvEserviceN2> findCvEserviceN2ByEn1Id(Long id) throws Exception {
		try{
			return getDao().findCvEserviceN2ByEn1Id(id);
		}catch(RuntimeException e){
			throw new Exception("findCvEserviceN2ByEn1Id failed with the id " + id + ": " + e.getMessage());
		}
	}
	
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvEserviceN2(CvEserviceN2 cvEserviceN2) throws Exception {
		try {
			getDao().persistCvEserviceN2(cvEserviceN2);
		} catch (RuntimeException e) {
			throw new Exception("persistCvEserviceN2 failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvEserviceN2(CvEserviceN2 cvEserviceN2) throws Exception {
		try {
			getDao().removeCvEserviceN2(cvEserviceN2);
		} catch (RuntimeException e) {
			throw new Exception("removeCvEserviceN2 failed: " + e.getMessage());
		}
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN3 findCvEserviceN3ById(Long id) throws Exception {
		try {
			return getDao().findCvEserviceN3ById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvEserviceN3ById failed with the id " + id + ": " + e.getMessage());
		}
	}
	
	/**
	 * Find a list entity by its En2Id
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvEserviceN3> findCvEserviceN3ByEn2Id(Long id) throws Exception {
		try{
			return getDao().findCvEserviceN3ByEn2Id(id);
		}catch(RuntimeException e){
			throw new Exception("findCvEserviceN3ByEn2Id failed with the id " + id + ": " + e.getMessage());
		}
	}
	
	/**
	 * Return all persistent instances of the <code>CvEserviceN3</code> entity.
	 */
	public List<CvEserviceN3> findAllCvEserviceN3s() throws Exception {
		try {
			return getDao().findAllCvEserviceN3s();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvEserviceN3s failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvEserviceN3> findCvEserviceN3sByExample(CvEserviceN3 cvEserviceN3) throws Exception {
		try {
			return getDao().findByExample(cvEserviceN3);
		} catch (RuntimeException e) {
			throw new Exception("findCvEserviceN3sByExample failed: " + e.getMessage());
		}
	}
	
	/**
	 * Return motives by area
	 */
	public List<String> getListaMotivos(Long idArea) throws Exception{
		try{
			return getDao().getListaMotivos(idArea);
		}catch(Exception e){
			throw new Exception("getListaMotivos failed with the area " + idArea + ": " + e.getMessage());
		}
	}
	
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvEserviceN3(CvEserviceN3 cvEserviceN3) throws Exception {
		try {
			getDao().persistCvEserviceN3(cvEserviceN3);
		} catch (RuntimeException e) {
			throw new Exception("persistCvEserviceN3 failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvEserviceN3(CvEserviceN3 cvEserviceN3) throws Exception {
		try {
			getDao().removeCvEserviceN3(cvEserviceN3);
		} catch (RuntimeException e) {
			throw new Exception("removeCvEserviceN3 failed: " + e.getMessage());
		}
	}

	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(ICombosDao dao) {
		this.dao = dao;
	}
	public ICombosDao getDao() {
		return this.dao;
	}
	/**
	 * Find an entity by its id (primary key).
	 * @return  The found entity instance or null if the entity does not exist.
	 */
	public CvBanco findCvBancoById(Long id) throws Exception {
		try {
			return getDao().findCvBancoById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvBancoById failed with the id " + id
					+ ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvBanco</code> entity.
	 */
	public List<CvBanco> findAllCvBancos() throws Exception {
		try {
			return getDao().findAllCvBancos();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvBancos failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvBanco> findCvBancosByExample(CvBanco cvBanco)
			throws Exception {
		try {
			return getDao().findByExample(cvBanco);
		} catch (RuntimeException e) {
			throw new Exception("findCvBancosByExample failed: "
					+ e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvBanco(CvBanco cvBanco) throws Exception {
		try {
			getDao().persistCvBanco(cvBanco);
		} catch (RuntimeException e) {
			throw new Exception("persistCvBanco failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvBanco(CvBanco cvBanco) throws Exception {
		try {
			getDao().removeCvBanco(cvBanco);
		} catch (RuntimeException e) {
			throw new Exception("removeCvBanco failed: " + e.getMessage());
		}
	}
	/**
	 * Find an entity by its id (primary key).
	 * @return  The found entity instance or null if the entity does not exist.
	 */
	public CvTipoTarjeta findCvTipoTarjetaById(Long id) throws Exception {
		try {
			return getDao().findCvTipoTarjetaById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvTipoTarjetaById failed with the id "
					+ id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvTipoTarjeta</code> entity.
	 */
	public List<CvTipoTarjeta> findAllCvTipoTarjetas() throws Exception {
		try {
			return getDao().findAllCvTipoTarjetas();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvTipoTarjetas failed: "
					+ e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvTipoTarjeta> findCvTipoTarjetasByExample(
			CvTipoTarjeta cvTipoTarjeta) throws Exception {
		try {
			return getDao().findByExample(cvTipoTarjeta);
		} catch (RuntimeException e) {
			throw new Exception("findCvTipoTarjetasByExample failed: "
					+ e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTipoTarjeta(CvTipoTarjeta cvTipoTarjeta)
			throws Exception {
		try {
			getDao().persistCvTipoTarjeta(cvTipoTarjeta);
		} catch (RuntimeException e) {
			throw new Exception("persistCvTipoTarjeta failed: "
					+ e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTipoTarjeta(CvTipoTarjeta cvTipoTarjeta)
			throws Exception {
		try {
			getDao().removeCvTipoTarjeta(cvTipoTarjeta);
		} catch (RuntimeException e) {
			throw new Exception("removeCvTipoTarjeta failed: " + e.getMessage());
		}
	}
}