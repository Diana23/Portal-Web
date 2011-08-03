package com.cablevision.util;

import java.util.Hashtable;import javax.naming.Context;import javax.naming.NamingEnumeration;import javax.naming.directory.Attributes;import javax.naming.directory.BasicAttribute;import javax.naming.directory.BasicAttributes;import javax.naming.directory.DirContext;import javax.naming.directory.InitialDirContext;import javax.naming.directory.SearchResult;


@SuppressWarnings("unchecked")
public class LdapUtil {
	
	public static boolean existUserInLdap(String userId) {		// Set up the environment for creating the initial context		Hashtable env = new Hashtable(11);		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");		env.put(Context.PROVIDER_URL, ConfigurationHelper.getPropiedad("ldap.url"));
		boolean valid = false;
		try {			// Create initial context			DirContext ctx = new InitialDirContext(env);
			// Specify the ids of the attributes to return			String[] attrIDs = {ConfigurationHelper.getPropiedad("ldap.useridAttribute")};
			// Specify the attributes to match			// Ask for objects that have the attribute 			Attributes matchAttrs = new BasicAttributes(true); // ignore case			matchAttrs.put(new BasicAttribute(ConfigurationHelper.getPropiedad("ldap.useridAttribute"), userId));
			// Search for objects that have those matching attributes			NamingEnumeration answer = ctx.search(ConfigurationHelper.getPropiedad("ldap.contextName"), matchAttrs, attrIDs);
			valid = answer.hasMoreElements();
			// Close the context when we're done			ctx.close();
		} catch (Exception e) {			e.printStackTrace();		}
		return valid;
	}		public static String getMail(String userId) {		// Set up the environment for creating the initial context		Hashtable env = new Hashtable(11);		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");		env.put(Context.PROVIDER_URL, ConfigurationHelper.getPropiedad("ldap.url"));		String mail = null;		try {			// Create initial context			DirContext ctx = new InitialDirContext(env);			// Specify the ids of the attributes to return			String[] attrIDs = {ConfigurationHelper.getPropiedad("ldap.useridAttribute"),"mail"};			// Specify the attributes to match			// Ask for objects that have the attribute 			Attributes matchAttrs = new BasicAttributes(true); // ignore case			matchAttrs.put(new BasicAttribute(ConfigurationHelper.getPropiedad("ldap.useridAttribute"), userId));			// Search for objects that have those matching attributes			NamingEnumeration<SearchResult> answer = ctx.search(ConfigurationHelper.getPropiedad("ldap.contextName"), matchAttrs, attrIDs);			if(answer.hasMoreElements()){				SearchResult element = answer.next();				if(element.getAttributes().get("mail")!=null)				mail = (String)element.getAttributes().get("mail").get();			}			// Close the context when we're done			ctx.close();		} catch (Exception e) {			e.printStackTrace();		}		return mail;	}
	
	public static void main(String[] args) {
		getMail("luishpm");
	}
}