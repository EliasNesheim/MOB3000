<?php
include "db_include.php";


//Input data

$user = $_GET["user"];
//$user = "test";
//echo "test";


$sql = mysqli_query($conn, "SELECT inn_id, inn_bruker, tittel, inn_tekst, inn_tid, brukernavn 
							FROM innlegg, bruker WHERE innlegg.inn_bruker = bruker_id
                            AND brukernavn = '$user'");
$count = mysqli_num_rows($sql);
if($count > 0)  {
    if ($sql) {
        while ( $row = $sql->fetch_assoc())  {
            echo $row['brukernavn'];
            echo (',');
            echo $row['tittel'];
            echo (',');
            echo $row['inn_tekst'];
            echo (',');
            echo $row['inn_tid'];
            echo (',');
            echo $row['inn_id'];
            echo ('/');
            }
    }
    else {
        echo("Error description: " . mysqli_error($conn));
        echo "Det oppstod et problem";
    }
} else {
    echo "Ingen innlegg";
}

mysqli_close($conn);
?>
