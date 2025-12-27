package torre.projetil;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import bloon.Bloon;
import prof.jogos2D.image.*;

/**
 * Interface que define todo o comportamento que os projéteis devem ter.
 *
 */
public interface Projetil {

	/**
	 * desenha o projétil
	 * 
	 */
	public void draw(Graphics2D g);

	/**
	 * efectua um ciclo de processamento
	 * 
	 */
	public void atualiza(List<Bloon> bloons);

	/**
	 * devolve o componente visual associado ao projétil
	 * 
	 */
	public ComponenteVisual getComponente();

	/**
	 * Indica qual o estrago que o projétil causa nos bloons
	 * 
	 */
	public int getEstrago();

	/**
	 * define em que posição do écran se deve colocar o projétil
	 * 
	 */
	public void setPosicao(Point p);

	/**
	 * devolve o alcance que o projétil possui
	 * 
	 */
	public int getAlcance();

	/**
	 * define o alcance do projétil
	 * 
	 */
	public void setAlcance(int alcance);

}
