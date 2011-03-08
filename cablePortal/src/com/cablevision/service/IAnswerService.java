package com.cablevision.service;

import java.util.List;

import com.cablevision.vo.Answer;

/**
 * The service interface for the Answer entity.
 */
public interface IAnswerService {
	
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Answer findAnswerById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>Answer</code> entity.
	 */
	public List<Answer> findAllAnswers() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<Answer> findAnswersByExample(Answer answer) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistAnswer(Answer answer) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeAnswer(Answer answer) throws Exception;
}