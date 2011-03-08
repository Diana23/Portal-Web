package com.cablevision.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.vo.CvEserviceN1;
import com.cablevision.vo.CvEserviceN2;
import com.cablevision.vo.CvEserviceN3;
import com.cablevision.dao.ICombosDao;
import com.cablevision.vo.CvBanco;
import com.cablevision.vo.CvTipoTarjeta;

/**
 * The DAO class for the entities: CvEserviceN1, CvEserviceN2, CvEserviceN3.
 */
public class CombosHibernateDao extends HibernateDaoSupport implements ICombosDao {
	public CombosHibernateDao() {
		super();
	}
	/**
	 * Return the persistent entities returned from a named query.
	 */
	public List findByNamedQuery(String queryName) {
		return getHibernateTemplate().findByNamedQuery(queryName);
	}
	/**
	 * Return the persistent entities returned from a named query with named parameters.
	 */
	public List findByNamedQuery(String queryName, String[] paramNames, Object[] paramValues) {
		if (paramNames.length != paramValues.length) {
			throw new IllegalArgumentException();
		}
		return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, paramValues);
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List findByExample(Object example) {
		return getHibernateTemplate().findByExample(example);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN1 findCvEserviceN1ById(Long id) {
		return (CvEserviceN1)getHibernateTemplate().load(CvEserviceN1.class, id);
	}
	/**
	 * Return all persistent instances of the <code>CvEserviceN1</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvEserviceN1> findAllCvEserviceN1s() {
	 	return getHibernateTemplate().loadAll(CvEserviceN1.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvEserviceN1(CvEserviceN1 cvEserviceN1) {
		getHibernateTemplate().saveOrUpdate(cvEserviceN1);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvEserviceN1(CvEserviceN1 cvEserviceN1) {
		getHibernateTemplate().delete(cvEserviceN1);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN2 findCvEserviceN2ById(Long id) {
		return (CvEserviceN2)getHibernateTemplate().load(CvEserviceN2.class, id);
	}
	
	/**
	 * Find an entity by its name
	 * @return The found entity instance or null if the entity does not exist.
	 */
	@SuppressWarnings("unchecked")
	public CvEserviceN2 findCvEserviceN2IdByName(String name) {
		List<CvEserviceN2> eServices = getHibernateTemplate().find("from CvEserviceN2 where en2Name=?",name); 
		if(eServices!= null && eServices.size()>0){
			for(CvEserviceN2 service : eServices ){
				return service;
			}
		}
		return null;
	}
	
	/**
	 * Find a list entity by its En1Id
	 * @return The found entity instance or null if the entity does not exist.
	 */
	@SuppressWarnings("unchecked")
	public List<CvEserviceN2> findCvEserviceN2ByEn1Id(Long id) {
		return getHibernateTemplate().find("from CvEserviceN2 where en2En1Id=?",id); 
	}
	
	/**
	 * Return all persistent instances of the <code>CvEserviceN2</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvEserviceN2> findAllCvEserviceN2s() {
	 	return getHibernateTemplate().loadAll(CvEserviceN2.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvEserviceN2(CvEserviceN2 cvEserviceN2) {
		getHibernateTemplate().saveOrUpdate(cvEserviceN2);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvEserviceN2(CvEserviceN2 cvEserviceN2) {
		getHibernateTemplate().delete(cvEserviceN2);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvEserviceN3 findCvEserviceN3ById(Long id) {
		return (CvEserviceN3)getHibernateTemplate().load(CvEserviceN3.class, id);
	}
	
	/**
	 * Find a list entity by its En2Id
	 * @return The found entity instance or null if the entity does not exist.
	 */
	@SuppressWarnings("unchecked")
	public List<CvEserviceN3> findCvEserviceN3ByEn2Id(Long id) {
		return getHibernateTemplate().find("from CvEserviceN3 where en3En2Id=?",id); 
	}
	
	/**
	 * Return all persistent instances of the <code>CvEserviceN3</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvEserviceN3> findAllCvEserviceN3s() {
	 	return getHibernateTemplate().loadAll(CvEserviceN3.class);
	}
	
	/**
	 * Return motives by area
	 */
	@SuppressWarnings("unchecked")
	public List<String> getListaMotivos(Long idArea){
		List motivos = getHibernateTemplate().find("SELECT e.en3Name FROM CvEserviceN3 e WHERE e.en3En2Id = ? ORDER BY e.en3Name", idArea);
		return motivos;
	}
	
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvEserviceN3(CvEserviceN3 cvEserviceN3) {
		getHibernateTemplate().saveOrUpdate(cvEserviceN3);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvEserviceN3(CvEserviceN3 cvEserviceN3) {
		getHibernateTemplate().delete(cvEserviceN3);
	}
	/**
	 * Find an entity by its id (primary key).
	 * @return  The found entity instance or null if the entity does not exist.
	 */
	public CvBanco findCvBancoById(Long id) {
		return (CvBanco) getHibernateTemplate().load(CvBanco.class, id);
	}
	/**
	 * Return all persistent instances of the <code>CvBanco</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvBanco> findAllCvBancos() {
		return getHibernateTemplate().loadAll(CvBanco.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvBanco(CvBanco cvBanco) {
		getHibernateTemplate().saveOrUpdate(cvBanco);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvBanco(CvBanco cvBanco) {
		getHibernateTemplate().delete(cvBanco);
	}
	/**
	 * Find an entity by its id (primary key).
	 * @return  The found entity instance or null if the entity does not exist.
	 */
	public CvTipoTarjeta findCvTipoTarjetaById(Long id) {
		return (CvTipoTarjeta) getHibernateTemplate().load(CvTipoTarjeta.class,
				id);
	}
	/**
	 * Return all persistent instances of the <code>CvTipoTarjeta</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvTipoTarjeta> findAllCvTipoTarjetas() {
		return getHibernateTemplate().loadAll(CvTipoTarjeta.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTipoTarjeta(CvTipoTarjeta cvTipoTarjeta) {
		getHibernateTemplate().saveOrUpdate(cvTipoTarjeta);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTipoTarjeta(CvTipoTarjeta cvTipoTarjeta) {
		getHibernateTemplate().delete(cvTipoTarjeta);
	}
}