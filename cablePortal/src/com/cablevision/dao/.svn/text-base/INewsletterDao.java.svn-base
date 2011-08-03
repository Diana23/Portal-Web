package com.cablevision.dao;

import java.util.List;

import com.cablevision.vo.CvCategorianewsletter;
import com.cablevision.vo.CvNewsletter;
import com.cablevision.vo.CvNewsletterCategoriausuario;

/**
 * The DAO interface for the CvNewsletter entity.
 */
public interface INewsletterDao {
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
	public CvNewsletter findCvNewsletterById(Long id);
	/**
	 * Find an entity by its email.
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvNewsletter> findCvNewsletterByEmail(String email);
	/**
	 * Return all persistent instances of the <code>CvNewsletter</code> entity.
	 */
	public List<CvNewsletter> findAllCvNewsletters();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvNewsletter(CvNewsletter cvNewsletter);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvNewsletter(CvNewsletter cvNewsletter);
	
	public CvNewsletter findCvNewsletterByIdAndAccount(Integer account);
	
	public List<CvCategorianewsletter> findCategoriaNewsletter();
	
	public void saveCategorias(List<CvNewsletterCategoriausuario> categorias);
	
	public  List<CvNewsletterCategoriausuario> findCategoriasByUser(Long idUsuario);
	
	public void removeCvNewsletterCategoriausuario(CvNewsletterCategoriausuario categorias) ;
}