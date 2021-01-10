<?php 
	//spinner PHP for oppdatering av brukerinfo

	//Tilkobling (inn i include)
	include "db_include.php";

	$sql = "SELECT * FROM institusjon";
	$result = mysqli_query($conn, $sql);

	// output data of each row
	//Fetch into associative array
	echo "Velg institusjon";
	echo(',');
	while ( $row = $result->fetch_assoc())  {
		echo utf8_encode($row['inst_navn']);
		echo(',');
	} 

	echo "/";

	$sql = "SELECT * FROM fag";
	$result = mysqli_query($conn, $sql);

	//output data of each row
	//Fetch into associative array
	echo "Velg fag";
	echo(',');
	while ( $row = $result->fetch_assoc())  {
		echo utf8_encode($row['fag_navn']);
		echo(',');
	} 
	
?>