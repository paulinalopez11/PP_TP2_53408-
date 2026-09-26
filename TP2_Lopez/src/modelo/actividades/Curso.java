package modelo.actividades;

import modelo.certificacion.Certificable;
import modelo.Estudiante;

public class Curso extends Actividad implements Certificable{
    private int nivel;

// creo el constructor con el super para que herede atributos
    public Curso (int id, String titulo, int cupoMaximo, int nivel){
        super(id, titulo, cupoMaximo);
        this.nivel=nivel;
    }


    @Override
    public double calcularCostoMateriales() {
        if (nivel<=10)
            return 3000;
        else
            return 0;
    }

    @Override
    public String getTipo() {
        return this.getClass().getName();
    }

    //implemento certificable

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "Certificado emitido por: " + ENTIDAD_EMISORA + ": se deja constancia de que " + estudiante.getNombre() + "asistió al curso " + getTitulo() + "de nivel " + + nivel + "." ;

    }
}
