package torre.estrategia;

import java.util.List;
import bloon.Bloon;
import torre.Torre;

/**
 * Ataca o bloon mais atrás no caminho.
 */
public class EstrategiaUltimo implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloons) {
        // Se não há bloons, retorna null
        if (bloons.isEmpty())
            return null;
        // Escolhe o bloon com menor avanço no caminho
        return bloons.stream()
                .min((b1, b2) -> Integer.compare(b1.getPosicaoNoCaminho(), b2.getPosicaoNoCaminho()))
                .orElse(null);
    }

    @Override
    public String getNome() {
        return "Último";
    }
}
