# Documento de Design

## Visão Geral

Este documento de design delineia a abordagem de refatoração para o jogo Tower Defense ESTouro para eliminar anti-padrões de design e implementar padrões de design orientados a objetos adequados. A refatoração abordará quatro áreas principais de problemas: declarações switch excessivas nas torres, duplicação de código, uso de instanceof e problemas de clonagem do BloonFabricante. Além disso, novos recursos serão adicionados incluindo uma torre Sniper, novas estratégias de ataque e decoradores de bloons com proteção temporária.

O design mantém compatibilidade com versões anteriores enquanto melhora a extensibilidade, manutenibilidade e aderência aos princípios SOLID. A solução aproveita múltiplos padrões de design incluindo Strategy, Template Method, Factory, Decorator e padrões Prototype.

## Arquitetura

### Problemas da Arquitetura Atual

1. **Problema de Declaração Switch**: Classes de torres contêm declarações switch para seleção de modo de ataque, violando o Princípio Aberto/Fechado
2. **Duplicação de Código**: Lógica de ataque similar é repetida em múltiplas implementações de torres
3. **Uso de Instanceof**: Verificação de tipo usando instanceof indica design polimórfico deficiente
4. **Problemas do BloonFabricante**: Clonagem inadequada leva a estado compartilhado e comportamento errático de bloons

### Arquitetura Proposta

A arquitetura refatorada usará uma abordagem em camadas com clara separação de responsabilidades:

```
Camada do Jogo
├── Sistema Torre (padrões Strategy + Template Method)
├── Sistema Bloon (padrões Decorator + Prototype)  
├── Sistema Estratégia de Ataque (padrão Strategy)
├── Sistema Projétil (padrão Factory)
└── Sistema de Criação (padrão Abstract Factory)
```

## Components and Interfaces

### 1. Torre System Refactoring

#### Strategy Pattern Implementation
Replace switch statements with Strategy pattern for attack mode selection:

```java
// Enhanced EstrategiaAtaque interface
public interface EstrategiaAtaque {
    Bloon escolherAlvo(Torre torre, List<Bloon> bloons);
    String getNome();
}

// New strategy implementations
public class EstrategiaLonge implements EstrategiaAtaque
public class EstrategiaForte implements EstrategiaAtaque
```

#### Template Method Pattern
Extract common attack logic into abstract base class using Template Method pattern:

```java
public abstract class TorreDefault implements Torre {
    // Template method defining attack algorithm
    public final Projetil[] atacar(List<Bloon> bloons) {
        atualizarCicloDisparo();
        List<Bloon> alvosDisponiveis = filtrarAlvosNoAlcance(bloons);
        Bloon alvo = estrategia.escolherAlvo(this, alvosDisponiveis);
        
        if (alvo != null) {
            orientarTorre(alvo);
        }
        
        if (podeDisparar() && alvo != null) {
            resetTempoDisparar();
            return criarProjeteis(alvo); // Abstract method
        }
        
        return new Projetil[0];
    }
    
    // Abstract methods for subclass customization
    protected abstract Projetil[] criarProjeteis(Bloon alvo);
    protected abstract void orientarTorre(Bloon alvo);
    protected abstract List<Bloon> filtrarAlvosNoAlcance(List<Bloon> bloons);
}
```

### 2. Bloon System Enhancement

#### Decorator Pattern Extension
Enhance existing decorator pattern for temporary protections:

```java
public class BloonArmaduraTemporaria extends BloonDecorator {
    private int impactosRestantes;
    
    @Override
    public int pop(int estrago) {
        if (impactosRestantes > 0) {
            impactosRestantes--;
            return 0; // Damage blocked
        }
        return super.pop(estrago);
    }
}

public class BloonEscudoTemporario extends BloonDecorator {
    private int explosoesRestantes;
    
    @Override
    public void explode(int estrago) {
        if (explosoesRestantes > 0) {
            explosoesRestantes--;
            return; // Explosion blocked
        }
        super.explode(estrago);
    }
}
```

#### Prototype Pattern Fix
Fix BloonFabricante cloning issues with proper deep cloning:

```java
public class BloonFabricante extends BloonSimples {
    private List<Bloon> prototipos; // Store prototypes instead of instances
    
    @Override
    public void mover() {
        super.mover();
        if (shouldCreateBloon()) {
            Bloon novoBloon = selecionarPrototipo().clone(); // Deep clone
            configurarNovoBloon(novoBloon);
            getMundo().addBloonPendente(novoBloon);
        }
    }
    
    @Override
    public Bloon clone() {
        BloonFabricante clone = new BloonFabricante(/*parameters*/);
        // Deep clone prototypes list
        for (Bloon prototipo : prototipos) {
            clone.addBloonProvavel(prototipo.clone());
        }
        return clone;
    }
}
```

### 3. New Torre Sniper Implementation

```java
public class TorreSniper extends TorreDefault {
    private double direcaoFixa;
    
    @Override
    protected Projetil[] criarProjeteis(Bloon alvo) {
        // Create instant-hit projectile with damage 5
        // After first hit, behaves as normal dart
        return new Projetil[] { new ProjetilSniper(alvo, 5) };
    }
    
    @Override
    protected List<Bloon> filtrarAlvosNoAlcance(List<Bloon> bloons) {
        // Infinite range - filter by line of sight in fixed direction
        return getBloonsInLine(bloons, getPosicao(), getDirecaoAlvo());
    }
    
    @Override
    protected void orientarTorre(Bloon alvo) {
        // Torre doesn't rotate - uses fixed direction set by player
    }
}
```

