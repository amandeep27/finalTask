<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language" value="${langType}" scope="session" />

<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle var="bundle" basename="com.web.bundle.messages" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="${language}">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="css/FontCss.css">
<title><fmt:message key="editInfo" bundle="${bundle}" /></title>
</head>

<body class="main">
	<div class="container">
		<form action="info" method="post">
			<button
				style="float: right; margin-top: -55px; background-color: #D32F2F;"
				type="submit" name="submit" value="logOut">
				<fmt:message key="LogOut" bundle="${bundle}" />
			</button>
		</form>
	</div>
	<FORM action="info" method="post">
		<DIV class="container">
			<p>
				<LABEL><b><fmt:message key="name" bundle="${bundle}" />
						:</b></LABEL> <INPUT type="text" name="uName" pattern="[a-zA-Z ]{4,}"
					value="${uName }"
					oninvalid="this.setCustomValidity('Invalid Name')"
					oninput="setCustomValidity('')" required />
			</p>
			<p>
				<LABEL><b><fmt:message key="oldPassword"
							bundle="${bundle}" /> :</b></LABEL> <INPUT type="password" name="oldpwd"
					pattern="[a-zA-Z0-9@#$%^&+=]{4,}"
					oninvalid="this.setCustomValidity('Invalid Password')"
					oninput="setCustomValidity('')" required />
			</p>
			<p>
				<LABEL><b><fmt:message key="Password" bundle="${bundle}" />
						:</b></LABEL> <INPUT type="password" name="pwd" id="pwd"
					pattern="[a-zA-Z0-9@#$%^&+=]{4,}"
					oninvalid="this.setCustomValidity('Invalid Password')"
					oninput="setCustomValidity('')" required />
			</p>
			<p>
				<LABEL><b><fmt:message key="repeatPassword"
							bundle="${bundle}" /> :</b></LABEL> <INPUT type="password" name="pwd-repeat"
					id="pwd-repeat" pattern="[a-zA-Z0-9@#$%^&+=]{4,}"
					oninvalid="this.setCustomValidity('Invalid Password')"
					oninput="setCustomValidity('')" required />
			</p>
			<p>
				<BUTTON type="submit" name="submit" value="editInfo">
					<fmt:message key="edit" bundle="${bundle}" />
				</BUTTON>
			</p>
			<strong style="color: red"><c:out value="${editInfoError}"></c:out></strong>
		</DIV>
	</FORM>
</body>

</html>