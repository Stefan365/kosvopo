-- 1.
CREATE TABLE t_user 
(
id INT(11) NOT NULL  AUTO_INCREMENT, 
first_name VARCHAR(20) NOT NULL, 
last_name VARCHAR(20) NOT NULL,
e_mail VARCHAR(50) NOT NULL,
login VARCHAR(50) NOT NULL,
password VARCHAR(50) NOT NULL,
 
CONSTRAINT usr_PK PRIMARY KEY (id)
); 


-- 2.
CREATE TABLE t_role 
(
id INT(11) NOT NULL  AUTO_INCREMENT, 
role_name VARCHAR(50) NOT NULL, 
rights_description VARCHAR(500) NOT NULL,

CONSTRAINT rol_PK PRIMARY KEY (id)
); 

-- 3.
CREATE TABLE t_user_role 
(
id INT(11) NOT NULL  AUTO_INCREMENT, 
role_id INT(11) NOT NULL,
user_id INT(11) NOT NULL,
since DATE NOT NULL,
till DATE,

CONSTRAINT uro_PK PRIMARY KEY (id),
CONSTRAINT uro_FK1 FOREIGN KEY(user_id) REFERENCES t_user(id),
CONSTRAINT uro_FK2 FOREIGN KEY(role_id) REFERENCES t_role(id)
); 



-- 4.
CREATE TABLE t_change
(
id INT(11) NOT NULL AUTO_INCREMENT,
date_stamp DATE NOT NULL,
user_id INT(11) NOT NULL,
table_name VARCHAR(30) NOT NULL,
row_id INT(11) NOT NULL,
visible_status INT(1) NOT NULL,

CONSTRAINT chg_PK PRIMARY KEY(id),
CONSTRAINT chg_FK FOREIGN KEY(user_id) REFERENCES t_user(id),
CONSTRAINT chg_UN1 UNIQUE(date_stamp, user_id)
);


-- 5.
CREATE TABLE t_tenure
(
id INT(11) NOT NULL  AUTO_INCREMENT,
since DATE NOT NULL,
till DATE,
visible INT(1) NOT NULL DEFAULT 1,

CONSTRAINT ten_PK PRIMARY KEY(id)
);

-- 6.
CREATE TABLE t_public_person
(
id INT(11) NOT NULL  AUTO_INCREMENT,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
date_of_birth  DATE NOT NULL,
visible INT(1) NOT NULL DEFAULT 1,

CONSTRAINT pup_PK PRIMARY KEY(id)
);

-- 7.
CREATE TABLE t_person_classification
(
id INT(11) NOT NULL  AUTO_INCREMENT,
classification_date DATE NOT NULL,
public_person_id INT(11) NOT NULL,
stability INT(2) NOT NULL,
public_usefulness INT(2) NOT NULL,
visible INT(1) NOT NULL DEFAULT 1,

CONSTRAINT pcl_PK PRIMARY KEY(id),
CONSTRAINT pcl_FK FOREIGN KEY(public_person_id) REFERENCES t_public_person(id)
);

-- 8.0.
CREATE TABLE T_KRAJ 
(
id INT(11) NOT NULL AUTO_INCREMENT, 
kraj_name VARCHAR(20) NOT NULL, 

CONSTRAINT kra_PK PRIMARY KEY (id)
);


-- 8.1.
CREATE TABLE T_OKRES 
(
id INT(11) NOT NULL AUTO_INCREMENT, 
okres_name VARCHAR(20) NOT NULL, 
kraj_id INT(11) NOT NULL, 

CONSTRAINT okr_PK PRIMARY KEY (id),
CONSTRAINT okr_FK FOREIGN KEY(kraj_id) REFERENCES T_KRAJ(id)
);

-- 8.2.
CREATE TABLE t_location 
(
id INT(11) NOT NULL AUTO_INCREMENT, 
obec_name VARCHAR(50) NOT NULL, 
mestka_cast VARCHAR(50), 
okres_id INT(11) NOT NULL, 
visible INT(1) NOT NULL DEFAULT 1,

CONSTRAINT loc_PK PRIMARY KEY (id),
CONSTRAINT loc_FK FOREIGN KEY(okres_id) REFERENCES T_OKRES(id),
CONSTRAINT loc_UN UNIQUE(obec_name, mestka_cast)
);


-- 9.
CREATE TABLE t_public_body
(
id INT(11) NOT NULL  AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
location_id INT(11) NOT NULL,
visible INT(1) NOT NULL DEFAULT 1,

CONSTRAINT pub_PK PRIMARY KEY(id),
CONSTRAINT pub_FK FOREIGN KEY(location_id) REFERENCES t_location(id)
);

