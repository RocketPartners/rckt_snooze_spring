# Spring Boot Quick Starter for the "Snooze" API as a Service Platform

This Spring Boot project template and guide can help you get your [Snooze](https://github.com/RocketPartners/rckt_snooze) powered REST API up and running in less than 5 minutes.


1. Clone this repo
1. Add your JDBC connection information to src/main/resources/snooze.properties
```properties
########################################################################
## DATABASES 
########################################################################
db.class=io.rcktapp.api.Db
db.name=db
db.driver=YOUR_JDBC_DRIVER_HERE
db.url=YOUR_JDBC_URL_HERE
db.user=YOUR_JDBC_USER_HERE
db.pass=YOUR_JDBC_PASSWORD_HERE
db.poolMin=3
db.poolMax=5
```  
1. From your repo root directory, run 'gradlew build bootRun'
1. Open your browser to http://localhost:8080/demo/helloworld/${NAME_OF_A_TABLE_IN_YOUR_DB}
1. See the main [Snooze Readme](https://github.com/RocketPartners/rckt_snooze/configuring-your-api) for more information.
1. Enjoy!
