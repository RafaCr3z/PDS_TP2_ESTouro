# Guia de Estudo para Defesa - ESTouro

## 1. Visão Geral do Projeto

**ESTouro** é um jogo tipo Tower Defense (inspirado em Bloons TD) desenvolvido em Java. O jogador coloca torres numa pista para destruir balões (bloons) que seguem um caminho. Se muitos balões escaparem, o jogador perde vidas.

### Componentes Principais:
- **Bloons**: Inimigos que seguem um caminho
- **Torres**: Defesas que atacam os bloons
- **Mundo**: Gestão do estado do jogo
- **Sistema de Níveis**: Gestão de rounds e progressão

---

## 2. Padrões de Design Implementados

### 2.1 **Decorator Pattern** (Bloons)

**Localização**: `bloon.decorador.*`

**Propósito**: Adicionar funcionalidades aos bloons dinamicamente (armadura, escudo, imunidades)

**Implementação**:
```
Bloon (interface)
  ↑
  ├── BloonSimples (implementação base)
  └── BloonDecorator (decorador abstrato)
       ↑
       ├── BloonArmadura (absorve dano)
       ├── BloonEscudo (proteção adicional)
       └── BloonImune (imunidade a certos ataques)
```

**Exemplo Prático**:
- Um bloon pode ter armadura que absorve dano antes de afetar a resistência base
- `BloonArmadura` intercepta o método `pop()` e absorve dano até a armadura quebrar
- Permite combinar múltiplas proteções: `new BloonArmadura(new BloonEscudo(bloonBase), 5)`

**Vantagens**:
- Extensibilidade: novos decoradores sem alterar código existente
- Flexibilidade: combinar proteções de forma dinâmica
- Princípio Open/Closed: aberto para extensão, fechado para modificação

---

### 2.2 **Composite Pattern** (Bloons Multi-Camada)

**Localização**: `BloonMultiCamada`

**Propósito**: Bloons que contêm outros bloons no interior

**Implementação**:
- `BloonMultiCamada` mantém uma lista de bloons internos
- Quando estoura, liberta os bloons contidos
- Os bloons libertados são posicionados no caminho (à frente/atrás do original)

**Código Chave**:
```java
private void libertarBloons() {
    int pathOffset = 0;
    for (Bloon b : bloons) {
        b.setCaminho(getCaminho());
        getMundo().addBloonPendente(b);
        b.setPosicaoNoCaminho(getPosicaoNoCaminho() + pathOffset);
        pathOffset = pathOffset > 0 ? -pathOffset : -pathOffset + 2;
    }
}
```

---

### 2.3 **Strategy Pattern** (Estratégias de Ataque)

**Localização**: `torre.estrategia.*`

**Propósito**: Permitir que torres escolham alvos de formas diferentes

**Estratégias Disponíveis**:
- `EstrategiaPrimeiro`: Ataca o bloon mais à frente no caminho
- `EstrategiaUltimo`: Ataca o bloon mais atrás
- `EstrategiaPerto`: Ataca o bloon mais próximo da torre
- `EstrategiaLonge`: Ataca o bloon mais distante
- `EstrategiaForte`: Ataca o bloon com mais resistência
- `EstrategiaJuntos`: Ataca grupos de bloons

**Interface**:
```java
public interface EstrategiaAtaque {
    Bloon escolherAlvo(Torre t, List<Bloon> bloons);
    String getNome();
}
```

**Vantagens**:
- Comportamento configurável em runtime
- Adicionar novas estratégias sem modificar torres
- Separação de responsabilidades

---

### 2.4 **Visitor Pattern** (Manipuladores de Torres)

**Localização**: `TorreVisitor`, `ManipuladorCreationVisitor`

**Propósito**: Criar manipuladores específicos para cada tipo de torre sem usar `instanceof`

**Implementação**:
```java
public interface TorreVisitor {
    void visit(TorreMacaco t);
    void visit(TorreOctogonal t);
    void visit(TorreMorteiro t);
    // ... outros tipos
}
```

**Uso**:
```java
ManipuladorCreationVisitor v = new ManipuladorCreationVisitor();
torre.accept(v);
ManipuladorTorre manipulador = v.getManipulador();
```

**Vantagens**:
- Evita type-casting e `instanceof`
- Adicionar operações sem modificar classes de torres
- Type-safe em compile-time

---

### 2.5 **Observer Pattern** (Eventos de Bloons)

**Localização**: `BloonObserver`, implementado por `EstouroJogo`

**Propósito**: Notificar quando bloons estouram ou escapam

**Interface**:
```java
public interface BloonObserver {
    void bloonEstourou(Bloon b);
    void bloonEscapou(Bloon b);
}
```

**Uso no Jogo**:
- Quando bloon estoura → adicionar dinheiro
- Quando bloon escapa → remover vidas

**Implementação**:
```java
public void bloonEstourou(Bloon b) {
    setDinheiro(dinheiro + b.getValor());
}

public void bloonEscapou(Bloon b) {
    setVidas(vidas - b.getValor());
}
```

---

### 2.6 **Factory Pattern** (Criação de Objetos)

