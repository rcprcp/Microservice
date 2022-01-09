## Sample Microservice

Sample microservice using some technologies that are new to me.  The idea is to build a plausible microservice that offers some REST endpoints that might be appropriate for the finance industry.  I chose a few based on my previous experience at Bloomberg.

The new technologies (for me) are:
* Javalin 4
* Swagger
* Finnhub  - which does not support a Java API.  Hence I used a package I found on GitHub: [Finnhub](https://github.com/oscerd/finnhub-java-client) which, as it turned out was missing some functionality, which led me to give the author [oscerd](https://github.com/oscerd) a pull request: [Pull 22](https://github.com/oscerd/finnhub-java-client/pull/22) to support getting Candle chart data.  (He accepted it graciously and quickly, thanks! [oscerd](https://github.com/oscerd))

### Example of the Swagger UI
![Swagger UI](/images/REST.png)

### Setup the database
* You'll need a Postgres database - I am running Postgres 12 in docker (in this example my containerId is 318...): 
```shell
docker run -d -p 5432:5432 -v /home/bob/data/postgres:/var/lib/postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=postgres postgres:latest
```
* Open `psql` on the database - since my database is running in docker, it goes like this:
```shell
docker exec -it 318 /bin/bash
su - postgres
psql -h localhost -U postgres
```
* You'll need to set up the schema - it's in resources/schema.sql.  I think the easiest way is to simply copy-and-paste the commands from the schema.sql file into the `psql` window.

* You'll need to load the test data for the accounts table - which means copying the data into the docker container then in the psql program we run the `copy` command to copy the data and load it into the table.  This data is sample, fake data I use from time to time.  I provided about 200 records in TSV (tab separated) format.
```shell
docker cp account_holders.tsv 318:/tmp
```
* You'll need to load the test data for the transaction table run this in the `psql` program:
```shell
copy account_holders from '/tmp/account_holders.tsv'; 
```
Load the sample transaction data into the transactions table.  Once again, copy-n-paste this into the `psql` window. You may need to commit after the INSERT.
### Download the code
```shell
git clone https://github.com/rcprcp/Micoservice.git
```
### Build the application
`cd` into the directory in which you cloned the code: 
```shell
cd Microservice 
# build
mvn clean package
# you'll need to set some environment variables to make the code work:
export FINNHUB_TOKEN=abcf12345653993cdfe
export DATABASE_URL=jdbc:postgresql://localhost:5432/tornado
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=postgres
## start the program:
java -jar target/Microservice-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Test the application
* For the JSON specification browse to [http://localhost:7002/swagger-docs](http://localhost:7002/swagger-docs)
* For the Swagger UI for testing purposes Browse to [http://localhost:7002/swagger-ui#](http://localhost:7002/swagger-ui#)
* Test accounts 27 - 34 with the following date ranges:
  * 2021-11-31 00:00:00 through 2021-12-09 00:00:00
  * endpoint /portfolio/pandl/{accountNumber}/{startDate}/{endDate}
  provides this answer for account 34:
  ```json
    {
    "accountNumber": 33,
    "holdings": [
    {
    "symbol": "SNOW",
    "startWindowPrice": 340.15,
    "startWindowShares": 0,
    "endWindowPrice": 361.32,
    "endWindowShares": 0,
    "runningTotal": 60,
    "runningShares": 0,
    "realizedPandL": 60,
    "unrealizedPandL": 0
    }
    ]
    }
  ``` 
   startWindowPrice and endWindowPrice are for debugging. The transactions in the window are 3 1-share buys at 100 and 1 sell for 3 shares a 120, so 60 in realized profit.

## Issues 
* the program will exit for unparsable date formats. use this format: 2021-11-31 00:00:00
* error conditions need more testing, most result in System.exit() to help identify the failures.


