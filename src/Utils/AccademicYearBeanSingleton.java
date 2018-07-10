package Utils;

import Bean.AccademicYearBean;

public class AccademicYearBeanSingleton {

    private static AccademicYearBeanSingleton instance = null;

    private AccademicYearBean yearBean;

    public static AccademicYearBeanSingleton getInstance(){

        if (instance == null){
            instance = new AccademicYearBeanSingleton();
        }
        return instance;
    }

    public AccademicYearBean getYearBean(){ return yearBean;}

    public void setYearBean(AccademicYearBean accademicYearBean){this.yearBean = accademicYearBean;}
}
