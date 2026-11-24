# Forma.

Aplicativo desktop para organizar e acompanhar treinos de academia, feito em **Java** com **JavaFX**. O visual foi desenhado antes no **Figma** e depois reproduzido em código.

**Autores:** Gian Li Zhang e Alexandre Bagini
**Contexto:** projeto da disciplina de Programação Orientada a Objetos, Bacharelado em Ciência da Computação, IFSP, Câmpus São João da Boa Vista.

> Projeto em desenvolvimento. A seção [Estado atual e próximos passos](#estado-atual-e-próximos-passos) diz o que já está pronto e o que falta.

---

## O que o app faz

- **Meus treinos:** cria e exclui treinos (nome, grupo e duração), adiciona e remove exercícios de cada treino.
- **Execução de treino:** o usuário marca cada exercício como feito e registra carga (kg) e repetições. O treino só pode ser concluído quando todos os exercícios estiverem marcados.
- **Histórico:** cada treino concluído vira uma sessão salva. A lista de treinos mostra "Concluído hoje" quando há uma sessão concluída no dia.
- **Conquistas:** objetivo do usuário, filtros por categoria (Todas, Treinos, Constância, Objetivos) e cartões com barra de progresso.
- **Visão geral:** painel inicial com indicadores da semana e o card do treino do dia, com atalho para começar o treino.
- **Navegação:** barra lateral com destaque do item ativo e barra superior com *breadcrumb* ("Meu espaço › tela atual"), data e avatar.
- **Persistência:** os dados são gravados em JSON e continuam disponíveis ao fechar e abrir o app.

---

## Tecnologias e bibliotecas

| Tecnologia | Para que serve no projeto |
|---|---|
| **Java** | Linguagem do projeto. |
| **JavaFX** (`javafx.controls`) | Toda a interface: `BorderPane`, `VBox`, `HBox`, `GridPane`, `StackPane`, `ScrollPane`, diálogos, além do CSS do JavaFX (`-fx-...`) para o visual. |
| **Gson** (`com.google.gson`) | Converte objetos Java em JSON e de volta, para salvar treinos e sessões. |
| **Maven** | Gerencia dependências e a compilação. |
| `java.time` | Datas das sessões e cálculo da semana atual (`LocalDate`). |
| `java.nio.file` | Leitura e gravação do arquivo de dados. |
| **Módulos Java** (`module-info.java`) | Declara as dependências (`javafx.controls`, `com.google.gson`) e abre o pacote de sessões para o Gson por reflexão. |
| **Figma** | Protótipo do visual (cores, espaçamentos, componentes). |

---

## Estrutura do projeto

```
src/main/java
├── module-info.java
└── br/edu/ifsp
    ├── App.java                  # janela, barra lateral, barra superior e troca de telas
    ├── TelaVisaoGeral.java       # painel inicial
    ├── TelaTreinos.java          # lista de treinos + detalhe + criar/excluir
    ├── TelaExecucaoTreino.java   # execução do treino e tela de conclusão
    ├── TelaConquistas.java       # objetivo, filtros e conquistas
    ├── TelaEvolucao.java         # (a implementar)
    ├── TelaMeuCorpo.java         # (a implementar)
    ├── Cards/                    # componentes reutilizáveis
    │   ├── StatCard.java         # card de indicador (título, valor, unidade, rodapé)
    │   └── TreinoCard.java       # card grande do treino, com imagem de fundo
    ├── TreinoSecao/              # dados e persistência
    │   ├── Treino.java
    │   ├── SessaoTreino.java
    │   ├── ExercicioRealizado.java
    │   └── RepositorioTreinos.java
    └── model/
        └── Conquista.java
src/main/resources
└── img/halteres.jpg              # imagem de fundo do TreinoCard (opcional)
```

---

## Como o código está organizado

### Navegação
O `App` monta uma `BorderPane` com a barra lateral à esquerda e, à direita, a barra superior fixa e uma área central com rolagem (`ScrollPane`). Cada item do menu guarda a tela que representa. O método `selecionar` destaca o item, atualiza o *breadcrumb* e troca o conteúdo da área central. A tela Visão geral recebe um `Runnable` para pedir a navegação para Meus treinos sem conhecer o `App`.

### Telas que reagem a dados
Telas como `TelaTreinos` e `TelaConquistas` seguem o mesmo fluxo: um clique altera um estado (treino selecionado, filtro atual) e a tela é redesenhada a partir dos dados, sem alterar estilos manualmente.

### Persistência
O `RepositorioTreinos` guarda treinos e sessões em um único arquivo:

```
~/.forma/dados.json
```

No primeiro uso, o arquivo é criado com três treinos de exemplo (Costas e bíceps, Pernas e glúteos, Peito e tríceps). Estrutura resumida:

```json
{
  "treinos": [
    { "id": "...", "nome": "Costas e bíceps", "grupo": "Superior",
      "duracaoMin": 55, "exercicios": ["Puxada frontal", "Remada baixa"] }
  ],
  "sessoes": [
    { "id": "...", "treinoId": "...", "treinoNome": "Costas e bíceps",
      "data": "2026-11-23", "concluida": true,
      "exercicios": [ { "nome": "Puxada frontal", "concluido": true,
                        "carga": 40.0, "repeticoes": 10 } ] }
  ]
}
```

Para voltar ao estado inicial, feche o app e renomeie ou apague esse arquivo.

---

## Conceitos de POO aplicados

- **Herança:** as telas e componentes estendem containers do JavaFX. `TelaVisaoGeral`, `TelaTreinos`, `TelaConquistas`, `TelaExecucaoTreino` e `StatCard` estendem `VBox`; `TreinoCard` estende `StackPane`. Assim herdam o comportamento de layout e acrescentam apenas o que é específico.
- **Polimorfismo:** o `App` recebe qualquer tela como `VBox` ao montar o menu e ao trocar de conteúdo, sem saber qual tela é. O método `start` sobrescreve o de `Application`, e `getChildren()` aceita qualquer `Node`.
- **Encapsulamento:** atributos privados com *getters* nas classes de dados (`Treino`, `SessaoTreino`, `ExercicioRealizado`, `Conquista`).
- **Composição:** um `Treino` possui uma lista de exercícios, e uma `SessaoTreino` possui uma lista de `ExercicioRealizado`.
- **Singleton:** `RepositorioTreinos.get()` devolve sempre a mesma instância, compartilhada por todas as telas.
- **Reutilização de componentes:** `StatCard` e `TreinoCard` são escritos uma vez e usados em vários pontos.
- **Callbacks:** `Runnable` e *lambdas* permitem que um componente avise quem o criou que algo aconteceu (por exemplo, "Começar treino").

---

## Como executar

**Requisitos:** JDK instalado (versão compatível com a configurada no `pom.xml`) e Maven. O IntelliJ IDEA já inclui o suporte ao Maven.

1. Abra a pasta do projeto no IntelliJ IDEA.
2. Recarregue o Maven (*Reload All Maven Projects*) para baixar JavaFX e Gson.
3. Confirme em *File → Project Structure* que o SDK e o *Language level* são os mesmos do `pom.xml`.
4. Execute a classe `br.edu.ifsp.App`.

Se o plugin do JavaFX estiver configurado no `pom.xml`, também é possível rodar pelo terminal com `mvn javafx:run`.

---

## Estado atual e próximos passos

**Pronto**
- Navegação completa com barra lateral e *breadcrumb*
- Meus treinos: criar, excluir, adicionar e remover exercícios
- Execução de treino com carga e repetições, e histórico salvo em JSON
- Conquistas com filtros e barras de progresso
- Componentes reutilizáveis (`StatCard`, `TreinoCard`)

**Em andamento / a fazer**
- [ ] Visão geral lendo os dados reais (hoje os indicadores e o treino do dia são valores fixos)
- [ ] Tela **Evolução**
- [ ] Tela **Meu corpo**
- [ ] Conquistas calculadas a partir do histórico (hoje o progresso é fixo em 0)
- [ ] Card "Sua constância" da Visão geral
- [ ] Nome do usuário personalizável (hoje há um texto provisório na saudação)
- [ ] Mover os estilos para um arquivo `.css`
- [ ] Ícones no lugar dos emojis

---

## Autores

- **Gian Li Zhang**
- **Alexandre Bagini**
