DROP TABLE users;
DROP TABLE messages;
DROP TABLE friendships;

DROP SEQUENCE messageSequence;
DROP SEQUENCE friendshipSequence;

CREATE TABLE users (username VARCHAR2(50));
CREATE TABLE messages (message_id NUMBER, sender VARCHAR2(100),receiver VARCHAR2(100), content VARCHAR2(100));
CREATE TABLE friendships (friendship_id NUMBER, first_user VARCHAR2(50), second_user VARCHAR2(50));

CREATE SEQUENCE messageSequence START WITH 1;
CREATE SEQUENCE friendshipSequence START WITH 1;