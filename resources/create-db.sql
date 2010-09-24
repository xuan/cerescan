-- MySQL dump 10.11
--
-- Host: localhost    Database: cerescan
-- ------------------------------------------------------
-- Server version	5.0.67

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `address` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `city` varchar(255) default NULL,
  `city_code` varchar(255) default NULL,
  `currentAddr` bit(1) default NULL,
  `state` varchar(255) default NULL,
  `street1` varchar(255) default NULL,
  `street2` varchar(255) default NULL,
  `zip_code` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `allergy_lookup`
--

DROP TABLE IF EXISTS `allergy_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `allergy_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `description` longtext,
  `allergy_type_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK1B335319F848531C` (`allergy_type_id`),
  CONSTRAINT `FK1B335319F848531C` FOREIGN KEY (`allergy_type_id`) REFERENCES `allergy_type_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `allergy_type_lookup`
--

DROP TABLE IF EXISTS `allergy_type_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `allergy_type_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `attorney`
--

DROP TABLE IF EXISTS `attorney`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `attorney` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `email1` varchar(255) default NULL,
  `email2` varchar(255) default NULL,
  `fax_phone` varchar(255) default NULL,
  `first_name` varchar(255) default NULL,
  `home_phone` varchar(255) default NULL,
  `last_name` varchar(255) default NULL,
  `middle_name` varchar(255) default NULL,
  `mobile_phone` varchar(255) default NULL,
  `work_phone` varchar(255) default NULL,
  `firm_name` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `attorney_address`
--

DROP TABLE IF EXISTS `attorney_address`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `attorney_address` (
  `attorney_id` bigint(20) NOT NULL,
  `address_id` bigint(20) NOT NULL,
  UNIQUE KEY `address_id` (`address_id`),
  KEY `FK17DA3393D71988C4` (`attorney_id`),
  KEY `FK17DA3393B97AF030` (`address_id`),
  CONSTRAINT `FK17DA3393B97AF030` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FK17DA3393D71988C4` FOREIGN KEY (`attorney_id`) REFERENCES `attorney` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `birth_trauma_lookup`
--

DROP TABLE IF EXISTS `birth_trauma_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `birth_trauma_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `clinical_site`
--

DROP TABLE IF EXISTS `clinical_site`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `clinical_site` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `development_milestone_lookup`
--

DROP TABLE IF EXISTS `development_milestone_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `development_milestone_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `development_trauma_lookup`
--

DROP TABLE IF EXISTS `development_trauma_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `development_trauma_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `dosage_lookup`
--

DROP TABLE IF EXISTS `dosage_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `dosage_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `education_level_lookup`
--

DROP TABLE IF EXISTS `education_level_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `education_level_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `employment_status_lookup`
--

DROP TABLE IF EXISTS `employment_status_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `employment_status_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `ethnicity_lookup`
--

DROP TABLE IF EXISTS `ethnicity_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `ethnicity_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `family_member`
--

DROP TABLE IF EXISTS `family_member`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `family_member` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `family_member_type_id` bigint(20) default NULL,
  `session_info_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK918353D5ADE601FD` (`session_info_id`),
  KEY `FK918353D54F88E02D` (`family_member_type_id`),
  CONSTRAINT `FK918353D54F88E02D` FOREIGN KEY (`family_member_type_id`) REFERENCES `family_member_type_lookup` (`id`),
  CONSTRAINT `FK918353D5ADE601FD` FOREIGN KEY (`session_info_id`) REFERENCES `session_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `family_member_incoming_diagnoses`
--

DROP TABLE IF EXISTS `family_member_incoming_diagnoses`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `family_member_incoming_diagnoses` (
  `family_member_id` bigint(20) NOT NULL,
  `incoming_diagnoses_id` bigint(20) NOT NULL,
  KEY `FKA47D96C22BA33F0E` (`incoming_diagnoses_id`),
  KEY `FKA47D96C24443BFAD` (`family_member_id`),
  CONSTRAINT `FKA47D96C24443BFAD` FOREIGN KEY (`family_member_id`) REFERENCES `family_member` (`id`),
  CONSTRAINT `FKA47D96C22BA33F0E` FOREIGN KEY (`incoming_diagnoses_id`) REFERENCES `incoming_diagnoses_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `family_member_type_lookup`
