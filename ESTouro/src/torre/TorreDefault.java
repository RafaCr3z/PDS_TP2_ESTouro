package torre;

import bloon.Bloon;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;
import java.util.Objects;
import mundo.Mundo;
import prof.jogos2D.image.ComponenteMultiAnimado;
import prof.jogos2D.util.DetectorColisoes;
import torre.projetil.Projetil;
import torre.estrategia.EstrategiaAtaque;
import torre.estrategia.EstrategiaPrimeiro;

/**
 * Classe que implementa os comportamentos e variáveis comuns a todos as torres.
 * Tambám possui alguns métodos auxiliares para as várias torres
 */
public abstract class TorreDefault implements Torre {

	private Mundo mundo; // mundo onde está a torre
	private ComponenteMultiAnimado imagem; // desenho da torre

	private torre.estrategia.EstrategiaAtaque estrategia = new torre.estrategia.EstrategiaPrimeiro();
	private int raioAtaque;
	private Point pontoDisparo;

	protected static final int PAUSA_ANIM = 0; // identifica a animação quando não está a disparar
	protected static final int ATAQUE_ANIM = 1; // identifica a animação de disparar

	private final int ritmoDisparo; // velocidade de disparo
	private int proxDisparo; // quando volta a disparar
	private final int frameDisparoDelay; // delay desde que a animação de disparo começa até que "realmente" dispara

	/**
	 * Construtor da torre. Cria uma torre dando-lhe uma imagem, um ponto de disparo
	 * e um raio de ataque. O ponto de disparo é a coordenada de onde sai o projétil
	 * e é dado relativamente ao ponto central da torre. O raio de ataque é o raio,
	 * a partir do centro da torre em que esta deteta bloons
	 * 
	 * @param cv           A imagem a usar para a torre
	 * @param ritmoDisparo quantos ciclos demora entre disparos
	 * @param delayDisparo delay da animação em que realmente ocorre o disparo
	 * @param pontoDisparo o ponto de disparo da torre, isto é, de onde sai o
	 *                     projétil. Coordenada relativa ao centro da torre
	 * @param raioAtaque   distãncia dentro da qual deteta bloons
	 */
	public TorreDefault(ComponenteMultiAnimado cv, int ritmoDisparo, int delayDisparo, Point pontoDisparo,
			int raioAtaque) {
		imagem = Objects.requireNonNull(cv);
		this.ritmoDisparo = ritmoDisparo;
		this.proxDisparo = 0;
		this.frameDisparoDelay = delayDisparo;
		this.pontoDisparo = Objects.requireNonNull(pontoDisparo);
		this.raioAtaque = raioAtaque;
	}

	protected void atualizarCicloDisparo() {
		proxDisparo++;
	}

	protected boolean podeDisparar() {
		return proxDisparo >= ritmoDisparo;
	}

	protected int resetTempoDisparar() {
		return proxDisparo = 0;
	}

	protected void sincronizarFrameDisparo(ComponenteMultiAnimado anim) {
		if (proxDisparo + frameDisparoDelay >= ritmoDisparo) {
			if (anim.getAnim() != ATAQUE_ANIM) {
				anim.setAnim(ATAQUE_ANIM);
				anim.reset();
			}
		}
	}

	protected void setComponente(ComponenteMultiAnimado v) {
		imagem = v;
	}

	@Override
	public void setMundo(Mundo w) {
		mundo = w;
	}

	@Override
	public Mundo getMundo() {
		return mundo;
	}

	@Override
	public ComponenteMultiAnimado getComponente() {
		return imagem;
	}

	@Override
	public void setPosicao(Point p) {
		imagem.setPosicaoCentro(p);
	}

	@Override
	public Point getPontoDisparo() {
		return pontoDisparo;
	}

	@Override
	public void setRaioAcao(int raio) {
		raioAtaque = raio;
	}

	@Override
	public int getRaioAcao() {
		return raioAtaque;
	}

	@Override
	public void desenhar(Graphics2D g) {
		imagem.desenhar(g);
	}

	@Override
	public void desenhaRaioAcao(Graphics2D g) {
		Point p = getComponente().getPosicaoCentro();
		Composite oldComp = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.setColor(Color.WHITE);
		g.fillOval(p.x - raioAtaque, p.y - raioAtaque, 2 * raioAtaque, 2 * raioAtaque);
		g.setColor(Color.BLUE);
		g.drawOval(p.x - raioAtaque, p.y - raioAtaque, 2 * raioAtaque, 2 * raioAtaque);
		g.setComposite(oldComp);
	}

