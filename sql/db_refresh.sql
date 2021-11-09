CREATE EXTENSION IF NOT EXISTS pgcrypto;


DROP TABLE IF EXISTS Students cascade;
DROP TABLE IF EXISTS Faculty cascade;
DROP TABLE IF EXISTS Users cascade;


CREATE TABLE Users (
    UserId INT PRIMARY KEY,
    Password varchar(40),
    FirstName varchar(40),
    LastName varchar(40),
    EmailAddress varchar(40),
    LastAccess date,
    EnrollDate date,
    Enabled boolean,
    Type char(1)
);

ALTER TABLE Users owner to webd4201_admin;

CREATE TABLE Students (
    UserId INT PRIMARY KEY REFERENCES Users(UserId),
    ProgramCode varchar(4),
    ProgramDescription varchar(40),
    Year int
);

ALTER TABLE Students owner to webd4201_admin;


CREATE TABLE Faculty (
    UserId INT PRIMARY KEY REFERENCES Users(UserId),
    SchoolCode varchar(5),
    SchoolDescription varchar(40),
    Office varchar(5),
    Extension char(4)
);

ALTER TABLE Faculty owner to webd4201_admin;

--Students
INSERT INTO Users VALUES(
	100543802,
	encode(digest ('SecurePassword6%', 'sha1'), 'hex'),
	'Thomas',
	'Sinka',
	'thomas.sinka@dcmail.ca',
	TO_DATE('09/01/2018', 'DD/MM/YYYY'),
	current_date,
	TRUE,
	'S'
);

INSERT INTO Users VALUES(
	100111111,
	encode(digest ('Password', 'sha1'), 'hex'),
	'Mike',
	'Jones',
	'Mike.Jones@dcmail.ca',
	TO_DATE('09/01/2017', 'DD/MM/YYYY'),
	TO_DATE('02/03/2020', 'DD/MM/YYYY'),

	TRUE,
	'S'
);

INSERT INTO Users VALUES(
	100845504,
	encode(digest ('Qwerty', 'sha1'), 'hex'),
	'Elizabeth',
	'Johnson',
	'ElizabethJohnson@gmail.com',
	TO_DATE('09/01/2018', 'DD/MM/YYYY'),
	current_date,
	TRUE,
	'S'
);



--Faculty
INSERT INTO Users VALUES(
	100757305,
	encode(digest ('123', 'sha1'), 'hex'),
	'Dennis',
	'Young',
	'Dennis.Young@yahoo.com',
	TO_DATE('09/01/2000', 'DD/MM/YYYY'),
	current_date,
	TRUE,
	'F'
);

INSERT INTO Users VALUES(
	100261025,
	encode(digest ('Password2', 'sha1'), 'hex'),
	'Sharon',
	'Myers',
	'S.Myers@yahoo.com',
	TO_DATE('09/01/2000', 'DD/MM/YYYY'),
	current_date,
	TRUE,
	'F'
);

INSERT INTO Users VALUES(
	100177869,
	encode(digest ('RalphRose', 'sha1'), 'hex'),
	'Ralph',
	'Rose',
	'R.Rose@hotmail.com',
	TO_DATE('09/01/2000', 'DD/MM/YYYY'),
	current_date,
	TRUE,
	'F'
);





INSERT INTO Faculty VALUES(
	100757305,
	'CPA',
	'Computer Programming and Analysis',
	'H-123',
	'4535'
);


INSERT INTO Faculty VALUES(
	100261025,
	'CPA',
	'Computer Programming and Analysis',
	'B-203',
	'4363'
);

INSERT INTO Faculty VALUES(
	100177869,
	'CPA',
	'Computer Programming and Analysis',
	'G-209',
	'7424'
);




INSERT INTO Students VALUES(
	100543802,
	'CPA',
	'Computer Programming and Analysis',
	2
);

INSERT INTO Students VALUES(
	100111111,
	'CSTY',
	'Computer System Technology',
	3
);

INSERT INTO Students VALUES(
	100845504,
	'CPA',
	'Computer Programming and Analysis',
	2
);






