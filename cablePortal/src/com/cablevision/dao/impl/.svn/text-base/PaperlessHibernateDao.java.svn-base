package com.cablevision.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.vo.CvPaperless;
import com.cablevision.vo.CvPaperlessHistorial;
import com.cablevision.dao.IPaperlessDao;

/**
 * The DAO class for the entities: CvPaperless, CvPaperlessHistorial.
 */
public class PaperlessHibernateDao extends HibernateDaoSupport implements IPaperlessDao {
	protected final Log log = LogFactory.getLog(PaperlessHibernateDao.class);

	public PaperlessHibernateDao() {
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
	public CvPaperless findCvPaperlessById(String id) {
		try {
		return (CvPaperless)getHibernateTemplate().load(CvPaperless.class, id);
		} catch(Exception e) {
			return null;
		}
//		List<CvPaperless> paperless = (List<CvPaperless>) getHibernateTemplate().find("from CvPaperless where pplContrato=?", id);
//		if(paperless !=null && paperless.size()>0){
//			for(CvPaperless pl : paperless){
//				return pl;
//			}
//		}
//		return null;
		
	}
	/**
	 * Return all persistent instances of the <code>CvPaperless</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvPaperless> findAllCvPaperlesses() {
	 	return getHibernateTemplate().loadAll(CvPaperless.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvPaperless(CvPaperless cvPaperless) {
		getHibernateTemplate().saveOrUpdate(cvPaperless);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvPaperless(CvPaperless cvPaperless) {
		getHibernateTemplate().delete(cvPaperless);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvPaperlessHistorial findCvPaperlessHistorialById(Long id) {
		return (CvPaperlessHistorial)getHibernateTemplate().load(CvPaperlessHistorial.class, id);
	}
	/**
	 * Return all persistent instances of the <code>CvPaperlessHistorial</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvPaperlessHistorial> findAllCvPaperlessHistorials() {
	 	return getHibernateTemplate().loadAll(CvPaperlessHistorial.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvPaperlessHistorial(CvPaperlessHistorial cvPaperlessHistorial) {
		getHibernateTemplate().saveOrUpdate(cvPaperlessHistorial);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvPaperlessHistorial(CvPaperlessHistorial cvPaperlessHistorial) {
		getHibernateTemplate().delete(cvPaperlessHistorial);
	}
	
	public String getCvPaperlessHistoryNextSeqValue(){
		String sequence = "";
		try{
			sequence = getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery("SELECT CV_PAPERLES_HISTORY_SEQ.NEXTVAL FROM DUAL").uniqueResult();
			}
		}).toString();
			log.info("getCvPaperlessHistoryNextSeqValue successful with next val " + sequence);
		} catch(RuntimeException re){
			re.printStackTrace();
			log.error("getCvUserNextSeqValue failed", re);
			throw re;
		}
		return sequence;
	}
	
}