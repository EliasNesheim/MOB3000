<?php
include "db_include.php";


$innleggID = $_GET['inn_id'];

$sql = mysqli_query($conn, "SELECT kom_id, kom_bruker, kom_tekst, kom_tid, brukernavn FROM kommentar, bruker WHERE kom_innlegg = '$innleggID' AND kommentar.kom_bruker = bruker_id");


	if ($sql) {
		while ( $row = $sql->fetch_assoc())  {
			echo utf8_encode($row['kom_id']);
			echo (',');
			echo utf8_encode($row['brukernavn']);
			echo (',');
			echo utf8_encode($row['kom_tekst']);
			echo (',');
			echo utf8_encode($row['kom_tid']);
			echo ('/');
			
			}
	}
	else {
		echo "Det oppstod et problem";
  }

mysqli_close($conn);
?>