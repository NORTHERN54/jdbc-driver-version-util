# jdbc-driver-version-util

## JRE / JDK
JRE 1.5.0.22 is required to run app

## How to start application from command line:
%JAVA_HOME%\bin\java.exe -jar JdbcDriverVersionUtil.jar

e.g. C:\jdk1.5.0_22\bin\java.exe -jar JdbcDriverVersionUtil.jar

## How to start application from command line with connection string as parameter:
%JAVA_HOME%\bin\java.exe -jar JdbcDriverVersionUtil.jar [jdbc:sqlserver://url]

e.g.  C:\jdk1.5.0_22\bin\java.exe -jar JdbcDriverVersionUtil.jar jdbc:sqlserver://zu***.windows.net:1433;databaseName=M***;user=[type user name];password=[type password];encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.4f4bacceafad.database.windows.net;