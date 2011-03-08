package com.cablevision.portal.sitemap.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bea.json.JSONException;
import com.bea.json.JSONObject;
import com.bea.netuix.servlets.controls.application.DesktopPresentationContext;
import com.bea.netuix.servlets.controls.page.BookPresentationContext;
import com.bea.netuix.servlets.controls.page.PagePresentationContext;
import com.bea.portal.tools.util.StringUtilities;
import com.bea.portlet.GenericURL;
import com.bea.portlet.PageURL;
import com.cablevision.portal.sitemap.Site;
import com.cablevision.portal.sitemap.SiteMap;
import com.cablevision.portal.sitemap.SiteMapException;
import com.cablevision.portal.sitemap.SiteMapFactory;

/**
 * A sample factory for building a sitemap based on
 * presentation contexts. This factory works well
 * when tree optimization is turned off however it
 * will give incomplete results when tree optimization is
 * on since only the partial portal control tree is available. 
 */
public class ContextSiteMapFactory implements SiteMapFactory {

	public SiteMap getSiteMap(HttpServletRequest request,
			HttpServletResponse response, boolean returnHidden) throws SiteMapException {
		List<Site> sites = new ArrayList<Site>();
		DesktopPresentationContext dpc = DesktopPresentationContext
		.getDesktopPresentationContext(request);
		BookPresentationContext bpc = dpc.getBookPresentationContext();
		getSites(request, response, bpc, sites,returnHidden, null);

		return new SiteMapImpl(sites.toArray(new Site[sites.size()]));
	}

	public SiteMap getSiteMapFromCurrentPage(HttpServletRequest request,
			HttpServletResponse response, boolean returnHidden, String definitionLabel) throws SiteMapException {
		List<Site> sites = new ArrayList<Site>();
		DesktopPresentationContext dpc = DesktopPresentationContext
		.getDesktopPresentationContext(request);
		if(StringUtilities.isNullOrEmpty(definitionLabel)) {
			definitionLabel = dpc.getBookPresentationContext().getDefaultPage();
		}

		PagePresentationContext currentpc =	dpc.getPagePresentationContextRecursive(definitionLabel);

		BookPresentationContext parentbpc = null;
		if(currentpc==null){
			currentpc =	dpc.getDisplayedPagePresentationContext();
		}
		if(currentpc.getParentBookPresentationContext() != null) {
			parentbpc = currentpc.getParentBookPresentationContext();
			if(parentbpc.getParentBookPresentationContext() != null && (parentbpc.getProperty("mainParent") == null ||  
					(parentbpc.getProperty("mainParent") != null && 
							!parentbpc.getPropertyAsBoolean("mainParent",true)))) {
				parentbpc = parentbpc.getParentBookPresentationContext();
			}
		}
		getSites(request, response, parentbpc, sites,returnHidden, definitionLabel);

		return new SiteMapImpl(sites.toArray(new Site[sites.size()]));
	}

	public SiteMap getBreadcrumbSites(HttpServletRequest request,
			HttpServletResponse response, boolean returnHidden, String currentPageDefinitionLabel) {
		DesktopPresentationContext dpc = DesktopPresentationContext
		.getDesktopPresentationContext(request);
		PagePresentationContext currentpc = dpc.getPagePresentationContextRecursive(currentPageDefinitionLabel);
		Site site = toSite(currentpc, request, response, returnHidden);
		return new SiteMapImpl(new Site[]{site});
	}

	private Site toSite(PagePresentationContext ppc, HttpServletRequest request,
			HttpServletResponse response, boolean returnHidden) {
		String defaultPage = null;
		
		if(ppc == null){
			DesktopPresentationContext dpc = DesktopPresentationContext
			.getDesktopPresentationContext(request);
			ppc = dpc.getDisplayedPagePresentationContext();
		}if (ppc instanceof BookPresentationContext) {
			defaultPage = ((BookPresentationContext)ppc).getDefaultPage();
		}

		PageURL defaultPageURL = null;
		if(defaultPage != null) {
			defaultPageURL = PageURL.createPageURL(request, response, defaultPage);
			defaultPageURL.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
			defaultPageURL.setEncodeSession(false);
			defaultPage = defaultPageURL.toString();
		}

		PageURL url = PageURL.createPageURL(request, response, ppc.getLabel());
		url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		url.setEncodeSession(false);

		if(ppc.getProperty("secure")!=null && "true".equals(ppc.getProperty("secure"))){
			url.setTemplate("https");
		}
		
		Site site;
		String link = url.toString();
		if(ppc.getProperty("link") != null) {
			link = ppc.getProperty("link");
		}
		String properties = ppc.getProperties();
		JSONObject json = null;
		try {
			if(properties != null)
				json = new JSONObject("{"+properties+"}");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		BookPresentationContext parent = ppc.getParentBookPresentationContext();
		Site parentSite = null;
		if(parent instanceof BookPresentationContext) {
			parentSite = toSite(parent, request, response, returnHidden);
		}
		site = new SiteImpl(ppc.getTitle(),ppc.getActiveImage(), ppc.getLabel(),
				link, ppc.getProperty("target"),
				ppc.isHidden(),(ppc instanceof BookPresentationContext), 
				defaultPage, json, parentSite);

		return site;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private void getSites(HttpServletRequest request,
			HttpServletResponse response, BookPresentationContext context,
			List<Site> sites, boolean returnHidden, String currentPageDefinitionLabel) {

		List children;

		if(returnHidden){
			//We are using a deprecated method because we need the hidden pages and book too
			children= context.getPagePresentationContexts();
		}else{
			children= context.getEntitledPagePresentationContexts();
		}

		for (int i = 0; i < children.size(); i++) {
			PagePresentationContext child = (PagePresentationContext) children
			.get(i);

			List<Site> childSites = new ArrayList<Site>();
			String defaultPage = null;
			if (child instanceof BookPresentationContext) {
				defaultPage = ((BookPresentationContext)child).getDefaultPage();
				childSites = new ArrayList<Site>();
				getSites(request, response, (BookPresentationContext) child,
						childSites,returnHidden, currentPageDefinitionLabel);
			}

			PageURL defaultPageURL = null;
			if(defaultPage != null) {
				defaultPageURL = PageURL.createPageURL(request, response, defaultPage);
				defaultPageURL.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
				defaultPageURL.setEncodeSession(false);
				defaultPage = defaultPageURL.toString();
			}

			PageURL url = PageURL.createPageURL(request, response, child.getLabel());
			url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
			url.setEncodeSession(false);
			
			if(child.getProperty("secure")!=null && "true".equals(child.getProperty("secure"))){
				url.setTemplate("https");
			}

			Site site;
			String link = url.toString();
			if(child.getProperty("link") != null) {
				link = child.getProperty("link");
			}
			String properties = child.getProperties();
			JSONObject json = null;
			try {
				if(properties != null)
					json = new JSONObject("{"+properties+"}");
			} catch (JSONException e) {
			}

			site = new SiteImpl(child.getTitle(),child.getActiveImage(), child.getLabel(),
					link, child.getProperty("target"),
					childSites.toArray(new Site[childSites.size()]),child.isHidden(),(child instanceof BookPresentationContext), defaultPage, json);

			try {
				if(currentPageDefinitionLabel != null && child.getLabel().equalsIgnoreCase(currentPageDefinitionLabel)) {
					site.setProperty("selected", true);
				} else if(currentPageDefinitionLabel != null && child.getPagePresentationContextRecursive(currentPageDefinitionLabel) != null) {
					site.setProperty("selected", true);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			sites.add(site);
		}
	}

}
