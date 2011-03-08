package com.cablevision.dao;

import java.util.List;

import com.cablevision.vo.Email;



/**
 * The DAO interface for the Email entity.
 */
public interface IEmailDao {
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
	 * Return all persistent instances of the <code>Email</code> entity.
	 */
	public List<Email> findAllEmails();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistEmail(Email email);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeEmail(Email email);
	/**
	 * Regresa el numero de emails en la base
	 */
	public Integer findByExampleCount(Email example);
	/**
	 * Find an entity by its id (primary key).
	 * @return  The found entity instance or null if the entity does not exist.
	 */
	public Email findEmailById(String id);
}