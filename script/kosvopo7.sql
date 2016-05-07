-- Adminer 4.1.0 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP DATABASE IF EXISTS `kosvopo7`;
CREATE DATABASE `kosvopo7` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_slovak_ci */;
USE `kosvopo7`;

DROP TABLE IF EXISTS `a_change`;
CREATE TABLE `a_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_stamp` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `public_body_id` int(11) DEFAULT NULL,
  `table_name` varchar(30) COLLATE utf8_slovak_ci DEFAULT NULL,
  `column_name` varchar(30) COLLATE utf8_slovak_ci DEFAULT NULL,
  `row_id` int(11) DEFAULT NULL,
  `old_value` longblob,
  `new_value` longblob,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;



DROP TABLE IF EXISTS `a_hierarchy`;
CREATE TABLE `a_hierarchy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(30) COLLATE utf8_slovak_ci DEFAULT NULL,
  `boss_table` varchar(30) COLLATE utf8_slovak_ci DEFAULT NULL,
  `boss_reference` varchar(30) COLLATE utf8_slovak_ci DEFAULT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `a_hierarchy` (`id`, `table_name`, `boss_table`, `boss_reference`, `visible`) VALUES
(1,	't_public_person',	NULL,	NULL,	CONV('1', 2, 10) + 0),
(2,	't_tenure',	NULL,	NULL,	CONV('1', 2, 10) + 0),
(3,	't_region',	NULL,	NULL,	CONV('1', 2, 10) + 0),
(4,	't_district',	't_region',	'region_id',	CONV('1', 2, 10) + 0),
(5,	't_location',	't_district',	'district_id',	CONV('1', 2, 10) + 0),
(6,	't_public_body',	't_location',	'location_id',	CONV('1', 2, 10) + 0),
(7,	't_person_classification',	't_public_person',	'public_person_id',	CONV('1', 2, 10) + 0),
(8,	't_public_role',	't_tenure',	'tenure_id',	CONV('1', 2, 10) + 0),
(9,	't_public_role',	't_public_body',	'public_body_id',	CONV('1', 2, 10) + 0),
(10,	't_public_role',	't_public_person',	'public_person_id',	CONV('1', 2, 10) + 0),
(11,	't_subject',	't_public_body',	'public_body_id',	CONV('1', 2, 10) + 0),
(12,	't_vote',	't_subject',	'subject_id',	CONV('1', 2, 10) + 0),
(13,	't_vote_classification',	't_vote',	'vote_id',	CONV('1', 2, 10) + 0),
(14,	't_vote_of_role',	't_vote',	'vote_id',	CONV('1', 2, 10) + 0),
(15,	't_vote_of_role',	't_public_role',	'public_role_id',	CONV('1', 2, 10) + 0),
(16,	'a_user',	NULL,	NULL,	CONV('1', 2, 10) + 0),
(17,	'a_role',	NULL,	NULL,	CONV('1', 2, 10) + 0),
(18,	'a_user_role',	'a_user',	'user_id',	CONV('1', 2, 10) + 0),
(19,	'a_user_role',	'a_role',	'role_id',	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `a_role`;
CREATE TABLE `a_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` smallint(6) DEFAULT NULL,
  `role_name` varchar(30) COLLATE utf8_slovak_ci DEFAULT NULL,
  `rights_description` text COLLATE utf8_slovak_ci,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `a_role` (`id`, `role`, `role_name`, `rights_description`, `visible`) VALUES
(1,	0,	'Dobrovoľník',	'všetky práva (vkladanie nových entít, ...), okrem priameho vstupu do databázy',	CONV('1', 2, 10) + 0),
(2,	1,	'Admin',	'všetky práva Dobrovoľník-a, vrátane práv na úpravu databázy',	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `a_user`;
CREATE TABLE `a_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) COLLATE utf8_slovak_ci DEFAULT NULL,
  `last_name` varchar(20) COLLATE utf8_slovak_ci DEFAULT NULL,
  `e_mail` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `login` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `image` longblob,
  `password` binary(16),
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `a_user` (`id`, `first_name`, `last_name`, `e_mail`, `login`, `password`, `visible`) VALUES
(1,	'admin',	'adminovic',	'admin@admin.sk',	'admin',	unhex('21232f297a57a5a743894a0e4a801fc3'),	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `a_user_role`;
CREATE TABLE `a_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT '1',
  `user_id` int(11) DEFAULT NULL,
  `actual` bit(1) DEFAULT b'1',
  `since` date DEFAULT NULL,
  `till` date DEFAULT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `a_user_role` (`id`, `role_id`, `user_id`, `actual`, `since`, `till`, `visible`) VALUES
(1,	1,	1,	CONV('1', 2, 10) + 0,	'2015-06-19',	NULL,	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `t_district`;
CREATE TABLE `t_district` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `district_name` varchar(20) COLLATE utf8_slovak_ci DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_district` (`id`, `district_name`, `region_id`, `visible`) VALUES
(1,	'Lučenec',	2,	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `t_document`;
CREATE TABLE `t_document` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(30) COLLATE utf8_slovak_ci DEFAULT NULL,
  `table_row_id` int(11) DEFAULT NULL,
  `file_name` varchar(30) COLLATE utf8_slovak_ci DEFAULT NULL,
  `upload_date` date DEFAULT NULL,
  `document` mediumblob,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;


DROP TABLE IF EXISTS `t_location`;
CREATE TABLE `t_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location_name` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `town_section` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `district_id` int(11) DEFAULT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_location` (`id`, `location_name`, `town_section`, `district_id`, `visible`) VALUES
(1,	'Kokava nad Rimavicou',	NULL,	1,	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `t_note`;
CREATE TABLE `t_note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(30) COLLATE utf8_slovak_ci NOT NULL,
  `table_row_id` int(11) NOT NULL,
  `title` varchar(30) COLLATE utf8_slovak_ci NOT NULL,
  `note_date` date NOT NULL,
  `note` text COLLATE utf8_slovak_ci NOT NULL,
  `visible` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;


DROP TABLE IF EXISTS `t_person_classification`;
CREATE TABLE `t_person_classification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classification_date` date DEFAULT NULL,
  `public_person_id` int(11) DEFAULT NULL,
  `stability` smallint(6) DEFAULT '2',
  `public_usefulness` smallint(6) DEFAULT '2',
  `actual` bit(1) DEFAULT b'1',
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;


DROP TABLE IF EXISTS `t_public_body`;
CREATE TABLE `t_public_body` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `image` longblob,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_public_body` (`id`, `name`, `location_id`, `visible`, `image`) VALUES
(1,	'Mestky urad Kokava nad Rimavicou',	1,	CONV('1', 2, 10) + 0, LOAD_FILE('public_body.jpg'));

DROP TABLE IF EXISTS `t_public_person`;
CREATE TABLE `t_public_person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `last_name` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `image` longblob,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_public_person` (`id`, `first_name`, `last_name`, `date_of_birth`, `visible`, `image`) VALUES
(1,	'Johann',	'Procházka',	'1956-09-07',	CONV('1', 2, 10) + 0, LOAD_FILE('/public_person.jpg')),
(2,	'Karol',	'Bútora',	'1966-05-17',	CONV('1', 2, 10) + 0, LOAD_FILE('public_person.jpg')),
(3,	'Emil',	'Čútora',	'1967-04-01',	CONV('0', 2, 10) + 0, LOAD_FILE('public_person.jpg')),
(4,	'Pavel',	'Oblázek',	'2001-07-10',	CONV('1', 2, 10) + 0, LOAD_FILE('public_person.jpg')),
(5,	'Karol',	'Predseda',	'1989-07-05',	CONV('1', 2, 10) + 0, LOAD_FILE('public_person.jpg'));

DROP TABLE IF EXISTS `t_public_role`;
CREATE TABLE `t_public_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `public_body_id` int(11) DEFAULT NULL,
  `tenure_id` int(11) DEFAULT NULL,
  `public_person_id` int(11) DEFAULT NULL,
  `name` smallint(6) DEFAULT '0',
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_public_role` (`id`, `public_body_id`, `tenure_id`, `public_person_id`, `name`, `visible`) VALUES
(1,	1,	1,	1,	0,	CONV('1', 2, 10) + 0),
(2,	1,	1,	2,	0,	CONV('1', 2, 10) + 0),
(3,	1,	1,	3,	0,	CONV('1', 2, 10) + 0),
(4,	1,	1,	4,	0,	CONV('1', 2, 10) + 0),
(5,	1,	1,	5,	0,	CONV('0', 2, 10) + 0);

DROP TABLE IF EXISTS `t_region`;
CREATE TABLE `t_region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `region_name` varchar(20) COLLATE utf8_slovak_ci DEFAULT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_region` (`id`, `region_name`, `visible`) VALUES
(1,	'Západoslovenský',	CONV('1', 2, 10) + 0),
(2,	'Stredoslovenský',	CONV('1', 2, 10) + 0),
(3,	'Východoslovenský',	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `t_subject`;
CREATE TABLE `t_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `brief_description` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `description` text COLLATE utf8_slovak_ci,
  `submitter_name` varchar(50) DEFAULT NULL,
  `public_body_id` int(11) DEFAULT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_subject` (`id`, `brief_description`, `description`, `submitter_name`, `public_body_id`, `visible`) VALUES
(1,	'Výberové konanie lesníckej firmy',	NULL,	'kokosko',	1,	CONV('1', 2, 10) + 0),
(2,	'Výberové konanie na firmu',	NULL,	'kokosko',	1,	CONV('1', 2, 10) + 0),
(3,	'Výberové konanie stavebnej firmy',	NULL,	'kokosko',	1,	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `t_tenure`;
CREATE TABLE `t_tenure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `since` date DEFAULT NULL,
  `till` date DEFAULT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_tenure` (`id`, `since`, `till`, `visible`) VALUES
(1,	'2011-07-04',	NULL,	CONV('1', 2, 10) + 0);


DROP TABLE IF EXISTS `t_vote`;
CREATE TABLE `t_vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vote_date` date DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `internal_nr` varchar(30) COLLATE utf8_slovak_ci DEFAULT NULL,
  `result_vote` smallint(6) DEFAULT '0',
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_vote` (`id`, `vote_date`, `subject_id`, `internal_nr`, `result_vote`, `visible`) VALUES
(1,	'2006-07-04',	1,	'957-KNR',	1,	CONV('1', 2, 10) + 0),
(2,	'2007-07-04',	2,	'958-KNR',	1,	CONV('1', 2, 10) + 0),
(3,	'2008-07-04',	3,	'959-KNR',	1,	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `t_vote_classification`;
CREATE TABLE `t_vote_classification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vote_id` int(11) DEFAULT NULL,
  `public_usefulness` smallint(6) DEFAULT '2',
  `brief_description` text COLLATE utf8_slovak_ci,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;


DROP TABLE IF EXISTS `t_vote_of_role`;
CREATE TABLE `t_vote_of_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `public_role_id` int(11) DEFAULT NULL,
  `vote_id` int(11) DEFAULT NULL,
  `decision` smallint(6) DEFAULT '0',
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_vote_of_role` (`id`, `public_role_id`, `vote_id`, `decision`, `visible`) VALUES
(1,	1,	1,	0,	CONV('1', 2, 10) + 0),
(2,	2,	1,	0,	CONV('1', 2, 10) + 0),
(3,	3,	1,	0,	CONV('1', 2, 10) + 0),
(4,	4,	1,	0,	CONV('1', 2, 10) + 0),
(5,	5,	1,	2,	CONV('0', 2, 10) + 0),
(6,	1,	2,	0,	CONV('1', 2, 10) + 0),
(7,	2,	2,	0,	CONV('1', 2, 10) + 0),
(8,	3,	2,	0,	CONV('1', 2, 10) + 0),
(9,	4,	2,	2,	CONV('1', 2, 10) + 0),
(10,	5,	2,	2,	CONV('0', 2, 10) + 0),
(11,	1,	3,	0,	CONV('1', 2, 10) + 0),
(12,	2,	3,	1,	CONV('1', 2, 10) + 0),
(13,	3,	3,	3,	CONV('1', 2, 10) + 0),
(14,	4,	3,	1,	CONV('1', 2, 10) + 0),
(15,	5,	3,	1,	CONV('0', 2, 10) + 0);

-- 2015-10-31 17:00:02
