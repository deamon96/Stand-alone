package Bean;

import Control.Controller;
import Entity.User;

public class LoginBean {

    public LoginBean() {
        this.username = "";
        this.password = "";
        /*this.name = name;
        this.surname = surname;
        this.type = type;*/
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String username;
    private String password;
    private String name;
    private String surname;
    private String type;

    public boolean validate() {
        // Controllo sintattico
        if (this.username.equals("") || this.password.equals("")) {
            return false;
        }

        Controller controller = new Controller();
        User found = controller.check(this.username, this.password);
        return  (found != null);
    }

}
