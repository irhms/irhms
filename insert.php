<?php
	include "generatesummary.php";
	include "call_clustering.php";
// data receive from mobile and update into table

include "database.php";
/*$host='192.168.108.40';
$uname='root';
$pwd='oneadmin';
$db="project";
*/
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	/*
		Parse data from JSON string obtained from the mobile and then put it in the "project" table
	*/

	$pid=$_REQUEST['p_id'];
	$name=$_REQUEST['name'];
	$hr=$_REQUEST['hr'];
	$br=$_REQUEST['br'];
	$systole=$_REQUEST['systole'];
	$diastole=$_REQUEST['diastole'];
	$ecg=$_REQUEST['ecg'];
	$spo2=$_REQUEST['spo2'];
	$lon=$_REQUEST['lon'];
	$lat=$_REQUEST['lat'];
	$acti=$_REQUEST['Activity'];
	$did="";
	$lat.=" , ".$lon;

	$query=mysql_query("SELECT did FROM patprofile WHERE pid='$pid'") or die(mysql_error());
	while($s=mysql_fetch_array($query))
	{
		$did=$s['did'];
		break;
	}
	$flag['code']=0;
	$ecg=intval($ecg);
		if($ecg<0)
			$ecg*=-1;
		$ecg=strval($ecg);
		$r=mysql_query("insert into project (pid, name,hr, br,bp_systole,bp_diastole,ecg,spo2,location,did,activity) values('$pid','$name','$hr','$br','$systole','$diastole','$ecg','$spo2','$lat','$did','$acti')") or die(mysql_error());
		if($r){
			$flag['code']=1;
		}
		$cnt=mysql_query("SELECT * FROM project WHERE pid='$pid'");
		$cnt1=mysql_num_rows($cnt);
		/* If 10 rows have been inserted since the last summary packet was generated, update timestamp in the "timestamp" table and
			then invoke function for summary packet
		*/
		if($cnt1%10==0)
		{
			$time="";
			$ts=mysql_query("SELECT time FROM project WHERE pid='$pid' ORDER BY time DESC")or die(mysql_error());
			while($res=mysql_fetch_array($ts))
			{
				$time=$res['time'];
				break;
			}
			$ins=mysql_query("INSERT INTO timestamp VALUES('$pid','$time')")or die(mysql_error());
			if($ins)
			{
			}
		}

	print(json_encode($flag)); //send response to android app

	$age="";
	$sex="";
	/*Obtain age and sex from the db for invoking clustering functions*/
	$getdetails=mysql_query("SELECT sex,age FROM patprofile WHERE pid='$pid'") or die(mysql_error());
	while($val=mysql_fetch_array($getdetails))
	{
		$age=$val['age'];
		$sex=$val['sex'];
		break;
	}
	mysql_close($con);

	summary_pac($pid,$name); //Redirects to generatesummary.php
	init_clusters($pid,$sex,$age); //Redirects to call_clustering.php
	?>
