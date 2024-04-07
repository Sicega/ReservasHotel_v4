package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Huespedes implements IHuespedes {

    // ArrayList para almacenar los hu�spedes
    private final List<Huesped> coleccionHuespedes;

    // Constructor
    public Huespedes() {
        this.coleccionHuespedes = new ArrayList<>();
    }

    // Para devolver una copia profunda de los hu�spedes
    public List<Huesped> get() {
        return copiaProfundaHuespedes();
    }

    // Para realizar la copia profunda de los hu�spedes
    private List<Huesped> copiaProfundaHuespedes() {
        List<Huesped> misHuesped = new ArrayList<>();

        // Itera sobre los hu�spedes y agrega copias profundas al nuevo ArrayList

        Iterator<Huesped> huespedIt = coleccionHuespedes.iterator();

        while (huespedIt.hasNext()){

            misHuesped.add(new Huesped(huespedIt.next()));

        }

        return misHuesped;
    }

    // Para obtener el tama�o de la lista
    public int getTamano() {
        return coleccionHuespedes.size();
    }

    // Para insertar un nuevo hu�sped en la colecci�n
    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un hu�sped nulo.");
        }

        // Comprueba si el hu�sped ya existe en la colecci�n
        if (buscar(huesped) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un hu�sped con ese dni.");
        }

        // Agrega el hu�sped al ArrayList
        coleccionHuespedes.add(new Huesped(huesped));
    }

    // Para buscar un hu�sped en la colecci�n
    public Huesped buscar(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede buscar un hu�sped nulo.");
        }

        // Utilizo un iterador para buscar el hu�sped en el ArrayList
        Iterator<Huesped> iterator = coleccionHuespedes.iterator();
        while (iterator.hasNext()) {
            Huesped actual = iterator.next();
            if (actual.equals(huesped)) {
                return new Huesped(actual);
            }
        }

        return null;
    }

    // Para eliminar un hu�sped de la colecci�n
    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un hu�sped nulo.");
        }

        if(!coleccionHuespedes.contains(huesped)){

            throw new OperationNotSupportedException("ERROR: No existe ning�n hu�sped como el indicado.");
        }

        // Utilizo un iterador para buscar y eliminar el hu�sped del ArrayList con remove
        Iterator<Huesped> iterator = coleccionHuespedes.iterator();
        while (iterator.hasNext()) {
            Huesped actual = iterator.next();
            if (actual.equals(huesped)) {
                iterator.remove();
                return;
            }
        }

    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }
}
