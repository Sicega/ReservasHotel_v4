package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Habitaciones implements IHabitaciones {

    private List<Habitacion> coleccionHabitaciones;
    private final String COLECCION="habitaciones";

    // M�TODOS

    public Habitaciones() {
        this.coleccionHabitaciones = new ArrayList<>();
    }

    // M�todo para obtener una copia profunda de las habitaciones
    public List<Habitacion> get() {
        List <Habitacion> miHabitacion = new ArrayList<>();
        FindIterable <Document> miIterador = MongoDB.getBD().getCollection(COLECCION).find();
        for(Document miDocumento : miIterador){
            miHabitacion.add(MongoDB.getHabitacion(miDocumento));
        }
        return miHabitacion;
    }

    // M�todo para obtener las habitaciones de un tipo espec�fico
    public List<Habitacion> get(TipoHabitacion tipoHabitacion) {
        List<Habitacion> habitacionesTipo = new ArrayList<>();

        for(Habitacion habitacion : coleccionHabitaciones) {
            if(tipoHabitacion.equals(TipoHabitacion.SIMPLE)) {
                if (habitacion instanceof Simple) {
                    habitacionesTipo.add(new Simple((Simple) habitacion));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.DOBLE)) {
                if (habitacion instanceof Doble) {
                    habitacionesTipo.add(new Doble((Doble) habitacion));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
                if (habitacion instanceof Triple) {
                    habitacionesTipo.add(new Triple((Triple) habitacion));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)) {
                if (habitacion instanceof Suite) {
                    habitacionesTipo.add(new Suite((Suite) habitacion));
                }
            }
        }

        // Devuelvo una nueva lista con las habitaciones del tipo especificado copiadas
        return new ArrayList<>(habitacionesTipo);
    }

    // M�todo para obtener el tama�o actual de la colecci�n
    public int getTamano() {
        return coleccionHabitaciones.size();
    }

    // M�todo para insertar una nueva habitaci�n en la colecci�n
    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitaci�n nula.");
        }

        if (buscar(habitacion) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una habitaci�n con ese identificador.");
        }

        // Utilizo instanceof seg�n el tipo de habitacion para a�adir a la colecci�n
        if (habitacion instanceof Simple) {
            coleccionHabitaciones.add(new Simple((Simple) habitacion));
        } else if (habitacion instanceof Doble) {
            coleccionHabitaciones.add(new Doble((Doble) habitacion));
        } else if (habitacion instanceof Triple) {
            coleccionHabitaciones.add(new Triple((Triple) habitacion));
        } else if (habitacion instanceof Suite) {
            coleccionHabitaciones.add(new Suite((Suite) habitacion));
        }
    }

    // M�todo privado para buscar el �ndice de una habitaci�n en la colecci�n
    private int buscarIndice(Habitacion habitacion) {
        return coleccionHabitaciones.indexOf(habitacion);
    }

    // M�todo para buscar una habitaci�n en la colecci�n
    public Habitacion buscar(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitaci�n nula.");
        }

        // Busco el �ndice de la habitaci�n en la colecci�n
        int indice = buscarIndice(habitacion);

        // Devuelvo la habitaci�n encontrada o null si no se encontr�
        if (indice != -1) {
            if (coleccionHabitaciones.get(indice) instanceof Simple) {
                return new Simple((Simple) coleccionHabitaciones.get(indice));
            } else if (coleccionHabitaciones.get(indice) instanceof Doble) {
                return new Doble((Doble) coleccionHabitaciones.get(indice));
            } else if (coleccionHabitaciones.get(indice) instanceof Triple) {
                return new Triple((Triple) coleccionHabitaciones.get(indice));
            } else if (coleccionHabitaciones.get(indice) instanceof Suite) {
                return new Suite((Suite) coleccionHabitaciones.get(indice));
            }
        } return null;
    }

    // M�todo para borrar una habitaci�n de la colecci�n
    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede borrar una habitaci�n nula.");
        }

        // Busco el �ndice de la habitaci�n en la colecci�n
        int indice = buscarIndice(habitacion);

        // Compruebo si encuentra la habitaci�n
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitaci�n como la indicada.");
        }

        // Elimino la habitaci�n de la colecci�n
        coleccionHabitaciones.remove(indice);
    }

    @Override
    public void comenzar() {

        FindIterable<Document>miIterador = MongoDB.getBD().getCollection(COLECCION).find();

        for(Document miDocumento : miIterador){
            coleccionHabitaciones.add(MongoDB.getHabitacion(miDocumento));
        }
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
        coleccionHabitaciones=null;
    }
}
