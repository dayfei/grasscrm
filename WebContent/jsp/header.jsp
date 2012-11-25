<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div id="top-bar">
  <div id="left-side">
    <a href="#">
      <img src="../../images/grass_logo.png" alt="Grass Logo" height="25px" width="80px"/>
    </a>
  </div>
  <div id="right-side">
	Welcome,
	<strong>
	   <security:authentication property="principal.username"/>
	</strong>
	 <a id="logout_link" href="<c:url value="/j_spring_security_logout"/>">[Log Out]</a>	
   </div>
</div>