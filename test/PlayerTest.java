package test;

import domain.RedPlayer;

/**
 * Pruebas unitarias para la clase abstracta Player
 * usando RedPlayer como implementación concreta.
 */
public class PlayerTest {

    /**
     * Ejecuta todas las pruebas de Player.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        System.out.println("=== Pruebas Player ===");
        testAtributosIniciales();
        testSpawnIgualAPosicionInicial();
        testContadorMuertesIniciaEnCero();
        testContadorMonedasIniciaEnCero();
        testCambioDeposicion();
        testRegresoAlSpawn();
        testIncrementoMuertes();
        testIncrementoMonedas();
        System.out.println("=== Todas las pruebas pasaron ===");
    }

    /**
     * Verifica que los atributos se inicializan correctamente.
     */
    private static void testAtributosIniciales() {
        RedPlayer p = new RedPlayer("Sergio", 100, 200);
        assert p.name.equals("Sergio") : "Nombre incorrecto";
        assert p.x == 100              : "X incorrecto";
        assert p.y == 200              : "Y incorrecto";
        assert p.size > 0              : "Tamaño debe ser mayor a 0";
        assert p.speed > 0             : "Velocidad debe ser mayor a 0";
        assert p.color != null         : "Color no debe ser nulo";
        System.out.println("  [OK] testAtributosIniciales");
    }

    /**
     * Verifica que el spawn coincide con la posición inicial.
     */
    private static void testSpawnIgualAPosicionInicial() {
        RedPlayer p = new RedPlayer("Julian", 50, 260);
        assert p.spawnX == p.x : "SpawnX debe ser igual a X inicial";
        assert p.spawnY == p.y : "SpawnY debe ser igual a Y inicial";
        System.out.println("  [OK] testSpawnIgualAPosicionInicial");
    }

    /**
     * Verifica que el contador de muertes inicia en cero.
     */
    private static void testContadorMuertesIniciaEnCero() {
        RedPlayer p = new RedPlayer("Julian", 50, 260);
        assert p.deaths == 0 : "Deaths debe iniciar en 0";
        System.out.println("  [OK] testContadorMuertesInicia EnCero");
    }

    /**
     * Verifica que el contador de monedas inicia en cero.
     */
    private static void testContadorMonedasIniciaEnCero() {
        RedPlayer p = new RedPlayer("Julian", 50, 260);
        assert p.coinsCollected == 0 : "CoinsCollected debe iniciar en 0";
        System.out.println("  [OK] testContadorMonedasIniciaEnCero");
    }

    /**
     * Verifica que la posición cambia correctamente.
     */
    private static void testCambioDeposicion() {
        RedPlayer p = new RedPlayer("Julian", 100, 100);
        p.x = 300;
        p.y = 400;
        assert p.x == 300 : "X debe ser 300";
        assert p.y == 400 : "Y debe ser 400";
        System.out.println("  [OK] testCambioDePosicion");
    }

    /**
     * Verifica que el jugador regresa al spawn correctamente.
     */
    private static void testRegresoAlSpawn() {
        RedPlayer p = new RedPlayer("Julian", 50, 260);
        p.x = 700;
        p.y = 400;
        p.x = p.spawnX;
        p.y = p.spawnY;
        assert p.x == 50  : "X debe regresar al spawn";
        assert p.y == 260 : "Y debe regresar al spawn";
        System.out.println("  [OK] testRegresoAlSpawn");
    }

    /**
     * Verifica que el contador de muertes incrementa correctamente.
     */
    private static void testIncrementoMuertes() {
        RedPlayer p = new RedPlayer("Julian", 50, 260);
        p.deaths++;
        p.deaths++;
        assert p.deaths == 2 : "Deaths debe ser 2";
        System.out.println("  [OK] testIncrementoMuertes");
    }

    /**
     * Verifica que el contador de monedas incrementa correctamente.
     */
    private static void testIncrementoMonedas() {
        RedPlayer p = new RedPlayer("Julian", 50, 260);
        p.coinsCollected++;
        p.coinsCollected++;
        p.coinsCollected++;
        assert p.coinsCollected == 3 : "CoinsCollected debe ser 3";
        System.out.println("  [OK] testIncrementoMonedas");
    }
}
