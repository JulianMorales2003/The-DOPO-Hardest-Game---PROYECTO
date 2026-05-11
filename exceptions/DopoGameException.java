package exceptions;

/**
 * Excepción base del juego The DOPO Hardest Game.
 */
public class DopoGameException extends RuntimeException {

    public enum ErrorCode {
        NIVEL_NO_ENCONTRADO,
        NOMBRE_JUGADOR_INVALIDO,
        ERROR_INICIALIZACION,
        MOVIMIENTO_INVALIDO,
        ERROR_REINICIO
    }

    private final ErrorCode errorCode;

    /**
     * Crea una excepción con un código de error y un mensaje descriptivo.
     *
     * @param errorCode código que identifica el tipo de error
     * @param mensaje   descripción del error ocurrido
     */
    public DopoGameException(ErrorCode errorCode, String mensaje) {
        super(mensaje);
        this.errorCode = errorCode;
        System.err.println("[DOPO ERROR - " + errorCode + "] " + mensaje);
    }

    /**
     * Crea una excepción con código, mensaje y causa original.
     *
     * @param errorCode código que identifica el tipo de error
     * @param mensaje   descripción del error ocurrido
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
