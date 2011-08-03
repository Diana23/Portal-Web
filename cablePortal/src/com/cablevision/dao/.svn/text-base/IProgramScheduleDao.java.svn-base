package com.cablevision.dao;

import java.util.Date;
import java.util.List;

import com.cablevision.vo.CvProgramRecord;
import com.cablevision.vo.CvScheduleRecord;
import com.cablevision.vo.CvStationRecord;
import com.cablevision.vo.CvTimeZoneRecord;
import com.cablevision.vo.CvTranslationRecord;

/**
 * The DAO interface for the entities: CvProgramRecord, CvScheduleRecord, CvStationRecord, CvTimeZoneRecord, CvTranslationRecord.
 */
public interface IProgramScheduleDao {
	/**
	 * Busca los programas en las fechas dadas
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List<CvScheduleRecord> findAllCvScheduleRecordsBetween(Date start,Date end, String cnlCategory) throws Exception;
	
	/**
	 * Busca los programas por categoria y fechas dadas
	 * @param start
	 * @param end
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public List<CvScheduleRecord> findAllCvScheduleRecordsByCategory(Date start,Date end,String category,String cnlCategory) throws Exception;
	
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
	public CvProgramRecord findCvProgramRecordById(String id);
	/**
	 * Return all persistent instances of the <code>CvProgramRecord</code> entity.
	 */
	public List<CvProgramRecord> findAllCvProgramRecords();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvProgramRecord(CvProgramRecord cvProgramRecord);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvProgramRecord(CvProgramRecord cvProgramRecord);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvScheduleRecord findCvScheduleRecordById(Long id);
	/**
	 * Return all persistent instances of the <code>CvScheduleRecord</code> entity.
	 */
	public List<CvScheduleRecord> findAllCvScheduleRecords();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvScheduleRecord(CvScheduleRecord cvScheduleRecord);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvScheduleRecord(CvScheduleRecord cvScheduleRecord);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvStationRecord findCvStationRecordById(Long id);
	/**
	 * Return all persistent instances of the <code>CvStationRecord</code> entity.
	 */
	public List<CvStationRecord> findAllCvStationRecords();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvStationRecord(CvStationRecord cvStationRecord);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvStationRecord(CvStationRecord cvStationRecord);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTimeZoneRecord findCvTimeZoneRecordById(String id);
	/**
	 * Return all persistent instances of the <code>CvTimeZoneRecord</code> entity.
	 */
	public List<CvTimeZoneRecord> findAllCvTimeZoneRecords();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTimeZoneRecord(CvTimeZoneRecord cvTimeZoneRecord);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTimeZoneRecord(CvTimeZoneRecord cvTimeZoneRecord);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTranslationRecord findCvTranslationRecordById(String id);
	/**
	 * Return all persistent instances of the <code>CvTranslationRecord</code> entity.
	 */
	public List<CvTranslationRecord> findAllCvTranslationRecords();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTranslationRecord(CvTranslationRecord cvTranslationRecord);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTranslationRecord(CvTranslationRecord cvTranslationRecord);

	/**
	 * Delete all objects of the class from database
	 */
	public int deleteAllFromObject(Class voClass);
}