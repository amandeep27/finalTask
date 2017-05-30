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
<style type="text/css">
#content {
	display: none;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta Http-Equiv="Cache-Control" Content="no-cache">
<meta Http-Equiv="Pragma" Content="no-cache">
<meta Http-Equiv="Expires" Content="0">
<title><fmt:message key="HomePage" bundle="${bundle}" /></title>
</head>
<body class="userHomeBody">

	<c:if test="${!empty sessionScope.uName}">
		<div class="container">

			<div class="form-group">
				<form action="info" method="post" class="form-horizontal">
					<LABEL for="lang" class="col-sm-1 control-label"><b><fmt:message
								key="language" bundle="${bundle}" /> : </b></LABEL>
					<div class="col-sm-3">
						<SELECT name="lang" class="form-control">
							<OPTION value="en" selected><fmt:message key="english"
									bundle="${bundle}" /></OPTION>
							<OPTION value="be"><fmt:message key="belarusian"
									bundle="${bundle}" /></OPTION>
						</SELECT>
					</div>
					<div class="col-sm-2">
						<button type="submit" name="submit" value="changeLanguage"
							class="btn btn-success btn-sm">
							<fmt:message key="changeLanguage" bundle="${bundle}" />
						</button>
					</div>
				</form>
				<form action="info" method="post" class="form-horizontal">

					<div class="col-sm-offset-2 col-sm-2">
						<a href="info?submit=getPage&page=editInfo"
							class="btn btn-default"> <fmt:message key="editInfo"
								bundle="${bundle}" />
						</a>
					</div>
					<div class="col-sm-2">
						<button class="btn btn-success btn-sm" type="submit" name="submit"
							value="logOut">
							<fmt:message key="LogOut" bundle="${bundle}" />
						</button>
					</div>

				</form>
			</div>
		</div>
		<div class="container">
			<fmt:message key="hello" bundle="${bundle}" />
			<c:out value=", ${sessionScope.uName}"></c:out>
		</div>
		<div class="container">
			<div class="form-group">

				<button id="showAddBookForm" class="btn btn-success btn-sm">
					<fmt:message key="addBook" bundle="${bundle}" />
				</button>

			</div>
			<div id="content">
				<form action="info" method="post" class="form-horizontal">
					<div class="form-group">
						<LABEL for="lang" class="col-sm-1 control-label"><b><fmt:message
									key="language" bundle="${bundle}" /> : </b></LABEL>
						<div class="col-sm-3">
							<SELECT name="lang" class="form-control">
								<OPTION value="en" selected><fmt:message key="english"
										bundle="${bundle}" /></OPTION>
								<OPTION value="be"><fmt:message key="belarusian"
										bundle="${bundle}" /></OPTION>
							</SELECT>
						</div>
					</div>
					<div class="form-group">
						<LABEL for="title" class="col-sm-1 control-label"><b><fmt:message
									key="title" bundle="${bundle}" /> : </b></LABEL>
						<div class="col-sm-3">
							<INPUT type="text" name="title" class="form-control" id="title"
								oninvalid="this.setCustomValidity('Invalid Entry')"
								oninput="setCustomValidity('')" required />
						</div>
					</div>
					<div class="form-group">
						<LABEL for="description" class="col-sm-1 control-label"><b><fmt:message
									key="description" bundle="${bundle}" /> : </b></LABEL>
						<div class="col-sm-3">
							<INPUT type="text" name="description" class="form-control"
								id="description"
								oninvalid="this.setCustomValidity('Invalid Entry')"
								oninput="setCustomValidity('')" required />
						</div>
					</div>
					<div class="form-group">
						<LABEL for="publishingYear" class="col-sm-1 control-label"><b><fmt:message
									key="publishingYear" bundle="${bundle}" /> : </b></LABEL>
						<div class="col-sm-3">
							<INPUT type="text" name="year" class="form-control" id="year"
								pattern="[0-9]{4,4}"
								oninvalid="this.setCustomValidity('Invalid Entry')"
								oninput="setCustomValidity('')" required />
						</div>
					</div>
					<div class="form-group">
						<LABEL for="author" class="col-sm-1 control-label"><b><fmt:message
									key="author" bundle="${bundle}" /> : </b></LABEL>
						<div class="col-sm-3">
							<INPUT type="text" name="author" class="form-control" id="author"
								oninvalid="this.setCustomValidity('Invalid Entry')"
								oninput="setCustomValidity('')" required />
						</div>
					</div>
					<div class="form-group">
						<LABEL for="type" class="col-sm-1 control-label"><b><fmt:message
									key="type" bundle="${bundle}" /> : </b></LABEL>
						<div class="col-sm-3">
							<SELECT name="type" class="form-control">
								<OPTION value="e-book" selected><fmt:message
										key="eBook" bundle="${bundle}" /></OPTION>
								<OPTION value="paperBound"><fmt:message
										key="paperBound" bundle="${bundle}" /></OPTION>
							</SELECT>
						</div>
					</div>
					<div class="form-group last">
						<div class="col-sm-offset-1 col-sm-11">
							<BUTTON type="submit" name="submit" value="addBook"
								class="btn btn-success btn-sm">
								<fmt:message key="Go" bundle="${bundle}" />
							</BUTTON>
						</div>
					</div>
				</form>
			</div>
			<c:if test="${!error }">
				<strong style="color: red"><c:out value="${AddBookError}"></c:out></strong>
			</c:if>
		</div>

		<div class="container">
			<form action="info" method="get" accept-charset="UTF-8"
				class="form-horizontal">
				<div class="form-group">
					<LABEL for="title" class="col-sm-1 control-label"><b><fmt:message
								key="title" bundle="${bundle}" /> : </b></LABEL>
					<div class="col-sm-4">
						<INPUT type="text" name="title" pattern="{4,}"
							class="form-control" id="title"
							oninvalid="this.setCustomValidity('Invalid Entry')"
							oninput="setCustomValidity('')" /> <b><fmt:message
								key="note" bundle="${bundle}" />:</b>
						<fmt:message key="emptyTitleField" bundle="${bundle}" />
					</div>
				</div>
				<div class="form-group">
					<LABEL for="author" class="col-sm-1 control-label"><b><fmt:message
								key="author" bundle="${bundle}" /> : </b></LABEL>
					<div class="col-sm-4">
						<INPUT type="text" name="author" pattern="{4,}"
							class="form-control" id="author"
							oninvalid="this.setCustomValidity('Invalid Entry')"
							oninput="setCustomValidity('')" /> <b><fmt:message
								key="note" bundle="${bundle}" />:</b>
						<fmt:message key="emptyAuthorField" bundle="${bundle}" />
					</div>
				</div>
				<div class="form-group">
					<LABEL for="type" class="col-sm-1 control-label"><b><fmt:message
								key="type" bundle="${bundle}" /> : </b></LABEL>
					<div class="col-sm-4">
						<SELECT class="form-control" name="type">
							<OPTION value="all" selected><fmt:message key="all"
									bundle="${bundle}" /></OPTION>
							<OPTION value="e-book"><fmt:message key="eBook"
									bundle="${bundle}" /></OPTION>
							<OPTION value="paperBound"><fmt:message key="paperBound"
									bundle="${bundle}" /></OPTION>
						</SELECT>
					</div>
				</div>
				<div class="form-group last">
					<div class="col-sm-offset-1 col-sm-11">
						<BUTTON type="submit" name="submit" value="getBook"
							class="btn btn-success btn-sm">
							<fmt:message key="Go" bundle="${bundle}" />
						</BUTTON>
					</div>
				</div>

			</form>
		</div>
		<c:if test="${gotResult}">
			<c:if test="${!has_result }">
				<strong><fmt:message key="noRecord" bundle="${bundle}" /></strong>
			</c:if>
			<c:if test="${has_result}">
				<div class="container">
					<strong class="col-sm-4">Info : </strong> <br> <br>
					<table border="2" class="table table-hover" style="background-color: white">
						<thead>
							<tr>
								<th><fmt:message key="title" bundle="${bundle}" /></th>
								<th><fmt:message key="description" bundle="${bundle}" /></th>
								<th><fmt:message key="publishingYear" bundle="${bundle}" /></th>
								<th><fmt:message key="author" bundle="${bundle}" /></th>
								<th><fmt:message key="bookType" bundle="${bundle}" /></th>
								<th><fmt:message key="removeAction" bundle="${bundle}" /></th>
								<th><fmt:message key="updateAction" bundle="${bundle}" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="book" items="${bookList}">
								<tr>
									<td>${book.booktitle}</td>
									<td>${book.description}</td>
									<td>${book.publishYear}</td>
									<td>${book.getAuthor().name}</td>
									<td>${book.getType().typeName}</td>
									<td><a
										href="info?method=doPost&submit=removeBook&id=${book.bookId }">
											<fmt:message key="removeBook" bundle="${bundle}" />
									</a></td>
									<td><a
										href="info?submit=getBookInfo&id=${book.bookId}&type=${book.getType().typeName}&author=${book.getAuthor().name}">
											<fmt:message key="updateBook" bundle="${bundle}" />
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</c:if>

	</c:if>
	<c:if test="${empty sessionScope.uName}">
		<c:redirect url="login.jsp"></c:redirect>
	</c:if>
	<script>
		var showForm = document.getElementById("showAddBookForm");
		var content = document.getElementById("content");

		showForm.addEventListener("click", function() {
			content.style.display = (content.dataset.toggled ^= 1) ? "block"
					: "none";
		}, false);
	</script>
</body>
</html>