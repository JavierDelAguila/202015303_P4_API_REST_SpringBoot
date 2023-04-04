package edu.comillas.icai.pat.ejemplopat.User;

import java.io.Serializable;

//Serializable para que pueda convertirse a json por ejemplo
public class User implements Serializable{
    private String username;
    private String passw;
    //private String correo;
    private String localidad;

    private static final String NO_DATA="-1";
    private final static String SEPARATOR=";";
    
    public User() {
        username=NO_DATA;
        localidad=NO_DATA;
    }


    public User(String name, String passw) {
        this.username = name;
        this.passw = passw;
        localidad=NO_DATA; 
    }


    public User(String name, String passw, String localidad) {
        this.username = name;
        this.passw = passw;
        this.localidad = localidad;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String name) {
        this.username = name;
    }
    public String getPassw() {
        return passw;
    }
    public void setPassw(String passw) {
        this.passw = passw;
    }
    public String getLocalidad() {
        return localidad;
    }
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }


    public static String getNoData() {
        return NO_DATA;
    }


    @Override
    public String toString() {
        return username+SEPARATOR+passw+SEPARATOR+localidad;
    }

}