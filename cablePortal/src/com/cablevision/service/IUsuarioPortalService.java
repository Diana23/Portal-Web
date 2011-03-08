package com.cablevision.service;

import java.util.List;

import com.cablevision.vo.CvContrasenaHistorial;
import com.cablevision.vo.CvUsuarioPortal;

/**
 * The service interface for the entities: CvContrasenaHistorial, CvUsuarioPortal.
 */
public interface IUsuarioPortalService {

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvContrasenaHistorial findCvContrasenaHistorialById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvContrasenaHistorial</code> entity.
	 */
	public List<CvContrasenaHistorial> findAllCvContrasenaHistorials() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvContrasenaHistorial> findCvContrasenaHistorialsByExample(CvContrasenaHistorial cvContrasenaHistorial) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvContrasenaHistorial(CvContrasenaHistorial cvContrasenaHistorial) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvContrasenaHistorial(CvContrasenaHistorial cvContrasenaHistorial) throws Exception;

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvUsuarioPortal findCvUsuarioPortalById(String id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvUsuarioPortal</code> entity.
	 */
	public List<CvUsuarioPortal> findAllCvUsuarioPortals() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvUsuarioPortal> findCvUsuarioPortalsByExample(CvUsuarioPortal cvUsuarioPortal) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvUsuarioPortal(CvUsuarioPortal cvUsuarioPortal) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvUsuarioPortal(CvUsuarioPortal cvUsuarioPortal) throws Exception;
	
	public boolean updateCvUsuarioPortal(String idUsuario, String foto);
	
	/**
	 * Return all persistent instances of the <code>CvContrasenaHistorial</code> entity by user id.
	 * @param idUser
	 * @return
	 */
	public List<CvContrasenaHistorial> findCvContrasenaHistorialByIdUser(String idUser) throws Exception;
	
	public void removeSessionIds() throws Exception;
}