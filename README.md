# Hertz Library demo

# Project structure

- parent module: just a wrapper for the other 3 submodules, to help build everything one go and manage dependency versions
- api module: to be included in clients so that they can use the models and have available ready-made services to call the library back-end
- service module: the actual library back-end
- consumer module: sample client application that uses the library-client and models provided in the api module

# Library service additional info

- since it doesn't use an actual DB, the "Repository" services were written as if they were implementing the CrudRepository and mimicking the inner workings of a persistence layer and DB.


# Building
from the parent folder, invoke maven:

> mvn clean build

it will build the parent module and all its sub-modules

# Running
start the server
> java -jar server/target/hertz-library-server-0.0.1-SNAPSHOT.jar

run a sample consumer application

> java -jar client/target/hertz-library-client-0.0.1-SNAPSHOT.jar

or use a rest client for manual testing.
I added a sample Insomnia export file ('hertz.library-collection.json'). Imported in Insomnia, this contains calls for endpoints for both the books and the members controllers

