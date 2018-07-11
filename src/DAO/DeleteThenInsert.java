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

public class DeleteThenInsert {

    public boolean deleteThenInsert(String nameAula, String tipoPrenota, String dataPrenota, LocalTime timeInizioPrenota,
                                    LocalTime timeFinePrenota, String sessione, String from){

        User user = UserSingleton.getInstance().getUser();

        Controller controller = new Controller();

        Statement stmt = null;
        Connection conn = null;

        try {

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

            //--------------TROVO GLI UTENTI A CUI SONO VERRANNO CANCELLATE LE PRENOTAZIONI-------------------//

            String us = String.format(Query.emailInfo_deleteThenInsert, dataPrenota, nameAula, timeInizioPrenota, timeInizioPrenota,
                    timeFinePrenota, timeFinePrenota, timeInizioPrenota, timeFinePrenota);

            ResultSet rs1 = stmt.executeQuery(us);

            while (rs1.next()){

                String Nome = rs1.getString("Name");
                String Cognome = rs1.getString("Surname");
                String email = rs1.getString("Email");
                String nomeAula = rs1.getString("nome");
                String data = rs1.getString("datapr");
                String inizio = rs1.getString("inizio");
                String fine = rs1.getString("fine");

                String testo = "Signor " + Nome + " " + Cognome + " la prenotazione da " +
                        "lei inserita per l'" + nomeAula + " nel giorno " + data +
                        " dalle ore " + inizio + " alle ore " + fine + " è stata " +
                        " eliminata. Rivolgersi alla segreteria per maggiori informazioni";


                controller.sendEmail(email,"Prenotazione Eliminata", testo);
            }


            //----------------DUPLICATE ENTRY: SI CANCELLANO LE AULE CHE DANNO FASTIDIO ALLA NUOVA

            /*String deleteSecretary = "DELETE FROM dbEsame.Aule WHERE (datapr='" + dataPrenota + "' AND nome='" + nameAula + "')" +
                    "AND ((inizio<='" + timeInizioPrenota + "' AND fine>='" + timeInizioPrenota + "') " +
                    "OR (fine>='" + timeFinePrenota + "' AND inizio<='" + timeFinePrenota + "') " +
                    "OR (inizio>='" + timeInizioPrenota + "' AND fine<='" + timeFinePrenota + "') " +
                    "OR ((inizio<='" + timeInizioPrenota + "' AND fine>='" + timeInizioPrenota + "')" +
                    " AND (fine>='" + timeFinePrenota + "' AND inizio<='" + timeFinePrenota + "')))";*/

            String deleteSecretary = String.format(Query.deleteSecretary, dataPrenota, nameAula, timeInizioPrenota, timeInizioPrenota,
                    timeFinePrenota, timeFinePrenota, timeInizioPrenota, timeFinePrenota);

            System.out.println(deleteSecretary);

            //@TODO questa query è stata modificata rispetto alla Stand-Alone BISONGA MODIFCARLA IN ESSA!!!


            stmt.executeUpdate(deleteSecretary);

            //----------------INSERIMENTO PRENOTAZIONE SEGRETARIA-----------------------//

            String insertSecretary = String.format(Query.insert, nameAula, tipoPrenota, dataPrenota, timeInizioPrenota, timeFinePrenota, user.getUsername(),from, sessione);
            stmt.executeUpdate(insertSecretary);

            stmt.close();

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
