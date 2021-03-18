# RawDataHandlingModule
A Simple Spring MVC Web Application which allows user to Upload, View and Download  Zip Files (May be Downloaded individually or the
Whole File)on MYSQL database .This Application Uses Spring JdbcTemplate to handle Database Operations .


**STEPS TO SETUP**
1. Download the Project

2. Import the project to IDE

3. create a MYSQL database named "rawdata_mvc"

4. create a table with following command:-

    CREATE TABLE `zipfiles` 
    (
  
    `id` int NOT NULL AUTO_INCREMENT,
  
    `name` varchar(50) DEFAULT NULL,
  
    `filedata` longblob,
  
    PRIMARY KEY (`id`)
  
    );


5. open "/src/main/java/database.properties" and 

   ->set "mysql.userName" as YOUR database user name
   
   ->set "mysql.password" as YOUR database password
   
   
6. Right Click on project-> SELECT Targete Runtimes -> SELECT Apache Tomcat

7. Run Project on Server !
