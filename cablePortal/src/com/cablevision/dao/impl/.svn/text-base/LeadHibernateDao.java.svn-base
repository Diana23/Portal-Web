package com.cablevision.dao.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.dao.ILeadDao;
import com.cablevision.vo.CvLead;
import com.cablevision.vo.CvLeadField;
import com.cablevision.vo.CvLeadStatus;
import com.cablevision.vo.CvLeadType;
import com.cablevision.vo.CvLeadValuefield;

/**
 * The DAO class for the CvLead entity.
 */
public class LeadHibernateDao extends HibernateDaoSupport implements ILeadDao {
	public LeadHibernateDao() {
		super();
	}
	
	public List<CvLeadType> findAllLeadTypes(){
		DetachedCriteria criteria = DetachedCriteria.forClass(CvLeadType.class);
		criteria.add(Restrictions.eq("cltPpeHdDeType", 0L));
		criteria.addOrder(Order.asc("cltNameType"));
		return getHibernateTemplate().findByCriteria(criteria);
		//return getHibernateTemplate().loadAll(CvLeadType.class);
	}
	
	public Integer findNumberOfTicketsInDay(String email){
		DetachedCriteria criteria = DetachedCriteria.forClass(CvLead.class);
		
		
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.HOUR, -24);
		Criterion less24hous = Restrictions.gt("cleDatecreation", calendar.getTime());
		
		criteria.createAlias("leadValueFields","v")
				.add(Restrictions.eq("v.lvfValue",email))
				.add(Restrictions.eq("v.field.cfsIdfield",5l))
				.add(less24hous);

		
		List<CvLead> leads = getHibernateTemplate().findByCriteria(criteria);
		
		if(leads == null) return 0;
		return leads.size();
	}
	
	public List<CvLeadField> findLeadFields(CvLeadType leadType){
		DetachedCriteria criteria = DetachedCriteria.forClass(CvLeadField.class);
		
		criteria.add(Property.forName("id.leadType.cltIdLeadtype").eq(leadType.getCltIdLeadtype()));
		criteria.addOrder(Order.asc("clfOrden"));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public CvLeadType findLeadTypeById(CvLeadType leadType){
		return (CvLeadType)getHibernateTemplate().load(CvLeadType.class, leadType.getCltIdLeadtype());
	}
	
	public CvLeadStatus findLeadStatusById(CvLeadStatus status){
		return (CvLeadStatus)getHibernateTemplate().load(CvLeadStatus.class, status.getClsIdLeadstatus());
	}
	
	public void persistCvLeadValuefield(CvLeadValuefield value){
		getHibernateTemplate().saveOrUpdate(value);
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
	public CvLead findCvLeadById(Integer id) {
		return (CvLead)getHibernateTemplate().load(CvLead.class, id);
	}
	/**
	 * Return all persistent instances of the <code>CvLead</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvLead> findAllCvLeads() {
	 	return getHibernateTemplate().loadAll(CvLead.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvLead(CvLead cvLead) {
		getHibernateTemplate().saveOrUpdate(cvLead);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvLead(CvLead cvLead) {
		getHibernateTemplate().delete(cvLead);
	}
}