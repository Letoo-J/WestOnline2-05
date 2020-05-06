-- MySQL dump 10.13  Distrib 5.7.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: question_box
-- ------------------------------------------------------
-- Server version	5.7.22-log

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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `UID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `MailBox` varchar(30) NOT NULL,
  `PicName` varchar(50) DEFAULT 'default.jpg',
  `OpenSta` varchar(10) NOT NULL DEFAULT 'close',
  `Act` varchar(10) NOT NULL DEFAULT 'no',
  `Prohibit` varchar(10) NOT NULL DEFAULT 'no',
  `Quiz` int(11) NOT NULL DEFAULT '0',
  `Questions` int(11) NOT NULL DEFAULT '0',
  `Replies` int(11) NOT NULL DEFAULT '0',
  `Salt` varchar(45) DEFAULT NULL,
  `isAdmin` varchar(10) NOT NULL DEFAULT 'no',
  `mailCode` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`UID`),
  UNIQUE KEY `UID_UNIQUE` (`UID`),
  UNIQUE KEY `Username_UNIQUE` (`Username`),
  UNIQUE KEY `MailBox_UNIQUE` (`MailBox`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','bd2d62ea7a710a2bbec630d6408538e7','1171372006@qq.com','default.jpg','open','yes','no',0,1,0,'b0a1611d-db9c-4a9a-b95e-cf7325298fdb','no',''),(3,'Lynx','f565ca0245a4b8806f5079effc98e3dc','aaq@qq.com','2020-04-28 013701(2).jpg','open','yes','no',0,0,0,'17f1d054-c8c8-4ce9-9447-d96a52ab5e86','no',''),(4,'MMM','bdf048ebe84546267d8848e031a4df58','1171392006@qq.com','2020-04-28 013701.jpg','open','yes','no',1,0,2,'2497c052-a019-45f2-9cd1-ac83e7de0572','yes',''),(5,'2020','f198f759ce08d253a958769a1d04d7dd','0505@qq.com','default.jpg','close','no','no',0,0,0,'3d836b7b-e5eb-41cc-8059-f65e1ef4e6c6','no',NULL),(6,'admin1','eeb956bf5b320fb5821e5550a0889530','vdaa@qqqq','default.jpg','close','no','no',0,0,0,'01a3c28a-1aa2-45a0-9e6a-1cf96a0dd0f3','no',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-06 21:58:04
