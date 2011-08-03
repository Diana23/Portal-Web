package com.cablevision.portal.sitemap.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bea.netuix.servlets.controls.content.backing.AbstractJspBacking;
import com.bea.portlet.GenericURL;
import com.cablevision.portal.sitemap.SiteMapException;
import com.cablevision.portal.sitemap.SiteMapManager;
import com.cablevision.util.PageNewUrl;

/**
 * This backing file is used to ensure that tree optimization is turned off. For
 * the presentation context based site map, tree optimization must be turned off
 * so that a full site map can be generated.
 * <p>
 * To tell the portlet whether tree optimization is on or off we pass a flag to
 * it through a meta data definition in the .portlet file. We could determine
 * this programatically but it is only available in DesktopDefinition which in
 * turn is only available in streaming portals. Since we want this used in dot
 * portal files as well we leave it to the developer to configure to handle both
 * cases.
 */
public class ContextSiteMapBackingFile extends AbstractJspBacking {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean preRender(HttpServletRequest request,
			HttpServletResponse response) {

		SiteMapMetaParameters params = new SiteMapMetaParameters(request);
		try {
			//Check if we need to flush the cache
			if(params.isFlushCache()){
				SiteMapManager.flushCache(request, SiteMapManager.getSiteMapFactory(request));
			}else{
				// If sitemap is already cached no need to consider redirecting to
				// turn off tree optimization.
				if (SiteMapManager.isSiteMapCached(request, params.isReturnHidden())){
					return true;
				}
			}
		} catch (SiteMapException e) {
			e.printStackTrace();
		}

		if (params.isTreeOptimization()
				&& !"false".equals(request
						.getParameter(GenericURL.TREE_OPTIMIZATION_PARAM))) {
			GenericURL url = GenericURL.createGenericURL(request, response);
//			url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
			try {
				response.sendRedirect(url.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
