package com.cablevision.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.cablevision.dao.IAnswerDao;
import com.cablevision.service.IAnswerService;
import com.cablevision.vo.Answer;

/**
 * The service class for the Answer entity.
 */
public class AnswerSpringService implements IAnswerService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IAnswerDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "AnswerService";
	
	public AnswerSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>IAnswerService</code> instance.
	 */
	public static IAnswerService getInstance(ApplicationContext context) {
		return (IAnswerService)context.getBean(SERVICE_BEAN_ID);
	}
	
	
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Answer findAnswerById(Long id) throws Exception {
		try {
			return getDao().findAnswerById(id);
		} catch (RuntimeException e) {
			throw new Exception("findAnswerById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>Answer</code> entity.
	 */
	public List<Answer> findAllAnswers() throws Exception {
		try {
			return getDao().findAllAnswers();
		} catch (RuntimeException e) {
			throw new Exception("findAllAnswers failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<Answer> findAnswersByExample(Answer answer) throws Exception {
		try {
			return getDao().findByExample(answer);
		} catch (RuntimeException e) {
			throw new Exception("findAnswersByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistAnswer(Answer answer) throws Exception {
		try {
			getDao().persistAnswer(answer);
		} catch (RuntimeException e) {
			throw new Exception("persistAnswer failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeAnswer(Answer answer) throws Exception {
		try {
			getDao().removeAnswer(answer);
		} catch (RuntimeException e) {
			throw new Exception("removeAnswer failed: " + e.getMessage());
		}
	}

	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(IAnswerDao dao) {
		this.dao = dao;
	}
	public IAnswerDao getDao() {
		return this.dao;
	}
}