@echo off
@echo Test-case #1: Java5 + Azure SQL, expected result:FAILED ---------------------------------
e:\software\java\jdk1.5.0_22\bin\java.exe -jar .\JdbcDriverVersionUtil.jar jdbc:sqlserver://zuse2dmsblddbs01.4f4bacceafad.database.windows.net:1433;databaseName=MSC_QA51;user=MSC_User@zuse2dmsblddbs01;password=msspd1;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.4f4bacceafad.database.windows.net;loginTimeout=30;
@echo End of Test-case #1: --------------------------------------------------------------------
@echo ..
@echo ..
echo Test-case #2: Java5 + SQL Paas, expected result: PASSED ---------------------------
e:\software\java\jdk1.5.0_22\bin\java.exe -jar .\JdbcDriverVersionUtil.jar jdbc:sqlserver://zuse2sccdbs01.database.windows.net:1433;database=MSC_QA51;user=msc_user@zuse2sccdbs01;password=Ce3NuBuK;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;
echo End of Test-case #2: --------------------------------------------------------------------