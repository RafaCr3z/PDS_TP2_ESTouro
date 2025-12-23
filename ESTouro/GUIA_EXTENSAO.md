# ESTouro - Guia de Extensão

## Como Adicionar Novas Torres

### Exemplo: Torre Helicóptero (Dispara múltiplos dardos em padrão circular)

#### 1. Criar a Classe

```java
package torre;

import bloon.Bloon;
import java.awt.Point;
import java.awt.image.BufferedImage;
import prof.jogos2D.image.*;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.Dardo;
import torre.projetil.Projetil;

/**
 * Torre que dispara dardos em padrão circular
 */
public class TorreHelicoptero extends TorreDefault {

    /**
     * Cria a torre helicóptero
     * 
     * @param img a imagem da torre
     */
    public TorreHelicoptero(BufferedImage img) {
        super(new ComponenteMultiAnimado(new Point(50, 50), img, 2, 4, 2),
                35, 5, new Point(25, 0), 110);
    }

    @Override
    protected Projetil[] criarProjeteis(Bloon alvo) {
        Point centro = getComponente().getPosicaoCentro();
        double angle = getComponente().getAngulo();

        // Calcular ponto de disparo
        Point disparo = getPontoDisparo();
        double cosA = Math.cos(angle);
        double senA = Math.sin(angle);
        int px = (int) (disparo.x * cosA - disparo.y * senA);
        int py = (int) (disparo.y * cosA + disparo.x * senA);
        Point shoot = new Point(centro.x + px, centro.y + py);

        // Disparar 6 dardos em padrão circular
        Projetil p[] = new Projetil[6];
        for (int i = 0; i < 6; i++) {
            double dartAngle = angle + (i * Math.PI * 2 / 6);
            ComponenteVisual img = new ComponenteAnimado(new Point(),
                    (BufferedImage) ImageLoader.getLoader().getImage("data/torres/dardo.gif"), 2, 2);
            p[i] = new Dardo(img, dartAngle, 8, 1);
            p[i].setPosicao(shoot);
            p[i].setAlcance(getRaioAcao() + 30);
        }
        return p;
    }

    @Override
    public void accept(TorreVisitor v) {
        v.visit(this);
    }
}
```

#### 2. Registar em TorreCreator

```java
public TorreCreator() {
    fabrica.put("macaco", this::criarMacaco);
    fabrica.put("octo", this::criarOctogonal);
    fabrica.put("canhao", this::criarCanhao);
    fabrica.put("morteiro", this::criarMorteiro);
    fabrica.put("balista", this::criarBalista);
    fabrica.put("ninja", this::criarNinja);
    fabrica.put("sniper", this::criarSniper);
    fabrica.put("helicoptero", this::criarHelicoptero);  // ✅ NOVO
}

/** Cria uma torre helicóptero */
public Torre criarHelicoptero() {
    java.awt.Image img = loader.getImage("data/torres/helicoptero/imagem.gif");
    return new TorreHelicoptero((BufferedImage) img);
}
```

#### 3. Adicionar em TowerInfo.txt

```
<torre>
helicoptero
Helicóptero
650
Dispara dardos em padrão circular.
Cobertura de 360 graus.
</torre>
```

#### 4. Estrutura de Ficheiros

```
data/torres/helicoptero/
├── imagem.gif          (Imagem da torre)
└── (opcional) imagens adicionais
```

**Pronto!** A nova torre:
- ✅ Suporta todas as 6 estratégias de ataque
- ✅ Aparece automaticamente na UI
- ✅ Pode ser comprada e colocada no mapa
- ✅ Interage com todos os bloons existentes

---

## Como Adicionar Novos Bloons

### Exemplo: Bloon Invisível (Multi-camada que fica invisível periodicamente)

#### 1. Criar Classe Base

