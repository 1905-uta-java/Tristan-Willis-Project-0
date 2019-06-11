CREATE TABLE bankuser(
UserID NUMBER NOT NULL,
UserName VARCHAR(20) NOT NULL,
UserPass VARCHAR(30) NOT NULL,
CONSTRAINT PK_bankuser PRIMARY KEY (userID)
);

ALTER TABLE bankuser 
ADD CONSTRAINT Unique_Name UNIQUE(UserName);

CREATE TABLE bankaccount(
AccountID NUMBER NOT NULL,
UserID NUMBER NOT NULL,
AccountBalance INTEGER DEFAULT 100 CHECK (AccountBalance >= 0) NOT NULL,
AccountName VARCHAR(30),
CONSTRAINT PK_bankaccount PRIMARY KEY (AccountID),
CONSTRAINT FK_bankuser FOREIGN KEY (userID) REFERENCES bankuser (userID)
);

CREATE SEQUENCE user_id_inc START WITH 1;
CREATE SEQUENCE account_id_inc START WITH 1;

CREATE OR REPLACE TRIGGER TG_user_inc
BEFORE INSERT ON bankuser
FOR EACH ROW
BEGIN
    SELECT user_id_inc.NEXTVAL
    INTO :NEW.UserID
    FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER TG_account_inc
BEFORE INSERT ON bankaccount
FOR EACH ROW
BEGIN
    SELECT account_id_inc.NEXTVAL
    INTO :NEW.AccountID
    FROM DUAL;
END;
/

CREATE OR REPLACE PROCEDURE delete_user(uid IN bankuser.UserID%TYPE)
AS
BEGIN
    DELETE FROM bankaccount B WHERE B.userID = uid;
    DELETE FROM bankuser U WHERE U.userID = uid;
END;
/

INSERT INTO bankuser(UserName, UserPass) VALUES ('ABC', 'PASS');
INSERT INTO bankuser(UserName, UserPass) VALUES ('DEF', 'PASS2');
INSERT INTO bankuser(UserName, UserPass) VALUES ('GHI', 'PASS3');
INSERT INTO bankuser(UserName, UserPass) VALUES ('JKL', 'PASS4');
INSERT INTO bankuser(UserName, UserPass) VALUES ('MNO', 'PASS5');
INSERT INTO bankuser(UserName, UserPass) VALUES ('PQR', 'PASS6');
INSERT INTO bankuser(UserID, UserName, UserPass) VALUES (1, 'STU', 'PASS7');

INSERT INTO bankaccount(UserID) VALUES (1);
INSERT INTO bankaccount(UserID) VALUES (2);
INSERT INTO bankaccount(UserID) VALUES (3);
INSERT INTO bankaccount(UserID) VALUES (4);
INSERT INTO bankaccount(UserID) VALUES (5);
INSERT INTO bankaccount(UserID) VALUES (6);
INSERT INTO bankaccount(AccountID, UserID) VALUES (1, 21);

UPDATE bankaccount SET accountname = 'unnamed';

commit;
exit;

SELECT * FROM bankuser;
SELECt * FROM bankaccount;