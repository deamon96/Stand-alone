package Control;

import Bean.*;
import DAO.*;
import Entity.Room;
import Entity.User;
import Utils.PrenotationBeanSingleton;
import Utils.SendMail;
import Utils.UserSingleton;

import java.time.LocalTime;
import java.util.ArrayList;

public class Controller {

    //--------------LOGIN---------------//

    public User check(String username, String Password){
        LoginDB loginDB = new LoginDB();
        User u = loginDB.findByNameAndPassword(username, Password);
        return u;
    }
    //------AULE DISPONIBILI PROFESSORE-------------//

    public Disponible_RoomBean show(LocalTime timeInizio, LocalTime timeFine, String dateSearch, String microfono,
                                    String proiettore, String lavagna, String lavElettronica, String ethernet, String presa, int posti) {
        Disponible_RoomBean showDatabase = DisponibleRooms_Prof.show(timeInizio, timeFine, dateSearch, microfono, proiettore, lavagna, lavElettronica, ethernet, presa, posti);
        return showDatabase;
    }

    //------AULE DIPONIBILI SEGRETARIA--------------//

    public Disponible_RoomBean show_Secretary(LocalTime timeInizio, LocalTime timeFine, String dateSearch) {
        Disponible_RoomBean showDatabase = DisponibleRooms_Secr.show_Secretary(timeInizio, timeFine, dateSearch);
        return showDatabase;
    }

    //-----------PRENOTAZIONI EFFETUATE SEGRETARIA----------------//

    public ArrayList<Room> show_s() {
        ArrayList<Room> rooms = ShowDatabase_Secr.show_secr();
        return rooms;
    }

    //--------------PRENOTAZIONI EFFETTUATE PROFESSORE---------------//

    public ArrayList<Room> showComplete_DB(){
        return ShowCompleteDB.show_completeDB();
    }
/*
    //-------------MOSTRA TUTTE LE PRENOTAZIONI (SEGRETARIA)-----------//

    public ArrayList<Room> allPrenotation(){
        ArrayList<Room> rooms = All_Prenotation.showAll();
        return rooms;
    }
*/
    //-----------------TUTTE LE AULE-----------------------------------//

    public ArrayList<RoomBean> allRooms(){
        ArrayList<RoomBean> rooms = All_Rooms.AllRooms();
        return rooms;
    }

    //----------------NUOVA PRENOTAZIONE PROFESSORE--------------//

    public boolean newPrenotationProfessore(String nameAula, String tipoPrenota, String dataPrenota, LocalTime timeInizioPrenota,
                                            LocalTime timeFinePrenota, String sessione) {
        Insert_Prof dbInsertProf = new Insert_Prof();
        return dbInsertProf.insert(nameAula, tipoPrenota, dataPrenota, timeInizioPrenota, timeFinePrenota, sessione);
    }

    //---------------------NUOVA PRENOTAZIONE SEGRETARIA-------------//

    public boolean newPrenotationSecretary(String nameAula, String tipoPrenota, String dataPrenota, LocalTime timeInizioPrenota,
                                           LocalTime timeFinePrenota, String sessione, String from){
        Insert_Secretary insert_secretary = new Insert_Secretary();
        return insert_secretary.insert(nameAula, tipoPrenota, dataPrenota, timeInizioPrenota, timeFinePrenota, sessione, from);

    }

    //------------------CANCELLA PRENOTAZIONI GIÀ ESISTENTI CHE DANNO CONFLITTO E POI INSERISCI LA NUOVA PRENOTAZIONE-------------//

    public boolean deleteThenInsert(String nameAula, String tipoPrenota, String dataPrenota, LocalTime timeInizioPrenota,
                                    LocalTime timeFinePrenota,String sessione, String from){

        DeleteThenInsert deleteThenInsert = new DeleteThenInsert();
        deleteThenInsert.deleteThenInsert(nameAula, tipoPrenota, dataPrenota, timeInizioPrenota, timeFinePrenota, sessione, from);
        return true;
    }
/*
    //-----------------------MODIFICA PRENOTAZIONE------------------//

    public boolean modify(String ID, LocalTime start, LocalTime end, String date, String type){

        Modify modify = new Modify();
        return modify.modify(ID, start, end, date, type);

    }

    //------------------CANCELLA PRENOTAZIONI GIÀ ESISTENTI CHE DANNO CONFLITTO E POI AGGIORNA LA PRENOTAZIONE SELEZIONATA-------------//

    public boolean deleteThenUpdate(String id, LocalTime start, LocalTime end, String date, String type){

        DeleteThenUpdate deleteThenUpdate = new DeleteThenUpdate();
        return deleteThenUpdate.deletethenUpdate(id, start, end, date, type);

    }

    //--------------CANCELLAZIONE PRENOTAZIONE----------//

    public boolean delete(String ID){

        Delete delete = new Delete();
        return delete.delete(ID);

    }

    //-----------CONTROLLO ENTRY DUPLICATE---------//

    public boolean duplicateControl(String name, String dataPrenota, LocalTime timeInizioPrenota, LocalTime timeFinePrenota){

        EntryController entryController = new EntryController();
        return entryController.duplicateController(name, dataPrenota, timeInizioPrenota, timeFinePrenota);

    }
*/
    //---------------CONTROLLO ENTRY VUOTE-------------//

