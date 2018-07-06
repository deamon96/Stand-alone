package Boundary;

import Bean.Disponible_RoomBean;
import Bean.SessionBean;
import Control.Controller;
import Utils.PrenotationSingleton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/SecretaryPageGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources){
        alert.setVisible(false);
        Controller controller = new Controller();

        cercaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Disponible_RoomBean r;
                LocalDate date = dataDP.getValue();
                String oraInizio = startTF.getText();
                String oraFine = endTF.getText();
                if (oraInizio == null || oraFine == null || date == null){
                    alert.setText("Completare tutti i campi!");
                    alert.setVisible(true);
                }else {
                    alert.setVisible(false);
                    try{
                        LocalTime inizio = LocalTime.parse(oraInizio);
                        LocalTime fine = LocalTime.parse(oraFine);
                        String data = date.toString();
                        r = controller.show_Secretary(inizio, fine, data);

                        if (r.getNome().isEmpty()){
                            alert.setText("Non ci sono aule prenotabili");
                        }

                        SessionBean s = controller.trovaSessione(data);

                        if (s.getDataInizio() == null){
                            alert.setText("La data inserita è fuori da ogni sessione");
                            alert.setVisible(true);
                        }

                        SessionBean sessionBean = new SessionBean();
                        sessionBean.setDataInizio(s.getDataInizio());
                        sessionBean.setDataFine(s.getDataFine());
                        sessionBean.setTipo(s.getTipo());

                        String sessione = sessionBean.getDataInizio()+"/"+sessionBean.getDataFine();

                        controller.createPrenotationBean(inizio, fine, data, sessione);

                        PrenotationControllerPopUp controllerPopUp = new PrenotationControllerPopUp();

                        //PopUp
                        PrenotationSingleton.getInstance().setListaAule(r.getNome());
                        controllerPopUp.istanziaPopUp();
                        //--//

                        for(int i = 0; i < r.getNome().size(); i++){
                            System.out.println(r.getNome().get(i));
                        }
                    }catch (DateTimeParseException e){
                        alert.setText("Formato dati inseriti errato!");
                        alert.setVisible(true);
                        System.out.println(e.getMessage());
                    }catch (NullPointerException n){
                        System.out.println(n.getMessage() + "\n we we we \n" + n.getCause());
                        n.printStackTrace();
                    }
                }

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
