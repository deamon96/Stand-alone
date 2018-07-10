package Boundary;

import Bean.AccademicYearBean;
import Control.Controller;
import Entity.Year;
import Utils.AccademicYearBeanSingleton;
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

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModifyYearControllerPopUp implements Initializable {
    @FXML
    private TableView<Year> yearTV = new TableView<>();
    @FXML
    private TableColumn yearTC;
    @FXML
    private TableColumn inizioTC;
    @FXML
    private TableColumn fineTC;
    @FXML
    private TextField yearTF;
    @FXML
    private Button modificaB;
    @FXML
    private Button indietroB;
    private Year year;

    public void istanziaPopUp(Event e){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Boundary/ModifyYearPopUp.fxml"));
            ((Node) (e.getSource())).getScene().setRoot(root);
        }catch (IOException er){
            System.out.println("-----IO Exception");
            er.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resource){

        Controller controller = new Controller();
        ArrayList<AccademicYearBean> s = controller.showYears();
        ObservableList<Year> anniAccademici = FXCollections.observableArrayList();
        modificaB.setDisable(true);

        try{
            for(int i = 0; i < s.size(); i++){
                Year year = new Year(s.get(i).getDataInizio(), s.get(i).getDataFine());
                anniAccademici.add(year);
            }
            if (yearTV != null){
                yearTC.setCellValueFactory(new PropertyValueFactory<>("nome"));
                inizioTC.setCellValueFactory(new PropertyValueFactory<>("inizio"));
                fineTC.setCellValueFactory(new PropertyValueFactory<>("fine"));
                yearTV.setItems(null);
                yearTV.setItems(anniAccademici);
            }
        }catch (NullPointerException nPE){
            System.out.println("-----Null Pointer Exception-----");
            nPE.printStackTrace();
        }

        assert yearTV != null;
        yearTV.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,(event -> {
            try{
                String nome = yearTV.getSelectionModel().getSelectedItem().getNome();
                LocalDate inizio = yearTV.getSelectionModel().getSelectedItem().getInizio();
                LocalDate fine = yearTV.getSelectionModel().getSelectedItem().getFine();
                year = new Year(inizio, fine);
                yearTF.setText(nome);
                modificaB.setDisable(false);
            }catch (Exception er){
                er.printStackTrace();
            }
        }));

        modificaB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AccademicYearBean bean = new AccademicYearBean();
                bean.setDataInizio(year.getInizio());
                bean.setDataFine(year.getFine());
                bean.setNome(year.getNome());
                //bean.setNome(yearTF.getText().substring(0,4)+"/"+yearTF.getText().substring(5));
                AccademicYearBeanSingleton.getInstance().setYearBean(bean);
                new ModifyYearControllerGUI().istanziaModifyYearGUI(event);
            }
        });

        indietroB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new SecretaryPageControllerGUI().istanziaSPageGUI(event);
            }
        });
    }
}
