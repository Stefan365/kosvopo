-- 1.
CREATE TABLE a_user 
(
id INT(11) AUTO_INCREMENT, 
first_name VARCHAR(20), 
last_name VARCHAR(20),
e_mail VARCHAR(50),
login VARCHAR(50),
password BLOB,
visible BIT DEFAULT 1,
 
CONSTRAINT PRIMARY KEY (id)
); 


-- 2.
CREATE TABLE a_role 
(
id INT(11) AUTO_INCREMENT, 
role SMALLINT,
role_name VARCHAR(30),
rights_description TEXT,
visible BIT DEFAULT 1,

CONSTRAINT PRIMARY KEY (id) 
); 

-- 3.
CREATE TABLE a_user_role 
(
id INT(11) AUTO_INCREMENT, 
role_id INT(11) DEFAULT 1,
user_id INT(11),
actual BIT DEFAULT 1,
since DATE,
till DATE, 
visible BIT DEFAULT 1,

CONSTRAINT PRIMARY KEY (id)
); 



-- 4.
CREATE TABLE a_change
(
id INT(11) AUTO_INCREMENT,
date_stamp DATE,
user_id INT(11),
public_body_id INT(11),
table_name VARCHAR(30),
column_name VARCHAR(30),
row_id INT(11),
old_value BLOB,
new_value BLOB,

visible BIT DEFAULT 1,

CONSTRAINT PRIMARY KEY (id)
);


-- 5.
CREATE TABLE t_tenure
(
id INT(11) AUTO_INCREMENT,
since DATE,
till DATE,
visible BIT DEFAULT 1, 

CONSTRAINT PRIMARY KEY (id)
);

-- 6.
CREATE TABLE t_public_person
(
id INT(11) AUTO_INCREMENT,
first_name VARCHAR(50) ,
last_name VARCHAR(50) ,
date_of_birth  DATE ,
visible BIT DEFAULT 1,
 
CONSTRAINT PRIMARY KEY (id)
);


-- 7.
CREATE TABLE t_person_classification
(
id INT(11) AUTO_INCREMENT,
classification_date DATE ,
public_person_id INT(11) ,
stability SMALLINT DEFAULT 2,
public_usefulness SMALLINT DEFAULT 2,
actual BIT DEFAULT 1,
visible BIT DEFAULT 1,
 
CONSTRAINT PRIMARY KEY (id)
);

-- 8.0.
CREATE TABLE t_region 
(
id INT(11) AUTO_INCREMENT, 
region_name VARCHAR(20), 
visible BIT DEFAULT 1,

CONSTRAINT PRIMARY KEY (id)
);


-- 8.1.
CREATE TABLE t_district 
(
id INT(11) AUTO_INCREMENT, 
district_name VARCHAR(20), 
region_id INT(11),
visible BIT DEFAULT 1,

CONSTRAINT PRIMARY KEY (id)
);


-- 8.2.
CREATE TABLE t_location 
(
id INT(11)  AUTO_INCREMENT, 
location_name VARCHAR(50), 
town_section VARCHAR(50), 
district_id INT(11), 
visible BIT  DEFAULT 1,

CONSTRAINT PRIMARY KEY (id)
);


-- 9.
CREATE TABLE t_public_body
(
id INT(11) AUTO_INCREMENT,
name VARCHAR(50),
location_id INT(11),
visible BIT  DEFAULT 1,
 
CONSTRAINT PRIMARY KEY (id)
);

-- 10.
CREATE TABLE t_public_role
(
id INT(11) AUTO_INCREMENT,
public_body_id INT(11),
tenure_id INT(11),
public_person_id INT(11),
name SMALLINT DEFAULT 0,
visible BIT  DEFAULT 1,

CONSTRAINT PRIMARY KEY (id)
);

-- 11.
CREATE TABLE t_theme
(
id INT(11) AUTO_INCREMENT,
brief_description VARCHAR(50),
description TEXT,
visible BIT  DEFAULT 1,
 
CONSTRAINT PRIMARY KEY (id)
);


-- 12. VOTING SUBJECT
CREATE TABLE t_subject
(
id INT(11) AUTO_INCREMENT,
brief_description VARCHAR(50),
description TEXT,
public_role_id INT(11),
theme_id INT(11),
visible BIT  DEFAULT 1, 

CONSTRAINT PRIMARY KEY (id)
);


-- 13. VOTE
CREATE TABLE t_vote
(
id INT(11) AUTO_INCREMENT,
vote_date DATE ,
subject_id INT(11),
internal_nr VARCHAR(30),
result_vote SMALLINT DEFAULT 0, 
visible BIT  DEFAULT 1,

CONSTRAINT PRIMARY KEY (id)
);

-- 14.
CREATE TABLE t_vote_of_role
(
id INT(11) AUTO_INCREMENT,
public_role_id INT(11),
vote_id INT(11),
decision SMALLINT DEFAULT 0,
visible BIT  DEFAULT 1, 

CONSTRAINT PRIMARY KEY (id)
);

-- 15.
CREATE TABLE t_vote_classification
(
id INT(11) AUTO_INCREMENT,
vote_id  INT(11) ,
public_usefulness SMALLINT DEFAULT 2,
brief_description TEXT,
visible BIT  DEFAULT 1,

CONSTRAINT vcl_PK PRIMARY KEY(id)
);

-- 16.
CREATE TABLE t_document
(
id INT(11) AUTO_INCREMENT,
table_name VARCHAR(30),
table_row_id INT(11),
file_name VARCHAR(30),
upload_date DATE,
document MEDIUMBLOB,
visible BIT DEFAULT 1,

CONSTRAINT doc_PK PRIMARY KEY(id)
);

-- 17.
CREATE TABLE a_hierarchy
(
id INT(11) AUTO_INCREMENT,
table_name VARCHAR(30),
boss_table VARCHAR(30),
boss_reference VARCHAR(30),

CONSTRAINT hie_PK PRIMARY KEY(id)
);

-- 18.
CREATE TABLE t_note
(
id INT(11) NOT NULL  AUTO_INCREMENT,
table_name VARCHAR(30) NOT NULL,
table_row_id INT(11) NOT NULL,
title VARCHAR(30) NOT NULL,
note_date DATE NOT NULL,
note TEXT NOT NULL,
visible BIT NOT NULL DEFAULT 1,

CONSTRAINT not_PK PRIMARY KEY(id)
);