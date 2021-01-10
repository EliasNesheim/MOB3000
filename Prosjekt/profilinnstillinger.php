<?php 
	//PHP for opdatering av brukerinfo

	//Tilkobling (inkluder egen fil!)
	include "db_include.php";

	//Logget inn bruker
	//$user = "test";
	$user = $_GET["user"];
	
	$salt = 'IT3_2020';

	//Fra inputfelt
	$inst = $_GET["inst"];
	$fag = $_GET["fag"];
	//$bruker = $_GET["bruker"];
	$gpassord = $_GET["gpassord"];
	//$gpassord = sha1($salt.$gpassord);
	$npassord = $_GET["npassord"];
	//$npassord = sha1($salt.$npassord);
	$email = $_GET["email"];

	if($conn) {
		
		// if(!empty($bruker) && !empty($email) && $inst != 0 && $fag != 0) {
		
		echo "Oppdatert:";
		// }
		
		//Brukernavn
		// if (!empty($bruker)) {
		// 	$sqlBruker = "UPDATE bruker
		// 					SET brukernavn = '$bruker'
		// 					WHERE brukernavn = '$user'";

		// 	mysqli_query($conn, $sqlBruker);
		// 	echo "\nBruker";
		// }

		//Epost
		if (!empty($email)) {
			$sqlMail = "UPDATE bruker
						SET epost = '$email' 
						WHERE brukernavn = '$user'";

			mysqli_query($conn, $sqlMail);
			echo "\nE-post";
		}

		//Institusjon/Fag
		if ($inst != 0) {
			$sqlInst = "UPDATE bruker
						SET bruker_inst = '$inst'
						WHERE brukernavn = '$user'";

			mysqli_query($conn, $sqlInst);
			echo "\nInstitusjon";
		}

		if ($fag != 0) {
			$sqlFag = "UPDATE bruker
						SET bruker_fag = '$fag'
						WHERE brukernavn = '$user'";

			mysqli_query($conn, $sqlFag);
			echo "\nFag";
		}

		//Passord
		$sqlFinnPassord = mysqli_query($conn, "SELECT passord 
												FROM bruker
												WHERE brukernavn = '$user'");
		$row = mysqli_fetch_assoc($sqlFinnPassord);
		$currentPass = $row["passord"];
		//$currentPass = sha1($salt.$currentPass);


		//Sjekker om gammelt passord skrevet inn er riktig
		if (!empty($gpassord) && !empty($currentPass)) {
			$gpassord = sha1($salt.$gpassord);
			$npassord = sha1($salt.$npassord);
			if($gpassord == $currentPass) {

				$sqlPassord = "UPDATE bruker
								SET passord = '$npassord' 
								WHERE brukernavn = '$user'";

				mysqli_query($conn, $sqlPassord);
				echo "\nPassord";
			} else {
				echo "\nERROR: Feil gammelt passord ";
			}
		}

	} else {echo "ingen connection!";}
	
?>