package test;

import exceptions.DopoGameException;

/**
 * Pruebas unitarias para la clase DopoGameException.
 */
public class DopoGameExceptionTest {

    /**
     * Ejecuta todas las pruebas de DopoGameException.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        System.out.println("=== Pruebas DopoGameException ===");
        testMensaje();
        testErrorCode();
        testConCausa();
        System.out.println("=== Todas las pruebas pasaron ===");
    }

    /**
     * Verifica que el mensaje de la excepción se guarda correctamente.
     */
    private static void testMensaje() {
        DopoGameException ex = new DopoGameException(
            DopoGameException.ErrorCode.NOMBRE_JUGADOR_INVALIDO,
            "El nombre no puede estar vacío"
        );
        assert ex.getMessage().equals("El nombre no puede estar vacío")
            : "Mensaje incorrecto";
        System.out.println("  [OK] testMensaje");
    }

    /**
     * Verifica que el código de error se asigna correctamente.
     */
    private static void testErrorCode() {
        DopoGameException ex = new DopoGameException(
            DopoGameException.ErrorCode.NIVEL_NO_ENCONTRADO,
            "Nivel 3 no existe"
        );
        assert ex.getErrorCode() == DopoGameException.ErrorCode.NIVEL_NO_ENCONTRADO
            : "Código de error incorrecto";
        System.out.println("  [OK] testErrorCode");
    }

    /**
     * Verifica que la excepción guarda correctamente la causa original.
     */
    private static void testConCausa() {
        Throwable causa = new RuntimeException("Error original");
        DopoGameException ex = new DopoGameException(
            DopoGameException.ErrorCode.ERROR_INICIALIZACION,
            "Error al inicializar",
            causa
        );
        assert ex.getCause() == causa : "Causa original incorrecta";
        System.out.println("  [OK] testConCausa");
    }
}
