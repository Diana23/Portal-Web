package com.cablevision.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.dao.IAnswerDao;
import com.cablevision.vo.Answer;

/**
 * The DAO class for the Answer entity.
 */
public class AnswerHibernateDao extends HibernateDaoSupport implements IAnswerDao {
	public AnswerHibernateDao() {
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
	public Answer findAnswerById(Long id) {
		return (Answer)getHibernateTemplate().load(Answer.class, id);
	}
	/**
	 * Return all persistent instances of the <code>Answer</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<Answer> findAllAnswers() {
	 	return getHibernateTemplate().loadAll(Answer.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistAnswer(Answer answer) {
		getHibernateTemplate().saveOrUpdate(answer);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeAnswer(Answer answer) {
		getHibernateTemplate().delete(answer);
	}
}