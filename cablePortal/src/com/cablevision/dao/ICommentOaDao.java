package com.cablevision.dao;

import java.util.List;

import com.cablevision.vo.CvCommentoa;
import com.cablevision.vo.CvTrackoa;

/**
 * The DAO interface for the entities: CvCommentoa, CvTrackoa.
 */
public interface ICommentOaDao {
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
	public CvCommentoa findCvCommentoaById(Long id);
	/**
	 * Return all persistent instances of the <code>CvCommentoa</code> entity.
	 */
	public List<CvCommentoa> findAllCvCommentoas();
	
	/**
	 * Find entity list  by its accountId.
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvCommentoa> findCvCommentoaByNoSolicitud(String noSolicitud);
	
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvCommentoa(CvCommentoa cvCommentoa);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvCommentoa(CvCommentoa cvCommentoa);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTrackoa findCvTrackoaById(Long id);
	
	/**
	 * Find an entity by its accountId.
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvTrackoa> findCvTrackoaByAccountId(String accountId);
	
	/**
	 * Return all persistent instances of the <code>CvTrackoa</code> entity.
	 */
	public List<CvTrackoa> findAllCvTrackoas();
	/**
	 * Find an entity by its accountId.
	 */
	public CvTrackoa findCvTrackoaByNumberOa(String numberOa);
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTrackoa(CvTrackoa cvTrackoa);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTrackoa(CvTrackoa cvTrackoa);
}