package domain.levels;

import domain.BasicEnemy;
import domain.Coin;

/**
 * Nivel 2 — La Tormenta.
 * Más enemigos, más rápidos y con patrones más caóticos.
 */
public class Level2 extends Level {

    /**
     * Crea el nivel 2 con su configuración de enemigos y monedas.
     */
    public Level2() {
        super(2, "La Tormenta");
    }

    /**
     * Inicializa los enemigos y monedas del nivel 2.
     * Más enemigos que el nivel 1 y con mayor velocidad.
     */
    @Override
    protected void inicializar() {

        // ── Enemigos rápidos horizontales ──────────────────────────────────
        BasicEnemy e1 = new BasicEnemy(100, 100,  1, 0);
        e1.speed = 4.0;
        enemies.add(e1);

        BasicEnemy e2 = new BasicEnemy(400, 200, -1, 0);
        e2.speed = 4.0;
        enemies.add(e2);

        BasicEnemy e3 = new BasicEnemy(200, 380,  1, 0);
        e3.speed = 4.5;
        enemies.add(e3);

        BasicEnemy e4 = new BasicEnemy(600, 440, -1, 0);
        e4.speed = 4.0;
        enemies.add(e4);

        // ── Enemigos rápidos verticales ────────────────────────────────────
        BasicEnemy e5 = new BasicEnemy(300,  50, 0,  1);
        e5.speed = 4.5;
        enemies.add(e5);

        BasicEnemy e6 = new BasicEnemy(500, 300, 0, -1);
        e6.speed = 4.0;
        enemies.add(e6);

        BasicEnemy e7 = new BasicEnemy(650, 100, 0,  1);
        e7.speed = 5.0;
        enemies.add(e7);

        // ── Enemigos diagonales rápidos ────────────────────────────────────
        BasicEnemy e8 = new BasicEnemy(200, 200,  1,  1);
        e8.speed = 3.5;
        enemies.add(e8);

        BasicEnemy e9 = new BasicEnemy(500, 100, -1,  1);
        e9.speed = 3.5;
        enemies.add(e9);

        BasicEnemy e10 = new BasicEnemy(350, 400,  1, -1);
        e10.speed = 4.0;
        enemies.add(e10);

        BasicEnemy e11 = new BasicEnemy(600, 300, -1, -1);
        e11.speed = 3.5;
        enemies.add(e11);

        BasicEnemy e12 = new BasicEnemy(150, 300,  1, -1);
        e12.speed = 4.5;
        enemies.add(e12);

        // ── Monedas en zonas extremadamente peligrosas ────────────────────
        coins.add(new Coin(290,  60));
        coins.add(new Coin(490,  60));
        coins.add(new Coin(640, 200));
        coins.add(new Coin(640, 380));
        coins.add(new Coin(370, 240));
        coins.add(new Coin(150, 200));
        coins.add(new Coin(150, 380));
        coins.add(new Coin(370, 420));
    }
}