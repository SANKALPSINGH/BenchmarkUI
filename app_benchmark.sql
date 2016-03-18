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
INSERT INTO `actions` VALUES ('App Kill and Reopen'),('App Kill Resume'),('App Opening'),('Chat Opening'),('Chat Scroll'),('Compose Screen Opening');
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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `run`
--

LOCK TABLES `run` WRITE;
/*!40000 ALTER TABLE `run` DISABLE KEYS */;
INSERT INTO `run` VALUES (1,'4.2.1'),(2,'4.3.1'),(3,'0'),(4,'0'),(5,'0'),(6,'0'),(7,'0'),(8,'0'),(9,'0'),(10,'0'),(11,'0'),(12,'0'),(13,'0'),(14,'0'),(15,'0'),(16,'0'),(17,'0'),(18,'0'),(19,'0'),(20,'0'),(21,'0'),(22,'0'),(23,'0'),(24,'0'),(25,'0'),(26,'4.0.8.81'),(27,'4.2.0.8.85');
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
INSERT INTO `run_data` VALUES (1,'Chat Opening',100,200,100,200,100,700,140.1),(1,'Chat Scroll',110,210,110,210,110,710,114.1),(2,'Chat Scroll',1100,2100,1100,2100,1100,7100,1140.1),(3,'App Opening',2100,2100,2100,2100,2100,9100,3140.1),(4,'App Kill and Reopen',21000,21000,21000,21000,21000,91000,31400.1),(5,'Chat Opening',110,210,110,210,110,710,114.1),(6,'Chat Scroll',1100,2100,1100,2100,1100,7100,1140.1),(7,'App Opening',2100,2100,2100,2100,2100,9100,3140.1),(8,'App Kill and Reopen',21000,21000,21000,21000,21000,91000,31400.1),(73,'App Kill and Reopen',442,397,409,402,400,2050,410),(73,'App Kill Resume',305,311,322,308,464,1710,342),(73,'App Opening',62214,63526,64855,66184,67489,324268,64853.6),(73,'Chat Opening',300,327,471,352,319,1769,353.8),(73,'Chat Scroll',1,2,1,1,4,9,1.8),(73,'Compose Screen Opening',179,81,96,114,107,577,115.4),(74,'App Kill and Reopen',430,409,415,435,416,2105,421),(74,'App Kill Resume',292,303,309,321,306,1531,306.2),(74,'App Opening',310534,311875,313342,314747,316049,1566547,313309.4),(74,'Chat Opening',318,323,327,335,489,1792,358.4),(74,'Chat Scroll',11,12,19,39,22,103,20.6),(74,'Compose Screen Opening',182,106,135,89,89,601,120.2),(75,'App Kill and Reopen',549,432,437,438,409,2265,453),(75,'App Kill Resume',295,291,297,304,317,1504,300.8),(75,'App Opening',1128570,1129987,1131328,1132643,1133955,5656483,1131296.6),(75,'Chat Opening',324,331,329,388,530,1902,380.4),(75,'Chat Scroll',103,106,83,125,87,504,100.8),(75,'Compose Screen Opening',218,156,147,101,107,729,145.8),(76,'App Kill and Reopen',470,398,435,450,431,2184,436.8),(76,'App Kill Resume',339,337,292,314,312,1594,318.8),(76,'App Opening',2549327,2550738,2552077,2553374,2554751,12760267,2552053.4),(76,'Chat Opening',488,361,311,329,329,1818,363.6),(76,'Chat Scroll',302,259,254,270,236,1321,264.2),(76,'Compose Screen Opening',250,105,110,95,119,679,135.8);
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
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `run_percentile`
--

LOCK TABLES `run_percentile` WRITE;
/*!40000 ALTER TABLE `run_percentile` DISABLE KEYS */;
INSERT INTO `run_percentile` VALUES (1,1,50),(2,1,80),(3,1,95),(4,1,99),(5,2,50),(6,2,80),(7,2,95),(8,2,99),(9,5,50),(10,5,80),(11,5,95),(12,5,99),(13,6,50),(14,6,80),(15,6,95),(16,6,99),(17,7,50),(18,7,80),(19,8,50),(20,8,80),(21,9,50),(22,9,80),(23,10,50),(24,10,80),(25,10,95),(26,11,50),(27,11,80),(28,11,95),(29,11,99),(30,12,50),(31,12,80),(32,12,95),(33,13,50),(34,13,80),(35,14,50),(36,14,80),(37,15,50),(38,15,80),(39,16,50),(40,16,80),(41,17,50),(42,17,80),(43,18,50),(48,18,80),(44,19,50),(45,19,80),(46,19,95),(47,19,99),(49,20,50),(50,20,80),(51,21,50),(52,21,80),(53,22,50),(54,22,80),(55,22,95),(56,22,99),(57,23,50),(58,23,80),(59,23,95),(60,23,99),(61,24,50),(62,24,80),(63,24,95),(64,24,99),(65,25,50),(66,25,80),(67,25,95),(68,25,99),(69,26,50),(70,26,80),(71,26,95),(72,26,99),(73,27,50),(74,27,80),(75,27,95),(76,27,99);
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

-- Dump completed on 2016-03-17 18:00:52
