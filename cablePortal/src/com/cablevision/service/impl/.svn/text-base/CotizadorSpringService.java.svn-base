package com.cablevision.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.cablevision.dao.ICotizadorDao;
import com.cablevision.service.ICotizadorService;
import com.cablevision.vo.CreceProd;
import com.cablevision.vo.Extra;
import com.cablevision.vo.ProductService;
import com.cablevision.vo.Service;
import com.cablevision.vo.UpgradesExtra;
import com.cablevision.vo.UpgradesProd;

/**
 * The service class for the ProductService entity.
 */
public class CotizadorSpringService implements ICotizadorService {
	/**
	 * The dao instance injected by Spring.
	 */
	private ICotizadorDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "CotizadorService";
	
	public CotizadorSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>ICotizadorService</code> instance.
	 */
	public static ICotizadorService getInstance(ApplicationContext context) {
		return (ICotizadorService)context.getBean(SERVICE_BEAN_ID);
	}
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public ProductService findProductServiceById(Long id) throws Exception {
		try {
			return getDao().findProductServiceById(id);
		} catch (RuntimeException e) {
			throw new Exception("findProductServiceById failed with the id " + id + ": " + e.getMessage());
		}
	}
	
	public List<Service> findAllServices()throws Exception{
		try {
			return getDao().findAllServices();
		} catch (RuntimeException e) {
			throw new Exception("findAllServices failed: " + e.getMessage());
		}
	}
	
	public List<ProductService> findProductsByService(Long idService)throws Exception{
		try{
			return getDao().findProductsByService(idService);
		}catch (Exception e) {
			throw new Exception("findServiceById failed: " + e.getMessage());
		}
	}
	
	public Extra findExtraById(Long idExtra) throws Exception{
		try{
			return getDao().findExtraById(idExtra);
		}catch (Exception e) {
			throw new Exception("findExtraById failed: " + e.getMessage());
		}
	}
	
	public List<UpgradesExtra> findUpgradesExtraByIdProduct(Long id) throws Exception{
		try{
			return getDao().findUpgradesExtraByIdProduct(id);
		}catch (Exception e) {
			throw new Exception("findUpgradesExtraByIdProduct failed: " + e.getMessage());
		}
	}
	
	public List<UpgradesProd> findUpgradesProductByIdProduct(Long id) throws Exception{
		try{
			return getDao().findUpgradesProductByIdProduct(id);
		}catch (Exception e) {
			throw new Exception("findUpgradesProductByIdProduct failed: " + e.getMessage());
		}
	}
	
	public List<CreceProd> findCreceProductByIdProduct(Long id) throws Exception{
		try{
			return getDao().findCreceProductByIdProduct(id);
		}catch (Exception e) {
			throw new Exception("findCreceProductByIdProduct failed: " + e.getMessage());
		}
	}
	
	/**
	 * Return all persistent instances of the <code>ProductService</code> entity.
	 */
	public List<ProductService> findAllProductServices() throws Exception {
		try {
			return getDao().findAllProductServices();
		} catch (RuntimeException e) {
			throw new Exception("findAllProductServices failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<ProductService> findProductServicesByExample(ProductService productService) throws Exception {
		try {
			return getDao().findByExample(productService);
		} catch (RuntimeException e) {
			throw new Exception("findProductServicesByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistProductService(ProductService productService) throws Exception {
		try {
			getDao().persistProductService(productService);
		} catch (RuntimeException e) {
			throw new Exception("persistProductService failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeProductService(ProductService productService) throws Exception {
		try {
			getDao().removeProductService(productService);
		} catch (RuntimeException e) {
			throw new Exception("removeProductService failed: " + e.getMessage());
		}
	}

	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(ICotizadorDao dao) {
		this.dao = dao;
	}
	public ICotizadorDao getDao() {
		return this.dao;
	}
}