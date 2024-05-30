# financial-app

## Database
We are using MySQL as our database. To run the database, you need to have MySQL installed on your machine. 
Or you can use Docker to run the database locally.

```bash
docker run --name local-mysql -e MYSQL_ROOT_PASSWORD=strong_password -p 3307:3306 -d mysql:8.4.0
```

Notice that we are using port 3307 to avoid conflicts with other services that might be running on port 3306.

The database schema is automatically created by the application. You don't need to create it manually.

## Running the application
To run the application, you need to have Java 17 installed on your machine.

The application is a Spring Boot application, so you can run it using the following command:

```bash mvn spring-boot:run```

It will start the application on port 7002.

## API Documentation
The API documentation is available at http://localhost:7002/swagger-ui.html


