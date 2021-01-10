DROP SCHEMA IF EXISTS studentforum;
CREATE SCHEMA studentforum;
USE studentforum;

CREATE TABLE institusjon (
	inst_id INTEGER(10) NOT NULL AUTO_INCREMENT,
    inst_navn VARCHAR(45),
    CONSTRAINT InstitusjonPK PRIMARY KEY (inst_id)
);

CREATE TABLE fag (
	fag_id INTEGER(10) NOT NULL AUTO_INCREMENT,
    fag_navn VARCHAR(45),
    CONSTRAINT FagPK PRIMARY KEY (fag_id)
);

CREATE TABLE bilde (
	bilde_id INTEGER(10) NOT NULL AUTO_INCREMENT,
    lokasjon VARCHAR(255),
    bilde BLOB,
    CONSTRAINT BildePK PRIMARY KEY (bilde_id)
);

CREATE TABLE bruker (
	bruker_id INTEGER(10) NOT NULL AUTO_INCREMENT,
    bruker_inst INTEGER(10),
    bruker_fag INTEGER(10),
    brukernavn VARCHAR(45),
    passord VARCHAR(45),
    epost VARCHAR(45),
    CONSTRAINT BrukerPK PRIMARY KEY (bruker_id),
    CONSTRAINT BrukerInstitusjonFK FOREIGN KEY (bruker_inst) REFERENCES institusjon (inst_id),
    CONSTRAINT BrukerFagFK FOREIGN KEY (bruker_fag) REFERENCES fag (fag_id)
);

CREATE TABLE innlegg (
	inn_id INTEGER(10) NOT NULL AUTO_INCREMENT,
    inn_bruker INTEGER(10),
    tittel VARCHAR(255),
    inn_tekst VARCHAR(1024),
    inn_tid DATETIME,
    CONSTRAINT InnleggPK PRIMARY KEY (inn_id),
    CONSTRAINT InnleggBrukerFK FOREIGN KEY (inn_bruker) REFERENCES bruker (bruker_id)
);

CREATE TABLE kommentar (
	kom_id INTEGER(10) NOT NULL AUTO_INCREMENT,
    kom_innlegg INTEGER(10),
    kom_bruker INTEGER(10),
    kom_tekst VARCHAR(1024),
    kom_tid DATETIME,
    CONSTRAINT KommentarPK PRIMARY KEY (kom_id),
    CONSTRAINT KommentarInnleggFK FOREIGN KEY (kom_innlegg) REFERENCES innlegg (inn_id),
    CONSTRAINT KommentarBrukerFK FOREIGN KEY (kom_bruker) REFERENCES bruker (bruker_id)
);

CREATE TABLE profilbilde (
	profil_bilde INTEGER(10),
    profil_bruker INTEGER(10),
    CONSTRAINT ProfilbildePK PRIMARY KEY (profil_bilde, profil_bruker),
    CONSTRAINT ProfilbildeBildeFK FOREIGN KEY (profil_bilde) REFERENCES bilde (bilde_id),
    CONSTRAINT ProfilbildeBrukerFK FOREIGN KEY (profil_bruker) REFERENCES bruker (bruker_id)
);

CREATE TABLE Innleggbilde (
	innbilde_bilde INTEGER(10),
    innbilde_bruker INTEGER(10),
	CONSTRAINT InnleggbildePK PRIMARY KEY (innbilde_bilde, innbilde_bruker),
    CONSTRAINT InnleggbildeBildeFK FOREIGN KEY (innbilde_bilde) REFERENCES bilde (bilde_id),
    CONSTRAINT InnleggbildeBrukerFK FOREIGN KEY (innbilde_bruker) REFERENCES bruker (bruker_id)
);

CREATE TABLE Kommentarbilde (
	kombilde_bilde INTEGER(10),
    kombilde_bruker INTEGER(10),
	CONSTRAINT KommentarbildePK PRIMARY KEY (kombilde_bilde, kombilde_bruker),
    CONSTRAINT KommentarbildeBildeFK FOREIGN KEY (kombilde_bilde) REFERENCES bilde (bilde_id),
    CONSTRAINT KommentarbildeBrukerFK FOREIGN KEY (kombilde_bruker) REFERENCES bruker (bruker_id)
);

/* Institusjonsdata */ 
INSERT INTO institusjon(inst_navn) VALUES("USN");
INSERT INTO institusjon(inst_navn) VALUES("BI");
INSERT INTO institusjon(inst_navn) VALUES("NTNU");

/* Fagdata */ 
INSERT INTO fag(fag_navn) VALUES("IT");
INSERT INTO fag(fag_navn) VALUES("Økonomi");
INSERT INTO fag(fag_navn) VALUES("Juss");
INSERT INTO fag(fag_navn) VALUES("Markedsføring");
INSERT INTO fag(fag_navn) VALUES("Pedagogikk");
INSERT INTO fag(fag_navn) VALUES("Naturvitenskap");

/* Bruker */
INSERT INTO bruker(bruker_inst, bruker_fag, brukernavn, passord, epost) VALUES(1, 1, "test", "test", "test@test.no");
INSERT INTO bruker(bruker_inst, bruker_fag, brukernavn, passord, epost) VALUES(2, 2, "bruker", "bruker", "bruker@bruker.no");

/* Innlegg */
INSERT INTO innlegg(inn_bruker, tittel, inn_tekst, inn_tid) VALUES(1, 'Overskrift', 'Dette er en tekst', '2011-03-12');
INSERT INTO innlegg(inn_bruker, tittel, inn_tekst, inn_tid) VALUES(1, 'Overskrift', 'Dette er et nytt innlegg', '2011-03-15');
INSERT INTO innlegg(inn_bruker, tittel, inn_tekst, inn_tid) VALUES(2, 'Overskrift2', 'Dette er en tekst ny tekst av en annen bruker', '2011-03-15');

/* Kommentar */
INSERT INTO kommentar(kom_innlegg, kom_bruker, kom_tekst, kom_tid) VALUES(1, 1, 'Ja, jeg ser det', '2011-03-13');
INSERT INTO kommentar(kom_innlegg, kom_bruker, kom_tekst, kom_tid) VALUES(1, 1, 'Hva så?', '2011-03-14');
INSERT INTO kommentar(kom_innlegg, kom_bruker, kom_tekst, kom_tid) VALUES(2, 2, 'Av bruker', '2011-03-14');
INSERT INTO kommentar(kom_innlegg, kom_bruker, kom_tekst, kom_tid) VALUES(2, 2, 'Test her', '2011-03-14');

