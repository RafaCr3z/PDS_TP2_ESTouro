package mundo;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bloon.Bloon;
import prof.jogos2D.image.ComponenteVisual;

import torre.*;
import torre.projetil.Projetil;

/**
 * classe que agrupa todos os elementos que se movem no jogo
 */
public class Mundo {

	/**
	 * listas com os vários elementos do jogo
	 */
	private List<Bloon> bloons = new ArrayList<>();
	private List<Torre> torres = new ArrayList<>();
	private List<Projetil> projeteis = new ArrayList<>();
	private List<ComponenteVisual> fxs = new ArrayList<>(); // efeitos visuais como explosões, etc
	private ComponenteVisual backGround; // imagem de fundo do mundo

	// lista de bloons que foram inseridos no mundo durante uma atualização, devido
	// a explosões de bloons, à criação de novos bloons por outros, etc
	private List<Bloon> bloonsPendentes = new ArrayList<>();

	/**
	 * atualiza o mundo, isto é, processa uma jogada
	 */
	public void atualizar() {
		// adicionar os que ficaram pendentes do ciclo anterior
		// lembrar que quando um bloon dá origem a outros, estes não podem ser
		// adicionados automaticamente senão serão logo processados e só o devem ser no
		// ciclo seguinte
		bloonsPendentes.forEach(this::addBloon);
		bloonsPendentes.clear();

		// move os bloons todos
		bloons.forEach(Bloon::mover);

		// move os projéteis todos, verificando se estes batem nos bloons
		projeteis.forEach(p -> p.atualiza(bloons));

		// verifica se as torres podem interagir com os bloons
		for (Torre t : torres) {
			// o resultado de um ataque pode ser um arrays de projéteis
			// se assim for temos de os adicionar aos projecteis
			Projetil ps[] = t.atacar(bloons);
			for (Projetil p : ps)
				addProjetil(p);
		}

		// depois de tudo atualizado vamos remover os bloons rebentados
		bloons.removeIf(b -> b.getResistencia() <= 0);

		// remover também os projéteis terminados
		projeteis.removeIf(p -> p.getEstrago() <= 0);
	}

	/**
	 * desenha o mundo e os seus constituintes
	 * 
	 */
	public void draw(Graphics2D g) {
		if (backGround != null)
			backGround.desenhar(g);
		bloons.forEach(b -> b.desenhar(g));
		torres.forEach(t -> t.desenhar(g));
		projeteis.forEach(p -> p.draw(g));
		fxs.forEach(fx -> fx.desenhar(g));

		// depois de tudo desenhado remover os efeitos visuais "fora de prazo"
		fxs.removeIf(fx -> fx.numCiclosFeitos() >= 1);
	}

	/**
	 * define a imagem de fundo do mundo
	 * 
	 */
	public void setFundo(ComponenteVisual fundo) {
		this.backGround = fundo;
	}

	/**
	 * adiciona um bloon ao mundo
	 * 
	 */
	public void addBloon(Bloon b) {
		b.setMundo(this); // associar o bloon ao mundo
		bloons.add(b);
	}

	/**
	 * remove um bloon do mundo
	 * 
	 */
	public void removeBloon(Bloon b) {
		b.setCaminho(null); // retira a informação do caminho
		b.setMundo(null); // retira a informação do mundo
		bloons.remove(b);
	}

	/**
	 * indica quantos bloons tem o mundo
	 * 
	 */
	public int getNumeroBloons() {
		return bloons.size() + bloonsPendentes.size();
	}

	/**
	 * Adiciona um bloon à lista dos pendentes.
	 * 
	 */
	public void addBloonPendente(Bloon b) {
		bloonsPendentes.add(b);
	}

	/**
	 * adiciona uma torre ao mundo
	 * 
	 */
	public void addTower(Torre t) {
		torres.add(t);
		t.setMundo(this);
	}

	/**
	 * remove uma torre do mundo
	 * 
	 */
	public void removeTower(Torre t) {
		torres.remove(t);
	}

	/**
	 * Retorna a torre que está numa dada posição do écran.
	 * 
	 */
	public Torre getTowerAt(Point pt) {
		return torres.stream().filter(t -> t.getComponente().getBounds().contains(pt)).findFirst().orElse(null);
	}

	/**
	 * adiciona um projétil ao mundo
	 * 
	 */
	public void addProjetil(Projetil p) {
		projeteis.add(p);
	}

	/**
	 * remove um projétil do mundo
	 * 
	 */
	public void removeProjetil(Projetil p) {
		projeteis.remove(p);
	}

	/**
	 * Adiciona um efeito especial ao mundo
	 * 
	 */
	public void addFx(ComponenteVisual fx) {
		fxs.add(fx);
	}

	/**
	 * Remove um efeito especial ao mundo
	 * 
	 */
	public void removeFx(ComponenteVisual fx) {
		fxs.remove(fx);
	}

	/**
	 * limpa o mundo todo
	 */
	public void limparTudo() {
		torres.clear();
		bloons.clear();
		bloonsPendentes.clear();
		projeteis.clear();
		fxs.clear();
	}

	/**
	 * limpa os elementos móveis do mundo, como
	 * os bloons e os projéteis
	 */
	public void limparMoveis() {
		bloons.clear();
		bloonsPendentes.clear();
		projeteis.clear();
	}

	/** indica se o nível já acabou */
	public boolean isOver() {
		return /* bloons.size() == 0 && */ fxs.size() == 0;
	}

	/** devolve todas as torres presentes no nível */
	public List<Torre> getTorres() {
		return Collections.unmodifiableList(torres);
	}
}
