<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="jstl-c"%>

<netui:html>
    <head>
        <netui:base/>
    </head>
    <netui:body>
        <p class="TituloBusqueda"><jsp:text>${errorMessage}</jsp:text></p>
        
    </netui:body>
</netui:html>