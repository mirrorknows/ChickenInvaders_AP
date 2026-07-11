package services;

import database.DatabaseManager;
import models.GameHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


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

}


