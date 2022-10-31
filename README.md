# App Sistema Bibliotecário

## Introdução
Este app é um sistema bibliotecário voltado para uma biblioteca escolar que permite um mantenedor de uma biblioteca registre empréstimo de devoluções de livros para alunos cadastrados.

## Alunos (Nome | RGA)
- Lucas Gonçalves Cordeiro | 2021.1906.031-0

---

# Especificação do Software

## 1. Visão Geral do Software

O sistema deve permitir funcionalidades CRUD para bibliotecários, alunos, livros, empréstimos de livros e devoluções de livros. Também é desejável que permita funcionalidades CRUD para fotos de perfil para bibliotecários, alunos e fotos do estado físico do livro associado a empréstimos e devoluções.

## 2. Classes de Usuários

| Ator | Descrição |
| - | - |
| Bibliotecário | Pessoa física, usuária final do sistema, mantenedor de uma biblioteca física, responsável por administrar os livros, empréstimos e devoluções

## 3. Definição de conceitos

| Termo | Definição |
| - | - |
| Aluno | Pessoa física que pode receber empréstimos e realizar devoluções de livros |
| Livro | Livro físico pertencente a bilbioteca |
| Empréstimo | Empréstimo de um livro físico para um aluno |
| Devolução | Devolução de um livro físico feita por um aluno |
| CRUD | Operações de inserir, visualizar, alterar e remover |

## 4. Requisitos Funcionais

### <b>Lançamentos diversos</b>
| Identificador | Descrição |
| - | - |
| RF-001 | O sistema deve permitir o CRUD de Bibliotecário com os seguintes atributos obrigatórios: cpf e nome. E atributos opcionais: endereço, telefone, email e foto de perfil |
| RF-002 | O sistema deve permitir o CRUD de Aluno com os seguintes atributos obrigatórios: rga, nome, endereço. E atributos opcionais: telefone, email e foto de perfil |
| RF-003 | O sistema deve permitir o CRUD de Livro com os seguintes atributos obrigatórios: titulo, editora e ano de publicação |

### <b>Transações</b>
| Identificador | Descrição |
| - | - |
| RF-004 | O sistema deve permitir o Empréstimo de um Livro para um Aluno com os seguintes atributos obrigatórios: identificação do Bibliotecário, identificação do Aluno, identificador do livro, data de empréstimo e data prevista para devolução. E atributo opcional: foto do livro |
| RF-005 | O sistema deve permitir a Devolução com os seguintes atributos obrigatórios: identificador do empréstimo, identificador do Bibliotecário que recebeu a devolução do livro e data de devolução. E atributo opcional: foto do livro |

### <b>Controle e nível de acesso</b>
| Identificador | Descrição |
| - | - |
| RF-006 | O sistema deve exigir que os usuários Bibliotecário façam login |
| RF-007 | O sistema apenas deve permitir cadastro de novos usuários Bibliotecário para um Bibliotecário já autenticado no sistema. (Exceto no caso do sistema não possuir nenhum cadastro, ele deve permitir o cadastro de novos usuários Bibliotecário sem a autenticação de outro usuário Bibliotecário) |
| RF-008 | O sistema deve permitir que apenas usuários Bibliotecários autenticados possam utilizar as funcionalidades do sistema |

## 5. Requisitos Não Funcionais

### <b>Usabilidade</b>
| Identificador | Descrição |
| - | - |
| RNF-001 | O sistema deve exibir poucas funcionalidades por tela (desejável de apenas uma única) |
| RNF-002 | O sistema deve validar todas informações inseridas pelo usuário afim de evitar mal funcionamento |

### <b>Acessibilidade</b>
| Identificador | Descrição |
| - | - |
| RNF-003 | O sistema deve utilizar cores com bom contraste para a interface de usuário |
| RNF-004 | O sistema deve fornecer uma descrição simples e curta dos componentes que estão visíveis na interface de usuário |

### <b>Segurança</b>
| Identificador | Descrição |
| - | - |
| RNF-005 | Devem ser salvas apenas hash das senhas utilizando o algoritmo SHA3 |
| RNF-006 | O sistema deve permitir que apenas usuários Bibliotecário tenham acesso aos dados do sistema |

## 6. Mapeamento Objeto Relacional

| Tabela | Atributos |
| - | - |
| bibliotecario | cpf\<\<PK\>\>, nome, endereco, telefone, email, foto |
| aluno | rga\<\<PK\>\>, nome, endereco, telefone, email, foto |
| livro | uuid\<\<PK\>\>, titulo, editora, ano_publicacao |
| emprestimo | uuid\<\<PK\>\>, bibliotecario\<\<FK\>\>, aluno\<\<FK\>\>, livro\<\<FK\>\>, data_emprestimo, data_devolucao_prevista, foto |
| devolucao | emprestimo_uuid\<\<PK\>\>\<\<FK\>\>, bibliotecario\<\<FK\>\>, data_devolucao, foto |