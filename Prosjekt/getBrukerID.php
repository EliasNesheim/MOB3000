<?php
include "db_include.php";

//echo("test1");
$user = $_GET["user"];

if($conn) { 
	$q = "SELECT bruker_id FROM bruker WHERE brukernavn LIKE '$user'";
	$result = mysqli_query($conn, $q);
	$result = mysqli_fetch_assoc($result);
	if(isset($result['bruker_id'])) {
		echo $result['bruker_id'];
	} else {
		echo "fant ikke bruker";
	}
} else {
	echo "Ingen tilkobling...!";
}
mysqli_close($conn);
?>
