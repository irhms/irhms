<?php
//check the database configuration

$host='192.168.108.40';
$uname='root';
$pwd='oneadmin';
$db="project";
$did = $_POST['did'];
$password=$_POST['password'];
$flag['code']=0;

$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");

$query=mysql_query("SELECT `did`,`name` FROM `doctor`WHERE `did` ='$did'
AND `password` =  '$password'");

if(mysql_num_rows($query)==1)
{
	$flag['code']=1;
}

print(json_encode($flag));
mysql_close();
?>
