DROP TABLE movies;
DROP TABLE genres;
DROP TABLE association;

/

CREATE TABLE movies (id INTEGER, title VARCHAR2(100), release_date DATE, 
                     duration INTEGER, score FLOAT(5));

/

CREATE TABLE genres (id INTEGER, genre_name VARCHAR2(20));

/

CREATE TABLE association (movie_id INTEGER, genre_id INTEGER);

/
