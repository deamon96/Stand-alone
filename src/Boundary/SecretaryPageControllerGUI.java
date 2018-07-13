package Boundary;

import Bean.Disponible_RoomBean;
import Bean.Prenotation_Bean;
import Bean.SessionBean;
import Control.Controller;
import Utils.PrenotationBeanSingleton;
import Utils.PrenotationSingleton;
import Utils.UserSingleton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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
                            return;
                        }

                        SessionBean sessionBean = new SessionBean();
                        sessionBean.setDataInizio(s.getDataInizio());
                        sessionBean.setDataFine(s.getDataFine());
                        sessionBean.setTipo(s.getTipo());

                        String sessione = sessionBean.getDataInizio()+"/"+sessionBean.getDataFine();

                        controller.createPrenotationBean(inizio, fine, data, sessione);
                        Prenotation_Bean bean = new Prenotation_Bean(inizio, fine, data, "Sessione "+
                                sessionBean.getTipo()+" Inizio: "+ sessionBean.getDataInizio()+", Fine: "+
                                sessionBean.getDataFine());
                        new PrenotationBeanSingleton().setPrenotation_bean(bean);

                        PrenotationControllerPopUp controllerPopUp = new PrenotationControllerPopUp();

                        //PopUp
                        PrenotationSingleton.getInstance().setListaAule(r.getNome());
                        controllerPopUp.istanziaPopUp(event);
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
            public void handle(ActionEvent event) {new ForcedPrenotationControllerPopUp().istanziaPopUp(event);}
        });

        mostraPrenotazioniB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {new Secr_AllPrenotationsControllerPopUp().istanziaPopUp(event);}
        });

        miePrenotazioniB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {new Secr_SelfPControllerPopUp().istanziaPopUp(event);}
        });

        modificaPrenotazioneB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {new Secr_ModifyPrenotationControllerGUI().istanziaPopUp(event);}
        });

        eliminaPrenotazioneB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {new DeletePrenotationControllerGUI().istanziaDeletePrenotationGUI(event);}
        });

        nuovoAnnoB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {new NewYearControllerGUI().istanziaNewYearGUI(event);
            }
        });

        modificaAnnoB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { new ModifyYearControllerPopUp().istanziaPopUp(event);}
        });

        nuovaSessioneB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { new NewSessionControllerPopUp().istanziaPopUp(event);}
        });

        modificaSessioneB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { new ModifySessionControllerPopUp().istanziaPopUp(event);}
        });

        logoutB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UserSingleton.getInstance().setUser(null);
                LoginControllerGUI loginControllerGUI = new LoginControllerGUI();
                loginControllerGUI.istanziaLoginGUI(event);
            }
        });
    }
}
