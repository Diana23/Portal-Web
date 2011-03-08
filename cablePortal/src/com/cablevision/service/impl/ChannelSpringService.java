package com.cablevision.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import com.cablevision.vo.Cvcategory;
import com.cablevision.vo.Cvchannel;
import com.cablevision.vo.CvchannelPack;
import com.cablevision.vo.Cvpack;
import com.cablevision.service.IChannelService;
import com.cablevision.dao.IChannelDao;

/**
 * The service class for the entities: Cvcategory, Cvchannel, CvchannelPack, Cvpack.
 */
public class ChannelSpringService implements IChannelService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IChannelDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "ChannelService";

	public ChannelSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>IChannelService</code> instance.
	 */
	public static IChannelService getInstance(ApplicationContext context) {
		return (IChannelService)context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Cvcategory findCvcategoryById(Long id) throws Exception {
		try {
			return getDao().findCvcategoryById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvcategoryById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>Cvcategory</code> entity.
	 */
	public List<Cvcategory> findAllCvcategories() throws Exception {
		try {
			return getDao().findAllCvcategories();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvcategories failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<Cvcategory> findCvcategoriesByExample(Cvcategory cvcategory) throws Exception {
		try {
			return getDao().findByExample(cvcategory);
		} catch (RuntimeException e) {
			throw new Exception("findCvcategoriesByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvcategory(Cvcategory cvcategory) throws Exception {
		try {
			getDao().persistCvcategory(cvcategory);
		} catch (RuntimeException e) {
			throw new Exception("persistCvcategory failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvcategory(Cvcategory cvcategory) throws Exception {
		try {
			getDao().removeCvcategory(cvcategory);
		} catch (RuntimeException e) {
			throw new Exception("removeCvcategory failed: " + e.getMessage());
		}
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Cvchannel findCvchannelById(Long id) throws Exception {
		try {
			Cvchannel channel = getDao().findCvchannelById(id);
			if(channel!=null){
				Set<Cvpack> packs = new HashSet<Cvpack>(getDao().findCvpacksByChannelID(channel.getIdchannel()));
				channel.setCvchannelPacks(packs);
			}

			return channel;
		} catch (RuntimeException e) {
			throw new Exception("findCvchannelById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>Cvchannel</code> entity.
	 */
	public List<Cvchannel> findAllCvchannels() throws Exception {
		try {
			List<Cvchannel> canales =  getDao().findAllCvchannels();
			
			for (Cvchannel canal : canales) {
				canal.setCvchannelPacks(new HashSet<Cvpack>(getDao().findCvpacksByChannelID(canal.getIdchannel())));
			}
			
			return canales;
		} catch (RuntimeException e) {
			throw new Exception("findAllCvchannels failed: " + e.getMessage());
		}
	}

	public List<CvchannelPack> findAllCvchannelPacksByIdChannel(Long idChannel) {
		return getDao().findAllCvchannelPacksByIdChannel(idChannel);
	}

	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<Cvchannel> findCvchannelsByExample(Cvchannel cvchannel) throws Exception {
		try {
			return getDao().findByExample(cvchannel);
		} catch (RuntimeException e) {
			throw new Exception("findCvchannelsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvchannel(Cvchannel cvchannel) throws Exception {
		try {
			getDao().persistCvchannel(cvchannel);
		} catch (RuntimeException e) {
			throw new Exception("persistCvchannel failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvchannel(Cvchannel cvchannel) throws Exception {
		try {
			getDao().removeCvchannel(cvchannel);
		} catch (RuntimeException e) {
			throw new Exception("removeCvchannel failed: " + e.getMessage());
		}
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvchannelPack findCvchannelPackById(com.cablevision.vo.CvchannelPackPK id) throws Exception {
		try {
			return getDao().findCvchannelPackById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvchannelPackById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvchannelPack</code> entity.
	 */
	public List<CvchannelPack> findAllCvchannelPacks() throws Exception {
		try {
			return getDao().findAllCvchannelPacks();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvchannelPacks failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvchannelPack> findCvchannelPacksByExample(CvchannelPack cvchannelPack) throws Exception {
		try {
			return getDao().findByExample(cvchannelPack);
		} catch (RuntimeException e) {
			throw new Exception("findCvchannelPacksByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvchannelPack(CvchannelPack cvchannelPack) throws Exception {
		try {
			getDao().persistCvchannelPack(cvchannelPack);
		} catch (RuntimeException e) {
			throw new Exception("persistCvchannelPack failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvchannelPack(CvchannelPack cvchannelPack) throws Exception {
		try {
			getDao().removeCvchannelPack(cvchannelPack);
		} catch (RuntimeException e) {
			throw new Exception("removeCvchannelPack failed: " + e.getMessage());
		}
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public Cvpack findCvpackById(Long id) throws Exception {
		try {
			return getDao().findCvpackById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvpackById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>Cvpack</code> entity.
	 */
	public List<Cvpack> findAllCvpacks() throws Exception {
		try {
			return getDao().findAllCvpacks();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvpacks failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<Cvpack> findCvpacksByExample(Cvpack cvpack) throws Exception {
		try {
			return getDao().findByExample(cvpack);
		} catch (RuntimeException e) {
			throw new Exception("findCvpacksByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvpack(Cvpack cvpack) throws Exception {
		try {
			getDao().persistCvpack(cvpack);
		} catch (RuntimeException e) {
			throw new Exception("persistCvpack failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvpack(Cvpack cvpack) throws Exception {
		try {
			getDao().removeCvpack(cvpack);
		} catch (RuntimeException e) {
			throw new Exception("removeCvpack failed: " + e.getMessage());
		}
	}

	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(IChannelDao dao) {
		this.dao = dao;
	}
	public IChannelDao getDao() {
		return this.dao;
	}

	public List <Cvpack> findCvpacksByChannelID(Long idChannel)throws Exception{
		return getDao().findCvpacksByChannelID(idChannel);
	}

	public List<Cvchannel> findCvChannel(Integer sort, String search, String[] categoryIdsForFilter) {
		List<Long> ids = new ArrayList<Long>();
		if(categoryIdsForFilter != null)
			for(int i=0; i<categoryIdsForFilter.length; i++)
				ids.add(new Long(categoryIdsForFilter[i]));
		List<Cvchannel> canales =  getDao().findCvChannel(sort, search, ids);
		
		for (Cvchannel canal : canales) {
			canal.setCvchannelPacks(new HashSet<Cvpack>(getDao().findCvpacksByChannelID(canal.getIdchannel())));
		}
		
		return canales;
	}
}