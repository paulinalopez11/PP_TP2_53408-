package modelo.actividades;

import modelo.Estudiante;
import modelo.certificacion.Certificable;

public class Taller extends Actividad implements Certificable {
    private boolean requiereNotebook;

    //constructor
    public Taller(int id, String titulo, int cupoMaximo, boolean requiereNotebook) {
        super(id, titulo, cupoMaximo); //llamo al constructor de la superclase
        this.requiereNotebook = requiereNotebook;
    }

    //métodos

    @Override
    public double calcularCostoMateriales() {
        if (this.requiereNotebook){ //pregunta si requiere notebook
            return 5000.0;
        }
        return 2000.0;
    }

    @Override
    public String getTipo() {
        return "modelo.modelo.actividades.Taller";
    }

    //método set, como es booleano, se coloca "is" y no get

    public boolean isRequiereNotebook() {
        return requiereNotebook;
    }

    //implemento el certificable en taller

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "Certificado emitido por " + ENTIDAD_EMISORA + ": se deja constancia de que " + estudiante.getNombre() + " partició en el taller -" + getTitulo() + " -";

    }
}