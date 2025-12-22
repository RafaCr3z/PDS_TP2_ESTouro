package bloon.decorador;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import bloon.Bloon;

/**
 * Decorador que adiciona um escudo ao bloon.
 * O escudo absorve dano explosivo até quebrar.
 */
public class BloonEscudo extends BloonDecorator {

    private int shield;

    /**
     * Cria um bloon com escudo
     * 
     * @param bloon  o bloon a decorar
     * @param shield a quantidade de dano explosivo que o escudo suporta
     */
    public BloonEscudo(Bloon bloon, int shield) {
        super(bloon);
        this.shield = shield;
    }

    @Override
    public void explode(int estrago) {
        if (shield > 0) {
            shield -= estrago;
            if (shield < 0) {
                int sobra = -shield;
                shield = 0;
                // O dano restante passa para o bloon
                super.explode(sobra);
            }
            // Dano totalmente absorvido
            return;
        }
        super.explode(estrago);
    }

    @Override
    public void desenhar(Graphics2D g) {
        super.desenhar(g);
        if (shield > 0) {
            Rectangle bounds = getBounds();
            g.setColor(Color.CYAN);
            g.setStroke(new java.awt.BasicStroke(2));
            g.drawOval(bounds.x - 2, bounds.y - 2, bounds.width + 4, bounds.height + 4); // Draw slightly outside
            // desenhar indicador de escudo
            g.setColor(Color.WHITE);
            g.drawString("" + shield, bounds.x + bounds.width / 2 - 5, bounds.y + bounds.height / 2 + 15);
        }
    }

    @Override
    public Bloon clone() {
        return new BloonEscudo(bloon.clone(), shield);
    }
}
