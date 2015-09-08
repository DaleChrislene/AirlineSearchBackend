# AirlineSearchBackend

A DropWizard Project connected to Postgresql Database
1) Provides API "/routes" and "routes/search"

# Run migrations to set up table in DB
java $JAVA_OPTS -jar target/AirlineSearchService-0.0.1-SNAPSHOT.jar db migrate airline-search-config.yml


#Run application
java $JAVA_OPTS -Ddw.server.connector.port=$PORT -jar target/AirlineSearchService-0.0.1-SNAPSHOT.jar server airline-search-config.yml


#Sample usage for deployed application 
1) View all routes stored in DB:
http://morning-everglades-2967.herokuapp.com/routes

2) POST a request:
http://morning-everglades-2967.herokuapp.com/routes/search

POST Data of format:
```xml
<route>
<from>Singapore</from>
<to>Tokyo</to>
</route>
```

