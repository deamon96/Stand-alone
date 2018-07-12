package Boundary;

import Bean.SessionBean;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Secr_ModifyPrenotationControllerGUI implements Initializable {
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
    private TextField idTF;
    @FXML
    private DatePicker dataDP;
    @FXML
    private RadioButton esameRB;
    @FXML
    private RadioButton conferenzaRB;
    @FXML
    private RadioButton testRB;
    @FXML
    private RadioButton laureaRB;
    @FXML
    private Button modificaB;
    @FXML
    private Button indietroB;
    @FXML
    private Button preEmptionB;
    @FXML
    private Button annullaB;
    @FXML
    private Label alert;
    private String inizio;
    private String fine;
    private String data;
    private String tipo;
    private String ID;
    private Room room = null;

    private void turn_off_all(boolean _01){
        dataDP.setDisable(_01);             esameRB.setDisable(_01);
        startTF.setDisable(_01);            conferenzaRB.setDisable(_01);
        endTF.setDisable(_01);              testRB.setDisable(_01);
        prenotationTV.setDisable(_01);      laureaRB.setDisable(_01);
        modificaB.setDisable(_01);          indietroB.setDisable(_01);
        preEmptionB.setVisible(_01);        annullaB.setVisible(_01);
        idTF.setDisable(_01);
    }

    public void istanziaPopUp(Event e ){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Secr_ModifyPrenotationGUI.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        } catch (Exception er){
            er.printStackTrace();
        }
    }

    private void aggiornaTabella(){

        ArrayList<Room> r = new Controller().allPrenotation();
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
        preEmptionB.setVisible(false);
        annullaB.setVisible(false);

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
                setID(idTF.getText());

                if (esameRB.isSelected()){
                    setTipo("esame");
                }else if (conferenzaRB.isSelected()){
                    setTipo("conferenza");
                }else if (testRB.isSelected()){
                    setTipo("test d'ingresso");
                }else{
                    setTipo("seduta di laurea");
                }

                if (inizio.equals("") || fine.equals("") || data.equals("") || ID.equals("")){
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
                            if (controller.modify(String.valueOf(room.getID()), LocalTime.parse(inizio), LocalTime.parse(fine), data, tipo)){
                                //ok
                                alert.setText("Prenotazione modificata con successo");
                                alert.setVisible(true);
                            }else {
                                turn_off_all(true);
                                alert.setText("La modifica sovrascriverà un'altra prenotazione. Procedere?");
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

        preEmptionB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.deleteThenUpdate(String.valueOf(room.getID()), LocalTime.parse(inizio), LocalTime.parse(fine), data, tipo);
                alert.setText("Prenotazione modificata con successo");
                alert.setVisible(true);
                turn_off_all(false);
                aggiornaTabella();
            }
        });

        annullaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                turn_off_all(false);
                alert.setText("Operazione annullata");
                alert.setVisible(true);
            }
        });

        indietroB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new SecretaryPageControllerGUI().istanziaSPageGUI(event);
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

    public void setID(String ID) {
        this.ID = ID;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}

