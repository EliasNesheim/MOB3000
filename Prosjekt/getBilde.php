<?php
include "db_include.php";


//Input data
$linje = $_GET['bilde_id'];




$sql = mysqli_query($conn, "SELECT * FROM bilde WHERE bilde_id ='$linje');

  if ($sql) {
    while ( $row = $sql->fetch_assoc())  {
	  echo utf8_encode($row['lokasjon']);
	  echo (',');
	}
  } else {
	echo("Error description: " . mysqli_error($conn));
    echo "Det oppstod et problem";
  }


mysqli_close($conn);
?>
