package io;

import java.awt.Point;
import java.io.PrintWriter;

import torre.*;

/**
 * Visitor que escreve os dados das torres para um arquivo de saída.
 */
public class GameWriterVisitor implements TorreVisitor {
    // Writer para saída dos dados
    private PrintWriter out;

    /**
     * Construtor para o visitor de escrita.
     * 
     */
    public GameWriterVisitor(PrintWriter out) {
        this.out = out;
    }

    /**
     * Escreve a posição da torre no formato x\ty\t
     * 
     */
    private void writePos(Torre t) {
        Point p = t.getComponente().getPosicaoCentro();
        out.print(p.x + "\t" + p.y + "\t");
    }

    @Override
    public void visit(TorreMacaco t) {
        // Escreve posição e tipo da torre macaco
        writePos(t);
        out.println("macaco");
    }

    @Override
    public void visit(TorreOctogonal t) {
        // Escreve posição, tipo e ângulo da torre octogonal
        writePos(t);
        out.print("octo\t");
        out.println(t.getComponente().getAngulo());
    }

    @Override
    public void visit(TorreCanhao t) {
        // Escreve posição e tipo da torre canhão
        writePos(t);
        out.println("canhao");
    }

    @Override
    public void visit(TorreMorteiro t) {
        // Escreve posição, tipo e área de alvo da torre morteiro
        writePos(t);
        out.print("morteiro\t");
        Point p = t.getAreaAlvo();
        out.println(p.x + "\t" + p.y);
    }

    @Override
    public void visit(TorreBalista t) {
        // Escreve posição, tipo e ângulo da torre balista
        writePos(t);
        out.print("balista\t");
        out.println(t.getComponente().getAngulo());
    }

    @Override
    public void visit(TorreNinja t) {
        // Escreve posição e tipo da torre ninja
        writePos(t);
        out.println("ninja");
    }

    @Override
    public void visit(TorreSniper t) {
        // Escreve posição, tipo e ângulo da torre sniper
        writePos(t);
        out.print("sniper\t");
        out.println(t.getComponente().getAngulo());
    }
}
