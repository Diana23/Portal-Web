package com.cablevision.service;

import java.util.List;

import com.cablevision.vo.CreceProd;
import com.cablevision.vo.Extra;
import com.cablevision.vo.ProductService;
import com.cablevision.vo.Service;
import com.cablevision.vo.UpgradesExtra;
import com.cablevision.vo.UpgradesProd;

/**
 * The service interface for the ProductService entity.
 */
public interface ICotizadorService {
	
	public List<Service> findAllServices()throws Exception;
	
	public List<ProductService> findProductsByService(Long idService)throws Exception;
	
	public Extra findExtraById(Long idExtra) throws Exception;
	
	public List<UpgradesExtra> findUpgradesExtraByIdProduct(Long id) throws Exception;
	
	public List<UpgradesProd> findUpgradesProductByIdProduct(Long id) throws Exception;
	
	public List<CreceProd> findCreceProductByIdProduct(Long id) throws Exception;
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public ProductService findProductServiceById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>ProductService</code> entity.
	 */
	public List<ProductService> findAllProductServices() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<ProductService> findProductServicesByExample(ProductService productService) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistProductService(ProductService productService) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeProductService(ProductService productService) throws Exception;
}