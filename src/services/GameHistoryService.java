package services;

import database.DatabaseManager;
import models.GameHistory;
import models.HighScore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class GameHistoryService {

    //field
    private DatabaseManager databaseManager;

    //constructor
    public GameHistoryService(){
        databaseManager = new DatabaseManager();
    }

    //save game result
    public boolean saveGame(GameHistory gameHistory){

        String sql = "INSERT INTO game_history(user_id, level, score, music_on, " +
                "shot_sound_on, crash_sound_on, gameover_sound_on) " +
                "VALUES(?,?,?,?,?,?,?)";

        try(Connection connection = databaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, gameHistory.getUserId());
            statement.setInt(2, gameHistory.getLevel());
            statement.setInt(3, gameHistory.getScore());
            statement.setBoolean(4, gameHistory.isMusicOn());
            statement.setBoolean(5, gameHistory.isShotSoundOn());
            statement.setBoolean(6, gameHistory.isCrashSoundOn());
            statement.setBoolean(7, gameHistory.isGameOverSoundOn());

            int rows = statement.executeUpdate();

            return rows > 0;

        }catch (SQLException e){

            e.printStackTrace();

        }
        return false;
    }

    //get high scores from game history
    public ArrayList<HighScore> getHighScores(){

        ArrayList<HighScore> highScores = new ArrayList<>();

        String sql = "SELECT users.username, MAX(game_history.score) AS high_score " +
                "FROM game_history " +
                "JOIN users ON users.id = game_history.user_id " +
                "GROUP BY users.username " +
                "ORDER BY high_score DESC " +
                "LIMIT 10";

        try(Connection connection = databaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){

            while(resultSet.next()){

                HighScore highScore = new HighScore(
                        resultSet.getString("username"),
                        resultSet.getInt("high_score")
                );

                highScores.add(highScore);
            }

        } catch(SQLException e){

            e.printStackTrace();

        }

        return highScores;
    }
}


