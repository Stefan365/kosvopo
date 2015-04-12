-- 1.
CREATE TABLE a_user 
(
id INT(11) NOT NULL AUTO_INCREMENT, 
first_name VARCHAR(20) NOT NULL, 
last_name VARCHAR(20) NOT NULL,
e_mail VARCHAR(50) NOT NULL,
login VARCHAR(50) NOT NULL,
password BLOB NOT NULL,
active BIT NOT NULL DEFAULT 1,

CONSTRAINT usr_PK PRIMARY KEY (id)
); 


-- 2.
CREATE TABLE a_role 
(
id INT(11) NOT NULL AUTO_INCREMENT, 
role SMALLINT NOT NULL DEFAULT 0, 
role_name VARCHAR(30) NOT NULL,
rights_description VARCHAR(500) NOT NULL,
active BIT NOT NULL DEFAULT 1,

CONSTRAINT rol_PK PRIMARY KEY (id)
); 

-- 3.
CREATE TABLE a_user_role 
(
id INT(11) NOT NULL AUTO_INCREMENT, 
role_id INT(11) NOT NULL,
user_id INT(11) NOT NULL,
since DATE NOT NULL,
till DATE,
active BIT NOT NULL DEFAULT 1,

CONSTRAINT uro_PK PRIMARY KEY (id),
CONSTRAINT uro_FK1 FOREIGN KEY(user_id) REFERENCES a_user(id),
CONSTRAINT uro_FK2 FOREIGN KEY(role_id) REFERENCES a_role(id)
); 



-- 4.
CREATE TABLE a_change
(
id INT(11) NOT NULL AUTO_INCREMENT,
date_stamp DATE NOT NULL,
user_id INT(11) NOT NULL,
public_body_id INT(11),
table_name VARCHAR(30) NOT NULL,
column_name VARCHAR(30) NOT NULL,
row_id INT(11) NOT NULL,
old_value BLOB,
new_value BLOB,
active BIT NOT NULL DEFAULT 1,

CONSTRAINT chg_PK PRIMARY KEY(id),
CONSTRAINT chg_FK FOREIGN KEY(user_id) REFERENCES a_user(id)
);


-- 5.
CREATE TABLE t_tenure
(
id INT(11) NOT NULL AUTO_INCREMENT,
since DATE NOT NULL,
till DATE,
visible BIT NOT NULL DEFAULT 1,

CONSTRAINT ten_PK PRIMARY KEY(id)
);

-- 6.
CREATE TABLE t_public_person
(
id INT(11) NOT NULL AUTO_INCREMENT,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
date_of_birth DATE,
visible BIT NOT NULL DEFAULT 1,

CONSTRAINT pup_PK PRIMARY KEY(id)
);

-- 6.B
 CREATE TABLE t_public_person2
 (
 id INT(11) NOT NULL AUTO_INCREMENT,
 first_name VARCHAR(50) NOT NULL,
 last_name VARCHAR(50) NOT NULL,
 date_of_birth DATE,
 visible BIT NOT NULL DEFAULT 1,

 CONSTRAINT pup2_PK PRIMARY KEY(id)
 );


-- 7.
CREATE TABLE t_person_classification
(
id INT(11) NOT NULL AUTO_INCREMENT,
classification_date DATE NOT NULL,
public_person_id INT(11) NOT NULL,
stability SMALLINT NOT NULL DEFAULT 2,
public_usefulness SMALLINT NOT NULL DEFAULT 2,
visible BIT NOT NULL DEFAULT 1,

CONSTRAINT pcl_PK PRIMARY KEY(id),
CONSTRAINT pcl_FK FOREIGN KEY(public_person_id) REFERENCES t_public_person(id)
);

-- 8.0.
CREATE TABLE t_region
(
id INT(11) NOT NULL AUTO_INCREMENT, 
region_name VARCHAR(20) NOT NULL, 
visible BIT NOT NULL DEFAULT 1,


CONSTRAINT reg_PK PRIMARY KEY (id)
);


-- 8.1.
 CREATE TABLE t_district
 (
 id INT(11) NOT NULL AUTO_INCREMENT, 
 district_name VARCHAR(20) NOT NULL, 
 region_id INT(11), 
 visible BIT NOT NULL DEFAULT 1,

CONSTRAINT dis_PK PRIMARY KEY (id),
CONSTRAINT dis_FK FOREIGN KEY(region_id) REFERENCES t_region(id)
);

-- 8.2.
CREATE TABLE t_location 
(
id INT(11) NOT NULL AUTO_INCREMENT, 
location_name VARCHAR(50) NOT NULL, 
town_section VARCHAR(50), 
district_id INT(11) NOT NULL, 
visible BIT NOT NULL DEFAULT 1,

CONSTRAINT loc_PK PRIMARY KEY (id),
CONSTRAINT loc_FK FOREIGN KEY(district_id) REFERENCES t_district(id),
CONSTRAINT loc_UN UNIQUE(location_name, town_section)
);


