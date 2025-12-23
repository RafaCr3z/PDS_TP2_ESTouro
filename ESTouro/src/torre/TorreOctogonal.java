package torre;

import bloon.Bloon;
import java.awt.Point;
import java.awt.image.BufferedImage;
import prof.jogos2D.image.*;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.Dardo;
import torre.projetil.Projetil;

/**
 * Classe que representa a torre octogonal. Esta torre dispara 8 dardos, um em
 * cada direção dos seus lançadores. Só dispara quando tem bloons dentro do seu
 * raio de ação.
 */
public class TorreOctogonal extends TorreDefault {

	private double baseAngle = 0;

	public TorreOctogonal(BufferedImage img) {
		super(new ComponenteMultiAnimado(new Point(), img, 2, 4, 2),
				20, 6, new Point(0, 0), 100);
	}

	@Override
	protected void orientarTorre(Bloon alvo) {
		// Torre octogonal não se orienta para alvos específicos
		// Mantém sua orientação atual
	}

	@Override
	protected Projetil[] criarProjeteis(Bloon alvo) {
		Point centro = getComponente().getPosicaoCentro();
		Point disparo = getPontoDisparo();
		double angle = 0; // ângulo não importa para cálculo do ponto de disparo
		
		double cosA = Math.cos(angle);
		double senA = Math.sin(angle);
		int px = (int) (disparo.x * cosA - disparo.y * senA);
		int py = (int) (disparo.y * cosA + disparo.x * senA);
		Point shoot = new Point(centro.x + px, centro.y + py);

		// Criar os 8 dardos em direções diferentes
		Projetil p[] = new Projetil[8];
		double angulo = baseAngle + Math.PI / 2;
		double incAng = Math.PI / 4;
		
		for (int i = 0; i < 8; i++) {
			ComponenteVisual img = new ComponenteAnimado(new Point(),
					(BufferedImage) ImageLoader.getLoader().getImage("data/torres/dardo.gif"), 2, 2);
			p[i] = new Dardo(img, angulo, 8, 1);
			p[i].setPosicao(shoot);
			p[i].setAlcance(getRaioAcao() + 15);
			angulo -= incAng;
		}
		return p;
	}

	/**
	 * Altera o ângulo da octo
	 * 
	 * @param angle o novo ângulo
	 */
	public void setAngle(double angle) {
		getComponente().setAngulo(angle);
		baseAngle = angle;
	}

	@Override
	public void accept(TorreVisitor v) {
		v.visit(this);
	}
}
