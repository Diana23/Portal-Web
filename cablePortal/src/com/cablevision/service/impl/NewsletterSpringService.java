package com.cablevision.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.cablevision.dao.INewsletterDao;
import com.cablevision.service.INewsletterService;
import com.cablevision.vo.CvCategorianewsletter;
import com.cablevision.vo.CvNewsletter;
import com.cablevision.vo.CvNewsletterCategoriausuario;

/**
 * The service class for the CvNewsletter entity.
 */
public class NewsletterSpringService implements INewsletterService {
	/**
	 * The dao instance injected by Spring.
	 */
	private INewsletterDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "NewsletterService";
	
	public NewsletterSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>INewsletterService</code> instance.
	 */
	public static INewsletterService getInstance(ApplicationContext context) {
		return (INewsletterService)context.getBean(SERVICE_BEAN_ID);
	}
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvNewsletter findCvNewsletterById(Long id) throws Exception {
		try {
			return getDao().findCvNewsletterById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvNewsletterById failed with the id " + id + ": " + e.getMessage());
		}
	}
	
	/**
	 * Find an entity by its email.
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvNewsletter> findCvNewsletterByEmail(String email) throws Exception{
		try{
			return getDao().findCvNewsletterByEmail(email);
		}catch(RuntimeException e){
			throw new Exception("findCvNewsletterByEmail failde with the email "+ email + ": "+e.getMessage());
		}
	}
	
	/**
	 * Return all persistent instances of the <code>CvNewsletter</code> entity.
	 */
	public List<CvNewsletter> findAllCvNewsletters() throws Exception {
		try {
			return getDao().findAllCvNewsletters();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvNewsletters failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvNewsletter> findCvNewslettersByExample(CvNewsletter cvNewsletter) throws Exception {
		try {
			return getDao().findByExample(cvNewsletter);
		} catch (RuntimeException e) {
			throw new Exception("findCvNewslettersByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvNewsletter(CvNewsletter cvNewsletter) throws Exception {
		try {
			getDao().persistCvNewsletter(cvNewsletter);
		} catch (RuntimeException e) {
			throw new Exception("persistCvNewsletter failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvNewsletter(CvNewsletter cvNewsletter) throws Exception {
		try {
			getDao().removeCvNewsletter(cvNewsletter);
		} catch (RuntimeException e) {
			throw new Exception("removeCvNewsletter failed: " + e.getMessage());
		}
	}

	
	
	public CvNewsletter findCvNewsletterByIdAndAccount(Integer account) throws Exception {
		try {
			return getDao().findCvNewsletterByIdAndAccount(account);
		} catch (RuntimeException e) {
			throw new Exception("findCvNewsletterByIdAndAccount failed: " + e.getMessage());
		}
	}
	
	
	public List<CvCategorianewsletter> findCategoriaNewsletter() throws Exception {
		try {
			return getDao().findCategoriaNewsletter();
		} catch (RuntimeException e) {
			throw new Exception("findCategoriaNewsletter failed: " + e.getMessage());
		}
	}
	
	public void removeCvNewsletterCategoriausuario(CvNewsletterCategoriausuario categorias)  throws Exception {
		try {
			getDao().removeCvNewsletterCategoriausuario(categorias);
		} catch (RuntimeException e) {
			throw new Exception("idUsuario failed: " + e.getMessage());
		}
	}
	
	
	public void saveCategorias(List<CvNewsletterCategoriausuario> categorias) throws Exception {
		try {
			getDao().saveCategorias(categorias);
		} catch (RuntimeException e) {
			throw new Exception("saveCategorias failed: " + e.getMessage());
		}
	}
	
	
	public List<CvNewsletterCategoriausuario> findCategoriasByUser(Long idUsuario) throws Exception {
		try {
			return getDao().findCategoriasByUser(idUsuario);
		} catch (RuntimeException e) {
			throw new Exception("idUsuario failed: " + e.getMessage());
		}
	}
	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(INewsletterDao dao) {
		this.dao = dao;
	}
	public INewsletterDao getDao() {
		return this.dao;
	}
}