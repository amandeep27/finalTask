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
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/FontCss.css">
<title><fmt:message key="editInfo" bundle="${bundle}" /></title>
</head>

<body class="editInfoBody">
	<div class="container">
		<form action="info" method="post" class="form-horizontal">
			<div class="form-group last">
				<div class="col-sm-offset-11 col-sm-1">
					<button class="btn btn-success btn-sm" type="submit" name="submit"
						value="logOut">
						<fmt:message key="LogOut" bundle="${bundle}" />
					</button>
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col-md-8">
				<div class="panel panel-default">
					<div class="panel-body">
						<FORM action="info" method="post" class="form-horizontal">
							<div class="form-group">
								<LABEL for="uName" class="col-sm-3 control-label"><b><fmt:message
											key="name" bundle="${bundle}" /> :</b></LABEL>
								<div class="col-sm-8">
									<INPUT type="text" name="uName" pattern="[a-zA-Z ]{4,}"
										class="form-control" id="uName" value="${uName }"
										oninvalid="this.setCustomValidity('Invalid Name')"
										oninput="setCustomValidity('')" required />
								</div>
							</div>
							<div class="form-group">
								<LABEL for="oldpwd" class="col-sm-3 control-label"><b><fmt:message
											key="oldPassword" bundle="${bundle}" /> :</b></LABEL>
								<div class="col-sm-8">
									<INPUT type="password" name="oldpwd" class="form-control"
										id="oldpwd" pattern="[a-zA-Z0-9@#$%^&+=]{4,}"
										oninvalid="this.setCustomValidity('Invalid Password')"
										oninput="setCustomValidity('')" required />
								</div>
							</div>
							<div class="form-group">
								<LABEL for="pwd" class="col-sm-3 control-label"><b><fmt:message
											key="Password" bundle="${bundle}" /> :</b></LABEL>
								<div class="col-sm-8">
									<INPUT type="password" name="pwd" id="pwd" class="form-control"
										pattern="[a-zA-Z0-9@#$%^&+=]{4,}"
										oninvalid="this.setCustomValidity('Invalid Password')"
										oninput="setCustomValidity('')" required />
								</div>
							</div>
							<div class="form-group">
								<LABEL for="pwd-repeat" class="col-sm-3 control-label"><b><fmt:message
											key="repeatPassword" bundle="${bundle}" /> :</b></LABEL>
								<div class="col-sm-8">
									<INPUT type="password" name="pwd-repeat" class="form-control"
										id="pwd-repeat" id="pwd-repeat"
										pattern="[a-zA-Z0-9@#$%^&+=]{4,}"
										oninvalid="this.setCustomValidity('Invalid Password')"
										oninput="setCustomValidity('')" required />
								</div>
							</div>
							<div class="form-group last">
								<div class="col-sm-offset-3 col-sm-8">
									<BUTTON type="submit" name="submit" value="editInfo"
										class="btn btn-success btn-sm">
										<fmt:message key="edit" bundle="${bundle}" />
									</BUTTON>
								</div>
							</div>
							<strong style="color: red"><c:out
									value="${editInfoError}"></c:out></strong>
						</FORM>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>

</html>