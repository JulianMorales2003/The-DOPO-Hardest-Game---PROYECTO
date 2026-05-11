package domain.levels;

import domain.BasicEnemy;
import domain.Coin;

/**
 * Nivel 1 — El Corredor del Caos.
 * Múltiples enemigos en distintas direcciones
 * bloqueando el paso hacia la meta.
 */
public class Level1 extends Level {

    /**
     * Crea el nivel 1 con su configuración de enemigos y monedas.
     */
    public Level1() {
        super(1, "El Corredor del Caos");
    }

    /**
     * Inicializa los enemigos y monedas del nivel 1.
     * Patrón difícil: enemigos horizontales, verticales y diagonales
     * bloqueando los caminos hacia las monedas.
     */
    @Override
    protected void inicializar() {

        // ── Enemigos horizontales — bloquean el paso central ──────────────
        enemies.add(new BasicEnemy(150,  80,  1,  0));
        enemies.add(new BasicEnemy(300, 160,  1,  0));
        enemies.add(new BasicEnemy(450, 240,  1,  0));
        enemies.add(new BasicEnemy(600, 320, -1,  0));
        enemies.add(new BasicEnemy(300, 400, -1,  0));
        enemies.add(new BasicEnemy(150, 440,  1,  0));

        // ── Enemigos verticales — cierran columnas ─────────────────────────
        enemies.add(new BasicEnemy(200,  60,  0,  1));
        enemies.add(new BasicEnemy(400, 200,  0,  1));
        enemies.add(new BasicEnemy(550,  40,  0,  1));
        enemies.add(new BasicEnemy(680, 300,  0, -1));

        // ── Enemigos diagonales — los más peligrosos ───────────────────────
        enemies.add(new BasicEnemy(250, 130,  1,  1));
        enemies.add(new BasicEnemy(500, 350, -1,  1));
        enemies.add(new BasicEnemy(350, 260,  1, -1));

        // ── Monedas — ubicadas en zonas de alto riesgo ────────────────────
        coins.add(new Coin(190, 70));
        coins.add(new Coin(370, 130));
        coins.add(new Coin(530, 70));
        coins.add(new Coin(190, 390));
        coins.add(new Coin(530, 390));
        coins.add(new Coin(660, 240));
        coins.add(new Coin(370, 430));
    }
}