-- 9.
CREATE TABLE t_public_body
(
id INT(11) NOT NULL AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
location_id INT(11) NOT NULL,
visible BIT NOT NULL DEFAULT 1,

CONSTRAINT pub_PK PRIMARY KEY(id),
CONSTRAINT pub_FK FOREIGN KEY(location_id) REFERENCES t_location(id)
);

-- 10.
CREATE TABLE t_public_role
(
id INT(11) NOT NULL AUTO_INCREMENT,
public_body_id INT(11) NOT NULL,
tenure_id INT(11) NOT NULL,
public_person_id INT(11) NOT NULL,
name SMALLINT NOT NULL DEFAULT 0,
visible BIT NOT NULL DEFAULT 1,

CONSTRAINT pur_PK PRIMARY KEY(id),
CONSTRAINT pur_FK1 FOREIGN KEY(public_body_id) REFERENCES t_public_body(id),
CONSTRAINT pur_FK2 FOREIGN KEY(tenure_id) REFERENCES t_tenure(id),
CONSTRAINT pur_FK3 FOREIGN KEY(public_person_id) REFERENCES t_public_person(id)
);

-- 11.
CREATE TABLE t_theme
(
id INT(11) NOT NULL AUTO_INCREMENT,
brief_description VARCHAR(50) NOT NULL,
description TEXT,
visible BIT NOT NULL DEFAULT 1,

CONSTRAINT the_PK PRIMARY KEY(id)
);


-- 12. VOTING SUBJECT
CREATE TABLE t_subject
(
id INT(11) NOT NULL  AUTO_INCREMENT,
brief_description VARCHAR(50) NOT NULL,
description TEXT,
public_role_id INT(11) NOT NULL,
theme_id INT(11),
visible BIT NOT NULL DEFAULT 1,

CONSTRAINT sub_PK PRIMARY KEY(id),
CONSTRAINT sub_FK1 FOREIGN KEY(public_role_id) REFERENCES t_public_role(id),
CONSTRAINT sub_FK2 FOREIGN KEY(theme_id) REFERENCES t_theme(id)
);

-- 13. VOTE
CREATE TABLE t_vote
(
id INT(11) NOT NULL  AUTO_INCREMENT,
vote_date DATE NOT NULL,
subject_id INT(11) NOT NULL,
internal_nr VARCHAR(30),
result_vote SMALLINT NOT NULL DEFAULT 0, 
for_vote INT(3) NOT NULL,
against_vote INT(3) NOT NULL,
refrain_vote INT(3) NOT NULL,
absent INT(11) NOT NULL,
visible BIT NOT NULL DEFAULT 1,

CONSTRAINT vot_PK PRIMARY KEY(id),
CONSTRAINT vot_FK2 FOREIGN KEY(subject_id) REFERENCES t_subject(id)
);

-- 14.
CREATE TABLE t_vote_of_role
(
id INT(11) NOT NULL AUTO_INCREMENT,
public_role_id INT(11) NOT NULL,
vote_id INT(11) NOT NULL,
decision SMALLINT NOT NULL DEFAULT 0,
visible BIT NOT NULL DEFAULT 1,

CONSTRAINT vor_PK PRIMARY KEY(id),
CONSTRAINT vor_FK1 FOREIGN KEY(public_role_id) REFERENCES t_public_role(id),
CONSTRAINT vor_FK2 FOREIGN KEY(vote_id) REFERENCES t_vote(id),
CONSTRAINT vor_UN UNIQUE(public_role_id, vote_id)
);

-- 15.
CREATE TABLE t_vote_classification
(
id INT(11) NOT NULL  AUTO_INCREMENT,
vote_id  INT(11) NOT NULL,
public_usefulness SMALLINT NOT NULL DEFAULT 2,
brief_description TEXT,
visible BIT NOT NULL DEFAULT 1,

CONSTRAINT vcl_PK PRIMARY KEY(id),
CONSTRAINT vcl_FK FOREIGN KEY(vote_id) REFERENCES t_vote(id),
CONSTRAINT vcl_UN UNIQUE(vote_id)
);

-- 16.
CREATE TABLE t_document
(
id INT(11) NOT NULL  AUTO_INCREMENT,
table_name VARCHAR(30) NOT NULL,
table_row_id INT(11) NOT NULL,
file_name VARCHAR(150) NOT NULL,
upload_date DATE,
document MEDIUMBLOB NOT NULL,
visible BIT NOT NULL DEFAULT 1,

CONSTRAINT doc_PK PRIMARY KEY(id)
);

-- 17.
CREATE TABLE a_hierarchy
(
id INT(11) NOT NULL AUTO_INCREMENT,
table_name VARCHAR(30) NOT NULL,
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
