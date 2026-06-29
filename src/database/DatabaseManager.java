package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;

    public void connect() {
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:game.db");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
