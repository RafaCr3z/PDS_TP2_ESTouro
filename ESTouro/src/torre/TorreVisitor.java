package torre;

/**
 * Interface que define o padrão Visitor para torres.
 */
public interface TorreVisitor {

    /**
     * Visita uma Torre Macaco, executando a operação específica para este tipo de torre.
     * 
     */
    public void visit(TorreMacaco t);

    /**
     * Visita uma Torre Octogonal, executando a operação específica para este tipo de torre.
     * 
     */
    public void visit(TorreOctogonal t);

    /**
     * Visita uma Torre Canhão, executando a operação específica para este tipo de torre.
     * 
     */
    public void visit(TorreCanhao t);

    /**
     * Visita uma Torre Morteiro, executando a operação específica para este tipo de torre.
     * 
     */
    public void visit(TorreMorteiro t);

    /**
     * Visita uma Torre Balista, executando a operação específica para este tipo de torre.
     * 
     */
    public void visit(TorreBalista t);

    /**
     * Visita uma Torre Ninja, executando a operação específica para este tipo de torre.
     * 
     */
    public void visit(TorreNinja t);

    /**
     * Visita uma Torre Sniper, executando a operação específica para este tipo de torre.
     * 
     */
    public void visit(TorreSniper t);
}
