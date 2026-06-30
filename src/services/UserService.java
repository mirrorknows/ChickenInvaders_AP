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

        Connection connection = databaseManager.getConnection();
        String sql = "SELECT * FROM users WHERE username = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return true;
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

        Connection connection = databaseManager.getConnection();

        String sql = "INSERT INTO users(username, password) VALUES(?,?)";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            int rows = statement.executeUpdate();
            if(rows > 0){
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //check username and password
    public User loginUser(String username, String password){
        Connection connection = databaseManager.getConnection();

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                User user = new User(
                        resultSet.getString("username"), resultSet.getString("password"),
                        resultSet.getInt("id"), resultSet.getInt("high_score"),
                        resultSet.getInt("last_level"), resultSet.getBoolean("music_on"),
                        resultSet.getBoolean("shot_sound_on"), resultSet.getBoolean("crash_sound_on"),
                        resultSet.getBoolean("gameover_sound_on")
                );
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    //update user high score
    public void updateHighScore(String username, int newHighScore){

        Connection connection = databaseManager.getConnection();

        String sql = "UPDATE users SET high_score = ? WHERE username = ?";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, newHighScore);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

}
