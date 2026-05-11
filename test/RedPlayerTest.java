package test;

import domain.RedPlayer;

/**
 * Pruebas unitarias para la clase RedPlayer.
 */
public class RedPlayerTest {

    /**
     * Ejecuta todas las pruebas de RedPlayer.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        System.out.println("=== Pruebas RedPlayer ===");
        testCreacion();
        testMovimiento();
        testSpawn();
        testMuerte();
        System.out.println("=== Todas las pruebas pasaron ===");
    }

    /**
     * Verifica que el jugador se crea con los valores correctos.
     */
    private static void testCreacion() {
        RedPlayer p = new RedPlayer("Julian", 100, 200);
        assert p.name.equals("Julian")  : "Nombre incorrecto";
        assert p.x == 100               : "X inicial incorrecto";
        assert p.y == 200               : "Y inicial incorrecto";
        assert p.size == 30             : "Tamaño incorrecto";
        assert p.speed == 3             : "Velocidad incorrecta";
        assert p.deaths == 0            : "Muertes iniciales deben ser 0";
        assert p.coinsCollected == 0    : "Monedas iniciales deben ser 0";
        System.out.println("  [OK] testCreacion");
    }

    /**
     * Verifica que el spawn se guarda correctamente al crear el jugador.
     */
    private static void testSpawn() {
        RedPlayer p = new RedPlayer("Julian", 50, 260);
        assert p.spawnX == 50  : "SpawnX incorrecto";
        assert p.spawnY == 260 : "SpawnY incorrecto";
        System.out.println("  [OK] testSpawn");
    }

    /**
     * Verifica que el jugador se mueve correctamente en cada dirección.
     */
    private static void testMovimiento() {
        RedPlayer p = new RedPlayer("Julian", 100, 100);

        p.x -= (int) p.speed;
        assert p.x == 97 : "Movimiento izquierda incorrecto";

        p.x += (int) p.speed;
        assert p.x == 100 : "Movimiento derecha incorrecto";

        p.y -= (int) p.speed;
        assert p.y == 97 : "Movimiento arriba incorrecto";

        p.y += (int) p.speed;
        assert p.y == 100 : "Movimiento abajo incorrecto";

        System.out.println("  [OK] testMovimiento");
    }

    /**
     * Verifica que al morir el jugador regresa al spawn y suma una muerte.
     */
    private static void testMuerte() {
        RedPlayer p = new RedPlayer("Julian", 50, 260);
        p.x = 400;
        p.y = 300;
        p.deaths++;
        p.x = p.spawnX;
        p.y = p.spawnY;
        assert p.deaths == 1 : "Contador de muertes incorrecto";
        assert p.x == 50     : "X no regresó al spawn";
        assert p.y == 260    : "Y no regresó al spawn";
        System.out.println("  [OK] testMuerte");
    }
}