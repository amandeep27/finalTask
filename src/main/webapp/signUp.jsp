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
<title><fmt:message key="SignUp" bundle="${bundle}" /></title>
</head>

<body class="SignUpBody">
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-7">
				<div class="panel panel-default">
					<div class="panel-heading">
						<strong class=""><fmt:message key="SignUp"
								bundle="${bundle}" /></strong>

					</div>
					<div class="panel-body">
						<FORM class="form-horizontal" role="form" action="info"
							method="post">
							<div class="form-group">
								<label for="userName" class="col-sm-3 control-label"><b><fmt:message
											key="UserName" bundle="${bundle}" />:</b></label>
								<div class="col-sm-12">
									<input type="text" name="name" class="form-control" id="name"
										placeholder=<fmt:message
											key="UserName" bundle="${bundle}" />
										pattern="[a-zA-Z ]{4,}"
										oninvalid="this.setCustomValidity('Invalid UserName (min. 4 alphabets)')"
										oninput="setCustomValidity('')" required>
								</div>
							</div>
							<div class="form-group">
								<label for="name" class="col-sm-3 control-label"><b><fmt:message
											key="name" bundle="${bundle}" />:</b></label>
								<div class="col-sm-12">
									<input type="text" name="uName" class="form-control" id="uName"
										placeholder=<fmt:message
											key="name" bundle="${bundle}" />
										pattern="[a-zA-Z ]{4,}"
										oninvalid="this.setCustomValidity('Invalid Name (min. 4 alphabets)')"
										oninput="setCustomValidity('')" required>
								</div>
							</div>
							<div class="form-group">
								<label for="role" class="col-sm-3 control-label"><b><fmt:message
											key="role" bundle="${bundle}" />:</b></label>
								<div class="col-sm-12">
									<div class="custom-control custom-radio">
										<INPUT type="radio" name="role" value="User" checked>
										<fmt:message key="user" bundle="${bundle}" />
									</div>
									<div class="custom-control custom-radio">
										<INPUT type="radio" name="role" value="Administrator">
										<fmt:message key="administrator" bundle="${bundle}" />
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="language" class="col-sm-3 control-label"><b><fmt:message
											key="language" bundle="${bundle}" />:</b></label>
								<div class="col-sm-12">
									<SELECT class="form-control" name="lang">
										<OPTION value="en" selected><fmt:message
												key="english" bundle="${bundle}" /></OPTION>
										<OPTION value="be"><fmt:message key="belarusian"
												bundle="${bundle}" /></OPTION>
									</SELECT>
								</div>
							</div>
							<div class="form-group">
								<label for="password" class="col-sm-3 control-label"><b><fmt:message
											key="Password" bundle="${bundle}" />:</b></label>
								<div class="col-sm-12">
									<input type="password" name="pwd"
										pattern="[a-zA-Z0-9@#$%^&+=]{4,}" class="form-control"
										id="pwd"
										placeholder=<fmt:message
											key="Password" bundle="${bundle}" />
										oninvalid="this.setCustomValidity('Invalid Password (min. 4 characters)')"
										oninput="setCustomValidity('')" required>
								</div>
							</div>
							<div class="form-group">
								<label for="repeatPassword" class="col-sm-3 control-label"><b><fmt:message
											key="repeatPassword" bundle="${bundle}" />:</b></label>
								<div class="col-sm-12">
									<input type="password" name="pwd-repeat"
										pattern="[a-zA-Z0-9@#$%^&+=]{4,}" class="form-control"
										id="pwd-repeat"
										placeholder=<fmt:message
											key="Password" bundle="${bundle}" />
										oninvalid="this.setCustomValidity('Invalid Password (min. 4 characters)')"
										oninput="setCustomValidity('')" required>
								</div>
							</div>
							<div class="form-group last">
								<div class="col-sm-offset-3 col-sm-9">
									<button type="submit" name="submit"
										class="btn btn-success btn-sm" value="SignUp">
										<fmt:message key="SignUp" bundle="${bundle}" />
									</button>
								</div>
							</div>
						</FORM>
						<strong style="color: red"><c:out value="${signUpError}"></c:out></strong>


						<div style="font-size: 14px">
							<c:set var="language" value="en_US" scope="session" />
							<fmt:setLocale value="${language}" scope="session" />
							<fmt:setBundle var="bundle" basename="com.web.bundle.messages" />
							<a
								href="info?newpage=signup&langType=en_US&submit=changePageLang">
								<fmt:message key="english" bundle="${bundle}" />
							</a>&nbsp;
							<c:set var="language" value="be_BY" scope="session" />
							<fmt:setLocale value="${language}" scope="session" />
							<fmt:setBundle var="bundle" basename="com.web.bundle.messages" />
							<a
								href="info?newpage=signup&langType=be_BY&submit=changePageLang">
								<fmt:message key="belarusian" bundle="${bundle}" />
							</a>
						</div>
					</div>


				</div>
			</div>
		</div>
	</div>
	<!--<FORM action="info" method="post">
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
	</FORM>-->
</body>

</html>