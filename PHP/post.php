
<?php
include "db_include.php";


//Input data
$brukernavn = $_GET['user'];
$tittel = $_GET['tittel'];
$tekst = $_GET['tekst'];
$fag = $_GET['fag_id'];

$now = date("Y-m-d H:i:s");
$tittel = str_replace('%', ' ', $tittel);
$tekst = str_replace('%', ' ', $tekst);

$sql = mysqli_query($conn, "INSERT INTO innlegg (inn_bruker, tittel, inn_tekst, inn_tid, fag_id) values ('$brukernavn', '$tittel', '$tekst', '$now', '$fag')");


  if ($sql) {
    echo "success";
  } else {
	echo("Error description: " . mysqli_error($conn));
    echo "Det oppstod et problem";
  }


mysqli_close($conn);
?>
