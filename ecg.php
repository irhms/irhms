<?php
// updation of cluster ecg based on calculated ecg data
	include_once "eliminate_outliers_ecg.php";
include "database.php";
	function update_ecg($pid)
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
		$ecg_1=array(0,0,0,0,0);
		//Obtain existing centers for current patient
		$centers=mysql_query("SELECT ecg_vlow,ecg_low,ecg_normal,ecg_high,ecg_vhigh FROM centers WHERE pid='$pid'")or die(mysql_error());

		while($result6=mysql_fetch_array($centers))
		{
			$ecg_1[0]=$result6['ecg_vlow'];
			$ecg_1[1]=$result6['ecg_low'];
			$ecg_1[2]=$result6['ecg_normal'];
			$ecg_1[3]=$result6['ecg_high'];
			$ecg_1[4]=$result6['ecg_vhigh'];
		}
		$values=mysql_query("SELECT ecg FROM summary WHERE pid='$pid' ORDER BY time DESC ")or die(mysql_error());
		$count=array(0,0,0,0,0);
		$min=0;
		$clust="";
		$newcenters=array(0,0,0,0,0);
		$values1=array(0,0,0,0,0);
		while($result7=mysql_fetch_assoc($values))
		{
			//echo "Value of ecg ".$result7['ecg']."<br>";
			$mindis=100;
			for($i=0;$i<5;$i++)
			{
				$x=pow((intval($ecg_1[$i])-intval($result7['ecg'])),2); //(x2-x1)^2
				$dis=sqrt($x); //calculate eucledian distance
				//echo $dis." Distance<br>";
				if($dis<$mindis)
				{
					$min=$i;
					$mindis=$dis;
				}
			}
			$count[$min]++; // to determine denominator for calculating new centers for each cluster using average
			$values1[$min]+=$result7['ecg'];
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
			//break;

		}
		//echo "New centers for ECG<br>";
		for($i=0;$i<5;$i++) //Updation of cluster centers based on calculated data
		{
			if($count[$i]!=0)
				$newcenters[$i]=$values1[$i]/$count[$i];
			else
				$newcenters[$i]=$ecg_1[$i];
			//echo $newcenters[$i]." ";
		}
		//echo "<br>Current ECG class is ".$clust;
		$ecg_to_replace=$newcenters[$min];
		//$update_query=mysql_query("UPDATE clustering_ecg SET cur_class='$clust' WHERE pid='$pid'");
		$update_query=mysql_query("UPDATE centers SET ecg_vlow='$newcenters[0]' WHERE pid='$pid'")or die(mysql_error());
		if(!$update_query)
			//echo "Error 3\n";
		$update_query=mysql_query("UPDATE centers SET ecg_low='$newcenters[1]' WHERE pid='$pid'")or die(mysql_error());
		if(!$update_query)
			//echo "Error 3\n";
		$update_query=mysql_query("UPDATE centers SET ecg_normal='$newcenters[2]' WHERE pid='$pid'")or die(mysql_error());
		if(!$update_query)
			//echo "Error 3\n";
		$update_query=mysql_query("UPDATE centers SET ecg_high='$newcenters[3]' WHERE pid='$pid'")or die(mysql_error());
		if(!$update_query)
			//echo "Error 3\n";
		$update_query=mysql_query("UPDATE centers SET ecg_vhigh='$newcenters[4]' WHERE pid='$pid'")or die(mysql_error());
		if(!$update_query)
			//echo "Error 3\n";
		//function for replacement of training data
			replace_outliers_ecg($pid,$age,$sex,$cur_class,$ecg_to_replace);
	}
	?>
