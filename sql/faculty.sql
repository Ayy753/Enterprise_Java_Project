--File:		Faculty.sql
--Athor:	Thomas Sinka
--Created: 	2/3/2020
--Description:	This script creates a faculty table and records that corrispond to the user table

CREATE EXTENSION IF NOT EXISTS pgcrypto;
DROP TABLE IF EXISTS Faculty cascade;

CREATE TABLE Faculty (
    UserId INT PRIMARY KEY REFERENCES Users(UserId),
    SchoolCode varchar(5),
    SchoolDescription varchar(40),
    Office varchar(5),
    Extension char(4)
);

ALTER TABLE Faculty owner to webd4201_admin;

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