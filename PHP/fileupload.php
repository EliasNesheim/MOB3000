<?php
    //PHP for å laste opp profilbilde

    //Tilkobling
    include "db_include.php";

    //Logget inn bruker (session?!)
    $user = $_GET["user"];

    $img = $_POST['upload'];

    $filename = "IMG".rand().".jpg";
    file_put_contents("bilder/".$filename,base64_decode($img));
    $filnavn = "bilder/"."$filename";

    $qry = "INSERT INTO bilde (lokasjon)
        VALUES('$filnavn')";

    $res = mysqli_query($conn,$qry);

    //Legger til i profilbilde tabell
    $sql = mysqli_query($conn,"SELECT bruker_id, bilde_id
                                FROM bruker, bilde
                                WHERE brukernavn='$user'
                                AND lokasjon = '$filnavn'");
    $count = mysqli_num_rows($sql);

    if($count > 0)  {
        $row = mysqli_fetch_assoc($sql);

        $brukerid = $row["bruker_id"];
        $bildeid = $row["bilde_id"];
        
        //Sletter gamle før oppdatering
        $sql_slett = mysqli_query($conn,"DELETE FROM profilbilde WHERE profil_bruker = '$brukerid'");

        $sqlBilde = "INSERT INTO profilbilde (profil_bilde, profil_bruker)
                VALUES($bildeid, $brukerid)";
        $resultat = mysqli_query($conn,$sqlBilde);

        if($resultat == true) {
            echo "Oppdatert: Profilbilde";
        } else {
            echo "Error: sjekk internett tilkobling";
        }
    } else {
        echo "Fant ikke bruker";
    }
    

?>