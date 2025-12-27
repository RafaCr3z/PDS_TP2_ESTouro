package game.manipulator;

import torre.*;

/**
 * Visitor que cria manipuladores apropriados para diferentes tipos de torres.
 */
public class ManipuladorCreationVisitor implements TorreVisitor {

    // O manipulador criado pelo visitor
    private ManipuladorTorre manipulador;

    /**
     * Retorna o manipulador criado.
     * 
     */
    public ManipuladorTorre getManipulador() {
        return manipulador;
    }

    @Override
    public void visit(TorreMacaco t) {
        // Cria manipulador vazio para torre macaco
        manipulador = new ManipuladorVazio(t);
    }

    @Override
    public void visit(TorreOctogonal t) {
        // Cria manipulador octogonal para torre octogonal
        manipulador = new ManipuladorOcto(t);
    }

    @Override
    public void visit(TorreCanhao t) {
        // Cria manipulador vazio para torre canhão
        manipulador = new ManipuladorVazio(t);
    }

    @Override
    public void visit(TorreMorteiro t) {
        // Cria manipulador para torre morteiro
        manipulador = new ManipuladorMorteiro(t);
    }

    @Override
    public void visit(TorreBalista t) {
        // Cria manipulador para torre balista
        manipulador = new ManipuladorBalista(t);
    }

    @Override
    public void visit(TorreNinja t) {
        // Cria manipulador vazio para torre ninja
        manipulador = new ManipuladorVazio(t);
    }

    @Override
    public void visit(TorreSniper t) {
        // Cria manipulador para torre sniper
        manipulador = new ManipuladorSniper(t);
    }

}
