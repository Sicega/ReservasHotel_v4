package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Huespedes implements IHuespedes {

    // ArrayList para almacenar los hu�spedes
    private List<Huesped> coleccionHuespedes;
    private final String COLECCION="huespedes";

    // Constructor
    public Huespedes() {
        this.coleccionHuespedes = new ArrayList<>();
    }

    public List<Huesped> get() {

        List<Huesped> miHuesped = new ArrayList<>();
        FindIterable<Document> miIterador = MongoDB.getBD().getCollection(COLECCION).find().sort(Sorts.ascending(MongoDB.DNI));
        for(Document miDocumento : miIterador){
            miHuesped.add(MongoDB.getHuesped(miDocumento));
        }
        return miHuesped;
    }

    public int getTamano() {
        return (int) MongoDB.getBD().getCollection(COLECCION).countDocuments();
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

        Document miDocumento = MongoDB.getDocumento(huesped);
        MongoDB.getBD().getCollection(COLECCION).insertOne(miDocumento);
        coleccionHuespedes.add(huesped);

    }

    // Para buscar un hu�sped en la colecci�n
    public Huesped buscar(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede buscar un hu�sped nulo.");
        }

        Document miDocumento=MongoDB.getBD().getCollection(COLECCION).find(Filters.eq(MongoDB.DNI,huesped.getDni())).first();
        Huesped miHuesped=MongoDB.getHuesped(miDocumento);

        return miHuesped;
    }

    // Para eliminar un hu�sped de la colecci�n
    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un hu�sped nulo.");
        }

        if(!coleccionHuespedes.contains(huesped)){

            throw new OperationNotSupportedException("ERROR: No existe ning�n hu�sped como el indicado.");
        }

        MongoDB.getBD().getCollection(COLECCION).deleteOne(Filters.eq(MongoDB.DNI,huesped.getDni()));
        coleccionHuespedes.remove(huesped);

    }

    @Override
    public void comenzar() {

        FindIterable<Document> miIterador = MongoDB.getBD().getCollection(COLECCION).find();

        for(Document miDocumento : miIterador){
            coleccionHuespedes.add(MongoDB.getHuesped(miDocumento));
        }
    }

    @Override
    public void terminar() {

        MongoDB.cerrarConexion();
        coleccionHuespedes=null;
    }
}
