package torre;

import java.awt.Point;
import java.awt.image.BufferedImage;

import bloon.Bloon;
import prof.jogos2D.image.ComponenteAnimado;
import prof.jogos2D.image.ComponenteMultiAnimado;
import prof.jogos2D.image.ComponenteSimples;
import prof.jogos2D.image.ComponenteVisual;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.BombaImpacto;
import torre.projetil.Dardo;
import torre.projetil.Projetil;

/**
 * Classe que representa a torre ninja. Esta torre dispara alternadamente 3
 * dardos ou 1 granada para os bloons de acordo com o seu modo de ataque.
 */
public class TorreNinja extends TorreDefault {

    private boolean dardos = false;

    /**
     * Cria uma torre ninja
     * 
     * @param img a imagem da torre
     */
    public TorreNinja(BufferedImage img) {
        super(new ComponenteMultiAnimado(new Point(50, 50), img, 2, 4, 3), 30, 8, new Point(20, 0), 100);
    }

    @Override
    protected Projetil[] criarProjetil(Bloon alvo) {
        Point centro = getComponente().getPosicaoCentro();
        double angle = getComponente().getAngulo();

        // primeiro calcular o ponto de disparo
        Point disparo = getPontoDisparo();
        double cosA = Math.cos(angle);
        double senA = Math.sin(angle);
        int px = (int) (disparo.x * cosA - disparo.y * senA);
        int py = (int) (disparo.y * cosA + disparo.x * senA); // repor o tempo de disparo
        Point shoot = new Point(centro.x + px, centro.y + py);

        // depois criar os projéteis
        dardos = !dardos; // inverter a vez
        if (dardos) {
            Projetil p[] = new Projetil[3];
            for (int i = 0; i < 3; i++) {
                ComponenteVisual img = new ComponenteAnimado(new Point(),
                        (BufferedImage) ImageLoader.getLoader().getImage("data/torres/dardo.gif"), 2, 2);
                p[i] = new Dardo(img, angle + (i - 1) * Math.PI / 6, 10, 3);
                p[i].setPosicao(shoot);
                p[i].setAlcance(getRaioAcao() + 30);
            }
            return p;
        } else {
            Projetil p[] = new Projetil[1];
            ComponenteVisual img = new ComponenteSimples(ImageLoader.getLoader().getImage("data/torres/bomba.gif"));
            p[0] = new BombaImpacto(img, angle, 12, 1, getMundo());
            p[0].setPosicao(shoot);
            p[0].setAlcance(getRaioAcao() + 20);
            return p;
        }
    }

    @Override
    public void accept(TorreVisitor v) {
        v.visit(this);
    }
}
