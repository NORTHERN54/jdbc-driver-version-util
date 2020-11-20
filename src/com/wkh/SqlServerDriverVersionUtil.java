package com.wkh;

import com.wkh.measurement.LoggerContext;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

public class SqlServerDriverVersionUtil {

    public static void main(String[] args) {
        System.out.println("Started AzureSQL database jdbc-driver compatibility test.");

        String javaVersion = System.getProperty("java.version");
        System.out.println("java version: " + javaVersion);
        if (!javaVersion.contains("1.5")) {
            throw new IllegalArgumentException(" Wrong java version " + javaVersion
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
            // Test-case #1
            conn = check.createDbConnection(driverClassName, connectionUrl);
            // Test-case #2
            check.executeQuery(driverClassName, connectionUrl, FAKE_SQL);
            // Test-case #3
            check.executeQuery(driverClassName, connectionUrl, WK_FAKE_SQL);

            System.out.println("OK. The connection is established.");
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

    private static final String FAKE_SQL = "SELECT CURRENT_TIMESTAMP";

    private static final String WK_FAKE_SQL = "SELECT 1 FROM MSC_QA51.dbo.MSC_A ORDER BY 1 offset 0 rows FETCH FIRST 1 ROWS ONLY";

    /**
     * Creates a connection
     *
     * @param driverClassName
     * @param connectionUrl
     * @return the connection.
     * @exclude This is for internal use only.
     */
    protected java.sql.Connection createDbConnection(String driverClassName, String connectionUrl) {
        LoggerContext.getInstance().start("createDbConnection");
        Connection conn = createDbConnectionImpl(driverClassName, connectionUrl);
        LoggerContext.getInstance().finish("createDbConnection", System.out);
        return conn;
    }

    /**
     * Creates a connection
     *
     * @param driverClassName
     * @param connectionUrl
     * @return the connection.
     * @exclude This is for internal use only.
     */
    private java.sql.Connection createDbConnectionImpl(String driverClassName, String connectionUrl) {
        Connection conn = null;
        try {
            Class.forName(driverClassName);
            conn = DriverManager.getConnection(connectionUrl);
        } catch (ClassNotFoundException exception) {
            System.out.println("FAILED.");
            throw new RuntimeException(exception);
        } catch (SQLException exception1) {
            System.out.println("FAILED.");
            throw new RuntimeException(exception1);
        }
        return conn;
    }

    /**
     * Creates a connection
     *
     * @param driverClassName
     * @param connectionUrl
     * @param aSql
     * @return the connection.
     * @exclude This is for internal use only.
     */
    protected void executeQuery(String driverClassName, String connectionUrl, String aSql) {
        LoggerContext.getInstance().start("executeQuery");
        Connection conn = createDbConnectionImpl(driverClassName, connectionUrl);
        executeQueryImpl(conn, aSql);
        LoggerContext.getInstance().finish("executeQuery", System.out);
    }

    /**
     * Execute query
     *
     * @param aConnection connection
     * @param aSql        SQL
     */
    private void executeQueryImpl(java.sql.Connection aConnection, String aSql) {
        try {
            Statement statement = aConnection.createStatement();
            ResultSet rs = statement.executeQuery(aSql);
        } catch (SQLException exception1) {
            System.out.println("FAILED.");
            throw new RuntimeException(exception1);
        }
    }
}