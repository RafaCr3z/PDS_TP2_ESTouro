package torre;

import bloon.Bloon;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;
import prof.jogos2D.image.*;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.Dardo;
import torre.projetil.Projetil;

/**
 * Torre Sniper: Alcance infinito, dano imediato (5), escolha de alvo por
 * estratégia.
 */
public class TorreSniper extends TorreDefault {

    private static final int ALCANCE_MAX = 2000; // Alcance prático "infinito"
    private static final int DANO = 5;

    public TorreSniper(BufferedImage img) {
        // Ajustar parâmetros conforme desejado (ritmo, delay, pivot)
        super(new ComponenteMultiAnimado(new Point(50, 50), img, 2, 4, 2),
                40, 2, new Point(20, 0), ALCANCE_MAX);
    }

    @Override
    public Projetil[] atacar(List<Bloon> bloons) {
        atualizarCicloDisparo();
        ComponenteMultiAnimado anim = getComponente();

        if (anim.getAnim() == ATAQUE_ANIM && anim.numCiclosFeitos() >= 1) {
            anim.setAnim(PAUSA_ANIM);
        }

        // Obter bloons na linha de visão
        Point centro = getComponente().getPosicaoCentro();
        double angle = getComponente().getAngulo();
        Point fim = new Point(
                (int) (centro.x + ALCANCE_MAX * Math.cos(angle)),
                (int) (centro.y + ALCANCE_MAX * Math.sin(angle)));

        List<Bloon> alvosPossiveis = getBloonsInLine(bloons, centro, fim);
        Bloon alvo = getEstrategia().escolherAlvo(this, alvosPossiveis);

        sincronizarFrameDisparo(anim);

        if (!podeDisparar() || alvo == null)
            return new Projetil[0];

        resetTempoDisparar();

        // Dano imediato
        alvo.pop(DANO);

        // Criar projétil a partir do alvo
        return criarProjetilPos(alvo.getComponente().getPosicaoCentro(), angle);
    }

    @Override
    protected Projetil[] criarProjeteis(Bloon alvo) {
        // Não usado diretamente pelo atacar sobrescrito, mas pode ser útil
        return criarProjetilPos(alvo.getComponente().getPosicaoCentro(), getComponente().getAngulo());
    }

    private Projetil[] criarProjetilPos(Point pos, double angle) {
        Projetil p[] = new Projetil[1];
        ComponenteVisual img = new ComponenteAnimado(new Point(),
                (BufferedImage) ImageLoader.getLoader().getImage("data/torres/dardo.gif"), 2, 2);
        // Dardo normal após impacto
        p[0] = new Dardo(img, angle, 10, 2); // Velocidade 10, Dano 2 (dardo normal)
        p[0].setPosicao(pos);
        p[0].setAlcance(ALCANCE_MAX);
        return p;
    }

    @Override
    public void desenhaRaioAcao(Graphics2D g) {
        // Desenhar a linha de tido
        Point centro = getComponente().getPosicaoCentro();
        double angle = getComponente().getAngulo();
        Point fim = new Point(
                (int) (centro.x + ALCANCE_MAX * Math.cos(angle)),
                (int) (centro.y + ALCANCE_MAX * Math.sin(angle)));

        Composite oldComp = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.setColor(Color.RED);
        Line2D.Float l = new Line2D.Float(centro, fim);
        g.setStroke(new BasicStroke(2));
        g.draw(l);
        g.setComposite(oldComp);
    }

    @Override
    public void accept(TorreVisitor v) {
        v.visit(this);
    }
}
