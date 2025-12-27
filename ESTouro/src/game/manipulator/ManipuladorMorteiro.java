package game.manipulator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;

import torre.TorreMorteiro;

/**
 * Manipulador para a torre morteiro. Configura a área
 * para a qual o morteiro dispara.
 */
public class ManipuladorMorteiro extends ManipuladorVazio {

	// composite para usar transparências nas miras
	private static final AlphaComposite transp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

	/**
	 * Construtor para o manipulador do morteiro.
	 * 
	 */
	public ManipuladorMorteiro(TorreMorteiro t) {
		super(t);
	}

	@Override
	public void desenhar(Graphics2D g) {
		// Salva o composite original
		Composite oldComp = g.getComposite();
		Point attackPoint = ((TorreMorteiro) getTorre()).getAreaAlvo();
		// Define transparência para a área de alvo
		g.setComposite(transp);
		g.setColor(Color.RED);
		if (attackPoint != null)
			g.fillOval(attackPoint.x - 20, attackPoint.y - 20, 40, 40);
		// Desenha um círculo vermelho na área de alvo
		g.fillOval(attackPoint.x - 20, attackPoint.y - 20, 40, 40);
		// Restaura o composite original
		g.setComposite(oldComp);
	}

	@Override
	public void mouseReleased(Point p) {
		// Define a área de alvo quando o mouse é solto
		((TorreMorteiro) getTorre()).setAreaAlvo(p);
	}

	@Override
	public void mouseDragged(Point p) {
		// Atualiza a área de alvo enquanto o mouse é arrastado
		((TorreMorteiro) getTorre()).setAreaAlvo(p);
	}

}
