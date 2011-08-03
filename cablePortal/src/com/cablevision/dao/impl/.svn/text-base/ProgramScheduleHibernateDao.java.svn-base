package com.cablevision.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.dao.IProgramScheduleDao;
import com.cablevision.vo.CvProgramRecord;
import com.cablevision.vo.CvScheduleRecord;
import com.cablevision.vo.CvStationRecord;
import com.cablevision.vo.CvTimeZoneRecord;
import com.cablevision.vo.CvTranslationRecord;

/**
 * The DAO class for the entities: CvProgramRecord, CvScheduleRecord, CvStationRecord, CvTimeZoneRecord, CvTranslationRecord.
 */
public class ProgramScheduleHibernateDao extends HibernateDaoSupport implements IProgramScheduleDao {
	public ProgramScheduleHibernateDao() {
		super();
	}
	
	public List<CvScheduleRecord> findAllCvScheduleRecordsBetween(Date start,Date end, String cnlCategory) throws Exception{
		DetachedCriteria criteria = DetachedCriteria.forClass(CvScheduleRecord.class);
		
		//esto es para evitar que traiga registros cuyo endTime sea algo como 10:00:23
		//utilizaremos como limite 09:59:23 para el ejemplo anterior
		Calendar cal = Calendar.getInstance();
		cal.setTime(end);
		cal.add(Calendar.MINUTE, -1);
		
		if(StringUtils.isNotEmpty(cnlCategory)){
			String queryString = "from CvScheduleRecord as SR " +
						"where SR.cvStationRecord.stnStationNum in "+ 
						"(select CH.idchannel from Cvchannel as CH where CH.cvcategory.idcategory = ?) "+
						"and ( SR.skeEndDateTime between ? and ? or " +
						"SR.compId.skeAirDateTime between ? and ? ) ";
			Object[] values = {
					Long.parseLong(cnlCategory),
					start,end,
					start,end
			};
			
			return getHibernateTemplate().find(queryString, values);
		}else{

			criteria.add(
				Expression.or(
					Property.forName("compId.skeAirDateTime").between(start, cal.getTime()), 
					Property.forName("skeEndDateTime").between(start, cal.getTime())
				)
			);
			criteria.addOrder(Order.asc("cvStationRecord.stnStationNum"));
			criteria.addOrder(Order.asc("compId.skeAirDateTime"));
			
			return getHibernateTemplate().findByCriteria(criteria);
		}
	}
	
	public List<CvScheduleRecord> findAllCvScheduleRecordsByCategory(Date start,Date end,String category,String cnlCategory) throws Exception{
		//esto es para evitar que traiga registros cuyo endTime sea algo como 10:00:23
		//utilizaremos como limite 09:59:23 para el ejemplo anterior
		Calendar cal = Calendar.getInstance();
		cal.setTime(end);
		cal.add(Calendar.MINUTE, -1);
		
		String queryString = "from CvScheduleRecord as SR " +
				"where (SR.cvProgramRecord.proGenreDesc1 like ? or " +
				"SR.cvProgramRecord.proGenreDesc2 like ? or " +
				"SR.cvProgramRecord.proGenreDesc3 like ? or " +
				"SR.cvProgramRecord.proGenreDesc4 like ? or " +
				"SR.cvProgramRecord.proGenreDesc5 like ? or " +
				"SR.cvProgramRecord.proGenreDesc6 like ? ) and (" +
				"SR.skeEndDateTime between ? and ? or " +
				"SR.compId.skeAirDateTime between ? and ? ) ";
		
		if(StringUtils.isNotEmpty(cnlCategory)){
			queryString += "and SR.cvStationRecord.stnStationNum in "+ 
			"(select CH.idchannel from Cvchannel as CH where CH.cvcategory.idcategory = "+Long.parseLong(cnlCategory)+") ";
		}
		
		queryString += "order by cvStationRecord.stnStationNum, SR.compId.skeAirDateTime";
		
		Object[] values = {
				"%"+category+"%",
				"%"+category+"%",
				"%"+category+"%",
				"%"+category+"%",
				"%"+category+"%",
				"%"+category+"%",
				start,end,
				start,end
			};
		
		return getHibernateTemplate().find(queryString, values);
	}
	
