package Boundary;

import Bean.SessionBean;
import Control.Controller;
import Entity.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewSessionControllerPopUp implements Initializable {
    @FXML
    private TableView<Session> sessionTV;
    @FXML
    private TableColumn tipoTC;
    @FXML
    private TableColumn inizioTC;
    @FXML
    private TableColumn fineTC;
    @FXML
    private Button indietroB;

    public void istanziaPopUp(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/ViewSessionPopUp.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (IOException er){
            System.out.println("-----IO Exception");
            er.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resource) {

        Controller controller = new Controller();
        ArrayList<SessionBean> s = controller.showAllSessions();
        ObservableList listaSessioni = FXCollections.observableArrayList();

        try {
            for (int i = 0; i < s.size(); i++) {
                Session session = new Session(LocalDate.parse(s.get(i).getDataInizio()),
                        LocalDate.parse(s.get(i).getDataFine()), s.get(i).getTipo());
                listaSessioni.add(session);
            }
            if (sessionTV != null) {
                tipoTC.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                inizioTC.setCellValueFactory(new PropertyValueFactory<>("inizio"));
                fineTC.setCellValueFactory(new PropertyValueFactory<>("fine"));
                sessionTV.setItems(null);
                sessionTV.setItems(listaSessioni);
            }
        } catch (NullPointerException nPE) {
            System.out.println("-----Null Pointer Exception-----");
            nPE.printStackTrace();
        }

        indietroB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {new ProfPageControllerGUI().istanziaPPageGUI(event);}
        });
    }
}
