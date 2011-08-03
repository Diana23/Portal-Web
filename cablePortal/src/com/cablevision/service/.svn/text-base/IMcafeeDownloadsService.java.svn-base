package com.cablevision.service;

import java.util.Date;
import java.util.List;

import com.cablevision.vo.CvMcafeeDownload;
import com.cablevision.vo.CvMcafeeReset;
import com.cablevision.vo.CvMcafeeUser;
import com.cablevision.vo.CvMcafeesuscribed;

/**
 * The service interface for the entities: CvMcafeeDownload, CvMcafeeReset, CvMcafeeUser.
 */
public interface IMcafeeDownloadsService {

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvMcafeeDownload findCvMcafeeDownloadById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvMcafeeDownload</code> entity.
	 */
	public List<CvMcafeeDownload> findAllCvMcafeeDownloads() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvMcafeeDownload> findCvMcafeeDownloadsByExample(CvMcafeeDownload cvMcafeeDownload) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeDownload(CvMcafeeDownload cvMcafeeDownload) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeDownload(CvMcafeeDownload cvMcafeeDownload) throws Exception;

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvMcafeeReset findCvMcafeeResetById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvMcafeeReset</code> entity.
	 */
	public List<CvMcafeeReset> findAllCvMcafeeResets() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvMcafeeReset> findCvMcafeeResetsByExample(CvMcafeeReset cvMcafeeReset) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeReset(CvMcafeeReset cvMcafeeReset) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeReset(CvMcafeeReset cvMcafeeReset) throws Exception;

	/**
	 * Return all persistent instances of the <code>CvMcafeeUser</code> entity.
	 */
	public List<CvMcafeeUser> findAllCvMcafeeUsers() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvMcafeeUser> findCvMcafeeUsersByExample(CvMcafeeUser cvMcafeeuser) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeUser(CvMcafeeUser cvMcafeeuser) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeUser(CvMcafeeUser cvMcafeeuser) throws Exception;
	
	
	public CvMcafeeUser getMcafeeUserByAccount(Long account) throws Exception;
	
	public List<CvMcafeeDownload> getMcafeeDownloadsByUserAccount(Long account) throws Exception;
	
	public CvMcafeeReset getMcafeeReset(Long account) throws Exception;
	public List getResumen() throws Exception;
	
	public List getOrigen(Date fechaInicio, Date fechaFinal, String status);
	public List getResumenPorFecha(Date fechaInicio, Date fechaFinal, String status);
	void generaReporte(Date fechaInicio, Date fechaFinal, String status,
			String fileName) throws Exception;
	CvMcafeesuscribed getSuscribdByAccount(Integer account);
	void aumentarDownload(String account);
	void persistCvMcafeesuscribed(CvMcafeesuscribed suscribed);
}