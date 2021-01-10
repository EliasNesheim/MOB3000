<?php
include "db_include.php";

$sqlfag = "SELECT * FROM fag";
$sqlinst = "SELECT * FROM institusjon";

$resultfag = mysqli_query($conn, $sqlfag);
$resultinst = mysqli_query($conn, $sqlinst);

// output data of each row
//Fetch into associative array
while ( $row = $resultfag->fetch_assoc())  {
  echo utf8_encode($row['fag_navn']);
  echo (',');
}
echo ('/');

while ( $row = $resultinst->fetch_assoc())  {
  echo utf8_encode($row['inst_navn']);
  echo (',');
}

mysqli_close($conn);
?>
