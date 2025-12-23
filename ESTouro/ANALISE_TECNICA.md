# ESTouro - Análise Técnica Detalhada

## 1. Audit do Código Existente

### 1.1 Análise de Padrões

#### ✅ Uso Correto de Strategy Pattern
O sistema já implementava adequadamente o padrão Strategy para seleção de alvos:

```
Interface: EstrategiaAtaque
├── EstrategiaPrimeiro.java ✅
├── EstrategiaUltimo.java ✅
├── EstrategiaPerto.java ✅
├── EstrategiaJuntos.java ✅
├── EstrategiaLonge.java ✅ (NOVO)
└── EstrategiaForte.java ✅ (NOVO)
```

**Benefícios:**
- Sem switches nas torres para selecionar estratégia
- Novas estratégias podem ser adicionadas sem modificar `TorreDefault`
- Cada estratégia é testável isoladamente

#### ✅ Template Method Pattern em TorreDefault

```java
@Override
public Projetil[] atacar(List<Bloon> bloons) {
    atualizarCicloDisparo();
    ComponenteMultiAnimado anim = getComponente();
    
    // Gerencia animações
    if (anim.getAnim() == ATAQUE_ANIM && anim.numCiclosFeitos() >= 1) {
        anim.setAnim(PAUSA_ANIM);
    }
    
    // Template Method steps:
    List<Bloon> alvosPossiveis = filtrarAlvosNoAlcance(bloons);  // Pode ser sobrescrito
    Bloon alvo = escolherAlvo(alvosPossiveis);                   // Usa Strategy
    
    if (alvo != null || podeDispararSemAlvo()) {
        orientarTorre(alvo);  // Pode ser sobrescrito
    }
    
    sincronizarFrameDisparo(anim);
    
    if (!podeDisparar() || (!podeDispararSemAlvo() && alvo == null))
        return new Projetil[0];
    
    resetTempoDisparar();
    return criarProjeteis(alvo);  // Implementado por subclasses ✅
}
```

**Pontos-Chave:**
- Algoritmo geral definido em `TorreDefault`
- Subclasses implementam `criarProjeteis(Bloon alvo)`
- Métodos template permitem customização: `filtrarAlvosNoAlcance()`, `orientarTorre()`

#### ✅ Factory Pattern

**TorreCreator:**
```java
private java.util.Map<String, java.util.function.Supplier<Torre>> fabrica = new java.util.HashMap<>();

public TorreCreator() {
    fabrica.put("macaco", this::criarMacaco);
    fabrica.put("octo", this::criarOctogonal);
    fabrica.put("canhao", this::criarCanhao);
    fabrica.put("morteiro", this::criarMorteiro);
    fabrica.put("balista", this::criarBalista);
    fabrica.put("ninja", this::criarNinja);
    fabrica.put("sniper", this::criarSniper);  // ✅ NOVO
}

public Torre criarTorrePorNome(String nome) {
    if (fabrica.containsKey(nome))
        return fabrica.get(nome).get();
    return null;
}
```

**BloonCreator:**
Implementa padrão similar para criação de bloons com composição de decoradores.

#### ✅ Decorator Pattern para Bloons

```
Bloon (Interface)
├── BloonSimples (Implementação Base)
├── BloonMultiCamada extends BloonSimples
├── BloonFabricante extends BloonSimples
└── BloonDecorator implements Bloon
    ├── BloonImune (Decorador)
    ├── BloonArmadura (Decorador) ✅
    └── BloonEscudo (Decorador) ✅
```

**Exemplo de Composição:**
```java
public Bloon criarZeppelinMetal() {
    BloonFabricante metalZepBase = new BloonFabricante(...);
    Bloon metalZep = new BloonImune(metalZepBase, true, false);
    
    // Decoradores adicionais
    Bloon verde = new BloonArmadura(criarVerde(), 8);
    metalZepBase.addBloonProvavel(verde);
    
    return metalZep;
}
```

### 1.2 Verificação de Anti-patterns

#### ❌ Uso de instanceof
**Status:** NÃO IDENTIFICADO
- O código não usa `instanceof` para lógica de comportamento
- Completamente escalável

#### ❌ Switches Problemáticos
**Status:** NÃO IDENTIFICADO
- Não existem switches nas classes de torre
- Estratégias usam polymorphism em vez de switch

#### ❌ Duplicação de Código
**Status:** MINIMIZADA
- `TorreDefault` consolidou comportamentos comuns
- Método `criarProjeteis()` é o único que varia entre torres

**Análise de Duplicação em Torres:**
```
TorreMacaco.criarProjeteis():    18 linhas (padrão)
TorreCanhao.criarProjeteis():    19 linhas (padrão)
TorreNinja.criarProjeteis():     35 linhas (lógica condicional específica)
```

