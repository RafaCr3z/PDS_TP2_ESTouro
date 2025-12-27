package game.manipulator;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

import prof.jogos2D.util.DetectorColisoes;
import torre.*;

/**
 * Manipulador para a torre balista. Permite rodar a torre para obter uma
 * direção mais conveniente.
 */
public class ManipuladorBalista extends ManipuladorVazio {

	// composite para usar transparências nas miras
	private static final AlphaComposite transp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

	/**
	 * Construtor para o manipulador da balista.
	 * 
	 */
	public ManipuladorBalista(Torre t) {
		super(t);
	}

	@Override
	public void mouseDragged(Point p) {
		// Calcula o ângulo entre o centro da torre e o ponto do mouse
		double angulo = DetectorColisoes.getAngulo(getTorre().getComponente().getPosicaoCentro(), p);
		// Define o ângulo de disparo da balista
		((TorreBalista) getTorre()).setAnguloDisparo((float) angulo);
	}

	@Override
	public void desenhar(Graphics2D g) {
		// Salva o composite original
		Composite oldComp = g.getComposite();
		Point centro = getTorre().getComponente().getPosicaoCentro();
		Point attackPoint = ((TorreBalista) getTorre()).getMira();
		Line2D.Float l = new Line2D.Float(centro, attackPoint);

		// Define transparência para a mira
		g.setComposite(transp);
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(20));
		// Desenha a linha da mira
		g.draw(l);
		// Restaura o composite original
		g.setComposite(oldComp);
	}
}
