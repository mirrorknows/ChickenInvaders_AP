package services;

import database.DatabaseManager;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//UserService manages user data

public class UserService {

    private DatabaseManager databaseManager;

    public UserService(){
        databaseManager = new DatabaseManager();
    }

    //check if user exists
    public boolean userExist(String username){

        String sql = "SELECT * FROM users WHERE username = ?";

        try(Connection connection = databaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, username);

            try(ResultSet resultSet = statement.executeQuery()){

                return resultSet.next();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    //add a new user to database
    public boolean registerUser(User user) {

        if(userExist(user.getUsername())){
            return false;
        }

        String sql = "INSERT INTO users(username, password) VALUES(?,?)";

        try(Connection connection = databaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //check username and password
    public User loginUser(String username, String password){

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try(Connection connection = databaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, username);
            statement.setString(2, password);

            try(ResultSet resultSet = statement.executeQuery()){

                if(resultSet.next()){

                    User user = new User(
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getInt("id"),
                            resultSet.getInt("high_score"),
                            resultSet.getInt("last_level"),
                            resultSet.getBoolean("music_on"),
                            resultSet.getBoolean("shot_sound_on"),
                            resultSet.getBoolean("crash_sound_on"),
                            resultSet.getBoolean("gameover_sound_on")
                    );

                    return user;
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;
    }

    //update high score
    public void updateHighScore(String username, int newHighScore){

        String sql = "UPDATE users SET high_score = ? WHERE username = ? AND high_score < ?";

        try(Connection connection = databaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, newHighScore);
            statement.setString(2, username);
            statement.setInt(3, newHighScore);

            statement.executeUpdate();
        } catch (SQLException e){

            e.printStackTrace();

        }
    }

    //update sound settings
    public void updateSoundSettings(String username, boolean musicOn, boolean shotSoundOn,
                                    boolean crashSoundOn, boolean gameOverSoundOn){
        String sql = "UPDATE users SET music_on = ?, shot_sound_on = ?, " +
                "crash_sound_on = ? , gameover_sound_on = ? WHERE username = ?";
        try(Connection connection = databaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setBoolean(1, musicOn);
            statement.setBoolean(2, shotSoundOn);
            statement.setBoolean(3, crashSoundOn);
            statement.setBoolean(4, gameOverSoundOn);
            statement.setString(5,username);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
