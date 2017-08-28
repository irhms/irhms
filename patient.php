<?php
// get and display patient summary
session_start();
if(!isset($_SESSION['patname'])&&!isset($_SESSION['viewprofile']))
	header('Location: logout.php');
?>
<!DOCTYPE html>
<html lang="en">
<head>
<script>
//validate doctor Credentials
	function validatedoc()
	{
		var uname=document.getElementById("email").value;
		var pwd=document.getElementById("pwd").value;
		var dataString='name='+uname+'&pid='+pwd;
		$.ajax({
			type: "POST",
			url: "ajaxjs_doctor.php",
			data: dataString,
			cache: false,
			success: function(html)
			{
				if(html=='Invalid Credentials')
					alert("Invalid username/password");
				else
					window.location.href = "doctor.php?did="+html;
			}
		}
		)
	}
	//validate patient Credentials
	function validatepat()
	{
		var uname=document.getElementById("email2").value;
		var pid=document.getElementById("pwd2").value;
		var dataString='name='+uname+'&pid='+pid;
		$.ajax({
			type: "POST",
			url: "project/ajaxjs_doctor.php",
			data: dataString,
			cache: false,
			success: function(html)
			{
				if(html=='Invalid Credentials')
					alert("Invalid username/password");
				else
						window.location.href = "patient.php?pid="+html;
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
		echo'<li><button type="button" class="btn btn-info btn-lg" ><a href="patient.php">Full History</a></button></li>
		<li><button type="button" id="btnid" class="btn btn-info btn-lg"><a href="viewdoctor.php">Doctor details</a></button></li>
		<li><button type="button" id="btnid2" class="btn btn-info btn-lg"><a href="patientprofile.php">Your Profile</a></button></li>
		<li><button type="button" id="btnid2" class="btn btn-info btn-lg"><a href="patientsummary.php">Current Summary</a></button></li>';
		}
		else if(isset($_SESSION['viewprofile'])){
		echo'<li><button type="button" class="btn btn-info btn-lg" ><a href="doctor.php">Back to your home</a></button></li>
		<li><button type="button" id="btnid" class="btn btn-info btn-lg"><a href="patient.php">View full history</a></button></li>
		<li><button type="button" id="btnid2" class="btn btn-info btn-lg"><a href="patientsummary.php">Current Summary</a></button></li>';
		}?>      </ul>
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
		if(isset($_SESSION['pid']))
			echo "Your Patient id: ".$_SESSION['pid'];
	}?></h1>
	<?php
include "database.php";
/*	$host='192.168.108.40';
	$uname='root';
	$pwd='oneadmin';
	$db="project";
*/
	if(isset($_SESSION['pid']))
		$pid=$_SESSION["pid"];
	else
		$pid=$_SESSION['viewprofile'];
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	$dr=mysql_query("SELECT did FROM project WHERE pid='$pid'")or die(mysql_error());
	while($row=mysql_fetch_array($dr))
	{
		$_SESSION['did']=$row['did'];
		//echo $_SESSION['did']." hi";
		//echo "<br>";
		break;
	}
	if(isset($_SESSION['profilename'])){
			echo '<p align="justify"><h1>Data for patient '.$_SESSION['profilename'].' </h1></p>';
	}
	else{
		echo '<p align="justify"><h1>Data for patient id '.$pid.' </h1></p>';
	}

	$query=mysql_query("SELECT * FROM project WHERE pid='$pid' ORDER BY time DESC") or die(mysql_error());
	?>
	<br><table id="tb1" class="table">
	<thead class="thead-inverse">
	<tr>
		<th>HR</th>
		<th>BR</th>
		<th>ECG</th>
		<th>BP Systole</th>
		<th>BP Diastole</th>
		<th>Spo2</th>
		<th>Activity</th>
		<th>Location</th>
		<th>Date Time</th>
	</tr>
	</thead>

	<?php
		while($row=mysql_fetch_array($query))
		{
			echo "<tr>";
			echo "<td>".$row['hr']."</td>";
			echo "<td>".$row['br']."</td>";
			echo "<td>".$row['ecg']."</td>";
			echo "<td>".$row['bp_systole']."</td>";
			echo "<td>".$row['bp_diastole']."</td>";
			echo "<td>".$row['spo2']."</td>";
			echo "<td>".$row['activity']."</td>";
			echo "<td>".$row['location']."</td>";
			echo "<td>".$row['time']."</td>";
			echo "</tr>";
		}
		mysql_close($con);
      ?>

	  </table>
    </div>
    <!-- Modal -->

</div>


</body>
</html>
