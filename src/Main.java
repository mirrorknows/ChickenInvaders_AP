import database.DatabaseManager;
import ui.LoginFrame;

public class Main {
    public static void main(String[] args){

        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.createTables();

        new LoginFrame();

    }
}