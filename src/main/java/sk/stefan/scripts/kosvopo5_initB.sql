

-- 1.
INSERT INTO a_user (first_name, last_name, e_mail, login, password)
VALUES ('Peter', 'Sarkoci', 'peter.sarkoci@gmail.com', 'petak', 'petak');
INSERT INTO a_user (first_name, last_name, e_mail, login, password)
VALUES ('Štefan', 'Vereš', 'stefan.veres@gmail.com', 'stefan63', 'stefan63');
INSERT INTO a_user (first_name, last_name, e_mail, login, password)
VALUES ('Miro', 'Sčibrányi', 'miro.scibranyi@gmail.com', 'mito108', 'miro108');
COMMIT; 


-- 2.
INSERT INTO a_role (role_name, rights_description)
VALUES ('ADMIN', 'všetky práva, vrátane práv na priamy zásah do databázy');
INSERT INTO a_role (role_name, rights_description)
VALUES ('HELP_ADMIN', 'všetky práva (vkladanie nových entít, ...), okrem priameho vstupu do databázy');
INSERT INTO a_role (role_name, rights_description)
VALUES ('JUDGE', 'má právo dávať posudky a vkladať objasňujúce komentáre, + vkladanie domumentov');
INSERT INTO a_role (role_name, rights_description)
VALUES ('HELPER', 'práva aktivistu, tj. môže vkladať dokumenty');
COMMIT; 

-- 3.
INSERT INTO a_user_role (role_id, user_id, since, till)
VALUES (1, 1, NOW(), null);
INSERT INTO a_user_role (role_id, user_id, since, till)
VALUES (1, 2, NOW(), null);
INSERT INTO a_user_role (role_id, user_id, since, till)
VALUES (3, 3, NOW(), null);
COMMIT; 

-- 5.
INSERT INTO t_tenure(since, till)
VALUES ('2011-07-04', '2012-09-26');
INSERT INTO t_tenure(since, till)
VALUES ('2010-07-04', '2011-09-26');
INSERT INTO t_tenure(since, till)
VALUES ('2009-07-04', '2010-09-26');
INSERT INTO t_tenure(since, till)
VALUES ('2008-07-04', '2009-09-25');
INSERT INTO t_tenure(since, till)
VALUES ('2007-07-04', '2008-09-24');
INSERT INTO t_tenure(since, till)
VALUES ('2006-07-04', '2007-09-23');
COMMIT; 


-- 6.
INSERT INTO t_public_person(first_name, last_name, date_of_birth)
VALUES ('Johan', 'Procházka', '1956-09-11');
INSERT INTO t_public_person(first_name, last_name, date_of_birth)
VALUES ('Karol', 'Čútora', '1966-05-13');
INSERT INTO t_public_person(first_name, last_name, date_of_birth)
VALUES ('Luboš', 'Jozefíny', '1954-12-21');
INSERT INTO t_public_person(first_name, last_name, date_of_birth)
VALUES ('Alex', 'Spevák', '1979-09-01');
INSERT INTO t_public_person(first_name, last_name, date_of_birth)
VALUES ('Emil', 'Čútora', '1967-04-01');
COMMIT; 

-- 6.B
INSERT INTO t_public_person2(first_name, last_name, date_of_birth)
VALUES ('Johan', 'Procházka', '1956-09-11');
INSERT INTO t_public_person2(first_name, last_name, date_of_birth)
VALUES ('Karol', 'Čútora', '1966-05-13');
INSERT INTO t_public_person2(first_name, last_name, date_of_birth)
VALUES ('Luboš', 'Jozefíny', '1954-12-21');
INSERT INTO t_public_person2(first_name, last_name, date_of_birth)
VALUES ('Alex', 'Spevák', '1979-09-01');
INSERT INTO t_public_person2(first_name, last_name, date_of_birth)
VALUES ('Emil', 'Čútora', '1967-04-01');
COMMIT; 


