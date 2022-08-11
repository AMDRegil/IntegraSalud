package com.example.integrasalud.model;

public class Actividad {
    private String uid;
    private String idPaciente;
    private String nombre;
    private String apellidos;
    private String correo;
    private String contrasena;
    private String oxi;
    private String ritmo;
    private String calorias;
    private String distancia;
    private String pasos;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente){
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo){
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena){
        this.contrasena = contrasena;
    }

    public String getOxi() {
        return oxi;
    }

    public void setOxi(String oxi){
        this.oxi = oxi;
    }

    public String getRitmo() {
        return ritmo;
    }

    public void setRitmo(String ritmo){
        this.ritmo = ritmo;
    }

    public String getCalorias() {
        return calorias;
    }

    public void setCalorias(String calorias){
        this.calorias = calorias;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia){
        this.distancia = distancia;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos){
        this.pasos = pasos;
    }

    @Override
    public String toString(){
        return nombre;
    }
}