--

DROP TABLE IF EXISTS `family_member_type_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `family_member_type_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `hospitalizations_and_surgeries`
--

DROP TABLE IF EXISTS `hospitalizations_and_surgeries`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `hospitalizations_and_surgeries` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `description` varchar(255) default NULL,
  `session_info_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK924617F5ADE601FD` (`session_info_id`),
  CONSTRAINT `FK924617F5ADE601FD` FOREIGN KEY (`session_info_id`) REFERENCES `session_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `incoming_diagnoses_lookup`
--

DROP TABLE IF EXISTS `incoming_diagnoses_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `incoming_diagnoses_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `injury_type`
--

DROP TABLE IF EXISTS `injury_type`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `injury_type` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `medication_type_lookup`
--

DROP TABLE IF EXISTS `medication_type_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `medication_type_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `medications`
--

DROP TABLE IF EXISTS `medications`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `medications` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `dosage_id` bigint(20) default NULL,
  `medicine_id` bigint(20) default NULL,
  `schedule_id` bigint(20) default NULL,
  `session_info_id` bigint(20) default NULL,
  `medication_type_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK6D938F84179CD439` (`schedule_id`),
  KEY `FK6D938F84ADE601FD` (`session_info_id`),
  KEY `FK6D938F849F2DCBF9` (`dosage_id`),
  KEY `FK6D938F847BEC0D59` (`medicine_id`),
  KEY `FK6D938F84F419BC18` (`medication_type_id`),
  CONSTRAINT `FK6D938F84F419BC18` FOREIGN KEY (`medication_type_id`) REFERENCES `medication_type_lookup` (`id`),
  CONSTRAINT `FK6D938F84179CD439` FOREIGN KEY (`schedule_id`) REFERENCES `schedule_lookup` (`id`),
  CONSTRAINT `FK6D938F847BEC0D59` FOREIGN KEY (`medicine_id`) REFERENCES `medicine_lookup` (`id`),
  CONSTRAINT `FK6D938F849F2DCBF9` FOREIGN KEY (`dosage_id`) REFERENCES `dosage_lookup` (`id`),
  CONSTRAINT `FK6D938F84ADE601FD` FOREIGN KEY (`session_info_id`) REFERENCES `session_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `medicine_lookup`
--

