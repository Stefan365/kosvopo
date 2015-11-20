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
  `old_value` blob,
  `new_value` blob,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `a_change` (`id`, `date_stamp`, `user_id`, `public_body_id`, `table_name`, `column_name`, `row_id`, `old_value`, `new_value`, `visible`) VALUES
(1,	'2015-06-20',	4,	NULL,	't_public_person',	'first_name',	4,	'Alex',	'Alexej',	CONV('1', 2, 10) + 0),
(2,	'2015-06-20',	4,	NULL,	't_public_person',	'last_name',	1,	'Proch·zka',	'Proch·zkar',	CONV('1', 2, 10) + 0),
(3,	'2015-06-20',	4,	NULL,	't_public_person',	'last_name',	1,	'Proch·zkar',	'Proch·zkare',	CONV('1', 2, 10) + 0),
(4,	'2015-06-20',	4,	NULL,	't_public_person',	'first_name',	2,	'Karol',	'Karolko',	CONV('1', 2, 10) + 0),
(5,	'2015-06-20',	4,	NULL,	't_public_person',	'date_of_birth',	2,	'1966-05-13',	'1966-05-17',	CONV('1', 2, 10) + 0),
(6,	'2015-06-20',	4,	NULL,	't_public_person',	'last_name',	2,	'?˙tora',	'?˙tora',	CONV('1', 2, 10) + 0),
(7,	'2015-06-20',	4,	NULL,	't_public_person',	'visible',	5,	'true',	'false',	CONV('1', 2, 10) + 0),
(8,	'2015-06-20',	4,	NULL,	't_person_classification',	'visible',	2,	'true',	'false',	CONV('1', 2, 10) + 0),
(9,	'2015-06-20',	4,	NULL,	't_public_role',	'visible',	5,	'true',	'false',	CONV('1', 2, 10) + 0),
(10,	'2015-06-20',	4,	NULL,	't_vote_of_role',	'visible',	5,	'true',	'false',	CONV('1', 2, 10) + 0),
(11,	'2015-06-20',	4,	NULL,	't_vote_of_role',	'visible',	10,	'true',	'false',	CONV('1', 2, 10) + 0),
(12,	'2015-06-20',	4,	NULL,	't_vote_of_role',	'visible',	15,	'true',	'false',	CONV('1', 2, 10) + 0),
(13,	'2015-06-20',	4,	NULL,	't_vote_of_role',	'visible',	20,	'true',	'false',	CONV('1', 2, 10) + 0),
(14,	'2015-06-20',	4,	NULL,	't_public_person',	'date_of_birth',	1,	'1956-09-11',	'1956-09-07',	CONV('1', 2, 10) + 0),
(15,	'2015-07-08',	4,	NULL,	't_public_body',	'visible',	7,	'null',	'true',	CONV('1', 2, 10) + 0),
(16,	'2015-07-08',	4,	NULL,	't_public_body',	'name',	7,	'null',	'KOKOSOVO',	CONV('1', 2, 10) + 0),
(17,	'2015-07-08',	4,	NULL,	't_public_body',	'id',	7,	'null',	'7',	CONV('1', 2, 10) + 0),
(18,	'2015-07-08',	4,	NULL,	't_public_body',	'location_id',	7,	'null',	'null',	CONV('1', 2, 10) + 0),
(19,	'2015-07-10',	4,	NULL,	't_public_person',	'visible',	6,	'null',	'true',	CONV('1', 2, 10) + 0),
(20,	'2015-07-10',	4,	NULL,	't_public_person',	'date_of_birth',	6,	'null',	'2001-07-10',	CONV('1', 2, 10) + 0),
(21,	'2015-07-10',	4,	NULL,	't_public_person',	'last_name',	6,	'null',	'Oblazek',	CONV('1', 2, 10) + 0),
(22,	'2015-07-10',	4,	NULL,	't_public_person',	'id',	6,	'null',	'6',	CONV('1', 2, 10) + 0),
(23,	'2015-07-10',	4,	NULL,	't_public_person',	'first_name',	6,	'null',	'Pavol',	CONV('1', 2, 10) + 0),
(24,	'2015-07-10',	4,	NULL,	't_public_person',	'visible',	7,	'null',	'true',	CONV('1', 2, 10) + 0),
(25,	'2015-07-10',	4,	NULL,	't_public_person',	'date_of_birth',	7,	'null',	'1989-07-05',	CONV('1', 2, 10) + 0),
(26,	'2015-07-10',	4,	NULL,	't_public_person',	'last_name',	7,	'null',	'Predseda',	CONV('1', 2, 10) + 0),
(27,	'2015-07-10',	4,	NULL,	't_public_person',	'id',	7,	'null',	'7',	CONV('1', 2, 10) + 0),
(28,	'2015-07-10',	4,	NULL,	't_public_person',	'first_name',	7,	'null',	'Karolko',	CONV('1', 2, 10) + 0),
(29,	'2015-07-10',	4,	NULL,	't_district',	'district_name',	15,	'null',	'KOLAROVO',	CONV('1', 2, 10) + 0),
(30,	'2015-07-10',	4,	NULL,	't_district',	'visible',	15,	'null',	'true',	CONV('1', 2, 10) + 0),
(31,	'2015-07-10',	4,	NULL,	't_district',	'region_id',	15,	'null',	'2',	CONV('1', 2, 10) + 0),
(32,	'2015-07-10',	4,	NULL,	't_district',	'id',	15,	'null',	'15',	CONV('1', 2, 10) + 0),
(33,	'2015-07-10',	4,	NULL,	't_district',	'district_name',	16,	'null',	'Kremnica',	CONV('1', 2, 10) + 0),
(34,	'2015-07-10',	4,	NULL,	't_district',	'visible',	16,	'null',	'true',	CONV('1', 2, 10) + 0),
(35,	'2015-07-10',	4,	NULL,	't_district',	'region_id',	16,	'null',	'2',	CONV('1', 2, 10) + 0),
(36,	'2015-07-10',	4,	NULL,	't_district',	'id',	16,	'null',	'16',	CONV('1', 2, 10) + 0),
(37,	'2015-07-10',	4,	NULL,	't_vote_classification',	'visible',	5,	'null',	'true',	CONV('1', 2, 10) + 0),
(38,	'2015-07-10',	4,	NULL,	't_vote_classification',	'public_usefulness',	5,	'null',	'PU3',	CONV('1', 2, 10) + 0),
(39,	'2015-07-10',	4,	NULL,	't_vote_classification',	'brief_description',	5,	'null',	'kokocina',	CONV('1', 2, 10) + 0),
(40,	'2015-07-10',	4,	NULL,	't_vote_classification',	'vote_id',	5,	'null',	'1',	CONV('1', 2, 10) + 0),
(41,	'2015-07-10',	4,	NULL,	't_vote_classification',	'id',	5,	'null',	'5',	CONV('1', 2, 10) + 0),
(42,	'2015-07-10',	4,	NULL,	't_vote_classification',	'visible',	6,	'null',	'true',	CONV('1', 2, 10) + 0),
(43,	'2015-07-10',	4,	NULL,	't_vote_classification',	'public_usefulness',	6,	'null',	'PU3',	CONV('1', 2, 10) + 0),
(44,	'2015-07-10',	4,	NULL,	't_vote_classification',	'brief_description',	6,	'null',	'kokocina',	CONV('1', 2, 10) + 0),
(45,	'2015-07-10',	4,	NULL,	't_vote_classification',	'vote_id',	6,	'null',	'1',	CONV('1', 2, 10) + 0),
(46,	'2015-07-10',	4,	NULL,	't_vote_classification',	'id',	6,	'null',	'6',	CONV('1', 2, 10) + 0),
(47,	'2015-07-10',	4,	NULL,	't_person_classification',	'public_usefulness',	4,	'PU1',	'PU4',	CONV('1', 2, 10) + 0),
(48,	'2015-07-10',	4,	NULL,	't_person_classification',	'stability',	4,	'S1',	'S5',	CONV('1', 2, 10) + 0),
(49,	'2015-07-10',	4,	NULL,	't_person_classification',	'actual',	6,	'null',	'null',	CONV('1', 2, 10) + 0),
(50,	'2015-07-10',	4,	NULL,	't_person_classification',	'visible',	6,	'null',	'true',	CONV('1', 2, 10) + 0),
(51,	'2015-07-10',	4,	NULL,	't_person_classification',	'public_usefulness',	6,	'null',	'PU5',	CONV('1', 2, 10) + 0),
(52,	'2015-07-10',	4,	NULL,	't_person_classification',	'classification_date',	6,	'null',	'2015-07-14',	CONV('1', 2, 10) + 0),
(53,	'2015-07-10',	4,	NULL,	't_person_classification',	'id',	6,	'null',	'6',	CONV('1', 2, 10) + 0),
(54,	'2015-07-10',	4,	NULL,	't_person_classification',	'stability',	6,	'null',	'S3',	CONV('1', 2, 10) + 0),
(55,	'2015-07-10',	4,	NULL,	't_person_classification',	'public_person_id',	6,	'null',	'3',	CONV('1', 2, 10) + 0),
(56,	'2015-07-10',	4,	NULL,	't_person_classification',	'actual',	7,	'null',	'null',	CONV('1', 2, 10) + 0),
(57,	'2015-07-10',	4,	NULL,	't_person_classification',	'visible',	7,	'null',	'true',	CONV('1', 2, 10) + 0),
(58,	'2015-07-10',	4,	NULL,	't_person_classification',	'public_usefulness',	7,	'null',	'PU1',	CONV('1', 2, 10) + 0),
(59,	'2015-07-10',	4,	NULL,	't_person_classification',	'classification_date',	7,	'null',	'2015-07-10',	CONV('1', 2, 10) + 0),
(60,	'2015-07-10',	4,	NULL,	't_person_classification',	'id',	7,	'null',	'7',	CONV('1', 2, 10) + 0),
(61,	'2015-07-10',	4,	NULL,	't_person_classification',	'stability',	7,	'null',	'S5',	CONV('1', 2, 10) + 0),
(62,	'2015-07-10',	4,	NULL,	't_person_classification',	'public_person_id',	7,	'null',	'1',	CONV('1', 2, 10) + 0),
(63,	'2015-07-10',	4,	NULL,	't_person_classification',	'visible',	7,	'true',	'false',	CONV('1', 2, 10) + 0),
(64,	'2015-07-31',	4,	NULL,	't_public_body',	'visible',	8,	'null',	'true',	CONV('1', 2, 10) + 0),
(65,	'2015-07-31',	4,	NULL,	't_public_body',	'name',	8,	'null',	'Mestky urad Kokava nad rimavicou',	CONV('1', 2, 10) + 0),
(66,	'2015-07-31',	4,	NULL,	't_public_body',	'id',	8,	'null',	'8',	CONV('1', 2, 10) + 0),
(67,	'2015-07-31',	4,	NULL,	't_public_body',	'location_id',	8,	'null',	'3',	CONV('1', 2, 10) + 0),
(68,	'2015-07-31',	4,	NULL,	't_public_body',	'name',	8,	'Mestky urad Kokava nad rimavicou',	'Mestky urad Kokava nad Rimavicou',	CONV('1', 2, 10) + 0),
(69,	'2015-07-31',	4,	NULL,	't_public_person',	'visible',	8,	'null',	'true',	CONV('1', 2, 10) + 0),
(70,	'2015-07-31',	4,	NULL,	't_public_person',	'date_of_birth',	8,	'null',	'1993-07-14',	CONV('1', 2, 10) + 0),
(71,	'2015-07-31',	4,	NULL,	't_public_person',	'last_name',	8,	'null',	'Sebes',	CONV('1', 2, 10) + 0),
(72,	'2015-07-31',	4,	NULL,	't_public_person',	'id',	8,	'null',	'8',	CONV('1', 2, 10) + 0),
(73,	'2015-07-31',	4,	NULL,	't_public_person',	'first_name',	8,	'null',	'Kamil',	CONV('1', 2, 10) + 0),
(74,	'2015-08-07',	4,	NULL,	't_public_role',	'name',	3,	'PREDSEDA',	'POSLANEC',	CONV('1', 2, 10) + 0),
(75,	'2015-09-04',	4,	NULL,	't_public_person',	'last_name',	2,	'?˙tora',	'?˙torako',	CONV('1', 2, 10) + 0),
(76,	'2015-09-04',	4,	NULL,	't_public_body',	'name',	5,	'MestkÈ zastupite?stvo Koöice z·humienok',	'MestkÈ zastupitelstvo Koöice z·humienok',	CONV('1', 2, 10) + 0),
(77,	'2015-09-04',	4,	NULL,	't_public_body',	'name',	4,	'MestkÈ zastupite?stvo R·zto?no',	'MestkÈ zastup R·zto?no',	CONV('1', 2, 10) + 0),
(78,	'2015-09-04',	4,	NULL,	't_public_body',	'name',	7,	'KOKOSOVO',	'KOsakovo',	CONV('1', 2, 10) + 0),
(79,	'2015-09-04',	4,	NULL,	't_public_body',	'name',	5,	'MestkÈ zastupitelstvo Koöice z·humienok',	'MestkÈ zvtovo Koöice z·humienok',	CONV('1', 2, 10) + 0),
(80,	'2015-09-06',	4,	NULL,	't_public_body',	'visible',	9,	'null',	'true',	CONV('1', 2, 10) + 0),
(81,	'2015-09-06',	4,	NULL,	't_public_body',	'name',	9,	'null',	'KUKUKUK',	CONV('1', 2, 10) + 0),
(82,	'2015-09-06',	4,	NULL,	't_public_body',	'id',	9,	'null',	'9',	CONV('1', 2, 10) + 0),
(83,	'2015-09-06',	4,	NULL,	't_public_body',	'location_id',	9,	'null',	'1',	CONV('1', 2, 10) + 0),
(84,	'2015-09-06',	4,	NULL,	't_public_role',	'public_body_id',	6,	'null',	'4',	CONV('1', 2, 10) + 0),
(85,	'2015-09-06',	4,	NULL,	't_public_role',	'visible',	6,	'null',	'true',	CONV('1', 2, 10) + 0),
(86,	'2015-09-06',	4,	NULL,	't_public_role',	'name',	6,	'null',	'PODPREDSEDA',	CONV('1', 2, 10) + 0),
(87,	'2015-09-06',	4,	NULL,	't_public_role',	'id',	6,	'null',	'6',	CONV('1', 2, 10) + 0),
(88,	'2015-09-06',	4,	NULL,	't_public_role',	'tenure_id',	6,	'null',	'6',	CONV('1', 2, 10) + 0),
(89,	'2015-09-06',	4,	NULL,	't_public_role',	'public_person_id',	6,	'null',	'6',	CONV('1', 2, 10) + 0),
(90,	'2015-09-06',	4,	NULL,	't_tenure',	'till',	7,	'null',	'2015-09-17',	CONV('1', 2, 10) + 0),
(91,	'2015-09-06',	4,	NULL,	't_tenure',	'visible',	7,	'null',	'true',	CONV('1', 2, 10) + 0),
(92,	'2015-09-06',	4,	NULL,	't_tenure',	'id',	7,	'null',	'7',	CONV('1', 2, 10) + 0),
(93,	'2015-09-06',	4,	NULL,	't_tenure',	'since',	7,	'null',	'2015-09-15',	CONV('1', 2, 10) + 0),
(94,	'2015-09-12',	4,	NULL,	't_public_body',	'name',	4,	'MestkÈ zastup R·zto?no',	'MestkÈ zastup R·ztocno',	CONV('1', 2, 10) + 0),
(95,	'2015-09-12',	4,	NULL,	't_public_body',	'name',	9,	'KUKUKUK',	'Moslimske zastupitelstvo Raca',	CONV('1', 2, 10) + 0),
(96,	'2015-09-12',	4,	NULL,	't_public_body',	'visible',	10,	'null',	'true',	CONV('1', 2, 10) + 0),
(97,	'2015-09-12',	4,	NULL,	't_public_body',	'name',	10,	'null',	'kokosak',	CONV('1', 2, 10) + 0),
(98,	'2015-09-12',	4,	NULL,	't_public_body',	'id',	10,	'null',	'10',	CONV('1', 2, 10) + 0),
(99,	'2015-09-12',	4,	NULL,	't_public_body',	'location_id',	10,	'null',	'3',	CONV('1', 2, 10) + 0),
(100,	'2015-09-12',	4,	NULL,	't_public_body',	'visible',	11,	'null',	'null',	CONV('1', 2, 10) + 0),
(101,	'2015-09-12',	4,	NULL,	't_public_body',	'name',	11,	'null',	'Samaritansky kruzok, Handlova',	CONV('1', 2, 10) + 0),
(102,	'2015-09-12',	4,	NULL,	't_public_body',	'id',	11,	'null',	'11',	CONV('1', 2, 10) + 0),
(103,	'2015-09-12',	4,	NULL,	't_public_body',	'location_id',	11,	'null',	'3',	CONV('1', 2, 10) + 0),
(104,	'2015-09-20',	4,	NULL,	't_public_body',	'name',	5,	'MestkÈ zvtovo Koöice z·humienok',	'MestkÈ zastupitelstvo Koöice z·humienok',	CONV('1', 2, 10) + 0),
(105,	'2015-09-20',	4,	NULL,	't_public_body',	'visible',	12,	'null',	'true',	CONV('1', 2, 10) + 0),
(106,	'2015-09-20',	4,	NULL,	't_public_body',	'name',	12,	'null',	'kokosatcke zastupitelstvo',	CONV('1', 2, 10) + 0),
(107,	'2015-09-20',	4,	NULL,	't_public_body',	'id',	12,	'null',	'12',	CONV('1', 2, 10) + 0),
(108,	'2015-09-20',	4,	NULL,	't_public_body',	'location_id',	12,	'null',	'4',	CONV('1', 2, 10) + 0),
(109,	'2015-09-20',	4,	NULL,	't_public_body',	'name',	10,	'kokosak',	'kokosakce zastupitestvo v Handlovej',	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `a_hierarchy`;
CREATE TABLE `a_hierarchy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(30) COLLATE utf8_slovak_ci DEFAULT NULL,
  `boss_table` varchar(30) COLLATE utf8_slovak_ci DEFAULT NULL,
  `boss_reference` varchar(30) COLLATE utf8_slovak_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `a_hierarchy` (`id`, `table_name`, `boss_table`, `boss_reference`) VALUES
(1,	't_public_person',	NULL,	NULL),
(2,	't_tenure',	NULL,	NULL),
(3,	't_theme',	NULL,	NULL),
(4,	't_region',	NULL,	NULL),
(5,	't_district',	't_region',	'region_id'),
(6,	't_location',	't_district',	'district_id'),
(7,	't_public_body',	't_location',	'location_id'),
(8,	't_person_classification',	't_public_person',	'public_person_id'),
(9,	't_public_role',	't_tenure',	'tenure_id'),
(10,	't_public_role',	't_public_body',	'public_body_id'),
(11,	't_public_role',	't_public_person',	'public_person_id'),
(12,	't_subject',	't_theme',	'theme_id'),
(13,	't_subject',	't_public_role',	'public_role_id'),
(14,	't_vote',	't_subject',	'subject_id'),
(15,	't_vote_classification',	't_vote',	'vote_id'),
(16,	't_vote_of_role',	't_vote',	'vote_id'),
(17,	't_vote_of_role',	't_public_role',	'public_role_id'),
(18,	'a_user',	NULL,	NULL),
(19,	'a_role',	NULL,	NULL),
(20,	'a_user_role',	'a_user',	'user_id'),
(21,	'a_user_role',	'a_role',	'role_id');

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
(1,	0,	'Dobrovoƒæn√≠k',	'v≈°etky pr√°va (vkladanie nov√Ωch ent√≠t, ...), okrem priameho vstupu do datab√°zy',	CONV('1', 2, 10) + 0),
(2,	1,	'Admin',	'v≈°etky pr√°va Dobrovoƒæn√≠k-a, vr√°tane pr√°v na √∫pravu datab√°zy',	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `a_user`;
CREATE TABLE `a_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) COLLATE utf8_slovak_ci DEFAULT NULL,
  `last_name` varchar(20) COLLATE utf8_slovak_ci DEFAULT NULL,
  `e_mail` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `login` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `password` blob,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `a_user` (`id`, `first_name`, `last_name`, `e_mail`, `login`, `password`, `visible`) VALUES