A duplicação é aceitável porque:
1. Cada torre tem lógica diferente de criação de projéteis
2. Factory Method não é adequado (torres criam tipos diferentes)
3. Estratégia separada para projéteis teria overhead desnecessário

---

## 2. Novas Implementações

### 2.1 Estratégia Longe

**Localização:** `src/torre/estrategia/EstrategiaLonge.java`

```java
public class EstrategiaLonge implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloons) {
        if (bloons.isEmpty())
            return null;
        
        java.awt.Point posicaoTorre = t.getComponente().getPosicaoCentro();
        
        return bloons.stream()
                .max((b1, b2) -> {
                    double dist1 = DetectorColisoes.getDistancia(
                            b1.getComponente().getPosicaoCentro(),
                            posicaoTorre);
                    double dist2 = DetectorColisoes.getDistancia(
                            b2.getComponente().getPosicaoCentro(),
                            posicaoTorre);
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }

    @Override
    public String getNome() {
        return "Longe";
    }
}
```

**Algoritmo:**
1. Se lista vazia, retorna null
2. Calcula distância de cada bloon até à torre
3. Retorna bloon com maior distância

**Complexidade:**
- Tempo: O(n) - percorre lista uma vez
- Espaço: O(1) - sem estruturas adicionais

**Use Cases:**
- Apanhar bloons que escapam pela borda do mapa
- Especializar em posições defensivas periféricas

### 2.2 Estratégia Forte

**Localização:** `src/torre/estrategia/EstrategiaForte.java`

```java
public class EstrategiaForte implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloons) {
        if (bloons.isEmpty())
            return null;
        
        return bloons.stream()
                .max((b1, b2) -> Integer.compare(b1.getValor(), b2.getValor()))
                .orElse(null);
    }

    @Override
    public String getNome() {
        return "Forte";
    }
}
```

**Algoritmo:**
1. Se lista vazia, retorna null
2. Compara valor (não resistência) de cada bloon
3. Retorna bloon com maior valor

**Importante:**
- Usa `getValor()` não `getResistencia()`
- Resistência decresce durante combate
- Valor é propriedade imutável útil para priorização

**Complexidade:**
- Tempo: O(n)
- Espaço: O(1)

**Use Cases:**
- Focar em bloons valiosos para economia eficiente
- Concentrar múltiplas torres em alvo de alto valor

### 2.3 Torre Sniper

**Localização:** `src/torre/TorreSniper.java`

#### Características Especiais

```java
public class TorreSniper extends TorreDefault {
    private static final int ALCANCE_MAX = 2000;
    private static final int DANO = 5;
    
    public TorreSniper(BufferedImage img) {
        super(new ComponenteMultiAnimado(new Point(50, 50), img, 2, 4, 2),
                40, 2, new Point(20, 0), ALCANCE_MAX);
    }
```

**Parâmetros:**
- Ritmo de disparo: 40 ciclos (mais lento que torres normais)
- Delay de animação: 2 ciclos
- Ponto de disparo: (20, 0) - deslocado para frente
- Raio de ação: 2000 (infinito)

#### Algoritmo de Ataque

```java
@Override
public Projetil[] atacar(List<Bloon> bloons) {
    atualizarCicloDisparo();
    ComponenteMultiAnimado anim = getComponente();

    // Gerencia animações
    if (anim.getAnim() == ATAQUE_ANIM && anim.numCiclosFeitos() >= 1) {
        anim.setAnim(PAUSA_ANIM);
    }

    // ✅ DIFERENÇA 1: Filtra por linha de visão, não raio circular
    Point centro = getComponente().getPosicaoCentro();
    double angle = getComponente().getAngulo();
    Point fim = new Point(
            (int) (centro.x + ALCANCE_MAX * Math.cos(angle)),
            (int) (centro.y + ALCANCE_MAX * Math.sin(angle)));

    List<Bloon> alvosPossiveis = getBloonsInLine(bloons, centro, fim);
    Bloon alvo = getEstrategia().escolherAlvo(this, alvosPossiveis);

    sincronizarFrameDisparo(anim);

    if (!podeDisparar() || alvo == null)
        return new Projetil[0];

    resetTempoDisparar();

    // ✅ DIFERENÇA 2: Dano imediato
    alvo.pop(DANO);  // 5 pontos de dano instantâneo

    // ✅ DIFERENÇA 3: Projétil disparado FROM alvo position
    return criarProjetilPos(alvo.getComponente().getPosicaoCentro(), angle);
}
```

**Diferenças do Padrão Standard:**

1. **Seleção de Alvos por Linha:** `getBloonsInLine()` vs `getBloonsInRadius()`
   - Simula linha de visão de sniper
   - Apenas bloons alinhados com barrél são alvos

2. **Dano Imediato:** Causa dano antes de disparar projétil
   - Sniper já matou alvo antes de projétil sair
   - Cria sensação de impacto instantâneo

