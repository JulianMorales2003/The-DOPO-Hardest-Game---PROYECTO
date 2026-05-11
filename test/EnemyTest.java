package test;

import domain.BasicEnemy;

/**
 * Pruebas unitarias para la clase abstracta Enemy
 * usando BasicEnemy como implementación concreta.
 */
public class EnemyTest {

    /**
     * Ejecuta todas las pruebas de Enemy.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        System.out.println("=== Pruebas Enemy ===");
        testAtributosIniciales();
        testColorNoNulo();
        testDireccionInicial();
        testDireccionDiagonal();
        testVelocidadPositiva();
        testTamanoPositivo();
        System.out.println("=== Todas las pruebas pasaron ===");
    }

    /**
     * Verifica que los atributos se inicializan correctamente.
     */
    private static void testAtributosIniciales() {
        BasicEnemy e = new BasicEnemy(100, 200, 1, 0);
        assert e.x == 100 : "X incorrecto";
        assert e.y == 200 : "Y incorrecto";
        assert e.dx == 1  : "DX incorrecto";
        assert e.dy == 0  : "DY incorrecto";
        System.out.println("  [OK] testAtributosIniciales");
    }

    /**
     * Verifica que el color del enemigo no es nulo.
     */
    private static void testColorNoNulo() {
        BasicEnemy e = new BasicEnemy(100, 200, 1, 0);
        assert e.color != null : "Color no debe ser nulo";
        System.out.println("  [OK] testColorNoNulo");
    }

    /**
     * Verifica que la dirección inicial horizontal es correcta.
     */
    private static void testDireccionInicial() {
        BasicEnemy e = new BasicEnemy(100, 200, 1, 0);
        assert e.dx == 1 : "DX debe ser 1 (derecha)";
        assert e.dy == 0 : "DY debe ser 0 (sin movimiento vertical)";
        System.out.println("  [OK] testDireccionInicial");
    }

    /**
     * Verifica que la dirección diagonal se asigna correctamente.
     */
    private static void testDireccionDiagonal() {
        BasicEnemy e = new BasicEnemy(100, 200, 1, 1);
        assert e.dx == 1 : "DX debe ser 1";
        assert e.dy == 1 : "DY debe ser 1";
        System.out.println("  [OK] testDireccionDiagonal");
    }

    /**
     * Verifica que la velocidad del enemigo es positiva.
     */
    private static void testVelocidadPositiva() {
        BasicEnemy e = new BasicEnemy(100, 200, 1, 0);
        assert e.speed > 0 : "Velocidad debe ser mayor a 0";
        System.out.println("  [OK] testVelocidadPositiva");
    }

    /**
     * Verifica que el tamaño del enemigo es positivo.
     */
    private static void testTamanoPositivo() {
        BasicEnemy e = new BasicEnemy(100, 200, 1, 0);
        assert e.size > 0 : "Tamaño debe ser mayor a 0";
        System.out.println("  [OK] testTamanoPositivo");
    }
}
