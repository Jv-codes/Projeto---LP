# SpeedMath

## Descrição do Mini Projeto da Disciplina – LP 2024.2

### Contexto e Objetivo da Atividade

O mini projeto proposto na disciplina de Linguagem de Programação (LP) para o semestre 2024.2 teve como objetivo principal o desenvolvimento de um sistema simples, porém completo, que envolvesse conceitos fundamentais da programação orientada a objetos e técnicas essenciais de desenvolvimento de software.

O projeto deveria contemplar desde o cadastro e manipulação de dados até o tratamento de exceções e persistência em arquivos, consolidando assim os conteúdos estudados ao longo do semestre.

O **SpeedMath** é um projeto que visa aprimorar as habilidades matemáticas dos usuários por meio de um quiz interativo. Os usuários podem se cadastrar, participar de quizzes com questões matemáticas e acompanhar seu desempenho em um ranking.

### Requisitos da Atividade

Foram especificados os seguintes requisitos obrigatórios para o projeto:

- **Criação de uma classe entidade com os métodos `hashCode` e `equals`:**  
Para garantir a correta comparação e gerenciamento dos objetos, uma classe principal do sistema deveria implementar esses métodos, preferencialmente gerados pelas ferramentas do ambiente de desenvolvimento (Eclipse, IntelliJ, VSCode).

- **Interface para operações principais do sistema:**  
O sistema deveria possuir uma interface que definisse as operações básicas (como cadastrar, pesquisar, remover), implementada por uma classe concreta que utiliza uma lista dinâmica (ArrayList ou LinkedList) para armazenar os dados.

- **Tratamento de exceções personalizadas:**  
O projeto deveria incluir ao menos uma classe de exceção criada pelo grupo, que fosse lançada em situações específicas e devidamente tratada no código, especialmente na classe que implementa o menu principal.

- **Persistência dos dados em arquivos:**  
Os dados cadastrados deveriam ser salvos em arquivo para manter a persistência entre execuções do programa, sendo recuperados na inicialização e salvos ao final.

- **Menu para interação com o usuário:**  
Um menu (pode ser textual simples) que permita o acesso às principais operações do sistema, garantindo usabilidade e organização.

- **(Opcional) Utilização de herança:**  
Poderia haver uma classe base, abstrata ou não, para promover reaproveitamento e melhor organização do código.
