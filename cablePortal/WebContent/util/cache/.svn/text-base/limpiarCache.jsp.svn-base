<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="com.bea.p13n.cache.Cache"%>
<%@page import="com.bea.p13n.cache.CacheFactory"%>

<%
	String[] cacheNames = CacheFactory.getCacheNames();
	for(int i=0; i<cacheNames.length; i++){
		if(CacheFactory.cacheExists(cacheNames[i])){
			System.out.println("limpiando cache "+cacheNames[i]+" ...");
			CacheFactory.flush(cacheNames[i]);
		}
	}
%>

<h2><%out.print("Cache Limpio");%></h2>
    