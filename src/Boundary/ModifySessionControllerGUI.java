package Boundary;

import Bean.SessionBean;
import Control.Controller;
import Utils.SessionBeanSingleton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifySessionControllerGUI implements Initializable {
    @FXML
    private DatePicker inizioDP;
    @FXML
    private DatePicker fineDP;
    @FXML
    private RadioButton invernaleRB;
    @FXML
    private RadioButton estivaRB;
    @FXML
    private RadioButton autunnaleRB;
   @FXML
   private RadioButton extraRB;
   @FXML
    private Button modificaB;
   @FXML
    private Button indietroB;
   @FXML
    private Label alert;

   public void istanziaModifySessionGUI(Event e){
       try{
           Parent root = FXMLLoader.load(getClass().getResource("/boundary/ModifySessionGUI.fxml"));
           ((Node) (e.getSource())).getScene().setRoot(root);
       }catch (Exception er){
           System.err.println(er.getMessage());
       }
   }

   public void initialize(URL location, ResourceBundle resource){

       extraRB.setSelected(true);
       alert.setVisible(false);

       Controller controller = new Controller();

       modificaB.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               alert.setVisible(false);

               String tipo;
               String inizio = inizioDP.getValue().toString();
               String fine = fineDP.getValue().toString();

               if (invernaleRB.isSelected()){
                   tipo = "Invernale";
               }else if (estivaRB.isSelected()){
                   tipo = "Estiva";
               }else if (autunnaleRB.isSelected()){
                   tipo = "Autunnale";
               }else {
                   tipo = "Extra";
               }

               if(inizio.equals("") || fine.equals("")){
                   alert.setText("Riempire entrambi i campi!");
                   alert.setVisible(true);
               }else {
                   alert.setVisible(false);
                   SessionBean bean = SessionBeanSingleton.getInstance().getSessionBean();
                   String vecchioInizio = bean.getDataInizio();
                   String vecchiaFine = bean.getDataFine();
                   String vecchiaSessione = vecchioInizio+"/"+vecchiaFine;
                   if (controller.modifySession(inizio, fine, vecchiaSessione, tipo)){
                       alert.setText("Modifica sessione avvenuta con successo!");
                       alert.setVisible(true);
                   }else {
                       alert.setText("C'è una sovrapposizione di sessioni per quelle date, operazione annullata");
                       alert.setVisible(true);
                   }


               }
           }
       });

       indietroB.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event){new ModifySessionControllerPopUp().istanziaPopUp(event);}
       });
   }
}
