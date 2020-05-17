<?php

session_start();

session_destroy();
$_SESSION['login_user']= null; 
header("Location: index.php");
?><?php
