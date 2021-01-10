<?php
    include "db_include.php";


    //Input data

    $user = $_GET["user"];
    //$user = "test";
    //echo "test";


    $sql = mysqli_query($conn, "SELECT kom_id, kom_innlegg, kom_bruker, kom_tekst, kom_tid, brukernavn , tittel
                                FROM kommentar, bruker, innlegg 
                                WHERE kommentar.kom_bruker = bruker_id
                                AND kom_innlegg = inn_id
                                AND brukernavn = '$user'");
    $count = mysqli_num_rows($sql);
    if($count > 0)  {
        if ($sql) {
            while ( $row = $sql->fetch_assoc())  {
                echo $row['brukernavn'];
                echo (',');
                echo $row['tittel'];
                echo (',');
                echo $row['kom_tekst'];
                echo (',');
                echo $row['kom_tid'];
                echo (',');
                echo $row['kom_id'];
                echo ('/');
                }
        }
        else {
            echo("Error description: " . mysqli_error($conn));
            echo "Det oppstod et problem";
        }
    } else {
        echo "Ingen kommentarer";
    }

    mysqli_close($conn);
?>
