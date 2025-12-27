package torre.projetil;

import java.util.List;

import bloon.Bloon;
import prof.jogos2D.image.*;

/**
 * Classe que representa um dardo
 * 
 */
public class Dardo extends ProjetilDefault {

	/**
	 * Cria um dardo
	 * 
	 */
	public Dardo(ComponenteVisual img, double dir, double veloc, int estrago) {
		super(img, dir, veloc, estrago);
		img.setAngulo(dir);
	}

	@Override
	public void atualiza(List<Bloon> bloons) {
		// actualização normal do dardo
		super.atualiza(bloons);

		// para cada bloon vai verificar se lhe bateu, enquanto tiver estragos para
		// fazer
		for (Bloon b : bloons) {
			// verificar se bateu no bloon
			if (b.getBounds().intersects(getComponente().getBounds())) {
				setEstrago(b.pop(getEstrago()));
				if (getEstrago() <= 0) // já não faz estragos por isso pára
					break;
			}
		}
	}

}
