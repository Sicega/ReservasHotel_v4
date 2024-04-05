package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Huesped {

    private static final String ER_TELEFONO = "[0-9]{9}"; //Valida n�meros de tel�fono que tienen 9 d�gitos
    private static final String ER_CORREO = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$"; //La primera parte de la ER representa el formato v�lido de la parte local del correo, seguido de una @ y la parte del dominio, tras ello un punto y la extensi�n del dominio que debe tener al menos 2 letras y como m�ximo 4
    private static final String ER_DNI = "([0-9]{8})([A-Za-z])"; //Valida 8 d�gitos del 0 al 9 seguidos de una letra
    public static final String FORMATO_FECHA = "dd/MM/yyyy"; // Establezco un formato de d�a/mes/a�o

    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;

    //M�TODOS

    /**
     * 3-Creo el m�todo privado formateaNombre, que recibe de par�metro un dato de tipo String y devuelve un String.
     * Creo un array de tipo String que:
     * Con el m�todo trim() se eliminan los espacios en blanco al principio y al final de la cadena de caracteres
     * Con el m�todo split se divide el nombre en palabras
     * Creo un objeto de tipo StringBuilder que almacenar� el resultado
     * Con un bucle for each recorro la cadena de caracteres haciendo que con el m�todo substring
     * lleve a la posici�n indicada de la cadena de caracteres y haga que ese caracter se convierta en may�scula
     * con toUppercase, lo mismo para las min�scu�as con toLowercase en la posici�n de caracter indicada.
     */

    private String formateaNombre(String nombre) {

        if(nombre==null){

            throw new NullPointerException("ERROR: El nombre de un hu�sped no puede ser nulo.");
        }

        if(nombre.isBlank()){

            throw new IllegalArgumentException("ERROR: El nombre de un hu�sped no puede estar vac�o.");
        }

        String[] nombre_apellidos = nombre.trim().toLowerCase().split("\\s+"); //La expresi�n regular \\s se interpreta como un espacio en blanco

        StringBuilder nombre_completo = new StringBuilder();

        for (String palabra : nombre_apellidos) {
            nombre_completo.append(palabra.substring(0, 1).toUpperCase()).append(palabra.substring(1)).append(" ");
        }

        return nombre_completo.toString().trim();
    }

    /**
     * 4- para comprobar la letra del DNI hay que trabajar con expresiones regulares, para ello importo las clases Matcher
     * y Pattern del paquete java.util.regex e implemento antes un m�todo que calcule si la letra del DNI es correcta en base
     * a los n�meros introducidos
     */

    private static char calcularLetraDni(int numeroDni) {
        // Este m�todo calcula la letra del DNI con las letras permitidas y dividiendo la parte num�rica del DNI entre 23
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int indiceLetra = numeroDni % 23;
        return letras.charAt(indiceLetra);
    }

    private boolean comprobarLetraDni(String dni) {

        // Defino la expresi�n regular para extraer el n�mero y la letra del DNI
        String regex = ER_DNI;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dni);

        if (matcher.matches()) {
            // Para obtener el n�mero y la letra del DNI usando grupos:
            String numeroDni = matcher.group(1);
            String letraDni = matcher.group(2).toUpperCase(); // Convierto a may�sculas la letra del DNI

            // Calcula la letra esperada usando el m�todo implementado antes, calcularLetraDni
            char letraCalculada = calcularLetraDni(Integer.parseInt(numeroDni));

            // Compara la letra calculada con la letra introducida
            if(letraCalculada == letraDni.charAt(0))

                return true;

            else{
                throw new IllegalArgumentException("ERROR: La letra del dni del hu�sped no es correcta.");
            }
        } else {
            // El formato del DNI no es v�lido si no cumple lo anterior
            throw new IllegalArgumentException("ERROR: El dni del hu�sped no tiene un formato v�lido.");

        }


    }

    /**
     * 5- Como ya est� implementado que el nombre se introduzca correctamente con el m�todo formateaNombre, y que la letra
     * del DNI se compruebe con el m�todo calcularLetraDNI, paso a implementar el resto de m�todos y sus comprobaciones de formato
     * utilizando las expresiones regulares declaradas
     */

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public String getTelefono() {

        return telefono;
    }

    public void setTelefono(String telefono) {

        if(telefono==null){

            throw new NullPointerException("ERROR: El tel�fono de un hu�sped no puede ser nulo.");
        }

        if(telefono.isBlank()){

            throw new IllegalArgumentException("ERROR: El tel�fono del hu�sped no tiene un formato v�lido.");
        }

        if (telefono.matches(ER_TELEFONO)) {

            this.telefono = telefono;

        } else {

            throw new IllegalArgumentException("ERROR: El tel�fono del hu�sped no tiene un formato v�lido.");

        }

    }

    public String getCorreo() {

        return correo;
    }

    public void setCorreo(String correo) {

        if(correo==null){

            throw new NullPointerException("ERROR: El correo de un hu�sped no puede ser nulo.");
        }

        if(correo.isBlank()){

            throw new IllegalArgumentException("ERROR: El correo del hu�sped no tiene un formato v�lido.");
        }

        if (correo.matches(ER_CORREO)) {

            this.correo = correo;

        } else {

            throw new IllegalArgumentException("ERROR: El correo del hu�sped no tiene un formato v�lido.");

        }
    }

    public String getDni() {

        return dni;
    }

    private void setDni(String dni) {

        if(dni==null){

            throw new NullPointerException("ERROR: El dni de un hu�sped no puede ser nulo.");
        }

        if(dni.isBlank()){

            throw new IllegalArgumentException("ERROR: El dni del hu�sped no tiene un formato v�lido.");
        }

        if (comprobarLetraDni(dni)) {

            this.dni = dni;


        }
    }

    public LocalDate getFechaNacimiento() {

        return fechaNacimiento;
    }

    /**Controlo con un condicional if-else que la fecha de nacimiento no sea posterior a la fecha actual
     * ni que tenga una diferencia de m�s de 120 a�os con la fecha actual, ya que es la edad m�xima registrada de longevidad*/
    public void setFechaNacimiento(LocalDate fechaNacimiento) {

        if(fechaNacimiento==null){

            throw new NullPointerException("ERROR: La fecha de nacimiento de un hu�sped no puede ser nula.");
        }

        if (fechaNacimiento.isAfter(LocalDate.now()) || fechaNacimiento.plusYears(120).isBefore(LocalDate.now())) {

            throw new IllegalArgumentException("Fecha de nacimiento no v�lida.");

        } else {

            this.fechaNacimiento = fechaNacimiento;
        }
    }

    /**6-Implemento el m�todo getIniciales que devuelve las iniciales del hu�sped aprovechando m�todo ya implementado
     * formateaNombre*/

    private String getIniciales() {
        // Llamo al m�todo formateaNombre y divido en palabras con un espacio entre ellas
        String[] palabras = formateaNombre(nombre).split(" ");
        StringBuilder iniciales = new StringBuilder(); //Creo un String Builder para almacenar las iniciales

        // Con un bucle for each, recorro la cadena de caracteres y tomo la primera letra de cada palabra
        for (String palabra : palabras) {

            iniciales.append(palabra.charAt(0)); //Agrega la primera letra de cada palabra al StringBuilder
        }

        return iniciales.toString(); //Devuelve las iniciales en may�sculas
    }

    /**7- Construyo el m�todo constructor con par�metros para hacer uso de los m�todos de modificaci�n (los setters)*/
    public Huesped (String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento){

        setNombre(formateaNombre(nombre));
        setDni(dni);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);

    }

    /**8- Creo el constructor copia asignandole los valores del hu�sped como par�metros a los atributos*/

    public Huesped (Huesped huesped){

        if(huesped==null){

            throw new NullPointerException("ERROR: No es posible copiar un hu�sped nulo.");
        }

        this.nombre = huesped.nombre;
        this.dni = huesped.dni;
        this.correo = huesped.correo;
        this.telefono = huesped.telefono;
        this.fechaNacimiento = huesped.fechaNacimiento;

    }

    /** 9-Importo java.util.Objects para comparar con el m�todo equals entre objetos, en este caso, para ver si
     * el nombre de la persona coincide con el DNI. Sobreescribo el m�todo equals, de tipo boolean.*/

    @Override
    public boolean equals(Object comprobarHuesped) { //Creo un objeto llamado comprobarHuesped

        if (this == comprobarHuesped) return true;  // Para comprobar si el objeto es el mismo que el del par�metro

        if (comprobarHuesped == null || getClass() != comprobarHuesped.getClass()) return false; // Comprueba si el objeto es nulo o si pertenece a otra clase

        Huesped huesped = (Huesped) comprobarHuesped; // Realiza un casting del objeto pasado como par�metro a la clase Huesped

        return Objects.equals(dni, huesped.dni); // Compara los DNIs de los dos objetos Huesped
    }

    @Override
    public int hashCode() {

        return Objects.hash(dni); // Utilizo Objects.hash para calcular un c�digo hash basado en el dni como identificador

    }

    /**10- Creo el m�todo toString para devolver informaci�n sobre los hu�spedes, donde pone %s se sustituye por los valores
     * declarados (nombre, dni, telefono, etc)*/

    @Override
    public String toString() {
        return String.format("nombre=%s ("+ getIniciales() +"), DNI=%s, correo=%s, tel�fono=%s, fecha nacimiento=%s",

                nombre, dni, correo, telefono, fechaNacimiento.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)));
    }
}
