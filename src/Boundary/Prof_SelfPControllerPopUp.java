package Boundary;

import Control.Controller;
import Entity.Room;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Prof_SelfPControllerPopUp implements Initializable {
    @FXML
    private TableView<Room> prenotationTV;
    @FXML
    private TableColumn nomeTC;
    @FXML
    private TableColumn dataprTC;
    @FXML
    private TableColumn inizioTC;
    @FXML
    private TableColumn fineTC;
    @FXML
    private TableColumn tipoprTC;
    @FXML
    private Button indietroB;

    @FXML
    public void istanziaPopUp(Event e ){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Prof_SelfPPopUp.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        } catch (Exception er){
            er.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resource){

        Controller controller = new Controller();
        ArrayList<Room> p = controller.showComplete_DB();
        ObservableList listaPrenotazioni = FXCollections.observableArrayList();

        try {
            for (int i = 0; i < p.size(); i++){
                Room room = new Room();
                room.setNome(p.get(i).getNome());
                room.setTipopr(p.get(i).getTipopr());
                room.setDatapr(p.get(i).getDatapr());
                room.setInizio(p.get(i).getInizio());
                room.setFine(p.get(i).getFine());
                room.setFromp(null);
                room.setSessione(null);
                listaPrenotazioni.add(room);
            }
            if (prenotationTV != null) {
                nomeTC.setCellValueFactory(new PropertyValueFactory<>("nome"));
                dataprTC.setCellValueFactory(new PropertyValueFactory<>("datapr"));
                inizioTC.setCellValueFactory(new PropertyValueFactory<>("inizio"));
                fineTC.setCellValueFactory(new PropertyValueFactory<>("fine"));
                tipoprTC.setCellValueFactory(new PropertyValueFactory<>("tipopr"));
                prenotationTV.setItems(null);
                prenotationTV.setItems(listaPrenotazioni);
            }
        }catch (NullPointerException nPE){
            System.out.println("-----Null Pointer Exception");
            nPE.printStackTrace();
        }

        indietroB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {new ProfPageControllerGUI().istanziaPPageGUI(event);}
        });
    }
}

