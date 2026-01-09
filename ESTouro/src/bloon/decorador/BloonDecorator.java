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
 * Decorador base para bloons
 * Encaminha chamadas para o objeto decorado
 */
public abstract class BloonDecorator implements Bloon {

    // O bloon que está sendo decorado
    protected Bloon bloon;

    /**
     * Cria um decorador para o bloon fornecido
     */
    public BloonDecorator(Bloon bloon) {
        this.bloon = bloon;
    }

    @Override
    public void desenhar(Graphics2D g) {
        // Delega para o original
        bloon.desenhar(g);
    }

    @Override
    public void mover() {
        // Delega o movimento para o bloon decorado
        bloon.mover();
    }

    @Override
    public ComponenteVisual getComponente() {
        // Retorna o componente visual do bloon decorado
        return bloon.getComponente();
    }

    @Override
    public ComponenteVisual getPopComponente() {
        // Retorna o componente de explosão do bloon decorado
        return bloon.getPopComponente();
    }

    @Override
    public void setCaminho(Caminho rua) {
        // Define o caminho para o bloon decorado
        bloon.setCaminho(rua);
    }

    @Override
    public Caminho getCaminho() {
        // Retorna o caminho do bloon decorado
        return bloon.getCaminho();
    }

    @Override
    public int getPosicaoNoCaminho() {
        // Retorna a posição no caminho do bloon decorado
        return bloon.getPosicaoNoCaminho();
    }

    @Override
    public void setPosicaoNoCaminho(int pos) {
        // Define a posição no caminho para o bloon decorado
        bloon.setPosicaoNoCaminho(pos);
    }

    @Override
    public void setVelocidade(float veloc) {
        // Define a velocidade para o bloon decorado
        bloon.setVelocidade(veloc);
    }

    @Override
    public float getVelocidade() {
        // Retorna a velocidade do bloon decorado
        return bloon.getVelocidade();
    }

    @Override
    public void setMundo(Mundo w) {
        // Define o mundo para o bloon decorado
        bloon.setMundo(w);
    }

    @Override
    public Mundo getMundo() {
        // Retorna o mundo do bloon decorado
        return bloon.getMundo();
    }

    @Override
    public void setPosicao(Point p) {
        // Define a posição para o bloon decorado
        bloon.setPosicao(p);
    }

    @Override
    public Rectangle getBounds() {
        // Retorna os limites do bloon decorado
        return bloon.getBounds();
    }

    @Override
    public int pop(int estrago) {
        // Delega o estouro para o bloon decorado
        return bloon.pop(estrago);
    }

    @Override
    public void explode(int estrago) {
        // Delega a explosão para o bloon decorado
        bloon.explode(estrago);
    }

    @Override
    public int getResistencia() {
        // Retorna a resistência do bloon decorado
        return bloon.getResistencia();
    }

    @Override
    public int getValor() {
        // Retorna o valor do bloon decorado
        return bloon.getValor();
    }

    @Override
    public void setValor(int val) {
        // Define o valor para o bloon decorado
        bloon.setValor(val);
    }

    @Override
    public void addBloonObserver(BloonObserver bo) {
        // Adiciona um observador ao bloon decorado
        bloon.addBloonObserver(bo);
    }

    @Override
    public void removeBloonObserver(BloonObserver bo) {
        // Remove um observador do bloon decorado
        bloon.removeBloonObserver(bo);
    }

    @Override
    public abstract Bloon clone();
}