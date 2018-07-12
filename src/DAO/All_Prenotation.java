package DAO;

import Entity.Room;
import Utils.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class All_Prenotation {

    public static ArrayList<Room> showAll(){

        /*DB_Connection connection = new DB_Connection();
        Connection connection2 = connection.connect_Aule();*/

        ArrayList<Room> room = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

            stmt = conn.createStatement();

            String query = String.format(Query.allPrenotation);

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {

                Room rooms = new Room();

                rooms.setNome(rs.getString("nome"));
                rooms.setTipopr(rs.getString("tipopr"));
                rooms.setDatapr(rs.getString("datapr"));
                rooms.setInizio(rs.getString("inizio"));
                rooms.setFine(rs.getString("fine"));
                rooms.setFromp(rs.getString("fromp"));
                rooms.setID(rs.getInt("ID"));
                room.add(rooms);
            }
            stmt.close();

        }catch (Exception e){
                System.err.println(e);
            }

        return room;
    }
}