```java
package bloon;

import prof.jogos2D.image.ComponenteVisual;

/**
 * Bloon invisível que fica invisível durante alguns ciclos
 */
public class BloonInvisivel extends BloonMultiCamada {
    
    private int cicloCego = 0;          // Ciclos restantes invisível
    private static final int DURACAO_INVISIBILIDADE = 30;
    private static final int INTERVALO_INVISIBILIDADE = 60;

    public BloonInvisivel(ComponenteVisual imagem, ComponenteVisual imagemPop,
            float veloc, int resist, int valor) {
        super(imagem, imagemPop, veloc, resist, valor);
        this.cicloCego = 0;
    }

    @Override
    public void mover() {
        super.mover();
        
        // Atualizar estado de invisibilidade
        if (cicloCego > 0) {
            cicloCego--;
        } else if (cicloCego == 0 && getResistencia() > 0) {
            // Ativar invisibilidade
            cicloCego = DURACAO_INVISIBILIDADE;
        }
    }

    public boolean estaInvisivel() {
        return cicloCego > INTERVALO_INVISIBILIDADE - DURACAO_INVISIBILIDADE;
    }

    @Override
    public void desenhar(Graphics2D g) {
        if (!estaInvisivel()) {
            super.desenhar(g);
        } else {
            // Desenhar com transparência
            Composite oldComp = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            super.desenhar(g);
            g.setComposite(oldComp);
        }
    }

    @Override
    public Bloon clone() {
        BloonInvisivel clone = new BloonInvisivel(
                getComponente().clone(), 
                getPopComponente().clone(),
                getVelocidade(), 
                getResistencia(), 
                getValor());
        
        for (Bloon b : filhosInternal) {
            clone.addBloon(b.clone());
        }
        return clone;
    }
}
```

#### 2. Adicionar em BloonCreator

```java
public BloonInvisivel() {
    fabrica.put("invisivel", this::criarInvisivel);
}

public Bloon criarInvisivel() {
    ComponenteVisual imagem = new ComponenteSimples(
            loader.getImage("data/bloons/invisivel/imagem.gif"));
    ComponenteVisual imagemPop = getImagemPop();
    
    BloonInvisivel inv = new BloonInvisivel(imagem, imagemPop, 4.5f, 5, 15);
    inv.addBloon(criarAzul());      // Liberta azul
    inv.addBloon(criarVermelho());  // Liberta vermelho
    return inv;
}
```

#### 3. Adicionar em Níveis

```
data/niveis/nivel_5_1.txt
```

Adicionar bloons invisíveis ao nível:
```
invisivel
invisivel,invisivel
```

**Pronto!** O novo bloon:
- ✅ Aparece em níveis quando configurado
- ✅ Interage com todas as torres
- ✅ Comportamento especial de invisibilidade
- ✅ Pode ser decorado com armadura/escudo

---

## Como Adicionar Novas Estratégias de Ataque

### Exemplo: Estratégia Múltiplos (Ataca o ponto onde houver mais bloons num raio pequeno)

#### 1. Criar Classe de Estratégia

```java
package torre.estrategia;

import java.util.List;
import bloon.Bloon;
import prof.jogos2D.util.DetectorColisoes;
import torre.Torre;

/**
 * Estratégia que escolhe o bloon no ponto com máxima densidade.
 * Útil para torres com ataque em área (morteiro, canhão).
 */
public class EstrategiaMultiplos implements EstrategiaAtaque {
    
    private static final int RAIO_DENSIDADE = 50;

    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloons) {
        if (bloons.isEmpty())
            return null;
        
        Bloon melhor = bloons.get(0);
        int maxVizinhos = contarVizinhos(melhor, bloons);
        
        for (Bloon b : bloons) {
            int vizinhos = contarVizinhos(b, bloons);
            if (vizinhos > maxVizinhos) {
                maxVizinhos = vizinhos;
                melhor = b;
            }
        }
        
        return melhor;
    }

    private int contarVizinhos(Bloon b, List<Bloon> bloons) {
        java.awt.Point pos = b.getComponente().getPosicaoCentro();
        return (int) bloons.stream()
                .filter(outro -> DetectorColisoes.intersectam(
                        outro.getBounds(), pos, RAIO_DENSIDADE))
                .count();
    }

    @Override
    public String getNome() {
        return "Múltiplos";
    }
}
```

