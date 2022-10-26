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
| Bibliotecário(a) | Pessoa física, usuária final do sistema, mantenedor de uma biblioteca física, responsável por administrar os livros, empréstimos e devoluções

## 3. Termos e Definições

| Termo | Definição |
| - | - |
| Aluno(a) | Pessoa física que pode receber empréstimos e realizar devoluções de livros |
| Livro | - |
| Eprestimo | - |
| Devolucao | - |
| CRUD | Operações de inserir, visualizar, alterar e remover |

## 4. Requisitos Funcionais

- TODO: Descrever textualmente os requisitos

## 5. Mapeamento Objeto Relacional

| Tabela | Atributos |
| - | - |
| bibliotecario | cpf\<\<PK\>\>, nome, endereco, telefone, email |
| aluno | rga\<\<PK\>\>, nome, endereco, telefone, email |
| livro | uuid\<\<PK\>\>, titulo, editora, ano_publicacao |
| emprestimo | uuid\<\<PK\>\>, bibliotecario\<\<FK\>\>, aluno\<\<FK\>\>, livro\<\<FK\>\>, data_emprestimo, data_devolucao_prevista |
| devolucao | emprestimo_uuid\<\<PK\>\>\<\<FK\>\>, bibliotecario\<\<FK\>\>, data_devolucao |