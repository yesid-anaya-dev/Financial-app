# financial-app

## Database
We are using MySQL as our database. To run the database, you need to have MySQL installed on your machine. 
Or you can use Docker to run the database locally.

```bash
docker run --name local-mysql -e MYSQL_ROOT_PASSWORD=strong_password -p 3307:3306 -d mysql:8.4.0
```

Notice that we are using port 3307 to avoid conflicts with other services that might be running on port 3306.