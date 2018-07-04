package Boundary;

import Bean.LoginBean;
import DAO.DB_Connection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import Control.Controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginControllerGUI extends Application implements Initializable{
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordPF;
    @FXML
    private Button loginB;
    @FXML
    private Button esciB;
    @FXML
    private Label alert;

    public void start(Stage primaryStage) throws Exception{
        //metodo della classe astratta Application.


        istanziaLoginGUI(primaryStage);
        //istanzia il controller grafico LoginGUI dell'interfaccia grafica LoginGUI.fxml.
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try{
                    DB_Connection.conn_Aule.close();
                    System.out.print("Connessione al database chiusa. Classroom Stand Alone terminata.");
                }catch(java.lang.NullPointerException | SQLException e){
                    System.out.println("Classroom Stand Alone stopped.");
                }}});
    }

    public void istanziaLoginGUI(Stage Stage) throws Exception{
        //Lancia l'interfaccia grafica LoginGUI.fxml.

        Parent root = FXMLLoader.load(getClass().getResource("/boundary/LoginGUI.fxml"));
        Stage.setTitle("AppStar");
        Stage.setScene(new Scene(root, 800, 450));
        //Imposta il root relativo alla schermata di Login.
        Stage.show();
    }

    public void istanziaLoginGUI(Event e){
        //Lancia l'interfaccia grafica LoginGUI.fxml.
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/LoginGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
            //Imposta il root relativo alla schermata di Login.
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources){

            alert.setVisible(false);
            Controller loginController = new Controller();

            // controlla le credenziali inserite
            loginB.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    LoginBean loginBean = new LoginBean();
                    loginBean.setUsername(usernameTF.getText());
                    loginBean.setPassword(passwordPF.getText());
                    loginBean.validate();
                    System.out.println(loginBean.getUsername());
                    if (loginBean.validate()){
                        SecretaryPageControllerGUI sPageController = new SecretaryPageControllerGUI();
                        sPageController.istanziaSPageGUI(event);
                    } else {
                        alert.setVisible(true);
                    }
                }});

            // termina l'applicazione
            esciB.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });
    }
}
