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
1. Open your browser to ht<span>tp://</span>//localhost:8080/demo/helloworld/${NAME_OF_A_TABLE_IN_YOUR_DB}
1. See the main [Snooze Readme](https://github.com/RocketPartners/rckt_snooze#configuring-your-api) for more information.
1. Enjoy!


## Clone / Fork

1. Create a new repo in GitHub without a README -- **IMPORTANT: DO NOT CREATE DEFAULT README**
1. Clone the repo 
`git clone git@github.com:RocketPartners/rckt_snooze_spring.git {NEW_REPO_NAME_GOES_HERE}`
1. Rename the cloned repo to "upstream"
`git remote rename origin upstream`
1. Make your new repo the "origin" remote
`git remote add origin git@github.com:RocketPartners/{NEW_REPO_NAME_GOES_HERE}.git`
1. Push the clone to your new repo
`git push origin master`
1. Make your local master branch use origin as it's remote-tracking-branch
`git branch master -u origin/master`


## Using Spring Boot Profiles

Snooze lets you [supply config files](https://github.com/RocketPartners/rckt_snooze#configuring-your-api) that are numbered and/or named to match a Spring deployment target profile.
This makes it easy to keep dev/stage/prod config files in the same project/deployment package and simply change the runtime profile.

For example, if you supply both a snooze.properties and snooze-prod.properties, the settings in the prod file will override the settings in the main "snooze.properties"
file.  To set the runtime target to prod and load this extra file, add the following to your JVM args '-Dspring.profiles.active=prod'.  You can have as many 
deployment targets as you would like.  A typical project may have something like the following all in the same project:

 * snooze.properties (will always load)
 * snooze-dev.properties (will only load when profile is 'dev' and will overwrite values from snooze.properties)
 * snooze-stage.properties (will only load when profile is 'stage' and will overwrite values from snooze.properties)
 * snooze-prod.properties (will only load when profile is 'prod' and will overwrite values from snooze.properties)
 
 
## Keeping Passwords out of Config Files

If you want to keep your database passwords (or any other sensative info) out of your snoooze.properties config files, you can simply set an environment variable OR
VM system property using the relevant key.  For example you could add '-Ddb.pass=MY_PASSWORD' to your JVM launch configuration OR something like 'EXPORT db.pass=MY_PASSWORD'
to the top of the batch file you use to launch our yoru Spring Boot app. If you are running in AWS ElasticBeanstalk, you could pass the keys/values as Environment Properties.  

For example, after running 'gradle build' you could launch like this:  

```bash
java -Ddb.pass=MY_PASSWORD -jar build/libs/rckt_snooze_spring-0.0.1.jar
```
 