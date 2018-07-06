package DAO;

import Bean.SessionBean;
import Utils.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Find_Session {

    public SessionBean find(String dataPrenotazione){
        SessionBean sessionBean = new SessionBean();

        try {

            Statement stmt = null;
            Connection conn = null;

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

            stmt = conn.createStatement();

            String query = String.format(Query.findSession, dataPrenotazione, dataPrenotazione);
            System.out.println(query);

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()){
                sessionBean.setDataInizio(rs.getString("DataInizio"));
                sessionBean.setDataFine(rs.getString("DataFine"));
                sessionBean.setTipo(rs.getString("Tipo"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return sessionBean;
    }
}
