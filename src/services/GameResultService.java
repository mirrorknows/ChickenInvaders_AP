package services;

import models.GameHistory;
import models.User;

//final game result
public class GameResultService {

    private GameHistoryService gameHistoryService;
    private UserService userService;

    public GameResultService(){

        gameHistoryService = new GameHistoryService();
        userService = new UserService();

    }

    //save history and update high score
    public void saveGameResult(int level, int score){

        User user = LoggedUser.getUser();

        if(user == null){
            return;
        }

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

        gameHistoryService.saveGame(gameHistory);

        if(score > user.getHighScore()){

            userService.updateHighScore(user.getUsername(), score);
        }
    }
}