    public boolean emptyControl(String name){

        EntryController entryController = new EntryController();
        return entryController.emptyController(name);
    }

    //------------EMAIL DI NOTIFICA---------//

    public void sendEmail(String dest, String object, String text){

        SendMail sendMail = new SendMail();
        sendMail.inviaMail(dest, object, text);

    }
/*
    //------------------------asdfasdfsdfasdfsf----------------------//

    public ArrayList<Room> storico(String datainizio, String datafine){
        PrenotationInAccademicYear p = new PrenotationInAccademicYear();
        return p.show(datainizio, datafine);

    }
*/
    //--------------------CREAZIONE SINGLETON--------------------//

    public void createSingleton(User u){
        UserSingleton singleton = UserSingleton.getInstance();
        singleton.setUser(u);

    }

    //-----------------BEAN----------------------//

    public void createPrenotationBean(LocalTime inizio, LocalTime fine, String date, String sessione){

        Prenotation_Bean prenotation_bean = new Prenotation_Bean(inizio, fine, date, sessione);
        PrenotationBeanSingleton.getInstance().setPrenotation_bean(prenotation_bean);

    }

    //--------------------ANNI ACCADEMICI--------------------------//

    public boolean newYear(String datainizio, String datafine){

        NewAccYear newAccYear = new NewAccYear();
        return newAccYear.newYaear(datainizio, datafine);
    }

    //------------------MOSTRA ANNI ACCADEMICI---------------------//

    public ArrayList<AccademicYearBean> showYears(){

        Show_Acc_Year show_acc_year = new Show_Acc_Year();
        return show_acc_year.show();
    }

    //-------------------SESSIONI-----------------------//

    public boolean newSess(String datainizio, String datafine, String tipo, String accYear){
        NewSess newSess = new NewSess();
        String nome = datainizio+"/"+datafine;
        return newSess.insertSess(datainizio, datafine, tipo, accYear, nome);
    }

    //-------------------MOSTRA TUTTE LE SESSIONI---------//

    public ArrayList<SessionBean> showAllSessions(){

        Show_Session show_session = new Show_Session();
        return show_session.showAllSession();
    }

    //-------------------MODIFICA ANNO ACCADEMICO-----------//

    public boolean modAccYear(String newInizio, String newFine, String oldInizio, String oldFine){

        Modify_AccYear modify_accYear = new Modify_AccYear();
        return modify_accYear.modify(newInizio, newFine, oldInizio, oldFine);
    }

    //-------------------MODIFICA SESSIONE-----------------//

    public boolean modifySession(String newinizio, String newfine, String session, String newTipo){

        Modify_Session modify_session = new Modify_Session();
        return modify_session.modify(newinizio, newfine, session, newTipo);
    }

    //-------------------TROVA SESSIONE PER PRENOTAZIONE IN BASE ALLA DATA DI PRENOTAZIONE I UN AULA--------------//

    public SessionBean trovaSessione(String dataPrenotazione){

        Find_Session find_session = new Find_Session();
        return find_session.find(dataPrenotazione);
    }
/*
    public void createPrenotationBean(String nome, LocalTime inizio, LocalTime fine, String date, String tipo){
        Prenotation_Bean prenotation_bean = new Prenotation_Bean(nome, inizio, fine, date, tipo);
    }

    public void createPrenotationSingleton(String nome, LocalTime inizio, LocalTime fine, String date, String tipo){
        Prenotation_Bean prenotation_bean = new Prenotation_Bean(nome, inizio, fine, date, tipo);
        PrenotationBeanSingleton.getInstance().setPrenotation_bean(prenotation_bean);
    }*/
}
