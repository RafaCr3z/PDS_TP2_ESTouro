package torre.projetil;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import bloon.Bloon;
import prof.jogos2D.image.ComponenteSimples;
import prof.jogos2D.image.ComponenteVisual;
import prof.jogos2D.util.ImageLoader;

/**
 * Projétil visual para a Torre Sniper.
 * Não causa dano (o dano é imediato na torre), apenas desenha o rastro do tiro.
 */
public class TiroSniper implements Projetil {

    private Point inicio;
    private Point fim;
    private int vidaUtil = 5; // dura 5 frames (aprox 0.25s)

    public TiroSniper(Point inicio, Point fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public void draw(Graphics2D g) {
        if (vidaUtil > 0) {
            g.setColor(new Color(255, 255, 0, 128 + (vidaUtil * 20))); // Amarelo a desaparecer
            g.setStroke(new BasicStroke(3));
            g.drawLine(inicio.x, inicio.y, fim.x, fim.y);
        }
    }

    @Override
    public void atualiza(List<Bloon> bloons) {
        vidaUtil--;
    }

    @Override
    public ComponenteVisual getComponente() {
        return new ComponenteSimples(ImageLoader.getLoader().getImage("data/misc/pop.png")); // Dummy
    }

    @Override
    public int getEstrago() {
        return 0;
    }

    @Override
    public void setPosicao(Point p) {
    }

    @Override
    public int getAlcance() {
        return 0;
    }

    @Override
    public void setAlcance(int alcance) {
    }
}
