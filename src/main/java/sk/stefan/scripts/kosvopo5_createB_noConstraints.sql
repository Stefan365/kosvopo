-- 1.
CREATE TABLE t_user 
(
id INT(11) AUTO_INCREMENT, 
first_name VARCHAR(20) , 
last_name VARCHAR(20) ,
e_mail VARCHAR(50) ,
login VARCHAR(50) ,
password VARCHAR(50) ,
 
CONSTRAINT PRIMARY KEY (id)
); 


-- 2.
CREATE TABLE t_role 
(
id INT(11)   AUTO_INCREMENT, 
role_name VARCHAR(50) , 
rights_description VARCHAR(500),
CONSTRAINT PRIMARY KEY (id) 

); 

-- 3.
CREATE TABLE t_user_role 
(
id INT(11)   AUTO_INCREMENT, 
role_id INT(11) ,
user_id INT(11) ,
since DATE ,
till DATE
, CONSTRAINT PRIMARY KEY (id)
); 



-- 4.
CREATE TABLE t_change
(
id INT(11)  AUTO_INCREMENT,
date_stamp DATE ,
user_id INT(11) ,
table_name VARCHAR(30) ,
row_id INT(11) ,
visible_status INT(1) 
, CONSTRAINT PRIMARY KEY (id)
);


-- 5.
CREATE TABLE t_tenure
(
id INT(11)   AUTO_INCREMENT,
since DATE ,
till DATE,
visible INT(1)  DEFAULT 1
, CONSTRAINT PRIMARY KEY (id)
);

-- 6.
CREATE TABLE t_public_person
(
id INT(11)   AUTO_INCREMENT,
first_name VARCHAR(50) ,
last_name VARCHAR(50) ,
date_of_birth  DATE ,
visible INT(1)  DEFAULT 1
, CONSTRAINT PRIMARY KEY (id)
);

-- 7.
CREATE TABLE t_person_classification
(
id INT(11)   AUTO_INCREMENT,
classification_date DATE ,
public_person_id INT(11) ,
stability INT(2) ,
public_usefulness INT(2) ,
visible INT(1)  DEFAULT 1
, CONSTRAINT PRIMARY KEY (id)
);

-- 8.0.
CREATE TABLE T_KRAJ 
(
id INT(11)  AUTO_INCREMENT, 
kraj_name VARCHAR(20) 
, CONSTRAINT PRIMARY KEY (id)
);


-- 8.1.
CREATE TABLE T_OKRES 
(
id INT(11)  AUTO_INCREMENT, 
okres_name VARCHAR(20) , 
kraj_id INT(11) 
, CONSTRAINT PRIMARY KEY (id)
);

-- 8.2.
CREATE TABLE t_location 
(
id INT(11)  AUTO_INCREMENT, 
obec_name VARCHAR(50) , 
mestka_cast VARCHAR(50), 
okres_id INT(11) , 
visible INT(1)  DEFAULT 1
, CONSTRAINT PRIMARY KEY (id)
);


-- 9.
CREATE TABLE t_public_body
(
id INT(11)   AUTO_INCREMENT,
name VARCHAR(50) ,
location_id INT(11) ,
visible INT(1)  DEFAULT 1
, CONSTRAINT PRIMARY KEY (id)
);

-- 10.
CREATE TABLE t_public_role
(
id INT(11)   AUTO_INCREMENT,
public_body_id INT(11) ,
tenure_id INT(11) ,
public_person_id INT(11) ,
name VARCHAR(50) ,
visible INT(1)  DEFAULT 1
, CONSTRAINT PRIMARY KEY (id)
);

-- 11.
CREATE TABLE t_theme
(
id INT(11)   AUTO_INCREMENT,
brief_description VARCHAR(50) ,
description BLOB,
visible INT(1)  DEFAULT 1
, CONSTRAINT PRIMARY KEY (id)
);


-- 12. VOTING SUBJECT
CREATE TABLE t_subject
(
id INT(11)   AUTO_INCREMENT,
brief_description VARCHAR(50) ,
description BLOB,
public_role_id INT(11) ,
theme_id INT(11),
visible INT(1)  DEFAULT 1
, CONSTRAINT PRIMARY KEY (id)
);

-- 13. VOTE
CREATE TABLE t_vote
(
id INT(11)   AUTO_INCREMENT,
vote_date DATE ,
public_body_id INT(11) ,
subject_id INT(11) ,
internal_nr VARCHAR(30),
result_vote VARCHAR(10) , 
for_vote INT(3) ,
against_vote INT(3) ,
refrain_vote INT(3) ,
absent INT(11) ,
link BLOB,
visible INT(11)  DEFAULT 1
, CONSTRAINT PRIMARY KEY (id)
);

-- 14.
CREATE TABLE t_vote_of_role
(
id INT(11)   AUTO_INCREMENT,
public_role_id INT(11) ,
vote_id INT(11) ,
decision VARCHAR(10) ,
visible INT(1)  DEFAULT 1
, CONSTRAINT PRIMARY KEY (id)
);

-- 15.
CREATE TABLE t_act_classification
(
id INT(11)   AUTO_INCREMENT,
vote_of_role_id  INT(11),
subject_id  INT(11),
zhoda_s_programom INT(2) ,
public_malignity INT(2) ,
link BLOB,
visible INT(1)  DEFAULT 1
, CONSTRAINT PRIMARY KEY (id)
);
