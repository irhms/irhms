<?php
// session has been closed
session_start();
unset($_SESSION['did']);
unset($_SESSION['docname']);
unset($_SESSION['patname']);
unset($_SESSION['pid']);
unset($_SESSION['viewprofile']);
unset($_SESSION['profilename']);
unset($_SESSION['searchid']);
unset($_SESSION['searchname']);
header("Location:home.php");
?>
