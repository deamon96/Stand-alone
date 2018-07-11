package DAO;

import Bean.RoomBean;
import Utils.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class All_Rooms {

    public static ArrayList<RoomBean> AllRooms(){

        ArrayList<RoomBean> room = new ArrayList<>();
        Statement stmt = null;
        Connection conn = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

            stmt = conn.createStatement();

            String query = String.format(Query.allRooms);

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {

                //Room rooms = new Room();
                RoomBean rooms = new RoomBean();
                rooms.setNome(rs.getString("nome"));
                room.add(rooms);
            }
            stmt.close();

        }catch (Exception e){
            System.err.println(e);
        }

        return room;
    }

}
