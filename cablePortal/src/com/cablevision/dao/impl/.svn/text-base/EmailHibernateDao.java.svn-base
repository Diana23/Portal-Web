package com.cablevision.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.dao.IEmailDao;
import com.cablevision.vo.Email;

/**
 * The DAO class for the Email entity.
 */
public class EmailHibernateDao extends HibernateDaoSupport implements IEmailDao {
	public EmailHibernateDao() {
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
	public Email findEmailById(String id) {
		return (Email)getHibernateTemplate().load(Email.class, id);
	}
	/**
	 * Return all persistent instances of the <code>Email</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<Email> findAllEmails() {
	 	return getHibernateTemplate().loadAll(Email.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistEmail(Email email) {
		getHibernateTemplate().saveOrUpdate(email);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeEmail(Email email) {
		getHibernateTemplate().delete(email);
	}
	
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public Integer findByExampleCount(Email example) {
		DetachedCriteria criteria = DetachedCriteria.forClass(example.getClass());
		
		if(example!=null && example.getEmail() != null){
			criteria.add(Property.forName("email").eq(example.getEmail()));
		}
		
		if(example!=null && example.getStatus() != null){
			criteria.add(Property.forName("status").eq(example.getStatus()));
		}
		
		criteria.setProjection(Projections.rowCount());
		
		return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
	}
}