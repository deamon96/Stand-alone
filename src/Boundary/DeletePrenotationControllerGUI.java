package Boundary;

import Control.Controller;
import Entity.Room;
import Entity.User;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DeletePrenotationControllerGUI implements Initializable {
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
    private TableColumn idTC;
    @FXML
    private TextField idTF;
    @FXML
    private Button eliminaB;
    @FXML
    private Button indietroB;
    @FXML
    private Label alert;

    public void istanziaDeletePrenotationGUI(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/DeletePrenotationGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (Exception er){
            er.printStackTrace();
        }
    }

    private ArrayList<String> aggiornaTabella(Controller controller){
        ArrayList<Room> r;
        ObservableList listaPrenotazioni = FXCollections.observableArrayList();
        User user = UserSingleton.getInstance().getUser();
        ArrayList<String> identificativi = new ArrayList<>();
        if (user.getType().equals("1")){
            r = controller.allPrenotation();
        }else {
            r = controller.showComplete_DB();
        }

        try {
            for (int i = 0; i < r.size(); i++){
                Room room = new Room();
                room.setNome(r.get(i).getNome());
                room.setTipopr(r.get(i).getTipopr());
                room.setDatapr(r.get(i).getDatapr());
                room.setInizio(r.get(i).getInizio());
                room.setFine(r.get(i).getFine());
                room.setID(r.get(i).getID());
                identificativi.add(String.valueOf(r.get(i).getID()));
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
                idTC.setCellValueFactory(new PropertyValueFactory<>("ID"));
                prenotationTV.setItems(null);
                prenotationTV.setItems(listaPrenotazioni);
            }
        }catch (NullPointerException nPE){
            System.out.println("-----Null Pointer Exception");
            nPE.printStackTrace();
        }
        return identificativi;
    }

    public void initialize(URL location, ResourceBundle resource){

        alert.setVisible(false);
        Controller controller = new Controller();
        ArrayList<String> identificativi = aggiornaTabella(controller);

        eliminaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setVisible(false);
                if (idTF.getText().equals("")){
                    alert.setText("Inserire l'ID della prenotazione da eliminare");
                    alert.setVisible(true);
                    return;
                }
                String id = idTF.getText();
                int i = 0;
                while (i < identificativi.size()) {
                    if (identificativi.get(i).equals(id)) {
                        controller.delete(id);
                        alert.setText("Prenotazione eliminata con successo");
                        alert.setVisible(true);
                        aggiornaTabella(controller);
                        return;
                    }else{
                        i++;
                    }
                }
                alert.setText("Inserire un ID tra quelli disponibili");
                alert.setVisible(true);
            }
        });

        indietroB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User user = UserSingleton.getInstance().getUser();
                if (user.getType().equals("1")){
                    new SecretaryPageControllerGUI().istanziaSPageGUI(event);
                }else {
                    new ProfPageControllerGUI().istanziaPPageGUI(event);
                }
            }
        });
    }
}
