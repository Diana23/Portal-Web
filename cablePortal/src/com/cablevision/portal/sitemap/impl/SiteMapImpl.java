package com.cablevision.portal.sitemap.impl;

import java.util.Iterator;

import com.cablevision.portal.sitemap.Site;
import com.cablevision.portal.sitemap.SiteMap;

/**
 * A class that represents a sitemap for a single
 * desktop. This class is immutable.
 */
public class SiteMapImpl implements SiteMap {

	private static final long serialVersionUID = 1L;

	private Site[] sites = null;

	public SiteMapImpl(Site[] sites) {
		this.sites = sites;
	}
	
	public Iterator<Site> getSites() {
		return new ArrayIterator<Site>(sites);
	}

}
