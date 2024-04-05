package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum TipoHabitacion {

    //Elimino el par�metro del n� max. de personas

    SUITE("Suite"),
    SIMPLE("Simple"),
    DOBLE("Doble"),
    TRIPLE("Triple");


    private String cadenaAMostrar;

    // Elimino numero m�ximo de personas


    TipoHabitacion(String cadenaAMostrar){ //Modifico el constructor del enumerado para no incluir el n� max. de personas

        this.cadenaAMostrar=cadenaAMostrar;
    }

    // Elimino el m�todo getNumeroMaximoDePersonas

    @Override
    public String toString() {

        return ordinal() + " .- " + cadenaAMostrar;
    }

}
