package Boundary;

import Entity.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrenotationControllerPopUp implements Initializable {
    private ArrayList<String> auleDisponibili;
    @FXML
    private TableView auleTV;
    @FXML
    private TableColumn nomeTC;
    @FXML
    private TextField nomeAulaTF;
    @FXML
    private Button prenotaB;
    @FXML
    private Button indietroB;

    private ObservableList<Room> listaAule;

    public void setAuleDisponibili(ArrayList<String> disponibili){
        this.auleDisponibili = disponibili;
    }

    public void istanziaPopUp(){
        try{
            listaAule = FXCollections.observableArrayList();
            for (int i = 0;  i < auleDisponibili.size(); i++){
                Room room = new Room();
                room.setNome(auleDisponibili.get(i));
                listaAule.add(room);
            }
            if (auleTV != null){
                nomeTC.setCellValueFactory(new PropertyValueFactory<>("name"));
                auleTV.setItems(null);
                auleTV.setItems(listaAule);
            }

            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/PrenotationPopUp.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Aule disponibili");
            stage.setScene(new Scene(root, 300, 450));
            stage.show();
        }catch (NullPointerException er){
            System.out.println("-----Null Pointer Exception-----");
            er.printStackTrace();
        }catch (IOException nPE){
            System.out.println("-----IO Exception-----");
            nPE.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resource){
        indietroB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });
    }
}
