package modelo;

import java.io.Serializable;

public class Estudiante implements Serializable {
    private String legajo;
    private String nombre;

    public Estudiante (String legajo, String nombre){
        this.legajo=legajo;
        this.nombre=nombre;
    }

    //creo los getters
    public String getLegajo(){
        return this.legajo;
    }

    public String getNombre() {
        return this.nombre; // Deja que otras clases lean el nombre de forma segura
    }
}
