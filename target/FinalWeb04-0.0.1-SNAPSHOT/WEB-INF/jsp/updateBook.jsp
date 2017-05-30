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
<title><fmt:message key="updateBookPage" bundle="${bundle}" /></title>
</head>
<body class="main">
	<c:if test="${!empty sessionScope.uName}">
		<div class="container">
			<form action="info" method="post">
				<button
					style="float: right; margin-top: -55px; background-color: #D32F2F;"
					type="submit" name="submit" value="logOut">
					<fmt:message key="LogOut" bundle="${bundle}" />
				</button>
			</form>
		</div>

		<div class="container">

			<form action="info" method="post">
				<p>
					<LABEL><b><fmt:message key="title" bundle="${bundle}" />
							: </b></LABEL> <input type="text" value="${book.getBooktitle() }"
						name="title" oninvalid="this.setCustomValidity('Invalid Entry')"
						oninput="setCustomValidity('')" required />
				</p>
				<p>
					<LABEL><b><fmt:message key="description"
								bundle="${bundle}" /> : </b></LABEL><INPUT type="text" name="description"
						value="${book.getDescription() }"
						oninvalid="this.setCustomValidity('Invalid Entry')"
						oninput="setCustomValidity('')" required />
				</p>
				<p>
					<LABEL><b><fmt:message key="publishingYear"
								bundle="${bundle}" /> : </b></LABEL><INPUT type="text" name="year"
						value="${book.getPublishYear() }" pattern="[0-9]{4,4}"
						oninvalid="this.setCustomValidity('Invalid Entry')"
						oninput="setCustomValidity('')" required />
				</p>
				<p>
					<LABEL><b><fmt:message key="author" bundle="${bundle}" />
							: </b></LABEL><INPUT type="text" name="author"
						value="${book.getAuthor().getName() }"
						oninvalid="this.setCustomValidity('Invalid Entry')"
						oninput="setCustomValidity('')" required />
				</p>
				<p>
					<LABEL><b><fmt:message key="type" bundle="${bundle}" />
							: </b></LABEL> <SELECT name="type">
						<OPTION value="e-book" selected><fmt:message key="eBook"
								bundle="${bundle}" /></OPTION>
						<OPTION value="paperBound"><fmt:message key="paperBound"
								bundle="${bundle}" /></OPTION>
					</SELECT>
				</p>
				<BUTTON type="submit" name="submit" value="updateBookInfo">
					<fmt:message key="Go" bundle="${bundle}" />
				</BUTTON>
			</form>

		</div>

	</c:if>
	<c:if test="${empty sessionScope.uName}">
		<c:redirect url="login.jsp"></c:redirect>
	</c:if>
</body>
</html>