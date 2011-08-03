package com.cablevision.service;

import java.util.List;

import com.cablevision.vo.Cvcategory;
import com.cablevision.vo.Cvchannel;
import com.cablevision.vo.CvchannelPack;
import com.cablevision.vo.Cvpack;

/**
 * The service interface for the entities: Cvcategory, Cvchannel, CvchannelPack, Cvpack.
 */
public interface IChannelService {

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Cvcategory findCvcategoryById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>Cvcategory</code> entity.
	 */
	public List<Cvcategory> findAllCvcategories() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<Cvcategory> findCvcategoriesByExample(Cvcategory cvcategory) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvcategory(Cvcategory cvcategory) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvcategory(Cvcategory cvcategory) throws Exception;

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Cvchannel findCvchannelById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>Cvchannel</code> entity.
	 */
	public List<Cvchannel> findAllCvchannels() throws Exception;
	
	public List<CvchannelPack> findAllCvchannelPacksByIdChannel(Long idChannel);
	
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<Cvchannel> findCvchannelsByExample(Cvchannel cvchannel) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvchannel(Cvchannel cvchannel) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvchannel(Cvchannel cvchannel) throws Exception;

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvchannelPack findCvchannelPackById(com.cablevision.vo.CvchannelPackPK id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvchannelPack</code> entity.
	 */
	public List<CvchannelPack> findAllCvchannelPacks() throws Exception;
	
	public List <Cvpack> findCvpacksByChannelID(Long idChannel)throws Exception;
	
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvchannelPack> findCvchannelPacksByExample(CvchannelPack cvchannelPack) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvchannelPack(CvchannelPack cvchannelPack) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvchannelPack(CvchannelPack cvchannelPack) throws Exception;

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Cvpack findCvpackById(Long id) throws Exception;
	/**
	 * Return all persistent instances of the <code>Cvpack</code> entity.
	 */
	public List<Cvpack> findAllCvpacks() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<Cvpack> findCvpacksByExample(Cvpack cvpack) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvpack(Cvpack cvpack) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvpack(Cvpack cvpack) throws Exception;
	
	public List<Cvchannel> findCvChannel(Integer sort, String search, String[] categoryIdsForFilter);
}