package torre.estrategia;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import prof.jogos2D.image.ComponenteVisual;

/**
 * Mock implementation of ComponenteVisual for testing purposes.
 */
public class MockComponenteVisual extends ComponenteVisual {
    
    private Point posicaoCentro;
    
    public MockComponenteVisual(Point posicao) {
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
        return new Rectangle(posicaoCentro.x - 5, posicaoCentro.y - 5, 10, 10);
    }

    @Override
    public void desenhar(Graphics2D g) {
        // Mock implementation - do nothing
    }

    @Override
    public ComponenteVisual clone() {
        return new MockComponenteVisual(posicaoCentro);
    }
}