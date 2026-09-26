package modelo;

import java.io.Serializable;
import java.time.LocalDate; //se le debe importar la herramienta a java

public class Inscripcion implements Serializable {
    private LocalDate fecha; //se usa siempre para fecha
    private String estado;
    private Estudiante estudiante; //estudiate asociado a la inscripcion

    //creo el constructor
    public Inscripcion (LocalDate fecha, String estado, Estudiante estudiante){
        this.fecha=fecha;
        this.estado=estado;
        this.estudiante=estudiante; //debe existir xq una inscipción no puede no tener alumno

    }

    //por lo que son privados, asigno get para poder mostrar

    public Estudiante getEstudiante(){ //para mostrar qn es el estudiante
        return this.estudiante;
    }

    public LocalDate getFecha(){
        return this.fecha;
    }

    public String getEstado(){
        return this.estado;
    }
}
