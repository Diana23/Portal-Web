package com.cablevision.portal.sitemap.impl;

import java.util.Iterator;

import com.bea.json.JSONException;
import com.bea.json.JSONObject;
import com.cablevision.portal.sitemap.Site;

/**
 * Contains information about a single site, i.e. a book or page. This is
 * an immutable class.
 */
public class SiteImpl implements Site {

	private static final long serialVersionUID = 1L;

	private Site[] children = null;

	private String label;

	private String title;

	private String url;
	
	private boolean hidden;
	
	private boolean book;
	
	private String defaultPage;
	
	private String activeImage;
	
	private String target;
	
	private Site parent;
	
	private JSONObject properties;
	
	public SiteImpl(String title, String activeImage, String label, String url, String target,
			Site[] children,boolean hidden, boolean book, String defaultPage, JSONObject properties) { 
		this.title = title;
		this.activeImage = activeImage;
		this.label = label;
		this.url = url;
		this.target = target;
		this.children = children;
		this.hidden = hidden;
		this.book = book;
		this.defaultPage = defaultPage;
		this.properties = properties;
	}
	
	public SiteImpl(String title, String activeImage, String label, String url, String target,
			boolean hidden, boolean book, String defaultPage, JSONObject properties, Site parent) { 
		this.title = title;
		this.activeImage = activeImage;
		this.label = label;
		this.url = url;
		this.target = target;
		this.hidden = hidden;
		this.book = book;
		this.defaultPage = defaultPage;
		this.properties = properties;
		this.parent = parent;
	}

	/**
	 * Return a list of children that belong to this site. The
	 * iterator is immutable and calling the remove method will
	 * result in an exception.
	 */
	public Iterator<Site> getChildren() {
		return new ArrayIterator<Site>(children);
	}

	/**
	 * Return the definition label for the book or page.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Return the title for the book or page.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Return the URL for the book or page.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Returns true if this is a book. 
	 */
	public boolean isBook() {
		return book;
	}

	public boolean isHidden() {
		return hidden;
	}

	public String getActiveImage() {
		return activeImage;
	}

	public String getTarget() {
		return (target==null?"_self":target);
	}

	@Override
	public JSONObject getProperties() {
		return properties;
	}

	@Override
	public String getProperty(String key) {
		try {
			if(properties != null)
				return properties.getString(key);
		} catch (JSONException  e) {
		}
		return null;
	}

	@Override
	public int getChildrenSize() {
		return children.length;
	}

	@Override
	public String getDefaultPage() {
		if(book)
			return defaultPage;
		return null;
	}

	@Override
	public void setProperty(String key, Object value) throws JSONException {
		if(properties == null) {
			properties = new JSONObject("{" + key + ":" + value + "}");
		} else 
			properties.put(key, value);
	}

	@Override
	public Site getParent() {
		return parent;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}
}
