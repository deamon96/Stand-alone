package Boundary;

import Bean.Prenotation_Bean;
import Entity.Room;
import Utils.PrenotationBeanSingleton;
import Utils.PrenotationSingleton;
import Utils.UserSingleton;
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

    public void istanziaPopUp(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/PrenotationPopUp.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (IOException er){
            System.out.println("-----IO Exception-----");
            er.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resource){

        ArrayList<String> auleDisponibili = PrenotationSingleton.getInstance().getListaAule();
        ObservableList<Room> listaAule = FXCollections.observableArrayList();
        prenotaB.setDisable(true);

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

        assert auleTV != null;
        auleTV.addEventHandler(MouseEvent.MOUSE_CLICKED,(event -> {
            try{
                String nome = auleTV.getSelectionModel().getSelectedItem().getNome();
                nomeAulaTF.setText(nome);
                prenotaB.setDisable(false);
            }catch (Exception e){
                e.printStackTrace();
            }
        }));

        prenotaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Prenotation_Bean bean = PrenotationBeanSingleton.getInstance().getPrenotation_bean();
                bean.setAula(nomeAulaTF.getText());
                if (UserSingleton.getInstance().getUser().getType().equals("1")){
                    Secr_PrenotationControllerGUI sPage = new Secr_PrenotationControllerGUI();
                    sPage.istanziaSPageGUI(event);
                }else if (UserSingleton.getInstance().getUser().getType().equals("0")){
                    Prof_PrenotationControllerGUI pPage = new Prof_PrenotationControllerGUI();
                    pPage.istanziaPPageGUI(event);
                }
            }
        });

        indietroB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //((Node) (event.getSource())).getScene().getWindow().hide();
                if (UserSingleton.getInstance().getUser().getType().equals("1")){
                    new SecretaryPageControllerGUI().istanziaSPageGUI(event);
                }else if (UserSingleton.getInstance().getUser().getType().equals("0")){
                    new ProfPageControllerGUI().istanziaPPageGUI(event);
                }
            }
        });
    }
}
