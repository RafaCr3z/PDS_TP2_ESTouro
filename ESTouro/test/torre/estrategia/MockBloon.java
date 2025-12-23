package torre.estrategia;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import bloon.Bloon;
import bloon.BloonObserver;
import mundo.Caminho;
import mundo.Mundo;
import prof.jogos2D.image.ComponenteVisual;

/**
 * Mock implementation of Bloon interface for testing purposes.
 * Provides minimal implementation needed for strategy testing.
 */
public class MockBloon implements Bloon {
    
    private MockComponenteVisual componente;
    private int valor;
    private int resistencia = 1;
    private float velocidade = 1.0f;
    private int posicaoNoCaminho = 0;
    private Caminho caminho;
    private Mundo mundo;

    public MockBloon(Point posicao, int valor) {
        this.componente = new MockComponenteVisual(posicao);
        this.valor = valor;
    }

    @Override
    public void desenhar(Graphics2D g) {
        // Mock implementation - do nothing
    }

    @Override
    public void mover() {
        // Mock implementation - do nothing
    }

    @Override
    public ComponenteVisual getComponente() {
        return componente;
    }

    @Override
    public ComponenteVisual getPopComponente() {
        return componente; // Return same component for simplicity
    }

    @Override
    public void setCaminho(Caminho rua) {
        this.caminho = rua;
    }

    @Override
    public Caminho getCaminho() {
        return caminho;
    }

    @Override
    public int getPosicaoNoCaminho() {
        return posicaoNoCaminho;
    }

    @Override
    public void setPosicaoNoCaminho(int pos) {
        this.posicaoNoCaminho = pos;
    }

    @Override
    public void setVelocidade(float veloc) {
        this.velocidade = veloc;
    }

    @Override
    public float getVelocidade() {
        return velocidade;
    }

    @Override
    public void setMundo(Mundo w) {
        this.mundo = w;
    }

    @Override
    public Mundo getMundo() {
        return mundo;
    }

    @Override
    public void setPosicao(Point p) {
        componente.setPosicaoCentro(p);
    }

    @Override
    public Rectangle getBounds() {
        return componente.getBounds();
    }

    @Override
    public int pop(int estrago) {
        resistencia -= estrago;
        if (resistencia < 0) {
            int sobra = -resistencia;
            resistencia = 0;
            return sobra;
        }
        return 0;
    }

    @Override
    public void explode(int estrago) {
        resistencia -= estrago;
        if (resistencia < 0) {
            resistencia = 0;
        }
    }

    @Override
    public int getResistencia() {
        return resistencia;
    }

    @Override
    public int getValor() {
        return valor;
    }

    @Override
    public void setValor(int val) {
        this.valor = val;
    }

    @Override
    public void addBloonObserver(BloonObserver bo) {
        // Mock implementation - do nothing
    }

    @Override
    public void removeBloonObserver(BloonObserver bo) {
        // Mock implementation - do nothing
    }

    @Override
    public Bloon clone() {
        MockBloon clone = new MockBloon(componente.getPosicaoCentro(), valor);
        clone.resistencia = this.resistencia;
        clone.velocidade = this.velocidade;
        clone.posicaoNoCaminho = this.posicaoNoCaminho;
        return clone;
    }
}