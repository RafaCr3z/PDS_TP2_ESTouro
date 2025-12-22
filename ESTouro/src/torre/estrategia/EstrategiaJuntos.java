package torre.estrategia;

import java.awt.Point;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import bloon.Bloon;
import torre.Torre;

public class EstrategiaJuntos implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloons) {
        if (bloons.isEmpty())
            return null;

        // Agrupa por posição (divisão inteira por 20 para criar "zonas")
        // Como feito no TorreMacaco original
        Map<Integer, List<Bloon>> posicoes = bloons.stream()
                .collect(Collectors.groupingBy(b -> b.getPosicaoNoCaminho() / 20));

        if (posicoes.isEmpty())
            return null;

        // Encontra a zona com mais bloons
        int posicaoComMais = Collections.max(posicoes.keySet(),
                (k1, k2) -> Integer.compare(posicoes.get(k1).size(), posicoes.get(k2).size()));

        // Retorna o primeiro bloon dessa zona
        List<Bloon> zona = posicoes.get(posicaoComMais);
        return zona != null && !zona.isEmpty() ? zona.get(0) : null;
    }

    @Override
    public String getNome() {
        return "Juntos";
    }
}
