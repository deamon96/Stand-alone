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

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifyYearControllerGUI implements Initializable {
    @FXML
    private DatePicker inizioDP;
    @FXML
    private DatePicker fineDP;
    @FXML
    private Button modificaB;
    @FXML
    private Button indietroB;
    @FXML
    private Label alert;

    public void istanziaModifyYearGUI(Event e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/ModifyYearGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        } catch (Exception er) {
            System.err.println(er.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resource){

        alert.setVisible(false);

        Controller controller = new Controller();

        modificaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setVisible(false);

                LocalDate inizio = inizioDP.getValue();
                LocalDate fine = fineDP.getValue();

                if (inizio == null || fine == null){
                    alert.setText("Riempire entrambi i campi!");
                    alert.setVisible(true);
                }else {
                    alert.setVisible(false);
                    AccademicYearBean bean = AccademicYearBeanSingleton.getInstance().getYearBean();
                    if(controller.modAccYear(inizio.toString(), fine.toString(), bean.getDataInizio().toString(),
                            bean.getDataFine().toString())){
                        controller.modAccYear(inizio.toString(), fine.toString(), bean.getDataInizio().toString(),
                                bean.getDataFine().toString());
                        alert.setText("Modifica anno accademico avvenuta con successo!");
                        alert.setVisible(true);
                    }else {
                        alert.setText("Ci sono sessioni non interamente coperte! Operazione annullata");
                        alert.setVisible(true);
                    }
                }
            }
        });

        indietroB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new ModifyYearControllerPopUp().istanziaPopUp(event);
            }
        });
    }
}
