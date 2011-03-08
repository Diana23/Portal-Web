package com.cablevision.dao;

import java.util.List;

import com.cablevision.vo.CvContrasenaHistorial;
import com.cablevision.vo.CvUsuarioPortal;

/**
 * The DAO interface for the entities: CvContrasenaHistorial, CvUsuarioPortal.
 */
public interface IUsuarioPortalDao {
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
	public CvContrasenaHistorial findCvContrasenaHistorialById(Long id);
	/**
	 * Return all persistent instances of the <code>CvContrasenaHistorial</code> entity.
	 */
	public List<CvContrasenaHistorial> findAllCvContrasenaHistorials();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvContrasenaHistorial(CvContrasenaHistorial cvContrasenaHistorial);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvContrasenaHistorial(CvContrasenaHistorial cvContrasenaHistorial);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvUsuarioPortal findCvUsuarioPortalById(String id);
	/**
	 * Return all persistent instances of the <code>CvUsuarioPortal</code> entity.
	 */
	public List<CvUsuarioPortal> findAllCvUsuarioPortals();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvUsuarioPortal(CvUsuarioPortal cvUsuarioPortal);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvUsuarioPortal(CvUsuarioPortal cvUsuarioPortal);
	
	public boolean updateCvUsuarioPortal(String idUsuario, String foto);
	
	/**
	 * Return all persistent instances of the <code>CvContrasenaHistorial</code> entity by user id.
	 * @param idUser
	 * @return
	 */
	public List<CvContrasenaHistorial> findCvContrasenaHistorialByIdUser(String idUser);
	
	public void removeSessionIds();
}