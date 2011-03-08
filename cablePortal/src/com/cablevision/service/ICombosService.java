package com.cablevision.service;

import java.util.List;

import com.cablevision.vo.CvEserviceN1;
import com.cablevision.vo.CvEserviceN2;
import com.cablevision.vo.CvEserviceN3;
import com.cablevision.vo.CvBanco;
import com.cablevision.vo.CvTipoTarjeta;

/**
 * The service interface for the entities: CvEserviceN1, CvEserviceN2, CvEserviceN3.
 */
public interface ICombosService {

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN1 findCvEserviceN1ById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvEserviceN1</code> entity.
	 */
	public List<CvEserviceN1> findAllCvEserviceN1s() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvEserviceN1> findCvEserviceN1sByExample(CvEserviceN1 cvEserviceN1) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvEserviceN1(CvEserviceN1 cvEserviceN1) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvEserviceN1(CvEserviceN1 cvEserviceN1) throws Exception;

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN2 findCvEserviceN2ById(Long id) throws Exception;
	
	/**
	 * Find an entity by its name
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN2 findCvEserviceN2IdByName(String name) throws Exception;
	
	/**
	 * Find a list entity by its En1Id
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvEserviceN2> findCvEserviceN2ByEn1Id(Long id) throws Exception;
	
	/**
	 * Return all persistent instances of the <code>CvEserviceN2</code> entity.
	 */
	public List<CvEserviceN2> findAllCvEserviceN2s() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvEserviceN2> findCvEserviceN2sByExample(CvEserviceN2 cvEserviceN2) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvEserviceN2(CvEserviceN2 cvEserviceN2) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvEserviceN2(CvEserviceN2 cvEserviceN2) throws Exception;

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN3 findCvEserviceN3ById(Long id) throws Exception;
	
	/**
	 * Find a list entity by its En2Id
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvEserviceN3> findCvEserviceN3ByEn2Id(Long id) throws Exception;
	
	/**
	 * Return all persistent instances of the <code>CvEserviceN3</code> entity.
	 */
	public List<CvEserviceN3> findAllCvEserviceN3s() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvEserviceN3> findCvEserviceN3sByExample(CvEserviceN3 cvEserviceN3) throws Exception;
	/**
	 * Return motives by area
	 */
	public List<String> getListaMotivos(Long idArea) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvEserviceN3(CvEserviceN3 cvEserviceN3) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvEserviceN3(CvEserviceN3 cvEserviceN3) throws Exception;
	/**
	 * Find an entity by its id (primary key).
	 * @return  The found entity instance or null if the entity does not exist.
	 */
	public CvBanco findCvBancoById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvBanco</code> entity.
	 */
	public List<CvBanco> findAllCvBancos() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvBanco> findCvBancosByExample(CvBanco cvBanco)
			throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvBanco(CvBanco cvBanco) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvBanco(CvBanco cvBanco) throws Exception;
	/**
	 * Find an entity by its id (primary key).
	 * @return  The found entity instance or null if the entity does not exist.
	 */
	public CvTipoTarjeta findCvTipoTarjetaById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvTipoTarjeta</code> entity.
	 */
	public List<CvTipoTarjeta> findAllCvTipoTarjetas() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvTipoTarjeta> findCvTipoTarjetasByExample(
			CvTipoTarjeta cvTipoTarjeta) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTipoTarjeta(CvTipoTarjeta cvTipoTarjeta)
			throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTipoTarjeta(CvTipoTarjeta cvTipoTarjeta)
			throws Exception;
}