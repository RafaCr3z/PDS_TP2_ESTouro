package torre.estrategia;

import java.util.List;
import bloon.Bloon;
import torre.Torre;

/**
 * Estratégia de ataque que escolhe o bloon menos avançado no caminho (último).
 */
public class EstrategiaUltimo implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloons) {
        // Se não há bloons, retorna null
        if (bloons.isEmpty())
            return null;
        // Escolhe o bloon com menor posição no caminho (mais atrás)
        return bloons.stream()
                .min((b1, b2) -> Integer.compare(b1.getPosicaoNoCaminho(), b2.getPosicaoNoCaminho()))
                .orElse(null);
    }

    @Override
    public String getNome() {
        // Retorna o nome da estratégia
        return "Último";
    }
}
