package torre.estrategia;

import bloon.Bloon;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import torre.Torre;

public class EstrategiaJuntos implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloons) {
        if (bloons.isEmpty())
            return null;

        // Agrupa bloons por zonas do caminho (divisão por 20)
        Map<Integer, List<Bloon>> posicoes = bloons.stream()
                .collect(Collectors.groupingBy(b -> b.getPosicaoNoCaminho() / 20));

        if (posicoes.isEmpty())
            return null;

        // Escolhe a zona mais populosa
        int posicaoComMais = Collections.max(posicoes.keySet(),
                (k1, k2) -> Integer.compare(posicoes.get(k1).size(), posicoes.get(k2).size()));

        // Retorna o primeiro dessa zona
        List<Bloon> zona = posicoes.get(posicaoComMais);
        return zona != null && !zona.isEmpty() ? zona.get(0) : null;
    }

    @Override
    public String getNome() {
        return "Juntos";
    }
}
