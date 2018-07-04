package Utils;

import Entity.User;

public class UserSingleton {
    private static UserSingleton instance = null;
    private User user;

    protected UserSingleton() {
        // Exists only to defeat instantiation.
    }
    public static UserSingleton getInstance() {
        if(instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User u){
        user = u;
    }

}
