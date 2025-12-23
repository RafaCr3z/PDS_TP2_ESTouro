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
 * Manipulador para a torre Sniper. Permite visualizar a linha de visão
 * e rodar a torre para obter uma direção mais conveniente.
 */
public class ManipuladorSniper extends ManipuladorVazio {

	// composite para usar transparências nas miras
	private static final AlphaComposite transp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

	public ManipuladorSniper(Torre t) {
		super(t);
	}

	@Override
	public void mouseDragged(Point p) {
		// Ao arrastar o rato, a torre roda para apontar naquela direção
		double angulo = DetectorColisoes.getAngulo(getTorre().getComponente().getPosicaoCentro(), p);
		getTorre().getComponente().setAngulo((float) angulo);
	}

	@Override
	public void desenhar(Graphics2D g) {
		Composite oldComp = g.getComposite();
		Point centro = getTorre().getComponente().getPosicaoCentro();
		double angle = getTorre().getComponente().getAngulo();
		
		// Desenhar linha de visão até ao alcance máximo
		int alcanceMax = 2000;
		Point fim = new Point(
				(int) (centro.x + alcanceMax * Math.cos(angle)),
				(int) (centro.y + alcanceMax * Math.sin(angle)));
		
		Line2D.Float l = new Line2D.Float(centro, fim);

		g.setComposite(transp);
		g.setColor(Color.YELLOW);
		g.setStroke(new BasicStroke(2));
		g.draw(l);
		g.setComposite(oldComp);
	}
}
