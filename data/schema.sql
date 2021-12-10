/*PRAGMA auto_vacuum = 1;
PRAGMA encoding = "UTF-8";
PRAGMA foreign_keys = 1;
PRAGMA journal_mode = WAL;
PRAGMA synchronous = NORMAL;
*/
CREATE TABLE ADRESSE (
    Land       VARCHAR NOT NULL CHECK(length(Land)>1 AND length(Land)<50 AND Land NOT GLOB '*[^ -~]*'),
    Stadt      VARCHAR NOT NULL CHECK(length(Stadt)>1 AND length(Stadt)<50 AND Stadt NOT GLOB '*[^ -~]*'),
    PLZ        INTEGER NOT NULL CHECK(PLZ>1000 AND PLZ<100000),
    Strasse    VARCHAR NOT NULL CHECK(length(Strasse)>1 AND length(Strasse)<50 AND Strasse NOT GLOB '*[^ -~]*'),
    Hausnummer INTEGER NOT NULL CHECK(Hausnummer>0 AND Hausnummer<2000),
    AdID       INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT
);
CREATE TABLE NUTZER (
    Benutzername VARCHAR NOT NULL COLLATE NOCASE UNIQUE CHECK(length(Benutzername)>1 AND length(Benutzername)<50 AND Benutzername NOT GLOB '*[^ -~]*'),
    Mail         VARCHAR NOT NULL COLLATE NOCASE UNIQUE  PRIMARY KEY CHECK(length(Mail)>1 AND length(Mail)<50 AND Mail LIKE '%@%.%' AND Mail NOT GLOB '*[^a-zA-Z0-9]*@*.*' AND Mail NOT GLOB '*@*[^a-zA-Z0-9]*.*' AND Mail NOT GLOB '*@*.*[^a-zA-Z]*'),
    Passwort     VARCHAR NOT NULL CHECK(length(Passwort)>2 AND length(Passwort)<9 AND Passwort NOT GLOB '*[^ -~]*' AND Passwort GLOB '*[A-Z]*[A-Z]*' AND Passwort GLOB '*[0-9]*'),
    AdID         INTEGER REFERENCES ADRESSE (AdID)
);
CREATE TABLE PREMIUMNUTZER (
    Vertragsende DATE NOT NULL CHECK(Vertragsende==strftime('%YYYY-%MM-%DD',Vertragsende)),
    Mail         VARCHAR REFERENCES NUTZER (Mail) ON DELETE CASCADE ON UPDATE CASCADE
                        PRIMARY KEY
);
CREATE TABLE KUENSTLER (
    Kuenstlername VARCHAR NOT NULL CHECK(length(Kuenstlername)>1 AND length(Kuenstlername)<50 AND Kuenstlername NOT GLOB '*[^ -~]*'),
    Mail          VARCHAR REFERENCES PREMIUMNUTZER (Mail) ON DELETE CASCADE ON UPDATE CASCADE
                         PRIMARY KEY
);
CREATE TABLE BAND (
    Bandname   VARCHAR NOT NULL CHECK(length(Bandname)>1 AND length(Bandname)<50 AND Bandname NOT GLOB '*[^ -~]*'),
    Geschichte TEXT CHECK(length(Geschichte)>1 AND length(Geschichte)<500 AND Geschichte NOT GLOB '*[^ -~]*'),
    BID        INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT
);
CREATE TABLE PLAYLIST (
    Playlistname VARCHAR NOT NULL CHECK(length(Playlistname)>1 AND length(Playlistname)<50 AND Playlistname NOT GLOB '*[^ -~]*'),
    Oeffentlich  BOOLEAN NOT NULL,
    Coverbild    VARCHAR COLLATE NOCASE CHECK(length(Coverbild)>1 AND length(Coverbild)<50 AND Coverbild NOT GLOB '*[^ -~]*' AND Coverbild GLOB '*.png'),
    PID          INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT,
    Mail         VARCHAR  REFERENCES PREMIUMNUTZER (Mail) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE GENRE (
    Genrename VARCHAR NOT NULL COLLATE NOCASE UNIQUE PRIMARY KEY CHECK(length(Genrename)>1 AND length(Genrename)<50 AND Genrename NOT GLOB '*[^a-zA-Z]*')
);
CREATE TABLE TITEL (
    Benennung VARCHAR NOT NULL CHECK(length(Benennung)>1 AND length(Benennung)<50 AND Benennung NOT GLOB '*[^ -~]*'),
    Dauer     TIME NOT NULL CHECK(Dauer==strftime('%H:%M:%S',Dauer)),
    TID       INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT,
    LQ        VARCHAR NOT NULL COLLATE NOCASE CHECK(length(LQ)>1 AND length(LQ)<50 AND LQ NOT GLOB '*[^ -~]*' AND LQ GLOB '*.mp3'),
    HQ        VARCHAR NOT NULL COLLATE NOCASE CHECK(length(HQ)>1 AND length(HQ)<50 AND HQ NOT GLOB '*[^ -~]*' AND HQ GLOB '*.wav'),
    Genrename VARCHAR  REFERENCES GENRE (Genrename)
);
CREATE TABLE ALBUM (
    Albumname         VARCHAR NOT NULL CHECK(length(Albumname)>1 AND length(Albumname)<50 AND Albumname NOT GLOB '*[^ -~]*'),
    Erscheinungsdatum DATE NOT NULL CHECK(Erscheinungsdatum==strftime('%YYYY-%MM-%DD',Erscheinungsdatum)),
    AlID              INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT
);
CREATE TABLE GEHOERT_ZU (
    BID  INTEGER REFERENCES BAND (BID),
    Mail VARCHAR REFERENCES KUENSTLER (Mail) ON DELETE CASCADE ON UPDATE CASCADE
                PRIMARY KEY
);
CREATE TABLE BEWERTET (
    Punkte INTEGER NOT NULL CHECK(Punkte<11 AND Punkte>0),
    Mail   VARCHAR  REFERENCES NUTZER (Mail) ON DELETE CASCADE ON UPDATE CASCADE,
    PID    INTEGER REFERENCES PLAYLIST (PID),
    PRIMARY KEY (
        Mail,
        PID
    )
);
CREATE TABLE VEROEFFENTLICHT (
    Mail VARCHAR  REFERENCES KUENSTLER (Mail) ON DELETE CASCADE ON UPDATE CASCADE,
    TID  INTEGER REFERENCES TITEL (TID),
    PRIMARY KEY (
        Mail,
        TID
    )
);
CREATE TABLE KOMMENTIERT (
    Mail VARCHAR  REFERENCES NUTZER (Mail) ON DELETE CASCADE ON UPDATE CASCADE,
    TID  INTEGER REFERENCES TITEL (TID),
    Text TEXT NOT NULL,
    PRIMARY KEY (
        Mail,
        TID
    )
);
CREATE TABLE UMFASST (
    AlID INTEGER REFERENCES ALBUM (AlID),
    TID  INTEGER REFERENCES TITEL (TID),
    PRIMARY KEY (
        AlID,
        TID
    )
);
CREATE TABLE PRODUZIERT (
    AlID INTEGER REFERENCES ALBUM (AlID)
                 PRIMARY KEY,
    BID  INTEGER REFERENCES BAND (BID)
);
CREATE TABLE ERSTELLT (
    Mail VARCHAR  REFERENCES KUENSTLER (Mail) ON DELETE CASCADE ON UPDATE CASCADE,
    AlID INTEGER REFERENCES ALBUM (AlID)
                 PRIMARY KEY
);
CREATE TABLE SAMMELT (
    PID INTEGER REFERENCES PLAYLIST (PID),
    TID INTEGER REFERENCES TITEL (TID),
    PRIMARY KEY (
        PID,
        TID
    )
);
CREATE TABLE EMPFIEHLT (
    Genrename1 VARCHAR REFERENCES GENRE (Genrename)  ON DELETE CASCADE ON UPDATE CASCADE
                      PRIMARY KEY,
    Genrename2 VARCHAR REFERENCES GENRE (Genrename)  ON DELETE CASCADE ON UPDATE CASCADE
);



CREATE TRIGGER bewertetMussKommentiertHaben/**/
BEFORE INSERT ON BEWERTET
BEGIN
    SELECT RAISE(ABORT, 'Muss mindestens einen Titel aus der Playlist kommentiert haben')
    WHERE NOT EXISTS (
      SELECT Mail FROM KOMMENTIERT
      WHERE TID IN (
        SELECT TID FROM SAMMELT
        WHERE PID = NEW.PID)
      AND Mail == NEW.Mail);
END;

CREATE TRIGGER schonProduziert/**/
BEFORE INSERT ON ERSTELLT
BEGIN
    SELECT RAISE(ABORT, 'Kann kein Album erstellen, welches bereits von einer Band produziert wurde')
    WHERE EXISTS (
        SELECT * FROM PRODUZIERT
        WHERE AlID == NEW.AlID);
END;

CREATE TRIGGER schonErstellt/**/
BEFORE INSERT ON PRODUZIERT
BEGIN
    SELECT RAISE(ABORT, 'Kann kein Album produzieren, welches bereits von einem Kuenstler erstellt wurde')
    WHERE EXISTS (
        SELECT * FROM ERSTELLT
        WHERE AlID == NEW.AlID);
END;

CREATE TRIGGER albumLoeschbar/**/
BEFORE DELETE ON ALBUM
BEGIN
    SELECT RAISE(ABORT, 'Kann kein Album loeschen, welches mindestens einen Titel enthaelt')
    WHERE EXISTS (
        SELECT * FROM UMFASST
        WHERE AlID == OLD.AlID);
END;

CREATE TRIGGER albumUnveraenderbar/**/
BEFORE UPDATE ON ALBUM
BEGIN
    SELECT RAISE(ABORT, 'Kann kein Album aendern, welches mindestens einen Titel enthaelt')
    WHERE EXISTS (
        SELECT * FROM UMFASST
        WHERE AlID == OLD.AlID);
END;
/*
.read data.sql
.read queries.sql
.exit
*/
