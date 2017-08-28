<?php
/* generate summary packet for received 10 records and send for classification process
*/
include "database.php";
	function summary_pac($id,$name){
/*	$host='192.168.108.40';
	$uname='root';
	$pwd='oneadmin';
	$db="project";
*/
	$pid=$id;
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	$previd=0;
	$one=0;
	$countFall=0;
	$query=mysql_query("SELECT pid,name,age,sex FROM patprofile WHERE pid='$id'") or die(mysql_error());
	while($row=mysql_fetch_array($query))
	{
		$name=$row['name'];
		$sex=$row['sex'];
		$age=$row['age'];
		$hr=0;
		$br=0;
		$bpsys=0;
		$bpdia=0;
		$ecg=0;
		$spo2=0;
		$time=0;
		$did=0;
		$ts=mysql_query("SELECT time FROM timestamp WHERE pid='$id' ORDER BY time DESC")or die(mysql_error());
			$two=0;
			while($res=mysql_fetch_array($ts))
			{
				if($two==1){
				$time=$res['time'];
				break;
				}
				$two++;
			}
		$q2=mysql_query("SELECT * FROM project WHERE pid='$pid' AND name='$name' AND time>'$time' ORDER BY time");
		$nrows=mysql_num_rows($q2);
		if($nrows==10)
		{
			$q2=mysql_query("SELECT * FROM project WHERE pid='$pid' AND name='$name' AND time>'$time' ORDER BY time") or die (mysql_error());
			while($inner=mysql_fetch_array($q2))
			{
				$hr+=$inner['hr'];
				$br+=$inner['br'];
				$bpsys+=$inner['bp_systole'];
				$bpdia+=$inner['bp_diastole'];
				$ecg+=$inner['ecg'];
				$spo2+=$inner['spo2'];
				$did=$inner['did'];
				if($inner['activity']=="Falling")
					$countFall++;
			}
			$hr/=10;
			$br/=10;
			$bpsys/=10;
			$bpdia/=10;
			$ecg/=10;
			$spo2/=10;
		 $ins=mysql_query("INSERT INTO summary VALUES('$pid','$name','$hr','$br','$bpsys','$bpdia','$ecg','$spo2','$time','$did','0')") or die(mysql_error());
// send values to classification process
$p= shell_exec('javac -cp $(find libs -iname *.jar | xargs | tr " " ":") classification/*.java geneticalgorithm/*.java com/messagebird/exceptions/*.java com/messagebird/objects/*.java com/messagebird/*.java 2>&1');
$pt = shell_exec('java -cp $(find /var/www/html/healthcare/libs -iname *.jar | xargs | tr " " ":")/usr/share/java/mysql-connector-java-5.1.28.jar: classification/TestData '.$pid.' '.$age.' '.$sex.' '.$hr.' '.$br.' '.$bpsys.' '.$ecg.' '.$spo2.' '.$countFall.' '.$name.' 2>&1');
echo $p;
echo $pt;

	}
	}
}
?>
