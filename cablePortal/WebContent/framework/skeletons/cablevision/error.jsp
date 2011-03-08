<%@ page session="false"%>

<%
    // The cause explains the cause of the error
    Throwable errorCause = (Throwable) request.getAttribute("portlet.error.cause");

    // Error message
    String errorMessage = (String) request.getAttribute("portlet.error.message");

    // Source URI that caused this error
    String sourceUri = (String) request.getAttribute("portlet.error.source.uri");

    if (sourceUri != null)
    {
%>
Error opening <%=sourceUri%>.
<%
    }
%>
<br>
The source of this error is:
<pre><%
    if (errorCause != null) {
        errorCause.printStackTrace(response.getWriter());
    } else {
        out.print(errorMessage);
    }

    Throwable rootCause = null;
    if (errorCause instanceof ServletException)
    {
        rootCause = ((ServletException) errorCause).getRootCause();
        if (rootCause == null)
        {
            rootCause = errorCause.getCause();
        }
    }

    if (rootCause != null)
    {
%></pre>
Caused by:
<pre><%
        rootCause.printStackTrace(response.getWriter());
    }
%></pre>