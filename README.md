# Welcome to the Account Manager
Application allows retrieving information about customers, 
that are in database and adding new account by making a transfer to chosen customer.

### Startup

To run this application you just need to download the code and run it!
<br>Homepage is available at address: **localhost:8080**
<br>The H2 in-memory database you can access at: **localhost:8080/h2-console**
<br>Setting should be set to: 
<br>
Saved settings: *Generic H2 (Embedded)*
<br>
JDBC URL: *jdbc:h2:mem:account-manager*
<br>
User name: *sa*
<br>
Password: (empty)

Data in the DB is loaded on the startup of the application - it's specified in StartupDataLoad class.

### Architecture
It's Spring Boot application, using Java 11, in-memory H2 database and frontend with Thymeleaf template engine.

H2 to tables are created according to JPA&Hibernate annotations in model package.

SQL data model diagram:
<img src="/documentation-files/sql_diagram.png" alt="SQL Diagram">

Java entities:
<img src="/documentation-files/java_class_diagram.png" alt="Java classes">

### API

Account Manager has two separate APIs. 
The first one is for backend purposes.
It's exposing two endpoints:
<br>/v1/customer/{customerId}
<br>/v1/customer/{customerId}/account?initalCredit=number&account_name=text
<br>
Where customerId is a path variable and both initialCredit and account_name are query parameters.
In documentation-files directory you can find Postman collection exported to JSON, that consists all possible 
requests and responses from the API.

<br>
Second API is for managing frontend data flow. You can access is at 
address localhost:8080 after you run the application.