<?php
$host='192.168.108.40';
$uname='root';
$pwd='oneadmin';
$db="project";
$did = $_POST['did'];
$pid = $_POST['pid'];

//$did = 7;
//$pid = 8040;

$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
        mysql_select_db($db,$con) or die("db selection failed");
//echo "connection successful";
//$query=mysql_query("SELECT `name`,`hr`,`br`, `bp_systole`,`bp_diastole`,`ecg`,`spo2` FROM `project` WHERE `pid` = '$pid' ORDER BY time DESC");
$query = mysql_query("SELECT `pid`,`name`, `hr`, `br`, `bp_systole`, `bp_diastole`, `ecg`, `spo2`,`activity` FROM `project` WHERE `pid`= $pid AND `did`= $did ORDER BY time DESC LIMIT 25");
//$query=mysql_query("SELECT `did`,`name` FROM `doctor`WHERE `did` ='$did' AND `password` =  '$password'");
//while($res = mysql_fetch_assoc($query))
  //      $output[]=$res;
while($row=mysql_fetch_array($query))
{
	$pat_array[] = $row;
 	//print(json_encode($emparray));
}     
print(json_encode($pat_array));
mysql_close();
?>
