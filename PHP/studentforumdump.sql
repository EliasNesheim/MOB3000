-- MySQL dump 10.13  Distrib 5.7.31, for Linux (x86_64)
--
-- Host: localhost    Database: studentforum
-- ------------------------------------------------------
-- Server version	5.7.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Innleggbilde`
--

DROP TABLE IF EXISTS `Innleggbilde`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Innleggbilde` (
  `innbilde_bilde` int(10) NOT NULL,
  `innbilde_bruker` int(10) NOT NULL,
  PRIMARY KEY (`innbilde_bilde`,`innbilde_bruker`),
  KEY `InnleggbildeBrukerFK` (`innbilde_bruker`),
  CONSTRAINT `InnleggbildeBildeFK` FOREIGN KEY (`innbilde_bilde`) REFERENCES `bilde` (`bilde_id`),
  CONSTRAINT `InnleggbildeBrukerFK` FOREIGN KEY (`innbilde_bruker`) REFERENCES `bruker` (`bruker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Innleggbilde`
--

LOCK TABLES `Innleggbilde` WRITE;
/*!40000 ALTER TABLE `Innleggbilde` DISABLE KEYS */;
/*!40000 ALTER TABLE `Innleggbilde` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Kommentarbilde`
--

DROP TABLE IF EXISTS `Kommentarbilde`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Kommentarbilde` (
  `kombilde_bilde` int(10) NOT NULL,
  `kombilde_bruker` int(10) NOT NULL,
  PRIMARY KEY (`kombilde_bilde`,`kombilde_bruker`),
  KEY `KommentarbildeBrukerFK` (`kombilde_bruker`),
  CONSTRAINT `KommentarbildeBildeFK` FOREIGN KEY (`kombilde_bilde`) REFERENCES `bilde` (`bilde_id`),
  CONSTRAINT `KommentarbildeBrukerFK` FOREIGN KEY (`kombilde_bruker`) REFERENCES `bruker` (`bruker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Kommentarbilde`
--

LOCK TABLES `Kommentarbilde` WRITE;
/*!40000 ALTER TABLE `Kommentarbilde` DISABLE KEYS */;
/*!40000 ALTER TABLE `Kommentarbilde` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bilde`
--

DROP TABLE IF EXISTS `bilde`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bilde` (
  `bilde_id` int(10) NOT NULL AUTO_INCREMENT,
  `lokasjon` varchar(255) DEFAULT NULL,
  `bilde` blob,
  PRIMARY KEY (`bilde_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bilde`
--

LOCK TABLES `bilde` WRITE;
/*!40000 ALTER TABLE `bilde` DISABLE KEYS */;
/*!40000 ALTER TABLE `bilde` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bruker`
--

DROP TABLE IF EXISTS `bruker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bruker` (
  `bruker_id` int(10) NOT NULL AUTO_INCREMENT,
  `bruker_inst` int(10) DEFAULT NULL,
  `bruker_fag` int(10) DEFAULT NULL,
  `brukernavn` varchar(45) DEFAULT NULL,
  `passord` varchar(45) DEFAULT NULL,
  `epost` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`bruker_id`),
  KEY `BrukerInstitusjonFK` (`bruker_inst`),
  KEY `BrukerFagFK` (`bruker_fag`),
  CONSTRAINT `BrukerFagFK` FOREIGN KEY (`bruker_fag`) REFERENCES `fag` (`fag_id`),
  CONSTRAINT `BrukerInstitusjonFK` FOREIGN KEY (`bruker_inst`) REFERENCES `institusjon` (`inst_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bruker`
--

LOCK TABLES `bruker` WRITE;
/*!40000 ALTER TABLE `bruker` DISABLE KEYS */;
INSERT INTO `bruker` VALUES (1,1,1,'test','test','test@test.no'),(2,2,2,'bruker','bruker','bruker@bruker.no'),(3,1,5,'elias','ceb6b846e7558b4077c3b88de733aed890ece908','elias@elias.no'),(4,1,6,'elias2','2a644539cbc13518e3f6465f76940c781440e543','elias2@elias.no'),(5,2,3,'eivind','f988ca37f1f57ac1fd5fe66de974ca070eb97a70','eivind@gmail.com'),(6,2,5,'elias3','06da7b7fb75013dee95b843316c6862a6ebc65e9','elias3@gmail.com'),(7,3,1,'elias4','48b67b819590880bd1aef47f6f14c4a2d6ddb131','elias4@gmail.com');
/*!40000 ALTER TABLE `bruker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fag`
--

DROP TABLE IF EXISTS `fag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fag` (
  `fag_id` int(10) NOT NULL AUTO_INCREMENT,
  `fag_navn` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`fag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fag`
--

LOCK TABLES `fag` WRITE;
/*!40000 ALTER TABLE `fag` DISABLE KEYS */;
INSERT INTO `fag` VALUES (1,'IT'),(2,'Økonomi'),(3,'Juss'),(4,'Markedsføring'),(5,'Pedagogikk'),(6,'Naturvitenskap');
/*!40000 ALTER TABLE `fag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `innlegg`
--

DROP TABLE IF EXISTS `innlegg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `innlegg` (
  `inn_id` int(10) NOT NULL AUTO_INCREMENT,
  `inn_bruker` int(10) DEFAULT NULL,
  `tittel` varchar(255) DEFAULT NULL,
  `inn_tekst` varchar(1024) DEFAULT NULL,
  `inn_tid` datetime DEFAULT NULL,
  `bilde_id` int(10) DEFAULT NULL,
  `fag_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`inn_id`),
  KEY `InnleggBrukerFK` (`inn_bruker`),
  KEY `bilde_id` (`bilde_id`),
  KEY `fag_id` (`fag_id`),
  CONSTRAINT `InnleggBrukerFK` FOREIGN KEY (`inn_bruker`) REFERENCES `bruker` (`bruker_id`),
  CONSTRAINT `innlegg_ibfk_1` FOREIGN KEY (`bilde_id`) REFERENCES `bilde` (`bilde_id`),
  CONSTRAINT `innlegg_ibfk_2` FOREIGN KEY (`fag_id`) REFERENCES `fag` (`fag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `innlegg`
--

LOCK TABLES `innlegg` WRITE;
/*!40000 ALTER TABLE `innlegg` DISABLE KEYS */;
INSERT INTO `innlegg` VALUES (1,4,'innlegg TITTEL','En hau med tekst av alt slags slag','2020-12-09 14:14:14',NULL,1),(2,4,'innlegg TITTEL','En hau med tekst av alt slags slag','2020-12-09 14:14:14',NULL,2),(3,4,'innlegg TITTEL','En hau med tekst av alt slags slag','2020-12-09 14:14:14',NULL,3),(4,4,'innlegg TITTEL','En hau med tekst av alt slags slag','2020-12-09 14:14:14',NULL,4),(5,4,'innlegg TITTEL','En hau med tekst av alt slags slag','2020-12-09 14:14:14',NULL,5),(6,4,'innlegg TITTEL','En hau med tekst av alt slags slag','2020-12-09 14:14:14',NULL,6),(7,6,'afwsfasgasfasf','asfsafasfasfasfasf','2020-12-09 16:10:12',NULL,5);
/*!40000 ALTER TABLE `innlegg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `institusjon`
--

DROP TABLE IF EXISTS `institusjon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `institusjon` (
  `inst_id` int(10) NOT NULL AUTO_INCREMENT,
  `inst_navn` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`inst_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institusjon`
--

LOCK TABLES `institusjon` WRITE;
/*!40000 ALTER TABLE `institusjon` DISABLE KEYS */;
INSERT INTO `institusjon` VALUES (1,'USN'),(2,'BI'),(3,'NTNU');
/*!40000 ALTER TABLE `institusjon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kommentar`
--

DROP TABLE IF EXISTS `kommentar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kommentar` (
  `kom_id` int(10) NOT NULL AUTO_INCREMENT,
  `kom_innlegg` int(10) DEFAULT NULL,
  `kom_bruker` int(10) DEFAULT NULL,
  `kom_tekst` varchar(1024) DEFAULT NULL,
  `kom_tid` datetime DEFAULT NULL,
  PRIMARY KEY (`kom_id`),
  KEY `KommentarInnleggFK` (`kom_innlegg`),
  KEY `KommentarBrukerFK` (`kom_bruker`),
  CONSTRAINT `KommentarBrukerFK` FOREIGN KEY (`kom_bruker`) REFERENCES `bruker` (`bruker_id`),
  CONSTRAINT `KommentarInnleggFK` FOREIGN KEY (`kom_innlegg`) REFERENCES `innlegg` (`inn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kommentar`
--

LOCK TABLES `kommentar` WRITE;
/*!40000 ALTER TABLE `kommentar` DISABLE KEYS */;
INSERT INTO `kommentar` VALUES (1,1,7,'hei',NULL),(2,2,6,'heui8',NULL),(3,3,6,'asfasffasasf',NULL),(4,7,6,'daSASDADS',NULL),(5,5,6,'fafas',NULL),(6,5,6,'fafas',NULL),(7,7,6,'asdfgasgfas',NULL);
/*!40000 ALTER TABLE `kommentar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profilbilde`
--

DROP TABLE IF EXISTS `profilbilde`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profilbilde` (
  `profil_bilde` int(10) NOT NULL,
  `profil_bruker` int(10) NOT NULL,
  PRIMARY KEY (`profil_bilde`,`profil_bruker`),
  KEY `ProfilbildeBrukerFK` (`profil_bruker`),
  CONSTRAINT `ProfilbildeBildeFK` FOREIGN KEY (`profil_bilde`) REFERENCES `bilde` (`bilde_id`),
  CONSTRAINT `ProfilbildeBrukerFK` FOREIGN KEY (`profil_bruker`) REFERENCES `bruker` (`bruker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profilbilde`
--

LOCK TABLES `profilbilde` WRITE;
/*!40000 ALTER TABLE `profilbilde` DISABLE KEYS */;
/*!40000 ALTER TABLE `profilbilde` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-09 22:47:35
