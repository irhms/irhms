<?php
// update hr value in training phase
include "database.php";
function replace_outliers_hr($pid,$age,$sex,$cur_class,$hr_to_replace)
{
/*	$host='192.168.108.40';
	$uname='root';
	$pwd='oneadmin';
	$db="project";
	*/
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	//Setting age, hr thresholds
	$upper_limithr=$hr_to_replace+7;
	$lower_limithr=$hr_to_replace-7;

	$upper_limitage=$age+4;
	$lower_limitage=$age-4;
	//For personalization, replace the tuples where lower HR, upper HR, lower age and higher age exceed to permissible thresholds with the current data
	$query=mysql_query("UPDATE training_hr SET lhr='$hr_to_replace', hhr='$hr_to_replace' WHERE hage<='$upper_limitage' AND lage>='$lower_limitage' AND sex='$sex' AND class='$cur_class' AND hhr<='$lower_limithr' AND lhr>='$upper_limithr'") or die(mysql_error());

	if($query)
	{
		//echo "<br>change in training set success<br>";
	}
}
?>
