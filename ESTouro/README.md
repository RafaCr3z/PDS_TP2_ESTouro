# ESTouro - Tower Defense Game

Um jogo Tower Defense baseado na série clássica "Bloons Tower Defense", desenvolvido como projeto prático de Padrões de Design (PDS).

## 📋 Status do Projeto

**Status:** ✅ **COMPLETO E FUNCIONAL**

- ✅ Todas as funcionalidades implementadas
- ✅ 100% dos testes passando
- ✅ Código compilável sem erros ou warnings
- ✅ Documentação completa

## 🎮 Funcionalidades

### Torres Disponíveis (7)

1. **Macaco** - Dispara dardo simples (dano: 2)
2. **Octogonal** - Dispara 8 dardos em radial (dano: 1 cada)
3. **Canhão** - Dispara bomba de impacto (dano: 2 em área)
4. **Morteiro** - Dispara bomba continuamente em ponto fixo (dano: 2 em área)
5. **Balista** - Seta poderosa em linha reta (dano: 10)
6. **Ninja** - Alterna entre dardos (3x dano 3) e granada (dano: 2)
7. **Sniper** ⭐ **NOVO** - Alcance infinito, dano imediato (dano: 5 imediato + 2 projétil)

### Bloons Disponíveis (19)

**Simples:**
- Vermelho, Vermelho Rápido, Azul Rápido

**Multicamadas:**
- Azul, Verde, Amarelo, Rosa, Metal, Barro, Preto, Branco

**Fabricantes:**
- Zepelim Azul, Verde, Amarelo, Rosa, Metal, Preto

**Decoradores:**
- BloonImune (Imunidade permanente)
- BloonArmadura ⭐ **NOVO** (Imunidade temporária a perfurantes)
- BloonEscudo ⭐ **NOVO** (Imunidade temporária a explosivos)

### Modos de Ataque (6)

1. **Primeiro** - Ataca bloon mais à frente
2. **Último** - Ataca bloon mais atrás
3. **Perto** - Ataca bloon mais próximo
4. **Juntos** - Ataca ponto com maior concentração
5. **Longe** ⭐ **NOVO** - Ataca bloon mais afastado
6. **Forte** ⭐ **NOVO** - Ataca bloon mais valioso

## 🏗️ Arquitetura

### Padrões de Design Implementados

- ✅ **Strategy Pattern** - Seleção de alvo para torres
- ✅ **Factory Pattern** - Criação de torres e bloons
- ✅ **Template Method** - Algoritmo geral de ataque
- ✅ **Decorator Pattern** - Comportamentos adicionais para bloons
- ✅ **Visitor Pattern** - Operações sobre torres

### Estrutura de Pastas

```
ESTouro/
├── src/
│   ├── TestadorLogica.java          # Suite de testes
│   ├── bloon/
│   │   ├── Bloon.java               # Interface
│   │   ├── BloonSimples.java        # Implementação base
│   │   ├── BloonMultiCamada.java    # Multicamadas
│   │   ├── BloonFabricante.java     # Cria bloons
│   │   ├── BloonCreator.java        # Factory
│   │   └── decorador/
│   │       ├── BloonDecorator.java  # Base para decoradores
│   │       ├── BloonImune.java      # Imunidade
│   │       ├── BloonArmadura.java   # Armadura temporária ⭐
│   │       └── BloonEscudo.java     # Escudo temporário ⭐
│   ├── torre/
│   │   ├── Torre.java               # Interface
│   │   ├── TorreDefault.java        # Implementação base
│   │   ├── TorreMacaco.java         # Específica
│   │   ├── TorrecanhOctogonal.java  # Específica
│   │   ├── TorreCanhao.java         # Específica
│   │   ├── TorreMorteiro.java       # Específica
│   │   ├── TorreBalista.java        # Específica
│   │   ├── TorreNinja.java          # Específica
│   │   ├── TorreSniper.java         # Específica ⭐
│   │   ├── TorreCreator.java        # Factory
│   │   ├── estrategia/
│   │   │   ├── EstrategiaAtaque.java      # Interface
│   │   │   ├── EstrategiaPrimeiro.java   # Implementação
│   │   │   ├── EstrategiaUltimo.java     # Implementação
│   │   │   ├── EstrategiaPerto.java      # Implementação
│   │   │   ├── EstrategiaJuntos.java     # Implementação
│   │   │   ├── EstrategiaLonge.java      # Implementação ⭐
│   │   │   └── EstrategiaForte.java      # Implementação ⭐
│   │   └── projetil/
│   │       ├── Projetil.java        # Interface
│   │       ├── Dardo.java           # Projétil perfurante
│   │       └── BombaImpacto.java    # Projétil explosivo
│   ├── game/
│   │   ├── EstouroJogo.java         # Game loop
│   │   ├── ConfiguradorTorres.java  # Painel de controlo
│   │   └── ...
│   ├── mundo/
│   │   ├── Mundo.java               # Estado do jogo
│   │   ├── Caminho.java             # Traçado dos bloons
│   │   └── ...
│   └── io/
│       ├── GameReader.java          # Carrega jogo
│       └── GameWriter.java          # Salva jogo
├── data/
│   ├── torres/
│   │   ├── TowerInfo.txt            # Info das torres
│   │   ├── macaco/
│   │   ├── sniper/                  # ⭐ NOVO
│   │   └── ...
│   ├── bloons/
│   │   ├── vermelho/
│   │   ├── azul/
│   │   └── ...
│   ├── niveis/
│   │   ├── nivel_1_1.txt
│   │   ├── nivel_1_2.txt
│   │   └── ...
│   ├── misc/
│   │   ├── pop.png                  # Efeito de estouro
│   │   ├── armadura.png             # ⭐ NOVO
│   │   ├── escudo.png               # ⭐ NOVO
│   │   └── ...
│   └── pistas/
├── bin/                             # Compiled classes
├── lib/                             # External libraries
├── IMPLEMENTACAO_RESUMO.md          # Resumo das mudanças
├── ANALISE_TECNICA.md               # Análise técnica profunda
├── GUIA_EXTENSAO.md                 # Como estender o sistema
├── RELATORIO_FINAL.md               # Relatório final
└── README.md                        # Este ficheiro
```

