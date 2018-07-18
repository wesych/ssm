<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" scope="request">${pageContext.request.contextPath}</c:set>
<%
    response.setHeader("Cache-Control","no-catch");
    response.setHeader("Pragma","no-catch");
    response.setDateHeader("Expires",0);
%>