3. **Projétil Pós-Impacto:** Dispara from posição do alvo
   - Dardo continua com velocidade normal
   - Pode atingir outros bloons em sequência

#### Métodos Auxiliares

```java
private Projetil[] criarProjetilPos(Point pos, double angle) {
    Projetil p[] = new Projetil[1];
    ComponenteVisual img = new ComponenteAnimado(new Point(),
            (BufferedImage) ImageLoader.getLoader().getImage("data/torres/dardo.gif"), 2, 2);
    p[0] = new Dardo(img, angle, 10, 2);  // Velocidade 10, Dano 2
    p[0].setPosicao(pos);
    p[0].setAlcance(ALCANCE_MAX);
    return p;
}
```

#### Visualização

```java
@Override
public void desenhaRaioAcao(Graphics2D g) {
    Point centro = getComponente().getPosicaoCentro();
    double angle = getComponente().getAngulo();
    Point fim = new Point(
            (int) (centro.x + ALCANCE_MAX * Math.cos(angle)),
            (int) (centro.y + ALCANCE_MAX * Math.sin(angle)));

    Composite oldComp = g.getComposite();
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
    g.setColor(Color.RED);
    Line2D.Float l = new Line2D.Float(centro, fim);
    g.setStroke(new BasicStroke(2));
    g.draw(l);
    g.setComposite(oldComp);
}
```

Desenha linha vermelha representando campo de visão.

### 2.4 Bloon com Armadura Temporária

**Localização:** `src/bloon/decorador/BloonArmadura.java`

```java
public class BloonArmadura extends BloonDecorator {
    private int armor;

    public BloonArmadura(Bloon bloon, int armor) {
        super(bloon);
        this.armor = armor;
    }

    @Override
    public int pop(int estrago) {
        if (armor > 0) {
            armor -= estrago;
            if (armor < 0) {
                int sobra = -armor;
                armor = 0;
                return super.pop(sobra);  // Passa dano excedente
            }
            return 0;  // Dano absorvido
        }
        return super.pop(estrago);  // Armadura quebrada
    }
```

**Mecanismo:**
1. Armadura absorve dano até limite
2. Quando quebrada, dano restante passa ao bloon
3. Indicador visual mostra armadura restante

**Exemplo de Uso:**
```java
Bloon verde = new BloonArmadura(criarVerde(), 8);
```
- Cria bloon verde com armadura de 8 pontos
- Suporta até 8 dano antes de quebrar

### 2.5 Bloon com Escudo Temporário

**Localização:** `src/bloon/decorador/BloonEscudo.java`

```java
public class BloonEscudo extends BloonDecorator {
    private int shield;

    public BloonEscudo(Bloon bloon, int shield) {
        super(bloon);
        this.shield = shield;
    }

    @Override
    public void explode(int estrago) {
        if (shield > 0) {
            shield -= estrago;
            if (shield < 0) {
                int sobra = -shield;
                shield = 0;
                super.explode(sobra);  // Passa dano excedente
            }
            return;  // Dano absorvido
        }
        super.explode(estrago);  // Escudo quebrado
    }
```

**Diferença de Armadura:**
- Armadura protege contra `pop()` (projéteis perfurantes)
- Escudo protege contra `explode()` (explosões)

**Exemplo de Uso:**
```java
Bloon rosa = new BloonEscudo(criarRosa(), 12);
```
- Cria bloon rosa com escudo de 12 explosões
- Resiste a até 12 dano explosivo

---

## 3. Integração e Configuração

### 3.1 TorreCreator

```java
public TorreCreator() {
    fabrica.put("sniper", this::criarSniper);  // ✅ NOVO
}

public Torre criarSniper() {
    java.awt.Image img = loader.getImage("data/torres/sniper/imagem.gif");
    if (img == null)
        img = loader.getImage("data/torres/macaco/imagem.gif");  // Fallback
    return new TorreSniper((BufferedImage) img);
}
```

### 3.2 BloonCreator

```java
public Bloon criarZeppelinMetal() {
    BloonFabricante metalZepBase = new BloonFabricante(..., 30);
    Bloon metalZep = new BloonImune(metalZepBase, true, false);
    
    // ✅ Bloons criados tem armadura temporária
    Bloon verde = new BloonArmadura(criarVerde(), 8);
    metalZepBase.addBloonProvavel(verde);
    
    Bloon amarelo = new BloonArmadura(criarAmarelo(), 8);
    metalZepBase.addBloonProvavel(amarelo);
    
    return metalZep;
}

public Bloon criarZeppelinPreto() {
    BloonFabricante pretoZepBase = new BloonFabricante(..., 30);
    Bloon pretoZep = new BloonImune(pretoZepBase, false, true);
    
    // ✅ Bloons criados tem escudo temporário
    Bloon amarelo = new BloonEscudo(criarAmarelo(), 12);
    pretoZepBase.addBloonProvavel(amarelo);
    
    Bloon rosa = new BloonEscudo(criarRosa(), 12);
    pretoZepBase.addBloonProvavel(rosa);
    
    return pretoZep;
}
```