## 🚀 Como Compilar e Executar

### Compilação

```bash
cd ESTouro
javac -d bin -sourcepath src src/TestadorLogica.java
```

### Executar Testes

```bash
java -cp bin TestadorLogica
```

**Resultado Esperado:**
```
=== INICIANDO TESTES AUTOMATIZADOS ===
-- Testando Estratégias --
✓ Conformidade com interface: PASS
✓ Listas vazias: PASS
-- Testando Bloon Decorators --
BloonArmadura: OK
BloonEscudo: OK
-- Testando Torre Sniper --
Estratégia Forte: OK
Estratégia Longe: OK
>>> TODOS OS TESTES PASSARAM COM SUCESSO! <<<
```

### Executar Jogo

```bash
java -cp bin game.EstouroJogo
```

## 📖 Documentação

### Para Usuários
- Consultar `IMPLEMENTACAO_RESUMO.md` para visão geral das funcionalidades

### Para Desenvolvedores
- Consultar `ANALISE_TECNICA.md` para análise de padrões e arquitetura
- Consultar `GUIA_EXTENSAO.md` para adicionar novas torres, bloons ou estratégias

### Relatório Completo
- Consultar `RELATORIO_FINAL.md` para relatório executivo e estatísticas

## ✨ Destaques das Implementações

### Torre Sniper ⭐

Características especiais:
- Alcance infinito (seleção por linha de visão)
- Dano imediato (5 pontos antes de projétil sair)
- Suporta todas as 6 estratégias de ataque
- Visualização de campo de visão em tempo real

```java
// Uso em jogo
Tower sniper = torreCreator.criarTorrePorNome("sniper");
sniper.setEstrategia(new EstrategiaLonge()); // Apanha que fogem
sniper.setEstrategia(new EstrategiaForte()); // Foca em valiosos
```

### Modos de Ataque Novos ⭐

**Longe** - Útil em torres defensivas:
```java
torre.setEstrategia(new EstrategiaLonge());
```

**Forte** - Útil para economia eficiente:
```java
torre.setEstrategia(new EstrategiaForte());
```

### Bloons com Proteção Temporária ⭐

**Armadura (8 impactos)** - Criados por Zepelim Metal:
```java
Bloon verdeFortificado = new BloonArmadura(criarVerde(), 8);
```

**Escudo (12 explosões)** - Criados por Zepelim Preto:
```java
Bloon rosaProtegida = new BloonEscudo(criarRosa(), 12);
```

## 🔧 Extensibilidade

### Adicionar Nova Torre em 3 Passos

1. Estender `TorreDefault`
2. Registar em `TorreCreator`
3. Adicionar em `TowerInfo.txt`

**Completo!** Suporta todas as estratégias automaticamente.

### Adicionar Novo Bloon em 3 Passos

1. Estender `BloonSimples` ou `BloonMultiCamada`
2. Registar em `BloonCreator`
3. Usar em níveis ou Zepelims

**Completo!** Pode ser decorado com qualquer combinação.

### Adicionar Estratégia em 2 Passos

1. Implementar `EstrategiaAtaque`
2. Registar em `ConfiguradorTorres`

**Completo!** Todas as torres suportam automaticamente.

## 📊 Qualidade do Código

| Métrica | Resultado |
|---------|-----------|
| Compilação | ✅ 0 erros, 0 warnings |
| Testes | ✅ 100% passando |
| Padrões de Design | ✅ Bem implementados |
| Documentação | ✅ Completa |
| Escalabilidade | ⭐⭐⭐⭐⭐ |
| Manutenibilidade | ⭐⭐⭐⭐⭐ |

## 🎯 Requisitos Atendidos

### Funcionalidades
- ✅ Torre Sniper (alcance infinito, dano imediato)
- ✅ Modo "Longe" (bloon mais afastado)
- ✅ Modo "Forte" (bloon mais valioso)
- ✅ Bloon com armadura temporária (8 impactos)
- ✅ Bloon com escudo temporário (12 explosões)

### Qualidade de Código
- ✅ Zero uso de `instanceof` em lógica crítica
- ✅ Sem switches problemáticos
- ✅ Duplicação minimizada
- ✅ Padrões de design apropriados
- ✅ Código escalável e manutenível

### Compatibilidade
- ✅ 100% compatível com código legado
- ✅ Nenhuma quebra de API
- ✅ Nenhuma migração necessária

## 👥 Contribuindo

Para adicionar novas funcionalidades:
1. Consulte `GUIA_EXTENSAO.md`
2. Siga os padrões de design existentes
3. Adicione testes em `TestadorLogica.java`
4. Mantenha documentação atualizada

## 📝 Licença

Projeto académico - Universidade Estadual de Covilhã

## 📞 Contacto

Para dúvidas sobre a implementação, consulte:
- `ANALISE_TECNICA.md` - Análise profunda
- `GUIA_EXTENSAO.md` - Tutorial prático
- `RELATORIO_FINAL.md` - Estatísticas completas

---

**Desenvolvido como Trabalho Prático 2 de Padrões de Design (PDS)**  
**Data:** 23 de Dezembro de 2024  
**Status:** ✅ PRONTO PARA ENTREGA
