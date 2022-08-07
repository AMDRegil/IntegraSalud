package com.example.integrasalud;

import android.os.Parcel;
import android.os.Parcelable;

public class DatosRVModal implements Parcelable {

    //Crear variables para los campos
    private String id;
    private String oxi;
    private String ritmo;
    private String calorias;
    private String distancia;
    private String pasos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Crear un constructor vacío
    public DatosRVModal() {

    }

    protected DatosRVModal(Parcel in) {
        id = in.readString();
        oxi = in.readString();
        ritmo = in.readString();
        calorias = in.readString();
        distancia = in.readString();
        pasos = in.readString();
    }

    public static final Creator<DatosRVModal> CREATOR = new Creator<DatosRVModal>() {
        @Override
        public DatosRVModal createFromParcel(Parcel in) {
            return new DatosRVModal(in);
        }

        @Override
        public DatosRVModal[] newArray(int size) {
            return new DatosRVModal[size];
        }
    };

    //Crear métodos get y set

    public String getOxi() {
        return oxi;
    }

    public void setOxi(String Oxi) {
        this.oxi = oxi;
    }

    public String getRitmo() {
        return ritmo;
    }

    public void setRitmo(String Ritmo) {
        this.ritmo = ritmo;
    }

    public String getCalorias() {
        return calorias;
    }

    public void setCalorias(String Calorias) {
        this.calorias = calorias;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String Distancia) {
        this.distancia = distancia;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String Peso) {
        this.pasos = pasos;
    }

    public DatosRVModal(String id, String oxi, String ritmo, String calorias, String distancia, String pasos) {
        this.id = id;
        this.oxi = oxi;
        this.ritmo = ritmo;
        this.calorias = calorias;
        this.distancia = distancia;
        this.pasos = pasos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(oxi);
        dest.writeString(ritmo);
        dest.writeString(calorias);
        dest.writeString(distancia);
        dest.writeString(pasos);
    }
}
