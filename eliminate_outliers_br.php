<?php
// update br value in training phase
include "database.php";
function replace_outliers_br($pid,$age,$sex,$cur_class,$br_to_replace)
{
/*	$host='192.168.108.40';
	$uname='root';
	$pwd='oneadmin';
	$db="project";
*/
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");

	$upper_limitbr=$br_to_replace+7;
	$lower_limitbr=$br_to_replace-7;
	$cur_class=strtoupper($cur_class);
	$upper_limitage=$age+4;
	$lower_limitage=$age-4;
	$query=mysql_query("UPDATE training_rr SET lrr='$br_to_replace', hrr='$br_to_replace' WHERE hage<='$upper_limitage' AND lage>='$lower_limitage' AND sex='$sex' AND class='$cur_class' AND hrr<='$lower_limitbr' AND lrr>='$upper_limitbr'") or die(mysql_error());
	if($query)
	{
		//echo "<br>change in training set success<br>";
	}
}
?>
