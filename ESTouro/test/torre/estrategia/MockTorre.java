package torre.estrategia;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import bloon.Bloon;
import mundo.Mundo;
import prof.jogos2D.image.ComponenteMultiAnimado;
import torre.Torre;
import torre.TorreVisitor;
import torre.projetil.Projetil;

/**
 * Mock implementation of Torre interface for testing purposes.
 * Provides minimal implementation needed for strategy testing.
 */
public class MockTorre implements Torre {
    
    private Point posicao;
    private MockComponenteMultiAnimado componente;
    private torre.estrategia.EstrategiaAtaque estrategia;
    private int raioAcao = 100;
    private Mundo mundo;

    public MockTorre(Point posicao) {
        this.posicao = posicao;
        this.componente = new MockComponenteMultiAnimado(posicao);
    }

    @Override
    public void setEstrategia(torre.estrategia.EstrategiaAtaque estrategia) {
        this.estrategia = estrategia;
    }

    @Override
    public torre.estrategia.EstrategiaAtaque getEstrategia() {
        return estrategia;
    }

    @Override
    public void setPosicao(Point p) {
        this.posicao = p;
        if (componente != null) {
            componente.setPosicaoCentro(p);
        }
    }

    @Override
    public void setMundo(Mundo m) {
        this.mundo = m;
    }

    @Override
    public Mundo getMundo() {
        return mundo;
    }

    @Override
    public ComponenteMultiAnimado getComponente() {
        return componente;
    }

    @Override
    public Projetil[] atacar(List<Bloon> bloons) {
        // Minimal implementation for testing
        return new Projetil[0];
    }

    @Override
    public void setRaioAcao(int raio) {
        this.raioAcao = raio;
    }

    @Override
    public int getRaioAcao() {
        return raioAcao;
    }

    @Override
    public Point getPontoDisparo() {
        return posicao;
    }

    @Override
    public void desenhar(Graphics2D g) {
        // Mock implementation - do nothing
    }

    @Override
    public void desenhaRaioAcao(Graphics2D g) {
        // Mock implementation - do nothing
    }

    @Override
    public Torre clone() {
        return new MockTorre(new Point(posicao));
    }

    @Override
    public void accept(TorreVisitor v) {
        // Mock implementation - do nothing
    }
}