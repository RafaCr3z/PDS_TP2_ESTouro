package torre;

public interface TorreVisitor {
    public void visit(TorreMacaco t);

    public void visit(TorreOctogonal t);

    public void visit(TorreCanhao t);

    public void visit(TorreMorteiro t);

    public void visit(TorreBalista t);

    public void visit(TorreNinja t);

    public void visit(TorreSniper t);
}
