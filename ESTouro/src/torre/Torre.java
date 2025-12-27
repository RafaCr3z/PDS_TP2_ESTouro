package torre;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import bloon.Bloon;
import mundo.Mundo;
import prof.jogos2D.image.ComponenteMultiAnimado;
import torre.projetil.Projetil;
import torre.estrategia.EstrategiaAtaque;

/**
 * Interface que representa o comportamento que as torres devem ter. As torres
 * são os elementos que atiram projéteis sobre os bloons.
 */
public interface Torre extends Cloneable {

	/**
	 * Define a estratégia de ataque da torre.
	 * 
	 */
	public void setEstrategia(EstrategiaAtaque estrategia);

	/**
	 * Devolve a estratégia de ataque da torre.
	 * 
	 */
	public EstrategiaAtaque getEstrategia();

	/**
	 * Define a posição no écran da torre
	 * 
	 */
	public void setPosicao(Point p);

	/**
	 * Define o mundo onde a torre está
	 * 
	 */
	public void setMundo(Mundo m);

	/**
	 * Retorna o mundo onde está a torre
	 * 
	 */
	public Mundo getMundo();

	/**
	 * devolve qual o componente visual da torre
	 * 
	 */
	public ComponenteMultiAnimado getComponente();

	/**
	 * faz uma jogada de ataque aos bloons. O resultado de cada ataque é uma série
	 * de projéteis
	 * 
	 */
	public Projetil[] atacar(List<Bloon> bloons);

	/**
	 * define qual o raio de accao da torre. O raio de accao
	 * é a distância máxima a que a torre consegue visualizar
	 * os inimigos
	 * 
	 */
	public void setRaioAcao(int raio);

	/**
	 * devolve o raio de ação da torre. O raio de ação
	 * é a distância máxima a que a torre consegue visualizar
	 * os inimigos
	 * 
	 */
	public int getRaioAcao();

	/**
	 * Retorna o ponto de disparo da torre. O Ponto de disparo é a posição relativa
	 * ao centro da torre de onde sai o projétil
	 */
	public Point getPontoDisparo();

	/**
	 * desenha a torre
	 * 
	 */
	public void desenhar(Graphics2D g);

	/**
	 * Desenha o alcance da torre
	 * 
	 */
	public void desenhaRaioAcao(Graphics2D g);

	/**
	 * Cria um clone desta torre
	 * 
	 */
	public Torre clone();

	/**
	 * Aceita um visitante
	 * 
	 */
	public void accept(TorreVisitor v);
}
