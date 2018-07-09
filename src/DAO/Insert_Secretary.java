package DAO;

import Control.Controller;
import Entity.User;
import Utils.Query;
import Utils.UserSingleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;

public class Insert_Secretary {

    public boolean insert(String nameAula, String tipoPrenota, String dataPrenota, LocalTime timeInizioPrenota,
                          LocalTime timeFinePrenota, String sessione, String from){

        User user = UserSingleton.getInstance().getUser();
        Controller controller = new Controller();

        try {

            Statement stmt = null;
            Connection conn = null;

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

            stmt = conn.createStatement();

            String retrieveInformation = String.format(Query.retrieveUsername, from);

            System.out.println(retrieveInformation);

            ResultSet rs = stmt.executeQuery(retrieveInformation); //Controllo che lo username inserito esista nel DB;

            ArrayList<String> info = new ArrayList<>();

            if (!rs.wasNull()){

                while (rs.next()){

                    info.add(0,rs.getString("Name"));
                    info.add(1,rs.getString("Surname"));
                    info.add(2,rs.getString("Email"));
                }

            }else return false; //Il professore inserito non esiste


            if (controller.emptyControl(nameAula)) {

                //Se esistono aule con valore nullo (cioè mai prenotate) cancella quell'entry nel db e
                //inserisce quella nuova, altrimenti
                //ce ne sarebbero state due nel DB!

                String del = String.format(Query.deleteEmpty, nameAula);
                stmt.executeUpdate(del);

                String insertSecretary = String.format(Query.insert, nameAula, tipoPrenota, dataPrenota, timeInizioPrenota, timeFinePrenota, from,sessione);

                stmt.executeUpdate(insertSecretary);
                String PrenotationInfo = "Signor " + info.get(0) + " " + info.get(1) +" la richiesta di prenotazione per l'"
                        + nameAula + " nel giorno " + dataPrenota +
                        " dalle ore " + timeInizioPrenota + " alle ore " + timeFinePrenota + " è stata " +
                        " inserita con successo dalla segreteria! ";

                //-------------Recupero dati dell'utente per cui è stata inserita la prenotazione----------------//


                controller.sendEmail(info.get(2), "Prenotazione effettuata", PrenotationInfo);
                stmt.close();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
