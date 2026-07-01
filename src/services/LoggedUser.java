package services;

import models.User;
//keep current user in the memory until the exit
public class LoggedUser {
    private static User user;

    //return logged-in user
    public static User getUser() {
        return user;
    }

    //save current user
    public static void setUser(User user) {
        LoggedUser.user = user;
    }
    //clear user from memory
    public static void logout() {
        user = null;
    }
}
