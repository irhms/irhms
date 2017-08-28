<?php
$host='192.168.108.40';
$uname='root';
$pwd='oneadmin';
$db="project";
$did = $_POST['did'];

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
$one++;
}
print(json_encode($pro));
mysql_close();
?>

