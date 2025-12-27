package bloon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import prof.jogos2D.image.ComponenteVisual;

/**
 * Bloon que cria outros bloons. A criação de bloons é aleatória, dentro de uma
 * lista de bloons prováveis.
 */
public class BloonFabricante extends BloonSimples {
    // a lista de bloons prováveis de serem criados
    private List<Bloon> provaveis = new ArrayList<>();
    // Ritmo de criação: de quantos em quantos ciclos cria um novo bloon
    private int ritmoCriacao;
    // Contador para o próximo ciclo de criação
    private int proximaCriacao;

    /**
     * Cria um bloon que fabrica outros bloons
     * 
     * @param imagem       imagem do bloon
     * @param imagemPop    imagem de quando o bloon rebenta
     * @param veloc        velocidade de deslocamento
     * @param resist       resistência do bloon
     * @param valor        valor
     * @param ritmoCriacao de quantos em quantos ciclos cria um novo bloon
     */
    public BloonFabricante(ComponenteVisual imagem, ComponenteVisual imagemPop, float veloc, int resist, int valor,
            int ritmoCriacao) {
        super(imagem, imagemPop, veloc, resist, valor);
        this.ritmoCriacao = ritmoCriacao;
        this.proximaCriacao = ritmoCriacao;
    }

    /**
     * Adiciona um bloon à lista dos bloons prováveis
     * 
     * @param b o bloon a poder ser criado
     */
    public void addBloonProvavel(Bloon b) {
        // Adiciona o bloon à lista de possíveis para criação
        provaveis.add(b);
    }

    @Override
    public void mover() {
        // Move o bloon fabricante como um bloon simples
        super.mover();
        // se por acaso já saiu não faz nada
        if (getResistencia() <= 0)
            return;
        // Decrementa o contador para a próxima criação
        proximaCriacao--;
        // Se chegou o momento de criar um novo bloon
        if (proximaCriacao <= 0) {
            // decidir aleatoriamente qual o bloon a "disparar"
            int idx = ThreadLocalRandom.current().nextInt(provaveis.size());
            // colocar o bloon um pouco à frente deste
            int pathOffset = 3;
            int pos = getPosicaoNoCaminho();
            // Verifica se a posição à frente existe, senão coloca na mesma posição
            if (getCaminho().getPoint(pos + pathOffset) == null)
                pathOffset = 0;

            // usar um clone do bloon escolhido para evitar reutilização
            Bloon escolhido = provaveis.get(idx).clone();
            // Configura o caminho e mundo para o novo bloon
            escolhido.setCaminho(getCaminho());
            getMundo().addBloonPendente(escolhido);
            escolhido.setPosicaoNoCaminho(pos + pathOffset);
            // Adiciona os observadores do fabricante ao novo bloon
            getObservers().forEach(o -> escolhido.addBloonObserver(o));
            // Reseta o contador para o próximo ciclo
            proximaCriacao = ritmoCriacao;
        }
    }

    @Override
    public Bloon clone() {
        // Clonar a imagem e o pop para garantir independência visual
        BloonFabricante clone = new BloonFabricante(getComponente().clone(), getPopComponente().clone(),
                getVelocidade(), getResistencia(), getValor(), ritmoCriacao);

        // Copiar a lista de prováveis
        for (Bloon b : provaveis) {
            clone.addBloonProvavel(b.clone()); // Clonar os modelos para o novo fabricante
        }

        return clone;
    }
}
