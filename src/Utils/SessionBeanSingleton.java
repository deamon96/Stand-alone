package Utils;

import Bean.SessionBean;

public class SessionBeanSingleton {

    private static SessionBeanSingleton instance = null;

    private SessionBean sessionBean;

    public static SessionBeanSingleton getInstance(){

        if (instance == null){
            instance = new SessionBeanSingleton();
        }
        return instance;
    }

    public SessionBean getSessionBean(){return sessionBean;}

    public void setSessionBean(SessionBean sessionBean){this.sessionBean = sessionBean;}
}
