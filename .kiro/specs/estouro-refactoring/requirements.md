# Documento de Requisitos

## Introdução

Esta especificação define os requisitos de refatoração para o jogo Tower Defense ESTouro para melhorar a manutenibilidade do código, escalabilidade e implementação de padrões de design. O código atual sofre de uso excessivo de declarações switch, duplicação de código, uso de instanceof e problemas com a implementação do BloonFabricante. A refatoração aplicará padrões de design apropriados para resolver esses problemas enquanto adiciona novas torres, modos de ataque e categorias de bloons.

## Glossário

- **Sistema_ESTouro**: O sistema completo do jogo Tower Defense incluindo torres, bloons, estratégias de ataque e mecânicas do jogo
- **Componente_Torre**: Entidades individuais de torres que atacam bloons usando projéteis
- **Entidade_Bloon**: Entidades inimigas que se movem ao longo de caminhos e devem ser destruídas pelas torres
- **Estrategia_Ataque**: Padrão comportamental que determina como as torres selecionam alvos
- **Sistema_Projetil**: Objetos disparados pelas torres para causar dano aos bloons
- **Componente_BloonFabricante**: Tipo especial de bloon que cria outros bloons durante o movimento
- **Padrao_Design**: Solução reutilizável para problemas comuns de design de software
- **Declaracao_Switch**: Estrutura de lógica condicional que precisa ser substituída por polimorfismo
- **Uso_Instanceof**: Mecanismo de verificação de tipo que indica design orientado a objetos deficiente

## Requisitos

### Requisito 1

**História do Usuário:** Como desenvolvedor de software, quero eliminar todas as declarações switch nas classes de torres, para que o código se torne mais manutenível e extensível.

#### Critérios de Aceitação

1. QUANDO analisar implementações de Componente_Torre, O Sistema_ESTouro DEVE conter zero declarações switch relacionadas a modos de ataque
2. QUANDO adicionar novas estratégias de ataque, O Sistema_ESTouro DEVE suportar extensão sem modificar código existente de Componente_Torre
3. QUANDO Componente_Torre seleciona alvos, O Sistema_ESTouro DEVE usar comportamento polimórfico em vez de lógica condicional
4. ONDE Componente_Torre usa estratégias de ataque, O Sistema_ESTouro DEVE implementar o padrão Strategy corretamente
5. ENQUANTO mantém funcionalidade existente, O Sistema_ESTouro DEVE preservar todos os comportamentos de ataque atuais

### Requisito 2

**História do Usuário:** Como desenvolvedor de software, quero reduzir a duplicação de código nas classes de torres, para que a manutenção se torne mais fácil e os bugs sejam reduzidos.

#### Critérios de Aceitação

1. QUANDO implementar métodos de ataque de Componente_Torre, O Sistema_ESTouro DEVE compartilhar lógica comum através de herança ou composição
2. QUANDO Componente_Torre cria projéteis, O Sistema_ESTouro DEVE usar métodos template ou padrões factory para eliminar duplicação
3. ENQUANTO preserva características individuais das torres, O Sistema_ESTouro DEVE extrair comportamentos comuns para classes base
4. ONDE implementações de Componente_Torre diferem, O Sistema_ESTouro DEVE usar polimorfismo em vez de código duplicado
5. QUANDO medir duplicação de código, O Sistema_ESTouro DEVE alcançar pelo menos 70% de redução na lógica de ataque duplicada

### Requisito 3

**História do Usuário:** Como desenvolvedor de software, quero eliminar todo uso de instanceof, para que o código siga princípios adequados de orientação a objetos.

#### Critérios de Aceitação

1. QUANDO verificar tipos de objetos, O Sistema_ESTouro DEVE usar chamadas de métodos polimórficos em vez de instanceof
2. QUANDO tipos de Entidade_Bloon precisam de tratamento diferente, O Sistema_ESTouro DEVE implementar o padrão Visitor ou double dispatch
3. ONDE comportamento específico de tipo é necessário, O Sistema_ESTouro DEVE usar métodos virtuais e herança
4. ENQUANTO mantém segurança de tipos, O Sistema_ESTouro DEVE evitar verificação de tipos em tempo de execução
5. QUANDO analisar o código, O Sistema_ESTouro DEVE conter zero declarações instanceof

### Requisito 4

**História do Usuário:** Como desenvolvedor de software, quero corrigir a implementação do BloonFabricante, para que os bloons criados se comportem corretamente sem anomalias.

#### Critérios de Aceitação

1. QUANDO Componente_BloonFabricante cria novos bloons, O Sistema_ESTouro DEVE gerar instâncias independentes usando clonagem adequada
2. QUANDO bloons criados se movem, O Sistema_ESTouro DEVE garantir que mantenham velocidade e direção corretas
3. ENQUANTO Componente_BloonFabricante opera, O Sistema_ESTouro DEVE prevenir que bloons se movam para trás ou exibam comportamento errático
4. ONDE criação de bloons ocorre, O Sistema_ESTouro DEVE implementar o padrão Prototype corretamente
5. QUANDO múltiplas instâncias de Componente_BloonFabricante existem, O Sistema_ESTouro DEVE garantir que cada uma crie instâncias independentes de bloons

