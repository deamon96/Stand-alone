package DAO;


import Utils.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalTime;


public class DeleteThenUpdate {

    public boolean deletethenUpdate(String id, LocalTime start, LocalTime end, String date, String type){

        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);
            stmt = conn.createStatement();


            /*String deleteQuery = "DELETE FROM dbEsame.Aule WHERE datapr='" + date + "' AND((inizio<='" + start +
                    "' AND fine>='" + start + "' AND ID !='" + id + "')"
                    + " OR(fine>='" + end + "' AND inizio<='" + end + "'AND ID !='" +id +"') " +
                    " OR(inizio>='" + start + "' AND fine<='" + end + "'AND ID !='" +id +"')"
                    + " OR(inizio<='" + start + "'AND fine>='" + end + "'AND ID !='" +id +"'))";*/

            String deleteQuery = String.format(Query.delete_deleteThenUpdate, date, start, start,
                    end, end, start, end);

            /*String modify = "UPDATE Aule SET inizio='" + start + "', fine='" + end + "', datapr='" + date + "', tipopr='"
                    + type + "' WHERE ID='" + id + "'";*/

            String modify = String.format(Query.modify, start, end, date, type, id);

            /*String modify = String.format(Query.modify, start, end, date, type, id);*/

            stmt.executeUpdate(deleteQuery);
            stmt.executeUpdate(modify);
            stmt.close();


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
