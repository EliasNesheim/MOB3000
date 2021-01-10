<?php
include "db_include.php";


//Input data
$kommentar = $_GET['kom'];
$i = $_GET['i'];
$user = $_GET['Bruknavn'];

if($conn) { 
	$q = "SELECT bruker_id FROM bruker WHERE brukernavn LIKE '$user';";

	$result = mysqli_query($conn, $q);
	$result2 = mysqli_fetch_assoc($result);
	if($result2['bruker_id'] != "") {
		$bruker_ID = $result2['bruker_id'];
	} else {
		echo "fant ikke bruker";
	}
} else {
	echo "Ingen tilkobling...!";
}

$sql = "INSERT INTO kommentar (kom_innlegg, kom_bruker, kom_tekst) values('$i', '$bruker_ID', '$kommentar')";
$result = mysqli_query($conn, $sql);


if ($result) {
    echo "kommentaren er registrert";
  } else {
	echo("Error description: " . mysqli_error($conn));
    echo "Det oppstod et problem";
  }
mysqli_close($conn);
?>
