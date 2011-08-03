package com.cablevision.portal.sitemap.impl;

import javax.servlet.http.HttpServletRequest;

import com.bea.netuix.servlets.controls.application.MetaData;
import com.bea.netuix.servlets.controls.portlet.PortletPresentationContext;
import com.bea.netuix.servlets.controls.portlet.backing.PortletBackingContext;

/**
 * Utility class for extracting sitemap meta parameters from
 * portlet definition.
 */
public class SiteMapMetaParameters {

	public final static String SITE_MAP_FACTORY_PARAMETER_NAME = "sitemap.factory";

	public final static String TREE_OPTIMIZATION_PARAMETER_NAME = "tree.optimization";

	public final static String RETURN_HIDDEN_PARAMETER_NAME = "map.returnHidden";
	
	public final static String FLUSH_CACHE_PARAMETER_NAME = "cache.flush";

	private boolean returnHidden;

	private boolean treeOptimization;
	
	private boolean flushCache=false;

	private String siteMapFactory;

	public SiteMapMetaParameters(HttpServletRequest request) {
		initValues(request);
	}
	
	public SiteMapMetaParameters(boolean returnHidden,boolean treeOptimization,boolean flushCache,String siteMapFactory){
		this.returnHidden = returnHidden;
		this.treeOptimization = treeOptimization;
		this.flushCache = flushCache;
		this.siteMapFactory = siteMapFactory;
	}

	public boolean isReturnHidden() {
		return returnHidden;
	}

	public boolean isTreeOptimization() {
		return treeOptimization;
	}

	public String getSiteMapFactoryClassName() {
		return siteMapFactory;
	}
	
	public boolean isFlushCache() {
		return flushCache;
	}

	private MetaData getMetaData(HttpServletRequest request, String name) {
		PortletPresentationContext ppc = PortletPresentationContext
				.getPortletPresentationContext(request);
		if (ppc != null)
			return ppc.getMetaData(name);

		PortletBackingContext pbc = PortletBackingContext
				.getPortletBackingContext(request);
		if (pbc != null)
			return pbc.getMetaData(name);

		return null;
	}

	private String getMetaDataValue(HttpServletRequest request, String name) {
		String result = null;
		MetaData data = getMetaData(request, name);
		if (data != null && data.getContents().length > 0) {
			result = data.getContents()[0];
		}
		return result;
	}

	private boolean getMetaDataBooleanValue(HttpServletRequest request,
			String name) {
		String result = getMetaDataValue(request, name);
		if (result == null)
			return false;
		else
			return Boolean.valueOf(result);
	}

	private void initValues(HttpServletRequest request) {
		siteMapFactory = getMetaDataValue(request,
				SITE_MAP_FACTORY_PARAMETER_NAME);
		treeOptimization = getMetaDataBooleanValue(request,
				TREE_OPTIMIZATION_PARAMETER_NAME);
		returnHidden = getMetaDataBooleanValue(request,
				RETURN_HIDDEN_PARAMETER_NAME);
		flushCache = getMetaDataBooleanValue(request,
				FLUSH_CACHE_PARAMETER_NAME);

	}

	
}
