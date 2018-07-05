package Boundary;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import Control.Controller;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfPageControllerGUI implements Initializable {
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
    private Button prenotazioniAttiveB;
    @FXML
    private Button miePrenotazioniB;
    @FXML
    private Button modificaPrenotazioneB;
    @FXML
    private Button eliminaPrenotazioneB;
    @FXML
    private Button visualizzaSessioniB;
    @FXML
    private Button storicoB;
    @FXML
    private RadioButton proiettore;
    @FXML
    private RadioButton microfono;
    @FXML
    private RadioButton lavagna;
    @FXML
    private RadioButton lavagnaInterattiva;
    @FXML
    private RadioButton cavoEthernet;
    @FXML
    private RadioButton preseIndividuali;
    @FXML
    private Label alert;

    private boolean proiettore01;
    private boolean microfono01;
    private boolean lavagna01;
    private boolean lavagnaInterattiva01;
    private boolean cavoEthernet01;
    private boolean preseIndividuali01;


    public void istanziaSPageGUI(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/ProfPageGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
            //Imposta il root relativo alla schermata di Login.
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources){

        alert.setVisible(false);

        cercaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                proiettore01 = proiettore.isSelected();
                microfono01 = microfono.isSelected();
                lavagna01 = lavagna.isSelected();
                lavagnaInterattiva01 = lavagnaInterattiva.isSelected();
                cavoEthernet01 = cavoEthernet.isSelected();
                preseIndividuali01 = preseIndividuali.isSelected();
            }
        });

        prenotazioniAttiveB.setOnAction(new EventHandler<ActionEvent>() {
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

        visualizzaSessioniB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        storicoB.setOnAction(new EventHandler<ActionEvent>() {
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
