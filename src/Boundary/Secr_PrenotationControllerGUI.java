package Boundary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Secr_PrenotationControllerGUI implements Initializable {
    @FXML
    private DatePicker dataDP;
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

    public void initialize(URL location, ResourceBundle resource){

    }
}
