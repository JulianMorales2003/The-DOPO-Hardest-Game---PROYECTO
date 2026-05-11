package test;

import domain.BasicEnemy;

/**
 * Pruebas unitarias para la clase BasicEnemy.
 */
public class BasicEnemyTest {

    /**
     * Ejecuta todas las pruebas de BasicEnemy.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        System.out.println("=== Pruebas BasicEnemy ===");
        testCreacion();
        testMovimientoHorizontal();
        testMovimientoVertical();
        testReboteBorde();
        System.out.println("=== Todas las pruebas pasaron ===");
    }

    /**
     * Verifica que el enemigo se crea con los valores correctos.
     */
    private static void testCreacion() {
        BasicEnemy e = new BasicEnemy(300, 200, 1, 0);
        assert e.x == 300    : "X inicial incorrecto";
        assert e.y == 200    : "Y inicial incorrecto";
        assert e.dx == 1     : "DX inicial incorrecto";
        assert e.dy == 0     : "DY inicial incorrecto";
        assert e.size == 28  : "Tamaño incorrecto";
        assert e.speed == 2.5: "Velocidad incorrecta";
        System.out.println("  [OK] testCreacion");
    }

    /**
     * Verifica que el enemigo se mueve horizontalmente.
     */
    private static void testMovimientoHorizontal() {
        BasicEnemy e = new BasicEnemy(100, 100, 1, 0);
        int xAntes = e.x;
        e.update(800, 520);
        assert e.x > xAntes : "El enemigo debería moverse a la derecha";
        assert e.y == 100   : "Y no debería cambiar en movimiento horizontal";
        System.out.println("  [OK] testMovimientoHorizontal");
    }

    /**
     * Verifica que el enemigo se mueve verticalmente.
     */
    private static void testMovimientoVertical() {
        BasicEnemy e = new BasicEnemy(100, 100, 0, 1);
        int yAntes = e.y;
        e.update(800, 520);
        assert e.y > yAntes : "El enemigo debería moverse hacia abajo";
        assert e.x == 100   : "X no debería cambiar en movimiento vertical";
        System.out.println("  [OK] testMovimientoVertical");
    }

    /**
     * Verifica que el enemigo rebota al llegar al borde derecho del mapa.
     */
    private static void testReboteBorde() {
        BasicEnemy e = new BasicEnemy(770, 100, 1, 0);
        double dxAntes = e.dx;
        e.update(800, 520);
        assert e.dx == -dxAntes : "El enemigo debería rebotar en el borde derecho";
        System.out.println("  [OK] testReboteBorde");
    }
}
