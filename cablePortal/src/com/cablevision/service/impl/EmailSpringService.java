package com.cablevision.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import com.cablevision.vo.Email;
import com.cablevision.service.IEmailService;
import com.cablevision.dao.IEmailDao;

/**
 * The service class for the Email entity.
 */
public class EmailSpringService implements IEmailService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IEmailDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "EmailService";
	
	public EmailSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>IEmailService</code> instance.
	 */
	public static IEmailService getInstance(ApplicationContext context) {
		return (IEmailService)context.getBean(SERVICE_BEAN_ID);
	}
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Email findEmailById(String id) throws Exception {
		try {
			return getDao().findEmailById(id);
		} catch (RuntimeException e) {
			throw new Exception("findEmailById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>Email</code> entity.
	 */
	public List<Email> findAllEmails() throws Exception {
		try {
			return getDao().findAllEmails();
		} catch (RuntimeException e) {
			throw new Exception("findAllEmails failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<Email> findEmailsByExample(Email email) throws Exception {
		try {
			return getDao().findByExample(email);
		} catch (RuntimeException e) {
			throw new Exception("findEmailsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistEmail(Email email) throws Exception {
		try {
			getDao().persistEmail(email);
		} catch (RuntimeException e) {
			throw new Exception("persistEmail failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeEmail(Email email) throws Exception {
		try {
			getDao().removeEmail(email);
		} catch (RuntimeException e) {
			throw new Exception("removeEmail failed: " + e.getMessage());
		}
	}

	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public Integer findEmailsByExampleCount(Email email) throws Exception {
		try {
			return getDao().findByExampleCount(email);
		} catch (RuntimeException e) {
			throw new Exception("findEmailsByExampleCount failed: " + e.getMessage());
		}
	}
	
	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(IEmailDao dao) {
		this.dao = dao;
	}
	public IEmailDao getDao() {
		return this.dao;
	}
}