package com.cablevision.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.dao.INewsletterDao;
import com.cablevision.vo.CvCategorianewsletter;
import com.cablevision.vo.CvNewsletter;
import com.cablevision.vo.CvNewsletterCategoriausuario;

/**
 * The DAO class for the CvNewsletter entity.
 */
public class NewsletterHibernateDao extends HibernateDaoSupport implements INewsletterDao {
	public NewsletterHibernateDao() {
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
	public CvNewsletter findCvNewsletterById(Long id) {
		return (CvNewsletter)getHibernateTemplate().load(CvNewsletter.class, id);
	}
	
	/**
	 * Find an entity by its email.
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvNewsletter> findCvNewsletterByEmail(String email){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CvNewsletter.class);
		criteria.add(Property.forName("nwlEmail").eq(email));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	/**
	 * Return all persistent instances of the <code>CvNewsletter</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvNewsletter> findAllCvNewsletters() {
	 	return getHibernateTemplate().loadAll(CvNewsletter.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvNewsletter(CvNewsletter cvNewsletter) {
		getHibernateTemplate().saveOrUpdate(cvNewsletter);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvNewsletter(CvNewsletter cvNewsletter) {
		getHibernateTemplate().delete(cvNewsletter);
	}
	
	
	public  CvNewsletter findCvNewsletterByIdAndAccount(Integer account){		
		StringBuffer query = new StringBuffer();
		query.append(" from CvNewsletter n where n.nwlAccount = ");
		query.append(account);
		if(getHibernateTemplate().find(query.toString()).size()>0)
			return (CvNewsletter)getHibernateTemplate().find(query.toString()).get(0);
		else
			return new CvNewsletter();
	}
	

	@SuppressWarnings("unchecked")
	public List<CvCategorianewsletter> findCategoriaNewsletter() {
		List<CvCategorianewsletter> allCateg = new ArrayList<CvCategorianewsletter>();
		List<CvCategorianewsletter> categorias = new ArrayList<CvCategorianewsletter>();
		List<CvCategorianewsletter> allHijos = new ArrayList<CvCategorianewsletter>();
		Map<Long, CvCategorianewsletter> padresMap = new HashMap<Long, CvCategorianewsletter>();
		allCateg = getHibernateTemplate().find("from CvCategorianewsletter c ");
		Long padreTmp=null;
		String padreName="";
		for(CvCategorianewsletter cp : allCateg) {
			CvCategorianewsletter newCategoria = new CvCategorianewsletter();
			if(cp.getCnlPadre()==null){
				allHijos = new ArrayList<CvCategorianewsletter>();
				padreTmp=cp.getCnlId();
				padreName=cp.getCnlNombre();
				continue;
			}else if(!padresMap.containsKey(cp.getCnlPadre())){
				for(CvCategorianewsletter cph : allCateg){
					if(cph.getCnlPadre()!=null && padreTmp!=null
							&& cph.getCnlPadre().intValue()==padreTmp.intValue())
						allHijos.add(cph);
					else
						continue;
				}
			}else{
				continue;
			}
			newCategoria.setHijos(allHijos);
			newCategoria.setPadreId(padreTmp);
			newCategoria.setPadreName(padreName);
			padresMap.put(cp.getCnlPadre(), newCategoria);
			categorias.add(newCategoria);
		}
		return categorias;
	}
	
	public void saveCategorias(List<CvNewsletterCategoriausuario> categorias) {
		for(CvNewsletterCategoriausuario cat:categorias){
			getHibernateTemplate().saveOrUpdate(cat);
		}
	}
	
	@SuppressWarnings("unchecked")
	public  List<CvNewsletterCategoriausuario> findCategoriasByUser(Long idUsuario){		
		StringBuffer query = new StringBuffer();
		query.append(" from CvNewsletterCategoriausuario n where compId.ncuIdusuario = ");
		query.append(idUsuario);
		if(getHibernateTemplate().find(query.toString()).size()>0)
			return getHibernateTemplate().find(query.toString());
		else
			return null;
	}
	
	public void removeCvNewsletterCategoriausuario(CvNewsletterCategoriausuario categorias) {
		getHibernateTemplate().delete(categorias);
	}
}