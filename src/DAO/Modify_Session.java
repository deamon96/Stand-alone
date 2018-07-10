package DAO;

import Control.Controller;
import Utils.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Modify_Session {

    public boolean modify(String newinizio, String newfine, String oldSession, String newTipo){

        Controller controller = new Controller();

        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

            stmt = conn.createStatement();

            //------------------Controllo che non ci siano conflitti con sessioni già esistenti------------//

            String controlDup = String.format(Query.duplicateSession, oldSession, newinizio, newinizio, newfine, newfine, newinizio, newfine);

            ResultSet rs = stmt.executeQuery(controlDup);

            if (rs.next()) {
                rs.close();
                return false;
            }

            //-------------Trovo le prenotazioni (ID) che sono fuori da ogni sessione---------------//

            String prenotationOutOfSession = String.format(Query.prenotationOutOfSession, oldSession);

            ResultSet resultSet = stmt.executeQuery(prenotationOutOfSession);

            ArrayList<Integer> id = new ArrayList<>();

            while (resultSet.next()) {

                id.add(resultSet.getInt("ID"));

            }
            ResultSet resultSet1 = null;

            //-------------------Trovo le credenziali di ogni utente a cui è sarà cancellata la prenotazione-----------------//
            //-------------------e invio una mail di notifica---------------------------------------------------------------//

            for (int j = 0; j<id.size(); j++){

                String emailInfo = String.format(Query.emailInfo, id.get(j));

                resultSet1=stmt.executeQuery(emailInfo);

                String nome;
                String cognome;
                String email;

                String ID = id.get(j).toString();

                while (resultSet1.next()){
                    nome = resultSet1.getString("Name");
                    cognome = resultSet1.getString("Surname");
                    email = resultSet1.getString("Email");

                    String testo = "Signor "+nome+" "+cognome + " la prenotazione numero " + ID + " da lei inserita è stata eliminata" +
                            " per una modifica effettuata alla sessione a cui apparteneva";

                    controller.sendEmail(email, "Prenotazione Eliminata", testo);
                }

            }

            //-------------Cancello le prenotazioni trovate---------------------------//

            for (int i = 0; i < id.size(); i++) {

                String deleteOutOfSession = String.format(Query.delete, id.get(i));

                stmt.executeUpdate(deleteOutOfSession);

            }

            //--------------Modifico la sessione-----------------//

            String modify = String.format(Query.modifySession, newinizio,newfine, newTipo, (newinizio+"/"+newfine), oldSession);
            stmt.executeUpdate(modify);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }
}
