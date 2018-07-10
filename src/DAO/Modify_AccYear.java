package DAO;

import Utils.Query;

import java.sql.*;

public class Modify_AccYear {

    public boolean modify(String newInizio, String newFine, String oldInizio, String oldFine){

        try {

            Statement stmt = null;
            Connection conn = null;

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Query.DB_URL, Query.USER, Query.PASS);

            stmt = conn.createStatement();

            //----------------Controllo che non ci siano sessioni che rimangano fuori dall'anno accademico con le nuove date-------//

            String control = String.format(Query.modifyAccYear_control, oldInizio, oldFine, oldInizio, oldFine);

            ResultSet rs = stmt.executeQuery(control);

            if (rs.next()){
                return false;  //Ci sono sessioni fuori dalle date del nuovo anno accademico (Modifica Negata)
            }

            String nome = newInizio.substring(0,4)+"/"+newFine.substring(0,4);

            String modify = String.format(Query.modifyAccYear, newInizio, newFine, nome, oldInizio.substring(0,4)+"/"+oldFine.substring(0,4));

            stmt.executeUpdate(modify);

        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

}
