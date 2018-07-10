package DAO;

import Bean.AccademicYearBean;
import Utils.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Show_Acc_Year {

    public ArrayList<AccademicYearBean> show() {

        ArrayList<AccademicYearBean> year = new ArrayList<>();

        try {

            Statement stmt = null;
            Connection conn = null;

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM anni_accademici");

            while (rs.next()) {

                AccademicYearBean accademic_yearBean = new AccademicYearBean();

                accademic_yearBean.setDataInizio(LocalDate.parse(rs.getString("datainizio")));
                accademic_yearBean.setDataFine(LocalDate.parse(rs.getString("datafine")));
                accademic_yearBean.setNome(rs.getString("nome"));
                year.add(accademic_yearBean);
            }

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return year;
    }
}
