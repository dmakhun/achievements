### Web-project awarding achievements and points to participants of some academy.


Either of those can be used to launch an app:

- Manual start
  - install Mysql database, Maven `brew install mysql@5.7 maven`
  - start Mysql server `/usr/local/opt/mysql@5.7/bin/mysql.server start`
  - connect to Mysql server: `mysql -h localhost -u <YOUR_USER> -p`
  - create a database: `CREATE DATABASE achievements;`
  - set properties in `src/main/resources/project.properties` to your values:
    - `SPRING_DATASOURCE_DB_HOST` - localhost
    - `SPRING_DATASOURCE_USERNAME` - your DB username
    - `SPRING_DATASOURCE_PASSWORD` - your DB password
  - insert test roles/users `mysql -h 127.0.0.1 -P 3306 -u <YOUR_USER> -p -D achievements < config/fillDB.sql`
  - run Intellij maven run configuration `Achievements` to start an app on a Tomcat or in a command-line
    `mvn tomcat7:run`
- Build and run on Docker (the simplest/slowest way)
  - install Docker `brew cask install docker`
  - build an artifact and create a Docker image of an app `docker build -t achievements -f docker/Dockerfile .`
  - create `docker/.env` file with contents:
    ```
    DB_USER=<YOUR_DB_USER>
    DB_PASSWORD=<YOUR_DB_PASSWORD>
    DB_ROOT_PASSWORD=<YOUR_DB_ROOT_PASSWORD>
    ```
  - run an app and the database by Docker compose `docker-compose -f docker/docker-compose.yml up`

After launching an app navigate to http://localhost:8080/achievements

Code is formatted with slightly modified default Intellij code style config, can be imported
from `config/intellij-code-style.xml`