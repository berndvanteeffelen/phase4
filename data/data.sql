INSERT INTO ADRESSE
VALUES ('Deutschland','Duesseldorf','40210','Universitaetsstrasse','1','1');
INSERT INTO ADRESSE
VALUES ('Deutschland','Duesseldorf','40210','Koenigsallee','15','2');
INSERT INTO ADRESSE
VALUES ('Deutschland','Hamburg','20090','Heinrichstrasse','25','3');
INSERT INTO ADRESSE
VALUES ('England','London','14560','Bridge Street','1','4');
INSERT INTO ADRESSE
VALUES ('USA','Fremont','45500','Freemont Boulevard','45','5');
INSERT INTO ADRESSE
VALUES ('USA','Palo Alto','20095','Deer Creek Road','13','6');
INSERT INTO ADRESSE
VALUES ('USA','Washington DC','20500','Pennsylvania Avenue','1','7');

INSERT INTO NUTZER
VALUES ('Mustermann','maxmustermann@gmail.de','1c4AB','1');
INSERT INTO NUTZER
VALUES ('Lukas','lukasmeier@gmx.de','3C6bA','2');
INSERT INTO NUTZER
VALUES ('Theresa','theresaheise@webmail.de','1A3B5','3');
INSERT INTO NUTZER
VALUES ('Boris','borisjohnson@brexit.uk','Br3xIt','4');
INSERT INTO NUTZER
VALUES ('Elon','elonmusk@outlook.com','TesLa7','5');
INSERT INTO NUTZER
VALUES ('XAEAXII','XAEAXII@outlook.com','BitC4oin','6');
INSERT INTO NUTZER
VALUES ('StableGenius','thedonald@foxnews.com','C0vFefe','7');

INSERT INTO PREMIUMNUTZER
VALUES ('2022-01-01','maxmustermann@gmail.de');
INSERT INTO PREMIUMNUTZER
VALUES ('2024-05-01','lukasmeier@gmx.de');
INSERT INTO PREMIUMNUTZER
VALUES ('2023-06-28','theresaheise@webmail.de');
INSERT INTO PREMIUMNUTZER
VALUES ('2022-05-04','borisjohnson@brexit.uk');
INSERT INTO PREMIUMNUTZER
VALUES ('2050-12-02','XAEAXII@outlook.com');
INSERT INTO PREMIUMNUTZER
VALUES ('2021-12-31','thedonald@foxnews.com');

INSERT INTO KUENSTLER
VALUES ('Muster','maxmustermann@gmail.de');
INSERT INTO KUENSTLER
VALUES ('Meier','lukasmeier@gmx.de');
INSERT INTO KUENSTLER
VALUES ('Heiser','theresaheise@webmail.de');
INSERT INTO KUENSTLER
VALUES ('BoJo','borisjohnson@brexit.uk');
INSERT INTO KUENSTLER
VALUES ('XAEAXII','XAEAXII@outlook.com');
INSERT INTO KUENSTLER
VALUES ('The Donald','thedonald@foxnews.com');

INSERT INTO BAND
VALUES ('BoJo and the Donald','Nach dem Brexit verband die Beiden eine tiefgehende Freundschaft und sie gruendeten diese Band.','1');
INSERT INTO BAND
VALUES ('Mustermeier','Zwei Schulfreunde aus Duesseldorf, verbunden durch ihre Liebe zur Musik.','2');

INSERT INTO PLAYLIST
VALUES ('Politics','TRUE','bilder/Politics.png','1','XAEAXII@outlook.com');
INSERT INTO PLAYLIST
VALUES ('Mix','FALSE','bilder/duck.png','2','thedonald@foxnews.com');

INSERT INTO GENRE
VALUES ('Rap');
INSERT INTO GENRE
VALUES ('Rock');
INSERT INTO GENRE
VALUES ('Pop');

INSERT INTO TITEL
VALUES ('Looking for Brexit','00:03:43','1','musik/brexit1lq.mp3','musik/brexit1hq.wav','Rap');
INSERT INTO TITEL
VALUES ('Brexit one more time','00:04:20','2','musik/brexit2lq.mp3','musik/brexit2hq.wav','Rock');
INSERT INTO TITEL
VALUES ('Its the final Brexit','00:05:10','3','musik/brexit3lq.mp3','musik/brexit3hq.wav','Pop');
INSERT INTO TITEL
VALUES ('Schoenste Stadt am Rhein','00:02:34','4','musik/duesseldorf1lq.mp3','musik/duesseldorf1hq.wav','Pop');
INSERT INTO TITEL
VALUES ('Duesseldorf meine Perle','00:04:17','5','musik/duesseldorf2lq.mp3','musik/duesseldorf2hq.wav','Pop');
INSERT INTO TITEL
VALUES ('Its TTIP Bitch!','00:02:26','6','musik/freetrade1lq.mp3','musik/freetrade1hq.wav','Pop');
INSERT INTO TITEL
VALUES ('American Idiot','00:02:56','7','musik/freetrade2lq.mp3','musik/freetrade2hq.wav','Pop');
INSERT INTO TITEL
VALUES ('All by myself','00:05:12','8','musik/brexit4lq.mp3','musik/brexit4hq.wav','Pop');

