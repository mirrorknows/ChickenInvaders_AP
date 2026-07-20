package services;

import models.GameHistory;
import models.User;

//saves the final game result
public class GameResultService {

    private GameHistoryService gameHistoryService;
    private UserService userService;

    public GameResultService(){

        gameHistoryService = new GameHistoryService();
        userService = new UserService();

    }

    //save history and update high score
    public boolean saveGameResult(int level, int score){

        User user = LoggedUser.getUser();

        if(user == null){
            return false;
        }

        //create the game history record
        GameHistory gameHistory = new GameHistory(
                0,
                user.getId(),
                level,
                score,
                "",
                user.isMusicOn(),
                user.isShotSoundOn(),
                user.isCrashSoundOn(),
                user.isGameOverSoundOn()
        );

        boolean saved = gameHistoryService.saveGame(gameHistory);

        if(!saved){
            return false;
        }
        //save the game history
        userService.updateLastLevel(user.getUsername(), level);

        user.setLastLevel(level);

        //update high score if the new score is higher
        if(score > user.getHighScore()){

            userService.updateHighScore(
                    user.getUsername(),
                    score
            );

            user.setHighScore(score);
        }

        return true;
    }
}
