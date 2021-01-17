<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <link rel="stylesheet" 
    	  href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" >
    <title>Upload File</title>
  </head>
  
  <body>
  <div class="container">
  								<!-- Form -->
  <div>
  <h1 align="center">File List</h1>
  <h2>Upload Your File</h2>
	<form action="uploadfile" method="post" enctype="multipart/form-data">		  
 		<div class="form-group">												  
    		<label for="fileid">Select File</label>
   			<input type="file" name="zipfile" class="form-control-file" id="fileid">
  		</div>
  		<button class="btn btn-outline-success">Upload</button>
	</form>
  </div><br>
  								<!-- Table -->
  <div>
  <table border="1" class="table table-striped">
    <thead class="thead-dark">
  	  <tr>
		<th>ID</th>
		<th>NAME</th>
		<th>DOWNLOAD</th>
		<th>VIEW</th>
	  </tr>
	</thead>
  	<c:forEach items="${fileListKey}" var="temp">
  	  <tr>
			<td>${temp.id}</td>
			<td>${temp.fileName}</td>
			<td ><a href="download?id=${temp.id}">Download</a></td>
			<td ><a href="viewfiles?id=${temp.id}">ViewFiles</a></td>
	  </tr>
  	</c:forEach>
  </table>
  </div>
  
  
  </div>
  </body>
</html>