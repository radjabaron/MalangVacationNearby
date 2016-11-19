package id.sch.smktelkom_mlg.project.xiirpl110203040.malangvacationnearby.model;

/**
 * Created by BARON on 11/13/2016.
 */

public class Taxi {
    public String namaTaxi;
    public String nomorTaxi;

    public Taxi(String namaTaxi, String nomorTaxi) {
        this.namaTaxi = namaTaxi;
        this.nomorTaxi = nomorTaxi;
    }

    public String getNamaTaxi() {
        return namaTaxi;
    }

    public void setNamaTaxi(String namaTaxi) {
        this.namaTaxi = namaTaxi;
    }

    public String getNomorTaxi() {
        return nomorTaxi;
    }

    public void setNomorTaxi(String nomorTaxi) {
        this.nomorTaxi = nomorTaxi;
    }

}

