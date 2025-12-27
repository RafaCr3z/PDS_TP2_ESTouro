package bloon.decorador;

import java.awt.Graphics2D;

import bloon.Bloon;

/**
 * Decorador que concede imunidade a certos tipos de dano.
 */
public class BloonImune extends BloonDecorator {

    // Indica se o bloon é imune a dano perfurante
    private boolean imunePerfurante;
    // Indica se o bloon é imune a dano explosivo
    private boolean imuneExplosao;

    /**
     * Cria um bloon com imunidades específicas.
     */
    public BloonImune(Bloon bloon, boolean imunePerfurante, boolean imuneExplosao) {
        super(bloon);
        this.imunePerfurante = imunePerfurante;
        this.imuneExplosao = imuneExplosao;
    }

    @Override
    public int pop(int estrago) {
        // Se imune a dano perfurante, retorna o estrago sem sofrer dano
        if (imunePerfurante)
            return estrago; // Retorna o estrago sem sofrer dano (projétil passa mas não aleija)
        return super.pop(estrago);
    }

    @Override
    public void explode(int estrago) {
        // Se imune a dano explosivo, ignora a explosão
        if (imuneExplosao)
            return; // Ignora explosão
        super.explode(estrago);
    }

    @Override
    public Bloon clone() {
        return new BloonImune(bloon.clone(), imunePerfurante, imuneExplosao);
    }
}
