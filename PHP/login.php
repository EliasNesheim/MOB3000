<?php
include "db_include.php";

$salt = 'IT3_2020';

$user = $_GET["user"];
$pass = $_GET["pass"];

$pass = sha1($salt.$pass);


if($conn) { 
	$q = "SELECT * FROM bruker WHERE brukernavn LIKE '$user' AND passord LIKE '$pass'";
	$result = mysqli_query($conn, $q);
	$result2 = mysqli_fetch_assoc($result);
	if(mysqli_num_rows($result) > 0) {
		echo "Innlogget";
		echo ",";
		echo utf8_encode($result2 ['brukernavn']);
		echo ",";
		echo utf8_encode($result2 ['bruker_fag']);
		echo ",";
		echo utf8_encode($result2 ['bruker_inst']);
	} else {
		echo "Brukernavn og passord samsvarer ikke";
	}
} else {
	echo "Ingen tilkobling...!";
}
mysqli_close($conn);
?>
