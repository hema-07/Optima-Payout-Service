<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Card Details</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<script>
function myFunction() {
  var x = document.getElementById("myDIV");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}
</script>
<body>
<h2 style="text-align: center;margin: 10px;">Optima Payout Service</h2><br>
<br>
<div class="row" style="background-color: lightgrey;margin: 20px;">  
 <div class="col-sm-1">
  
  </div>
  <div class="col-sm-3">
  <center><h6 style="margin: 20px;">Pay from</h6></center>
   <c:forEach items="${d}" var="entry">
  <div class="card" style="margin: 30px;padding: 10px;">
  		<div><center><h6>${entry.key}% AER <b style="color: red;">£ ${entry.getValue().get(0)}</b></h6>
  			<h6>MinBal £${entry.getValue().get(1)} Stand: £${entry.getValue().get(2)} </h6></center>
  		</div>
  	</div>
  </c:forEach>
  </div>
 <div class="col-sm-1">
  
  </div>
  <div class="col-sm-3">
    <center><h6 style="margin: 20px;">Total Amount Due</h6></center>
   <c:forEach items="${c}" var="entry">
  <div class="card" style="margin:30px;padding: 10px;">
  		<div>
  			<center><h6> ${entry.key}% APR <b style="color: red;">£ ${entry.getValue().get(0)}</b></h6>
  			<h6>Min Due £${entry.getValue().get(1)}</h6> </center>
  		</div>
  	</div>
  </c:forEach>
</div>
 <div class="col-sm-3">
   <center><h6 style="margin: 20px;">Outstanding Balance</h6></center>
   <c:forEach items="${creditresult}" var="entry">
  <div class="card" style="margin: 30px;padding: 10px;">
  		<div><center>
  		<h6>Outstanding</h6>
	  		<h6 style="color: red;">£ ${entry.getValue().get(0)}</h6>
	  		</center>
  		</div>
  	</div>
  </c:forEach>
  </div>
 <div class="col-sm-1">
  
  </div>
</div>
<br>
 <div align="center"><button onclick="myFunction()" class="btn btn-success btn-lg">
   Agree & Pay ->
  </button></div> 
  
 <div id="myDIV">
 <div class="row" style="background-color: lightgrey;margin: 20px;">  
 <div class="col-sm-3">
 <center><h6 style="margin: 20px;">Available Balance</h6></center>
 
   <c:forEach items="${funds}" var="funds">
     
  <div class="card" style="margin: 30px;padding: 20px;">

  		<div><center><h6><b style="color: red;">£ ${funds}</b></h6></center>
  		</div>
  		
  	</div>
  	  </c:forEach>
   
  </div>
  <div class="col-sm-3">
  <center><h6 style="margin: 20px;">Paid from</h6></center>
 
   <c:forEach items="${d}" var="entry">
     
  <div class="card" style="margin: 30px;padding: 10px;">

  		<div><center><h6>${entry.key}% AER </h6>
  			<h6>MinBal £${entry.getValue().get(1)} Stand: £${entry.getValue().get(2)} </h6></center>
  		</div>
  		
  	</div>
  	  </c:forEach>


  </div>

  <div class="col-sm-3">
    <center><h6 style="margin: 20px;">Total Amount Due</h6></center>
   <c:forEach items="${credit}" var="entry">
  <div class="card" style="margin:30px;padding: 10px;">
  		<div>
  			<center><h6> ${entry.key}% APR <b style="color: red;">£ ${entry.getValue().get(0)}</b></h6>
  			<h6>Min Due £${entry.getValue().get(1)}</h6> </center>
  		</div>
  	</div>
  </c:forEach>
</div>
 <div class="col-sm-3">
   <center><h6 style="margin: 20px;">Outstanding Balance</h6></center>
   <c:forEach items="${creditresult}" var="entry">
  <div class="card" style="margin: 30px;padding: 10px;">
  		<div><center>
  		<h6>Outstanding</h6>
	  		<h6 style="color: red;">£ ${entry.getValue().get(0)}</h6>
	  		</center>
  		</div>
  	</div>
  </c:forEach>
  </div>
</div>
 </div>

<hr>
</body>
</html>