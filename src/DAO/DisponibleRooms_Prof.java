package DAO;

import Bean.Disponible_RoomBean;
import Utils.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

public class DisponibleRooms_Prof {

    public static Disponible_RoomBean show(LocalTime timeInizio, LocalTime timeFine, String dateSearch, String microfono,
                                           String proiettore, String lavagna, String lavElettronica, String ethernet, String presa, int posti){

        java.sql.Statement stmt = null;
        Connection conn = null;

        ArrayList<Disponible_RoomBean> Classrooms = new ArrayList<>();
        Disponible_RoomBean disponible_roomBean = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

            stmt = conn.createStatement();


            /*String query ="SELECT DISTINCT nome FROM dbEsame.Aule WHERE nome NOT IN (SELECT nome FROM dbEsame.Aule WHERE datapr='"
                    +dateSearch+"' AND ((inizio<='"+timeInizio+"' AND fine>='"+timeInizio+"') "+"OR (fine>='"+timeFine
                    +"' AND inizio<='"+timeFine+"') "+"OR (inizio>='"+timeInizio+"' AND fine<='"+timeFine+"')))";*/

            String query = String.format(Query.disponibleRooms, microfono, lavagna, lavElettronica,
                    proiettore, ethernet, presa, posti, dateSearch, timeInizio, timeInizio, timeFine, timeFine, timeInizio, timeFine);

            System.out.println("\n" + query);

            ResultSet rs = stmt.executeQuery(query);

            ArrayList<String> rooms = new ArrayList<>();
            while (rs.next()){
               rooms.add(rs.getString(1));
            }

            disponible_roomBean = new Disponible_RoomBean(rooms);

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return disponible_roomBean;
    }

    }

