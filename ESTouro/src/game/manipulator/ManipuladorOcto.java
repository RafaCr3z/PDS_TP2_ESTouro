package game.manipulator;

import java.awt.Point;

import prof.jogos2D.util.DetectorColisoes;
import torre.*;

/**
 * Manipulador para a torre octogonal. Permite rodar a torre para obter uma
 * direção mais conveniente.
 */
public class ManipuladorOcto extends ManipuladorVazio {

	/**
	 * Construtor para o manipulador da torre octogonal.
	 * 
	 */
	public ManipuladorOcto(Torre t) {
		super(t);
	}

	@Override
	public void mouseDragged(Point p) {
		// Calcula o ângulo entre o centro da torre e o ponto do mouse
		double angulo = DetectorColisoes.getAngulo(getTorre().getComponente().getPosicaoCentro(), p);
		// Define o ângulo da torre octogonal
		((TorreOctogonal) getTorre()).setAngle(angulo);
	}
}
