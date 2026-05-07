package exceptions;

/**
 * Excepción base del juego The DOPO Hardest Game.
 * Todas las excepciones propias del juego deben extender esta clase.
 */
public class DopoGameException extends RuntimeException {

    /**
     * Códigos de error posibles en el juego.
     */
    public enum ErrorCode {
        /** Error al cargar un nivel */
        NIVEL_NO_ENCONTRADO,
        /** El nombre del jugador está vacío o es inválido */
        NOMBRE_JUGADOR_INVALIDO,
        /** Error al inicializar las entidades del juego */
        ERROR_INICIALIZACION,
        /** Movimiento fuera de los límites del mapa */
        MOVIMIENTO_INVALIDO,
        /** Error al reiniciar el nivel */
        ERROR_REINICIO
    }

    private final ErrorCode errorCode;

    /**
     * Crea una excepción con un código de error y un mensaje descriptivo.
     *
     * @param errorCode código que identifica el tipo de error
     * @param mensaje   descripción detallada del error ocurrido
     */
    public DopoGameException(ErrorCode errorCode, String mensaje) {
        super(mensaje);
        this.errorCode = errorCode;
        System.err.println("[DOPO ERROR - " + errorCode + "] " + mensaje);
    }

    /**
     * Crea una excepción con un código de error, un mensaje y la causa original.
     *
     * @param errorCode código que identifica el tipo de error
     * @param mensaje   descripción detallada del error ocurrido
     * @param causa     excepción original que provocó este error
     */
    public DopoGameException(ErrorCode errorCode, String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.errorCode = errorCode;
        System.err.println("[DOPO ERROR - " + errorCode + "] " + mensaje);
    }

    /**
     * Retorna el código de error asociado a esta excepción.
     *
     * @return código de error de tipo {@link ErrorCode}
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}