-- 10.
CREATE TABLE t_public_role
(
id INT(11) NOT NULL  AUTO_INCREMENT,
public_body_id INT(11) NOT NULL,
tenure_id INT(11) NOT NULL,
public_person_id INT(11) NOT NULL,
name VARCHAR(50) NOT NULL,
visible INT(1) NOT NULL DEFAULT 1,

CONSTRAINT pur_PK PRIMARY KEY(id),
CONSTRAINT pur_FK1 FOREIGN KEY(public_body_id) REFERENCES t_public_body(id),
CONSTRAINT pur_FK2 FOREIGN KEY(tenure_id) REFERENCES t_tenure(id),
CONSTRAINT pur_FK3 FOREIGN KEY(public_person_id) REFERENCES t_public_person(id)
);

-- 11.
CREATE TABLE t_theme
(
id INT(11) NOT NULL  AUTO_INCREMENT,
brief_description VARCHAR(50) NOT NULL,
description BLOB,
visible INT(1) NOT NULL DEFAULT 1,

CONSTRAINT the_PK PRIMARY KEY(id)
);


-- 12. VOTING SUBJECT
CREATE TABLE t_subject
(
id INT(11) NOT NULL  AUTO_INCREMENT,
brief_description VARCHAR(50) NOT NULL,
description BLOB,
public_role_id INT(11) NOT NULL,
theme_id INT(11),
visible INT(1) NOT NULL DEFAULT 1,

CONSTRAINT sub_PK PRIMARY KEY(id),
CONSTRAINT sub_FK1 FOREIGN KEY(public_role_id) REFERENCES t_public_role(id),
CONSTRAINT sub_FK2 FOREIGN KEY(theme_id) REFERENCES t_theme(id)
);

-- 13. VOTE
CREATE TABLE t_vote
(
id INT(11) NOT NULL  AUTO_INCREMENT,
vote_date DATE NOT NULL,
public_body_id INT(11) NOT NULL,
subject_id INT(11) NOT NULL,
internal_nr VARCHAR(30),
result_vote VARCHAR(10) NOT NULL, 
for_vote INT(3) NOT NULL,
against_vote INT(3) NOT NULL,
refrain_vote INT(3) NOT NULL,
absent INT(11) NOT NULL,
link BLOB,
visible INT(11) NOT NULL DEFAULT 1,

CONSTRAINT vot_PK PRIMARY KEY(id),
CONSTRAINT vot_FK1 FOREIGN KEY(public_body_id) REFERENCES t_public_body(id),
CONSTRAINT vot_FK2 FOREIGN KEY(subject_id) REFERENCES t_subject(id)
);

-- 14.
CREATE TABLE t_vote_of_role
(
id INT(11) NOT NULL  AUTO_INCREMENT,
public_role_id INT(11) NOT NULL,
vote_id INT(11) NOT NULL,
decision VARCHAR(10) NOT NULL,
visible INT(1) NOT NULL DEFAULT 1,

CONSTRAINT vor_PK PRIMARY KEY(id),
CONSTRAINT vor_FK1 FOREIGN KEY(public_role_id) REFERENCES t_public_role(id),
CONSTRAINT vor_FK2 FOREIGN KEY(vote_id) REFERENCES t_vote(id),
CONSTRAINT vor_UN UNIQUE(public_role_id, vote_id)
);

-- 15.
CREATE TABLE t_act_classification
(
id INT(11) NOT NULL  AUTO_INCREMENT,
vote_of_role_id  INT(11),
subject_id  INT(11),
zhoda_s_programom INT(2) NOT NULL,
public_malignity INT(2) NOT NULL,
link BLOB,
visible INT(1) NOT NULL DEFAULT 1,

CONSTRAINT acl_PK PRIMARY KEY(id),
CONSTRAINT acl_FK1 FOREIGN KEY(vote_of_role_id) REFERENCES t_vote_of_role(id),
CONSTRAINT acl_FK2 FOREIGN KEY(subject_id) REFERENCES t_subject(id),
CONSTRAINT acl_CHK CHECK ((vote_of_role_id IS NOT NULL AND subject_id IS NULL) OR 
                          (vote_of_role_id IS NULL AND subject_id IS NOT NULL)),
CONSTRAINT acl_UN UNIQUE(vote_of_role_id, subject_id)
);
