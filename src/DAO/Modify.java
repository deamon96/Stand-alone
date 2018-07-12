package DAO;

import Utils.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;

public class Modify {

    public boolean modify(String idVecchio, LocalTime start, LocalTime end, String date, String type) {

        /*String modify = "UPDATE Aule SET inizio='" + start + "', fine='" + end + "', datapr='" + date + "', tipopr='"
                + type + "' WHERE ID='" + id + "'";*/
        String modify = String.format(Query.modify, start, end, date, type, idVecchio);

            try {

                Statement stmt = null;
                Connection conn = null;

                Class.forName("com.mysql.jdbc.Driver");

                conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

                stmt = conn.createStatement();

                String controlQuery = String.format(Query.duplicateControl_Modify, date, idVecchio, start, start,
                        end, end, start, end);

                System.out.println(controlQuery);

                ResultSet control = stmt.executeQuery(controlQuery);

                if (control.next()) {
                    return false;
                }

                stmt.executeUpdate(modify);
                stmt.close();

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        return true;
    }
}
