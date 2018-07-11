package Bean;

import java.time.LocalTime;

public class RoomBean {

    private String nome;
    private String tipopr;
    private String datapr;
    private LocalTime inizio;
    private LocalTime fine;
    private String fromp;
    private String ID;
    private String sessione;

    public String getSessione() {
        return sessione;
    }

    public void setSessione(String sessione) {
        this.sessione = sessione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipopr() {
        return tipopr;
    }

    public void setTipopr(String tipopr) {
        this.tipopr = tipopr;
    }

    public String getDatapr() {
        return datapr;
    }

    public void setDatapr(String datapr) {
        this.datapr = datapr;
    }

    public LocalTime getInizio() {
        return inizio;
    }

    public void setInizio(LocalTime inizio) {
        this.inizio = inizio;
    }

    public LocalTime getFine() {
        return fine;
    }

    public void setFine(LocalTime fine) {
        this.fine = fine;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFromp() {
        return fromp;
    }

    public void setFromp(String fromp) {
        this.fromp = fromp;
    }





}
