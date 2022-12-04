package com.example.friendslocation;

public class MyLocation {
    String nom;
    String numero;
    String longitude;
    String latitude;

    public MyLocation(String nom, String numero, String longitude, String latitude) {
        this.nom = nom;
        this.numero = numero;
        this.longitude = longitude;
        latitude = latitude;
    }

    @Override
    public String toString() {
        return "MyLocation{" +
                "nom='" + nom + '\'' +
                ", numero='" + numero + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
