package com.cablevision.service;

import java.util.List;

import com.cablevision.vo.Email;

/**
 * The service interface for the Email entity.
 */
public interface IEmailService {
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Email findEmailById(String id) throws Exception;
	/**
	 * Return all persistent instances of the <code>Email</code> entity.
	 */
	public List<Email> findAllEmails() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<Email> findEmailsByExample(Email email) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistEmail(Email email) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeEmail(Email email) throws Exception;
	
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public Integer findEmailsByExampleCount(Email email) throws Exception;
}