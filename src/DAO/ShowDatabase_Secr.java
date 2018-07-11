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

public class ShowDatabase_Secr {

    public static ArrayList<Room> show_secr() {

        ArrayList<Room> Classrooms = new ArrayList<>();

        User user = UserSingleton.getInstance().getUser();

        Statement stmt = null;
        Connection conn = null;


        //-----------------MODIFICABILITÀ DELLE CELLE

        /*tableUser.setEditable(true);
        columnOra.setCellFactory(TextFieldTableCell.forTableColumn());
        columnData.setCellFactory(TextFieldTableCell.forTableColumn());
        columnTipo.setCellFactory(TextFieldTableCell.forTableColumn());
        columnStato.setCellFactory(TextFieldTableCell.forTableColumn());*/

        //---------------------------------------------

        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

            stmt = conn.createStatement();

            String completeDB = String.format(Query.completeDB, user.getUsername());
            ResultSet rs = stmt.executeQuery(completeDB);
            while (rs.next()) {
                Room room = new Room();

                room.setNome(rs.getString("nome"));
                room.setTipopr(rs.getString("tipopr"));
                room.setDatapr(rs.getString("datapr"));
                room.setInizio(rs.getString("inizio"));
                room.setFine(rs.getString("fine"));
                room.setFromp(rs.getString("fromp"));
                Classrooms.add(room);
            }
            stmt.close();
        } catch (Exception e) {
            System.err.println("Error" + e);
        }

        return Classrooms;

    }
}


