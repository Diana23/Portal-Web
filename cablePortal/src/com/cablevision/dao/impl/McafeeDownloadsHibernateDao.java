package com.cablevision.dao.impl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.vo.CvMcafeeDownload;
import com.cablevision.vo.CvMcafeeReset;
import com.cablevision.vo.CvMcafeeUser;
import com.cablevision.vo.CvMcafeesuscribed;
import com.cablevision.dao.IMcafeeDownloadsDao;

/**
 * The DAO class for the entities: CvMcafeeDownload, CvMcafeeReset,
 * CvMcafeeUser.
 */
public class McafeeDownloadsHibernateDao extends HibernateDaoSupport implements
		IMcafeeDownloadsDao {
	public McafeeDownloadsHibernateDao() {
		super();
	}

	/**
	 * Return the persistent entities returned from a named query.
	 */
	public List findByNamedQuery(String queryName) {
		return getHibernateTemplate().findByNamedQuery(queryName);
	}
	@Override
	public List getResumenDownloads(){
		try{
			return (List)getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					return session.createSQLQuery(
							"select count(*),  to_date(to_char(mte_suscribedate,'mm/yyyy'),'mm/yyyy') from CV_MCAFEESUSCRIBED " +
							"group by to_date(to_char(mte_suscribedate,'mm/yyyy'),'mm/yyyy') "+ 
					"order by to_date(to_char(mte_suscribedate,'mm/yyyy'),'mm/yyyy') asc ").list();
				}
			});
		} catch(RuntimeException re){
			re.printStackTrace();
			throw re;
		}
	}
	@Override
	public List getResumenPorFecha(final Date fechaInicio, final Date fechaFinal, final String status){
		try{
			return (List)getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Calendar finalCalendar = Calendar.getInstance();
					if(fechaFinal==null){
						finalCalendar.setTime(fechaInicio);
					}else{
						finalCalendar.setTime(fechaFinal);
					}
					finalCalendar.add(Calendar.DATE, 1);
					Query query = session.createSQLQuery(
							"select count(*) from (select U.MUS_ACCOUNTID from cv_mcafeesuscribed d, cv_mcafeeusers u "+
							"where d.mte_iduser = u.mus_id and TRIM(u.mus_cvstatus)=:STATUS "+
							"AND D.MTE_SUSCRIBEDATE < :FECHAINICIO  "+
							"GROUP BY(U.MUS_ACCOUNTID)) "+
							"union all "+
							"select count(*) from (select U.MUS_ACCOUNTID from cv_mcafeesuscribed d, cv_mcafeeusers u "+
							"where d.mte_iduser = u.mus_id and TRIM(u.mus_cvstatus)=:STATUS "+
							"AND D.MTE_SUSCRIBEDATE >= :FECHAINICIO AND D.MTE_SUSCRIBEDATE<:FECHAFINAL "+
					"GROUP BY(U.MUS_ACCOUNTID))");
					
					query.setParameter("STATUS", status);
					query.setParameter("FECHAINICIO", fechaInicio);
					query.setParameter("FECHAFINAL", finalCalendar.getTime());
					return query.list();
				}
			});
		} catch(RuntimeException re){
			re.printStackTrace();
			throw re;
		}
	}
	@Override
	public List getOrigen(final Date fechaInicio, final Date fechaFinal, final String status){
		try{
			return (List)getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Calendar finalCalendar = Calendar.getInstance();
					if(fechaFinal==null){
						finalCalendar.setTime(fechaInicio);
					}else{
						finalCalendar.setTime(fechaFinal);
					}
					finalCalendar.add(Calendar.DATE, 1);
					Query query = session.createSQLQuery(
							"select COUNT(*) , s.MTE_SOURCE from cv_mcafeesuscribed s, cv_mcafeeusers u "+
							" where s.mte_iduser = u.mus_id and TRIM(u.mus_cvstatus) = :STATUS AND "+
					" s.mte_suscribedate >= :FECHAINICIO AND s.MTE_SUSCRIBEDATE < :FECHAFINAL group by s.MTE_SOURCE");
					
					query.setParameter("STATUS", status);
					query.setParameter("FECHAINICIO", fechaInicio);
					query.setParameter("FECHAFINAL", finalCalendar.getTime());
					return query.list();
				}
			});
		} catch(RuntimeException re){
			re.printStackTrace();
			throw re;
		}
	}
	@Override
	public CvMcafeesuscribed getSuscribdByAccount(Integer account){
		List list = getHibernateTemplate().find("from CvMcafeesuscribed where mteAccount=? ",new Long(account));
		
		if(list.size()>0){
			return (CvMcafeesuscribed)list.get(0);
		}else{
			return null;
		}
	}
	
	public List<CvMcafeeDownload> getDownloadsByUser(Integer idUser){
		return getHibernateTemplate().find("from CvMcafeeDownload where mdlIduser=?",idUser);
	}
	@Override
	public List<CvMcafeesuscribed> getSuscripciones(final Date fechaInicio, final Date fechaFinal, final String status,int inicio,int maxResults){
		Calendar finalCalendar = Calendar.getInstance();
		if(fechaFinal==null){
			finalCalendar.setTime(fechaInicio);
		}else{
			finalCalendar.setTime(fechaFinal);
		}
		finalCalendar.add(Calendar.DATE, 1);
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CvMcafeesuscribed.class);
		
		criteria.add(Property.forName("mteSuscribedate").ge(fechaInicio));
		criteria.add(Property.forName("mteSuscribedate").lt(finalCalendar.getTime()));
		criteria.add(Property.forName("mteStatus").eq(status));
		
		
		return getHibernateTemplate().findByCriteria(criteria,inicio,maxResults);
		
	}

	/**
	 * Return the persistent entities returned from a named query with named
	 * parameters.
	 */
	public List findByNamedQuery(String queryName, String[] paramNames,
			Object[] paramValues) {
		if (paramNames.length != paramValues.length) {
			throw new IllegalArgumentException();
		}
		return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName,
				paramNames, paramValues);
	}

	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List findByExample(Object example) {
		return getHibernateTemplate().findByExample(example);
	}

	/**
	 * Find an entity by its id (primary key).
	 * 
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvMcafeeDownload findCvMcafeeDownloadById(Long id) {
		return (CvMcafeeDownload) getHibernateTemplate().load(
				CvMcafeeDownload.class, id);
	}

	/**
	 * Return all persistent instances of the <code>CvMcafeeDownload</code>
	 * entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafeeDownload> findAllCvMcafeeDownloads() {
		return getHibernateTemplate().loadAll(CvMcafeeDownload.class);
	}

	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeDownload(CvMcafeeDownload cvMcafeeDownload) {
		getHibernateTemplate().saveOrUpdate(cvMcafeeDownload);
	}

	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeDownload(CvMcafeeDownload cvMcafeeDownload) {
		getHibernateTemplate().delete(cvMcafeeDownload);
	}

	/**
	 * Find an entity by its id (primary key).
	 * 
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvMcafeeReset findCvMcafeeResetById(Long id) {
		return (CvMcafeeReset) getHibernateTemplate().load(CvMcafeeReset.class,
				id);
	}

	/**
	 * Return all persistent instances of the <code>CvMcafeeReset</code>
	 * entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafeeReset> findAllCvMcafeeResets() {
		return getHibernateTemplate().loadAll(CvMcafeeReset.class);
	}

	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeReset(CvMcafeeReset cvMcafeeReset) {
		getHibernateTemplate().saveOrUpdate(cvMcafeeReset);
	}

	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeReset(CvMcafeeReset cvMcafeeReset) {
		getHibernateTemplate().delete(cvMcafeeReset);
	}

	/**
	 * Return all persistent instances of the <code>CvMcafeeUser</code>
	 * entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafeeUser> findAllCvMcafeeUsers() {
		return getHibernateTemplate().loadAll(CvMcafeeUser.class);
	}

	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeUser(CvMcafeeUser cvMcafeeuser) {
		getHibernateTemplate().saveOrUpdate(cvMcafeeuser);
	}
	@Override
	public void persistCvMcafeesuscribed(CvMcafeesuscribed suscribed) {
		getHibernateTemplate().saveOrUpdate(suscribed);
	}
	@Override
	public void aumentarDownload(final String account){
		try{
			getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createSQLQuery(
							"update cv_mcafeesuscribed "+
							" set mte_attemps=mte_attemps+1 where mte_account=:ACCOUNT");
					
					query.setParameter("ACCOUNT", account);
					return query.executeUpdate();
				}
			});
		} catch(RuntimeException re){
			re.printStackTrace();
			throw re;
		}
		
		
	}

	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeUser(CvMcafeeUser cvMcafeeuser) {
		getHibernateTemplate().delete(cvMcafeeuser);
	}

	@Override
	public CvMcafeeUser getMcafeeUserByAccount(Long account) {
		String queryString = "from CvMcafeeUser where musAccount=?";
		List<CvMcafeeUser> mcafeeUsers = getHibernateTemplate().find(
				queryString, account);
		if (mcafeeUsers.size() > 0)
			return mcafeeUsers.get(0);
		return null;
	}

	@Override
	public List<CvMcafeeDownload> getMcafeeDownloadsByUserAccount(Long account) {
		String queryString = "from CvMcafeeDownload where mdlIduser=?";
		List<CvMcafeeDownload> mcafeeDownloads = getHibernateTemplate().find(
				queryString, account);
		return mcafeeDownloads;
	}

	@Override
	public CvMcafeeReset getMcafeeReset(Long account) {
		String queryString = "from CvMcafeeReset where mreAccount=?";
		List<CvMcafeeReset> mcafeeReset = getHibernateTemplate().find(
				queryString, account);
		if (mcafeeReset.size() > 0)
			return mcafeeReset.get(0);
		return null;
	}
}