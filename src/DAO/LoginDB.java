package DAO;

import Control.Controller;
import Entity.User;
import Utils.Query;

import java.sql.Connection;
import java.sql.ResultSet;

import static DAO.DB_Connection.conn_Aule;


public class LoginDB {

    public static User findByNameAndPassword(String u_name, String p) {
        // STEP 1: dichiarazioni
        java.sql.Statement stmt;
        //Connection conn = null;
        DB_Connection connection_aule;
        User u = null;
        try {

            connection_aule= new DB_Connection();
            Connection connection = connection_aule.connect_Aule();

            // STEP 2: loading dinamico del driver mysql
            //Class.forName("com.mysql.jdbc.Driver");

            // STEP 3: apertura connessione
            //conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 4: creazione ed esecuzione della query
            stmt = conn_Aule.createStatement();
            //String QUERY = "SELECT * FROM Users.users WHERE Username =" + "'" + u_name + "'" + " AND Password=" + "'" + p + "'";
            String QUERY = String.format(Query.login, u_name, p);
            ResultSet rs = stmt.executeQuery(QUERY);
            System.out.println(u_name+p);

            if(rs.next()){

                String nome = rs.getString("Name");
                String cognome = rs.getString(2);
                String usernameLoaded = rs.getString(3);
                String password = rs.getString(4);
                String type = rs.getString(5);
                String mail = rs.getString("Email");
                u = new User(nome, cognome, usernameLoaded, password, type, mail);
                Controller controller = new Controller();
                controller.createSingleton(u);
            }

        } catch (Exception e) {
            // Errore nel loading del driver
            e.printStackTrace();
        }
        return u;
    }

    /*public static Utente findByNameAndPasswordMockup(String username, String password) {
        if ("myusername".equals(username) && "mypassword".equals(password))
            return new Utente("myusername", "", "Tizio","Caio");
        else return null;
    }*/
}
