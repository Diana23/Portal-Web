package com.cablevision.service;

import java.util.Date;
import java.util.List;

import com.cablevision.vo.CvProgramRecord;
import com.cablevision.vo.CvScheduleRecord;
import com.cablevision.vo.CvStationRecord;
import com.cablevision.vo.CvTimeZoneRecord;
import com.cablevision.vo.CvTranslationRecord;

/**
 * The service interface for the entities: CvProgramRecord, CvScheduleRecord, CvStationRecord, CvTimeZoneRecord, CvTranslationRecord.
 */
public interface IProgramScheduleService {

	/**
	 * Busca todos los registros de los programas entre las fechas dadas
	 */
	public List<CvScheduleRecord> findAllCvScheduleRecordsBetween(Date start, String cnlCategory) throws Exception;
	
	/**
	 * Busca los programas por categoria
	 * @param now
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public List<CvScheduleRecord> findAllCvScheduleRecordsByCategory(Date now,String category,String cnlCategory) throws Exception;
	
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvProgramRecord findCvProgramRecordById(String id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvProgramRecord</code> entity.
	 */
	public List<CvProgramRecord> findAllCvProgramRecords() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvProgramRecord> findCvProgramRecordsByExample(CvProgramRecord cvProgramRecord) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvProgramRecord(CvProgramRecord cvProgramRecord) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvProgramRecord(CvProgramRecord cvProgramRecord) throws Exception;
	
	/**
	 * Recorre una coleccion de objetos guardandolos uno a uno
	 * @throws Exception 
	 */
	public void saveAllProgramRecords(List<CvProgramRecord> collection) throws Exception;

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvScheduleRecord findCvScheduleRecordById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvScheduleRecord</code> entity.
	 */
	public List<CvScheduleRecord> findAllCvScheduleRecords() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvScheduleRecord> findCvScheduleRecordsByExample(CvScheduleRecord cvScheduleRecord) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvScheduleRecord(CvScheduleRecord cvScheduleRecord) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvScheduleRecord(CvScheduleRecord cvScheduleRecord) throws Exception;

	/**
	 * Recorre una coleccion de objetos guardandolos uno a uno
	 */
	public void saveAllScheduleRecords(List<CvScheduleRecord> collection);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvStationRecord findCvStationRecordById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvStationRecord</code> entity.
	 */
	public List<CvStationRecord> findAllCvStationRecords() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvStationRecord> findCvStationRecordsByExample(CvStationRecord cvStationRecord) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvStationRecord(CvStationRecord cvStationRecord) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvStationRecord(CvStationRecord cvStationRecord) throws Exception;
	
	/**
	 * Recorre una coleccion de objetos guardandolos uno a uno
	 */
	public void saveAllStationRecords(List<CvStationRecord> collection);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTimeZoneRecord findCvTimeZoneRecordById(String id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvTimeZoneRecord</code> entity.
	 */
	public List<CvTimeZoneRecord> findAllCvTimeZoneRecords() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvTimeZoneRecord> findCvTimeZoneRecordsByExample(CvTimeZoneRecord cvTimeZoneRecord) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTimeZoneRecord(CvTimeZoneRecord cvTimeZoneRecord) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTimeZoneRecord(CvTimeZoneRecord cvTimeZoneRecord) throws Exception;
	
	/**
	 * Recorre una coleccion de objetos guardandolos uno a uno
	 */
	public void saveAllTimeZoneRecords(List<CvTimeZoneRecord> collection);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTranslationRecord findCvTranslationRecordById(String id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvTranslationRecord</code> entity.
	 */
	public List<CvTranslationRecord> findAllCvTranslationRecords() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvTranslationRecord> findCvTranslationRecordsByExample(CvTranslationRecord cvTranslationRecord) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTranslationRecord(CvTranslationRecord cvTranslationRecord) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTranslationRecord(CvTranslationRecord cvTranslationRecord) throws Exception;
	
	/**
	 * Recorre una coleccion de objetos guardandolos uno a uno
	 */
	public void saveAllTranslationRecords(List<CvTranslationRecord> collection);
	
	/**
	 * Delete all objects of the class from database
	 */
	public int deleteAllFromObject(Class voClass);
}