**Localização**: `BloonCreator`, `TorreCreator`, `ManipuladorCreator`

**Propósito**: Centralizar e encapsular a criação de objetos complexos

**Exemplo - BloonFabricante**:
- Cria bloons aleatoriamente durante o jogo
- Mantém lista de bloons prováveis
- Usa `clone()` para criar instâncias independentes

---

### 2.7 **State Pattern** (Estados do Jogo)

**Localização**: Classes internas em `EstouroJogo`

**Estados**:
1. `EstadoSelecionarTorre`: Modo normal, selecionar torres existentes
2. `EstadoInserirTorre`: Colocar nova torre no mapa
3. `EstadoManipularTorre`: Configurar torre selecionada

**Implementação**:
```java
private class EstadoJogo {
    public void desenhar(Graphics2D g) {}
    public void mousePressed(Point p) {}
    public void mouseReleased(Point p) {}
    public void mouseMoved(Point p) {}
}
```

**Transições**:
- Clicar em botão de torre → `EstadoInserirTorre`
- Clicar em torre existente → `EstadoManipularTorre`
- Soltar rato → volta a `EstadoSelecionarTorre`

---

## 3. Arquitetura do Sistema

### 3.1 Hierarquia de Bloons

```
Bloon (interface)
  ├── BloonSimples (base)
  │    ├── BloonMultiCamada (contém outros bloons)
  │    └── BloonFabricante (cria bloons dinamicamente)
  └── BloonDecorator (decoradores)
       ├── BloonArmadura
       ├── BloonEscudo
       └── BloonImune
```

### 3.2 Hierarquia de Torres

```
Torre (interface)
  └── TorreDefault (implementação base)
       ├── TorreMacaco
       ├── TorreCanhao
       ├── TorreMorteiro
       ├── TorreBalista
       ├── TorreNinja
       ├── TorreSniper
       └── TorreOctogonal
```

### 3.3 Sistema de Projéteis

```
Projetil (interface)
  └── ProjetilDefault (base)
       ├── Dardo (perfurante)
       ├── BombaImpacto (explosão ao impacto)
       ├── BombaDirigida (segue alvo)
       └── TiroSniper (instantâneo)
```

---

## 4. Fluxo de Execução

### 4.1 Inicialização do Jogo

1. `EstouroJogo` cria interface gráfica
2. Carrega informações de torres (`TowerInfo.txt`)
3. Carrega configurações de pistas (`tracks.txt`)
4. Jogador escolhe pista
5. Carrega primeiro nível

### 4.2 Loop Principal (Thread Actualizador)

```java
while (!roundOver) {
    mundo.atualizar();                    // Atualiza bloons, torres, projéteis
    List<Bloon> criados = gestorNivel.criarBloons();  // Cria novos bloons
    // Adiciona bloons ao mundo
    // Verifica condições de vitória/derrota
    zonaJogo.repaint();                   // Redesenha
    sleep(50);                            // ~20 FPS
}
```

### 4.3 Atualização do Mundo

1. **Mover Bloons**: Cada bloon avança no caminho
2. **Torres Atacam**: Cada torre escolhe alvo e dispara
3. **Mover Projéteis**: Projéteis avançam e verificam colisões
4. **Processar Colisões**: Bloons sofrem dano, podem estourar
5. **Limpar Mortos**: Remove bloons/projéteis destruídos

---

## 5. Gestão de Dados

### 5.1 Formato de Níveis (`nivel_X_Y.txt`)

Contém:
- Imagem de fundo da pista
- Definição do caminho (pontos)
- Ciclos de criação de bloons (tipo, quantidade, intervalo)

### 5.2 Persistência (Save/Load)

**Localização**: `io.GameWriter`, `io.GameReader`

**Dados Salvos**:
- Pista atual
- Round atual
- Dinheiro e vidas
- Torres colocadas (posição, tipo, configuração)

**Uso do Visitor**:
- `GameWriterVisitor` visita cada torre para serializar

---

## 6. Pontos Fortes do Design

### 6.1 Extensibilidade
- Adicionar novo tipo de bloon: criar subclasse de `BloonSimples`
- Adicionar nova torre: estender `TorreDefault`
- Adicionar nova estratégia: implementar `EstrategiaAtaque`
- Adicionar novo decorador: estender `BloonDecorator`

### 6.2 Manutenibilidade
- Separação clara de responsabilidades
- Padrões de design bem aplicados
- Código modular e organizado

### 6.3 Reutilização
- `clone()` permite criar cópias independentes
- Componentes visuais reutilizáveis (`ComponenteVisual`)
- Estratégias e decoradores combinam-se livremente

---

## 7. Possíveis Melhorias (para discussão)

### 7.1 Warnings do Código
- Campos que podem ser `final` (ex: `bloons` em `BloonMultiCamada`)
- Valores de retorno não usados (ex: `sofreEstrago()` em alguns lugares)
- Imports não utilizados

### 7.2 Design
- **Separação UI/Lógica**: `EstouroJogo` mistura lógica de jogo com UI Swing
  - Solução: Separar em `GameEngine` (lógica) e `GameUI` (interface)
  
