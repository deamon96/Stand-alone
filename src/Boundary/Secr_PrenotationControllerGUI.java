package Boundary;

import Bean.Prenotation_Bean;
import Utils.PrenotationBeanSingleton;
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
import java.util.ResourceBundle;

public class Secr_PrenotationControllerGUI implements Initializable {
    @FXML
    private TextField dataTF;
    @FXML
    private TextField startTF;
    @FXML
    private TextField endTF;
    @FXML
    private TextField usernameProfTF;
    @FXML
    private Label sessioneL;
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
    private Label alert;

    public void istanziaSPageGUI(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Secr_PrenotationGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (Exception er){
            System.err.println(er.getMessage());
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

    public void initialize(URL location, ResourceBundle resource){

        laureaRB.setSelected(true);
        Prenotation_Bean bean = PrenotationBeanSingleton.getInstance().getPrenotation_bean();
        dataTF.setText(bean.getDate()); startTF.setText(bean.getInizio().toString());
        endTF.setText(bean.getFine().toString()); sessioneL.setText(bean.getSessione());

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
                if (controllaDelegante()){
                    if (esameRB.isSelected()){
                        new Controller().newPrenotationSecretary(bean.getAula(), "esame", bean.getDate(),
                                bean.getInizio(), bean.getFine(), bean.getSessione(), usernameProfTF.getText());
                        success();
                    }else if (conferenzaRB.isSelected()){
                        new Controller().newPrenotationSecretary(bean.getAula(), "conferenza", bean.getDate(),
                                bean.getInizio(), bean.getFine(), bean.getSessione(), usernameProfTF.getText());
                        success();
                    }
                }else if (testRB.isSelected()){
                    new Controller().newPrenotationSecretary(bean.getAula(), "test", bean.getDate(),
                            bean.getInizio(), bean.getFine(), bean.getSessione(),
                            UserSingleton.getInstance().getUser().getUsername());
                    success();
                }else if (laureaRB.isSelected()){
                    new Controller().newPrenotationSecretary(bean.getAula(), "laurea", bean.getDate(),
                            bean.getInizio(), bean.getFine(), bean.getSessione(),
                            UserSingleton.getInstance().getUser().getUsername());
                }
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
