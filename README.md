# HR-Nexos

## Table of contents
* [General info](#general-info)
* [Configuring the Wildfly Server](#configuring-the-wildfly-server)
* [Executing the project in docker](#executing-the-project-in-docker)

## General info
The project is a management system that focuses on two main entities: departments and employees. In the domain layer,
each department has a unique identity and can contain several employees. Employees, on the other hand, are associated 
with a department and have their own attributes.

The system allows CRUD (Create, Read, Update, Delete) operations on both entities. Users can create new departments or
employees, read information about existing departments or employees, update department or employee information, and 
delete departments or employees if necessary.

## Configuring the Wildfly Server

This section provides detailed instructions on how to set up and configure the Wildfly server for optimal performance. 
It covers everything from initial setup, user creation, to optional configurations like setting up a custom H2 
Datasource. Whether you’re using MacOS, Linux, or Windows, you’ll find step-by-step guidance tailored to your operating 
system.

### 1. Install Galleon CLI

To access the latest version of Galleon CLI, please follow the steps below:

* Navigate to the [Galleon Releases](https://github.com/wildfly/galleon/releases) page.
* Locate the most recent release and download the corresponding zip file.
* Once the download is complete, extract the contents of the zip file.

In this guide, we will be using version 5.2.0.Final as an example.

### 2. Download Wildfly 24 server

Depending on your operating system, execute the appropriate command to download Wildfly 24 server:

For MacOS or Linux:
```sh
./galleon-5.2.0.Final/bin/galleon.sh install wildfly:24 --dir=wildfly-24
```
For Windows:
```bash
galleon-5.2.0.Final\bin\galleon.bat install wildfly:24 --dir=wildfly-24
```

You can change the --dir flag for the location that you want.

### 3.  Creating a Wildfly User

To create a new user for Wildfly, execute the following commands. In this example, we are creating a user with the 
username ‘admin’, password ‘secret!’, and group ‘admin’. Feel free to modify these credentials as needed.

For MacOS or Linux:
```sh
./wilfly-24/bin/add-user.sh -u 'admin' -p 'secret!' -g 'admin'
```
For Windows:
```bash
wilfly-24\bin\add-user.bat -u "admin" -p "secret!" -g "admin"
```

### 4. Configuring a Custom H2 Datasource (Optional)

This step is optional and involves setting up a custom H2 Datasource.

#### 4.1. Launching the Wildfly Server

Depending on your operating system, use the following command to start the Wildfly server:

For MacOS or Linux:
```sh
./wilfly-24/bin/standalone.sh
```
For Windows:
```bash
wilfly-24\bin\standalone.bat
```

#### 4.2. Accessing Jboss CLI

To connect to the Jboss CLI, execute the appropriate command for your operating system:

For MacOS or Linux:
```sh
./wilfly-24/bin/jboss-cli.sh --connect controller=127.0.0.1
```
For Windows:
```bash
wilfly-24\bin\jboss-cli.bat --connect controller=127.0.0.1
```

#### 4.3. Adding H2 Datasource in Jboss CLI

Use the following command to add an H2 datasource in the Jboss CLI:
```sh
data-source add --name=H2Datasource --jndi-name=java:jboss/datasources/H2Datasource --driver-name=h2 --connection-url=jdbc:h2:mem:db_nexos;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE --user-name=user --password=secret
```
To exit from Jboss CLI, simply type `exit`.

## Executing the Project in Docker

To run the project in Docker, follow the steps below:

1. Build the Docker image:
    ```sh
    docker build -t hr-nexos .
    ```
2. Run the Docker container:
    ```sh
    docker run -p 8083:80 -e PORT=80 hr-nexos
    ```
In this command, we’re mapping port 8083 on your machine to port 80 on the Docker container. The -e PORT=80 sets an 
environment variable inside the container that specifies the port the application should listen on.
