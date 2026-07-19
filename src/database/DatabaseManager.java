package database;

import java.sql.*;

//manage database connection
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
                "gameover_sound_on INTEGER DEFAULT 1," +
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

            addSelectedPlaneColumn(connection);

        } catch(SQLException e){

            e.printStackTrace();

        }
    }


    //add selected plane col
    private void addSelectedPlaneColumn(Connection connection)
            throws SQLException {

        boolean columnExists = false;

        //get info users info col
        String sql = "PRAGMA table_info(users)";

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            //search for selected_plane
            while(resultSet.next()) {

                String columnName = resultSet.getString("name");

                if(columnName.equals("selected_plane")) {
                    columnExists = true;
                    break;
                }
            }
        }

        //add the col if it does not exist
        if(!columnExists) {

            String addColumnSql =
                            "ALTER TABLE users " +
                            "ADD COLUMN selected_plane TEXT DEFAULT 'Default'";

            try(Statement statement = connection.createStatement()) {

                statement.execute(addColumnSql);
            }
        }
    }
}
