package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB_Connection {

    public static Connection conn_Aule;
    public Connection connect_Aule() {

        final String url = "jdbc:mysql://localhost:3306/dbEsame";
        final String user = "root";
        final String password = "password";

            try{
                Class.forName("com.mysql.jdbc.Driver");
                DB_Connection.conn_Aule = DriverManager.getConnection(url, user, password);
                System.out.println("CONNESSO");
            }catch (Exception e){
                Logger.getLogger(DB_Connection.class.getName()).log(Level.SEVERE, null, e);
            }
            return conn_Aule;
    }
}