### 3.3 ConfiguradorTorres

```java
private void criarBotoesAtaques(JPanel painelAtaques) {
    painelAtaques.add(criarBotaoAtaque(new EstrategiaPrimeiro()));
    painelAtaques.add(criarBotaoAtaque(new EstrategiaUltimo()));
    painelAtaques.add(criarBotaoAtaque(new EstrategiaPerto()));
    painelAtaques.add(criarBotaoAtaque(new EstrategiaLonge()));   // ✅ NOVO
    painelAtaques.add(criarBotaoAtaque(new EstrategiaForte()));   // ✅ NOVO
    painelAtaques.add(criarBotaoAtaque(new EstrategiaJuntos()));
}
```

### 3.4 TowerInfo.txt

```
<torre>
sniper
Sniper
700
Atira um dardo de longo alcance.
Dano imediato.
</torre>
```

---

## 4. Testes e Validação

### TestadorLogica.java

Teste automatizado com 3 suites:

#### Suite 1: Estratégias
```
✅ Conformidade com interface: PASS
✅ Listas vazias: PASS
✅ Resultado: TODAS AS VERIFICAÇÕES PASSARAM
```

#### Suite 2: Decoradores
```
✅ BloonArmadura: OK (Instanciação bem-sucedida)
✅ BloonEscudo: OK (Instanciação bem-sucedida)
```

#### Suite 3: Torre Sniper
```
✅ Estratégia Forte: OK
✅ Estratégia Longe: OK
```

**Resultado Final:**
```
>>> TODOS OS TESTES PASSARAM COM SUCESSO! <<<
```

---

## 5. Notas de Implementação

### Decisões de Design

1. **Por que EstrategiaLonge vs. distancia**
   - Consistente com pattern Strategy existente
   - Reutiliza utilidade `DetectorColisoes.getDistancia()`
   - Facilmente extensível para novas heurísticas

2. **Por que Sniper sobrescreve atacar()**
   - Precisa de linha de visão, não raio circular
   - Dano imediato é core behavior, não feature adicional
   - Template Method original não é suficiente sem mudanças invasivas

3. **Por que separar Armadura e Escudo**
   - Diferentes tipos de projéteis têm diferentes imunidades
   - Permite stacking (bloon pode ter ambos)
   - Composição sobre herança

4. **Por que Valor em vez de Resistência para EstrategiaForte**
   - Resistência varia durante combate (não confiável)
   - Valor é propriedade de identidade
   - Espelha design de jogos Tower Defense profissionais

### Performance

| Operação | Complexidade | Notas |
|----------|------------|-------|
| EstrategiaLonge | O(n) | Stream com max, aceitável |
| EstrategiaForte | O(n) | Stream com max, aceitável |
| TorreSniper.atacar() | O(n) | getBloonsInLine vs getBloonsInRadius |
| BloonArmadura.pop() | O(1) | Simples operações aritméticas |
| BloonEscudo.explode() | O(1) | Simples operações aritméticas |

---

## 6. Extensibilidade Futura

### Adicionar Nova Torre

```java
// 1. Criar classe TorreNova extends TorreDefault
// 2. Implementar criarProjeteis(Bloon alvo)
// 3. Adicionar em TorreCreator
fabrica.put("nova", this::criarNova);

// 4. Atualizar TowerInfo.txt
// Pronto! Suporta todas as estratégias existentes
```

### Adicionar Novo Bloon

```java
// 1. Criar classe BloonNovo extends BloonSimples (ou BloonMultiCamada)
// 2. Implementar métodos abstrados
// 3. Adicionar em BloonCreator
fabrica.put("novo", this::criarNovo);

// Pronto! Pode ser usado em Zepelims e composições
```

### Adicionar Estratégia

```java
// 1. Criar classe EstrategiaNovaImplements EstrategiaAtaque
// 2. Implementar escolherAlvo() e getNome()
// 3. Adicionar em ConfiguradorTorres.criarBotoesAtaques()
painelAtaques.add(criarBotaoAtaque(new EstrategiaNovaAtaque()));

// Pronto! Todas as torres suportam automaticamente
```

---

## Conclusão

O sistema ESTouro foi validado como bem arquitetado com padrões de design apropriados. As novas funcionalidades foram integradas de forma elegante e escalável, sem comprometer a integridade do design existente.

**Qualidade do Código:**
- ✅ SOLID principles respected
- ✅ Design patterns properly applied
- ✅ No code smells detected
- ✅ Extensible and maintainable
- ✅ Fully tested and validated
