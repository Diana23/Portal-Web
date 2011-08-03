package com.cablevision.dao.impl;

import java.util.ArrayList;import java.util.Iterator;import java.util.List;import org.apache.commons.lang.StringUtils;import org.hibernate.Hibernate;import org.hibernate.criterion.DetachedCriteria;import org.hibernate.criterion.MatchMode;import org.hibernate.criterion.Order;import org.hibernate.criterion.Restrictions;import org.hibernate.type.Type;import org.springframework.orm.hibernate3.support.HibernateDaoSupport;import com.cablevision.dao.IChannelDao;import com.cablevision.util.Constantes;import com.cablevision.vo.Cvcategory;import com.cablevision.vo.Cvchannel;import com.cablevision.vo.CvchannelPack;import com.cablevision.vo.Cvpack;

/**
 * The DAO class for the entities: Cvcategory, Cvchannel, CvchannelPack, Cvpack.
 */
public class ChannelHibernateDao extends HibernateDaoSupport implements IChannelDao {
	public ChannelHibernateDao() {
		super();
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
	public Cvcategory findCvcategoryById(Long id) {
		return (Cvcategory)getHibernateTemplate().load(Cvcategory.class, id);
	}
	/**
	 * Return all persistent instances of the <code>Cvcategory</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<Cvcategory> findAllCvcategories() {
		return getHibernateTemplate().loadAll(Cvcategory.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvcategory(Cvcategory cvcategory) {
		getHibernateTemplate().saveOrUpdate(cvcategory);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvcategory(Cvcategory cvcategory) {
		getHibernateTemplate().delete(cvcategory);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Cvchannel findCvchannelById(Long id) {
		return (Cvchannel)getHibernateTemplate().load(Cvchannel.class, id);
	}
	/**
	 * Return all persistent instances of the <code>Cvchannel</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<Cvchannel> findAllCvchannels() {
		return getHibernateTemplate().find("from Cvchannel order by idchannel");
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvchannel(Cvchannel cvchannel) {
		getHibernateTemplate().saveOrUpdate(cvchannel);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvchannel(Cvchannel cvchannel) {
		getHibernateTemplate().delete(cvchannel);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvchannelPack findCvchannelPackById(com.cablevision.vo.CvchannelPackPK id) {
		return (CvchannelPack)getHibernateTemplate().load(CvchannelPack.class, id);
	}
	/**
	 * Return all persistent instances of the <code>CvchannelPack</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvchannelPack> findAllCvchannelPacks() {
		return getHibernateTemplate().loadAll(CvchannelPack.class);
	}	public List<CvchannelPack> findAllCvchannelPacksByIdChannel(Long idChannel) {		return getHibernateTemplate().find("from CvchannelPack where cvchannel.idchannel=?", idChannel);	}
	public List <Cvpack> findCvpacksByChannelID(Long idChannel){
		try {
			List list = getHibernateTemplate().find("from Cvpack p where p.idpack in(select cp.cvpack.idpack from CvchannelPack cp where cp.cvchannel.idchannel = ?)",
					new Object[]{idChannel});

			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvchannelPack(CvchannelPack cvchannelPack) {
		getHibernateTemplate().saveOrUpdate(cvchannelPack);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvchannelPack(CvchannelPack cvchannelPack) {
		getHibernateTemplate().delete(cvchannelPack);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Cvpack findCvpackById(Long id) {
		return (Cvpack)getHibernateTemplate().load(Cvpack.class, id);
	}
	/**
	 * Return all persistent instances of the <code>Cvpack</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<Cvpack> findAllCvpacks() {
		return getHibernateTemplate().loadAll(Cvpack.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvpack(Cvpack cvpack) {
		getHibernateTemplate().saveOrUpdate(cvpack);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvpack(Cvpack cvpack) {
		getHibernateTemplate().delete(cvpack);
	}	public List<Cvchannel> findCvChannel(Integer sort, String search, List<Long> categoryIdsForFilter) {		List<Cvchannel> result = new ArrayList<Cvchannel>();		DetachedCriteria criteria = DetachedCriteria.forClass(Cvchannel.class);		if(sort != null && sort > 0) {			String sortBy = (sort.intValue() == Constantes.CANALES_ORDENAR_NOMBRE_CANAL)?"name":				(sort.intValue() == Constantes.CANALES_ORDENAR_NUMERO_CANAL)?"idchannel":					(sort.intValue() == Constantes.CANALES_ORDENAR_CATEGORIAS)?"name":						(sort.intValue() == Constantes.CANALES_ORDENAR_PAQUETES)?"name":"name";						if(sort.intValue() == Constantes.CANALES_ORDENAR_CATEGORIAS) {				criteria.createCriteria("cvcategory", "cvcategory").addOrder(Order.asc(sortBy));			} else				criteria.addOrder(Order.asc(sortBy));		} else {			criteria.addOrder(Order.asc("idchannel"));		}		Long newValLong = null;		if(StringUtils.isNotBlank(search)) {			try {				newValLong = new Long(search);			}catch(NumberFormatException nfe) {				System.out.println("El valor no pudo ser parseado a Long para buscar por numero de canal...");			}			if(newValLong != null)				criteria.add(Restrictions.eq("idchannel", newValLong));			else				criteria.add(Restrictions.like("name", search, MatchMode.ANYWHERE).ignoreCase());		}		List<Type> typeList = new ArrayList<Type>();		if(categoryIdsForFilter != null && categoryIdsForFilter.size() > 0) {			StringBuilder queryFilter = new StringBuilder();			queryFilter.append("(");			for(Iterator<Long> it = categoryIdsForFilter.iterator(); it.hasNext(); ) {				it.next();				queryFilter.append("CNL_CATEGORY=?"); 				typeList.add(Hibernate.LONG);				if(it.hasNext()) {					queryFilter.append(" OR ");				}			}			queryFilter.append(")");			Type[] types = new Type[typeList.size()];			int c = 0;			for(Type t : typeList)				types[c++] = t;			criteria.add(Restrictions.sqlRestriction(queryFilter.toString(), categoryIdsForFilter.toArray(), types));		}		result = getHibernateTemplate().findByCriteria(criteria);		return result;	}
}