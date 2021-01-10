<?php
include "db_include.php";


//Input data
$linje = $_GET['inn_id'];


$sql = mysqli_query($conn, "SELECT inn_id, inn_bruker, tittel, inn_tekst, inn_tid, brukernavn FROM innlegg, bruker WHERE innlegg.inn_id = '$linje' AND innlegg.inn_bruker = bruker_id;");

	if ($sql) {
		while ( $row = $sql->fetch_assoc())  {
			echo utf8_encode($row['brukernavn']);
			echo (',');
			echo utf8_encode($row['tittel']);
			echo (',');
			echo utf8_encode($row['inn_tekst']);
			echo (',');
			echo utf8_encode($row['inn_tid']);
			echo ('/');
			}
	}
	else {
		echo("Error description: " . mysqli_error($conn));
		echo "Det oppstod et problem";
  }

mysqli_close($conn);
?>
