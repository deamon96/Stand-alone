package Boundary;

import Bean.Prenotation_Bean;
import Control.Controller;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Prof_PrenotationControllerGUI implements Initializable {

    @FXML
    private TextField dataTF;
    @FXML
    private TextField startTF;
    @FXML
    private TextField endTF;
    @FXML
    private Label sessioneL;
    @FXML
    private RadioButton esameRB;
    @FXML
    private RadioButton conferenzaRB;
    @FXML
    private Button prenotaB;
    @FXML
    private Button indietroB;
    @FXML
    private Label alert;

    public void istanziaPPageGUI(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Prof_PrenotationGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (Exception er){
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resource){

        Controller controller = new Controller();

        esameRB.setSelected(true);
        Prenotation_Bean bean = PrenotationBeanSingleton.getInstance().getPrenotation_bean();
        dataTF.setText(bean.getDate()); startTF.setText(bean.getInizio().toString());
        endTF.setText(bean.getFine().toString()); sessioneL.setText(bean.getSessione());

        prenotaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (esameRB.isSelected()){
                    controller.newPrenotationProfessore(bean.getAula(), "esame", bean.getDate(),
                            bean.getInizio(), bean.getFine(), bean.getSessione());
                }else if (conferenzaRB.isSelected()){
                    controller.newPrenotationProfessore(bean.getAula(), "conferenza", bean.getDate(),
                            bean.getInizio(), bean.getFine(), bean.getSessione());
                }
            }
        });

        indietroB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new ProfPageControllerGUI().istanziaPPageGUI(event);
            }
        });
    }
}
