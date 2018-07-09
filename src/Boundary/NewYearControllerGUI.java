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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class NewYearControllerGUI implements Initializable {

    @FXML
    private DatePicker inizioDP;
    @FXML
    private DatePicker fineDP;
    @FXML
    private Button apriB;
    @FXML
    private Button indietroB;
    @FXML
    private Label alert;

    public void istanziaNewYearGUI(Event e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/boundary/NewYearGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        } catch (Exception er) {
            System.err.println(er.getMessage());
        }
    }

        public  void initialize(URL location, ResourceBundle resource){

            alert.setVisible(false);

            Controller controller = new Controller();

            apriB.setOnAction(new EventHandler<ActionEvent>() {
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
                        controller.newYear(inizio.toString(), fine.toString());
                        alert.setText("Apertura anno accademico avvenuta con successo!");
                        alert.setVisible(true);
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