DROP TABLE IF EXISTS `medicine_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `medicine_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `patient` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `email1` varchar(255) default NULL,
  `email2` varchar(255) default NULL,
  `fax_phone` varchar(255) default NULL,
  `first_name` varchar(255) default NULL,
  `home_phone` varchar(255) default NULL,
  `last_name` varchar(255) default NULL,
  `middle_name` varchar(255) default NULL,
  `mobile_phone` varchar(255) default NULL,
  `work_phone` varchar(255) default NULL,
  `date_of_birth` datetime default NULL,
  `gender` varchar(1) default NULL,
  `mr_number` varchar(255) default NULL,
  `notes` longtext,
  `other_first_name` varchar(255) default NULL,
  `other_last_name` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `mr_number` (`mr_number`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `patient_address`
--

DROP TABLE IF EXISTS `patient_address`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `patient_address` (
  `patient_id` bigint(20) NOT NULL,
  `address_id` bigint(20) NOT NULL,
  UNIQUE KEY `address_id` (`address_id`),
  KEY `FK4D3658BAB97AF030` (`address_id`),
  KEY `FK4D3658BA3EC4A90` (`patient_id`),
  CONSTRAINT `FK4D3658BA3EC4A90` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
  CONSTRAINT `FK4D3658BAB97AF030` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `patient_session_info`
--

DROP TABLE IF EXISTS `patient_session_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `patient_session_info` (
  `patient_id` bigint(20) NOT NULL,
  `sessions_id` bigint(20) NOT NULL,
  UNIQUE KEY `sessions_id` (`sessions_id`),
  KEY `FKB92A17B1E5512857` (`sessions_id`),
  KEY `FKB92A17B13EC4A90` (`patient_id`),
  CONSTRAINT `FKB92A17B13EC4A90` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
  CONSTRAINT `FKB92A17B1E5512857` FOREIGN KEY (`sessions_id`) REFERENCES `session_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `physician`
--

DROP TABLE IF EXISTS `physician`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `physician` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `email1` varchar(255) default NULL,
  `email2` varchar(255) default NULL,
  `fax_phone` varchar(255) default NULL,
  `first_name` varchar(255) default NULL,
  `home_phone` varchar(255) default NULL,
  `last_name` varchar(255) default NULL,
  `middle_name` varchar(255) default NULL,
  `mobile_phone` varchar(255) default NULL,
  `work_phone` varchar(255) default NULL,
  `clinical_ind_for_referral` varchar(255) default NULL,
  `npi_number` varchar(255) default NULL,
  `practice_name` varchar(255) default NULL,
  `speciality` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `physician_address`
--

DROP TABLE IF EXISTS `physician_address`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `physician_address` (
  `physician_id` bigint(20) NOT NULL,
  `address_id` bigint(20) NOT NULL,
  UNIQUE KEY `address_id` (`address_id`),
  KEY `FK498ACFDFB97AF030` (`address_id`),
  KEY `FK498ACFDF9444B6F0` (`physician_id`),
  CONSTRAINT `FK498ACFDF9444B6F0` FOREIGN KEY (`physician_id`) REFERENCES `physician` (`id`),
  CONSTRAINT `FK498ACFDFB97AF030` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `psychometric_assessment`
--

DROP TABLE IF EXISTS `psychometric_assessment`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `psychometric_assessment` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `description` varchar(255) default NULL,
  `file_name` varchar(255) default NULL,
  `file_path` varchar(255) default NULL,
  `uploaded_by_user` varchar(255) default NULL,
  `psychometric_assessment_type_id` bigint(20) default NULL,
  `session_info_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK63D2BD1DADE601FD` (`session_info_id`),
  KEY `FK63D2BD1D4505D63D` (`psychometric_assessment_type_id`),
  CONSTRAINT `FK63D2BD1D4505D63D` FOREIGN KEY (`psychometric_assessment_type_id`) REFERENCES `psychometric_assessment_type_lookup` (`id`),
  CONSTRAINT `FK63D2BD1DADE601FD` FOREIGN KEY (`session_info_id`) REFERENCES `session_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `psychometric_assessment_type_lookup`
--

DROP TABLE IF EXISTS `psychometric_assessment_type_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `psychometric_assessment_type_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `relationships_status_lookup`
--

DROP TABLE IF EXISTS `relationships_status_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `relationships_status_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `scanned_form`
--

DROP TABLE IF EXISTS `scanned_form`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `scanned_form` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `description` varchar(255) default NULL,
  `file_name` varchar(255) default NULL,
  `file_path` varchar(255) default NULL,
  `uploaded_by_user` varchar(255) default NULL,
  `scanned_form_type_id` bigint(20) default NULL,
  `session_info_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK802005B3ADE601FD` (`session_info_id`),
  KEY `FK802005B3AA231B23` (`scanned_form_type_id`),
  CONSTRAINT `FK802005B3AA231B23` FOREIGN KEY (`scanned_form_type_id`) REFERENCES `scanned_form_type_lookup` (`id`),
  CONSTRAINT `FK802005B3ADE601FD` FOREIGN KEY (`session_info_id`) REFERENCES `session_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `scanned_form_type_lookup`
--

DROP TABLE IF EXISTS `scanned_form_type_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `scanned_form_type_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `schedule_lookup`
--

DROP TABLE IF EXISTS `schedule_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `schedule_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `session_info`
--

DROP TABLE IF EXISTS `session_info`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `session_info` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `addtional_development_notes` longtext,
  `alcohol_abuse_desc` longtext,
  `alcohol_consumption` varchar(255) default NULL,
  `biomarker_data` longtext,
  `baseline_scan_counts` bigint(20) default NULL,
  `baseline_scan_date` datetime default NULL,
  `baseline_scan_dose` bigint(20) default NULL,
  `baseline_scan_injection_site_location` varchar(255) default NULL,
  `baseline_scan_injection_site_side` varchar(255) default NULL,
  `baseline_scan_tech_initials` varchar(255) default NULL,
  `baseline_scan_injection_time` varchar(255) default NULL,
  `combat_date` varchar(255) default NULL,
  `combat_history` varchar(255) default NULL,
  `combat_location` varchar(255) default NULL,
  `concentration_scan_concentration_task` varchar(255) default NULL,
  `concentration_scan_counts` bigint(20) default NULL,
  `concentration_scan_date` datetime default NULL,
  `concentration_scan_dose` varchar(255) default NULL,
  `concentration_scan_injection_site_location` varchar(255) default NULL,
  `concentration_scan_injection_site_side` varchar(255) default NULL,
  `concentration_scan_tech_initials` varchar(255) default NULL,
  `concentration_scan_injection_time` varchar(255) default NULL,
  `concentration_scan_scan_time` varchar(255) default NULL,
  `current_caffiene_use` varchar(255) default NULL,
  `current_nicotine_use` varchar(255) default NULL,
  `disorders_in_childhood` longtext,
  `drug_abuse_desc` longtext,
  `eye_dominance` varchar(1) default NULL,
  `forensic_patient` varchar(1) default NULL,
  `hand_dominance` varchar(1) default NULL,
  `height` varchar(255) default NULL,
  `alcohol_abuse` bit(1) default NULL,
  `drug_abuse` bit(1) default NULL,
  `incoming_diagnoses_comment` longtext,
  `injury_description` varchar(255) default NULL,
  `adoptee` bit(1) default NULL,
  `neuropsychiatric_patient` varchar(1) default NULL,
  `notes` longtext,
  `protocol_exceptions` longtext,
  `read_by_sheikh` bit(1) default NULL,
  `read_by_henderson` bit(1) default NULL,
  `symptom_note` varchar(255) default NULL,
  `veteran_status` varchar(1) default NULL,
  `weight` bigint(20) default NULL,
  `alternate_physician_id` bigint(20) default NULL,
  `education_level_id` bigint(20) default NULL,
  `employment_status_id` bigint(20) default NULL,
  `ethnicity_id` bigint(20) default NULL,
  `injury_type_id` bigint(20) default NULL,
  `referring_attorney_id` bigint(20) default NULL,
  `referring_physician_id` bigint(20) default NULL,
  `relationship_status_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKD713C8B76E0A75B5` (`referring_attorney_id`),
  KEY `FKD713C8B7F7CAF695` (`alternate_physician_id`),
  KEY `FKD713C8B7DD2260E8` (`injury_type_id`),
  KEY `FKD713C8B77CC76AAA` (`relationship_status_id`),
  KEY `FKD713C8B7DB71681F` (`referring_physician_id`),
  KEY `FKD713C8B7AEA46B5B` (`ethnicity_id`),
  KEY `FKD713C8B7EBC359D2` (`employment_status_id`),
  KEY `FKD713C8B7DDBF85C8` (`education_level_id`),
  CONSTRAINT `FKD713C8B7DDBF85C8` FOREIGN KEY (`education_level_id`) REFERENCES `education_level_lookup` (`id`),
  CONSTRAINT `FKD713C8B76E0A75B5` FOREIGN KEY (`referring_attorney_id`) REFERENCES `attorney` (`id`),
  CONSTRAINT `FKD713C8B77CC76AAA` FOREIGN KEY (`relationship_status_id`) REFERENCES `relationships_status_lookup` (`id`),
  CONSTRAINT `FKD713C8B7AEA46B5B` FOREIGN KEY (`ethnicity_id`) REFERENCES `ethnicity_lookup` (`id`),
  CONSTRAINT `FKD713C8B7DB71681F` FOREIGN KEY (`referring_physician_id`) REFERENCES `physician` (`id`),
  CONSTRAINT `FKD713C8B7DD2260E8` FOREIGN KEY (`injury_type_id`) REFERENCES `injury_type` (`id`),
  CONSTRAINT `FKD713C8B7EBC359D2` FOREIGN KEY (`employment_status_id`) REFERENCES `employment_status_lookup` (`id`),
  CONSTRAINT `FKD713C8B7F7CAF695` FOREIGN KEY (`alternate_physician_id`) REFERENCES `physician` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `session_info_allergies`
--

DROP TABLE IF EXISTS `session_info_allergies`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `session_info_allergies` (
  `session_info_id` bigint(20) NOT NULL,
  `allergies_id` bigint(20) NOT NULL,
  UNIQUE KEY `allergies_id` (`allergies_id`),
  KEY `FK2294DE9643E1752` (`allergies_id`),
  KEY `FK2294DE96ADE601FD` (`session_info_id`),
  KEY `FK2294DE96DBAEE9D` (`allergies_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `session_info_allergy_lookup`
--

DROP TABLE IF EXISTS `session_info_allergy_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `session_info_allergy_lookup` (
  `session_info_id` bigint(20) NOT NULL,
  `allergies_id` bigint(20) NOT NULL,
  UNIQUE KEY `allergies_id` (`allergies_id`),
  KEY `FK68C893A1DBAEE9D` (`allergies_id`),
  KEY `FK68C893A1ADE601FD` (`session_info_id`),
  CONSTRAINT `FK68C893A1ADE601FD` FOREIGN KEY (`session_info_id`) REFERENCES `session_info` (`id`),
  CONSTRAINT `FK68C893A1DBAEE9D` FOREIGN KEY (`allergies_id`) REFERENCES `allergy_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `session_info_family_member`
--

DROP TABLE IF EXISTS `session_info_family_member`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `session_info_family_member` (
  `session_info_id` bigint(20) NOT NULL,
  `familyMembers_id` bigint(20) NOT NULL,
  UNIQUE KEY `familyMembers_id` (`familyMembers_id`),
  KEY `FK837FE24DADE601FD` (`session_info_id`),
  KEY `FK837FE24DFD1F824D` (`familyMembers_id`),
  CONSTRAINT `FK837FE24DFD1F824D` FOREIGN KEY (`familyMembers_id`) REFERENCES `family_member` (`id`),
  CONSTRAINT `FK837FE24DADE601FD` FOREIGN KEY (`session_info_id`) REFERENCES `session_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `session_info_hospitalizations_and_surgeries`
--

DROP TABLE IF EXISTS `session_info_hospitalizations_and_surgeries`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `session_info_hospitalizations_and_surgeries` (
  `session_info_id` bigint(20) NOT NULL,
  `hospitalizations_id` bigint(20) NOT NULL,
  UNIQUE KEY `hospitalizations_id` (`hospitalizations_id`),
  KEY `FK6799487DA1066451` (`hospitalizations_id`),
  KEY `FK6799487DADE601FD` (`session_info_id`),
  CONSTRAINT `FK6799487DADE601FD` FOREIGN KEY (`session_info_id`) REFERENCES `session_info` (`id`),
  CONSTRAINT `FK6799487DA1066451` FOREIGN KEY (`hospitalizations_id`) REFERENCES `hospitalizations_and_surgeries` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `session_info_medication`
--

DROP TABLE IF EXISTS `session_info_medication`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `session_info_medication` (
  `session_info_id` bigint(20) NOT NULL,
  `medication_id` bigint(20) NOT NULL,
  UNIQUE KEY `medication_id` (`medication_id`),
  KEY `FK10408F57ADE601FD` (`session_info_id`),
  KEY `FK10408F571FE16CA4` (`medication_id`),
  CONSTRAINT `FK10408F571FE16CA4` FOREIGN KEY (`medication_id`) REFERENCES `medications` (`id`),
  CONSTRAINT `FK10408F57ADE601FD` FOREIGN KEY (`session_info_id`) REFERENCES `session_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `session_info_psychometric_assessment`
--

DROP TABLE IF EXISTS `session_info_psychometric_assessment`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `session_info_psychometric_assessment` (
  `session_info_id` bigint(20) NOT NULL,
  `assessments_id` bigint(20) NOT NULL,
  UNIQUE KEY `assessments_id` (`assessments_id`),
  KEY `FKB1299595ADE601FD` (`session_info_id`),
  KEY `FKB12995951EAD1B19` (`assessments_id`),
  CONSTRAINT `FKB12995951EAD1B19` FOREIGN KEY (`assessments_id`) REFERENCES `psychometric_assessment` (`id`),
  CONSTRAINT `FKB1299595ADE601FD` FOREIGN KEY (`session_info_id`) REFERENCES `session_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `session_info_scanned_form`
--

DROP TABLE IF EXISTS `session_info_scanned_form`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `session_info_scanned_form` (
  `session_info_id` bigint(20) NOT NULL,
  `scannedForms_id` bigint(20) NOT NULL,
  UNIQUE KEY `scannedForms_id` (`scannedForms_id`),
  KEY `FK3D9BC83B4745CFE5` (`scannedForms_id`),
  KEY `FK3D9BC83BADE601FD` (`session_info_id`),
  CONSTRAINT `FK3D9BC83BADE601FD` FOREIGN KEY (`session_info_id`) REFERENCES `session_info` (`id`),
  CONSTRAINT `FK3D9BC83B4745CFE5` FOREIGN KEY (`scannedForms_id`) REFERENCES `scanned_form` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `session_info_traumatic_brain_injury`
--

DROP TABLE IF EXISTS `session_info_traumatic_brain_injury`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `session_info_traumatic_brain_injury` (
  `session_info_id` bigint(20) NOT NULL,
  `traumaticBrainInjuries_id` bigint(20) NOT NULL,
  UNIQUE KEY `traumaticBrainInjuries_id` (`traumaticBrainInjuries_id`),
  KEY `FKE8E961BFDFDBCC46` (`traumaticBrainInjuries_id`),
  KEY `FKE8E961BFADE601FD` (`session_info_id`),
  CONSTRAINT `FKE8E961BFADE601FD` FOREIGN KEY (`session_info_id`) REFERENCES `session_info` (`id`),
  CONSTRAINT `FKE8E961BFDFDBCC46` FOREIGN KEY (`traumaticBrainInjuries_id`) REFERENCES `traumatic_brain_injury` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sessions_birth_trauma`
--

DROP TABLE IF EXISTS `sessions_birth_trauma`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sessions_birth_trauma` (
  `sessioninfo_id` bigint(20) NOT NULL,
  `birth_trauma_id` bigint(20) NOT NULL,
  KEY `FKD2EC4028362267DA` (`birth_trauma_id`),
  KEY `FKD2EC402860F85750` (`sessioninfo_id`),
  CONSTRAINT `FKD2EC402860F85750` FOREIGN KEY (`sessioninfo_id`) REFERENCES `session_info` (`id`),
  CONSTRAINT `FKD2EC4028362267DA` FOREIGN KEY (`birth_trauma_id`) REFERENCES `birth_trauma_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sessions_clinical_sites`
--

DROP TABLE IF EXISTS `sessions_clinical_sites`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sessions_clinical_sites` (
  `sessioninfo_id` bigint(20) NOT NULL,
  `clinical_site_id` bigint(20) NOT NULL,
  KEY `FK260C9C2916AF340` (`clinical_site_id`),
  KEY `FK260C9C260F85750` (`sessioninfo_id`),
  CONSTRAINT `FK260C9C260F85750` FOREIGN KEY (`sessioninfo_id`) REFERENCES `session_info` (`id`),
  CONSTRAINT `FK260C9C2916AF340` FOREIGN KEY (`clinical_site_id`) REFERENCES `clinical_site` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sessions_development_milestone`
--

DROP TABLE IF EXISTS `sessions_development_milestone`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sessions_development_milestone` (
  `sessioninfo_id` bigint(20) NOT NULL,
  `development_milestome_id` bigint(20) NOT NULL,
  KEY `FK6482B14A467B6521` (`development_milestome_id`),
  KEY `FK6482B14A60F85750` (`sessioninfo_id`),
  CONSTRAINT `FK6482B14A60F85750` FOREIGN KEY (`sessioninfo_id`) REFERENCES `session_info` (`id`),
  CONSTRAINT `FK6482B14A467B6521` FOREIGN KEY (`development_milestome_id`) REFERENCES `development_milestone_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sessions_development_trauma`
--

DROP TABLE IF EXISTS `sessions_development_trauma`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sessions_development_trauma` (
  `sessioninfo_id` bigint(20) NOT NULL,
  `development_trauma_id` bigint(20) NOT NULL,
  KEY `FKB31082AC6841EC12` (`development_trauma_id`),
  KEY `FKB31082AC60F85750` (`sessioninfo_id`),
  CONSTRAINT `FKB31082AC60F85750` FOREIGN KEY (`sessioninfo_id`) REFERENCES `session_info` (`id`),
  CONSTRAINT `FKB31082AC6841EC12` FOREIGN KEY (`development_trauma_id`) REFERENCES `development_trauma_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sessions_incoming_diagnoses`
--

DROP TABLE IF EXISTS `sessions_incoming_diagnoses`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sessions_incoming_diagnoses` (
  `sessioninfo_id` bigint(20) NOT NULL,
  `incoming_diagnoses_id` bigint(20) NOT NULL,
  KEY `FK3B0714FA2BA33F0E` (`incoming_diagnoses_id`),
  KEY `FK3B0714FA60F85750` (`sessioninfo_id`),
  CONSTRAINT `FK3B0714FA60F85750` FOREIGN KEY (`sessioninfo_id`) REFERENCES `session_info` (`id`),
  CONSTRAINT `FK3B0714FA2BA33F0E` FOREIGN KEY (`incoming_diagnoses_id`) REFERENCES `incoming_diagnoses_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sessions_symptoms`
--

DROP TABLE IF EXISTS `sessions_symptoms`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sessions_symptoms` (
  `sessioninfo_id` bigint(20) NOT NULL,
  `symptom_id` bigint(20) NOT NULL,
  KEY `FK60D3D8CC4CBE9F5B` (`symptom_id`),
  KEY `FK60D3D8CC60F85750` (`sessioninfo_id`),
  CONSTRAINT `FK60D3D8CC60F85750` FOREIGN KEY (`sessioninfo_id`) REFERENCES `session_info` (`id`),
  CONSTRAINT `FK60D3D8CC4CBE9F5B` FOREIGN KEY (`symptom_id`) REFERENCES `symptom_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `symptom_lookup`
--

DROP TABLE IF EXISTS `symptom_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `symptom_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `traumatic_brain_injury`
--

DROP TABLE IF EXISTS `traumatic_brain_injury`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `traumatic_brain_injury` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `description` longtext,
  `session_info_id` bigint(20) default NULL,
  `traumatic_brain_injury_type_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK203929378DCAEC56` (`traumatic_brain_injury_type_id`),
  KEY `FK20392937ADE601FD` (`session_info_id`),
  CONSTRAINT `FK20392937ADE601FD` FOREIGN KEY (`session_info_id`) REFERENCES `session_info` (`id`),
  CONSTRAINT `FK203929378DCAEC56` FOREIGN KEY (`traumatic_brain_injury_type_id`) REFERENCES `traumatic_brain_injury_lookup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `traumatic_brain_injury_lookup`
--

DROP TABLE IF EXISTS `traumatic_brain_injury_lookup`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `traumatic_brain_injury_lookup` (
  `id` bigint(20) NOT NULL auto_increment,
  `created_date` datetime default NULL,
  `last_modified` datetime default NULL,
  `version` datetime default NULL,
  `deleted` int(11) default NULL,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-04-10  5:07:09
