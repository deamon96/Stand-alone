package Boundary;

import Bean.SessionBean;
import Control.Controller;
import Entity.Session;
import Utils.SessionBeanSingleton;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModifySessionControllerPopUp implements Initializable {
    @FXML
    private TableView<Session> sessionTV;
    @FXML
    private TableColumn tipoTC;
    @FXML
    private TableColumn inizioTC;
    @FXML
    private TableColumn fineTC;
    @FXML
    private TextField tipoTF;
    @FXML
    private TextField inizioTF;
    @FXML
    private TextField fineTF;
    @FXML
    private Button modificaB;
    @FXML
    private Button indietroB;

    private Session session;

    public void istanziaPopUp(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/ModifySessionPopUp.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (IOException er){
            System.out.println("-----IO Exception");
            er.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resource){

        Controller controller = new Controller();
        ArrayList<SessionBean> s = controller.showAllSessions();
        ObservableList listaSessioni = FXCollections.observableArrayList();
        modificaB.setDisable(true);

        try{
            for (int i = 0; i < s.size(); i++){
                Session session = new Session(LocalDate.parse(s.get(i).getDataInizio()),
                        LocalDate.parse(s.get(i).getDataFine()), s.get(i).getTipo());
                listaSessioni.add(session);
            }
            if (sessionTV != null){
                tipoTC.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                inizioTC.setCellValueFactory(new PropertyValueFactory<>("inizio"));
                fineTC.setCellValueFactory(new PropertyValueFactory<>("fine"));
                sessionTV.setItems(null);
                sessionTV.setItems(listaSessioni);
            }
        }catch (NullPointerException nPE){
            System.out.println("-----Null Pointer Exception-----");
            nPE.printStackTrace();
        }

        assert sessionTV != null;
        sessionTV.addEventHandler(MouseEvent.MOUSE_CLICKED, (event -> {
            try{
                String tipo = sessionTV.getSelectionModel().getSelectedItem().getTipo();
                LocalDate inizio = sessionTV.getSelectionModel().getSelectedItem().getInizio();
                LocalDate fine = sessionTV.getSelectionModel().getSelectedItem().getFine();
                session = new Session(inizio, fine, tipo);
                tipoTF.setText(tipo);
                inizioTF.setText(inizio.toString());
                fineTF.setText(fine.toString());
                modificaB.setDisable(false);
            }catch (Exception e){
                e.printStackTrace();
            }
        }));

        modificaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SessionBean bean = new SessionBean();
                bean.setTipo(session.getTipo());
                bean.setDataInizio(session.getInizio().toString());
                bean.setDataFine(session.getFine().toString());
                SessionBeanSingleton.getInstance().setSessionBean(bean);
                new ModifySessionControllerGUI().istanziaModifySessionGUI(event);
            }
        });

        indietroB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {new SecretaryPageControllerGUI().istanziaSPageGUI(event);}
        });
    }


}
