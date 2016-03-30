-- MySQL dump 10.13  Distrib 5.7.10, for osx10.10 (x86_64)
--
-- Host: localhost    Database: app_benchmark
-- ------------------------------------------------------
-- Server version	5.7.10

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
-- Table structure for table `actions`
--

DROP TABLE IF EXISTS `actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actions` (
  `actions` varchar(55) NOT NULL,
  PRIMARY KEY (`actions`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actions`
--

LOCK TABLES `actions` WRITE;
/*!40000 ALTER TABLE `actions` DISABLE KEYS */;
INSERT INTO `actions` VALUES ('App Force Kill'),('App Force Stop'),('Chat Thread Opening'),('Chat Thread Scroll'),('Contact Loading time in Compose screen');
/*!40000 ALTER TABLE `actions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `percentile`
--

DROP TABLE IF EXISTS `percentile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `percentile` (
  `percentile` int(11) NOT NULL,
  `msisdn` varchar(45) NOT NULL,
  `msisdn_to_test_on` varchar(45) NOT NULL,
  PRIMARY KEY (`percentile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `percentile`
--

LOCK TABLES `percentile` WRITE;
/*!40000 ALTER TABLE `percentile` DISABLE KEYS */;
INSERT INTO `percentile` VALUES (50,'4444440100','+919900000001'),(80,'4444440101','+919900000001'),(95,'4444440105','+919900000001'),(99,'4444440107','+919900000015');
/*!40000 ALTER TABLE `percentile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `run`
--

DROP TABLE IF EXISTS `run`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `run` (
  `run_id` int(11) NOT NULL AUTO_INCREMENT,
  `apk_version` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`run_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `run`
--

LOCK TABLES `run` WRITE;
/*!40000 ALTER TABLE `run` DISABLE KEYS */;
INSERT INTO `run` VALUES (39,'4.2.0.82'),(41,'4.2.0.82.34'),(48,'4.0.8.81'),(50,'4.2.0.82.37.1.oem.prebundle.games');
/*!40000 ALTER TABLE `run` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `run_data`
--

DROP TABLE IF EXISTS `run_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `run_data` (
  `run_percentile` int(11) NOT NULL,
  `action` varchar(45) NOT NULL,
  `reading1` double NOT NULL,
  `reading2` double NOT NULL,
  `reading3` double NOT NULL,
  `reading4` double NOT NULL,
  `reading5` double NOT NULL,
  `total` double NOT NULL,
  `average` double NOT NULL,
  UNIQUE KEY `uniqueness` (`run_percentile`,`action`),
  KEY `run_percentile_id_idx` (`run_percentile`),
  KEY `action_idx` (`action`),
  CONSTRAINT `action` FOREIGN KEY (`action`) REFERENCES `actions` (`actions`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `run_percentile_id` FOREIGN KEY (`run_percentile`) REFERENCES `run_percentile` (`run_percentile_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `run_data`
--

LOCK TABLES `run_data` WRITE;
/*!40000 ALTER TABLE `run_data` DISABLE KEYS */;
INSERT INTO `run_data` VALUES (114,'App Force Kill',720,749,775,712,725,7279,1455.8),(114,'App Force Stop',718,702,667,686,825,7279,1455.8),(114,'Chat Thread Opening',268,782,338,381,362,335,302),(114,'Chat Thread Scroll',1,1,4,1,9,16,3.2),(114,'Contact Loading time in Compose screen',163,116,179,106,142,706,141.2),(115,'App Force Kill',763,732,704,803,760,7741,1548.2),(115,'App Force Stop',817,793,764,799,806,7741,1548.2),(115,'Chat Thread Opening',323,329,375,309,345,1681,336.2),(115,'Chat Thread Scroll',18,25,24,23,10,100,20),(115,'Contact Loading time in Compose screen',169,79,98,141,83,570,114),(116,'App Force Kill',785,749,784,728,704,7814,1562.8),(116,'App Force Stop',931,800,767,800,766,7814,1562.8),(116,'Chat Thread Opening',358,312,336,347,369,1722,344.4),(116,'Chat Thread Scroll',106,80,118,91,64,459,91.8),(116,'Contact Loading time in Compose screen',220,91,114,94,106,625,125),(117,'App Force Kill',810,745,767,762,778,7974,1594.8),(117,'App Force Stop',871,804,809,828,800,7974,1594.8),(117,'Chat Thread Opening',333,355,346,334,319,1687,337.4),(117,'Chat Thread Scroll',251,210,324,265,270,1320,264),(117,'Contact Loading time in Compose screen',208,94,92,87,93,574,114.8),(120,'App Force Kill',737,713,696,677,690,7397,1479.4),(120,'App Force Stop',789,757,782,761,795,7397,1479.4),(120,'Chat Thread Opening',319,277,388,304,323,1611,322.2),(120,'Chat Thread Scroll',5,1,2,2,2,12,2.4),(120,'Contact Loading time in Compose screen',204,97,141,102,85,629,125.8),(121,'App Force Kill',772,731,674,749,734,7539,1507.8),(121,'App Force Stop',803,774,751,764,787,7539,1507.8),(121,'Chat Thread Opening',312,337,346,377,344,1716,343.2),(121,'Chat Thread Scroll',23,16,21,30,31,121,24.2),(121,'Contact Loading time in Compose screen',215,104,90,98,119,626,125.2),(122,'App Force Kill',751,724,691,676,722,7471,1494.2),(122,'App Force Stop',827,782,783,777,738,7471,1494.2),(122,'Chat Thread Opening',307,299,368,312,320,1606,321.2),(122,'Chat Thread Scroll',77,103,114,89,83,466,93.2),(122,'Contact Loading time in Compose screen',208,89,100,113,81,591,118.2),(123,'App Force Kill',785,708,737,712,697,7601,1520.2),(123,'App Force Stop',797,768,813,816,768,7601,1520.2),(123,'Chat Thread Opening',315,350,346,345,343,1699,339.8),(123,'Chat Thread Scroll',267,262,256,237,240,1262,252.4),(123,'Contact Loading time in Compose screen',224,90,83,127,99,623,124.6),(139,'App Force Kill',601,766,692,633,641,6377,1275.4),(139,'App Force Stop',647,610,576,612,599,6377,1275.4),(139,'Chat Thread Opening',298,259,330,265,283,1435,287),(139,'Chat Thread Scroll',1,1,1,2,2,7,1.4),(139,'Contact Loading time in Compose screen',191,102,119,103,103,618,123.6),(140,'App Force Kill',732,630,659,658,616,6998,1399.6),(140,'App Force Stop',740,742,743,724,754,6998,1399.6),(140,'Chat Thread Opening',290,263,274,285,288,1400,280),(140,'Chat Thread Scroll',12,16,12,14,20,74,14.8),(140,'Contact Loading time in Compose screen',172,140,91,118,114,635,127),(141,'App Force Kill',713,606,655,637,655,7099,1419.8),(141,'App Force Stop',838,771,754,730,740,7099,1419.8),(141,'Chat Thread Opening',277,270,493,293,272,1605,321),(141,'Chat Thread Scroll',85,97,77,84,63,406,81.2),(141,'Contact Loading time in Compose screen',170,112,76,76,88,522,104.4),(142,'App Force Kill',736,666,718,700,663,7353,1470.6),(142,'App Force Stop',884,752,730,757,747,7353,1470.6),(142,'Chat Thread Opening',276,265,286,301,265,1393,278.6),(142,'Chat Thread Scroll',282,274,240,282,248,1326,265.2),(142,'Contact Loading time in Compose screen',201,117,111,80,75,584,116.8),(147,'App Force Kill',755,715,712,719,714,7488,1497.6),(147,'App Force Stop',876,779,742,738,738,7488,1497.6),(147,'Chat Thread Opening',371,340,344,334,344,1733,346.6),(147,'Chat Thread Scroll',2,1,18,4,1,26,5.2),(147,'Contact Loading time in Compose screen',178,84,88,103,78,531,106.2),(148,'App Force Kill',766,712,678,687,674,7375,1475),(148,'App Force Stop',788,793,747,765,765,7375,1475),(148,'Chat Thread Opening',352,378,341,347,349,1767,353.4),(148,'Chat Thread Scroll',19,13,23,24,23,102,20.4),(148,'Contact Loading time in Compose screen',223,87,117,84,125,636,127.2),(149,'App Force Kill',750,698,723,687,681,7528,1505.6),(149,'App Force Stop',849,776,796,770,798,7528,1505.6),(149,'Chat Thread Opening',336,346,324,314,333,1653,330.6),(149,'Chat Thread Scroll',125,105,85,119,79,513,102.6),(149,'Contact Loading time in Compose screen',204,86,96,85,87,558,111.6),(150,'App Force Kill',790,763,752,751,740,7865,1573),(150,'App Force Stop',898,810,792,796,773,7865,1573),(150,'Chat Thread Opening',377,330,368,374,385,1834,366.8),(150,'Chat Thread Scroll',247,254,251,220,252,1224,244.8),(150,'Contact Loading time in Compose screen',228,80,91,94,109,602,120.4);
/*!40000 ALTER TABLE `run_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `run_percentile`
--

DROP TABLE IF EXISTS `run_percentile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `run_percentile` (
  `run_percentile_id` int(11) NOT NULL AUTO_INCREMENT,
  `run_id` int(11) NOT NULL,
  `run_percentile` int(11) NOT NULL,
  PRIMARY KEY (`run_percentile_id`),
  UNIQUE KEY `run_percentile_id_UNIQUE` (`run_percentile_id`),
  UNIQUE KEY `uniqueness` (`run_id`,`run_percentile`),
  KEY `run_id_idx` (`run_id`),
  KEY `run_percentile_idx` (`run_percentile`),
  CONSTRAINT `run_id` FOREIGN KEY (`run_id`) REFERENCES `run` (`run_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `run_percentile` FOREIGN KEY (`run_percentile`) REFERENCES `percentile` (`percentile`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `run_percentile`
--

LOCK TABLES `run_percentile` WRITE;
/*!40000 ALTER TABLE `run_percentile` DISABLE KEYS */;
INSERT INTO `run_percentile` VALUES (114,39,50),(115,39,80),(116,39,95),(117,39,99),(120,41,50),(121,41,80),(122,41,95),(123,41,99),(139,48,50),(140,48,80),(141,48,95),(142,48,99),(147,50,50),(148,50,80),(149,50,95),(150,50,99);
/*!40000 ALTER TABLE `run_percentile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-30 10:17:21
