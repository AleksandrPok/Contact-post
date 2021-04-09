# Contact-post Application [![Build Status](https://travis-ci.com/AleksandrPok/Contact-post.svg?branch=main)](https://travis-ci.com/AleksandrPok/Contact-post)

This application returns single contact by id.
Requests and responses are logging to the log4j2.log file. The file will be created after the first application launch.

## Running the Application

1. Download and install JDK;
2. Download and install MySQL Server;
3. Setup connection properties in `application.properties` file:
    - `spring.datasource.url=jdbc:mysql://localhost/your_db_name`
    - `spring.datasource.username=your username`
    - `spring.datasource.password=your password`