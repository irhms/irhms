<?php
// update ecg value in training phase
include "database.php";
function replace_outliers_ecg($pid,$age,$sex,$cur_class,$ecg_to_replace)
{
/*	$host='192.168.108.40';
	$uname='root';
	$pwd='oneadmin';
	$db="project";
*/
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");


	$query=mysql_query("UPDATE project SET lecg='$ecg_to_replace', hecg='$ecg_to_replace' WHERE hage<='$upper_limitage' AND lage>='$lower_limitage' AND sex='$sex' AND class='$cur_class' AND hecg<='$lower_limitecg' AND lecg>='$upper_limitecg'")or die(mysql_error());
	if($query)
	{
		//echo "<br>change in training set success<br>";
	}
}
?>
