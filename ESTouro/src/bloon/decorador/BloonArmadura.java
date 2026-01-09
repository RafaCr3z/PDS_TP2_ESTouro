package bloon.decorador;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import bloon.Bloon;

/**
 * Adiciona uma armadura que absorve dano normal
 */
public class BloonArmadura extends BloonDecorator {

    private int armor;

    /**
     * Cria um bloon com armadura
     */
    public BloonArmadura(Bloon bloon, int armor) {
        super(bloon);
        this.armor = armor;
    }

    @Override
    public int pop(int estrago) {
        // Se tem armadura, absorve dano
        if (armor > 0) {
            armor -= estrago;
            // Se quebrou, o resto vai para o bloon
            if (armor < 0) {
                int sobra = -armor;
                armor = 0;
                // O dano restante passa para o bloon
                return super.pop(sobra);
            }
            // Dano totalmente absorvido pela armadura
            return 0;
        }
        // Sem armadura, passa o dano diretamente para o bloon
        return super.pop(estrago);
    }

    @Override
    public void desenhar(Graphics2D g) {
        super.desenhar(g);
        if (armor > 0) {
            Rectangle bounds = getBounds();
            g.setColor(Color.LIGHT_GRAY);
            g.setStroke(new java.awt.BasicStroke(2));
            g.drawOval(bounds.x, bounds.y, bounds.width, bounds.height);
            // desenhar indicador de armadura
            g.setColor(Color.WHITE);
            g.drawString("" + armor, bounds.x + bounds.width / 2 - 5, bounds.y + bounds.height / 2 + 5);
        }
    }

    @Override
    public Bloon clone() {
        return new BloonArmadura(bloon.clone(), armor);
    }
}
