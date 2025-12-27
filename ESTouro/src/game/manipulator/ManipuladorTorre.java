package game.manipulator;

import java.awt.Graphics2D;
import java.awt.Point;

import torre.Torre;

/**
 * Interface que representa um manipulador de torres. Um manipulador de torres
 * providencia um modo de o utilizador poder configurar algumas torres. Por
 * exemplo, a torre octogonal pode ser rodada para disparar numa dada direção. O
 * jogo recorre ao manipulador para não ter de saber como se manipula cada torre
 */
public interface ManipuladorTorre {

	/**
	 * desenha alguma coisa que seja necessária na configuração
	 * 
	 */
	public void desenhar(Graphics2D g);

	/**
	 * indicação de que o rato foi libertado enquanto se manipula uma torre
	 * 
	 */
	public void mouseReleased(Point p);

	/**
	 * indicação de que o rato foi arrastado enquanto se manipula uma torre
	 * 
	 */
	public void mouseDragged(Point p);

	/**
	 * indica qual a torre manipulada
	 * 
s	 */
	public Torre getTorre();
}
