package com.cablevision.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;

import com.cablevision.vo.CvPaperless;
import com.cablevision.vo.CvPaperlessHistorial;

/**
 * The service interface for the entities: CvPaperless, CvPaperlessHistorial.
 */
public interface IPaperlessService {

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvPaperless findCvPaperlessById(String id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvPaperless</code> entity.
	 */
	public List<CvPaperless> findAllCvPaperlesses() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvPaperless> findCvPaperlessesByExample(CvPaperless cvPaperless) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvPaperless(CvPaperless cvPaperless) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvPaperless(CvPaperless cvPaperless) throws Exception;
	public String savePaperless(String contrato,  String nombre,String idUsuario,String email,Date fecha);
	public String updatePaperless(String contrato,  String nombre,String idUsuario,String email,Date fecha);
	public String removePaperless(String contrato,String nombre,Date fecha, String idUsuario);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvPaperlessHistorial findCvPaperlessHistorialById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvPaperlessHistorial</code> entity.
	 */
	public List<CvPaperlessHistorial> findAllCvPaperlessHistorials() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvPaperlessHistorial> findCvPaperlessHistorialsByExample(CvPaperlessHistorial cvPaperlessHistorial) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvPaperlessHistorial(CvPaperlessHistorial cvPaperlessHistorial) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvPaperlessHistorial(CvPaperlessHistorial cvPaperlessHistorial) throws Exception;
	
	public String getCvPaperlessHistoryNextSeqValue();

}