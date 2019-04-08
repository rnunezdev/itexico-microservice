drop table USERS if exists;

create table USERS (ID bigint identity primary key,
                    FIRST_NAME varchar(35) not null,
                    LAST_NAME varchar(35) not null,
                    DATE_OF_BIRTH varchar(10) not null,
                    STATUS varchar(10) not null);

ALTER TABLE USERS ALTER COLUMN STATUS SET DEFAULT 'ACTIVE';