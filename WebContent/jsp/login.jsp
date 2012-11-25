<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" type="image/ico" href="/images/favicon.ico" />
<title>Login</title>
<link href="../css/styles.css" type="text/css" media="screen"
	rel="stylesheet" />
<link href="../css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript"
	src="../js/jquery.keyboard.extension-typing.js"></script>
<script type="text/javascript" src="../js/jquery.keyboard.js"></script>
</head>
<body id="login" onload="document.f.j_username.focus();">
	<div id="wrappertop"></div>
	<div id="wrapper">
		<div id="content">
			<div id="header">
				<h1>
					<a href=""> <!--؅׃Logo <img src="logo.png"   height="50"  width="100"  alt="logo">--></a>
				</h1>
				<c:if test="${not empty param.login_error}">
				    <br />
					<font color="red"><s:text name='error.login.info'/><br /> <s:text name='error.login.reason'/>: <c:out
							value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.
					</font>
				</c:if>
			</div>
			<div id="darkbanner" class="banner320">
				<h2>Login System</h2>
			</div>
			<div id="darkbannerwrap"></div>
			<form name="f" action="<c:url value='/j_spring_security_check'/>"
				method="POST">
				<fieldset class="form">
					<p>
						<label class="loginlabel" for="j_username"><s:text name='login.username.label'/>:</label> <input
							class="logininput ui-keyboard-input ui-widget-content ui-corner-all"
							id="j_username" name="j_username" type="text"
							value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' />
					</p>
					<p>
						<label class="loginlabel" for="j_password"> <s:text name='login.password.label'/>:</label> <span>
							<input class="logininput" name="j_password" id="j_password" type="password" onkeypress="keypress();"/>
						</span>
					</p>
					<button id="loginbtn" type="button" class="positive" name="Submit"
						type="submit" onclick="f.submit();">
						<img src="../images/key.png" alt="Announcement" /><s:text name='login.button.label'/>
					</button>
				</fieldset>
			</form>
		</div>
	</div>
	<div id="wrapperbottom_branding">
		<div id="wrapperbottom_branding_text">
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#j_password').keyboard({
				openOn : null,
				stayOpen : true,
				layout : 'qwerty'
			}).addTyping();

			$(".logininput").blur(function() {
				if ($(this).val() == "") {
					$(this).css("border-color", "red");
				} else
					$(this).css("border-color", "#D9D6C4");
			})
		});

		function keypress(){
			var ev=window.event.keyCode;
			if(ev == 13){
				f.submit();
			}
		}
	</script>
</body>
</html>
