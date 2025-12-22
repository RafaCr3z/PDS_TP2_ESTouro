package bloon.decorador;

import java.awt.Graphics2D;

import bloon.Bloon;

/**
 * Decorador que concede imunidade a certos tipos de dano.
 */
public class BloonImune extends BloonDecorator {

    private boolean imunePerfurante;
    private boolean imuneExplosao;

    public BloonImune(Bloon bloon, boolean imunePerfurante, boolean imuneExplosao) {
        super(bloon);
        this.imunePerfurante = imunePerfurante;
        this.imuneExplosao = imuneExplosao;
    }

    @Override
    public int pop(int estrago) {
        if (imunePerfurante)
            return estrago; // Retorna o estrago sem sofrer dano (projétil passa mas não aleija)
        // Ou devolve 0?
        // Bloon.pop docs: "Se o bloon não aguentar ... devolve qual o estrago que não
        // suportou."
        // Se suporta tudo (ignora), devia retornar 0?
        // Mas se retornar 0, o projétil pode pensar que acertou e consumiu "estrago"?
        // Normalmente return 0 significa "damage handled".
        // Se eu retorno "estrago", significa "não aguentei (sobrou) this much damage"?
        // No, BloonSimples returns estrago if alive?
        // Let's check BloonSimples.pop.
        return super.pop(estrago);
    }

    @Override
    public void explode(int estrago) {
        if (imuneExplosao)
            return; // Ignora explosão
        super.explode(estrago);
    }

    @Override
    public Bloon clone() {
        return new BloonImune(bloon.clone(), imunePerfurante, imuneExplosao);
    }
}
