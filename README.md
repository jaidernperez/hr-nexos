# hr-nexos

> This project ...

## Table of contents
* [General info](#general-info)
* [Run in wildfly server](#run-in-wildfly-server)

## General info
Describe your project...

## Run in wildfly server

```bash
# Install Galleon CLI
# Install wildfly server
galleon.sh install wildfly:current --dir=my-wildfly-server
cd my-wildfly-server\bin
# Add wildfly user
./add-user.sh -u 'admin' -p 'secret!' -g 'admin'
# Start Server and enter into wildfly cli
./jboss-cli.sh --connect controller=127.0.0.1
# Create postgresql Driver
module add --name=org.postgresql --resources=/path/to/driver/postgresql-version.jar --dependencies=javax.api,javax.transaction.api
# Add Postgresql Driver
/subsystem=datasources/jdbc-driver=postgresql:add(driver-name="postgresql",driver-module-name="org.postgresql",driver-class-name=org.postgresql.Driver)
# Add datasource Postgres
data-source add --name=ExampleDSPg --jndi-name=java:jboss/datasources/ExamplePg --driver-name=postgresql --connection-url=jdbc:postgresql://localhost:5432/db_name --user-name=user --password=secret
# Add datasource H2
data-source add --name=ExampleDS --jndi-name=java:jboss/datasources/ExampleDS --driver-name=h2 --connection-url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE --user-name=user --password=secret
# Exit of jboss-cli

```