	/**
	 * Return the persistent entities returned from a named query.
	 */
	public List findByNamedQuery(String queryName) {
		return getHibernateTemplate().findByNamedQuery(queryName);
	}
	/**
	 * Return the persistent entities returned from a named query with named parameters.
	 */
	public List findByNamedQuery(String queryName, String[] paramNames, Object[] paramValues) {
		if (paramNames.length != paramValues.length) {
			throw new IllegalArgumentException();
		}
		return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, paramValues);
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List findByExample(Object example) {
		return getHibernateTemplate().findByExample(example);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvProgramRecord findCvProgramRecordById(String id) {
		return (CvProgramRecord)getHibernateTemplate().load(CvProgramRecord.class, id);
	}
	/**
	 * Return all persistent instances of the <code>CvProgramRecord</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvProgramRecord> findAllCvProgramRecords() {
	 	return getHibernateTemplate().loadAll(CvProgramRecord.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvProgramRecord(CvProgramRecord cvProgramRecord) {
		try {
		getHibernateTemplate().save(cvProgramRecord);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvProgramRecord(CvProgramRecord cvProgramRecord) {
		getHibernateTemplate().delete(cvProgramRecord);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvScheduleRecord findCvScheduleRecordById(Long id) {
		return (CvScheduleRecord)getHibernateTemplate().load(CvScheduleRecord.class, id);
	}
	/**
	 * Return all persistent instances of the <code>CvScheduleRecord</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvScheduleRecord> findAllCvScheduleRecords() {
	 	return getHibernateTemplate().loadAll(CvScheduleRecord.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvScheduleRecord(CvScheduleRecord cvScheduleRecord) {
		try {
			getHibernateTemplate().save(cvScheduleRecord);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvScheduleRecord(CvScheduleRecord cvScheduleRecord) {
		getHibernateTemplate().delete(cvScheduleRecord);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvStationRecord findCvStationRecordById(Long id) {
		return (CvStationRecord)getHibernateTemplate().load(CvStationRecord.class, id);
	}
	/**
	 * Return all persistent instances of the <code>CvStationRecord</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvStationRecord> findAllCvStationRecords() {
	 	return getHibernateTemplate().loadAll(CvStationRecord.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvStationRecord(CvStationRecord cvStationRecord) {
		try {
			getHibernateTemplate().save(cvStationRecord);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvStationRecord(CvStationRecord cvStationRecord) {
		getHibernateTemplate().delete(cvStationRecord);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTimeZoneRecord findCvTimeZoneRecordById(String id) {
		return (CvTimeZoneRecord)getHibernateTemplate().load(CvTimeZoneRecord.class, id);
	}
	/**
	 * Return all persistent instances of the <code>CvTimeZoneRecord</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvTimeZoneRecord> findAllCvTimeZoneRecords() {
	 	return getHibernateTemplate().loadAll(CvTimeZoneRecord.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTimeZoneRecord(CvTimeZoneRecord cvTimeZoneRecord) {
		try {
			getHibernateTemplate().save(cvTimeZoneRecord);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTimeZoneRecord(CvTimeZoneRecord cvTimeZoneRecord) {
		getHibernateTemplate().delete(cvTimeZoneRecord);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTranslationRecord findCvTranslationRecordById(String id) {
		return (CvTranslationRecord)getHibernateTemplate().load(CvTranslationRecord.class, id);
	}
	/**
	 * Return all persistent instances of the <code>CvTranslationRecord</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvTranslationRecord> findAllCvTranslationRecords() {
	 	return getHibernateTemplate().loadAll(CvTranslationRecord.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTranslationRecord(CvTranslationRecord cvTranslationRecord) {
		try {
			getHibernateTemplate().save(cvTranslationRecord);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTranslationRecord(CvTranslationRecord cvTranslationRecord) {
		getHibernateTemplate().delete(cvTranslationRecord);
	}
	
	/**
	 * Delete all objects of the class from database
	 */
	public int deleteAllFromObject(Class voClass) {
		return getHibernateTemplate().bulkUpdate("delete " + voClass.getName());
	}
	
}