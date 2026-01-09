package torre.estrategia;

import java.util.List;
import bloon.Bloon;
import torre.Torre;

/**
 * Ataca o bloon mais à frente no caminho.
 */
public class EstrategiaPrimeiro implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloons) {
        // Se não há bloons, retorna null
        if (bloons.isEmpty())
            return null;
        // Escolhe o bloon com maior avanço no caminho
        return bloons.stream()
                .max((b1, b2) -> Integer.compare(b1.getPosicaoNoCaminho(), b2.getPosicaoNoCaminho()))
                .orElse(null);
    }

    @Override
    public String getNome() {
        return "Primeiro";
    }
}
