package Boundary;

import Bean.SessionBean;
import Entity.Room;
import Control.Controller;
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
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Prof_ModifyPrenotationControllerGUI implements Initializable {
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
    private TextField startTF;
    @FXML
    private TextField endTF;
    @FXML
    private DatePicker dataDP;
    @FXML
    private RadioButton esameRB;
    @FXML
    private RadioButton conferenzaRB;
    @FXML
    private Button modificaB;
    @FXML
    private Button indietroB;
    @FXML
    private Label alert;
    private String inizio;
    private String fine;
    private String data;
    private String tipo;
    private Room room = null;

    public void istanziaPopUp(Event e ){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Prof_ModifyPrenotationGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        } catch (Exception er){
            er.printStackTrace();
        }
    }

    private void aggiornaTabella(){

        ArrayList<Room> r = new Controller().showComplete_DB();
        ObservableList listaPrenotazioni = FXCollections.observableArrayList();

        try {
            for (int i = 0; i < r.size(); i++){
                Room room = new Room();
                room.setNome(r.get(i).getNome());
                room.setTipopr(r.get(i).getTipopr());
                room.setDatapr(r.get(i).getDatapr());
                room.setInizio(r.get(i).getInizio());
                room.setFine(r.get(i).getFine());
                room.setID(r.get(i).getID());
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
    }

    public void initialize(URL location, ResourceBundle resource){

        alert.setVisible(false);

        Controller controller = new Controller();
        aggiornaTabella();

        assert prenotationTV != null;
        prenotationTV.addEventHandler(MouseEvent.MOUSE_CLICKED, (event -> {
            alert.setVisible(false);
            try{
                setRoom(prenotationTV.getSelectionModel().getSelectedItem());
            }catch (Exception e){
                e.printStackTrace();
            }
        }));

        modificaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setVisible(false);

                setInizio(startTF.getText());
                setFine(endTF.getText());
                setData(dataDP.getValue().toString());

                if (esameRB.isSelected()){
                    setTipo("esame");
                }else{
                    setTipo("conferenza");
                }

                if (inizio.equals("") || fine.equals("") || data.equals("")){
                    alert.setText("Riempire tutti i campi");
                    alert.setVisible(true);
                }else {
                    alert.setVisible(false);
                    SessionBean s = controller.trovaSessione(data);
                    if (s.getDataInizio() == null){
                        alert.setText("La data inserita è fuori da ogni sessione");
                        alert.setVisible(true);
                    }else{
                        try{
                            if (room == null){
                                alert.setText("Selezionare aula");
                                alert.setVisible(true);
                            }
                            if (controller.modify(room.getNome(), String.valueOf(room.getID()), LocalTime.parse(inizio),
                                    LocalTime.parse(fine), data, tipo)){
                                //ok
                                alert.setText("Prenotazione modificata con successo");
                                alert.setVisible(true);
                            }else {
                                alert.setText("Impossibile prenotare, esiste già un'altra prenotazione per quella " +
                                        "fascia oraria.");
                                alert.setVisible(true);
                            }
                        }catch (DateTimeParseException e){
                            alert.setText("Formato dati errato");
                            alert.setVisible(true);
                            e.printStackTrace();
                        }
                    }
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

    public void setInizio(String inizio) {
        this.inizio = inizio;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
