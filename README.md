
# Banking sample app

## Run the app with Spring Boot

```bash
mvn spring-boot:run
```

The app will be deployed to <http://localhost:8080>.

## REST API endpoints

Create transaction:
```
PUT localhost:8080/api/v1/transaction/{account_from_id}/{account_to_id}/{amount_in_cents}
```

Account data:
```
GET localhost:8080/api/v1/account/{account_id}
```

## Future developments

Authentication

Pagination of the results

Using real database with environment-specific configuration

Caching the results