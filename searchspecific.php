<?php
// search and display patient details based on their Credentials
session_start();
if(!isset($_SESSION['docname']))
	header('Location: logout.php');
?>
<!DOCTYPE html>
<html lang="en">
<head>
<script>
function getRow(b) {
	var pid=$(b).parent().parent().find('.reqd').text();
	alert(pid);
	var dataString='pid='+pid;
	$.ajax({
			type: "POST",
			url: "ajaxjs_viewprofile.php",
			data: dataString,
			cache: false,
			success: function(html)
			{
				if(html=='done'){
						window.location.href="patientprofile.php";
				}
				else
					alert("Error.Try later")
			}
		}
		)

};
// search patient detailswrt patient id and name
	function searchpat()
	{
		var pid=document.getElementById("pid").value;
		var name=document.getElementById("name").value;
		var non_empty=0;
		if(pid.length==0&&name.length==0)
		{
			alert("Both fields cannot be empty");
		}
		else{
			if(pid.length==0)
			{
				non_empty=2;
			}
			else if(name.length==0)
			{
				non_empty=1;
			}
			else
			{
				non_empty=3;
			}
			var dataString="";
			if(non_empty==1)
				dataString='non_empty='+non_empty+'&pid='+pid;
			else if(non_empty==2)
				dataString='non_empty='+non_empty+'&name='+name;
			else if(non_empty==3)
				dataString='non_empty='+non_empty+'&pid='+pid+'&name='+name;
			$.ajax({
				type: "POST",
				url: "ajaxjs_setsessionvars.php",
				data: dataString,
				cache: false,
				success: function(html)
				{
					if(html=='Invalid Credentials')
						alert("No results found");
					else{
						alert("Login Authorised !! "+html)
							window.location.href = "searchspecific.php";
					}
				}
			})
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
        <?php if(isset($_SESSION['did'])){
			echo '<li><button type="button" class="btn btn-info btn-lg"><a href="doctor.php">Back to your home desk</a></button></li>';
		}
		?>      <ul class="nav navbar-nav">

	  </ul></ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="logout.php"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container-fluid text-center">
  <div class="row content">
    <div class="col-sm-2 sidenav">

    </div>
    <div class="col-sm-8 text-left">
	<div class="collapse navbar-collapse" id="myNavbar">
			<div class="modal fade" id="search" role="dialog">
		<div class="modal-dialog">

		<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Enter a search criteria</h4>
				</div>
				<div class="modal-body" id="modal_body">
					<div class="col-sm-8 text-left">
					<form role="form" >
					<div class="form-group">
						<label for="pid">Patient id:</label>
						<input type="text" class="form-control" id="pid"><div id="spanpid"></div>
					</div>
					<div class="form-group">
						<label for="name">Name:</label>
						<input type="text" class="form-control" id="name"><div id="spanpname"></div>
					</div>
					<a class="btn btn-default" onclick="searchpat()">Submit</a>
					</form>
					</div>
					</div><br><br><br><br><br>
					<div class="modal-footer">

					</div>

			</div>
		</div>
	</div>

	  </ul>
     <!-- <ul class="nav navbar-nav navbar-right">
        <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
      </ul>-->
    </div>
	<?php
	include "database.php";
	$did=$_SESSION['did'];
	if(isset($_SESSION['docname']))
		echo "<h1>Hello Dr ".$_SESSION['docname']."</h1>";
	if(isset($_SESSION['searchid']))
	{
		$pid=$_SESSION['searchid'];
	}
	else
		$pid="notset";
	if(isset($_SESSION['searchname']))
		$patname=$_SESSION['searchname'];
	else
		$patname="notset";
/*		$host='192.168.108.40';
		$uname='root';
		$pwd='oneadmin';
		$db="project";
*/
	echo $patname;
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	if($patname=="notset")
		$query=mysql_query("SELECT pid,name,hr,br,time FROM project WHERE pid LIKE '%$pid%' AND did='$did'") or die(mysql_error());
	else if($pid=="notset")
		$query=mysql_query("SELECT pid,name,hr,br,time FROM project WHERE name LIKE '%$patname%'AND did='$did'") or die(mysql_error());
	else
		$query=mysql_query("SELECT pid,name,hr,br,time FROM project WHERE pid LIKE '%$pid%' AND name LIKE '%$patname%' AND did='$did'") or die(mysql_error());
	?><h3>Patients that match your seach criteria</h3><br><table id="mytable" class="table">
	<thead class="thead-inverse">
	<tr>
		<th>Patient id</th>
		<th>Name</th>
		<th>Age</th>
		<th>Sex</th>
		<th>HR</th>
		<th>BR</th>
		<th>BP Systole</th>
		<th>BP Diastole</th>
		<th>SPO2</th>
		<th>ECG</th>
	    <th>Profile</th>
	</tr>
	</thead>
	<?php
	$one1=0;
	$previd=0;
	while($result=mysql_fetch_array($query))
	{
		if($one1==0)
			$previd=$result['pid'];
		else{
			if($result['pid']==$previd)
				continue;
			else
				$previd=$result['pid'];
		}
		echo "<tr>";
		$resid=$result['pid'];
		$age="";
		$sex="";
		$que=mysql_query("SELECT age,sex FROM patprofile WHERE pid ='$resid'") or die(mysql_error());
		while($ro=mysql_fetch_array($que))
		{
			$age=$ro['age'];
			$sex=$ro['sex'];
		}
		$q1=mysql_query("SELECT hr,br,bp_systole,bp_diastole,ecg,spo2 FROM summary WHERE pid='$pid' ORDER BY time DESC") or die(mysql_error());
		$one2=0;
		while($row=mysql_fetch_array($q1))
		{
			if($one2==1)
				break;
			else
			{
				echo "<td class='reqd'>".$resid."</td>";
				echo "<td>".$result['name']."</td>";
				echo "<td>".$age."</td>";
				echo "<td>".$sex."</td>";
				echo "<td>".$row['hr']."</td>";
				echo "<td>".$row['br']."</td>";
				echo "<td>".$row['bp_systole']."</td>";
				echo "<td>".$row['bp_diastole']."</td>";
				echo "<td>".$row['spo2']."</td>";
				echo "<td>".$row['ecg']."</td>";
			;?>
				<td><button type="button" class="mybutton" onclick="getRow(this)">View Profile</button></td>
			<?php
			}
			$one2++;
		}

		echo "</tr>";
		$one1++;
	}
	?>
	</table>
	</div>
    <!-- Modal -->

</div>


</body>
</html>
