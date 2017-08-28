<?php
	session_start();
	//Home page for web portal
?>
<!DOCTYPE html>
<html lang="en">
<head>
<script>
	function validatedoc()
	{
		var uname=document.getElementById("email").value;
		var pwd=document.getElementById("pwd").value;
		var dataString='uname='+uname+'&password='+pwd;
		$.ajax({
			type: "POST",
			url: "ajaxjs_doctor.php",
			data: dataString,
			cache: false,
			success: function(html)
			{
				//alert(html);
				if(html=='Invalid Credentials')
					alert("Invalid username/password");
				else{
					//alert("Login Authorised "+html);
							window.location.href = "doctor.php";

				}
			}
		});
	}
	// validate patient credentials
	function validatepat()
	{
		var uname=document.getElementById("email2").value;
		var pid=document.getElementById("pwd2").value;
		var dataString='name='+uname+'&pid='+pid;
		$.ajax({
			type: "POST",
			url: "ajaxjs_patient.php",
			data: dataString,
			cache: false,
			success: function(html)
			{
				if(html=='Invalid Credentials')
					alert("Invalid username/password");
				else{
					window.location.href = "patient.php";
				}
			}
		}
		)

	}

</script>
  <title>Personalized Healthcare System</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
   <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <style>
    /* Remove the navbar's default margin-bottom and rounded borders */
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
    }

    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 450px}

    /* Set gray background color and 100% height */
    .sidenav {
      padding-top: 20px;
      background-color: #f1f1f1;
      height: 100%;
    }

    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }

    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height:auto;}
    }

	.carousel-inner > .item > img,
  .carousel-inner > .item > a > img {
      height: 500px;
	  width: 70%;
      margin: auto;
	  overflow: hidden;
  }


  </style>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><button type="button" class="btn btn-info btn-lg"><img src="images/logo2.jpg" height="35" width="75"/><a href="#"></a>Home Page</button></li>
	<li><button type="button" id="btnid" class="btn btn-info btn-lg" data-toggle="modal" data-target="#doctor"><img src="images/doclogin.jpeg" width="75" height="35"></img>Doctor Login</button></li>
	<li><button type="button" id="btnid2" class="btn btn-info btn-lg" data-toggle="modal" data-target="#patient"><img src="images/patlogin.png" width="75" height="35"></img>Patient Login</button></li>
	  </ul>
     <!-- <ul class="nav navbar-nav navbar-right">
        <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
      </ul>-->
    </div>
  </div>
</nav>

<div class="container-fluid text-center">
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
  <!-- Indicators -->
  <ol class="carousel-indicators">
    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
    <li data-target="#myCarousel" data-slide-to="1"></li>
    <li data-target="#myCarousel" data-slide-to="2"></li>
    <!--<li data-target="#myCarousel" data-slide-to="3"></li>-->
  </ol>

  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
    <div class="item active">
      <img class="carouselimg" class="carouselimg" src="images/rhc.png">
      <div class="carousel-caption">
        <h3>Remote Health Care Monitoring</h3>
        <p>Seamless health monitoring for all</p>
      </div>
    </div>

    <div class="item">
      <img class="carouselimg" class="carouselimg" src="images/doctor4.jpg">
      <div class="carousel-caption">
        <h3 align="left"><font color="black">Doctors</h3>
        <p><ul align="left"><li>Secure Login</li>
		   <li>Overall monitoring of your patients</li>
		   <li>Summary Packets for each patient</li>
		   <li>Abnormality Alerts</font></li></ul>
		</p>
      </div>
    </div>

    <div class="item">
      <img class="carouselimg" src="images/patient.jpg">
      <div class="carousel-caption">
        <h3 align="left"><font color="black">Patient</h3>
        <p><ul align="left">					   <li>Secure Login</li>
							   <li>History Maintenance</li>
							   <li>Know Your Doctor</li>
							   <li>24x7 Health Monitoring</li>
							   <li>Android App</li></ul></p>
      </div>
    </div>

  </div>

  <!-- Left and right controls -->
  <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>
    <div class="col-sm-8 text-left">

    </div>
    <!-- Modal -->
  <div class="modal fade" id="doctor" role="dialog">
		<div class="modal-dialog">

		<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Doctor Login</h4>
				</div>
				<div class="modal-body" id="modal_body">
					<div class="col-sm-8 text-left">
					<form role="form">
					<div class="form-group">
						<label for="email">Username:</label>
						<input type="text" class="form-control" id="email" required><div id="spanem1"></div>
					</div>
					<div class="form-group">
						<label for="pwd">Password:</label>
						<input type="password" class="form-control" id="pwd" required><div id="spanpw1"></div>
					</div>

					<div class="checkbox">
						<label><input type="checkbox">Remember me</label>
					</div>
					<a class="btn btn-default" onclick="validatedoc()">Submit</a>
					</form>
					</div>
					</div><br><br><br><br><br>
					<div class="modal-footer">

					</div>

			</div>
		</div>
	</div>



	<div class="modal fade" id="patient" role="dialog">
		<div class="modal-dialog">

		<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Patient Login</h4>
				</div>
				<div class="modal-body">
					<div class="col-sm-8 text-left">
					<form role="form">
					<div class="form-group">
						<label for="email">Username:</label>
						<input type="text" class="form-control" id="email2" required><div id="spanem2"></div>
					</div>
					<div class="form-group">
						<label for="pwd">Password:</label>
						<input type="password" name="password" class="form-control" id="pwd2" required><div id="spanpw2"></div>
					</div>
					<div class="checkbox">
						<label><input type="checkbox"> Remember me</label>
					</div>
					<a class="btn btn-default" onclick="validatepat()">Submit</a>
					</form>
					</div>
					<div class="modal-footer">
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>
