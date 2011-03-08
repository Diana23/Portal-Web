package com.cablevision.dao;

import java.util.List;

import com.cablevision.vo.Answer;

/**
 * The DAO interface for the Answer entity.
 */
public interface IAnswerDao {
	
	
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
	public Answer findAnswerById(Long id);
	/**
	 * Return all persistent instances of the <code>Answer</code> entity.
	 */
	public List<Answer> findAllAnswers();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistAnswer(Answer answer);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeAnswer(Answer answer);
}