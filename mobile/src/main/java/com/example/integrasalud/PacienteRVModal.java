package com.example.integrasalud;

import android.os.Parcel;
import android.os.Parcelable;

public class PacienteRVModal implements Parcelable {

    //Crear variables para los campos
    private String id;
    private String nombre;
    private String apellidos;
    private String edad;
    private String clinica;
    private String peso;
    private String altura;
    private String actividad;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Crear un constructor vacío
    public PacienteRVModal() {

    }

    protected PacienteRVModal(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        apellidos = in.readString();
        edad = in.readString();
        clinica = in.readString();
        peso = in.readString();
        altura = in.readString();
        actividad = in.readString();
    }

    public static final Creator<PacienteRVModal> CREATOR = new Creator<PacienteRVModal>() {
        @Override
        public PacienteRVModal createFromParcel(Parcel in) {
            return new PacienteRVModal(in);
        }

        @Override
        public PacienteRVModal[] newArray(int size) {
            return new PacienteRVModal[size];
        }
    };

    //Crear métodos get y set

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String Nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.apellidos = apellidos;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String Edad) {
        this.edad = edad;
    }

    public String getClinica() {
        return clinica;
    }

    public void setClinica(String Clinica) {
        this.clinica = clinica;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String Peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String Altura) {
        this.altura = altura;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String Actividad) {
        this.actividad = actividad;
    }

    public PacienteRVModal(String id, String nombre, String apellidos, String edad, String clinica, String peso, String altura, String actividad) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.clinica = clinica;
        this.peso = peso;
        this.altura = altura;
        this.actividad = actividad;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(edad);
        dest.writeString(clinica);
        dest.writeString(peso);
        dest.writeString(altura);
        dest.writeString(actividad);
    }
}
