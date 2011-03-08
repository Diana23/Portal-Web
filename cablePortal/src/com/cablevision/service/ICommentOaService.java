package com.cablevision.service;

import java.util.List;

import com.cablevision.vo.CvCommentoa;
import com.cablevision.vo.CvTrackoa;

/**
 * The service interface for the entities: CvCommentoa, CvTrackoa.
 */
public interface ICommentOaService {

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvCommentoa findCvCommentoaById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvCommentoa</code> entity.
	 */
	public List<CvCommentoa> findAllCvCommentoas() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvCommentoa> findCvCommentoasByExample(CvCommentoa cvCommentoa) throws Exception;
	/**
	 * Find entity list  by its accountId.
	 */
	public List<CvCommentoa> findCvCommentoaByNoSolicitud(String noSolicitud) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvCommentoa(CvCommentoa cvCommentoa) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvCommentoa(CvCommentoa cvCommentoa) throws Exception;

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTrackoa findCvTrackoaById(Long id) throws Exception;
	
	/**
	 * Find an entity by its accountId.
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvTrackoa> findCvTrackoaByAccountId(String accountId) throws Exception;
	/**
	 * Find an entity by its accountId.
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTrackoa findCvTrackoaByNumberOa(String numberOa) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvTrackoa</code> entity.
	 */
	public List<CvTrackoa> findAllCvTrackoas() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvTrackoa> findCvTrackoasByExample(CvTrackoa cvTrackoa) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTrackoa(CvTrackoa cvTrackoa) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTrackoa(CvTrackoa cvTrackoa) throws Exception;
}