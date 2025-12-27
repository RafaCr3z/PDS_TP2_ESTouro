package bloon.decorador;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import bloon.Bloon;

/**
 * Decorador que adiciona uma armadura ao bloon.
 * A armadura absorve dano até quebrar.
 */
public class BloonArmadura extends BloonDecorator {

    private int armor;

    /**
     * Cria um bloon com armadura
     * 
     * @param bloon o bloon a decorar
     * @param armor a quantidade de dano que a armadura suporta
     */
    public BloonArmadura(Bloon bloon, int armor) {
        super(bloon);
        this.armor = armor;
    }

    @Override
    public int pop(int estrago) {
        // Verifica se a armadura ainda está ativa
        if (armor > 0) {
            // Subtrai o dano da armadura
            armor -= estrago;
            // Se a armadura for quebrada (armor < 0), calcula o dano restante
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
