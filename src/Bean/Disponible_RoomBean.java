package Bean;

import java.util.ArrayList;

public class Disponible_RoomBean {

    public Disponible_RoomBean(ArrayList<String> nome) {
        this.nome = nome;
    }

    public ArrayList<String> getNome() {
        return nome;
    }

    public void setNome(ArrayList<String> nome) {
        this.nome = nome;
    }

    private ArrayList<String> nome;

}
