
 Liferay example
====================

A sample project that shows how to prepare a Liferay portal on the Docker platform


 Prerequisities
----------------

- [Docker installed](https://docs.docker.com/engine/installation/)
    - needed for running Liferay locally in the container


 Workspace Setup
-----------------

1. Create `docker-compose.override.yml` file in the same directory in which `docker-compose.yml` is.
    - mount folder with Liferay logs to your local file system. Exampe of this file is below, remember to change path to your system path.

```
version: '3'

services:

  portal:
    volumes:
      - ./deploy:/opt/liferay/deploy
      - c:/docker/example/logs/:/opt/liferay/logs/
```


 Build
-----------------

### Building with Docker

When you run `docker compose up --build -d` the whole project is built within the docker and local Liferay is started.

### Running local Liferay Tomcat bundle

You can find local Liferay bundle in the `/opt/liferay` folder in the portal container. To run the portal run `docker compose start portal`.

Portal could be shut down by `docker compose stop portal` script.

**Liferay HTTP endpoint** runs on **port `8080`**.

### Debugging running portal

By default, running local Tomcat could be connected to **remote debugger via JDWP** on **port `8000`**.

You can also use **remote JMX** connection to VM on **port `8099`**.

**Gogo shell** is accessible on **port `11311`**.

### Logs

All Tomcat and Liferay logs are configured to follow the same timestamp pattern and for the local environment are stored in `/opt/liferay/logs` directory in the container.

### Default Admin account

Portal is initialized with following admin user (screenname as login):

* screenname: `admin`
* password: `admin`

### Connecting to portal database

Portal database runs in docker container and is available on your system:

* url: `jdbc:postgresql://localhost:5432/liferay`
* username: `liferay`
* password: `liferay`

By default schema `liferay` is used. By default database is empty and Liferay tables gets autocreated. 

### Connecting to portal docker image

You can connect do running portal docker image using the following command. 
Run it in the terminal in the folder where docker-compose is located: `docker-compose exec -ti portal /bin/bash` 
