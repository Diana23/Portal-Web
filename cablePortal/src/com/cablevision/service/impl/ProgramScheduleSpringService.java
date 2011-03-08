package com.cablevision.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.sql.BatchUpdateException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.UncategorizedSQLException;

import com.cablevision.dao.IProgramScheduleDao;
import com.cablevision.service.IProgramScheduleService;
import com.cablevision.vo.CvProgramRecord;
import com.cablevision.vo.CvScheduleRecord;
import com.cablevision.vo.CvStationRecord;
import com.cablevision.vo.CvTimeZoneRecord;
import com.cablevision.vo.CvTranslationRecord;

/**
 * The service class for the entities: CvProgramRecord, CvScheduleRecord, CvStationRecord, CvTimeZoneRecord, CvTranslationRecord.
 */
public class ProgramScheduleSpringService implements IProgramScheduleService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IProgramScheduleDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "ProgramScheduleService";
	
	public ProgramScheduleSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>IProgramScheduleService</code> instance.
	 */
	public static IProgramScheduleService getInstance(ApplicationContext context) {
		return (IProgramScheduleService)context.getBean(SERVICE_BEAN_ID);
	}

	public List<CvScheduleRecord> findAllCvScheduleRecordsBetween(Date now, String cnlCategory) throws Exception{
		Date[] range = this.getRange(now);
		return getDao().findAllCvScheduleRecordsBetween(range[0],range[1], cnlCategory);
	}
	
	public List<CvScheduleRecord> findAllCvScheduleRecordsByCategory(Date now,String category, String cnlCategory) throws Exception{
		Date[] range = this.getRange(now);
		return getDao().findAllCvScheduleRecordsByCategory(range[0],range[1], category, cnlCategory);
	}
	
	/**
	 * Crea el rango de tiempo para la fecha dada
	 * @param now
	 * @return
	 */
	private Date[] getRange(Date now){
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		
		Integer min = cal.get(Calendar.MINUTE);
		if(min < 20){
			cal.set(Calendar.MINUTE, 0);
		}else if(min < 50){
			cal.set(Calendar.MINUTE, 30);
		}else{
			cal.set(Calendar.MINUTE, 0);
			cal.add(Calendar.HOUR, 1);
		}
		
		Date start = cal.getTime();
		
		cal.add(Calendar.HOUR, 2);
		Date end = cal.getTime();
		
		Date[] values = {start,end};
		
		return values;
	}
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvProgramRecord findCvProgramRecordById(String id) throws Exception {
		try {
			return getDao().findCvProgramRecordById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvProgramRecordById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvProgramRecord</code> entity.
	 */
	public List<CvProgramRecord> findAllCvProgramRecords() throws Exception {
		try {
			return getDao().findAllCvProgramRecords();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvProgramRecords failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvProgramRecord> findCvProgramRecordsByExample(CvProgramRecord cvProgramRecord) throws Exception {
		try {
			return getDao().findByExample(cvProgramRecord);
		} catch (RuntimeException e) {
			throw new Exception("findCvProgramRecordsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvProgramRecord(CvProgramRecord cvProgramRecord) throws Exception {
		try {
			getDao().persistCvProgramRecord(cvProgramRecord);
		} catch (RuntimeException e) {
			throw new Exception("persistCvProgramRecord failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvProgramRecord(CvProgramRecord cvProgramRecord) throws Exception {
		try {
			getDao().removeCvProgramRecord(cvProgramRecord);
		} catch (RuntimeException e) {
			throw new Exception("removeCvProgramRecord failed: " + e.getMessage());
		}
	}
	
	/**
	 * Recorre una coleccion de objetos guardandolos uno a uno
	 */
	public void saveAllProgramRecords(List<CvProgramRecord> collection)throws Exception{
		for(CvProgramRecord cvProgramRecord : collection) {
			try {
				persistCvProgramRecord(cvProgramRecord);
			} catch (Exception e) {
				throw new Exception("saveAllProgramRecords failed : " + e.getMessage());
			}
		}
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvScheduleRecord findCvScheduleRecordById(Long id) throws Exception {
		try {
			return getDao().findCvScheduleRecordById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvScheduleRecordById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvScheduleRecord</code> entity.
	 */
	public List<CvScheduleRecord> findAllCvScheduleRecords() throws Exception {
		try {
			return getDao().findAllCvScheduleRecords();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvScheduleRecords failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvScheduleRecord> findCvScheduleRecordsByExample(CvScheduleRecord cvScheduleRecord) throws Exception {
		try {
			return getDao().findByExample(cvScheduleRecord);
		} catch (RuntimeException e) {
			throw new Exception("findCvScheduleRecordsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvScheduleRecord(CvScheduleRecord cvScheduleRecord) throws Exception {
		try {
			getDao().persistCvScheduleRecord(cvScheduleRecord);
		} catch (RuntimeException e) {
			throw new Exception("persistCvScheduleRecord failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvScheduleRecord(CvScheduleRecord cvScheduleRecord) throws Exception {
		try {
			getDao().removeCvScheduleRecord(cvScheduleRecord);
		} catch (RuntimeException e) {
			throw new Exception("removeCvScheduleRecord failed: " + e.getMessage());
		}
	}
	
	/**
	 * Recorre una coleccion de objetos guardandolos uno a uno
	 */
	public void saveAllScheduleRecords(List<CvScheduleRecord> collection) {
		for(CvScheduleRecord cvScheduleRecord : collection) {
			try {
				persistCvScheduleRecord(cvScheduleRecord);
			} catch (Exception e) {
				// TODO imprimir el log
			}
		}
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvStationRecord findCvStationRecordById(Long id) throws Exception {
		try {
			return getDao().findCvStationRecordById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvStationRecordById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvStationRecord</code> entity.
	 */
	public List<CvStationRecord> findAllCvStationRecords() throws Exception {
		try {
			return getDao().findAllCvStationRecords();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvStationRecords failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvStationRecord> findCvStationRecordsByExample(CvStationRecord cvStationRecord) throws Exception {
		try {
			return getDao().findByExample(cvStationRecord);
		} catch (RuntimeException e) {
			throw new Exception("findCvStationRecordsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvStationRecord(CvStationRecord cvStationRecord) throws Exception {
		try {
			getDao().persistCvStationRecord(cvStationRecord);
		} catch (RuntimeException e) {
			throw new Exception("persistCvStationRecord failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvStationRecord(CvStationRecord cvStationRecord) throws Exception {
		try {
			getDao().removeCvStationRecord(cvStationRecord);
		} catch (RuntimeException e) {
			throw new Exception("removeCvStationRecord failed: " + e.getMessage());
		}
	}
	
	/**
	 * Recorre una coleccion de objetos guardandolos uno a uno
	 */
	public void saveAllStationRecords(List<CvStationRecord> collection) {
		for(CvStationRecord cvStationRecord : collection) {
			try {
				persistCvStationRecord(cvStationRecord);
			} catch (Exception e) {
				// TODO imprimir el log
			}
		}
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTimeZoneRecord findCvTimeZoneRecordById(String id) throws Exception {
		try {
			return getDao().findCvTimeZoneRecordById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvTimeZoneRecordById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvTimeZoneRecord</code> entity.
	 */
	public List<CvTimeZoneRecord> findAllCvTimeZoneRecords() throws Exception {
		try {
			return getDao().findAllCvTimeZoneRecords();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvTimeZoneRecords failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvTimeZoneRecord> findCvTimeZoneRecordsByExample(CvTimeZoneRecord cvTimeZoneRecord) throws Exception {
		try {
			return getDao().findByExample(cvTimeZoneRecord);
		} catch (RuntimeException e) {
			throw new Exception("findCvTimeZoneRecordsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTimeZoneRecord(CvTimeZoneRecord cvTimeZoneRecord) throws Exception {
		try {
			getDao().persistCvTimeZoneRecord(cvTimeZoneRecord);
		} catch (RuntimeException e) {
			throw new Exception("persistCvTimeZoneRecord failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTimeZoneRecord(CvTimeZoneRecord cvTimeZoneRecord) throws Exception {
		try {
			getDao().removeCvTimeZoneRecord(cvTimeZoneRecord);
		} catch (RuntimeException e) {
			throw new Exception("removeCvTimeZoneRecord failed: " + e.getMessage());
		}
	}
	
	/**
	 * Recorre una coleccion de objetos guardandolos uno a uno
	 */
	public void saveAllTimeZoneRecords(List<CvTimeZoneRecord> collection) {
		for(CvTimeZoneRecord cvTimeZoneRecord : collection) {
			try {
				persistCvTimeZoneRecord(cvTimeZoneRecord);
			} catch (Exception e) {
				// TODO imprimir el log
			}
		}
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTranslationRecord findCvTranslationRecordById(String id) throws Exception {
		try {
			return getDao().findCvTranslationRecordById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvTranslationRecordById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvTranslationRecord</code> entity.
	 */
	public List<CvTranslationRecord> findAllCvTranslationRecords() throws Exception {
		try {
			return getDao().findAllCvTranslationRecords();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvTranslationRecords failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvTranslationRecord> findCvTranslationRecordsByExample(CvTranslationRecord cvTranslationRecord) throws Exception {
		try {
			return getDao().findByExample(cvTranslationRecord);
		} catch (RuntimeException e) {
			throw new Exception("findCvTranslationRecordsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTranslationRecord(CvTranslationRecord cvTranslationRecord) throws Exception {
		try {
			getDao().persistCvTranslationRecord(cvTranslationRecord);
		} catch (RuntimeException e) {
			throw new Exception("persistCvTranslationRecord failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTranslationRecord(CvTranslationRecord cvTranslationRecord) throws Exception {
		try {
			getDao().removeCvTranslationRecord(cvTranslationRecord);
		} catch (RuntimeException e) {
			throw new Exception("removeCvTranslationRecord failed: " + e.getMessage());
		}
	}
	
	/**
	 * Recorre una coleccion de objetos guardandolos uno a uno
	 */
	public void saveAllTranslationRecords(List<CvTranslationRecord> collection) {
		for(CvTranslationRecord cvTranslationRecord : collection) {
			try {
				persistCvTranslationRecord(cvTranslationRecord);
			} catch (Exception e) {
				// TODO imprimir el log
			}
		}
	}
	
	/**
	 * Delete all objects of the class from database
	 */
	public int deleteAllFromObject(Class voClass) {
		return getDao().deleteAllFromObject(voClass);
	}

	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(IProgramScheduleDao dao) {
		this.dao = dao;
	}
	public IProgramScheduleDao getDao() {
		return this.dao;
	}
}