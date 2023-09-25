-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: trainease
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `batches`
--

DROP TABLE IF EXISTS `batches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batches` (
  `batch_id` varchar(255) NOT NULL,
  `batch_name` varchar(255) NOT NULL,
  `batch_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batches`
--

LOCK TABLES `batches` WRITE;
/*!40000 ALTER TABLE `batches` DISABLE KEYS */;
INSERT INTO `batches` VALUES ('2023_Q1','2023 Q1 DevOps','This training batch is for the TechOps joining in the first Quarter of 2023.'),('2023_Q2','2023 Q2 Associate SDE','This training batch is for the Associate SDE who would join in the second Quarter of 2023.'),('test','testing ui','xyz');
/*!40000 ALTER TABLE `batches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_progress`
--

DROP TABLE IF EXISTS `course_progress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_progress` (
  `progress_id` int NOT NULL AUTO_INCREMENT,
  `email_id` varchar(255) NOT NULL,
  `batch_id` varchar(255) NOT NULL,
  `course_id` varchar(255) NOT NULL,
  `status` tinyint DEFAULT NULL,
  `feedback` varchar(255) DEFAULT NULL,
  `actual_start_date` datetime(6) DEFAULT NULL,
  `actual_end_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`progress_id`),
  KEY `email_id` (`email_id`),
  KEY `batch_id` (`batch_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `course_progress_ibfk_1` FOREIGN KEY (`email_id`) REFERENCES `users` (`email_id`),
  CONSTRAINT `course_progress_ibfk_2` FOREIGN KEY (`batch_id`) REFERENCES `batches` (`batch_id`),
  CONSTRAINT `course_progress_ibfk_3` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_progress`
--

LOCK TABLES `course_progress` WRITE;
/*!40000 ALTER TABLE `course_progress` DISABLE KEYS */;
INSERT INTO `course_progress` VALUES (1,'zeeshan.mujawar@ideas.com','2023_Q1','C1',1,NULL,NULL,NULL),(2,'zeeshan.mujawar@ideas.com','2023_Q1','C2',0,NULL,NULL,NULL),(3,'zeeshan.mujawar@ideas.com','2023_Q1','C3',0,NULL,NULL,NULL),(4,'zeeshan.mujawar@ideas.com','2023_Q1','C4',0,NULL,NULL,NULL),(5,'zeeshan.mujawar@ideas.com','2023_Q1','C5',0,NULL,NULL,NULL),(6,'shamik.puranik@ideas.com','2023_Q1','C1',0,NULL,NULL,NULL),(7,'shamik.puranik@ideas.com','2023_Q1','C2',0,NULL,NULL,NULL),(8,'shamik.puranik@ideas.com','2023_Q1','C3',0,NULL,NULL,NULL),(9,'shamik.puranik@ideas.com','2023_Q1','C4',0,NULL,NULL,NULL),(10,'shamik.puranik@ideas.com','2023_Q1','C5',0,NULL,NULL,NULL),(11,'pavitra.poojary@ideas.com','2023_Q1','C1',0,NULL,NULL,NULL),(12,'pavitra.poojary@ideas.com','2023_Q1','C2',0,NULL,NULL,NULL),(13,'pavitra.poojary@ideas.com','2023_Q1','C3',0,NULL,NULL,NULL),(14,'pavitra.poojary@ideas.com','2023_Q1','C4',0,NULL,NULL,NULL),(15,'pavitra.poojary@ideas.com','2023_Q1','C5',0,NULL,NULL,NULL),(16,'garima.arora@ideas.com','2023_Q1','C1',0,NULL,NULL,NULL),(17,'garima.arora@ideas.com','2023_Q1','C2',0,NULL,NULL,NULL),(18,'garima.arora@ideas.com','2023_Q1','C3',0,NULL,NULL,NULL),(19,'garima.arora@ideas.com','2023_Q1','C4',0,NULL,NULL,NULL),(20,'garima.arora@ideas.com','2023_Q1','C5',0,NULL,NULL,NULL),(21,'gaurav.shinde@ideas.com','2023_Q1','C1',0,NULL,NULL,NULL),(22,'gaurav.shinde@ideas.com','2023_Q1','C2',0,NULL,NULL,NULL),(23,'gaurav.shinde@ideas.com','2023_Q1','C3',0,NULL,NULL,NULL),(24,'gaurav.shinde@ideas.com','2023_Q1','C4',0,NULL,NULL,NULL),(25,'gaurav.shinde@ideas.com','2023_Q1','C5',0,NULL,NULL,NULL),(26,'aarti.goyal@ideas.com','2023_Q2','C10',0,NULL,NULL,NULL),(27,'aarti.goyal@ideas.com','2023_Q2','C6',0,NULL,NULL,NULL),(28,'aarti.goyal@ideas.com','2023_Q2','C7',0,NULL,NULL,NULL),(29,'aarti.goyal@ideas.com','2023_Q2','C8',0,NULL,NULL,NULL),(30,'aarti.goyal@ideas.com','2023_Q2','C9',0,NULL,NULL,NULL),(31,'sayali.nikam@ideas.com','2023_Q2','C10',0,NULL,NULL,NULL),(32,'sayali.nikam@ideas.com','2023_Q2','C6',0,NULL,NULL,NULL),(33,'sayali.nikam@ideas.com','2023_Q2','C7',0,NULL,NULL,NULL),(34,'sayali.nikam@ideas.com','2023_Q2','C8',0,NULL,NULL,NULL),(35,'sayali.nikam@ideas.com','2023_Q2','C9',0,NULL,NULL,NULL),(36,'eshwari.patole@ideas.com','2023_Q2','C10',0,NULL,NULL,NULL),(37,'eshwari.patole@ideas.com','2023_Q2','C6',0,NULL,NULL,NULL),(38,'eshwari.patole@ideas.com','2023_Q2','C7',0,NULL,NULL,NULL),(39,'eshwari.patole@ideas.com','2023_Q2','C8',0,NULL,NULL,NULL),(40,'eshwari.patole@ideas.com','2023_Q2','C9',0,NULL,NULL,NULL),(41,'bhargavee.nemade@ideas.com','2023_Q2','C10',0,NULL,NULL,NULL),(42,'bhargavee.nemade@ideas.com','2023_Q2','C6',0,NULL,NULL,NULL),(43,'bhargavee.nemade@ideas.com','2023_Q2','C7',0,NULL,NULL,NULL),(44,'bhargavee.nemade@ideas.com','2023_Q2','C8',0,NULL,NULL,NULL),(45,'bhargavee.nemade@ideas.com','2023_Q2','C9',0,NULL,NULL,NULL),(46,'utkarsh.archit@ideas.com','2023_Q2','C10',0,NULL,NULL,NULL),(47,'utkarsh.archit@ideas.com','2023_Q2','C6',0,NULL,NULL,NULL),(48,'utkarsh.archit@ideas.com','2023_Q2','C7',0,NULL,NULL,NULL),(49,'utkarsh.archit@ideas.com','2023_Q2','C8',0,NULL,NULL,NULL),(50,'utkarsh.archit@ideas.com','2023_Q2','C9',0,NULL,NULL,NULL),(51,'shranay.shahane@ideas.com','2023_Q2','C10',0,NULL,NULL,NULL),(52,'shranay.shahane@ideas.com','2023_Q2','C6',0,NULL,NULL,NULL),(53,'shranay.shahane@ideas.com','2023_Q2','C7',0,NULL,NULL,NULL),(54,'shranay.shahane@ideas.com','2023_Q2','C8',0,NULL,NULL,NULL),(55,'shranay.shahane@ideas.com','2023_Q2','C9',0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `course_progress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `course_id` varchar(255) NOT NULL,
  `batch_id` varchar(255) NOT NULL,
  `course_name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `duration_in_hours` double DEFAULT NULL,
  `estimated_start_date` datetime(6) DEFAULT NULL,
  `estimated_end_date` datetime(6) DEFAULT NULL,
  `subject_matter_expert` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  KEY `batch_id` (`batch_id`),
  CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`batch_id`) REFERENCES `batches` (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('C1','2023_Q1','Microservices','Microservices course for batch 2023_Q1','microservices.com',5,'2023-09-27 10:00:00.000000','2023-09-28 10:00:00.000000','Rohan Mule , Vaibhav Javadekar'),('C10','2023_Q2','Spring Boot','Spring Boot course for batch 2023_Q2','spring.com',6.8,'2023-09-27 10:00:00.000000','2023-09-28 10:00:00.000000','Abhijeet Dalal'),('c100','2023_Q1','testc100','testc100','testc100',10,'2023-09-27 10:00:00.000000','2023-09-27 10:00:00.000000','testc100'),('C2','2023_Q1','AWS','AWS course for batch 2023_Q1','aws.com',12.3,'2023-09-27 10:00:00.000000','2023-09-28 10:00:00.000000','Gaurav Aher , Vaibhav Javadekar'),('C3','2023_Q1','Docker','Docker course for batch 2023_Q1','docker.com',6.2,'2023-09-27 10:00:00.000000','2023-09-28 10:00:00.000000','Abhijeet Dalal , Pravin Nimodiya'),('C4','2023_Q1','MongoDB','MongoDB course for batch 2023_Q1','mongodb.com',10.8,'2023-09-27 10:00:00.000000','2023-09-28 10:00:00.000000','Arun Pratap Singh'),('C5','2023_Q1','MySQL','MySQL course for batch 2023_Q1','mysql.com',7.3,'2023-09-27 10:00:00.000000','2023-09-28 10:00:00.000000','Sandeep Ghiya , Pravin Nimodiya'),('C6','2023_Q2','Selenium Testing','Selenium Testing course for batch 2023_Q2','selenium.com',5,'2023-09-27 10:00:00.000000','2023-09-28 10:00:00.000000','Rahul Saraf , Kirtesh Wani'),('C7','2023_Q2','Test Driven Development','Test Driven Development course for batch 2023_Q2','tdd.com',3.2,'2023-09-27 10:00:00.000000','2023-09-28 10:00:00.000000','Saurabh Murhekar , Pravin Nimodiya'),('C8','2023_Q2','Angular','Angular course for batch 2023_Q2','angular.com',4.2,'2023-09-27 10:00:00.000000','2023-09-28 10:00:00.000000','Chaitanya Deshmukh , Mayuresh Halshikar'),('C9','2023_Q2','Java OOPs','Java OOPs course for batch 2023_Q2','java.com',10,'2023-09-27 10:00:00.000000','2023-09-28 10:00:00.000000','Gaurav Aher , Pravin Nimodiya');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'1','Create Batch','SQL','V1__Create_Batch.sql',-1513128631,'root','2023-09-19 16:27:14',14,1),(2,'2','Create Course','SQL','V2__Create_Course.sql',-1530294626,'root','2023-09-19 16:27:14',14,1),(3,'3','Create User','SQL','V3__Create_User.sql',2081457523,'root','2023-09-19 16:30:09',15,1),(4,'4','Create CourseProgress','SQL','V4__Create_CourseProgress.sql',-620763830,'root','2023-09-19 16:30:09',20,1),(5,'5','Remove Columns CourseProgress','SQL','V5__Remove_Columns_CourseProgress.sql',-933612816,'root','2023-09-19 17:12:40',16,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `email_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `role` tinyint DEFAULT NULL,
  `batch_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`email_id`),
  KEY `batch_id` (`batch_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`batch_id`) REFERENCES `batches` (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('aarti.goyal@ideas.com','Aarti Goyal',1,'2023_Q2'),('abhijeet.dalal@ideas.com','Abhijeet Dalal',2,NULL),('admin@ideas.com','Admin Pavitra',0,NULL),('annie.lawrence@ideas.com','Annie Lawrence',0,NULL),('arunpratap.singh@ideas.com','Arun Pratap Singh',2,NULL),('ashish.parkhi@ideas.com','Ashish Parkhi',0,NULL),('bhargavee.nemade@ideas.com','Bhargavee Nemade',1,'2023_Q2'),('chaitanya.deshmukh@ideas.com','Chaitanya Deshmukh',2,NULL),('eshwari.patole@ideas.com','Eshwari Patole',1,'2023_Q2'),('garima.arora@ideas.com','Garima Arora',1,'2023_Q1'),('gaurav.aher@ideas.com','Gaurav Aher',2,NULL),('gaurav.shinde@ideas.com','Gaurav Shinde',1,'2023_Q1'),('kirtesh.wani@ideas.com','Kitesh Wani',2,NULL),('mayuresh.halshikar@ideas.com','Mayuresh Halshikar',2,NULL),('pavitra.poojary@ideas.com','Pavitra Poojary',1,'2023_Q1'),('pravin.nimodiya@ideas.com','Pravin Nimodiya',2,NULL),('rahul.saraf@ideas.com','Rahul Saraf',2,NULL),('rohan.mule@ideas.com','Rohan Mule',2,NULL),('sachin.natu@ideas.com','Sachin Natu',0,NULL),('sandeep.ghiya@ideas.com','Sandeep Ghiya',2,NULL),('saurabh.murhekar@ideas.com','Saurabh Murhekar',2,NULL),('sayali.nikam@ideas.com','Sayali Nikam',1,'2023_Q2'),('shamik.puranik@ideas.com','Shamik Puranik',1,'2023_Q1'),('shranay.shahane@ideas.com','Shranay Shahane',1,'2023_Q2'),('test@test.com','test test',0,NULL),('test@test2.com','test2',0,NULL),('utkarsh.archit@ideas.com','Utkarsh Archit',1,'2023_Q2'),('vaibhav.javadekar@ideas.com','Vaibhav Javadekar',2,NULL),('zeeshan.mujawar@ideas.com','Zeeshan Mujawar',1,'2023_Q1');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-21 10:03:06
