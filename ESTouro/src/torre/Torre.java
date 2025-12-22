package torre;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import bloon.Bloon;
import mundo.Mundo;
import prof.jogos2D.image.ComponenteMultiAnimado;
import torre.projetil.Projetil;

/**
 * Interface que representa o comportamento que as torres devem ter. As torres
 * são os elementos que atiram projéteis sobre os bloons.
 */
public interface Torre extends Cloneable {

	/**
	 * Define a estratégia de ataque da torre.
	 * 
	 * @param estrategia a nova estratégia
	 */
	public void setEstrategia(torre.estrategia.EstrategiaAtaque estrategia);

	/**
	 * Devolve a estratégia de ataque da torre.
	 * 
	 * @return a estratégia de ataque
	 */
	public torre.estrategia.EstrategiaAtaque getEstrategia();

	/**
	 * Define a posição no écran da torre
	 * 
	 * @param p a nova posição
	 */
	public void setPosicao(Point p);

	/**
	 * Define o mundo onde a torre está
	 * 
	 * @param m o mundo
	 */
	public void setMundo(Mundo m);

	/**
	 * Retorna o mundo onde está a torre
	 * 
	 * @return o mundo onde está a torre
	 */
	public Mundo getMundo();

	/**
	 * devolve qual o componente visual da torre
	 * 
	 * @return o componente visual da torre
	 */
	public ComponenteMultiAnimado getComponente();

	/**
	 * faz uma jogada de ataque aos bloons. O resultado de cada ataque é uma série
	 * de projéteis
	 * 
	 * @param bloons lista de bloons a atacar
	 * @return os projéteis lançados pela torre
	 */
	public Projetil[] atacar(List<Bloon> bloons);

	/**
	 * define qual o raio de accao da torre. O raio de accao
	 * é a distância máxima a que a torre consegue visualizar
	 * os inimigos
	 * 
	 * @param raio o novo raio de accao
	 */
	public void setRaioAcao(int raio);

	/**
	 * devolve o raio de ação da torre. O raio de ação
	 * é a distância máxima a que a torre consegue visualizar
	 * os inimigos
	 * 
	 * @return o raio de ação da torre
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
	 * @param g ambiente onde desenhar a torre
	 */
	public void desenhar(Graphics2D g);

	/**
	 * Desenha o alcance da torre
	 * 
	 * @param g sistema gráfico onde desenhar
	 */
	public void desenhaRaioAcao(Graphics2D g);

	/**
	 * Cria um clone desta torre
	 * 
	 * @return uma torre igual à original
	 */
	public Torre clone();

	/**
	 * Aceita um visitante
	 * 
	 * @param v o visitante
	 */
	public void accept(TorreVisitor v);
}
