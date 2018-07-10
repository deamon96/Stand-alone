package Boundary;

import Bean.AccademicYearBean;
import Control.Controller;
import Utils.AccademicYearBeanSingleton;
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
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

public class NewSessionControllerGUI implements Initializable {
    @FXML
    private DatePicker inizioDP;
    @FXML
    private DatePicker fineDP;
    @FXML
    private RadioButton invernaleRB;
    @FXML
    private RadioButton estivaRB;
    @FXML
    private RadioButton autunnaleRB;
    @FXML
    private RadioButton extraRB;
    @FXML
    private Button apriB;
    @FXML
    private Button indietroB;
    @FXML
    private Label alert;

    public void istanziaNewSessionGUI(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/NewSessionGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resource){

        alert.setVisible(false);

        Controller controller = new Controller();

        apriB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                extraRB.setSelected(true);
                alert.setVisible(false);

                String inizio = inizioDP.getValue().toString();
                String fine = fineDP.getValue().toString();
                String tipo;

                if (invernaleRB.isSelected()){
                    tipo = "Invernale";
                }else if (estivaRB.isSelected()){
                    tipo = "Estiva";
                }else if (autunnaleRB.isSelected()){
                    tipo = "Autunnale";
                }else {
                    tipo = "Extra";
                }

                if (inizio.equals("") || fine.equals("")){
                    alert.setText("Riempire entrambi i campi!");
                    alert.setVisible(true);
                }else {
                    alert.setVisible(false);
                    AccademicYearBean bean = AccademicYearBeanSingleton.getInstance().getYearBean();
                    controller.newSess(inizio, fine, tipo, bean.getNome());
                }
            }
        });

        indietroB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {new NewSessionControllerPopUp().istanziaPopUp(event);}
        });
    }
}
