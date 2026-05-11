package domain.levels;

import domain.BasicEnemy;
import domain.Coin;

/**
 * Nivel 3 — El Infierno.
 * Máxima dificultad: enemigos muy rápidos en todas direcciones,
 * monedas en las esquinas más peligrosas del mapa.
 */
public class Level3 extends Level {

    /**
     * Crea el nivel 3 con su configuración de enemigos y monedas.
     */
    public Level3() {
        super(3, "El Infierno");
    }

    /**
     * Inicializa los enemigos y monedas del nivel 3.
     * Velocidades extremas y posiciones diseñadas para el máximo caos.
     */
    @Override
    protected void inicializar() {

        // ── Muro horizontal superior ───────────────────────────────────────
        BasicEnemy e1 = new BasicEnemy(100,  60,  1, 0);
        e1.speed = 5.5;
        enemies.add(e1);

        BasicEnemy e2 = new BasicEnemy(350,  60, -1, 0);
        e2.speed = 5.5;
        enemies.add(e2);

        BasicEnemy e3 = new BasicEnemy(600,  60,  1, 0);
        e3.speed = 5.5;
        enemies.add(e3);

        // ── Muro horizontal inferior ───────────────────────────────────────
        BasicEnemy e4 = new BasicEnemy(100, 440, -1, 0);
        e4.speed = 5.5;
        enemies.add(e4);

        BasicEnemy e5 = new BasicEnemy(350, 440,  1, 0);
        e5.speed = 5.5;
        enemies.add(e5);

        BasicEnemy e6 = new BasicEnemy(600, 440, -1, 0);
        e6.speed = 5.5;
        enemies.add(e6);

        // ── Columnas verticales ────────────────────────────────────────────
        BasicEnemy e7 = new BasicEnemy(200,  80, 0,  1);
        e7.speed = 5.0;
        enemies.add(e7);

        BasicEnemy e8 = new BasicEnemy(400, 300, 0, -1);
        e8.speed = 5.0;
        enemies.add(e8);

        BasicEnemy e9 = new BasicEnemy(600, 150, 0,  1);
        e9.speed = 5.5;
        enemies.add(e9);

        BasicEnemy e10 = new BasicEnemy(300, 420, 0, -1);
        e10.speed = 5.0;
        enemies.add(e10);

        BasicEnemy e11 = new BasicEnemy(500,  80, 0,  1);
        e11.speed = 6.0;
        enemies.add(e11);

        // ── Diagonales extremos ────────────────────────────────────────────
        BasicEnemy e12 = new BasicEnemy(150, 150,  1,  1);
        e12.speed = 5.0;
        enemies.add(e12);

        BasicEnemy e13 = new BasicEnemy(550, 150, -1,  1);
        e13.speed = 5.0;
        enemies.add(e13);

        BasicEnemy e14 = new BasicEnemy(150, 350,  1, -1);
        e14.speed = 5.0;
        enemies.add(e14);

        BasicEnemy e15 = new BasicEnemy(550, 350, -1, -1);
        e15.speed = 5.0;
        enemies.add(e15);

        BasicEnemy e16 = new BasicEnemy(350, 250,  1,  1);
        e16.speed = 5.5;
        enemies.add(e16);

        // ── Monedas en las posiciones más difíciles ────────────────────────
        coins.add(new Coin(180,  55));
        coins.add(new Coin(380,  55));
        coins.add(new Coin(580,  55));
        coins.add(new Coin(180, 435));
        coins.add(new Coin(380, 435));
        coins.add(new Coin(580, 435));
        coins.add(new Coin(370, 240));
        coins.add(new Coin(660, 240));
        coins.add(new Coin( 80, 240));
    }
}