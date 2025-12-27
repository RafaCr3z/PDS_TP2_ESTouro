package bloon;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import mundo.Caminho;
import mundo.Mundo;

import prof.jogos2D.image.ComponenteVisual;

/**
 * Interface que define quais os métodos que todos os bloons devem implementar
 */
public interface Bloon {

	/**
	 * desenha o bloon
	 * 
	 */
	public void desenhar(Graphics2D g);

	/**
	 * move o bloon
	 */
	public void mover();

	/**
	 * devolve o componente visual que representa este bloon
	 * 
	 */
	public ComponenteVisual getComponente();

	/**
	 * Retorna o componente visual para o efeito especial do balão rebentar
	 * 
	 */
	public ComponenteVisual getPopComponente();

	/**
	 * Define qual o caminho que o bloon percorre
	 * 
	 */
	public void setCaminho(Caminho rua);

	/**
	 * Retorna o caminho em que o bloon se move
	 * 
	 */
	public Caminho getCaminho();

	/**
	 * indica em que posição do caminho está o bloon.
	 * 
	 */
	public int getPosicaoNoCaminho();

	/**
	 * coloca o bloon numa nova posição do caminho.
	 * 
	 */
	public void setPosicaoNoCaminho(int pos);

	/**
	 * Altera a velocidade do bloon
	 * 
	 */
	public void setVelocidade(float veloc);

	/**
	 * Retorna a velocidade de deslocamento
	 * 
	 */
	public float getVelocidade();

	/**
	 * define em que mundo se move o balão
	 * 
	 */
	public void setMundo(Mundo w);

	/**
	 * Retorna o mundo onde se move o bloon
	 * 
	 */
	public Mundo getMundo();

	/**
	 * define a posição do écran onde se coloca o bloon
	 * 
	 */
	public void setPosicao(Point p);

	/**
	 * devolve o retângulo ocupado pelo bloon no écran
	 * 
	 */
	public Rectangle getBounds();

	/**
	 * Se o bloon não aguentar o estrago estoura e devolve qual o
	 * estrago que não suportou.
	 * 
	 */
	public int pop(int estrago);

	/**
	 * Se o bloon não aguentar o estrago estoura.
	 * 
	 */
	public void explode(int estrago);

	/**
	 * devolve a resistência atual do bloon.
	 * 
	 */
	public int getResistencia();

	/**
	 * devolve o valor, ou seja, quanto vale o bloon em termos de pontos, dinheiro,
	 * etc
	 * 
	 */
	public int getValor();

	/**
	 * define o valor do bloon, ou seja, quanto ele vale em termos de pontos,
	 * dinheiro, etc
	 * 
	 */
	public void setValor(int val);

	/**
	 * adiciona um observador ao bloon
	 * 
	 */
	public void addBloonObserver(BloonObserver bo);

	/**
	 * remove um observador do bloon
	 * 
	 */
	public void removeBloonObserver(BloonObserver bo);

	/**
	 * Cria uma cópia deste bloon
	 * 
	 */
	public Bloon clone();
}
