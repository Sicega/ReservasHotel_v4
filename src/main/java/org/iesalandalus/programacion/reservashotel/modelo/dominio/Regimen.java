package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum Regimen {

    SOLO_ALOJAMIENTO("Solo Alojamiento",0),
    ALOJAMIENTO_DESAYUNO("Alojamiento y Desayuno", 15),
    MEDIA_PENSION("Media Pensi�n", 30),
    PENSION_COMPLETA("Pensi�n Completa", 50);

    //2-Creo los atributos, ambos de visibilidad privada
    private String cadenaAMostrar;
    private double incrementoPrecio;

    //M�TODOS

    //4-Creo el m�todo constructor del enumerado Regimen

    private Regimen (String cadenaAMostrar, int incrementoPrecio){

        this.cadenaAMostrar=cadenaAMostrar;

        this.incrementoPrecio=incrementoPrecio;


    }

    //3-Creo el getter de visibilidad p�blica y que devuelve un dato de tipo double

    public double getIncrementoPrecio() {

        return incrementoPrecio;
    }

    //5-Creo el m�todo ToString que devuelve la cadena a mostrar

    @Override
    public String toString() {

        //return "La opci�n de " + cadenaAMostrar + " conlleva un incremento de precio de " + incrementoPrecio;

        return ordinal() + " .- " + cadenaAMostrar;
    }
}
