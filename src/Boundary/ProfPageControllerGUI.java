package Boundary;

import Bean.Disponible_RoomBean;
import Bean.Prenotation_Bean;
import Bean.SessionBean;
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
import Control.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class ProfPageControllerGUI implements Initializable {
    @FXML
    private DatePicker dataDP;
    @FXML
    private TextField startTF;
    @FXML
    private TextField endTF;
    @FXML
    private TextField noPostiTF;
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

    private String proiettore01 = "no"; private String microfono01 = "no"; private String lavagna01 = "no";
    private String lavagnaInterattiva01 = "no"; private String cavoEthernet01 = "no";
    private String preseIndividuali01 = "no";

    public void istanziaPPageGUI(Event e){
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
        Controller controller = new Controller();

        cercaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                if (proiettore.isSelected()){proiettore01 = "si";}
                if (microfono.isSelected()){microfono01 = "si";}
                if (lavagna.isSelected()){lavagna01 = "si";}
                if (lavagnaInterattiva.isSelected()){lavagnaInterattiva01 = "si";}
                if (cavoEthernet.isSelected()){cavoEthernet01 = "si";}
                if (preseIndividuali.isSelected()){preseIndividuali01 = "si";}

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
                        r = controller.show(inizio, fine, data, microfono01, proiettore01, lavagna01,
                                lavagnaInterattiva01, cavoEthernet01, preseIndividuali01,
                                Integer.parseInt(noPostiTF.getText()));

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
                        controllerPopUp.istanziaPopUp(event);
                        //--//

                        Prenotation_Bean bean = new Prenotation_Bean(inizio, fine, data, "Sessione "+
                                sessionBean.getTipo()+" Inizio: "+ sessionBean.getDataInizio()+", Fine: "+
                                sessionBean.getDataFine());
                        new PrenotationBeanSingleton().setPrenotation_bean(bean);

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
                    }catch (NumberFormatException f){
                        alert.setText("Numero di posti non valido!");
                        alert.setVisible(true);
                        System.out.println(f.getMessage());
                    }
                }

            }
        });

        prenotazioniAttiveB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        miePrenotazioniB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {new Prof_SelfPControllerPopUp().istanziaPopUp(event);}
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
            public void handle(ActionEvent event) {new ViewSessionControllerPopUp().istanziaPopUp(event);}
        });

        storicoB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
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
