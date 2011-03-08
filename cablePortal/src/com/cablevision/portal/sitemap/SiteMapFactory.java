package com.cablevision.portal.sitemap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An interface that defines a factory for building a sitemap.
 */
public interface SiteMapFactory {

	/**
	 * Returns the sitemap for the current request.
	 * @param request
	 * @param response
	 * @return A list of sites.
	 * @throws SiteMapException
	 */
	public SiteMap getSiteMap(HttpServletRequest request, HttpServletResponse response,boolean returnHidden) throws SiteMapException;
	
	public SiteMap getSiteMapFromCurrentPage(HttpServletRequest request, HttpServletResponse response,boolean returnHidden, String definitionLabel) throws SiteMapException;
	
	public SiteMap getBreadcrumbSites(HttpServletRequest request,
			HttpServletResponse response, boolean returnHidden, String currentPageDefinitionLabel);
}