	@Override
	public void setEstrategia(EstrategiaAtaque estrategia) {
		this.estrategia = estrategia;
	}

	@Override
	public EstrategiaAtaque getEstrategia() {
		return estrategia;
	}

	
	@Override
	public Projetil[] atacar(List<Bloon> bloons) {
		atualizarCicloDisparo();
		ComponenteMultiAnimado anim = getComponente();

		// Se acabou animação de ataque, volta a pausa
		if (anim.getAnim() == ATAQUE_ANIM && anim.numCiclosFeitos() >= 1) {
			anim.setAnim(PAUSA_ANIM);
		}

		List<Bloon> alvosPossiveis = filtrarAlvosNoAlcance(bloons);
		Bloon alvo = escolherAlvo(alvosPossiveis);

		if (alvo != null || podeDispararSemAlvo()) {
			orientarTorre(alvo);
		}

		sincronizarFrameDisparo(anim);

		if (!podeDisparar() || (!podeDispararSemAlvo() && alvo == null))
			return new Projetil[0];

		resetTempoDisparar();
		return criarProjeteis(alvo);
	}

	protected Bloon escolherAlvo(List<Bloon> alvosPossiveis) {
		return estrategia.escolherAlvo(this, alvosPossiveis);
	}

	/**
	 * Determina se a torre pode disparar mesmo sem alvos específicos.
	 * Usado por torres como morteiro que disparam para áreas fixas.
	 * 
	 */
	protected boolean podeDispararSemAlvo() {
		return false;
	}

	/**
	 * Roda a torre na direção do alvo.
	 * Pode ser sobrescrito por torres que não rodam ou rodam de forma diferente.
	 * 
	 */
	protected void orientarTorre(Bloon alvo) {
		double angle = DetectorColisoes.getAngulo(alvo.getComponente().getPosicaoCentro(),
				getComponente().getPosicaoCentro());
		getComponente().setAngulo(angle);
	}

	/**
	 * Filtra os bloons que estão no alcance da torre.
	 * Implementação padrão usa raio circular, mas pode ser sobrescrita
	 * por torres com diferentes tipos de alcance (linha, área específica, etc).
	 * 
	 */
	protected List<Bloon> filtrarAlvosNoAlcance(List<Bloon> bloons) {
		return getBloonsInRadius(bloons, getComponente().getPosicaoCentro(), getRaioAcao());
	}

	/**
	 * Cria os projéteis a disparar contra o alvo.
	 * Deve ser implementado pelas subclasses para definir o tipo e quantidade
	 * de projéteis específicos de cada torre.
	 * 
	 */
	protected abstract Projetil[] criarProjeteis(Bloon alvo);

	/**
	 * Retorna uma lista com os bloons que estejam dentro de um raio de
	 * ação circular
	 * 
	 * @param bloons lista de bloons a verificar
	 * @param center centro do raio de ação
	 * @param radius raio de ação
	 * @return lista de bloons que estão dentro desse raio de ação
	 */
	protected List<Bloon> getBloonsInRadius(List<Bloon> bloons, Point center, int radius) {
		return bloons.stream().filter(b -> DetectorColisoes.intersectam(b.getBounds(), center, radius)).toList();
	}

	/**
	 * Retorna uma lista com os bloons que intersetam um segmento de reta
	 * 
	 * @param bloons lista de bloons a verificar
	 * @param p1     ponto de início do segemento de reta
	 * @param p2     ponto de fim do segment de reta
	 * @return lista de bloons que tocam nesse segmento de reta
	 */
	protected List<Bloon> getBloonsInLine(List<Bloon> bloons, Point p1, Point p2) {
		return bloons.stream().filter(b -> b.getBounds().intersectsLine(p1.x, p1.y, p2.x, p2.y)).toList();
	}

	/**
	 * Clona a torre, criando uma cópia independente com imagem clonada e mundo
	 * resetado.
	 * 
	 */
	@Override
	public Torre clone() {
		try {
			TorreDefault copia = (TorreDefault) super.clone();
			copia.imagem = imagem.clone();
			copia.mundo = null;
			copia.pontoDisparo = new Point(pontoDisparo);
			return copia;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
