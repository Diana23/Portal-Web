package com.cablevision.dao;

import java.util.List;

import com.cablevision.vo.Cvcategory;
import com.cablevision.vo.Cvchannel;
import com.cablevision.vo.CvchannelPack;
import com.cablevision.vo.Cvpack;

/**
 * The DAO interface for the entities: Cvcategory, Cvchannel, CvchannelPack, Cvpack.
 */
public interface IChannelDao {
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
	public Cvcategory findCvcategoryById(Long id);
	/**
	 * Return all persistent instances of the <code>Cvcategory</code> entity.
	 */
	public List<Cvcategory> findAllCvcategories();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvcategory(Cvcategory cvcategory);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvcategory(Cvcategory cvcategory);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Cvchannel findCvchannelById(Long id);
	/**
	 * Return all persistent instances of the <code>Cvchannel</code> entity.
	 */
	public List<Cvchannel> findAllCvchannels();
	
	public List<CvchannelPack> findAllCvchannelPacksByIdChannel(Long idChannel);
	
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvchannel(Cvchannel cvchannel);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvchannel(Cvchannel cvchannel);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvchannelPack findCvchannelPackById(com.cablevision.vo.CvchannelPackPK id);
	/**
	 * Return all persistent instances of the <code>CvchannelPack</code> entity.
	 */
	public List<CvchannelPack> findAllCvchannelPacks();
	
	public List <Cvpack> findCvpacksByChannelID(Long idChannel);
	
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvchannelPack(CvchannelPack cvchannelPack);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvchannelPack(CvchannelPack cvchannelPack);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Cvpack findCvpackById(Long id);
	/**
	 * Return all persistent instances of the <code>Cvpack</code> entity.
	 */
	public List<Cvpack> findAllCvpacks();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvpack(Cvpack cvpack);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvpack(Cvpack cvpack);
	
	public List<Cvchannel> findCvChannel(Integer sort, String search, List<Long> categoryIdsForFilter);
}