#### 2. Registar em ConfiguradorTorres

```java
private void criarBotoesAtaques(JPanel painelAtaques) {
    painelAtaques.add(criarBotaoAtaque(new EstrategiaPrimeiro()));
    painelAtaques.add(criarBotaoAtaque(new EstrategiaUltimo()));
    painelAtaques.add(criarBotaoAtaque(new EstrategiaPerto()));
    painelAtaques.add(criarBotaoAtaque(new EstrategiaLonge()));
    painelAtaques.add(criarBotaoAtaque(new EstrategiaForte()));
    painelAtaques.add(criarBotaoAtaque(new EstrategiaJuntos()));
    painelAtaques.add(criarBotaoAtaque(new EstrategiaMultiplos()));  // ✅ NOVO
}
```

**Pronto!** A estratégia:
- ✅ Aparece automaticamente em todas as torres
- ✅ Funciona com torre existente sem mudanças
- ✅ Pode ser testada isoladamente

---

## Como Adicionar Decoradores de Bloons

### Exemplo: Bloon Rápido (Aumenta velocidade)

#### 1. Criar Decorador

```java
package bloon.decorador;

import bloon.Bloon;

/**
 * Decorador que aumenta a velocidade de um bloon
 */
public class BloonRapido extends BloonDecorator {
    
    private float velocidadeExtra;

    public BloonRapido(Bloon bloon, float velocidadeExtra) {
        super(bloon);
        this.velocidadeExtra = velocidadeExtra;
    }

    @Override
    public float getVelocidade() {
        return bloon.getVelocidade() + velocidadeExtra;
    }

    @Override
    public void setVelocidade(float veloc) {
        // Ao mudar velocidade, remover extra
        bloon.setVelocidade(veloc - velocidadeExtra);
    }

    @Override
    public Bloon clone() {
        return new BloonRapido(bloon.clone(), velocidadeExtra);
    }
}
```

#### 2. Usar em Criações

```java
public Bloon criarVermelhoSupeRapido() {
    Bloon vermelho = criarVermelho();
    return new BloonRapido(vermelho, 2.0f);  // Adiciona 2 de velocidade
}
```

#### 3. Usar em Níveis

```
vermelho_super_rapido
vermelho_super_rapido,vermelho_super_rapido
```

**Pronto!** O decorador:
- ✅ Pode ser combinado com outros decoradores
- ✅ Altera comportamento sem alterar classe original
- ✅ Totalmente composável

---

## Checklist de Implementação

### Nova Torre
- [ ] Criar classe estendendo `TorreDefault`
- [ ] Implementar `criarProjeteis(Bloon alvo)`
- [ ] Implementar `accept(TorreVisitor v)`
- [ ] Registar em `TorreCreator`
- [ ] Adicionar em `TowerInfo.txt`
- [ ] Criar ficheiros de imagem em `data/torres/[nome]/`
- [ ] Testar todas as estratégias de ataque
- [ ] Testar com todos os tipos de bloons
- [ ] Documentar parâmetros

### Novo Bloon
- [ ] Criar classe estendendo `BloonSimples` ou `BloonMultiCamada`
- [ ] Implementar `clone()`
- [ ] Implementar métodos abstratos de desenho
- [ ] Registar em `BloonCreator`
- [ ] Criar ficheiros de imagem em `data/bloons/[nome]/`
- [ ] Testar com todas as torres
- [ ] Testar com decoradores
- [ ] Testar em Zepelims
- [ ] Documentar comportamento

