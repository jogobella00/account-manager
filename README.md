# Welcome to the Account Manager
Application allows retrieving information about customers, 
that are in database or adding new account by making a transfer to chosen customer.

###Architecture
It's Spring Boot application, using Java 11, in-memory H2 database and frontend with Thymeleaf template engine.

H2 to tables are created according to JPA&Hibernate annotations in model package.

SQL data model diagram:
<img src="/documentation-files/sql_diagram.png" alt="SQL Diagram">