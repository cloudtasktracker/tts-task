drop table tts_task cascade;
drop table tts_user cascade;

CREATE TABLE tts_task (
	id MEDIUMINT NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL DEFAULT '',
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tts_user (
	id MEDIUMINT NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL DEFAULT '',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;