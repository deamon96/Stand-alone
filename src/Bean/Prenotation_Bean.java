package Bean;

import java.time.LocalTime;

public class Prenotation_Bean {

    private LocalTime inizio;
    private LocalTime fine;
    private String date;
    private String sessione;

    public Prenotation_Bean(LocalTime inizio, LocalTime fine, String date, String sessione) {
        this.inizio = inizio;
        this.fine = fine;
        this.date = date;
        this.sessione = sessione;
    }

    public String getSessione() {
        return sessione;
    }

    public void setSessione(String sessione) {
        this.sessione = sessione;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}