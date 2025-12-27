package bloon;

import java.util.ArrayList;
import java.util.List;

import prof.jogos2D.image.ComponenteVisual;

/**
 * Classe que representa um bloon que quando estoura liberta vários bloons que
 * estavam no seu interior.
 */
public class BloonMultiCamada extends BloonSimples {

	/** A lista com os bloons que serão libertados quando este se estoura */
	private List<Bloon> bloons = new ArrayList<>();

	/**
	 * Cria um bloon multicamada
	 * 
	 */
	public BloonMultiCamada(ComponenteVisual img, ComponenteVisual imgPop, float veloc, int resist, int valor) {
		super(img, imgPop, veloc, resist, valor);
	}

	/**
	 * Adicona um bloon à lista dos que serão libertados quando este estoura.
	 * 
	 */
	public void addBloon(Bloon b) {
		// Adiciona o bloon à lista de bloons internos
		bloons.add(b);
	}

	@Override
	public int pop(int damage) {
		// Se já não tem resistência, retorna o dano sem fazer nada
		if (getResistencia() <= 0)
			return damage;

		// Aplica o dano e obtém o dano restante
		damage = sofreEstrago(damage);

		// ver se já saiu da pista, senão não faz nada
		if (getCaminho().getPoint(getPosicaoNoCaminho()) == null)
			return damage;

		// Se ainda tem resistência, retorna o dano restante
		if (getResistencia() > 0)
			return damage;

		// Liberta os bloons internos
		libertarBloons();

		// assinalar este como estando rebentado
		notificarBloonEstourou();

		// Adiciona o efeito visual de explosão
		getPopComponente().setPosicaoCentro(getComponente().getPosicaoCentro());
		getMundo().addFx(getPopComponente());

		return damage;
	}

	@Override
	public void explode(int damage) {
		// Se já não tem resistência, não faz nada
		if (getResistencia() <= 0)
			return;

		// Aplica o dano explosivo
		damage = sofreEstrago(damage);

		// ver se já saiu da pista, senão não faz nada
		if (getCaminho().getPoint(getPosicaoNoCaminho()) == null)
			return;

		// Se ainda tem resistência, não liberta bloons
		if (getResistencia() > 0)
			return;

		// Liberta os bloons internos
		libertarBloons();

		// assinalar este como estando rebentado
		notificarBloonEstourou();

		// Adiciona o efeito visual de explosão
		getPopComponente().setPosicaoCentro(getComponente().getPosicaoCentro());
		getMundo().addFx(getPopComponente());
	}

	private void libertarBloons() {
		// colocar os bloons no local deste, ou à frente e atrás
		int pathOffset = 0;
		for (Bloon b : bloons) {
			// Configura o caminho e mundo para cada bloon liberto
			b.setCaminho(getCaminho());
			getMundo().addBloonPendente(b);
			int pos = getPosicaoNoCaminho();
			// Define a posição no caminho com offset alternado
			b.setPosicaoNoCaminho(pos + pathOffset);
			// Alterna o offset para distribuir os bloons
			pathOffset = pathOffset > 0 ? -pathOffset : -pathOffset + 2;
			// se estiver fora do caminho recoloca na posição inicial
			if (getCaminho().getPoint(pos + pathOffset) == null)
				pathOffset = 0;
			// Adiciona os observadores do bloon pai aos filhos
			getObservers().forEach(o -> b.addBloonObserver(o));
		}
	}
}
