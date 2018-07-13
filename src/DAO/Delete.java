package DAO;

import Bean.UserBean;
import Control.Controller;
import Entity.User;
import Utils.Query;
import Utils.UserSingleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Delete {

    public boolean delete(String ID){

        Controller controller = new Controller();
        User user = UserSingleton.getInstance().getUser();
        UserBean userBean = new UserBean();
        String nome = null;
        String data = null;
        String inizio = null;
        String fine = null;

        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

            stmt = conn.createStatement();


            String emailInfo = String.format(Query.emailInfo, ID); //Recupero i dati dell'utente a cui è stata eliminata la prenotazione

            ResultSet resultSet = stmt.executeQuery(emailInfo);

            if (resultSet.next()){

                userBean.setName(resultSet.getString("Name"));
                userBean.setSurname(resultSet.getString("Surname"));
                userBean.setEmail(resultSet.getString("Email"));
                userBean.setUsername(resultSet.getString("Username"));
            }

            String sql = String.format(Query.delete, ID);   //Cancello l'aula, la query non è una DELETE ma un UPDATE perchè se uso DELETE cancello l'aula dal DB
                                                            //invece la prenotazione va "svuotata" in modo che l'aula rimane nel DB ed è possibile prenotarla di nuovo
            String classInformation = String.format(Query.classInformation, ID);

            ResultSet info = stmt.executeQuery(classInformation); //Recupero i dati dell'aula eliminata

            while (info.next()){
                nome = info.getString("nome");
                data = info.getString("datapr");
                inizio = info.getString("inizio");
                fine = info.getString("fine");

            }

            stmt.executeUpdate(sql);

            String deleteInformation = null;


           /* if (userBean.getUsername().equals(user.getUsername())){

                deleteInformation = "Signor " + user.getName() + " " + user.getSurname() + " la richiesta di eliminazione " +
                        "della sua prenotazione per l'" + nome + " nel giorno " + data + "dalle ore " + inizio +
                        " alle ore " + fine + " è stata effettuata con successo";

                controller.sendEmail(userBean.getEmail(), "Eliminazione Effettuata", deleteInformation);
                return true;
            }
            deleteInformation = "Signor " + userBean.getName() + " " + userBean.getSurname() +  " la prenotazione da " +
                    "lei inserita per l'" + nome + " nel giorno " + data +
                    " dalle ore " + inizio + " alle ore " + fine + " è stata " +
                    " eliminata da " + user.getName() + " " + user.getSurname();

          //  controller.sendEmail(userBean.getEmail(), "Eliminazione Effettuata", deleteInformation);*/

            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
