package excepciones;

public class CupoExcedidoException extends Exception {  //hereda de exception
    public CupoExcedidoException(String mensaje){ //constructor que recibe el emnsaje explicativo del error
        super(mensaje); //se le pasa el mensaje a la clase padre exception para q dsp se pueda leer usando e.getMessage() en la clase App
    }
}
