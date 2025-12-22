package torre.estrategia;

import java.util.List;
import bloon.Bloon;
import torre.Torre;

public class EstrategiaUltimo implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloons) {
        if (bloons.isEmpty())
            return null;
        // Escolhe o bloon com menor posição no caminho (mais atrás)
        return bloons.stream()
                .min((b1, b2) -> Integer.compare(b1.getPosicaoNoCaminho(), b2.getPosicaoNoCaminho()))
                .orElse(null);
    }

    @Override
    public String getNome() {
        return "Último";
    }
}
