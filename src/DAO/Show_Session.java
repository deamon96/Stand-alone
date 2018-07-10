package DAO;

import Bean.SessionBean;
import Utils.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Show_Session {

    public ArrayList<SessionBean> showAllSession(){

        ArrayList<SessionBean> sessions = new ArrayList<>();

        try {

            Statement stmt = null;
            Connection conn = null;

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM sessioni");

            while (rs.next()){

                SessionBean sessionBean = new SessionBean();

                sessionBean.setDataInizio(rs.getString("DataInizio"));
                sessionBean.setDataFine(rs.getString("DataFine"));
                sessionBean.setTipo(rs.getString("Tipo"));

                sessions.add(sessionBean);

            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return sessions;
    }
}
