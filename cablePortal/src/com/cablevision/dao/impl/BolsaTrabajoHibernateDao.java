package com.cablevision.dao.impl; 

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.dao.IBolsaTrabajoDao;
import com.cablevision.vo.CvCurriculum;
import com.cablevision.vo.CvUser;

/**
 * The DAO class for the entities: CvCurriculum, CvProspectus, CvUser, CvVacancy, CvVacancyItem.
 */
@SuppressWarnings("unchecked")
public class BolsaTrabajoHibernateDao extends HibernateDaoSupport implements IBolsaTrabajoDao {
	
	protected final Log log = LogFactory.getLog(BolsaTrabajoHibernateDao.class);
	
	public BolsaTrabajoHibernateDao() {
		super();
	}
	
	public CvUser findUserById(String id){
		List<CvUser> users = getHibernateTemplate().find("from CvUser where email=?", id);
		if(users !=null && users.size()>0){
			for(CvUser u : users){
				return u;
			}
		}
		return null;
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
	public CvCurriculum findCvCurriculumById(String id) {
		//return (CvCurriculum)getHibernateTemplate().load(CvCurriculum.class, id);

		List<CvCurriculum> curriculums = getHibernateTemplate().find("from CvCurriculum where email=?", id);
		if(curriculums!=null && curriculums.size()>0){
			for(CvCurriculum c : curriculums){
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Return all persistent instances of the <code>CvCurriculum</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvCurriculum> findAllCvCurriculums() {
	 	return getHibernateTemplate().loadAll(CvCurriculum.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvCurriculum(CvCurriculum cvCurriculum) {
		if(this.findCvCurriculumById(cvCurriculum.getEmail()) == null){
			getHibernateTemplate().save(cvCurriculum);
		}else{
			getHibernateTemplate().merge(cvCurriculum);
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvCurriculum(CvCurriculum cvCurriculum) {
		getHibernateTemplate().delete(cvCurriculum);
	}


	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvUser findCvUserById(String id) {
		return (CvUser)getHibernateTemplate().load(CvUser.class, id);
	}
	/**
	 * Return all persistent instances of the <code>CvUser</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvUser> findAllCvUsers() {
		try {
			log.info("entra en este daooo");
			return getHibernateTemplate().find("from CvUser");
			
			/*String queryString = "from CvUser";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();*/
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	 	//return getHibernateTemplate().loadAll(CvUser.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvUser(CvUser cvUser) {
		getHibernateTemplate().saveOrUpdate(cvUser);
	}
	
	public String getCvUserNextSeqValue(){
		String sequence = "";
		try{
			sequence = getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery("SELECT CV_USERS_SEQ.NEXTVAL FROM DUAL").uniqueResult();
			}
		}).toString();
			log.info("getCvUserNextSeqValue successful with next val " + sequence);
		} catch(RuntimeException re){
			re.printStackTrace();
			log.error("getCvUserNextSeqValue failed", re);
			throw re;
		}
		return sequence;
	}	
	
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvUser(CvUser cvUser) {
		getHibernateTemplate().delete(cvUser);
	}
	
}