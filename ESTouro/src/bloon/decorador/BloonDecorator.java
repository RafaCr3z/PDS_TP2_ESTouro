package bloon.decorador;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import bloon.Bloon;
import bloon.BloonObserver;
import mundo.Caminho;
import mundo.Mundo;
import prof.jogos2D.image.ComponenteVisual;

/**
 * Decorador abstrato para Bloons.
 * Encaminha todas as chamadas para o bloon decorado.
 */
public abstract class BloonDecorator implements Bloon {

    protected Bloon bloon;

    public BloonDecorator(Bloon bloon) {
        this.bloon = bloon;
    }

    @Override
    public void desenhar(Graphics2D g) {
        bloon.desenhar(g);
    }

    @Override
    public void mover() {
        bloon.mover();
    }

    @Override
    public ComponenteVisual getComponente() {
        return bloon.getComponente();
    }

    @Override
    public ComponenteVisual getPopComponente() {
        return bloon.getPopComponente();
    }

    @Override
    public void setCaminho(Caminho rua) {
        bloon.setCaminho(rua);
    }

    @Override
    public Caminho getCaminho() {
        return bloon.getCaminho();
    }

    @Override
    public int getPosicaoNoCaminho() {
        return bloon.getPosicaoNoCaminho();
    }

    @Override
    public void setPosicaoNoCaminho(int pos) {
        bloon.setPosicaoNoCaminho(pos);
    }

    @Override
    public void setVelocidade(float veloc) {
        bloon.setVelocidade(veloc);
    }

    @Override
    public float getVelocidade() {
        return bloon.getVelocidade();
    }

    @Override
    public void setMundo(Mundo w) {
        bloon.setMundo(w);
    }

    @Override
    public Mundo getMundo() {
        return bloon.getMundo();
    }

    @Override
    public void setPosicao(Point p) {
        bloon.setPosicao(p);
    }

    @Override
    public Rectangle getBounds() {
        return bloon.getBounds();
    }

    @Override
    public int pop(int estrago) {
        return bloon.pop(estrago);
    }

    @Override
    public void explode(int estrago) {
        bloon.explode(estrago);
    }

    @Override
    public int getResistencia() {
        return bloon.getResistencia();
    }

    @Override
    public int getValor() {
        return bloon.getValor();
    }

    @Override
    public void setValor(int val) {
        bloon.setValor(val);
    }

    @Override
    public void addBloonObserver(BloonObserver bo) {
        bloon.addBloonObserver(bo);
    }

    @Override
    public void removeBloonObserver(BloonObserver bo) {
        bloon.removeBloonObserver(bo);
    }

    @Override
    public abstract Bloon clone();
}
