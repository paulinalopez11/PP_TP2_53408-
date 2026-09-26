package modelo.actividades;

public class Charla extends Actividad {
    private String disertante;

    //creo el constructor con parámetros de la superclase+atributo de subclase

    public Charla(int id, String titulo, int cupoMaximo, String disertante) {
        super (id, titulo, cupoMaximo); //llamo al ocnstructor de la superclase
        this.disertante=disertante;

    }

    //invoco los métodos de la superclase para usarlos

    @Override
    public double calcularCostoMateriales() {
        return 0.0;
    }

    @Override
    public String getTipo() {
        return "modelo.modelo.actividades.Charla";
    }

    //métood get

    public String getDisertante() {
        return disertante;
    }
}