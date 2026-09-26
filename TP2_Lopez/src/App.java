//se le avisa a java q vamos a usar listas dinámicas
import modelo.EventoUniversitario;
import modelo.Sala;
import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Curso;
import modelo.actividades.Taller;
import modelo.Inscripcion;
import modelo.certificacion.Certificable;
import modelo.Estudiante;
import modelo.actividades.Curso;
import excepciones.CupoExcedidoException;



import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException; //para la persistencia

public class App {
    public static void main(String[] args) {

        //EJERCICIO 2
        //creo la lista para guardar objetos de tipo modelo.modelo.Estudiante
        List<Estudiante> estudiantes = new ArrayList<>();

        //Para agregar estudiantes a la lista uso .add()
        estudiantes.add(new Estudiante("53408", "Paulina Lopez"));
        estudiantes.add(new Estudiante("40800", "Adrián Gómez"));
        estudiantes.add(new Estudiante("42500", "María González"));

        //Recorre la lista para imprimir los nombres
        System.out.println("--- LISTA DE ESTUDIANTES ---");
        for (Estudiante i : estudiantes) { // para cada i(est) de la lista estudiantes del tipo modelo.modelo.Estudiante, imprimir
            System.out.println("Legajo: " + i.getLegajo() + " | Nombre: " + i.getNombre());
        }

        //creo eventos

        EventoUniversitario evento1 = new EventoUniversitario("A01", "Programacion en Java", 7000, false);
        EventoUniversitario evento2 = new EventoUniversitario("A02", "Base de datos", 0, true);

        //creo el objeto sala y le paso la sala al evento (usando el método)
        Sala sala1 = new Sala(1, "Laboratorio de sistemas 1");
        evento1.asignarSala(sala1);
        Sala sala2 = new Sala(1, "Clase de algoritmos");
        evento2.asignarSala(sala2);

        //creo modelo.modelo.actividades en los eventos
        evento1.crearActividad(01, "Programación", 2, "taller", "Prof Castro", true, 0);
        evento2.crearActividad(02, "Pseudocódigo", 40, "charla", "Prof. Gómez", false, 0);
        evento1.crearActividad(03, "Curso java", 2, "curso", null, false, 2);

        //estructura de try-catch-finally de excepciones
        System.out.println("===INSCRIBIENDO ALUMNOS EN EVENTO===");
        try {
            //insc en curso
            evento1.getActividades().get(1).inscribir(estudiantes.get(0));
            evento1.getActividades().get(1).inscribir(estudiantes.get(2));

            evento1.getActividades().get(0).inscribir(estudiantes.get(0));
            evento1.getActividades().get(0).inscribir(estudiantes.get(1));
            evento1.getActividades().get(0).inscribir(estudiantes.get(2)); //supera cupo


            evento2.getActividades().get(0).inscribir(estudiantes.get(1));
            evento2.getActividades().get(0).inscribir(estudiantes.get(2));

        } catch (CupoExcedidoException e) {
            //manejo de la excepción
            System.out.println("Error al inscribir (Cupo Excedido): " + e.getMessage());
        }

        //Emisión de certificados
        for (Actividad actividad : evento1.getActividades()) {
            if (actividad instanceof Certificable certificable) {
                System.out.println("\nCERTIFICADOS EMITIDOS PARA LA ACTIVIDAD " + actividad.getTitulo());
                for (Inscripcion inscripcion : actividad.getInscripciones()) {
                    String certificado = certificable.generarCertificado(inscripcion.getEstudiante());
                    System.out.println(certificado);
                }
            }
        }

        System.out.println("\n\n************  Filtrando la lista de actividades **********");
        List<Taller> talleres = evento1.filtrarActividadesPorTipo(Taller.class);
        List<Curso> cursos = evento1.filtrarActividadesPorTipo(Curso.class);
        List<Charla> charlas = evento1.filtrarActividadesPorTipo(Charla.class);

        System.out.println("Actividades filtradas por tipo usando método parametrizado acotado:");
        System.out.println("Charlas encontradas: " + charlas.size());
        System.out.println("Talleres encontrados: " + talleres.size());
        System.out.println("Cursos encontrados: " + cursos.size());

        System.out.println("\n************ Calculando costos de las listas filtradas **********");
        System.out.println("Costo de materiales de talleres: $" + evento1.calcularCostoMateriales(talleres));
        System.out.println("Costo de materiales de cursos: $" + evento1.calcularCostoMateriales(cursos));
        System.out.println("Costo de materiales de todas las actividades: $" + evento1.calcularCostoMateriales(evento1.getActividades()));


        //muestra datos del evento en memoria RAM
        evento1.mostrarDatos();

        //persistencia
        try {
            System.out.println("Almacenando el evento Id° " + evento1.getId());
            evento1.persistirEvento();
            System.out.println("Evento guardado exitosamente");
        } catch (
                FileNotFoundException e) {
            System.out.println("Imposible guardar el evento id° " + evento1.getId() + ". Error al buscar el archivo: " + e.getMessage());
        } catch (
                IOException e) {
            System.out.println("Imposible guardar el evento id° " + evento1.getId() + ".");
            e.printStackTrace();
        }

        //deserialización (rec el archivo desde binario)
        try {
            EventoUniversitario copiaDesdeArchivo = evento1.recuperarEvento(evento1.getId());
            System.out.println("Mostrando el evento id° " + evento1.getId() + " almacenado previamente");
            copiaDesdeArchivo.mostrarDatos();
        } catch (
                ClassNotFoundException e) {
            System.out.println("No fue posible reconstruir el objeto almacenado: " + e.getMessage());
        } catch (
                FileNotFoundException e) {
            System.out.println("Imposible recuperar el evento id° " + evento1.getId() + ".Error al buscar el archivo: " + e.getMessage());
        } catch (
                IOException e) {
            System.out.println("Imposible recuperar el evento id° " + evento1.getId() + ".");
            e.printStackTrace();
        } finally { //cumpke con el try-catch-finally
            System.out.println("===PROCESO DE PRUEBA DE PERSISTENCIA Y EXCEPCIONES FINALIZADO");
        }
    }
}