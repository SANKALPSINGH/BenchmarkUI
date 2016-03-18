-- MySQL dump 10.13  Distrib 5.6.28, for osx10.8 (x86_64)
--
-- Host: localhost    Database: dexterdb
-- ------------------------------------------------------
-- Server version	5.6.28

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
-- Table structure for table `CurrentRunInfo`
--

DROP TABLE IF EXISTS `CurrentRunInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CurrentRunInfo` (
  `currentRunId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `runInfo` int(10) unsigned NOT NULL,
  `testCases` int(10) unsigned NOT NULL,
  `devices` varchar(100) NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`currentRunId`),
  KEY `runInfo` (`runInfo`),
  KEY `testCases` (`testCases`),
  KEY `devices` (`devices`),
  CONSTRAINT `currentruninfo_ibfk_1` FOREIGN KEY (`runInfo`) REFERENCES `RunInfo` (`runInfoId`),
  CONSTRAINT `currentruninfo_ibfk_2` FOREIGN KEY (`testCases`) REFERENCES `TestCases` (`testCaseId`),
  CONSTRAINT `currentruninfo_ibfk_3` FOREIGN KEY (`devices`) REFERENCES `Devices` (`serialId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CurrentRunInfo`
--

LOCK TABLES `CurrentRunInfo` WRITE;
/*!40000 ALTER TABLE `CurrentRunInfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `CurrentRunInfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DeviceType`
--

DROP TABLE IF EXISTS `DeviceType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DeviceType` (
  `deviceId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `deviceName` varchar(200) NOT NULL,
  `canonicalName` varchar(200) DEFAULT NULL,
  `OSVersion` varchar(50) DEFAULT NULL,
  `OSName` varchar(50) DEFAULT 'Android',
  PRIMARY KEY (`deviceId`),
  KEY `OSName` (`OSName`),
  CONSTRAINT `devicetype_ibfk_1` FOREIGN KEY (`OSName`) REFERENCES `OS` (`OSName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DeviceType`
--

LOCK TABLES `DeviceType` WRITE;
/*!40000 ALTER TABLE `DeviceType` DISABLE KEYS */;
/*!40000 ALTER TABLE `DeviceType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Devices`
--

DROP TABLE IF EXISTS `Devices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Devices` (
  `containerId` varchar(200) NOT NULL,
  `serialId` varchar(100) NOT NULL,
  `devID` varchar(100) NOT NULL,
  `busID` varchar(100) NOT NULL,
  `deviceId` int(10) unsigned NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`serialId`),
  KEY `deviceId` (`deviceId`),
  CONSTRAINT `devices_ibfk_1` FOREIGN KEY (`deviceId`) REFERENCES `DeviceType` (`deviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Devices`
--

LOCK TABLES `Devices` WRITE;
/*!40000 ALTER TABLE `Devices` DISABLE KEYS */;
/*!40000 ALTER TABLE `Devices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Methods`
--

DROP TABLE IF EXISTS `Methods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Methods` (
  `testCases` varchar(100) NOT NULL,
  `methodName` varchar(100) NOT NULL,
  `testCaseIdentifier` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`testCases`,`methodName`),
  UNIQUE KEY `testCases` (`testCases`),
  CONSTRAINT `methods_ibfk_1` FOREIGN KEY (`testCases`) REFERENCES `TestCases` (`testCaseName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Methods`
--

LOCK TABLES `Methods` WRITE;
/*!40000 ALTER TABLE `Methods` DISABLE KEYS */;
/*!40000 ALTER TABLE `Methods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OS`
--

DROP TABLE IF EXISTS `OS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OS` (
  `OSName` varchar(50) NOT NULL,
  PRIMARY KEY (`OSName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OS`
--

LOCK TABLES `OS` WRITE;
/*!40000 ALTER TABLE `OS` DISABLE KEYS */;
/*!40000 ALTER TABLE `OS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Roles`
--

DROP TABLE IF EXISTS `Roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Roles` (
  `roleId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `roleName` varchar(100) NOT NULL,
  PRIMARY KEY (`roleId`),
  UNIQUE KEY `roleName` (`roleName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Roles`
--

LOCK TABLES `Roles` WRITE;
/*!40000 ALTER TABLE `Roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `Roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Run`
--

DROP TABLE IF EXISTS `Run`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Run` (
  `runId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(200) NOT NULL,
  `squadName` varchar(500) NOT NULL,
  `distributed` bit(1) DEFAULT NULL,
  `template` int(10) unsigned NOT NULL,
  PRIMARY KEY (`runId`),
  UNIQUE KEY `squadName` (`squadName`),
  KEY `userName` (`userName`),
  KEY `template` (`template`),
  CONSTRAINT `run_ibfk_1` FOREIGN KEY (`userName`) REFERENCES `Users` (`userEmailID`),
  CONSTRAINT `run_ibfk_2` FOREIGN KEY (`squadName`) REFERENCES `Squads` (`squadName`),
  CONSTRAINT `run_ibfk_3` FOREIGN KEY (`template`) REFERENCES `Template` (`templateId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Run`
--

LOCK TABLES `Run` WRITE;
/*!40000 ALTER TABLE `Run` DISABLE KEYS */;
/*!40000 ALTER TABLE `Run` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RunInfo`
--

DROP TABLE IF EXISTS `RunInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RunInfo` (
  `runInfoId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `runId` int(10) unsigned NOT NULL,
  `deviceType` int(10) unsigned DEFAULT 'ANDROID',
  PRIMARY KEY (`runInfoId`),
  KEY `runId` (`runId`),
  KEY `deviceType` (`deviceType`),
  CONSTRAINT `runinfo_ibfk_1` FOREIGN KEY (`runId`) REFERENCES `Run` (`runId`),
  CONSTRAINT `runinfo_ibfk_2` FOREIGN KEY (`deviceType`) REFERENCES `deviceType` (`deviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RunInfo`
--

LOCK TABLES `RunInfo` WRITE;
/*!40000 ALTER TABLE `RunInfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `RunInfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Squads`
--

DROP TABLE IF EXISTS `Squads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Squads` (
  `squadEmailId` varchar(200) NOT NULL,
  `squadName` varchar(500) NOT NULL,
  PRIMARY KEY (`squadEmailId`),
  UNIQUE KEY `squadName` (`squadName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Squads`
--

LOCK TABLES `Squads` WRITE;
/*!40000 ALTER TABLE `Squads` DISABLE KEYS */;
/*!40000 ALTER TABLE `Squads` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Template`
--

DROP TABLE IF EXISTS `Template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Template` (
  `templateId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `templateName` varchar(500) NOT NULL,
  `toolType` varchar(50) NOT NULL,
  PRIMARY KEY (`templateId`),
  UNIQUE KEY `templateName` (`templateName`),
  UNIQUE KEY `toolType` (`toolType`),
  CONSTRAINT `template_ibfk_1` FOREIGN KEY (`toolType`) REFERENCES `Tools` (`toolType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Template`
--

LOCK TABLES `Template` WRITE;
/*!40000 ALTER TABLE `Template` DISABLE KEYS */;
/*!40000 ALTER TABLE `Template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TestCases`
--

DROP TABLE IF EXISTS `TestCases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TestCases` (
  `testCaseId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `templateId` int(10) unsigned NOT NULL,
  `testCaseName` varchar(100) NOT NULL,
  `description` text,
  `packageName` varchar(500) NOT NULL,
  PRIMARY KEY (`testCaseId`),
  UNIQUE KEY `testCaseName` (`testCaseName`),
  KEY `TestCases` (`templateId`),
  CONSTRAINT `testcases_ibfk_1` FOREIGN KEY (`templateId`) REFERENCES `Template` (`templateId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TestCases`
--

LOCK TABLES `TestCases` WRITE;
/*!40000 ALTER TABLE `TestCases` DISABLE KEYS */;
/*!40000 ALTER TABLE `TestCases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ToolOSRelation`
--

DROP TABLE IF EXISTS `ToolOSRelation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ToolOSRelation` (
  `toolType` varchar(100) NOT NULL DEFAULT '',
  `OSName` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`toolType`,`OSName`),
  KEY `OSName` (`OSName`),
  CONSTRAINT `toolosrelation_ibfk_1` FOREIGN KEY (`toolType`) REFERENCES `Tools` (`toolType`),
  CONSTRAINT `toolosrelation_ibfk_2` FOREIGN KEY (`OSName`) REFERENCES `OS` (`OSName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ToolOSRelation`
--

LOCK TABLES `ToolOSRelation` WRITE;
/*!40000 ALTER TABLE `ToolOSRelation` DISABLE KEYS */;
/*!40000 ALTER TABLE `ToolOSRelation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tools`
--

DROP TABLE IF EXISTS `Tools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tools` (
  `toolType` varchar(100) NOT NULL,
  PRIMARY KEY (`toolType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tools`
--

LOCK TABLES `Tools` WRITE;
/*!40000 ALTER TABLE `Tools` DISABLE KEYS */;
/*!40000 ALTER TABLE `Tools` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserSquadRelation`
--

DROP TABLE IF EXISTS `UserSquadRelation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserSquadRelation` (
  `squadEmailId` varchar(200) NOT NULL DEFAULT '',
  `userEmailID` varchar(200) NOT NULL DEFAULT '',
  PRIMARY KEY (`squadEmailId`,`userEmailID`),
  KEY `userEmailID` (`userEmailID`),
  CONSTRAINT `usersquadrelation_ibfk_1` FOREIGN KEY (`squadEmailId`) REFERENCES `Squads` (`squadEmailId`),
  CONSTRAINT `usersquadrelation_ibfk_2` FOREIGN KEY (`userEmailID`) REFERENCES `Users` (`userEmailID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserSquadRelation`
--

LOCK TABLES `UserSquadRelation` WRITE;
/*!40000 ALTER TABLE `UserSquadRelation` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserSquadRelation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `userEmailID` varchar(200) NOT NULL,
  `roles` int(10) unsigned NOT NULL,
  PRIMARY KEY (`userEmailID`),
  KEY `roles` (`roles`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`roles`) REFERENCES `Roles` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-15 12:42:51
