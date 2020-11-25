# withward

## Description

_withward_ is the combination of the word "with" and the native English suffix ["-ward"](https://www.dictionary.com/browse/-ward), which is defined as denoting spatial or temporal direction, as specified by the initial element, such as toward; seaward; afterward; and backward.

For thousands of years, humans have traveled together to survive. If you are familiar with The Legend of Zelda, you may have come to know the quote, "It's dangerous to go alone! Take this." This [quote](https://en.wikipedia.org/wiki/It%27s_dangerous_to_go_alone!) appears when Link is given a sword from the unnamed old man inside of the first cave of the game. This sword becomes instrumental in his quest to defeat Ganon and rescue princess Zelda. So, how does this help me understand _withward_? I'm glad you asked...

The idea of _withward_ means to travel _with_ someone toward any destination. The _withward_ app will help you stay organized in your real life quests by connecting you with other users, keeping track of your visited and to-be-visited destinations, and allow you and your companions to contribute their ratings to a visited place.

## Table of Contents

- [Installation](#installation)
- [Setup](#setup)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Tests](#tests)
- [Credits](#credits)
- [License](#license)

## Installation

You will have to download the following in order to set up the app locally:

- maven
- tomcat
- jdk8
- Running PostgreSQL DB
  Make sure you have the following environment variables configured (usually you could also link to official guides/ website):

- JAVA_HOME
- CATALINA_HOME
- MAVEN_HOME
- M2_HOME
- DB_URL = connection string for postgresql jdbc drier
- DB_USERNAME = user name for db
- DB_PASSWORD = password for user for PG DB Notice! This app should use the above environment variables for the DB.... but we happen to have hardcoded it in this current version.

## Setup

1. clone the application from repository

> git clone https://github.com/dansirdan/withward-java.git

2. Pre-populate the db using the `schema.sql` file that is provided

1. setup tomcat

- Manually:
    - package into a war with maven:
        > mvn clean package
    - deploy to tomcat by copying the war to the `webapps` folder in Tomcat Directory
    - run startup.sh/startup.bat in tomcat folder
    - navigate to {/withward/tbd}
- IDE:
    - setup tomcat to run virtually within your ide
    - startup tomcat and navigate to {/withward/tbd}

## Usage

Below you will find the various endpoints for the api. All requests stem from the following paths:

- /users
- /withlists
- /destinations
- /login

## Endpoints

### POST /users/new
This will create a new user in the database.
```
  {
    "username": "",
    "email": "",
    "password": "",
    "photo": ""
  }
```

### PUT /users/edit?{id}
This is will edit a user in the database based on id.

### DELETE /users/delete?{id}
Delete user by id from database.

### GET /users/all
Returns an array of user objects.

### GET /users?{id}
Returns a single user object based on id.

### POST /withlists/new
This will create a new withlist in the database.
```
  {
	"title": "",
	"description": ""
  }
```

### PUT /withlists/edit?{id}
This is will edit a withlist in the database based on id.

### DELETE /withlists/delete?{id}
Delete withlist by id from database.

### GET /withlists/all?{userid}
Returns an array of withlist objects that belong to the user.

### GET /withlists?{id}
Returns a single withlist object based on id.


### POST /destinations/new
This will create a new destination in the database.
```
  {
	"name": "",
	"description": "",
	"photo": ""
  }
```

### PUT /destinations/edit?{id}
This is will edit a destination in the database based on id.

### DELETE /destinations/delete?{id}
Delete destination by id from database.

### GET /destinations/all?{withlistid}
Returns an array of destination objects that belong to a specific withlist.

### GET /destinations?{id}
Returns a single destination object based on id.

## Tests

```
While in the root folder of the project, run:
```
> mvn test

## Credits

William Ono for the .gitignore

## License

Apache License

## Copyright (c) 2020 Daniel Mont-Eton

🏆
