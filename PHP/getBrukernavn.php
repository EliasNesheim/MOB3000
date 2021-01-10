<?php
include "db_include.php";

$userID = $_GET["user_ID"];

if($conn) 
	{$q = "SELECT brukernavn FROM bruker WHERE bruker_id LIKE '$user_ID'";
	$result = mysqli_query($conn, $q);

	if (mysqli_num_rows($result) > 0) {
		echo   $result;
	} else {
		echo "fant ikke bruker";
	}
} else {
	echo "Ingen tilkobling...!";
}
mysqli_close($conn);
?>


