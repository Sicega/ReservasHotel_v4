package org.iesalandalus.programacion.reservashotel;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.modelo.*;
import org.iesalandalus.programacion.reservashotel.vista.Opcion;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

public class MainApp {

    public static void main(String[] args) {

        // Creo instancias de modelo, vista y controlador

        /*Modelo modelo = new Modelo(FactoriaFuenteDatos.MONGODB.crear());
         Modelo modelo = new Modelo(FactoriaFuenteDatos.MEMORIA.crear());*/

        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);

        // Inicio la aplicaci�n invocando el m�todo comenzar del controlador

        controlador.comenzar();
        controlador.terminar();
        
    }

}