### Nova Estratégia
- [ ] Criar classe implementando `EstrategiaAtaque`
- [ ] Implementar `escolherAlvo(Torre t, List<Bloon> bloons)`
- [ ] Implementar `getNome()`
- [ ] Registar em `ConfiguradorTorres`
- [ ] Testar com todas as torres
- [ ] Documentar caso de uso

### Novo Decorador
- [ ] Estender `BloonDecorator`
- [ ] Sobrescrever métodos relevantes
- [ ] Implementar `clone()`
- [ ] Testar composição com outros decoradores
- [ ] Documentar efeito

---

## Padrões de Código Recomendados

### Ordem de Inicialização em Construtores

```java
public TorraNova(BufferedImage img) {
    // ComponenteMultiAnimado(posição, imagem, largura_tile, altura_tile, num_tiles)
    // (ritmo_disparo, delay_animacao, ponto_disparo, raio_acao)
    super(
        new ComponenteMultiAnimado(new Point(50, 50), img, 2, 4, 2),
        30,  // ritmo_disparo
        8,   // frameDisparoDelay
        new Point(15, 15),  // pontoDisparo
        100  // raioAtaque
    );
}
```

### Criar Projéteis com Padrão

```java
@Override
protected Projetil[] criarProjeteis(Bloon alvo) {
    // 1. Obter posição e ângulo da torre
    Point centro = getComponente().getPosicaoCentro();
    double angle = getComponente().getAngulo();

    // 2. Calcular ponto de disparo (relativo ao centro)
    Point disparo = getPontoDisparo();
    double cosA = Math.cos(angle);
    double senA = Math.sin(angle);
    int px = (int) (disparo.x * cosA - disparo.y * senA);
    int py = (int) (disparo.y * cosA + disparo.x * senA);
    Point shoot = new Point(centro.x + px, centro.y + py);

    // 3. Criar array de projéteis
    Projetil p[] = new Projetil[quantidade];
    
    // 4. Para cada projétil
    for (int i = 0; i < quantidade; i++) {
        ComponenteVisual img = new ComponenteAnimado(...);
        p[i] = new TipoProjetil(img, angle, velocidade, dano);
        p[i].setPosicao(shoot);
        p[i].setAlcance(getRaioAcao() + margem);
    }
    
    return p;
}
```

---

## Troubleshooting

### Torre não aparece na UI
1. Verificar `TowerInfo.txt` tem 7 torres (contagem no topo)
2. Verificar sintaxe XML `<torre>...</torre>`
3. Verificar nome exato em `TorreCreator`

### Bloon não é criado
1. Verificar nome em `BloonCreator`
2. Verificar ficheiros de imagem existem
3. Verificar estrutura de directório

### Estratégia não funciona
1. Verificar nome em `getNome()`
2. Verificar registada em `ConfiguradorTorres`
3. Verificar não tem typos em seleção de alvo

### Imagens não carregam
1. Verificar caminho relativo a `data/`
2. Verificar formato GIF ou PNG
3. Verificar permissões de ficheiro

---

## Recursos Úteis

### Classes Importantes
- `TorreDefault` - Base para todas as torres
- `BloonDecorator` - Base para decoradores
- `EstrategiaAtaque` - Interface para estratégias
- `DetectorColisoes` - Utilitários de geometria
- `ImageLoader` - Carregamento de imagens
- `ComponenteMultiAnimado` - Animações de torres

### Ficheiros de Configuração
- `data/torres/TowerInfo.txt` - Info das torres
- `data/niveis/*.txt` - Níveis do jogo
- `data/torres/tracks.txt` - Configuração de pistas

### Directórios de Recursos
- `data/torres/[nome]/` - Imagens das torres
- `data/bloons/[nome]/` - Imagens dos bloons
- `data/misc/` - Efeitos e assets diversos

---

Este guia permite estender ESTouro com completa flexibilidade mantendo qualidade arquitetural.
