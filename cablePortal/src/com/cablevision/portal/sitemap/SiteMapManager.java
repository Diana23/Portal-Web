package com.cablevision.portal.sitemap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bea.p13n.cache.Cache;
import com.bea.p13n.cache.CacheFactory;
import com.cablevision.portal.sitemap.impl.SiteMapMetaParameters;

/**
 * Returns a site map for the portlet using the configured site map factory.
 * This manager class caches the sitemap for the set of roles since in theory
 * any user with the same roles should see the same site map.
 * <p>
 * This manager class will also cache the sitemap in either a global Portal
 * cache or the users session. If the Portal desktop is customizable then the
 * sitemap is cached in the user session. This implementation is somewhat naive
 * because not every user will have a customized desktop nor will all parts of
 * the desktop be customizable. Thus users are encourage to tweak how caching is
 * managed in order to best fit their particular scenario. The caching
 * implemented here is a bare bones implementation.
 */
public class SiteMapManager {

	private static final String SITE_MAP_CACHE_NAME = "site.map.cache";


	private static final String SITE_MAP_FACTORY_REQUEST_ATTRIBUTE = "site.map.factory";

	@SuppressWarnings("unchecked")
	public static SiteMap getSiteMap(HttpServletRequest request,
			HttpServletResponse response, boolean returnHidden)
			throws SiteMapException {
		SiteMapFactory factory = getSiteMapFactory(request);
		SiteMap map = getSiteMapFromCache(request, factory,
				returnHidden);

		if (map == null) {
			map = factory.getSiteMap(request, response,returnHidden);
			putSiteMapInCache(request, factory, returnHidden, map);
		}
		return map;
	}
	
	public static SiteMap getSiteMapFromCurrentPage(HttpServletRequest request,
			HttpServletResponse response, boolean returnHidden, String definitionLabel)
			throws SiteMapException {
		SiteMapFactory factory = getSiteMapFactory(request);
		SiteMap map = getSiteMapFromCache(request, factory,
				returnHidden);

//		if (map == null) {
			map = factory.getSiteMapFromCurrentPage(request, response, returnHidden, definitionLabel);
//			putSiteMapInCache(request, factory, returnHidden, map);
//		}
		return map;
	}
	
	public static SiteMap getBreadcrumbSites(HttpServletRequest request,
			HttpServletResponse response, boolean returnHidden, String definitionLabel) 
			throws SiteMapException {
		SiteMapFactory factory = getSiteMapFactory(request);
		SiteMap map = factory.getBreadcrumbSites(request, response, returnHidden, definitionLabel);
		//putSiteMapInCache(request, factory, returnHidden, map);
		return map;
	}

	public static boolean isSiteMapCached(HttpServletRequest request,
			boolean isPortalUserCustomizable) throws SiteMapException {
		return getSiteMapFromCache(request, getSiteMapFactory(request),
				isPortalUserCustomizable) != null;
	}

	public static SiteMapFactory getSiteMapFactory(HttpServletRequest request)
			throws SiteMapException {
		SiteMapFactory factory = (SiteMapFactory) request
				.getAttribute(SITE_MAP_FACTORY_REQUEST_ATTRIBUTE);
		if (factory == null) {
			SiteMapMetaParameters params = new SiteMapMetaParameters(request);
			if (params.getSiteMapFactoryClassName() == null) {
				throw new SiteMapException(
						"No sitemap factory has been configured for this portlet");
			}
			try {
				factory = (SiteMapFactory) Class.forName(
						params.getSiteMapFactoryClassName()).newInstance();
				request.setAttribute(SITE_MAP_FACTORY_REQUEST_ATTRIBUTE,
						factory);
			} catch (Exception e) {
				throw new SiteMapException("Could not instantiate factory '"
						+ params.getSiteMapFactoryClassName() + "'", e);
			}
		}
		return factory;
	}

	private static void putSiteMapInCache(HttpServletRequest request,
			SiteMapFactory factory, boolean returnHidden,
			SiteMap map) throws SiteMapException {
		
			String key = getGlobalCacheKey(request, factory)+returnHidden;
			Cache cache = CacheFactory.getCache(SITE_MAP_CACHE_NAME);
			cache.put(key, map);
	}

	@SuppressWarnings( { "unused", "unchecked" })
	public static SiteMap getSiteMapFromCache(HttpServletRequest request,
			SiteMapFactory factory, boolean returnHidden)
			throws SiteMapException {
		Object result = null;
		String key = getGlobalCacheKey(request, factory)+returnHidden;
		Cache cache = CacheFactory.getCache(SITE_MAP_CACHE_NAME);
		result = cache.get(key);
			// Otherwise pull it from session cache if session is available
		
		// Do instanceof check has SiteMap can give ClassCastException if
		// redployment of web application happens and it was retrieved from
		// global cache. Typically only happens during development.
		if (result instanceof SiteMap)
			return (SiteMap) result;
		else
			return null;
	}
	
	@SuppressWarnings( { "unused", "unchecked" })
	public static void flushCache(HttpServletRequest request,SiteMapFactory factory)
			throws SiteMapException {
		Object result = null;
		String key = getGlobalCacheKey(request, factory);
		CacheFactory.flush(SITE_MAP_CACHE_NAME);
	}

	


	/**
	 * Return the key to use when caching the sitemap in a global portal cache.
	 */
	private static String getGlobalCacheKey(HttpServletRequest request,
			SiteMapFactory factory) throws SiteMapException {
		
		return "CABLEVISION_SITE_MAP";
	}



	
}
