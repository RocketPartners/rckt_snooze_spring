# Spring Boot Quick Starter for the "Snooze" API as a Service Platform

This Spring Boot project template and guide can help you get your [Snooze](https://github.com/RocketPartners/rckt_snooze) powered REST API up and running in less than 5 minutes.

## Quick Start

1. Clone this repo
1. Add your JDBC connection information to src/main/resources/snooze.properties
```properties
########################################################################
## DATABASES 
########################################################################
db.class=io.rcktapp.api.handler.sql.SqlDb
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




## Using Spring Boot Profiles

Snooze lets you [supply config files](https://github.com/RocketPartners/rckt_snooze#configuring-your-api) that are numbered and/or named to match a Spring deployment target profile.
This makes it easy to keep dev/stage/prod config files in the same project/deployment package and simply change the runtime profile.

For example, if you supply both a snooze.properties and snooze-prod.properties, the settings in the prod file will override the settings in the main "snooze.properties"
file.  To set the runtime target to prod and load this extra file, add the following to your JVM args '-Dspring.profiles.active=prod'.  You can have as many 
deployment targets as you would like.  A typical project may have something like:

 * snooze.properties
 * snooze-dev.properties
 * snooze-stage.properties
 * snooze-prod.properties
 
all in the same project.




## Keeping Passwords out of Config Files

If you want to keep your database passwords (or any other sensative info) out of your snoooze.properties config files, you can simply set an environment variable OR
VM system property using the relevant key.  For example you could add '-Ddb.pass=MY_PASSWORD' to your JVM launch configuration OR something like 'EXPORT db.pass=MY_PASSWORD'
to the top of the batch file you use to launch our yoru Spring Boot app. If you are running in AWS ElasticBeanstalk, you could pass the keys/values as Environment Properties.  

For example, after running 'gradle build' you could launch like this:  

```bash
java -Ddb.pass=MY_PASSWORD -jar build/libs/rckt_snooze_spring-0.0.1.jar
```
 