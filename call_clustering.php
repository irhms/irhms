<?php
// clustering intializing and updating process
include "database.php";
	include_once "hr.php";
	include_once "br.php";
	include_once "ecg.php";
	include_once "spo2.php";
	include_once "bp.php";
	$host='192.168.108.40';
	$uname='root';
	$pwd='oneadmin';
	$db="project";

	function init_clusters($pid,$sex,$age)
	{
		//include "hr.php";
/*		$host='192.168.108.40';
		$uname='root';
		$pwd='oneadmin';
		$db="project";
*/
		$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
		mysql_select_db($db,$con) or die("db selection failed");
		/* For the first time, initialize clusters based on patient's age and sex*/
		//Heart rate centers for male
		if(strtolower($sex)=="male")
		{
			if($age>=16&&$age<=34)
			{
				$hr_vlow=48;
				$hr_low=55;
				$hr_normal=68;
				$hr_high=80;
				$hr_vhigh=95;
			}
			if($age>=35&&$age<=44)
			{
				$hr_vlow=49;
				$hr_low=56;
				$hr_normal=69;
				$hr_high=81;
				$hr_vhigh=96;
			}
			if($age>=45&&$age<=54)
			{
				$hr_vlow=50;
				$hr_low=57;
				$hr_normal=70;
				$hr_high=82;
				$hr_vhigh=97;
			}
			if($age>=55&&$age<=99)
			{
				$hr_vlow=46;
				$hr_low=52;
				$hr_normal=65;
				$hr_high=82;
				$hr_vhigh=92;
			}
		}
		//heart rate centers for female
		if(strtolower($sex)=="female")
		{
			if($age>=16&&$age<=34)
			{
				$hr_vlow=51;
				$hr_low=58;
				$hr_normal=71;
				$hr_high=83;
				$hr_vhigh=98;
			}
			if($age>=35&&$age<=44)
			{
				$hr_vlow=52;
				$hr_low=58;
				$hr_normal=71;
				$hr_high=83;
				$hr_vhigh=98;
			}
			if($age>=45&&$age<=54)
			{
				$hr_vlow=53;
				$hr_low=58;
				$hr_normal=72;
				$hr_high=83;
				$hr_vhigh=98;
			}
			if($age>=55&&$age<=99)
			{
				$hr_vlow=45;
				$hr_low=50;
				$hr_normal=67;
				$hr_high=80;
				$hr_vhigh=90;
			}
		}
		//breating rate centers for male
		if(strtolower($sex)=="male")
		{
				if($age<18)
				{
					$br_vlow=6;
					$br_low=10;
					$br_normal=17;
					$br_high=27;
					$br_vhigh=32;
				}
				if($age>=18&&$age<=59)
				{
					$br_vlow=6;
					$br_low=9;
					$br_normal=15;
					$br_high=22;
					$br_vhigh=28;
				}
				if($age>=60&&$age<=99)
				{
					$br_vlow=6;
					$br_low=9;
					$br_normal=14;
					$br_high=21;
					$br_vhigh=28;
				}
		}
		//br centers for female
		else if(strtolower($sex)=="female")
		{
				if($age<18)
				{
					$br_vlow=7;
					$br_low=10;
					$br_normal=17;
					$br_high=25;
					$br_vhigh=33;
				}
				if($age>=18&&$age<=59)
				{
					$br_vlow=6;
					$br_low=9;
					$br_normal=15;
					$br_high=22;
					$br_vhigh=28;
				}
				if($age>=60&&$age<=99)
				{
					$br_vlow=6;
					$br_low=9;
					$br_normal=14;
					$br_high=21;
					$br_vhigh=26;
				}
		}
		//ECG centers for male
		if(strtolower($sex)=="male")
		{
				if($age>=18&&$age<=59)
				{
					$ecg_vlow=390;
					$ecg_low=420;
					$ecg_normal=445;
					$ecg_high=475;
					$ecg_vhigh=550;
				}
				if($age>=60&&$age<=99)
				{
					$ecg_vlow=400;
					$ecg_low=430;
					$ecg_normal=455;
					$ecg_high=485;
					$ecg_vhigh=560;
				}
		}
		//ECG centers for female
		else if(strtolower($sex)=="female")
		{
				if($age>=18&&$age<=59)
				{
					$ecg_vlow=390;
					$ecg_low=420;
					$ecg_normal=445;
					$ecg_high=475;
					$ecg_vhigh=550;
				}
				if($age>=60&&$age<=99)
				{
					$ecg_vlow=400;
					$ecg_low=430;
					$ecg_normal=450;
					$ecg_high=480;
					$ecg_vhigh=555;
				}
		}
		//BP centers based on age
		if($age>=16&&$age<=44)
		{
			$bp_vlow=65;
			$bp_low=85;
			$bp_normal=115;
			$bp_high=140;
			$bp_vhigh=180;
		}
		if($age>=45&&$age<=54)
		{
			$bp_vlow=70;
			$bp_low=90;
			$bp_normal=123;
			$bp_high=145;
			$bp_vhigh=180;
		}
		if($age>=55&&$age<=99)
		{
			$bp_vlow=75;
			$bp_low=95;
			$bp_normal=133;
			$bp_high=150;
			$bp_vhigh=185;
		}
		$spo2_vlow=88;
		$spo2_low=93;
		$spo2_normal=98;
		$spo2_high=0;
		$spo2_vhigh=0;
		$counter=mysql_query("SELECT * FROM centers WHERE pid='$pid'") or die(mysql_error());
		$val=mysql_num_rows($counter);

		//If data for the patient is coming for the first time, then insert the initialized values based on age and sex
		if($val==0){
			$clusterhr=mysql_query("insert into centers values('$pid','$hr_vlow','$hr_low','$hr_normal','$hr_high','$hr_vhigh','$br_vlow','$br_low','$br_normal','$br_high','$br_vhigh','$bp_vlow','$bp_low','$bp_normal','$bp_high','$bp_vhigh','$ecg_vlow','$ecg_low','$ecg_normal','$ecg_high','$ecg_vhigh','$spo2_vlow','$spo2_low','$spo2_normal','$spo2_high','$spo2_vhigh')");
			echo "<br> Insert HR Centers Called";
			}
			//If patient's centers already exist, then call modified Kmeans algorithm

			else{
				update_hr($pid); //hr.php
				update_br($pid); //br.php
				update_ecg($pid);  //ecg.php
				update_spo2($pid); //spo2.php
				update_bp($pid);   //bp.php

			}
	}
?>
