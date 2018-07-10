package Entity;

import java.time.LocalDate;

public class Session {

    private LocalDate inizio;
    private LocalDate fine;
    private String tipo;

    public Session(LocalDate inizio, LocalDate fine, String tipo){
        this.inizio = inizio;
        this.fine = fine;
        this.tipo = tipo;
    }

    public LocalDate getInizio() {
        return inizio;
    }

    public void setInizio(LocalDate inizio) {
        this.inizio = inizio;
    }

    public LocalDate getFine() {
        return fine;
    }

    public void setFine(LocalDate fine) {
        this.fine = fine;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
