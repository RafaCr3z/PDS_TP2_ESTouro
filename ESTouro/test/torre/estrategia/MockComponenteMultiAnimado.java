package torre.estrategia;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import prof.jogos2D.image.ComponenteMultiAnimado;

/**
 * Mock implementation of ComponenteMultiAnimado for testing purposes.
 */
public class MockComponenteMultiAnimado extends ComponenteMultiAnimado {
    
    private Point posicaoCentro;
    
    public MockComponenteMultiAnimado(Point posicao) {
        this.posicaoCentro = new Point(posicao);
    }

    @Override
    public Point getPosicaoCentro() {
        return new Point(posicaoCentro);
    }

    @Override
    public void setPosicaoCentro(Point p) {
        this.posicaoCentro = new Point(p);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(posicaoCentro.x - 10, posicaoCentro.y - 10, 20, 20);
    }

    @Override
    public void desenhar(Graphics2D g) {
        // Mock implementation - do nothing
    }

    @Override
    public ComponenteMultiAnimado clone() {
        return new MockComponenteMultiAnimado(posicaoCentro);
    }
}