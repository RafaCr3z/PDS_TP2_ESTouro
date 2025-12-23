package torre;

import bloon.Bloon;
import java.awt.Point;
import java.awt.image.BufferedImage;
import prof.jogos2D.image.*;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.BombaImpacto;
import torre.projetil.Projetil;

/**
 * Classe que representa a torre canhão. Esta torre dispara uma bomba que
 * explode ao contato com os bloons. Só dispara quando tem bloons dentro do seu
 * raio de ação e atira para o bloon de acordo com o seu modo de ataque
 */
public class TorreCanhao extends TorreDefault {

	public TorreCanhao(BufferedImage img) {
		super(new ComponenteMultiAnimado(new Point(50, 50), img, 2, 4, 2),
				30, 0, new Point(25, 0), 120);
	}

	@Override
	protected Projetil[] criarProjeteis(Bloon alvo) {
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
		Projetil p[] = new Projetil[1];
		ComponenteVisual img = new ComponenteSimples(ImageLoader.getLoader().getImage("data/torres/bomba.gif"));
		p[0] = new BombaImpacto(img, angle, 12, 2, getMundo());
		p[0].setPosicao(shoot);
		p[0].setAlcance(getRaioAcao() + 20);
		return p;
	}

	@Override
	public void accept(TorreVisitor v) {
		v.visit(this);
	}
}
