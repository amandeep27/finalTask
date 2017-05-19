<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language" value="${langType }" scope="session" />

<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle var="bundle" basename="com.web.bundle.messages" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="${language}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">
.main {
	border: 3px solid #f1f1f1;
}

.container {
	padding: 16px;
}

button {
	background-color: #D32F2F;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta Http-Equiv="Cache-Control" Content="no-cache">
<meta Http-Equiv="Pragma" Content="no-cache">
<meta Http-Equiv="Expires" Content="0">
<title><fmt:message key="HomePage" bundle="${bundle}" /></title>
</head>
<body class="main">
	<c:if test="${!empty sessionScope.uName}">
		<form action="info" method="post">
			<div>
				<LABEL><b><fmt:message key="language" bundle="${bundle}" />
						: </b></LABEL> <SELECT name="lang">
					<OPTION value="en" selected><fmt:message key="english"
							bundle="${bundle}" /></OPTION>
					<OPTION value="be"><fmt:message key="belarusian"
							bundle="${bundle}" /></OPTION>
				</SELECT>
				<button type="submit" name="submit" value="changeLanguage">
					<fmt:message key="changeLanguage" bundle="${bundle}" />
				</button>
			</div>
		</form>
		<form action="info" method="post">
			<button
				style="float: right; margin-top: -55px; background-color: #D32F2F;"
				type="submit" name="submit" value="logOut">
				<fmt:message key="LogOut" bundle="${bundle}" />
			</button>
		</form>
		<div class="container">
			<fmt:message key="hello" bundle="${bundle}" />
			<c:out value=", ${sessionScope.uName}"></c:out>
		</div>


	</c:if>
	<c:if test="${empty sessionScope.uName}">
		<c:redirect url="login.jsp"></c:redirect>
	</c:if>
</body>
</html>