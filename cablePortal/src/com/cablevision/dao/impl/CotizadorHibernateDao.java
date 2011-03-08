package com.cablevision.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.dao.ICotizadorDao;
import com.cablevision.vo.CreceProd;
import com.cablevision.vo.Extra;
import com.cablevision.vo.ProductService;
import com.cablevision.vo.Service;
import com.cablevision.vo.UpgradesExtra;
import com.cablevision.vo.UpgradesProd;

/**
 * The DAO class for the ProductService entity.
 */
public class CotizadorHibernateDao extends HibernateDaoSupport implements ICotizadorDao {
	public CotizadorHibernateDao() {
		super();
	}
	
	public List<Service> findAllServices(){
		DetachedCriteria criteria = DetachedCriteria.forClass(Service.class);
		
		criteria.add(Property.forName("activo").eq(Boolean.TRUE));
		criteria.addOrder(Order.asc("orden"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public List<ProductService> findProductsByService(Long idService){
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductService.class);
		
		criteria.add(Property.forName("service.idService").eq(idService));
		criteria.addOrder(Order.asc("priority"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Extra findExtraById(Long idExtra){
		return (Extra)getHibernateTemplate().load(Extra.class, idExtra);
	}
	
	public List<UpgradesExtra> findUpgradesExtraByIdProduct(Long id){
		DetachedCriteria criteria = DetachedCriteria.forClass(UpgradesExtra.class);
		
		criteria.add(Property.forName("id.productService.idProductService").eq(id));
		criteria.addOrder(Order.asc("priority"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public List<UpgradesProd> findUpgradesProductByIdProduct(Long id){
		DetachedCriteria criteria = DetachedCriteria.forClass(UpgradesProd.class);
		
		criteria.add(Property.forName("id.productService.idProductService").eq(id));
		criteria.addOrder(Order.asc("priority"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public List<CreceProd> findCreceProductByIdProduct(Long id){
		DetachedCriteria criteria = DetachedCriteria.forClass(CreceProd.class);
		
		criteria.add(Property.forName("compId.productService.idProductService").eq(id));
		criteria.addOrder(Order.asc("cprPriority"));
		return getHibernateTemplate().findByCriteria(criteria);
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
	public ProductService findProductServiceById(Long id) {
		return (ProductService)getHibernateTemplate().load(ProductService.class, id);
	}
	/**
	 * Return all persistent instances of the <code>ProductService</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<ProductService> findAllProductServices() {
	 	return getHibernateTemplate().loadAll(ProductService.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistProductService(ProductService productService) {
		getHibernateTemplate().saveOrUpdate(productService);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeProductService(ProductService productService) {
		getHibernateTemplate().delete(productService);
	}
}