<?php
// spo2 value is updated in training phase
include "database.php";
function replace_outliers_spo2($pid,$age,$sex,$cur_class,$spo2_to_replace)
{
/*	$host='192.168.108.40';
	$uname='root';
	$pwd='oneadmin';
	$db="project";
	*/
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");

	$upper_limitspo2=$spo2_to_replace+7;
	$lower_limitspo2=$spo2_to_replace-7;

	/*$upper_limitage=$age+4;
	//$lower_limitage=$age-4;

	//$query=mysql_query("UPDATE training_spo2 SET lspo2='$spo2_to_replace', hspo2='$spo2_to_replace' WHERE hage<='$upper_limitage' AND lage>='$lower_limitage' AND sex='$sex' AND class='$cur_class' AND hspo2<=$lower_limitage AND lspo2>=$upper_limitage");
	//if($query)
	{
		echo "change in training set success";
	}*/
}
?>
