package com.cablevision.dao;

import java.util.List;

import com.cablevision.vo.CreceProd;
import com.cablevision.vo.Extra;
import com.cablevision.vo.ProductService;
import com.cablevision.vo.UpgradesExtra;
import com.cablevision.vo.UpgradesProd;

/**
 * The DAO interface for the ProductService entity.
 */
public interface ICotizadorDao {
	
	public List findAllServices();
	
	public List<ProductService> findProductsByService(Long idService);
	
	public Extra findExtraById(Long idExtra);
	
	public List<UpgradesExtra> findUpgradesExtraByIdProduct(Long id); 
	
	public List<UpgradesProd> findUpgradesProductByIdProduct(Long id);
	
	public List<CreceProd> findCreceProductByIdProduct(Long id);
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
	public ProductService findProductServiceById(Long id);
	/**
	 * Return all persistent instances of the <code>ProductService</code> entity.
	 */
	public List<ProductService> findAllProductServices();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistProductService(ProductService productService);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeProductService(ProductService productService);
}