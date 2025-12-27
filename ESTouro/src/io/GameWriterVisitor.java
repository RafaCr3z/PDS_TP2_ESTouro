package io;

import java.awt.Point;
import java.io.PrintWriter;

import torre.*;

public class GameWriterVisitor implements TorreVisitor {
    private PrintWriter out;

    public GameWriterVisitor(PrintWriter out) {
        this.out = out;
    }

    private void writePos(Torre t) {
        Point p = t.getComponente().getPosicaoCentro();
        out.print(p.x + "\t" + p.y + "\t");
    }

    @Override
    public void visit(TorreMacaco t) {
        writePos(t);
        out.println(TorreConstantes.MACACO);
    }

    @Override
    public void visit(TorreOctogonal t) {
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
