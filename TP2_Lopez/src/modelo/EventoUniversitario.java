package modelo;

import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Taller;
import modelo.actividades.Curso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventoUniversitario implements Serializable {
    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;
    private Sala sala; //creo como atributo al ser agregación
    private List<Actividad> actividades; //creo atributo por ser composición

    //atributo de clase
    private static int cantidadEventos = 0; //lo inicializé

    //constructor
    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        this.actividades= new ArrayList<>(); //inicializo la lista

        cantidadEventos++;
    }

    //constructor copia
    public EventoUniversitario(EventoUniversitario otroEvento) {
        this.id = otroEvento.id;
        this.titulo = otroEvento.titulo;
        this.costoBase = otroEvento.costoBase;
        this.gratuito = otroEvento.gratuito;
        this.actividades= new ArrayList<>();

        cantidadEventos++;
    }

    //creo método
    public double calcularCostoEstimado() {
        if (this.gratuito) {
            return 0.0;
        }
        double costoActividades=0.0; //inicio una variable para guardar valores temporales
        for (Actividad actividad: actividades) { // recorre la lista
            costoActividades += actividad.calcularCostoMateriales();
        }
        return (this.costoBase+costoActividades)*1.21;
    }

    //recibe como argumento tipo sala

    public void asignarSala(Sala sala) { //inicializo sala
        this.sala = sala;
    }


    //creo un procedimiento
    public void crearActividad(int id, String titulo, int cupo, String tipo, String disertante, boolean requiereNotebook, int nivel) { //se agregan demás atributos creados en subclases
        //Caso CHARLA
        if (tipo.equalsIgnoreCase("charla") || tipo.contains("Charla")) {
            this.actividades.add(new Charla(id, titulo, cupo, disertante));
        }
        //Caso TALLER
        else if (tipo.equalsIgnoreCase("taller") || tipo.contains("Taller")) {
            this.actividades.add(new Taller(id, titulo, cupo, requiereNotebook));
        }
        //Caso CURSO
        else if (tipo.equalsIgnoreCase("curso") || tipo.contains("Curso")) {
            this.actividades.add(new Curso(id, titulo, nivel, cupo));
        }
        else {
            System.out.println("Tipo de actividad no reconocido.");
        }

    }

    //get
    public List<Actividad> getActividades(){
        return this.actividades;
    }

    //método parametrizado acotado
    //@param tipo = clase concreta que se desea filtrar
    //@param <T> = tipo de actividad a recuperar
    //@return = lista tipada con las act del tipo indicado
    public <T extends Actividad> List <T> filtrarActividadesPorTipo(Class<T> tipo){
        List<T> resultado = new ArrayList<>();
        for (Actividad actividad : actividades) {
            if (tipo.isInstance(actividad)) {
                resultado.add(tipo.cast(actividad));
            }
        }
        return resultado;
    }

    //Método que usa wildcard acotado
    //@param actividadads lista de act
    //@return costo total de materiales
    public double calcularCostoMateriales(List<? extends Actividad> actividades) {
        double total = 0.0;
        for (Actividad actividad : actividades) {
            total += actividad.calcularCostoMateriales();
        }
        return total;
    }

    //creo método

    public void mostrarDatos() {
        System.out.println("===============");
        System.out.println("ID: " + id);
        System.out.println("Título: " + titulo);
        System.out.println("Costo base con IVA: " + this.calcularCostoEstimado());
        System.out.println("Gratuito: " + gratuito); //xq se pone q es gratuito y arriba un precio?

        //muetsra la sala asignada
        System.out.println("modelo.Sala asignada: "+ (sala != null ? sala.getNombre() : "Sin sala" + "\n") );

        System.out.println("Actividades: ");
        System.out.println("___________");


        //se recorre la lista de actividadaes de este evento
        for (Actividad actividad: actividades){
            actividad.mostrarIdentification();
            System.out.println("Cupo máximo: "+ actividad.getCupoMaximo());
            actividad.mostrarInscripciones();
            System.out.println ("==========");

        }
    }

    public static int getCantidadEventos() {
        return cantidadEventos;
    }


    //creo método de persistencia
    public boolean persistirEvento() throws IOException {
        String nombreArchivo = "evento_" + this.id + ".dat"; //define dinámicamente el nombre del archivo en disco usando el id del evento
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(this);
            return true; //para verificar que la operación finalizó correctamente

        }

    }

    // creo método de descerialización
    public EventoUniversitario recuperarEvento(String id) throws IOException, ClassNotFoundException {
        String nombreArchivo = "evento_" + id + ".dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            return (EventoUniversitario) ois.readObject();
        }
    }

    //método get
    public String getId(){ //para mostrar qn es el estudiante
        return this.id;
    }
    public String getTitulo(){ //para mostrar qn es el estudiante
        return this.titulo;
    }
    public double getcostoBase(){ //para mostrar qn es el estudiante
        return this.costoBase;
    }
    public boolean getGratuito(){ //para mostrar qn es el estudiante
        return this.gratuito;
    }
    public Sala getSala(){ //para mostrar qn es el estudiante
        return this.sala;
    }


}