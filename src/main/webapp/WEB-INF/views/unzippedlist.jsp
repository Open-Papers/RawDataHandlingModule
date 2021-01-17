<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	  href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
</head>
<body>
  <div align="center" class="container" >
  <h1>Unzipped Files</h1>
	 <table border="1" class="table table-striped">
	 <thead class="thead-dark">
  	  <tr>
		<th>NAME</th>
		<th>ACTION</th>
	  </tr>
	 </thead>
  	<c:forEach items="${fileNamesKey}" var="temp">
  	  <tr>
			<td>${temp}</td>
			<td><a href="downloadThis?name=${temp}&id=${idKey}">Download</a></td>
	  </tr>
  	</c:forEach>
  </table>
  </div>
</body>
</html>