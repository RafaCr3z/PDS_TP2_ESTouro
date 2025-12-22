package torre.estrategia;

import java.util.List;
import bloon.Bloon;
import torre.Torre;

public class EstrategiaPrimeiro implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloons) {
        if (bloons.isEmpty())
            return null;
        // Escolhe o bloon com maior posição no caminho (mais à frente)
        return bloons.stream()
                .max((b1, b2) -> Integer.compare(b1.getPosicaoNoCaminho(), b2.getPosicaoNoCaminho()))
                .orElse(null);
    }

    @Override
    public String getNome() {
        return "Primeiro";
    }
}
