package DAO;

import Utils.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;

public class EntryController {


    public boolean emptyController(String name){

        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);
            stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(String.format(Query.emptyControl, name));

            if (resultSet.next()){

                return true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean duplicateController(String name, String dataPrenota, LocalTime timeInizioPrenota, LocalTime timeFinePrenota){

        Statement stmt = null;
        Connection conn = null;

        //Controlla se ci sono aule che vanno in conflitto con i criteri inseriti

        /*String duplicateControl = "SELECT nome FROM dbEsame.Aule " + "WHERE datapr='" + dataPrenota + "' AND (inizio<='"
                + timeInizioPrenota + "' AND fine>='" + timeInizioPrenota + "' AND nome='" + name + "') " + "" + "OR (fine>='"
                + timeFinePrenota + "' AND inizio<='" + timeFinePrenota + "'AND nome='" + name + "') " + "OR (inizio>='"
                + timeInizioPrenota + "' AND fine<='" + timeFinePrenota + "' AND nome='" + name + "') " + "OR ((inizio<='"
                + timeInizioPrenota + "' AND fine>='" + timeInizioPrenota + "' AND nome='" + name + "') " + "AND (fine>='"
                + timeFinePrenota + "' AND inizio<='" + timeFinePrenota + "'AND nome='" + name + "'))";
*/
        /*String duplicateControl = "SELECT DISTINCT nome FROM dbEsame.Aule WHERE nome NOT IN (SELECT nome FROM dbEsame.Aule WHERE datapr='"
                +dataPrenota+"' AND ((inizio<'"+timeInizioPrenota+"' AND fine>'"+timeInizioPrenota+"') "+"" +
                "OR (fine>'"+timeFinePrenota
                +"' AND inizio<'"+timeFinePrenota+"') "+"OR (inizio>'"+timeInizioPrenota+"' AND fine<'"+timeFinePrenota+"')))";*/

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);
            stmt = conn.createStatement();

            String duplicateControl = String.format(Query.duplicateControl, dataPrenota, name, timeInizioPrenota, timeInizioPrenota,
                    timeFinePrenota, timeFinePrenota, timeInizioPrenota, timeFinePrenota);

            ResultSet resultSet = stmt.executeQuery(duplicateControl);
            if (resultSet.next()){
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


}