-- 7.
INSERT INTO t_person_classification(classification_date, public_person_id, stability,  public_usefulness)
VALUES (NOW(), 4, 8, 2);
INSERT INTO t_person_classification(classification_date, public_person_id, stability,  public_usefulness)
VALUES (NOW(), 5, 3, 5);
INSERT INTO t_person_classification(classification_date, public_person_id, stability,  public_usefulness)
VALUES (NOW(), 3, 9, 7);
INSERT INTO t_person_classification(classification_date, public_person_id, stability,  public_usefulness)
VALUES (NOW(), 2, 10, 7);
INSERT INTO t_person_classification(classification_date, public_person_id, stability, public_usefulness)
VALUES (NOW(), 1, 4, 2);
COMMIT; 

-- 8.0
INSERT INTO T_KRAJ(kraj_name) VALUES ('Západoslovenský');
INSERT INTO T_KRAJ(kraj_name) VALUES ('Stredoslovenský');
INSERT INTO T_KRAJ(kraj_name) VALUES ('Východoslovenský');
COMMIT;

-- 8.1. 
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Bratislava', 1);
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Trnava', 1);
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Nitra', 1);
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Trenčín', 1);
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Prievidza', 2);
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Banská Bystrica', 2);
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Zvolen', 2);
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Žarnovica', 2);
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Lučenec', 2);
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Kosice', 3);
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Michalovce', 3);
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Zvolen', 3);
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Roznava', 3);
INSERT INTO T_OKRES(okres_name, kraj_id) VALUES ('Presov', 3);
COMMIT;

-- 8.2.
INSERT INTO t_location(obec_name, mestka_cast, okres_id) VALUES ('Bratislava', 'Rača', 1);
INSERT INTO t_location(obec_name, mestka_cast, okres_id) VALUES ('Bratislava', 'Dúbravka', 1);
INSERT INTO T_location(obec_name, mestka_cast, okres_id) VALUES ('Handlová', null, 5);
INSERT INTO t_location(obec_name, mestka_cast, okres_id) VALUES ('Nováky', null, 5);
INSERT INTO t_location(obec_name, mestka_cast, okres_id) VALUES ('Patrizánske', null, 5);
INSERT INTO t_location(obec_name, mestka_cast, okres_id) VALUES ('Ráztočno', null, 5);
INSERT INTO t_location(obec_name, mestka_cast, okres_id) VALUES ('Topoľčany', null, 3);
INSERT INTO t_location(obec_name, mestka_cast, okres_id) VALUES ('Košice', 'Záhumienok', 10);
INSERT INTO t_location(obec_name, mestka_cast, okres_id) VALUES ('Sereď', null, 1);
COMMIT;



-- 9.
INSERT INTO t_public_body(name, location_id) VALUES ('Mestké zastupiteľstvo Bratislava Rača', 1);
INSERT INTO t_public_body(name, location_id) VALUES ('Mestké zastupiteľstvo Handlová', 3);
INSERT INTO t_public_body(name, location_id) VALUES ('Mestké zastupiteľstvo Nováky', 4);
INSERT INTO t_public_body(name, location_id) VALUES ('Mestké zastupiteľstvo Ráztočno', 6);
INSERT INTO t_public_body(name, location_id) VALUES ('Mestké zastupiteľstvo Košice záhumienok', 8);
INSERT INTO t_public_body(name, location_id) VALUES ('Mestké zastupiteľstvo Sereď', 9);
COMMIT;


-- 10.
INSERT INTO t_public_role(public_body_id, tenure_id, public_person_id, name) VALUES (5, 1, 1, 'poslanec');
INSERT INTO t_public_role(public_body_id, tenure_id, public_person_id, name) VALUES (5, 1, 2, 'poslanec');
INSERT INTO t_public_role(public_body_id, tenure_id, public_person_id, name) VALUES (5, 1, 3, 'predseda');
INSERT INTO t_public_role(public_body_id, tenure_id, public_person_id, name) VALUES (5, 1, 4, 'poslanec');
INSERT INTO t_public_role(public_body_id, tenure_id, public_person_id, name) VALUES (5, 1, 5, 'poslanec');
COMMIT;


