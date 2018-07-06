package Utils;

import Bean.Prenotation_Bean;

public class PrenotationBeanSingleton {

    private static PrenotationBeanSingleton instance = null;

    private Prenotation_Bean prenotation_bean;

    public static PrenotationBeanSingleton getInstance(){

        if (instance == null){
            instance = new PrenotationBeanSingleton();
        }
        return instance;
    }

    public Prenotation_Bean getPrenotation_bean() {
        return prenotation_bean;
    }

    public void setPrenotation_bean(Prenotation_Bean prenotation_bean) {
        this.prenotation_bean = prenotation_bean;
    }

}
