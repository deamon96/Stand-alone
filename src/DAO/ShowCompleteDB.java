package DAO;

import Entity.Room;
import Entity.User;
import Utils.Query;
import Utils.UserSingleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ShowCompleteDB {

    public static ArrayList<Room> show_completeDB(){

        User user = UserSingleton.getInstance().getUser();

        Statement stmt = null;
        Connection conn = null;

        ArrayList<Room> rooms = new ArrayList<>();

        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(String.format(Query.completeDB, user.getUsername()));
            while (rs.next()){

                Room room = new Room();

                room.setNome(rs.getString("nome"));
                room.setTipopr(rs.getString("tipopr"));
                room.setDatapr(rs.getString("datapr"));
                room.setInizio(rs.getString("inizio"));
                room.setFine(rs.getString("fine"));
                room.setSessione(rs.getString("sessione"));
                room.setID(rs.getInt("ID"));

                rooms.add(room);
            }

            stmt.close();
            }catch (Exception e) {
            System.err.println("Error" + e);
        }

        return rooms;
    }

}
