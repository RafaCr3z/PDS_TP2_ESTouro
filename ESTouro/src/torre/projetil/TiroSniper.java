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
 */
public class TiroSniper implements Projetil {

    /** Ponto de início do tiro (posição da torre). */
    private Point inicio;
    /** Ponto de fim do tiro (posição do alvo). */
    private Point fim;
    /** Vida útil do efeito visual em frames (dura 5 frames (aprox 0.25s)). */
    private int vidaUtil = 5;

    /**
     * Construtor para criar um projétil visual do sniper.
     * 
     */
    public TiroSniper(Point inicio, Point fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    /**
     * Desenha o rastro do tiro na tela, com efeito de desaparecimento gradual.
     * 
     */
    @Override
    public void draw(Graphics2D g) {
        if (vidaUtil > 0) {
            // Define a cor amarela com transparência que diminui com o tempo
            g.setColor(new Color(255, 255, 0, 128 + (vidaUtil * 20))); // Amarelo a desaparecer
            g.setStroke(new BasicStroke(3));
            g.drawLine(inicio.x, inicio.y, fim.x, fim.y);
        }
    }

    /**
     * Atualiza o estado do projétil, diminuindo a vida útil para simular o desaparecimento.
     * 
     */
    @Override
    public void atualiza(List<Bloon> bloons) {
        vidaUtil--;
    }

    /**
     * Retorna o componente visual do projétil (dummy, pois é apenas visual).
     * 
     */
    @Override
    public ComponenteVisual getComponente() {
        return new ComponenteSimples(ImageLoader.getLoader().getImage("data/misc/pop.png")); // Dummy
    }

    /**
     * Retorna o estrago causado pelo projétil (0, pois o dano é imediato na torre).
     * 
     */
    @Override
    public int getEstrago() {
        return 0;
    }

    /**
     * Define a posição do projétil (não aplicável, pois é apenas visual).
     * 
     */
    @Override
    public void setPosicao(Point p) {
    }

    /**
     * Retorna o alcance do projétil (0, pois é apenas visual).
     * 
     */
    @Override
    public int getAlcance() {
        return 0;
    }

    /**
     * Define o alcance do projétil (não aplicável, pois é apenas visual).
     * 
     */
    @Override
    public void setAlcance(int alcance) {
    }
}