- **Acoplamento**: Algumas classes conhecem detalhes de implementação
  - Exemplo: Bloons conhecem `Mundo` diretamente
  
- **Testes**: Falta de testes unitários
  - Dificulta validação de comportamentos complexos

### 7.3 Performance
- Thread única para atualização
- Possível otimização: spatial partitioning para deteção de colisões

---

## 8. Perguntas Prováveis do Professor

### Q1: "Por que usaram o padrão Decorator para os bloons?"
**R**: Para adicionar funcionalidades (armadura, escudo, imunidades) dinamicamente sem criar uma explosão de subclasses. Permite combinar proteções de forma flexível: um bloon pode ter armadura E escudo ao mesmo tempo.

### Q2: "Como funciona o sistema de estratégias de ataque?"
**R**: Usamos Strategy Pattern. Cada torre tem uma `EstrategiaAtaque` que decide qual bloon atacar. Isso permite mudar o comportamento em runtime e adicionar novas estratégias sem modificar as torres.

### Q3: "Explique o padrão Visitor no contexto dos manipuladores"
**R**: Quando o jogador clica numa torre, precisamos criar um manipulador específico para aquele tipo. Em vez de usar `instanceof`, usamos Visitor: a torre "aceita" um visitor que cria o manipulador correto. É type-safe e extensível.

### Q4: "Como garantem que os bloons multi-camada funcionam corretamente?"
**R**: `BloonMultiCamada` sobrescreve `pop()` e `explode()`. Quando estoura, chama `libertarBloons()` que posiciona os bloons internos no caminho, copia os observers, e adiciona ao mundo como bloons pendentes.

### Q5: "O que acontece quando um bloon escapa?"
**R**: O bloon notifica seus observers (padrão Observer). `EstouroJogo` implementa `BloonObserver` e no método `bloonEscapou()` reduz as vidas do jogador pelo valor do bloon.

### Q6: "Como é gerida a concorrência no jogo?"
**R**: Temos uma thread `Actualizador` que roda o loop principal. O método `drawGameArea()` é `synchronized` para evitar race conditions entre atualização e renderização.

### Q7: "Explique o sistema de save/load"
**R**: Usamos `GameWriter` com um `GameWriterVisitor` que visita cada torre para serializar seus dados. `GameReader` lê o ficheiro e reconstrói o estado do jogo (pista, round, dinheiro, vidas, torres).

### Q8: "Quais as vantagens do método `clone()` nos bloons?"
**R**: Permite criar cópias independentes de bloons template. Essencial para `BloonFabricante` e `BloonMultiCamada` que precisam criar novos bloons sem partilhar estado. Cada clone tem sua própria imagem, posição e observers.

---

## 9. Demonstração Prática

### Cenário 1: Adicionar Novo Tipo de Bloon
```java
public class BloonRegenerativo extends BloonSimples {
    private int taxaRegeneracao;
    
    @Override
    public void mover() {
        super.mover();
        // Regenerar resistência
        if (getResistencia() < resistenciaMaxima) {
            resistencia += taxaRegeneracao;
        }
    }
}
```

### Cenário 2: Nova Estratégia de Ataque
```java
public class EstrategiaAleatoria implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloons) {
        if (bloons.isEmpty()) return null;
        int idx = ThreadLocalRandom.current().nextInt(bloons.size());
        return bloons.get(idx);
    }
    
    @Override
    public String getNome() {
        return "Aleatório";
    }
}
```

### Cenário 3: Novo Decorador
```java
public class BloonInvisivel extends BloonDecorator {
    private boolean invisivel = true;
    
    @Override
    public void desenhar(Graphics2D g) {
        if (!invisivel) {
            super.desenhar(g);
        }
    }
    
    @Override
    public int pop(int estrago) {
        invisivel = false; // Torna-se visível ao ser atingido
        return super.pop(estrago);
    }
}
```

---

## 10. Conclusão

O projeto ESTouro demonstra:
- ✅ Aplicação correta de múltiplos padrões de design
- ✅ Arquitetura extensível e manutenível
- ✅ Separação de responsabilidades
- ✅ Uso adequado de herança e polimorfismo
- ✅ Sistema de eventos (Observer)
- ✅ Persistência de dados

**Pontos de Destaque**:
- Decorator permite funcionalidades dinâmicas
- Strategy torna comportamento configurável
- Visitor evita type-checking manual
- Observer desacopla lógica de jogo da UI
- Clone pattern permite criação eficiente de objetos

---

## Dicas para a Defesa

1. **Conhece os padrões**: Sabe explicar cada um e onde está aplicado
2. **Mostra código**: Aponta para exemplos concretos no código
3. **Justifica decisões**: Explica POR QUE escolheram cada padrão
4. **Admite limitações**: Reconhece pontos de melhoria (mostra maturidade)
5. **Demonstra extensibilidade**: Mostra como adicionar funcionalidades
6. **Prepara exemplos**: Tem cenários práticos prontos
7. **Testa ao vivo**: Consegue executar e mostrar o jogo funcionando

Boa sorte na defesa! 🎯
