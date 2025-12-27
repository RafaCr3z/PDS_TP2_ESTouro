package torre;

import bloon.Bloon;
import java.awt.Point;
import java.awt.image.BufferedImage;
import prof.jogos2D.image.*;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.Dardo;
import torre.projetil.Projetil;

/**
 * Classe que representa uma torre Macaco. Esta torre manda um dardo com dano de
 * 2. Só dispara quando tem bloons dentro do seu raio de ação e atira para o
 * bloon de acordo com o seu modo de ataque.
 */
public class TorreMacaco extends TorreDefault {

	/**
	 * Cria a torre macaco
	 * 
	 */
	public TorreMacaco(BufferedImage img) {
		super(new ComponenteMultiAnimado(new Point(50, 50), img, 2, 4, 3), 30, 8, new Point(15, 15), 100);
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
		ComponenteVisual img = new ComponenteAnimado(new Point(),
				(BufferedImage) ImageLoader.getLoader().getImage("data/torres/dardo.gif"), 2, 2);
		p[0] = new Dardo(img, angle, 10, 2);
		p[0].setPosicao(shoot);
		p[0].setAlcance(getRaioAcao() + 30);
		return p;
	}

	@Override
	public void accept(TorreVisitor v) {
		v.visit(this);
	}

}
