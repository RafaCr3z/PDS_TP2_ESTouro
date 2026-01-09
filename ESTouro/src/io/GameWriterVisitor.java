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

    public GameWriterVisitor(PrintWriter out) {
        this.out = out;
    }

    /**
     * Escreve a posição da torre 
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
        out.println(TorreConstantes.MACACO);
    }

    @Override
    public void visit(TorreOctogonal t) {
        // Escreve posição, tipo e ângulo da torre octogonal
        writePos(t);
        out.print(TorreConstantes.OCTO + "\t");
        out.println(t.getComponente().getAngulo());
    }

    @Override
    public void visit(TorreCanhao t) {
        writePos(t);
        out.println(TorreConstantes.CANHAO);
    }

    @Override
    public void visit(TorreMorteiro t) {
        writePos(t);
        out.print(TorreConstantes.MORTEIRO + "\t");
        Point p = t.getAreaAlvo();
        out.println(p.x + "\t" + p.y);
    }

    @Override
    public void visit(TorreBalista t) {
        writePos(t);
        out.print(TorreConstantes.BALISTA + "\t");
        out.println(t.getComponente().getAngulo());
    }

    @Override
    public void visit(TorreNinja t) {
        writePos(t);
        out.println(TorreConstantes.NINJA);
    }

    @Override
    public void visit(TorreSniper t) {
        writePos(t);
        out.print(TorreConstantes.SNIPER + "\t");
        out.println(t.getComponente().getAngulo());
    }
}
