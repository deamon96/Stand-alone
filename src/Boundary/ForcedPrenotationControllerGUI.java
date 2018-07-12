package Boundary;

import Bean.Prenotation_Bean;
import Control.Controller;
import Utils.PrenotationBeanSingleton;
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
import java.util.ResourceBundle;

public class ForcedPrenotationControllerGUI implements Initializable {
    @FXML
    private DatePicker dataDP;
    @FXML
    private TextField startTF;
    @FXML
    private TextField endTF;
    @FXML
    private TextField usernameProfTF;
    @FXML
    private RadioButton esameRB;
    @FXML
    private RadioButton conferenzaRB;
    @FXML
    private RadioButton testRB;
    @FXML
    private RadioButton laureaRB;
    @FXML
    private Button prenotaB;
    @FXML
    private Button indietroB;
    @FXML
    private Button preEmptionB;
    @FXML
    private Button annullaB;
    @FXML
    private Label alert;

    private String tipoP;

    /*public void istanziaGUI(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/ForcedPrenotationGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }*/

    void istanziaForcedPControllerGUI(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ForcedPrenotationControllerGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (Exception er){
            er.printStackTrace();
        }
    }

    private boolean controllaDelegante() {
        if ((esameRB.isSelected() || conferenzaRB.isSelected()) && (usernameProfTF.getText().equals(""))) {
            alert.setText("Inserire nome del professore delegante!");
            alert.setVisible(true);
            return false;
        }
        return true;
    }

    private void success(){
        alert.setText("Prenotazione avvenuta con successo");
        alert.setVisible(true);
        prenotaB.setDisable(true);
    }

    private void turn_off_all(boolean _01){
        dataDP.setDisable(_01);            esameRB.setDisable(_01);
        startTF.setDisable(_01);           conferenzaRB.setDisable(_01);
        endTF.setDisable(_01);             testRB.setDisable(_01);
        usernameProfTF.setDisable(_01);    laureaRB.setDisable(_01);
        prenotaB.setDisable(_01);          indietroB.setDisable(_01);
        preEmptionB.setVisible(_01);       annullaB.setVisible(_01);
    }

    public void initialize(URL location, ResourceBundle resource){

        Controller controller = new Controller();

        laureaRB.setSelected(true);
        Prenotation_Bean bean = PrenotationBeanSingleton.getInstance().getPrenotation_bean();

        esameRB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                usernameProfTF.setDisable(false);
                if (usernameProfTF.getText().equals("")){
                    prenotaB.setDisable(true);
                }else {
                    prenotaB.setDisable(false);
                }
            }
        });

        conferenzaRB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                usernameProfTF.setDisable(false);
                if (usernameProfTF.getText().equals("")){
                    prenotaB.setDisable(true);
                }else {
                    prenotaB.setDisable(false);
                }
            }
        });

        testRB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                usernameProfTF.setDisable(true);
                prenotaB.setDisable(false);
            }
        });

        laureaRB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                usernameProfTF.setDisable(true);
                prenotaB.setDisable(false);
            }
        });

        prenotaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setVisible(false);
                if (controllaDelegante()) {
                    if (esameRB.isSelected()) {
                        tipoP = "esame";
                    } else if (conferenzaRB.isSelected()) {
                        tipoP = "conferenza";
                    }
                } else if (testRB.isSelected()) {
                    tipoP = "test d'ingresso";
                } else if (laureaRB.isSelected()){
                    tipoP = "seduta di laurea";
                }

                if (controller.newPrenotationSecretary(bean.getAula(), tipoP, bean.getDate(),
                        bean.getInizio(), bean.getFine(), bean.getSessione(), usernameProfTF.getText())) {

                    success(); //ok
                } else {
                    turn_off_all(true);
                    alert.setText("Esiste già una prenotazione per quest'aula. Forzare la prenotazione ?");
                    alert.setVisible(true);
                    //TODO : ci stanno aule che danno fastidio ---> [PRE-EMPTION] OR [NON PRE-EMPTION]
                    //DONE
                    //si a premption allora DELETETHENINSERT
                    //si a non preemption allora abort
                    /* new Controller().newPrenotationSecretary(bean.getAula(), "esame", bean.getDate(),
                    bean.getInizio(), bean.getFine(), bean.getSessione(), usernameProfTF.getText());
                    success();*/
                }
            }
        });

        preEmptionB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.deleteThenInsert(bean.getAula(), tipoP, bean.getDate(), bean.getInizio(), bean.getFine(),
                        bean.getSessione(), usernameProfTF.getText());
                turn_off_all(false);
                alert.setVisible(false);
            }
        });

        annullaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                turn_off_all(false);
                alert.setText("Operazione annullata.");
                alert.setVisible(true);
            }
        });

        indietroB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new SecretaryPageControllerGUI().istanziaSPageGUI(event);
            }
        });
    }
}

