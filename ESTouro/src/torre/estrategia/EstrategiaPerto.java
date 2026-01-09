package torre.estrategia;

import java.awt.Point;
import java.util.List;
import bloon.Bloon;
import torre.Torre;

/**
 * Ataca o bloon mais perto da torre.
 */
public class EstrategiaPerto implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloons) {
        // Se não há bloons, retorna null
        if (bloons.isEmpty())
            return null;
        Point centro = t.getComponente().getPosicaoCentro();
        // Escolhe o bloon com menor distância à torre
        return bloons.stream()
                .min((b1, b2) -> Double.compare(
                        b1.getComponente().getPosicaoCentro().distance(centro),
                        b2.getComponente().getPosicaoCentro().distance(centro)))
                .orElse(null);
    }

    @Override
    public String getNome() {
        return "Perto";
    }
}
