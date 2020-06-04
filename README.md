Run Redis:

`docker run --name starbux-redis -p 6379:6379 -d redis`

Run PostgreSQL:

`docker run --name coffee-fiddle -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -d postgres`

Run Flyway migrations:

`mvn flyway:migrate`

Run app:

`mvn spring-boot:run`


