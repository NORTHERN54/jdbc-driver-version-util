package com.wkh;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SqlServerDriverVersionUtil {

    public static void main(String[] args) {
        System.out.println("Started AzureSQL database jdbc-driver compatibility test.");

        String javaVersion = System.getProperty("java.version");
        System.out.println("java version: " + javaVersion);
        if (!javaVersion.contains("1.5")) {
            throw new IllegalArgumentException("Wrong java version " + javaVersion
                    + "IMPORTANT: The solution for JRE 1.5u22 is needed.");
        }

        Connection conn = null;
        try {
            InputStream is = SqlServerDriverVersionUtil.class.getClassLoader().getResourceAsStream("config.properties");
            Properties config = new Properties();
            config.load(is);

            String driverClassName = config.getProperty("jdbc.driver.classname", DRIVER_CLASS_NAME);
            System.out.println("JDBC driver class name: " + driverClassName);

            String connectionUrl = config.getProperty("connection.url");
            if (args.length > 0) {
                connectionUrl = args[0];
            }
            System.out.println("Connection url: " + connectionUrl);

            SqlServerDriverVersionUtil check = new SqlServerDriverVersionUtil();
            conn = check.createDbConnection(driverClassName, connectionUrl);

            System.out.println("Finished AzureSQL database jdbc-driver compatibility test.");

        } catch (IOException exc) {
            exc.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception exc) {
                // can not happen, nothing to-do
            }
        }
    }

    private static final String DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    /**
     * Creates a connection
     *
     * @param driverClassName
     * @param connectionUrl
     * @return the connection.
     * @exclude This is for internal use only.
     */
    protected java.sql.Connection createDbConnection(String driverClassName, String connectionUrl) {
        Connection conn = null;
        try {
            Class.forName(driverClassName);
            conn = DriverManager.getConnection(connectionUrl);
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        } catch (SQLException exception1) {
            throw new RuntimeException(exception1);
        }
        return conn;
    }
}