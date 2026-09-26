package modelo.actividades;

import excepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.Inscripcion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public abstract class Actividad implements Serializable {
    private int id;
    private String titulo;
    private int cupoMaximo;
    private List<Inscripcion> inscripciones; //creo la lista de inscrip

    public static final int cupominimo;

    //le doy valor a cupominimo (estatico)
    static {
        cupominimo = 5;
        System.out.println("Inicializador estático: se cargó la clase modelo.modelo.actividades.Actividad.");
    }

    //creo constructor
    public Actividad (int id, String titulo, int cupoMaximo){
        this.id=id;
        this.titulo=titulo;
        this.cupoMaximo=cupoMaximo;
        this.inscripciones=new ArrayList<>(); //inicializo la lista
    }

    //creo método inscribir q recibe estudiante asociado a la inscripción
    public Inscripcion inscribir (Estudiante estudiante) throws CupoExcedidoException {
        //verificación para ver si hay cupo
        if (this.inscripciones.size() >= this.cupoMaximo) { //size es el tope de la fila
            throw new CupoExcedidoException("No se puede inscribir al estudiante "+ estudiante.getNombre() + ". Cupo máximo alcanzado");
        }

        //en caso de que si haya lugar, creo una nueva lista con los atributos de inscripcion
        Inscripcion nuevaFicha=new Inscripcion(LocalDate.now(), "Inscripto", estudiante);


        this.inscripciones.add(nuevaFicha); //agrego a la lista de inscripciones
        return nuevaFicha; //muestro la ficha

    }

    public void mostrarInscripciones() {
        if (this.inscripciones.isEmpty()) { //pregunta si está vacía la lista
            System.out.println("No hay alumnos inscriptos");
        } else {
            for (Inscripcion ficha : this.inscripciones) { //se posiciona en la lista
                System.out.println("   - modelo.modelo.Estudiante: " + ficha.getEstudiante().getNombre() + " | Fecha: " + ficha.getFecha());//trae el nombre del estudiante y la fecha de insc

            }
        }
    }

    //creo procedimiento mostrar identificacion
    public final void mostrarIdentification() {
        System.out.println("modelo.modelo.actividades.Actividad: " + getTipo() + " | Id: " + getId() + " | Título: " + getTitulo());

    }

    //métodos abstractos
    public abstract double calcularCostoMateriales();
    public abstract String getTipo();

    //uso los métodos get
    public int getId(){
        return this.id;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public int getCupoMaximo(){
        return this.cupoMaximo;
    }

    public List<Inscripcion> getInscripciones() {
        return this.inscripciones;
    }



}
