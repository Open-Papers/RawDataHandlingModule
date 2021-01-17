# RawDataHandlingModule
A Simple Spring MVC Web Application which allows user to Upload, View and Download  Zip Files (May be Downloaded individually or the
Whole File)on MYSQL database .This Application Uses Spring JdbcTemplate to handle Database Operations .
NOTE: The Application uses a Directory to Store the ZipFiles before Extracting(in case user wants to download files individually)
      and are later deleted once Operation is complete

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

5. Create a Folder anywhere (For Storing Zip Files before Extraction)

6. open "/src/main/java/database.properties" and 

   ->set "mysql.userName" as YOUR database user name
   
   ->set "mysql.password" as YOUR database password
   
   ->set "folder.path" as PATH OF THE FOLDER that you just created
   
7. Right Click on project-> SELECT Targete Runtimes -> SELECT Apache Tomcat

8. Run Project on Server !
