package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//manage database connection
public class DatabaseManager {

    public Connection getConnection() {
       try {
           return DriverManager.getConnection("jdbc:sqlite:game.db");
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return null;
    }
}
