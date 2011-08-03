package com.cablevision.service;

import java.util.List;

import com.cablevision.vo.CvCategorianewsletter;
import com.cablevision.vo.CvNewsletter;
import com.cablevision.vo.CvNewsletterCategoriausuario;

/**
 * The service interface for the CvNewsletter entity.
 */
public interface INewsletterService {
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvNewsletter findCvNewsletterById(Long id) throws Exception;
	
	/**
	 * Find an entity by its email.
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvNewsletter> findCvNewsletterByEmail(String email) throws Exception;
	
	/**
	 * Return all persistent instances of the <code>CvNewsletter</code> entity.
	 */
	public List<CvNewsletter> findAllCvNewsletters() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvNewsletter> findCvNewslettersByExample(CvNewsletter cvNewsletter) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvNewsletter(CvNewsletter cvNewsletter) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvNewsletter(CvNewsletter cvNewsletter) throws Exception;
	
	public CvNewsletter findCvNewsletterByIdAndAccount(Integer account) throws Exception;
	
	public List<CvCategorianewsletter> findCategoriaNewsletter()throws Exception;
	
	public void saveCategorias(List<CvNewsletterCategoriausuario> categorias)throws Exception;
	
	public  List<CvNewsletterCategoriausuario> findCategoriasByUser(Long idUsuario)throws Exception;
	
	public void removeCvNewsletterCategoriausuario(CvNewsletterCategoriausuario categorias)throws Exception;
}