### 4. Attack Strategy Extensions

#### Far Strategy (Longe)
```java
public class EstrategiaLonge implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre torre, List<Bloon> bloons) {
        Point posicaoTorre = torre.getComponente().getPosicaoCentro();
        return bloons.stream()
            .max(Comparator.comparingDouble(b -> 
                calcularDistancia(posicaoTorre, b.getComponente().getPosicaoCentro())))
            .orElse(null);
    }
}
```

#### Strong Strategy (Forte)
```java
public class EstrategiaForte implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(Torre torre, List<Bloon> bloons) {
        return bloons.stream()
            .max(Comparator.comparingInt(Bloon::getValor))
            .orElse(null);
    }
}
```

## Data Models

### Torre Hierarchy
```
Torre (interface)
└── TorreDefault (abstract class - Template Method)
    ├── TorreMacaco
    ├── TorreOctogonal  
    ├── TorreCanhao
    ├── TorreMorteiro
    ├── TorreBalista
    ├── TorreNinja
    └── TorreSniper (new)
```

### Bloon Hierarchy with Decorators
```
Bloon (interface)
├── BloonSimples
├── BloonMultiCamada
├── BloonFabricante (fixed)
└── BloonDecorator (abstract)
    ├── BloonImune (existing)
    ├── BloonArmadura (existing)
    ├── BloonEscudo (existing)
    ├── BloonArmaduraTemporaria (new)
    └── BloonEscudoTemporario (new)
```

### Strategy Hierarchy
```
EstrategiaAtaque (interface)
├── EstrategiaPrimeiro (existing)
├── EstrategiaUltimo (existing)
├── EstrategiaPerto (existing)
├── EstrategiaJuntos (existing)
├── EstrategiaLonge (new)
└── EstrategiaForte (new)
```

## Error Handling

### Exception Hierarchy
```java
public class EstouroGameException extends Exception {
    public EstouroGameException(String message) { super(message); }
}

public class TorreCreationException extends EstouroGameException {
    public TorreCreationException(String towerType) {
        super("Failed to create tower of type: " + towerType);
    }
}

public class BloonCreationException extends EstouroGameException {
    public BloonCreationException(String bloonType) {
        super("Failed to create bloon of type: " + bloonType);
    }
}

public class InvalidStrategyException extends EstouroGameException {
    public InvalidStrategyException(String strategyName) {
        super("Invalid attack strategy: " + strategyName);
    }
}
```

### Error Handling Strategy
1. **Factory Methods**: Throw specific exceptions for invalid types
2. **Cloning Operations**: Handle CloneNotSupportedException gracefully
3. **Strategy Selection**: Validate strategy names and provide fallbacks
4. **Resource Loading**: Handle missing image files with default alternatives

## Testing Strategy

### Unit Testing Approach
1. **Torre Testing**: Test each tower's projectile creation and targeting behavior
2. **Strategy Testing**: Verify each attack strategy selects correct targets
3. **Bloon Testing**: Test decorator behavior and cloning functionality
4. **Factory Testing**: Verify correct object creation for all types

### Integration Testing
1. **Torre-Strategy Integration**: Test strategy switching and target selection
2. **Bloon-Decorator Integration**: Test multiple decorator combinations
3. **Factory Integration**: Test complete object creation workflows

### Test Structure
```java
// Example test structure
public class TorreDefaultTest {
    @Test
    public void testAttackTemplateMethod() {
        // Test template method execution flow
    }
    
    @Test
    public void testStrategyIntegration() {
        // Test strategy pattern integration
    }
}

public class BloonFabricanteTest {
    @Test
    public void testProperCloning() {
        // Test that cloned bloons are independent
    }
    
    @Test
    public void testBloonCreation() {
        // Test that created bloons behave correctly
    }
}
```

### Performance Testing
1. **Memory Usage**: Verify proper object cleanup and no memory leaks
2. **Cloning Performance**: Ensure cloning operations are efficient
3. **Strategy Performance**: Test strategy selection performance with large bloon lists

## Implementation Phases

### Phase 1: Strategy Pattern Implementation
- Replace switch statements in torre classes
- Implement new attack strategies (Longe, Forte)
- Update ConfiguradorTorres to support new strategies

### Phase 2: Template Method Refactoring  
- Extract common attack logic to TorreDefault
- Refactor existing torre classes to use template method
- Eliminate code duplication

### Phase 3: Instanceof Elimination
- Replace instanceof checks with polymorphic method calls
- Implement visitor pattern where needed
- Use method overloading for type-specific behavior

### Phase 4: BloonFabricante Fix
- Implement proper prototype pattern
- Fix cloning issues with deep copying
- Test bloon creation and behavior

### Phase 5: New Features Implementation
- Implement TorreSniper with infinite range
- Add temporary protection decorators
- Update factory classes for new types

### Phase 6: Integration and Testing
- Comprehensive testing of all components
- Performance optimization
- Documentation updates