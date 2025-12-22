package torre.estrategia;

import java.awt.Point;
import java.util.List;
import bloon.Bloon;
import torre.Torre;

public class EstrategiaPerto implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloons) {
        if (bloons.isEmpty())
            return null;
        Point centro = t.getComponente().getPosicaoCentro();
        // Escolhe o bloon fisicamente mais perto da torre
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
