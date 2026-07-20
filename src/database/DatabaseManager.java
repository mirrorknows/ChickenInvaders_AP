package database;

import java.sql.*;

//manage database connection and tables
public class DatabaseManager {

    public Connection getConnection() throws SQLException {

        Connection connection =
                DriverManager.getConnection("jdbc:sqlite:game.db");

        try(Statement statement = connection.createStatement()){

            statement.execute("PRAGMA foreign_keys = ON");
        }

        return connection;
    }

    //create database tables
    public void createTables(){

        String usersTable = "CREATE TABLE IF NOT EXISTS users(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "high_score INTEGER DEFAULT 0, " +
                "last_level INTEGER DEFAULT 1, " +
                "music_on INTEGER DEFAULT 1, " +
                "shot_sound_on INTEGER DEFAULT 1, " +
                "crash_sound_on INTEGER DEFAULT 1, " +
                "gameover_sound_on INTEGER DEFAULT 1, " +
                "selected_plane TEXT DEFAULT 'Default'" +
                ")";

        String gameHistoryTable = "CREATE TABLE IF NOT EXISTS game_history(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "level INTEGER NOT NULL, " +
                "score INTEGER NOT NULL, " +
                "played_time DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "music_on INTEGER DEFAULT 1, " +
                "shot_sound_on INTEGER DEFAULT 1, " +
                "crash_sound_on INTEGER DEFAULT 1, " +
                "gameover_sound_on INTEGER DEFAULT 1, " +
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
                ")";

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()){

            statement.execute(usersTable);
            statement.execute(gameHistoryTable);

        } catch(SQLException e){

            e.printStackTrace();

        }
    }
}
