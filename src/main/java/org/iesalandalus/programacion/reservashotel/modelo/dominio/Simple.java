package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Simple extends Habitacion{

    private static final int NUM_MAXIMO_PERSONAS=1; // Declaro la constante privada que almacena el n� max. de personas, en este caso 1 al ser simple

    public Simple(int planta, int puerta, double precio) {
        super(planta, puerta, precio); // Llamo a la superclase con super
    }

    public Simple(Simple habitacionSimple) {
        super(habitacionSimple); // Utilizo super tambi�n para el constructor copia
    }

    public int getNumeroMaximoPersonas(){ // Implemento el m�todo que debe tener la clase al heredar de Habitacion
        return NUM_MAXIMO_PERSONAS; // Devuelve el n� m�ximo de personas al ser habitacion simple, uso la constante para ello
    }

    @Override
    public String toString() { // Genero el toString con la cadena esperada por el test
        return String.format("%s, habitaci�n simple, capacidad=%d personas",
                super.toString(), getNumeroMaximoPersonas());
    }
}
