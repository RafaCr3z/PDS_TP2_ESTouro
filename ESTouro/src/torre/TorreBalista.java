package torre;

import bloon.Bloon;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;
import prof.jogos2D.image.*;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.Dardo;
import torre.projetil.Projetil;

/**
 * Classe que representa a torre balista. Esta torre dispara 1 dardo enorme e
 * potente na direção especificada pelo jogador.
 */
public class TorreBalista extends TorreDefault {

	/** ponto de mira */
	private Point mira;

	/**
	 * Cria uma balista.
	 * 
	 * @param img imagem da balista
	 */
	public TorreBalista(BufferedImage img) {
		super(new ComponenteMultiAnimado(new Point(), img, 2, 4, 2),
				20, 0, new Point(20, -3), 100);
		setAnguloDisparo(0);
	}

	/**
	 * Define o ângulo de disparo da balista
	 * 
	 * @param angulo o novo ângulo
	 */
	public void setAnguloDisparo(float angulo) {
		getComponente().setAngulo(angulo);
		definirMira(angulo);
	}

	/**
	 * Define a pontaria, isto é, a posição para onde a balusta irá apontar
	 * 
	 * @param angulo angulo do disparo, para poder calcular a área de ataque
	 */
	private void definirMira(double angulo) {
		double cos = Math.cos(angulo);
		double sin = Math.sin(angulo);
		Point centro = getComponente().getPosicaoCentro();
		mira = new Point((int) (centro.x + getRaioAcao() * cos), (int) (centro.y + getRaioAcao() * sin));
	}

	/**
	 * Retorna o ponto para onde a balista irá disparar
	 * 
	 * @return o ponto para onde a balista irá disparar
	 */
	public Point getMira() {
		return mira;
	}

	@Override
	public void setPosicao(Point p) {
		super.setPosicao(p);
		definirMira(getComponente().getAngulo());
	}

	@Override
	public void desenhaRaioAcao(Graphics2D g) {
		Point centro = getComponente().getPosicaoCentro();
		Point mira = getMira();
		Composite oldComp = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.setColor(Color.BLUE);
		Line2D.Float l = new Line2D.Float(centro, mira);
		g.setStroke(new BasicStroke(20));
		g.draw(l);
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(18));
		g.draw(l);
		g.setComposite(oldComp);
	}

	@Override
	protected List<Bloon> filtrarAlvosNoAlcance(List<Bloon> bloons) {
		// Balista usa linha de visão em vez de raio circular
		return getBloonsInLine(bloons, getComponente().getPosicaoCentro(), getMira());
	}

	@Override
	protected void orientarTorre(Bloon alvo) {
		// Balista não se orienta automaticamente para alvos
		// Mantém direção definida pelo jogador
	}

	@Override
	protected Projetil[] criarProjeteis(Bloon alvo) {
		// Balista dispara um dardo enorme e potente
		Point centro = getComponente().getPosicaoCentro();
		double angle = getComponente().getAngulo();
		Point disparo = getPontoDisparo();

		double cosA = Math.cos(angle);
		double senA = Math.sin(angle);
		int px = (int) (disparo.x * cosA - disparo.y * senA);
		int py = (int) (disparo.y * cosA + disparo.x * senA);
		Point shoot = new Point(centro.x + px, centro.y + py);

		// Criar dardo poderoso
		Projetil p[] = new Projetil[1];
		ComponenteVisual img = new ComponenteSimples(ImageLoader.getLoader().getImage("data/torres/seta.gif"));
		p[0] = new Dardo(img, angle, 10, 20);
		p[0].setPosicao(shoot);
		p[0].setAlcance(getRaioAcao() + 50);
		return p;
	}

	@Override
	public Torre clone() {
		TorreBalista copia = (TorreBalista) super.clone();
		copia.mira = new Point(mira);
		return copia;
	}

	@Override
	public void accept(TorreVisitor v) {
		v.visit(this);
	}
}
