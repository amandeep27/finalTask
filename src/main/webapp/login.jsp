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
<link rel="stylesheet"
	href="css/FontCss.css">
<title><fmt:message key="LoginPage" bundle="${bundle}" /></title>
</head>

<body class="main">
	<FORM action="info" method="post">
		<DIV class="container">

			<p>
				<LABEL><b><fmt:message key="UserId" bundle="${bundle}" />
						:</b></LABEL> <INPUT type="text" name="uId" pattern="[0-9]{6,}"
					oninvalid="this.setCustomValidity('Invalid UserId (min. 6 numbers)')"
					oninput="setCustomValidity('')" required />
			</p>
			<p>
				<LABEL><b><fmt:message key="Password" bundle="${bundle}" />
						:</b></LABEL> <INPUT type="password" name="pwd"
					pattern="[a-zA-Z0-9@#$%^&+=]{4,}"
					oninvalid="this.setCustomValidity('Invalid Password (min. 4 characters)')"
					oninput="setCustomValidity('')" required />
			</p>
			<c:if test="${!validUser}">
				<strong style="color: red"><c:out value="${error}"></c:out></strong>
			</c:if>
			<p>
				<BUTTON type="submit" name="submit" value="SignIn">
					<fmt:message key="SignIn" bundle="${bundle}" />
				</BUTTON>
			</p>

			<div style="font-size: 14px">
				<c:set var="language" value="en_US" scope="session" />
				<fmt:setLocale value="${language}" scope="session" />
				<fmt:setBundle var="bundle" basename="com.web.bundle.messages" />
				<a href="info?langType=en_US&submit=changePageLang"> <fmt:message
						key="english" bundle="${bundle}" />
				</a>&nbsp;
				<c:set var="language" value="be_BY" scope="session" />
				<fmt:setLocale value="${language}" scope="session" />
				<fmt:setBundle var="bundle" basename="com.web.bundle.messages" />
				<a href="info?langType=be_BY&submit=changePageLang"> <fmt:message
						key="belarusian" bundle="${bundle}" />
				</a>
			</div>

		</DIV>

	</FORM>
	<c:set var="language" value="${langType}" scope="session" />

	<fmt:setLocale value="${language}" scope="session" />
	<fmt:setBundle var="bundle" basename="com.web.bundle.messages" />
	<div class="container">
		<a href="signUp.jsp"> <fmt:message key="NoAccount"
				bundle="${bundle}" />
		</a>
	</div>

</body>

</html>
