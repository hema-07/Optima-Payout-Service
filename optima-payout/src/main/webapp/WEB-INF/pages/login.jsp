<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>login</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style type="text/css">
 .container {
  margin-top: 50px;
  margin-left:500px;
  width: 500px;
  height: 500px;
  }
  </style>
</head>
<body>

<div class="container">
<div class="col-sm-12">
 
 <div class="table-responsive">
 <h1>Optima Payout Service</h1>
  <table class="table" style="display: block;">
  	<tr><td><p style="font-size:x-large;font-weight:bold;color: light blue;">UserName</p>
  	<input type="text" name="username" class="form-control input-lg" required pattern="[A-Za-z0-9]{4,10}">
  	
  	<br><p style="font-size:x-large;font-weight:bold;color: light blue;">Password</p>
  	<input type="password" name="password" class="form-control input-lg" required pattern="[A-Za-z0-9]{4,10}">
  	</td>
  	</tr>
  	
  </table>
  </div>
  
<hr>
<a href="/payOutPlanService/landingpage" style="text-decoration:none;font-size:large;font-weight:bold;"><button onclick="/landingpage" class="btn btn-lg btn-success"><span class="glyphicon glyphicon-log-in"></span>&nbsp; Login</button></a>

 </div>
</div>

</body>
</html>