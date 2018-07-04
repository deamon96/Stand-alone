package Boundary;

import Control.Controller;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SecretaryPageControllerGUI implements Initializable{
    @FXML
    private DatePicker dataDP;
    @FXML
    private TextField startTF;
    @FXML
    private TextField endTF;
    @FXML
    private Button cercaB;
    @FXML
    private Button logoutB;
    @FXML
    private Button inserisciB;
    @FXML
    private Button mostraPrenotazioniB;
    @FXML
    private Button miePrenotazioniB;
    @FXML
    private Button modificaPrenotazioneB;
    @FXML
    private Button eliminaPrenotazioneB;
    @FXML
    private Button nuovoAnnoB;
    @FXML
    private Button modificaAnnoB;
    @FXML
    private Button nuovaSessioneB;
    @FXML
    private Button modificaSessioneB;
    @FXML
    private Label alert;

    public void istanziaSPageGUI(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/SecretaryPageGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
            //Imposta il root relativo alla schermata di Login.
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources){

        alert.setVisible(false);
        Controller SPageController = new Controller();

        cercaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        inserisciB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        mostraPrenotazioniB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        miePrenotazioniB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        modificaPrenotazioneB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        eliminaPrenotazioneB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        nuovoAnnoB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        modificaAnnoB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        nuovaSessioneB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        modificaSessioneB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        logoutB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoginControllerGUI loginControllerGUI = new LoginControllerGUI();
                loginControllerGUI.istanziaLoginGUI(event);
            }
        });
    }
}
