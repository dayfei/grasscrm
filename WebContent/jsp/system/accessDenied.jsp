<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css"
	href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />
<script type="text/javascript" src="../../js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
</head>

<body>
	<div id="page-wrap">

		<s:include value="../header.jsp" />

		<s:include value="../menu.jsp" />

		<div id="feature">
			<div id="feature-title">
				<h2>
					<s:text name="title.access.denied" />
				</h2>
			</div>
			<div id="feature-content">
			  <h2><s:text name="info.access.denied" /></h2>
				
			  <p>
				<%= request.getAttribute("SPRING_SECURITY_403_EXCEPTION")%>
			  </p>
			  <p>
				<%      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				        if (auth != null) { %>
				        Authentication object as a String: <%= auth.toString() %><br /><br />
				<%      } %>
			  </p>
			</div>
		</div>
		<s:include value="../footer.jsp" />

	</div>
</body>
</html>
