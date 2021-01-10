<?php
include "db_include.php";


//Input data
$inn_id = $_GET['inn_id'];

//echo "test";


$sql = mysqli_query($conn, "SELECT inn_id, inn_bruker, tittel, inn_tekst, inn_tid, brukernavn FROM innlegg, bruker WHERE innlegg.inn_id = '$inn_id' AND innlegg.inn_bruker = bruker_id;");

	if ($sql) {
		while ( $row = $sql->fetch_assoc())  {
			echo $row['brukernavn'];
			echo (',');
			echo $row['tittel'];
			echo (',');
			echo $row['inn_tekst'];
			echo (',');
			echo $row['inn_tid'];
			}
	}
	else {
		echo("Error description: " . mysqli_error($conn));
		echo "Det oppstod et problem";
  }

mysqli_close($conn);
?>
