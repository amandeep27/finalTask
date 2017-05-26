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
<title><fmt:message key="SignUp" bundle="${bundle}" /></title>
</head>

<body class="main">
	<FORM action="info" method="post">
		<DIV class="container">
			<p>
				<LABEL><b><fmt:message key="UserName" bundle="${bundle}" />
						:</b></LABEL> <INPUT type="text" name="name" pattern="[a-zA-Z ]{4,}"
					oninvalid="this.setCustomValidity('Invalid UserName (min. 4 alphabets)')"
					oninput="setCustomValidity('')" required />
			</p>
			<p>
				<LABEL><b><fmt:message key="name" bundle="${bundle}" />
						:</b></LABEL> <INPUT type="text" name="uName" pattern="[a-zA-Z ]{4,}"
					oninvalid="this.setCustomValidity('Invalid Name (min. 4 alphabets)')"
					oninput="setCustomValidity('')" required />
			</p>
			<p>
				<LABEL><b><fmt:message key="role" bundle="${bundle}" />
						: </b></LABEL>
			</p>
			<INPUT type="radio" name="role" value="User" checked>
			<fmt:message key="user" bundle="${bundle}" />
			<INPUT type="radio" name="role" value="Administrator">
			<fmt:message key="administrator" bundle="${bundle}" />

			<p>
				<LABEL><b><fmt:message key="language" bundle="${bundle}" />
						: </b></LABEL> <SELECT name="lang">
					<OPTION value="en" selected><fmt:message key="english"
							bundle="${bundle}" /></OPTION>
					<OPTION value="be"><fmt:message key="belarusian"
							bundle="${bundle}" /></OPTION>
				</SELECT>
			</p>

			<p>
				<LABEL><b><fmt:message key="Password" bundle="${bundle}" />
						:</b></LABEL> <INPUT type="password" name="pwd"
					pattern="[a-zA-Z0-9@#$%^&+=]{4,}"
					oninvalid="this.setCustomValidity('Invalid Password (min. 4 characters)')"
					oninput="setCustomValidity('')" required />
			</p>
			<p>
				<LABEL><b><fmt:message key="repeatPassword"
							bundle="${bundle}" /> :</b></LABEL> <INPUT type="password" name="pwd-repeat"
					pattern="[a-zA-Z0-9@#$%^&+=]{4,}"
					oninvalid="this.setCustomValidity('Invalid Password (min. 4 characters)')"
					oninput="setCustomValidity('')" required />
			</p>
			<p>
				<BUTTON type="submit" name="submit" value="SignUp">
					<fmt:message key="SignUp" bundle="${bundle}" />
				</BUTTON>
			</p>

			<strong style="color: red"><c:out value="${signUpError}"></c:out></strong>

		</DIV>
		<div style="font-size: 14px">
			<c:set var="language" value="en_US" scope="session" />
			<fmt:setLocale value="${language}" scope="session" />
			<fmt:setBundle var="bundle" basename="com.web.bundle.messages" />
			<a href="info?newpage=signup&langType=en_US&submit=changePageLang">
				<fmt:message key="english" bundle="${bundle}" />
			</a>&nbsp;
			<c:set var="language" value="be_BY" scope="session" />
			<fmt:setLocale value="${language}" scope="session" />
			<fmt:setBundle var="bundle" basename="com.web.bundle.messages" />
			<a href="info?newpage=signup&langType=be_BY&submit=changePageLang">
				<fmt:message key="belarusian" bundle="${bundle}" />
			</a>
		</div>
	</FORM>
</body>

</html>