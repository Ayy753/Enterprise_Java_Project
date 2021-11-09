--File:		Students.sql
--Athor:	Thomas Sinka
--Created: 	2/3/2020
--Description:	This script creates a student table and records that corrispond to the user table


CREATE EXTENSION IF NOT EXISTS pgcrypto;
DROP TABLE IF EXISTS Students cascade;

CREATE TABLE Students (
    UserId INT PRIMARY KEY REFERENCES Users(UserId),
    ProgramCode varchar(4),
    ProgramDescription varchar(40),
    Year int
);

ALTER TABLE Students owner to webd4201_admin;

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