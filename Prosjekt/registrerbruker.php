
<?php
  include "db_include.php";

  $salt = 'IT3_2020';

  //Input data
  $brukernavn = $_GET['bNavn'];
  $passord = $_GET['PW'];
  $institusjon = $_GET['inst'];
  $fag = $_GET['fag'];
  $mail = $_GET['email'];
  //$fag = utf8_encode($fag);
  
  // if (strpos($fag,"Okonomi")!== true) {
  //   $fag = "Økonomi";
  // }
  // echo "$fag";
  //krypterer passord
  $passord = sha1($salt.$passord);

  $sqlhentinst = mysqli_query($conn, "SELECT inst_id FROM institusjon WHERE inst_navn = '$institusjon'");
  $sqlhentfag = mysqli_query($conn, "SELECT fag_id FROM fag WHERE fag_navn = '$fag'");
  $sqlhentbruker = mysqli_query($conn, "SELECT brukernavn FROM bruker WHERE brukernavn = '$brukernavn'");

  $hentinst = mysqli_fetch_assoc($sqlhentinst);
  $hentfag = mysqli_fetch_assoc($sqlhentfag);

  $inst = $hentinst['inst_id'];
  $fag = $hentfag['fag_id'];

  if(mysqli_num_rows($sqlhentbruker) > 0) {
    echo "bruker eksisterer";
  } else {
    $sql = "INSERT INTO bruker (bruker_inst, bruker_fag, brukernavn, passord, epost) values($inst, $fag, '$brukernavn', '$passord', '$mail')";
    $result = mysqli_query($conn, $sql);

    if ($result) {
      echo "Brukeren er registrert";
    } else {
    echo("Error description: " . mysqli_error($conn));

      echo "Det oppstod et problem!";
    }
  }

  mysqli_close($conn);
?>