(1,	'Peter',	'Sarkoci',	'peter.sarkoci@gmail.com',	'petak',	'Äz∆y©ÓÎpN´Ä\\ìe',	CONV('1', 2, 10) + 0),
(2,	'≈†tefan',	'Vere≈°',	'stefan.veres@gmail.com',	'stefan',	'.óÇ.\Zà4 =´∂YÏ',	CONV('1', 2, 10) + 0),
(3,	'Miro',	'Sƒçibr√°nyi',	'miro.scibranyi@gmail.com',	'miro',	'‡ÍÛÑå•øì¢¬°–dçD',	CONV('1', 2, 10) + 0),
(4,	'admin',	'adminovic',	'admin@admin.sk',	'admin',	'!#/)zW•ßCâJJÄ√',	CONV('1', 2, 10) + 0);

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
(1,	1,	1,	CONV('1', 2, 10) + 0,	'2015-06-19',	NULL,	CONV('1', 2, 10) + 0),
(2,	1,	2,	CONV('1', 2, 10) + 0,	'2015-06-19',	NULL,	CONV('1', 2, 10) + 0),
(3,	2,	2,	CONV('1', 2, 10) + 0,	'2015-06-19',	NULL,	CONV('1', 2, 10) + 0),
(4,	2,	4,	CONV('1', 2, 10) + 0,	'2015-06-20',	NULL,	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `t_district`;
CREATE TABLE `t_district` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `district_name` varchar(20) COLLATE utf8_slovak_ci DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_district` (`id`, `district_name`, `region_id`, `visible`) VALUES
(1,	'Bratislava',	1,	CONV('1', 2, 10) + 0),
(2,	'Trnava',	1,	CONV('1', 2, 10) + 0),
(3,	'Nitra',	1,	CONV('1', 2, 10) + 0),
(4,	'Trenƒç√≠n',	1,	CONV('1', 2, 10) + 0),
(5,	'Prievidza',	2,	CONV('1', 2, 10) + 0),
(6,	'Bansk√° Bystrica',	2,	CONV('1', 2, 10) + 0),
(7,	'Zvolen',	2,	CONV('1', 2, 10) + 0),
(8,	'≈Ωarnovica',	2,	CONV('1', 2, 10) + 0),
(9,	'Luƒçenec',	2,	CONV('1', 2, 10) + 0),
(10,	'Kosice',	3,	CONV('1', 2, 10) + 0),
(11,	'Michalovce',	3,	CONV('1', 2, 10) + 0),
(12,	'Zvolen',	3,	CONV('1', 2, 10) + 0),
(13,	'Roznava',	3,	CONV('1', 2, 10) + 0),
(14,	'Presov',	3,	CONV('1', 2, 10) + 0),
(15,	'KOLAROVO',	2,	CONV('1', 2, 10) + 0),
(16,	'Kremnica',	2,	CONV('1', 2, 10) + 0);

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
(1,	'Bratislava',	'Raƒça',	1,	CONV('1', 2, 10) + 0),
(2,	'Bratislava',	'D√∫bravka',	1,	CONV('1', 2, 10) + 0),
(3,	'Handlov√°',	NULL,	5,	CONV('1', 2, 10) + 0),
(4,	'Nov√°ky',	NULL,	5,	CONV('1', 2, 10) + 0),
(5,	'Patriz√°nske',	NULL,	5,	CONV('1', 2, 10) + 0),
(6,	'R√°ztoƒçno',	NULL,	5,	CONV('1', 2, 10) + 0),
(7,	'Topoƒæƒçany',	NULL,	3,	CONV('1', 2, 10) + 0),
(8,	'Ko≈°ice',	'Z√°humienok',	10,	CONV('1', 2, 10) + 0),
(9,	'Sereƒè',	NULL,	1,	CONV('1', 2, 10) + 0);

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

INSERT INTO `t_person_classification` (`id`, `classification_date`, `public_person_id`, `stability`, `public_usefulness`, `actual`, `visible`) VALUES
(1,	'2015-06-19',	4,	3,	3,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(2,	'2015-06-19',	5,	3,	4,	CONV('0', 2, 10) + 0,	CONV('0', 2, 10) + 0),
(3,	'2015-06-19',	3,	2,	2,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(4,	'2015-06-19',	2,	4,	3,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(5,	'2015-06-19',	1,	4,	2,	CONV('0', 2, 10) + 0,	CONV('1', 2, 10) + 0),
(6,	'2015-07-14',	3,	2,	4,	NULL,	CONV('1', 2, 10) + 0),
(7,	'2015-07-10',	1,	4,	0,	NULL,	CONV('0', 2, 10) + 0);

DROP TABLE IF EXISTS `t_public_body`;
CREATE TABLE `t_public_body` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_public_body` (`id`, `name`, `location_id`, `visible`) VALUES
(1,	'Mestk√© zastupiteƒæstvo Bratislava Raƒça',	1,	CONV('1', 2, 10) + 0),
(2,	'Mestk√© zastupiteƒæstvo Handlov√°',	3,	CONV('1', 2, 10) + 0),
(3,	'Mestk√© zastupiteƒæstvo Nov√°ky',	4,	CONV('1', 2, 10) + 0),
(4,	'Mestk√© zastup R√°ztocno',	6,	CONV('1', 2, 10) + 0),
(5,	'Mestk√© zastupitelstvo Ko≈°ice z√°humienok',	8,	CONV('1', 2, 10) + 0),
(6,	'Mestk√© zastupiteƒæstvo Sereƒè',	9,	CONV('1', 2, 10) + 0),
(7,	'KOsakovo',	4,	CONV('1', 2, 10) + 0),
(8,	'Mestky urad Kokava nad Rimavicou',	3,	CONV('1', 2, 10) + 0),
(9,	'Moslimske zastupitelstvo Raca',	1,	CONV('1', 2, 10) + 0),
(10,	'kokosakce zastupitestvo v Handlovej',	3,	CONV('1', 2, 10) + 0),
(11,	'Samaritansky kruzok, Handlova',	3,	CONV('1', 2, 10) + 0),
(12,	'kokosatcke zastupitelstvo',	4,	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `t_public_person`;
CREATE TABLE `t_public_person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `last_name` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_public_person` (`id`, `first_name`, `last_name`, `date_of_birth`, `visible`) VALUES
(1,	'Johan',	'Proch√°zkare',	'1956-09-07',	CONV('1', 2, 10) + 0),
(2,	'Karolko',	'?√∫torako',	'1966-05-17',	CONV('1', 2, 10) + 0),
(3,	'Lubo≈°',	'Jozef√≠ny',	'1954-12-21',	CONV('1', 2, 10) + 0),
(4,	'Alexej',	'Spev√°k',	'1979-09-01',	CONV('1', 2, 10) + 0),
(5,	'Emil',	'ƒå√∫tora',	'1967-04-01',	CONV('0', 2, 10) + 0),
(6,	'Pavol',	'Oblazek',	'2001-07-10',	CONV('1', 2, 10) + 0),
(7,	'Karolko',	'Predseda',	'1989-07-05',	CONV('1', 2, 10) + 0),
(8,	'Kamil',	'Sebes',	'1993-07-14',	CONV('1', 2, 10) + 0);

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
(1,	5,	1,	1,	0,	CONV('1', 2, 10) + 0),
(2,	5,	1,	2,	0,	CONV('1', 2, 10) + 0),
(3,	5,	1,	3,	0,	CONV('1', 2, 10) + 0),
(4,	5,	1,	4,	0,	CONV('1', 2, 10) + 0),
(5,	5,	1,	5,	0,	CONV('0', 2, 10) + 0),
(6,	4,	6,	6,	2,	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `t_region`;
CREATE TABLE `t_region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `region_name` varchar(20) COLLATE utf8_slovak_ci DEFAULT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_region` (`id`, `region_name`, `visible`) VALUES
(1,	'Z√°padoslovensk√Ω',	CONV('1', 2, 10) + 0),
(2,	'Stredoslovensk√Ω',	CONV('1', 2, 10) + 0),
(3,	'V√Ωchodoslovensk√Ω',	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `t_subject`;
CREATE TABLE `t_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `brief_description` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `description` text COLLATE utf8_slovak_ci,
  `public_role_id` int(11) DEFAULT NULL,
  `theme_id` int(11) DEFAULT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_subject` (`id`, `brief_description`, `description`, `public_role_id`, `theme_id`, `visible`) VALUES
(1,	'V√Ωberov√© konanie lesn√≠ckej firmy',	NULL,	1,	1,	CONV('1', 2, 10) + 0),
(2,	'V√Ωberov√© konanie na firmu',	NULL,	1,	4,	CONV('1', 2, 10) + 0),
(3,	'V√Ωberov√© konanie stavebnej firmy',	NULL,	1,	6,	CONV('1', 2, 10) + 0),
(4,	'Hlasovanie o odpredaji pozemku XYZ Uranovym baniam',	NULL,	1,	5,	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `t_tenure`;
CREATE TABLE `t_tenure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `since` date DEFAULT NULL,
  `till` date DEFAULT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_tenure` (`id`, `since`, `till`, `visible`) VALUES
(1,	'2011-07-04',	NULL,	CONV('1', 2, 10) + 0),
(2,	'2010-07-04',	NULL,	CONV('1', 2, 10) + 0),
(3,	'2009-07-04',	NULL,	CONV('1', 2, 10) + 0),
(4,	'2008-07-04',	NULL,	CONV('1', 2, 10) + 0),
(5,	'2007-07-04',	NULL,	CONV('1', 2, 10) + 0),
(6,	'2006-07-04',	NULL,	CONV('1', 2, 10) + 0),
(7,	'2015-09-15',	'2015-09-17',	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `t_theme`;
CREATE TABLE `t_theme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `brief_description` varchar(50) COLLATE utf8_slovak_ci DEFAULT NULL,
  `description` text COLLATE utf8_slovak_ci,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_theme` (`id`, `brief_description`, `description`, `visible`) VALUES
(1,	'K√°canie z√°humienkov√©ho lesa',	NULL,	CONV('1', 2, 10) + 0),
(2,	'Zru≈°enie obecej ≈°koly',	NULL,	CONV('1', 2, 10) + 0),
(3,	'Vym√°hanie pohƒæad√°vok',	NULL,	CONV('1', 2, 10) + 0),
(4,	'Stavba divokej skl√°dky',	NULL,	CONV('1', 2, 10) + 0),
(5,	'Predaj pozemkov ur√°nov√Ωm baniam',	NULL,	CONV('1', 2, 10) + 0),
(6,	'Dokonƒçenie stavby kult√∫rneho domu',	NULL,	CONV('1', 2, 10) + 0);

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
(1,	'2006-07-04',	1,	'957-AC',	1,	CONV('1', 2, 10) + 0),
(2,	'2007-07-04',	2,	'958-AC',	1,	CONV('1', 2, 10) + 0),
(3,	'2008-07-04',	2,	'959-AC',	1,	CONV('1', 2, 10) + 0),
(4,	'2009-07-04',	4,	'963-AC',	0,	CONV('1', 2, 10) + 0);

DROP TABLE IF EXISTS `t_vote_classification`;
CREATE TABLE `t_vote_classification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vote_id` int(11) DEFAULT NULL,
  `public_usefulness` smallint(6) DEFAULT '2',
  `brief_description` text COLLATE utf8_slovak_ci,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovak_ci;

INSERT INTO `t_vote_classification` (`id`, `vote_id`, `public_usefulness`, `brief_description`, `visible`) VALUES
(1,	1,	0,	'uplne na picu hlasovanie, akodlive jak das',	CONV('1', 2, 10) + 0),
(2,	2,	4,	'hlasovanie naozaj v zaujme obcanov',	CONV('1', 2, 10) + 0),
(3,	3,	2,	'nieco medzi, asi v tom bude tunel, nicmene stale je to prospesne verejnosti',	CONV('1', 2, 10) + 0),
(4,	4,	1,	'nieco medzi, asi v tom bude tunel, nicmene stale je to prospesne verejnosti',	CONV('1', 2, 10) + 0),
(5,	1,	2,	'kokocina',	CONV('1', 2, 10) + 0),
(6,	1,	2,	'kokocina',	CONV('1', 2, 10) + 0);

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
(15,	5,	3,	1,	CONV('0', 2, 10) + 0),
(16,	1,	4,	0,	CONV('1', 2, 10) + 0),
(17,	2,	4,	0,	CONV('1', 2, 10) + 0),
(18,	3,	4,	0,	CONV('1', 2, 10) + 0),
(19,	4,	4,	1,	CONV('1', 2, 10) + 0),
(20,	5,	4,	1,	CONV('0', 2, 10) + 0);

-- 2015-10-31 17:00:02
