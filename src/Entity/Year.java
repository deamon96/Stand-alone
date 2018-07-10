package Entity;

import java.time.LocalDate;

public class Year {

    private LocalDate inizio;
    private LocalDate fine;
    private String nome;

    public Year(LocalDate inizio, LocalDate fine){
        this.inizio = inizio;
        this.fine = fine;
        nome = inizio.toString().substring(0,4) + "/" +
                fine.toString().substring(0,4);
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
