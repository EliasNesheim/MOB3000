<?php
	//PHP for å vise info på profilsiden

	//Tilkobling (inkluder egen fil!)
	include "db_include.php";

	//Logget inn bruker
	$user = $_GET["user"];

	if($conn) { 

		$sql = mysqli_query($conn,"SELECT brukernavn, fag_navn, inst_navn, epost
									FROM bruker
									LEFT JOIN fag
										ON fag_id = bruker_fag
									LEFT JOIN institusjon
										ON inst_id = bruker_inst
									WHERE brukernavn = '$user'");
		$count = mysqli_num_rows($sql);
		
		if($count > 0)  {
			$row = mysqli_fetch_assoc($sql);
	
			$brukernavn = $row["brukernavn"];
			$fag = $row["fag_navn"];
			$institusjon = $row["inst_navn"];
			$epost = $row["epost"];
			
			echo utf8_encode($fag);
			echo ",";
			echo utf8_encode($institusjon);
			echo ",";
			echo utf8_encode($epost);
			
			//Innlegg
			$sqlInnlegg = mysqli_query($conn,"SELECT COUNT(inn_id) AS antall_innlegg
									FROM bruker
									INNER JOIN innlegg
									WHERE brukernavn = '$user'
									AND inn_bruker = bruker_id");
			$count = mysqli_num_rows($sqlInnlegg);
		
			if($count > 0)  {
				$row = mysqli_fetch_assoc($sqlInnlegg);

				$innlegg = $row["antall_innlegg"];

				echo ",";
				echo utf8_encode($innlegg);
			}
			//Kommentarer
			$sqlKom = mysqli_query($conn,"SELECT COUNT(kom_id) AS antall_kom
									FROM bruker
									INNER JOIN kommentar
									WHERE brukernavn = '$user'
									AND kom_bruker = bruker_id");
			$count = mysqli_num_rows($sqlKom);
		
			if($count > 0)  {
				$row = mysqli_fetch_assoc($sqlKom);

				$kommentarer = $row["antall_kom"];

				echo ",";
				echo utf8_encode($kommentarer);
			}
			
			//Bilde
			$sql = mysqli_query($conn,"SELECT profil_bilde, bruker_id, lokasjon
										FROM profilbilde
										LEFT JOIN bruker
										ON bruker_id = profil_bruker
										LEFT JOIN bilde
										ON bilde_id = profil_bilde
										WHERE brukernavn='$user'");
			$count = mysqli_num_rows($sql);
		
			if($count > 0)  {
				$row = mysqli_fetch_assoc($sql);

				$lokasjon = $row["lokasjon"];

				echo ",";
				echo utf8_encode($lokasjon);
			}
		}
	} else {
		echo "Not Connected...!";
	}
?>