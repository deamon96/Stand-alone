package Entity;

public class Room {


    private String nome;
    private String tipopr;
    private String datapr;
    private String inizio;
    private String fine;
    private String fromp;
    private String sessione;

    public String getSessione() {
        return sessione;
    }

    public void setSessione(String sessione) {
        this.sessione = sessione;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID;

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

    public String getInizio() {
        return inizio;
    }

    public void setInizio(String inizio) {
        this.inizio = inizio;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String getFromp() {
        return fromp;
    }

    public void setFromp(String fromp) {
        this.fromp = fromp;
    }
}
