package com.cablevision.portal.sitemap;

import java.io.Serializable;
import java.util.Iterator;

import com.bea.json.JSONException;
import com.bea.json.JSONObject;

/**
 * Class that contains information about a single
 * site within a sitemap. Typically classes that implement
 * this interface would be immutable.
 */
public interface Site extends Serializable {

	/**
	 * The URL for the site.
	 */
	public String getUrl();
	
	/**
	 * The title of the site.
	 */
	public String getTitle();
	
	public void setTitle(String title);
	
	/**
	 * The book or page definition label of the
	 * site.
	 */
	public String getLabel();
	
	/**
	 * Returns true if this site is a book.
	 */
	public boolean isBook(); 
	
	public String getDefaultPage();
	
	/**
	 * A list of child sites under this site.
	 */
	public Iterator<Site> getChildren();
	
	public int getChildrenSize();
	
	public Site getParent();
	
	public JSONObject getProperties();
	
	public String getProperty(String key);
	
	public void setProperty(String key, Object value) throws JSONException;
	
	/**
	 * A list of child sites under this site.
	 */
	public boolean isHidden();
	
	/**
	 * A string represents the active image path
	 */
	public String getActiveImage();
	
	/**
	 * @return a String representing the target window to display the link
	 * _blank: display the page on a new window or tab
	 * _self: display the page on the same window or tab
	 */
	public String getTarget();
	
}
