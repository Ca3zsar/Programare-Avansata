DROP TABLE movies;
DROP TABLE genres;
DROP TABLE movie_genres;
DROP TABLE actors;
DROP TABLE directors;
DROP TABLE actors_movies;
DROP TABLE directors_movies;
DROP SEQUENCE movieSequence;
DROP SEQUENCE actorsSequence;
DROP SEQUENCE directorsSequence;
DROP SEQUENCE genresSequence;

CREATE TABLE movies (id INTEGER PRIMARY KEY, title VARCHAR2(200), release_date DATE, 
                     duration INTEGER, score FLOAT(5));


CREATE TABLE genres (id INTEGER, genre_name VARCHAR2(20));


CREATE TABLE movie_genres (movie_id INTEGER, genre_id INTEGER);


CREATE TABLE actors (id INT,name VARCHAR2(200), birthday DATE);


CREATE TABLE directors(id INT, name VARCHAR2(200), birthday DATE);


CREATE TABLE actors_movies(actor_id INT, movie_id INT);


CREATE TABLE directors_movies(director_id INT, movie_id INT);


CREATE SEQUENCE movieSequence START WITH 1;
CREATE SEQUENCE actorsSequence START WITH 1;
CREATE SEQUENCE directorsSequence START WITH 1;
CREATE SEQUENCE genresSequence START WITH 1;