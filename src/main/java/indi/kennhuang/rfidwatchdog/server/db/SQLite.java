package indi.kennhuang.rfidwatchdog.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite {
    protected static Connection connection = null;
    protected static Statement statement = null;

    public static void openDatabase(String url) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        try {
            // create a database connection
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` string, `groups` string, `uid` TEXT, `doors` TEXT, `metadata` TEXT )");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS groups " +
                    "( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT, `doors` TEXT )");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS doors " +
                    "( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT, `auth_token` TEXT )");
            connection.commit();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Statement getStatement(){
        return statement;
    }

    public static Connection getConnection(){
        return connection;
    }
}
