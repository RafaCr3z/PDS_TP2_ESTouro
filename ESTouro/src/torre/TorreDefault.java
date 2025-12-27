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

/**
 * Classe que implementa os comportamentos e variáveis comuns a todos as torres.
 * Tambám possui alguns métodos auxiliares para as várias torres
 */
public abstract class TorreDefault implements Torre {

	/** Mundo onde está a torre. */
	private Mundo mundo; // mundo onde está a torre
	/** Desenho da torre usando componente multi-animado. */
	private ComponenteMultiAnimado imagem; // desenho da torre

	/** Estratégia de ataque da torre (modo de ataque). */
	private torre.estrategia.EstrategiaAtaque estrategia = new torre.estrategia.EstrategiaPrimeiro(); // modo de ataque
																										// da torre
	/** Raio de ataque, isto é, área circular onde consegue detetar bloons. */
	private int raioAtaque; // raio de ataque, isto é, área circular onde consegue detetar bloons
	/** Ponto de onde sai o disparo (relativo ao centro da torre). */
	private Point pontoDisparo; // ponto de onde sai o disparo

	/** Identifica a animação quando não está a disparar. */
	protected static final int PAUSA_ANIM = 0; // identifica a animação quando não está a disparar
	/** Identifica a animação de disparar. */
	protected static final int ATAQUE_ANIM = 1; // identifica a animação de disparar
	/** Velocidade de disparo (quantos ciclos demora entre disparos). */
	private final int ritmoDisparo; // velocidade de disparo
	/** Quando volta a disparar (contador de ciclos). */
	private int proxDisparo; // quando volta a disparar
	/** Delay desde que a animação de disparo começa até que "realmente" dispara. */
	private final int frameDisparoDelay; // delay desde que a animação de disparo começa até que "realmente" dispara

	/**
	 * Construtor da torre.
	 * 
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

	/**
	 * Atualiza o contador do ciclo de disparo, incrementando o tempo até o próximo disparo.
	 */
	protected void atualizarCicloDisparo() {
		proxDisparo++;
	}

	/**
	 * Verifica se a torre pode disparar com base no ritmo de disparo.
	 * 
	 */
	protected boolean podeDisparar() {
		return proxDisparo >= ritmoDisparo;
	}

	/**
	 * Reseta o contador de tempo para disparar, permitindo um novo ciclo.
	 * 
	 */
	protected int resetTempoDisparar() {
		return proxDisparo = 0;
	}

	/**
	 * Sincroniza a animação de disparo com o ciclo de disparo, iniciando a animação de ataque se necessário.
	 * 
	 */
	protected void sincronizarFrameDisparo(ComponenteMultiAnimado anim) {
		if (proxDisparo + frameDisparoDelay >= ritmoDisparo) {
			if (anim.getAnim() != ATAQUE_ANIM) {
				anim.setAnim(ATAQUE_ANIM);
				anim.reset();
			}
		}
	}

	/**
	 * Define o componente visual da torre.
	 * 
	 */
	protected void setComponente(ComponenteMultiAnimado v) {
		imagem = v;
	}

	/**
	 * Define o mundo onde a torre está localizada.
	 * 
	 */
	@Override
	public void setMundo(Mundo w) {
		mundo = w;
	}

	/**
	 * Retorna o mundo onde a torre está localizada.
	 * 
	 */
	@Override
	public Mundo getMundo() {
		return mundo;
	}

	/**
	 * Retorna o componente visual da torre.
	 * 
	 */
	@Override
	public ComponenteMultiAnimado getComponente() {
		return imagem;
	}

	/**
	 * Define a posição da torre no mundo.
	 * 
	 */
	@Override
	public void setPosicao(Point p) {
		imagem.setPosicaoCentro(p);
	}

	/**
	 * Retorna o ponto de disparo da torre (relativo ao centro).
	 * 
	 */
	@Override
	public Point getPontoDisparo() {
		return pontoDisparo;
	}

	/**
	 * Define o raio de ação da torre.
	 * 
	 */
	@Override
	public void setRaioAcao(int raio) {
		raioAtaque = raio;
	}

	/**
	 * Retorna o raio de ação da torre.
	 * 
	 */
	@Override
	public int getRaioAcao() {
		return raioAtaque;
	}

	/**
	 * Desenha a torre na tela.
	 * 
	 */
	@Override
	public void desenhar(Graphics2D g) {
		imagem.desenhar(g);
	}

	/**
	 * Desenha o raio de ação da torre como um círculo semi-transparente.
	 * 
	 */
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

	/**
	 * Define a estratégia de ataque da torre.
	 * 
	 */
	@Override
	public void setEstrategia(torre.estrategia.EstrategiaAtaque estrategia) {
		this.estrategia = estrategia;
	}

	/**
	 * Retorna a estratégia de ataque atual da torre.
	 * 
	 */
	@Override
	public torre.estrategia.EstrategiaAtaque getEstrategia() {
		return estrategia;
	}

	/**
	 * Template method que define o algoritmo geral de ataque das torres.
	 * 
	 */
	@Override
	public Projetil[] atacar(List<Bloon> bloons) {
		atualizarCicloDisparo();
		ComponenteMultiAnimado anim = getComponente();

		// já acabou a animação de disparar? volta à animação de pausa
		if (anim.getAnim() == ATAQUE_ANIM && anim.numCiclosFeitos() >= 1) {
			anim.setAnim(PAUSA_ANIM);
		}

		// Filtra alvos no alcance usando método template
		List<Bloon> alvosPossiveis = filtrarAlvosNoAlcance(bloons);

		// Escolhe o alvo usando a estratégia definida
		Bloon alvo = escolherAlvo(alvosPossiveis);

		// Se tiver alvo, orienta a torre na direção dele
		if (alvo != null || podeDispararSemAlvo()) {
			orientarTorre(alvo);
		}

		// se vai disparar daqui a pouco, começamos já com a animação de ataque
		sincronizarFrameDisparo(anim);

		// se ainda não está na altura de disparar, ou não tem condições para disparar, não dispara
		if (!podeDisparar() || (!podeDispararSemAlvo() && alvo == null))
			return new Projetil[0];

		// disparar
		resetTempoDisparar();
		return criarProjeteis(alvo);
	}

	/**
	 * Escolhe o alvo usando a estratégia de ataque.
	 */
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
	 */
	protected List<Bloon> getBloonsInRadius(List<Bloon> bloons, Point center, int radius) {
		return bloons.stream().filter(b -> DetectorColisoes.intersectam(b.getBounds(), center, radius)).toList();
	}

	/**
	 * Retorna uma lista com os bloons que intersetam um segmento de reta
	 * 
	 */
	protected List<Bloon> getBloonsInLine(List<Bloon> bloons, Point p1, Point p2) {
		return bloons.stream().filter(b -> b.getBounds().intersectsLine(p1.x, p1.y, p2.x, p2.y)).toList();
	}

	/**
	 * Clona a torre, criando uma cópia independente com imagem clonada e mundo resetado.
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
