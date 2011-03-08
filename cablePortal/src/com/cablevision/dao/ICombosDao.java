package com.cablevision.dao;

import java.util.List;

import com.cablevision.vo.CvEserviceN1;
import com.cablevision.vo.CvEserviceN2;
import com.cablevision.vo.CvEserviceN3;
import com.cablevision.vo.CvBanco;
import com.cablevision.vo.CvTipoTarjeta;

/**
 * The DAO interface for the entities: CvEserviceN1, CvEserviceN2, CvEserviceN3.
 */
public interface ICombosDao {
	/**
	 * Return the persistent entities returned from a named query.
	 */
	public List findByNamedQuery(String queryName);
	/**
	 * Return the persistent entities returned from a named query with named parameters.
	 */
	public List findByNamedQuery(String queryName, String[] paramNames, Object[] paramValues);
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List findByExample(Object example);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN1 findCvEserviceN1ById(Long id);
	/**
	 * Return all persistent instances of the <code>CvEserviceN1</code> entity.
	 */
	public List<CvEserviceN1> findAllCvEserviceN1s();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvEserviceN1(CvEserviceN1 cvEserviceN1);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvEserviceN1(CvEserviceN1 cvEserviceN1);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN2 findCvEserviceN2ById(Long id);
	
	/**
	 * Find an entity by its name
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN2 findCvEserviceN2IdByName(String name);
	
	/**
	 * Find a list entity by its En1Id
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvEserviceN2> findCvEserviceN2ByEn1Id(Long id);
	
	/**
	 * Return all persistent instances of the <code>CvEserviceN2</code> entity.
	 */
	public List<CvEserviceN2> findAllCvEserviceN2s();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvEserviceN2(CvEserviceN2 cvEserviceN2);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvEserviceN2(CvEserviceN2 cvEserviceN2);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN3 findCvEserviceN3ById(Long id);
	
	/**
	 * Find a list entity by its En2Id
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvEserviceN3> findCvEserviceN3ByEn2Id(Long id);
	
	/**
	 * Return all persistent instances of the <code>CvEserviceN3</code> entity.
	 */
	public List<CvEserviceN3> findAllCvEserviceN3s();
	
	/**
	 * Return motives by area
	 */
	public List<String> getListaMotivos(Long idArea);
	
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvEserviceN3(CvEserviceN3 cvEserviceN3);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvEserviceN3(CvEserviceN3 cvEserviceN3);
	/**
	 * Find an entity by its id (primary key).
	 * @return  The found entity instance or null if the entity does not exist.
	 */
	public CvBanco findCvBancoById(Long id);
	/**
	 * Return all persistent instances of the <code>CvBanco</code> entity.
	 */
	public List<CvBanco> findAllCvBancos();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvBanco(CvBanco cvBanco);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvBanco(CvBanco cvBanco);
	/**
	 * Find an entity by its id (primary key).
	 * @return  The found entity instance or null if the entity does not exist.
	 */
	public CvTipoTarjeta findCvTipoTarjetaById(Long id);
	/**
	 * Return all persistent instances of the <code>CvTipoTarjeta</code> entity.
	 */
	public List<CvTipoTarjeta> findAllCvTipoTarjetas();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTipoTarjeta(CvTipoTarjeta cvTipoTarjeta);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTipoTarjeta(CvTipoTarjeta cvTipoTarjeta);
}