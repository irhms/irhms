<?php
//	include "call_clustering.php";

include "database.php";
/*	$host='192.168.108.40';
	$uname='root';
	$pwd='oneadmin';
	$db="project";
*/
	/*
		The register function is invoked, the first time the app is installed on the mobile
		This gets the personal details from the patient and inserts them into the "patprofile" table
	*/
	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	//$pid="";
	$que=mysql_query("SELECT (pid) FROM patprofile ORDER BY pid DESC")or die(mysql_error());

	while($ro=mysql_fetch_array($que))
	{
		$pid=$ro['pid'];
		break;
	}
	$pid+=1;
	$name=$_REQUEST['name'];
	$age=$_REQUEST['age'];
	$sex=$_REQUEST['sex'];
	$email=$_REQUEST['email'];
	$phone=$_REQUEST['phone'];
	$city=$_REQUEST['city'];
	$did="";
	/* Assign a doctor based on the city of the patient*/
	$query=mysql_query("SELECT did FROM doctor WHERE city='$city'") or die(mysql_error());
	if(mysql_num_rows($query)>0)
	{
		$cnt=mysql_num_rows($query);
		$val=1;
		$ran=rand(1,$cnt);
		while($res=mysql_fetch_array($query))
		{
			if($val==$ran)
			{
				$did=$res['did'];
				break;
			}
			$val++;
		}
	}
	else
	{
		$query=mysql_query("SELECT did FROM doctor") or die(mysql_error());
		$ran=rand(1,mysql_num_rows($query));
		$val=1;
		while($res=mysql_fetch_array($query))
		{
			if($val==$ran)
			{
				$did=$res['did'];
				break;
			}
			$val++;
		}
	}
	$flag['code']=0;
		$r=mysql_query("insert into patprofile (pid,name,age,sex,email,phone,did,city) values('$pid','$name','$age','$sex','$email','$phone','$did','$city')") or die(mysql_error());
		if($r){
			$flag['code']=1;
			$flag['pid']=$pid;
		}
	//create an exclusive training dataset for this patient
	$mailmsg="Hello ".$name.".Your credentials<br>"."Name: ".$name."\nPatient id: ".$pid."\nUse these to login using your app\n";
	print(json_encode($flag));
	mysql_close($con);
	?>
