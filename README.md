# WannaWatch - Film & TV Show Tracker

>Is a back-end API built to be used alongside the [WannaWatch Android application](https://github.com/dsciocan/media-tracker-front), built with Spring Boot. 
> It offers access to saved film/TV show data from TMDB, and authentication locked user-specific data.  

---


## Used Technologies

- Film and TV show data from [TMDB API](https://developer.themoviedb.org/reference/intro/getting-started)
- [Spring Boot](https://spring.io/projects/spring-boot):  
  *Is a open-source tool to make it easier to manage Java-based frameworks such as:  
  **Security**, **in app h2 database**, **lombok**, ...*
- [Hibernate](https://hibernate.org/):  
  *Is an object–relational mapping tool for the Java programming language. It provides a framework for mapping an object-oriented domain model to a relational database.*
- [PostgreSQL](https://www.postgresql.org/):  
  *Stores the user and movie data in a local "relational database".*
- [Firebase](https://firebase.google.com/):  
  *To authenticate users through there google account.*
- [TMDB](https://developer.themoviedb.org/docs/getting-started):  
  *Movie database api to get the movie information.*


--- 

## Achitecture

### Entity Relationship Diagram
*The relation of the tables and there data to each other in the postgres database.*
![Entity Relationship Diagram](./img/Entity%20Relationship%20Diagram.jpg)


---
## Planned Features
- Expansion of user information and saved data to allow more interaction between users
- Improved efficiency and deployment options

---

## End-points

### Film/TV Show Data
### /api/v1/mediatracker/films/ *or* /api/v1/mediatracker/shows/
> **GET search/*search-query*** Film/TV Show Search Results

> **GET /details/*api-id*** Unsaved Film/TV Show Details from External API

> **GET /save/*db-id*** Saved Film Details from Database by Database Id

> **GET /saved/*db-id*** Saved Tv Show Details from Database by Database Id

> **GET /saved/tmdb=*api-id*** Saved Film/TV Show Details from Database by External API Id

> **POST /save/*film-db-id*** Save Film Details to Database

> **POST /save** Save TV Show Details to Database

> **DELETE /delete/*film-db-id*** Saved Film from Database

> **DELETE /*show-db-id*** Saved Tv Show from Database


### User Details
### /api/v1/mediatracker/users
#### must be logged in or add an active authentication header

> **GET /auth** Saved Google OAuth User (and save if new user)

> **DELETE /delete** Google OAuth logged in user

### User Show Details
### /api/v1/mediatracker/users
#### must be logged in or add an active authentication header

> **GET /shows/** All TV Shows saved by signed-in user

> **GET /shows/*showId*** User TV Show by id

> **GET /shows?status=*query*(&genre=*query*)** Saved user TV Show details by watching status and optionally by genre

> **GET /shows/*showId*/episodes** Specific saved user TV Show Episode list

> **GET /shows/*showId*/latest** Latest episode marked as watched from specific TV Show on user's list

> **POST /shows/save/*apiShowId*** Save TV Show to user's list

> **PUT /shows/*showId*** Update saved user TV Show


### User Episode Details
### /api/v1/mediatracker/users
#### must be logged in or add an active authentication header

> **GET /episode/*episodeId*** User episode by id

> **PATCH /episode/*episodeId*** Update episode on user's list


### User Film Details
### /api/v1/mediatracker/users
#### must be logged in or add an active authentication header

> **GET /films** All films saved by signed-in user

> **GET /shows/*filmId*** User film by id

> **GET /films/search?status={query}** Saved user film details by watching status

> **POST /films/*filmId*** Save film to user's list

> **PATCH /films/*filmId*** Update saved user film

> **DELETE /films/*filmId*** Delete film from user's list


### User Statistics
### /api/v1/mediatracker/users
#### must be logged in or add an active authentication header

> **GET /totalWatchedRuntime** Total runtime of watched films and TV shows

> **GET /genreStats** Number of watched films & TV shows for each genre

---
