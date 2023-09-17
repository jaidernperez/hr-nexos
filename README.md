# HR-Nexos

## Table of contents
* [General info](#general-info)
* [Configuring the Wildfly Server](#configuring-the-wildfly-server)
* [Project Execution](#project-execution)
* [Executing the project in docker](#executing-the-project-in-docker)
* [Application Showcase](#application-showcase)

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

## Project Execution

To run the project, ensure that you have set up the Wildfly server as outlined in the previous step and have installed 
**mvn** on your operating system.

1. Navigate to the project folder and execute the following command:

   ```sh
   mvn -f pom.xml clean package
   ```
2. Transfer the ear artifact to the Wildfly Server:

   For MacOS asn Linux:

   ```sh
   cp hr-nexos-ear/target/hr-nexos-ear-1.0-SNAPSHOT.ear <SERVER-PATH>/wildfly-24/standalone/deployments/
   ```
   For Windows:
      ```sh
   copy hr-nexos-ear\target\hr-nexos-ear-1.0-SNAPSHOT.ear <SERVER-PATH>\wildfly-24\standalone\deployments\
   ```
   
   Replace <**SERVER-PATH**> with the path of the previously configured Wildfly Server.

3. **Starting the Wildfly Server**: First, you need to go to the Wildfly directory. Depending on your operating system, 
use the corresponding command to initiate the Wildfly server:

   For MacOS or Linux:
   ```sh
   ./wilfly-24/bin/standalone.sh
   ```
   For Windows:
   ```bash
   wilfly-24\bin\standalone.bat
   ```
   
4. **Accessing the Web Application**: Once the server is up and running, you can open the web application in your browser 
using the following link: `http://127.0.0.1:8080/hr-nexos-web/`.

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

## Application Showcase

Here’s a quick overview of the application:

1. Employee View

   * The following image presents the employee management view. Here, you can perform operations such as creating, 
   updating, and deleting one or multiple employees.
   
   <br>

   ![Employee Management](https://files-manager-jp.s3.us-east-2.amazonaws.com/employeeView.png)

   <br>

   * The subsequent image displays the employee details modal. This feature allows you to edit the current information
   of an individual employee.

   <br>

   ![Employee Details](https://files-manager-jp.s3.us-east-2.amazonaws.com/employeeDetails.png)

   <br>

2. Department view

   * The department management view, as shown in the next image, allows you to oversee all departments. Here, you can 
   perform various operations such as creating new departments, updating existing ones, or deleting departments as needed.
   
   <br>
   
   ![Department Management](https://files-manager-jp.s3.us-east-2.amazonaws.com/departmentsView.png)
