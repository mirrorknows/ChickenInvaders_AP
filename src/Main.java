import database.DatabaseManager;
import ui.LoginFrame;
import ui.RegisterFrame;


public class Main {
    public static void main(String[] args){

        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.createTables();

        new LoginFrame();

    }
}