package com.cablevision.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.dao.ICommentOaDao;
import com.cablevision.vo.CvCommentoa;
import com.cablevision.vo.CvTrackoa;

/**
 * The DAO class for the entities: CvCommentoa, CvTrackoa.
 */
@SuppressWarnings("unchecked")
public class CommentOaHibernateDao extends HibernateDaoSupport implements ICommentOaDao {
	public CommentOaHibernateDao() {
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
	public CvCommentoa findCvCommentoaById(Long id) {
		return (CvCommentoa)getHibernateTemplate().load(CvCommentoa.class, id);
	}
	
	/**
	 * Find an entity by its no. de solicitud
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvTrackoa> findCvTrackoaByAccountId(String accountId) {
		List<CvTrackoa> tracks = getHibernateTemplate().find("from CvTrackoa where toaAccountid=? order by toaDate desc", accountId);
		return tracks;
	}
	
	/**
	 * Find an entity by its accountId.
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTrackoa findCvTrackoaByNumberOa(String numberOa){
		List<CvTrackoa> tracks = getHibernateTemplate().find("from CvTrackoa where toaNumberOa=?", numberOa);
		if(tracks!=null && tracks.size()>0){
			for(CvTrackoa t : tracks){
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Find entity list by its noSolicitud.
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvCommentoa> findCvCommentoaByNoSolicitud(String noSolicitud){
		List<CvCommentoa> comments = getHibernateTemplate().find("from CvCommentoa c where c.coaToaId in (select t.toaId from CvTrackoa t where t.toaNumberOa= ?)", noSolicitud);
		return comments;
	}
	
	
	/**
	 * Return all persistent instances of the <code>CvCommentoa</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvCommentoa> findAllCvCommentoas() {
	 	return getHibernateTemplate().loadAll(CvCommentoa.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvCommentoa(CvCommentoa cvCommentoa) {
		getHibernateTemplate().saveOrUpdate(cvCommentoa);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvCommentoa(CvCommentoa cvCommentoa) {
		getHibernateTemplate().delete(cvCommentoa);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTrackoa findCvTrackoaById(Long id) {
		return (CvTrackoa)getHibernateTemplate().load(CvTrackoa.class, id);
	}
	
	/**
	 * Return all persistent instances of the <code>CvTrackoa</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvTrackoa> findAllCvTrackoas() {
	 	return getHibernateTemplate().loadAll(CvTrackoa.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTrackoa(CvTrackoa cvTrackoa) {
		getHibernateTemplate().saveOrUpdate(cvTrackoa);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTrackoa(CvTrackoa cvTrackoa) {
		getHibernateTemplate().delete(cvTrackoa);
	}
}