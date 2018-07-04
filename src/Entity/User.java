package Entity;

public class User {

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public User(String name, String surname, String username, String password, String type, String mail) {
        this.Name = name;
        this.Surname = surname;
        this.Username = username;
        this.Password = password;
        this.Type = type;
        this.Mail = mail;
    }

    private String Name;
    private String Surname;
    private String Username;
    private String Password;
    private String Type;
    private String Mail;






}