INSERT INTO ALBUM
VALUES ('Brexit','2020-02-01','1');
INSERT INTO ALBUM
VALUES ('Duesseldorf','2021-05-06','2');
INSERT INTO ALBUM
VALUES ('Free Trade','2021-01-01','3');

INSERT INTO VEROEFFENTLICHT
VALUES ('borisjohnson@brexit.uk','1');
INSERT INTO VEROEFFENTLICHT
VALUES ('borisjohnson@brexit.uk','2');
INSERT INTO VEROEFFENTLICHT
VALUES ('borisjohnson@brexit.uk','3');
INSERT INTO VEROEFFENTLICHT
VALUES ('borisjohnson@brexit.uk','6');
INSERT INTO VEROEFFENTLICHT
VALUES ('borisjohnson@brexit.uk','7');
INSERT INTO VEROEFFENTLICHT
VALUES ('borisjohnson@brexit.uk','8');
INSERT INTO VEROEFFENTLICHT
VALUES ('maxmustermann@gmail.de','4');
INSERT INTO VEROEFFENTLICHT
VALUES ('maxmustermann@gmail.de','5');
INSERT INTO VEROEFFENTLICHT
VALUES ('lukasmeier@gmx.de','4');
INSERT INTO VEROEFFENTLICHT
VALUES ('lukasmeier@gmx.de','5');
INSERT INTO VEROEFFENTLICHT
VALUES ('thedonald@foxnews.com','2');
INSERT INTO VEROEFFENTLICHT
VALUES ('thedonald@foxnews.com','6');
INSERT INTO VEROEFFENTLICHT
VALUES ('thedonald@foxnews.com','7');

INSERT INTO UMFASST
VALUES ('1','1');
INSERT INTO UMFASST
VALUES ('1','2');
INSERT INTO UMFASST
VALUES ('1','3');
INSERT INTO UMFASST
VALUES ('1','8');
INSERT INTO UMFASST
VALUES ('2','4');
INSERT INTO UMFASST
VALUES ('2','5');
INSERT INTO UMFASST
VALUES ('3','6');
INSERT INTO UMFASST
VALUES ('3','7');


INSERT INTO EMPFIEHLT
VALUES ('Pop','Rock');
INSERT INTO EMPFIEHLT
VALUES ('Rock','Rap');

INSERT INTO PRODUZIERT
VALUES ('3','1');
INSERT INTO PRODUZIERT
VALUES ('2','2');

INSERT INTO ERSTELLT
VALUES ('borisjohnson@brexit.uk','1');

INSERT INTO GEHOERT_ZU
VALUES ('1','borisjohnson@brexit.uk');
INSERT INTO GEHOERT_ZU
VALUES ('1','thedonald@foxnews.com');
INSERT INTO GEHOERT_ZU
VALUES ('2','maxmustermann@gmail.de');
INSERT INTO GEHOERT_ZU
VALUES ('2','lukasmeier@gmx.de');

INSERT INTO SAMMELT
VALUES ('1','1');
INSERT INTO SAMMELT
VALUES ('1','2');
INSERT INTO SAMMELT
VALUES ('1','3');
INSERT INTO SAMMELT
VALUES ('1','6');
INSERT INTO SAMMELT
VALUES ('1','7');
INSERT INTO SAMMELT
VALUES ('1','8');
INSERT INTO SAMMELT
VALUES ('2','1');
INSERT INTO SAMMELT
VALUES ('2','5');

INSERT INTO KOMMENTIERT
VALUES ('theresaheise@webmail.de','5','Das ist ganz dreist geklaut!');
INSERT INTO KOMMENTIERT
VALUES ('thedonald@foxnews.com','7','Best Song in the history of Music, maybe ever.');
INSERT INTO KOMMENTIERT
VALUES ('thedonald@foxnews.com','8','Covfefe');
INSERT INTO KOMMENTIERT
VALUES ('theresaheise@webmail.de','7','Ein sehr passender Titel.');
INSERT INTO KOMMENTIERT
VALUES ('thedonald@foxnews.com','5','I dont like those Dutch and their cancer-inducing windmills and their cheese, and what is it with the color orange?');

INSERT INTO BEWERTET
VALUES ('1','theresaheise@webmail.de','2');
INSERT INTO BEWERTET
VALUES ('1','thedonald@foxnews.com','2');
INSERT INTO BEWERTET
VALUES ('10','thedonald@foxnews.com','1');
INSERT INTO BEWERTET
VALUES ('8','theresaheise@webmail.de','1');
