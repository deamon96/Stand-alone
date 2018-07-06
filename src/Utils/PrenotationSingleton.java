package Utils;

import java.util.ArrayList;

public class PrenotationSingleton {

    private static PrenotationSingleton instance = null;

    private ArrayList<String> listaAule;

    public static PrenotationSingleton getInstance(){

        if (instance == null){
            instance = new PrenotationSingleton();
        }
        return instance;
    }

    public ArrayList<String> getListaAule(){
        return listaAule;
    }

    public void setListaAule(ArrayList<String> listaAule){
        this.listaAule = listaAule;
    }
}