<?php
// get and update patient details
session_start();
if(!isset($_SESSION['pid'])&&!isset($_SESSION['viewprofile']))
	header('Location: logout.php');
?>
<!DOCTYPE html>
<html lang="en">
<head>
<script>
// validate updation of patient details
	function validatedoc()
	{
		var name=document.getElementById("name").value;
		var age=document.getElementById("age").value;
		var email=document.getElementById("email").value;
		var phone=document.getElementById("phone").value;
		//alert("helo");
		var x=true;
		if(name.length==0&&age.length==0&&email.length==0&&phone.length==0)
			alert("You can't submit an empty form");
		else if(phone.length>0&&phone.length<10)
			alert("Invalid details");
		else
		{
			 x=confirm("Are you sure? Click OK to confirm changes or Close to change again");
		}
		if(x==true){
		if(name.length==0)
			name="garb";
		if(age.length==0)
			age="garb";
		if(email.length==0)
			email="garb";
		if(phone.length==0)
			phone="garb";

		var dataString='name='+name+'&age='+age+'&email='+email+'&phone='+phone;
			$.ajax({
			type: "POST",
			url: "ajaxjs_update.php",
			data: dataString,
			cache: false,
			success: function(html)
			{
				//alert(html);
				if(html=="fail2")
					alert("Update failed!");
				else if(html=="fail1")
					alert("All data upto date");
				else if(html=="success"){
					alert("Updated Successfully!");
							window.location.href = "patientprofile.php";
				}
				window.location.reload();
			}
		});
	}
	}
	</script>
  <title>Personalized Healthcare System</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
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
		<?php if(isset($_SESSION['pid'])){
		echo'<li><button type="button" id="btnid0" class="btn btn-info btn-lg" ><a href="patient.php">Full History</a></button></li>
		<li><button type="button" id="btnid" class="btn btn-info btn-lg"><a href="viewdoctor.php">Doctor details</a></button></li>
		<li><button type="button" id="btnid2" class="btn btn-info btn-lg"><a href="#">Your Profile</a></button></li>
		<li><button type="button" id="btnid3" class="btn btn-info btn-lg"><a href="patientsummary.php">Current Summary</a></button></li>';

		}
		else if(isset($_SESSION['viewprofile'])){
		echo'<li><button type="button" id="btnid0" class="btn btn-info btn-lg" ><a href="doctor.php">Back to your home</a></button></li>
		<li><button type="button" id="btnid" class="btn btn-info btn-lg"><a href="patient.php">View full history</a></button></li>
		<li><button type="button" id="btnid2" class="btn btn-info btn-lg"><a href="patientsummary.php">Current Summary</a></button></li>';
		}?>
	  </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="logout.php"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container-fluid text-center">
  <div class="row content">
    <div class="col-sm-2 sidenav">
      <!--<p><a href="#">Link</a></p>
      <p><a href="#">Link</a></p>
      <p><a href="#">Link</a></p>-->
    </div>
    <div class="col-sm-8 text-left">
	<h1><?php
	if(isset($_SESSION['patname'])){
		echo "Hello ".$_SESSION['patname'];
		echo "<br>";
		echo "Your Patient id: ".$_SESSION['pid'];
	}?></h1>
	<?php
include "database.php";
/*	$host='192.168.108.40';
	$uname='root';
	$pwd='oneadmin';
	$db="project";
*/
	if(isset($_SESSION["pid"]))
	$pid=$_SESSION["pid"];
	if(isset($_SESSION["viewprofile"]))
	$pid=$_SESSION["viewprofile"];
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	$query=mysql_query("SELECT name,age,sex,email,phone FROM patprofile WHERE pid='$pid'") or die(mysql_error());
	?>
	<br><table id="tb1" class="table">
	<thead class="thead-inverse">
	<tr>
		<th>Name</th>
		<th>Age</th>
		<th>Sex</th>
		<th>Mail</th>
		<th>Phone</th>
	</tr>
	</thead>
	<?php
		while($row=mysql_fetch_array($query))
		{
			echo "<tr>";
			echo "<td>".$row['name']."</td>";
			echo "<td>".$row['age']."</td>";
			echo "<td>".$row['sex']."</td>";
			echo "<td>".$row['email']."</td>";
			echo "<td>".$row['phone']."</td>";
			echo "</tr>";
			break;

		}
      ?>
	  </table>
	  <button type="button" id="btnid3" class="btn btn-info btn-lg" data-toggle="modal" data-target="#patient">Edit details</button>
    </div>
    <!-- Modal -->
	<div class="modal fade" id="patient" role="dialog">
		<div class="modal-dialog">

		<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Update form</h4>
				</div>
				<div class="modal-body" id="modal_body">
					<div class="col-sm-8 text-left">
					<form role="form">
					<div class="form-group">
						<label for="name">Name:</label>
						<input type="text" class="form-control" id="name"><div id="spanem1"></div>
					</div>
					<div class="form-group">
						<label for="age">Age:</label>
						<input type="text" class="form-control" id="age"><div id="spanpw1"></div>
					</div>
					<div class="form-group">
						<label for="email">Email:</label>
						<input type="text" class="form-control" id="email"><div id="spanpw1"></div>
					</div>
					<div class="form-group">
						<label for="phone">Contact(only mobile)</label>
						<input type="text" class="form-control" id="phone"><div id="spanpw1"></div>
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
</div>


</body>

</html>
