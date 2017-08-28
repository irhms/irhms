<?php
// Start the session
// Get and send patient details
session_start();
?>
<?php
include "database.php";
/*	$host='192.168.108.40';
	$uname='root';
	$pwd='oneadmin';
	$db="project";
*/
$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
mysql_select_db($db,$con) or die("db selection failed");
$name=$_POST['name'];
$pid=$_POST['pid'];

$query=mysql_query("SELECT pid,name FROM project WHERE name='$name' AND pid='$pid'");
if(mysql_num_rows($query)>0)
{
	while($ret=mysql_fetch_array($query))
	{
		$_SESSION["pid"]=$ret['pid'];
		$_SESSION["patname"]=$ret['name'];
		echo $ret['pid'];
		break;
	}
}
else
	echo "Invalid Credentials";
mysql_close($con);
?>
