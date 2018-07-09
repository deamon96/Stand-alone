package DAO;

import Utils.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class NewAccYear {

    public boolean newYaear(String datainizio, String datafine) {

        try {

            Statement stmt = null;
            Connection conn = null;

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

            stmt = conn.createStatement();

            String nome = datainizio.substring(0,4)+"/"+datafine.substring(0,4);

            String query = String.format(Query.newAccYear, datainizio, datafine, nome);

            stmt.executeUpdate(query);


            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

}
