package test;

import domain.Map;
import domain.Coin;
import domain.BasicEnemy;

/**
 * Pruebas unitarias para la clase Map.
 */
public class MapTest {

    /**
     * Ejecuta todas las pruebas de Map.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        System.out.println("=== Pruebas Map ===");
        testCreacion();
        testListasIniciaVacias();
        testAgregaCoin();
        testAgregaEnemy();
        testCellSize();
        System.out.println("=== Todas las pruebas pasaron ===");
    }

    /**
     * Verifica que el mapa se crea con las dimensiones correctas.
     */
    private static void testCreacion() {
        Map m = new Map(800, 520);
        assert m.width == 800  : "Ancho incorrecto";
        assert m.height == 520 : "Alto incorrecto";
        System.out.println("  [OK] testCreacion");
    }

    /**
     * Verifica que las listas de monedas y enemigos inician vacías.
     */
    private static void testListasIniciaVacias() {
        Map m = new Map(800, 520);
        assert m.coins.isEmpty()   : "Coins debe iniciar vacía";
        assert m.enemies.isEmpty() : "Enemies debe iniciar vacía";
        System.out.println("  [OK] testListasInicianVacias");
    }

    /**
     * Verifica que se puede agregar una moneda al mapa.
     */
    private static void testAgregaCoin() {
        Map m = new Map(800, 520);
        m.coins.add(new Coin(100, 200));
        assert m.coins.size() == 1 : "Debe haber 1 moneda";
        assert m.coins.get(0).x == 100 : "X de la moneda incorrecto";
        System.out.println("  [OK] testAgregaCoin");
    }

    /**
     * Verifica que se puede agregar un enemigo al mapa.
     */
    private static void testAgregaEnemy() {
        Map m = new Map(800, 520);
        m.enemies.add(new BasicEnemy(300, 200, 1, 0));
        assert m.enemies.size() == 1 : "Debe haber 1 enemigo";
        System.out.println("  [OK] testAgregaEnemy");
    }

    /**
     * Verifica que el tamaño de celda del grid es correcto.
     */
    private static void testCellSize() {
        Map m = new Map(800, 520);
        assert m.cellSize == 40 : "CellSize debe ser 40";
        System.out.println("  [OK] testCellSize");
    }
}
