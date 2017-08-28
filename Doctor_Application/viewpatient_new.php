<?php
$host='192.168.108.40';
$uname='root';
$pwd='oneadmin';
$db="project";
$did = $_POST['did'];

//$pid = $_POST['pid'];

//$did = 9;

$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
        mysql_select_db($db,$con) or die("db selection failed");
$query=mysql_query("SELECT DISTINCT pid FROM patprofile WHERE did='$did'") or die(mysql_error());
$one1=0;
$previd=0;
while($result=mysql_fetch_array($query))
{
	if($one1==0)
	$previd=$result['pid'];
	else
	{
		if($result['pid']==$previd)
			continue;
		else
			$previd=$result['pid'];
	}
		$resid=$result['pid'];
		$name="";
		$age="";
		$sex="";
		$q2=mysql_query("SELECT pid,name,age,sex FROM patprofile WHERE pid='$resid'") or die(mysql_error());
		while($result1=mysql_fetch_array($q2))
		{
			$name=$result1['name'];
			$age=$result1['age'];
			$sex=$result1['sex'];
			$pro[] = $result1;
			break;
		}
		$q1=mysql_query("SELECT summary.pid,patprofile.name,patprofile.age,patprofile.sex,summary.hr,summary.br,summary.bp_systole,summary.bp_diastole,summary.ecg,summary.spo2,summary.abnormal FROM summary, patprofile WHERE summary.pid='$resid' AND patprofile.pid = '$resid' ORDER BY summary.time DESC") or die(mysql_error());
		$one2=0;
		while($row=mysql_fetch_array($q1))
		{
			if($one2==1)
				break;
			else
			{

				//$pat_res[] = $name;
				//$pat_res[] = $age;
				//$pat_res[] = $pro[];
				$pat_res[] = $row;
				
			}
			$one2++;
		}
		$one1++;
}
//print(json_encode($pro));
print(json_encode($pat_res));
//print(json_encode(array_merge(json_decode($pro, true),json_decode($pat_res, true))));
mysql_close();
?>

