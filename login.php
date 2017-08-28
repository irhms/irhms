<?php
include "database.php";
/*$host='192.168.108.40';
$uname='root';
$pwd='oneadmin';
$db="project";
*/
$pemail = $_POST['email'];
$password=$_POST['password'];

$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
//echo "connection successful";
$query=mysql_query("SELECT `pid`,`email` FROM `patprofile` WHERE `email` = '$pemail' and `pid`= '$password'");

if(mysql_num_rows($query)==1)
{
	$flag['code']=1;
}

print(json_encode($flag));
mysql_close();
?>