### Requisito 5

**História do Usuário:** Como designer de jogos, quero adicionar uma nova torre Sniper, para que os jogadores tenham mais opções estratégicas com capacidades de alcance infinito.

#### Critérios de Aceitação

1. QUANDO Torre_Sniper é criada, O Sistema_ESTouro DEVE fornecer capacidade de mira com alcance infinito
2. QUANDO Torre_Sniper dispara, O Sistema_ESTouro DEVE entregar dano imediato de 5 ao primeiro alvo
3. ENQUANTO projétil da Torre_Sniper continua, O Sistema_ESTouro DEVE se comportar como dardo normal após primeiro impacto
4. ONDE Torre_Sniper mira, O Sistema_ESTouro DEVE permitir configuração de direção controlada pelo jogador
5. QUANDO Torre_Sniper seleciona alvos, O Sistema_ESTouro DEVE suportar todos os modos de estratégia de ataque

### Requisito 6

**História do Usuário:** Como designer de jogos, quero adicionar novos modos de ataque (Longe e Forte), para que os jogadores tenham mais opções táticas de mira.

#### Critérios de Aceitação

1. QUANDO Estrategia_Ataque_Longe é selecionada, O Sistema_ESTouro DEVE mirar no bloon fisicamente mais distante da torre
2. QUANDO Estrategia_Ataque_Forte é selecionada, O Sistema_ESTouro DEVE mirar no bloon com maior valor atual
3. ENQUANTO usa Estrategia_Ataque_Forte, O Sistema_ESTouro DEVE priorizar por valor do bloon em vez de resistência
4. ONDE estratégias de ataque são configuradas, O Sistema_ESTouro DEVE permitir troca de estratégia em tempo de execução
5. QUANDO Componente_Torre suporta modos de ataque, O Sistema_ESTouro DEVE incluir estratégias Longe e Forte nas opções disponíveis

### Requisito 7

**História do Usuário:** Como designer de jogos, quero adicionar categorias de bloons com armadura e escudo temporários, para que o gameplay se torne mais desafiador e estratégico.

#### Critérios de Aceitação

1. QUANDO Bloon_Armadura_Temporaria é atingido por projéteis perfurantes, O Sistema_ESTouro DEVE ignorar dano por número especificado de impactos
2. QUANDO Bloon_Escudo_Temporario é atingido por projéteis explosivos, O Sistema_ESTouro DEVE ignorar dano por número especificado de explosões
3. ENQUANTO armadura ou escudo está ativo, O Sistema_ESTouro DEVE decrementar contadores de proteção a cada impacto relevante
4. ONDE proteção expira, O Sistema_ESTouro DEVE tornar bloons vulneráveis a tipos de dano previamente bloqueados
5. QUANDO bloons Zeppelin criam descendentes, O Sistema_ESTouro DEVE aplicar proteções temporárias conforme especificado nos requisitos

### Requisito 8

**História do Usuário:** Como desenvolvedor de software, quero que o sistema suporte adição fácil de novas torres e bloons, para que extensões futuras requeiram mudanças mínimas no código.

#### Critérios de Aceitação

1. QUANDO adicionar novos tipos de Componente_Torre, O Sistema_ESTouro DEVE requerer mudanças apenas no TorreCreator e arquivos de configuração
2. QUANDO adicionar novos tipos de Entidade_Bloon, O Sistema_ESTouro DEVE requerer mudanças apenas no BloonCreator e arquivos de configuração
3. ENQUANTO mantém extensibilidade, O Sistema_ESTouro DEVE usar padrões Factory para criação de objetos
4. ONDE novas estratégias de ataque são adicionadas, O Sistema_ESTouro DEVE requerer mudanças mínimas no código existente
5. QUANDO arquitetura do sistema é avaliada, O Sistema_ESTouro DEVE demonstrar conformidade com o Princípio Aberto/Fechado

### Requisito 9

**História do Usuário:** Como desenvolvedor de software, quero manter toda funcionalidade existente do jogo, para que a refatoração não quebre recursos atuais.

#### Critérios de Aceitação

1. QUANDO refatoração está completa, O Sistema_ESTouro DEVE preservar todos os comportamentos e características existentes das torres
2. QUANDO bloons se movem e interagem, O Sistema_ESTouro DEVE manter mecânicas e física originais do jogo
3. ENQUANTO aplica padrões de design, O Sistema_ESTouro DEVE manter todos os tipos de projéteis atuais e cálculos de dano
4. ONDE interfaces de usuário existem, O Sistema_ESTouro DEVE manter capacidades de interação do jogador existentes
5. QUANDO testar sistema refatorado, O Sistema_ESTouro DEVE passar todos os testes de verificação de funcionalidade existente