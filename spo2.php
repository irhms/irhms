<?php
// updation of cluster center of spo2 based on calculated spo2 data
include "database.php";
include_once "eliminate_outliers_spo2.php";

	function update_spo2($pid)
	{
/*		$host='192.168.108.40';
		$uname='root';
		$pwd='oneadmin';
		$db="project";
*/
		$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
		mysql_select_db($db,$con) or die("db selection failed");
		$agensex=mysql_query("SELECT distinct age,sex FROM patprofile WHERE pid='$pid'",$con);
		$age=0;
		$sex=0;
		$cur_class="normal";
		while($result5=mysql_fetch_assoc($agensex))
		{
			$age=$result5['age'];
			$sex=$result5['sex'];
		}
		$spo2_1=array(0,0,0,0,0);
		//Obtain existing centers for current patient
		$centers=mysql_query("SELECT spo2_vlow,spo2_low,spo2_normal,spo2_high,spo2_vhigh FROM centers WHERE pid='$pid'")or die(mysql_error());

		while($result6=mysql_fetch_array($centers))
		{
			$spo2_1[0]=$result6['spo2_vlow'];
			$spo2_1[1]=$result6['spo2_low'];
			$spo2_1[2]=$result6['spo2_normal'];
			$spo2_1[3]=$result6['spo2_high'];
			$spo2_1[4]=$result6['spo2_vhigh'];
		}
		$values=mysql_query("SELECT spo2 FROM summary WHERE pid='$pid' ORDER BY time DESC")or die(mysql_error());
		$count=array(0,0,0,0,0);
		$min=0;
		$clust="";
		$newcenters=array(0,0,0,0,0);
		$values1=array(0,0,0,0,0);
		while($result7=mysql_fetch_assoc($values))
		{
			////echo "Spo2 value ".$result7['spo2']."<br>";
			$mindis=100;
			for($i=0;$i<5;$i++)
			{
				$x=pow((intval($spo2_1[$i])-intval($result7['spo2'])),2); //(x2-x1)^2
				$dis=sqrt($x); //calculate eucledian distance
				////echo $dis." Distance<br>";
				if($dis<$mindis)
				{
					$min=$i;
					$mindis=$dis;
				}
			}
			$count[$min]++; // to determine denominator for calculating new centers for each cluster using average
			$values1[$min]+=$result7['spo2'];
			//$clust stores current centre
			if($min==0)
				$clust="very low";
			else if($min==1)
				$clust="low";
			else if($min==2)
				$clust="normal";
			else if($min==3)
				$clust="high";
			else if($min==4)
				$clust="very high";
			break;
		}

		//New centers for spo2
		for($i=0;$i<5;$i++) //Updation of cluster centers based on calculated data
		{
			if($count[$i]!=0)
				$newcenters[$i]=$values1[$i]/$count[$i];
			else
				$newcenters[$i]=$spo2_1[$i];

		}

		$spo2_to_replace=$newcenters[$min];
		$update_query=mysql_query("UPDATE centers SET spo2_vlow='$newcenters[0]' WHERE pid='$pid'")or die(mysql_error());
		$update_query=mysql_query("UPDATE centers SET spo2_low='$newcenters[1]' WHERE pid='$pid'")or die(mysql_error());
		$update_query=mysql_query("UPDATE centers SET spo2_normal='$newcenters[2]' WHERE pid='$pid'")or die(mysql_error());
		$update_query=mysql_query("UPDATE centers SET spo2_high='$newcenters[3]' WHERE pid='$pid'")or die(mysql_error());
		$update_query=mysql_query("UPDATE centers SET spo2_vhigh='$newcenters[4]' WHERE pid='$pid'")or die(mysql_error());

		//function for replacement of training data
			replace_outliers_spo2($pid,$age,$sex,$cur_class,$spo2_to_replace);
	}
	?>
