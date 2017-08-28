<?php
// update ranges of BP from calculated cluster data
include "database.php";
function replace_outliers_bp($pid,$age,$sex,$cur_class,$bp_to_replace)
{
/*	$host='192.168.108.40';
	$uname='root';
	$pwd='oneadmin';
	$db="project";
	*/
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");

	$upper_limitbp=$bp_to_replace+7;
	$lower_limitbp=$bp_to_replace-7;

	$upper_limitage=$age+4;
	$lower_limitage=$age-4;

	$query=mysql_query("UPDATE training_bp SET lbp='$bp_to_replace', hbp='$bp_to_replace' WHERE hage<='$upper_limitage' AND lage>='$lower_limitage' AND sex='$sex' AND class='$cur_class' AND hbp<='$lower_limitbp' AND lbp>='$upper_limitbp'") or die(mysql_error());
	if($query)
	{
		////echo "<br>change in training set success<br>";
	}
}
?>
