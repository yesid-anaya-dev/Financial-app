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

``` mvn spring-boot:run```

It will start the application on port 7002.

## API Documentation
The API documentation is available at http://localhost:7002/swagger-ui.html

## Security
The application uses api keys to authenticate the requests. You need to provide the api key and api secret in the headers of the request.
`api-key: api_key`
`api-secret: api_secret`
The api key and api secret are hardcoded in the application. You can find them in _application.properties_ file.

## Monitoring
The application uses Spring Boot Actuator to provide monitoring endpoints. You can find them at http://localhost:7002/actuator

## Postman/Bruno Collection
You can find a Postman collection in the folder **docs** in the root of the project. It contains all the requests that you can make to the application.

## Test coverage
The application has unit tests that cover the main functionalities. You can run the tests using the following command:

```mvn clean test```
A coverage report is generated in the folder **target/site/jacoco/index.html**

## Some considerations

- The birthdate is a string in the format `dd/MM/yyyy`.
- For a deposit, we need to provide the account number in the field `receiver_account_number`.
- For a withdrawal, we need to provide the account number in the field `sender_account_number`.
- For a transfer, we need to provide the account number in the fields `sender_account_number` and `receiver_account_number`.

## Future improvements
- Add more tests
- Add integration tests
- Add more validations
- Improve the security layer (use JWT, for example)
- Improve the error handling, returning more meaningful messages
- Use of docker-compose to run the application and the database together
- Add some quality checks (SonarQube, Checkstyle, PMD, etc)
- Add a CI/CD pipeline



