package game.manipulator;

import torre.*;

public class ManipuladorCreationVisitor implements TorreVisitor {

    private ManipuladorTorre manipulador;

    public ManipuladorTorre getManipulador() {
        return manipulador;
    }

    @Override
    public void visit(TorreMacaco t) {
        manipulador = new ManipuladorVazio(t);
    }

    @Override
    public void visit(TorreOctogonal t) {
        manipulador = new ManipuladorOcto(t);
    }

    @Override
    public void visit(TorreCanhao t) {
        manipulador = new ManipuladorVazio(t);
    }

    @Override
    public void visit(TorreMorteiro t) {
        manipulador = new ManipuladorMorteiro(t);
    }

    @Override
    public void visit(TorreBalista t) {
        manipulador = new ManipuladorBalista(t);
    }

    @Override
    public void visit(TorreNinja t) {
        manipulador = new ManipuladorVazio(t);
    }

    @Override
    public void visit(TorreSniper t) {
        manipulador = new ManipuladorSniper(t);
    }

}
