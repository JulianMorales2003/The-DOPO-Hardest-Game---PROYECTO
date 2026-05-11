package test;

import domain.Coin;

/**
 * Pruebas unitarias para la clase Coin.
 */
public class CoinTest {

    /**
     * Ejecuta todas las pruebas de Coin.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        System.out.println("=== Pruebas Coin ===");
        testCreacion();
        testRecoleccion();
        testReinicio();
        System.out.println("=== Todas las pruebas pasaron ===");
    }

    /**
     * Verifica que la moneda se crea con los valores correctos.
     */
    private static void testCreacion() {
        Coin c = new Coin(180, 140);
        assert c.x == 180          : "X inicial incorrecto";
        assert c.y == 140          : "Y inicial incorrecto";
        assert c.size == 20        : "Tamaño incorrecto";
        assert !c.collected        : "La moneda no debe estar recolectada al crearse";
        System.out.println("  [OK] testCreacion");
    }

    /**
     * Verifica que la moneda se marca como recolectada correctamente.
     */
    private static void testRecoleccion() {
        Coin c = new Coin(180, 140);
        c.collected = true;
        assert c.collected : "La moneda debería estar recolectada";
        System.out.println("  [OK] testRecoleccion");
    }

    /**
     * Verifica que la moneda se puede reiniciar a no recolectada.
     */
    private static void testReinicio() {
        Coin c = new Coin(180, 140);
        c.collected = true;
        c.collected = false;
        assert !c.collected : "La moneda debería reiniciarse a no recolectada";
        System.out.println("  [OK] testReinicio");
    }
}