-- 11.
INSERT INTO t_theme(brief_description, description) VALUES ('Kácanie záhumienkového lesa', null);
INSERT INTO t_theme(brief_description, description) VALUES ('Zrušenie obecej školy', null);
INSERT INTO t_theme(brief_description, description) VALUES ('Vymáhanie pohľadávok', null);
INSERT INTO t_theme(brief_description, description) VALUES ('Stavba divokej skládky', null);
INSERT INTO t_theme(brief_description, description) VALUES ('Predaj pozemkov uránovým baniam', null);
INSERT INTO t_theme(brief_description, description) VALUES ('Dokončenie stavby kultúrneho domu', null);
COMMIT;


-- 12. VOTING SUBJECT vsetko navrhuje sudruh Prochazka
INSERT INTO t_subject(brief_description, description, public_role_id, theme_id) 
VALUES ('Výberové konanie lesníckej firmy', null, 1, 1);
INSERT INTO t_subject(brief_description, description, public_role_id, theme_id) 
VALUES ('Výberové konanie na firmu', null, 1, 4);
INSERT INTO t_subject(brief_description, description, public_role_id, theme_id) 
VALUES ('Výberové konanie stavebnej firmy', null, 1, 6);
INSERT INTO t_subject(brief_description, description, public_role_id, theme_id) 
VALUES ('Hlasovanie o odpredaji pozemku XYZ Uranovym baniam', null, 1, 5);
COMMIT;


-- 13. VOTE
INSERT INTO t_vote(vote_date, public_body_id, subject_id, internal_nr, result_vote, 
            for_vote, against_vote, refrain_vote, absent, link)			
VALUES (NOW(), 5, 1, '957-AC',1, 4, 0, 1, 0, null);
INSERT INTO t_vote(vote_date, public_body_id, subject_id, internal_nr, result_vote, 
            for_vote, against_vote, refrain_vote, absent, link)			
VALUES (NOW(), 5, 2, '958-AC',1, 3, 0, 2, 0, null);
INSERT INTO t_vote(vote_date, public_body_id, subject_id, internal_nr, result_vote, 
			for_vote, against_vote, refrain_vote, absent, link)			
VALUES (NOW(), 5, 2, '959-AC',1, 1, 4, 0, 0, null);
INSERT INTO t_vote(vote_date, public_body_id, subject_id, internal_nr, result_vote, 
            for_vote, against_vote, refrain_vote, absent, link)			
VALUES (NOW(), 5, 4, '963-AC',1, 3, 2, 0, 0, null);
COMMIT;


-- 14.
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (1, 1, 'ZA');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (2, 1, 'ZA');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (3, 1, 'ZA');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (4, 1, 'ZA');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (5, 1, 'ZDRZAL SA');

INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (1, 2, 'ZA');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (2, 2, 'ZA');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (3, 2, 'ZA');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (4, 2, 'ZDRZAL SA');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (5, 2, 'ZDRZAL SA');

INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (1, 3, 'ZA');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (2, 3, 'PROTI');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (3, 3, 'PROTI');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (4, 3, 'PROTI');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (5, 3, 'PROTI');

INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (1, 4, 'ZA');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (2, 4, 'ZA');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (3, 4, 'ZA');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (4, 4, 'PROTI');
INSERT INTO t_vote_of_role(public_role_id, vote_id, decision) VALUES (5, 4, 'PROTI');
COMMIT;


-- 15.
INSERT INTO t_vote_classification(vote_id, public_malignity, brief_description)
VALUES (1, 9, 'uplne na picu hlasovanie, akodlive jak das');

INSERT INTO t_vote_classification(vote_id, public_malignity, brief_description)
VALUES (2, 0, 'hlasovanie naozaj v zaujme obcanov');

INSERT INTO t_vote_classification(vote_id, public_malignity, brief_description)
VALUES (3, 5, 'nieco medzi, asi v tom bude tunel, nicmene stale je to prospesne verejnosti');

INSERT INTO t_vote_classification(vote_id, public_malignity, brief_description)
VALUES (4, 3, 'nieco medzi, asi v tom bude tunel, nicmene stale je to prospesne verejnosti');

COMMIT;