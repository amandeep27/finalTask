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
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/FontCss.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta Http-Equiv="Cache-Control" Content="no-cache">
<meta Http-Equiv="Pragma" Content="no-cache">
<meta Http-Equiv="Expires" Content="0">
<title><fmt:message key="updateBookPage" bundle="${bundle}" /></title>
</head>
<body class="updateBookBody">
	<c:if test="${!empty sessionScope.uName}">
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
							<form action="info" method="post" class="form-horizontal">
								<div class="form-group">
									<LABEL for="title" class="col-sm-3 control-label"><b><fmt:message
												key="title" bundle="${bundle}" /> : </b></LABEL>
									<div class="col-sm-8">
										<input type="text" value="${book.getBooktitle() }"
											class="form-control" id="title" name="title"
											oninvalid="this.setCustomValidity('Invalid Entry')"
											oninput="setCustomValidity('')" required />
									</div>
								</div>
								<div class="form-group">
									<LABEL for="description" class="col-sm-3 control-label"><b><fmt:message
												key="description" bundle="${bundle}" /> : </b></LABEL>
									<div class="col-sm-8">
										<INPUT type="text" name="description" class="form-control"
											id="description" value="${book.getDescription() }"
											oninvalid="this.setCustomValidity('Invalid Entry')"
											oninput="setCustomValidity('')" required />
									</div>
								</div>
								<div class="form-group">
									<LABEL for="year" class="col-sm-3 control-label"><b><fmt:message
												key="publishingYear" bundle="${bundle}" /> : </b></LABEL>
									<div class="col-sm-8">
										<INPUT type="text" name="year" class="form-control" id="year"
											value="${book.getPublishYear() }" pattern="[0-9]{4,4}"
											oninvalid="this.setCustomValidity('Invalid Entry')"
											oninput="setCustomValidity('')" required />
									</div>
								</div>
								<div class="form-group">
									<LABEL for="author" class="col-sm-3 control-label"><b><fmt:message
												key="author" bundle="${bundle}" /> : </b></LABEL>
									<div class="col-sm-8">
										<INPUT type="text" name="author" class="form-control"
											id="author" value="${book.getAuthor().getName() }"
											oninvalid="this.setCustomValidity('Invalid Entry')"
											oninput="setCustomValidity('')" required />
									</div>
								</div>
								<div class="form-group">
									<LABEL for="type" class="col-sm-3 control-label"><b><fmt:message
												key="type" bundle="${bundle}" /> : </b></LABEL>
									<div class="col-sm-8">
										<SELECT name="type" class="form-control">
											<OPTION value="e-book" selected><fmt:message
													key="eBook" bundle="${bundle}" /></OPTION>
											<OPTION value="paperBound"><fmt:message
													key="paperBound" bundle="${bundle}" /></OPTION>
										</SELECT>
									</div>
								</div>
								<div class="form-group last">
									<div class="col-sm-offset-3 col-sm-8">
										<BUTTON type="submit" name="submit" value="updateBookInfo"
											class="btn btn-success btn-sm">
											<fmt:message key="Go" bundle="${bundle}" />
										</BUTTON>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>

		</div>

	</c:if>
	<c:if test="${empty sessionScope.uName}">
		<c:redirect url="login.jsp"></c:redirect>
	</c:if>
</body>
</html>