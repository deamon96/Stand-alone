package Boundary;

import Entity.Room;
import Utils.PrenotationSingleton;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrenotationControllerPopUp implements Initializable {
    @FXML
    private TableView<Room> auleTV = new TableView<>();
    @FXML
    private TableColumn nomeTC;
    @FXML
    private TextField nomeAulaTF;
    @FXML
    private Button prenotaB;
    @FXML
    private Button indietroB;

    public void istanziaPopUp(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/PrenotationPopUp.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Aule disponibili");
            stage.setScene(new Scene(root, 300, 450));
            stage.show();
        }catch (IOException er){
            System.out.println("-----IO Exception-----");
            er.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resource){

        ArrayList<String> auleDisponibili = PrenotationSingleton.getInstance().getListaAule();
        ObservableList<Room> listaAule = FXCollections.observableArrayList();

        try{
            for (int i = 0;  i < auleDisponibili.size(); i++){
                Room room = new Room();
                room.setNome(auleDisponibili.get(i));
                listaAule.add(room);
            }
            if (auleTV != null){
                nomeTC.setCellValueFactory(new PropertyValueFactory<>("nome"));
                auleTV.setItems(null);
                auleTV.setItems(listaAule);
            }
        }catch (NullPointerException nPE){
            System.out.println("-----Null Pointer Exception-----");
            nPE.printStackTrace();
        }

        auleTV.addEventHandler(MouseEvent.MOUSE_CLICKED,(event -> {
            try{
                String nome = auleTV.getSelectionModel().getSelectedItem().getNome();
                nomeAulaTF.setText(nome);
            }catch (Exception e){
                e.printStackTrace();
            }
        }));

        prenotaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        indietroB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });
    }
}
