package DAO;

import Control.Controller;
import Entity.User;
import Utils.Query;
import Utils.UserSingleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalTime;


public class Insert_Prof {

    public boolean insert(String nameAula, String tipoPrenota, String dataPrenota, LocalTime timeInizioPrenota,
                          LocalTime timeFinePrenota, String sessione) {

        User user = UserSingleton.getInstance().getUser();
        Controller controller = new Controller();

        try {

            Statement stmt = null;
            Connection conn = null;

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);
            stmt = conn.createStatement();

            if (controller.emptyControl(nameAula)) {

                String del = String.format(Query.deleteEmpty, nameAula);
                stmt.executeUpdate(del);
            }

            String insertProf = String.format(Query.insert, nameAula, tipoPrenota, dataPrenota, timeInizioPrenota, timeFinePrenota, user.getUsername(), sessione);

            /*String PrenotationInfo = "Signor " + user.getName() + " " + user.getSurname() + " la prenotazione da " +
                    "lei inserita per l'" + nameAula + " nel giorno " + dataPrenota +
                    " dalle ore " + timeInizioPrenota + " alle ore " + timeFinePrenota + " è stata " +
                    " inserita con successo! ";

            controller.sendEmail(user.getMail(), "Prenotazione effettuata", PrenotationInfo);*/
            stmt.executeUpdate(insertProf);
            stmt.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
