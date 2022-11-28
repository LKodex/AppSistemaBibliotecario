package io.github.lkodex.appsistemabibliotecario.sistema;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.github.lkodex.appsistemabibliotecario.sistema.database.AppDatabase;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Aluno;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Devolucao;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Emprestimo;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Livro;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.dao.DevolucaoDAO;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.dao.EmprestimoDAO;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.dao.LivroDAO;
import io.github.lkodex.appsistemabibliotecario.sistema.exception.AlunoBlockedForEmprestimo;
import io.github.lkodex.appsistemabibliotecario.sistema.exception.InvalidAttributeValueException;

public class LibraryController {
    protected AppDatabase database;
    protected Authentication authentication;
    protected final Integer DEFAULT_DAYS_TO_DEVOLUCAO = 14;

    public LibraryController(AppDatabase database, Authentication authentication){
        this.database = database;
        this.authentication = authentication;
    }

    public void registerEmprestimo(Aluno aluno, Livro... livros) throws AlunoBlockedForEmprestimo {
        registerEmprestimo(aluno, DEFAULT_DAYS_TO_DEVOLUCAO, livros);
    }
    /*
     *  Ignorando validações
     *  Presumindo que não é possível passar parametros inválidos
     *  É necessário garantir isso através de Interface de Usuário
     */
    public void registerEmprestimo(Aluno aluno, Integer diasParaDevolucao, Livro... livros) throws AlunoBlockedForEmprestimo {
        diasParaDevolucao = diasParaDevolucao == null ? DEFAULT_DAYS_TO_DEVOLUCAO : diasParaDevolucao;

        if (aluno.emprestimoBloqueadoAte != null && new Date(System.currentTimeMillis()).before(aluno.emprestimoBloqueadoAte))
            throw new AlunoBlockedForEmprestimo(String.format("Aluno bloqueado até %s",
                    new SimpleDateFormat("dd-MM-yyyy").format(aluno.emprestimoBloqueadoAte)));

        List<Emprestimo> emprestimos = new ArrayList<>();
        for (Livro livro : livros) {
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.uuid = UUID.randomUUID();
            emprestimo.bibliotecario = authentication.getUser().cpf;
            emprestimo.aluno = aluno.rga;
            emprestimo.livro = livro.uuid;
            emprestimo.dataEmprestimo = new Date(System.currentTimeMillis());
            emprestimo.dataDevolucaoPrevista = new Date(getDateInMilliAfterToday(diasParaDevolucao));
            emprestimos.add(emprestimo);
        }

        EmprestimoDAO emprestimoDAO = database.emprestimoDAO();
        for (Emprestimo emprestimo : emprestimos) emprestimoDAO.insertAll(emprestimo);
    }

    public void registerDevolucao(Emprestimo... emprestimos){
        List<Emprestimo> emprestimosAbertos = database.emprestimoDAO().carregarEmprestimosNaoDevolvidos();
        Boolean blockAluno = false;

        List<Devolucao> devolucoes = new ArrayList<>();
        for (Emprestimo emprestimo : emprestimos){
            Devolucao devolucao = new Devolucao();
            devolucao.emprestimo = emprestimo.uuid;
            devolucao.bibliotecario = authentication.getUser().cpf;
            devolucao.dataDevolucao = new Date(System.currentTimeMillis());
            devolucoes.add(devolucao);

            if (devolucao.dataDevolucao.after(emprestimo.dataDevolucaoPrevista)) blockAluno = true;
        }

        DevolucaoDAO devolucaoDAO = database.devolucaoDAO();
        for (Devolucao devolucao : devolucoes) devolucaoDAO.insertAll(devolucao);

        if (blockAluno) {
            /* TODO: Bloquear aluno por X dias */
        }
    }

    public void registerNewBook(Livro... livros) throws InvalidAttributeValueException {
        for (Livro livro : livros) {
            livro.uuid = UUID.randomUUID();

            if (livro.titulo.isEmpty()) throw new InvalidAttributeValueException("Campo titulo do livro está vazio");
            if (livro.editora.isEmpty()) throw new InvalidAttributeValueException("Campo editora do livro está vazio");
            if (livro.anoPublicacao == null) throw new InvalidAttributeValueException("Campo ano de publicação do livro está vazio");
        }

        database.livroDAO().insertAll(livros);
    }

    public void deleteExistentBook(Livro... livros) throws InvalidAttributeValueException {
        LivroDAO dao = database.livroDAO();
        for (Livro livro : livros) {
            if (livro.uuid == null) throw new InvalidAttributeValueException("UUID do livro não selecionado");
            dao.delete(livro);
        }
    }

    public List<Livro> getAllLivros(){
        return database.livroDAO().getAll();
    }

    public static long getDateInMilliAfterToday(long days){
        final long MILLI_IN_A_SECOND = 1000;
        final long SECONDS_IN_A_MINUTE = 60;
        final long MINUTES_IN_AN_HOUR = 60;
        final long HOURS_IN_A_DAY = 24;
        return MILLI_IN_A_SECOND * SECONDS_IN_A_MINUTE * MINUTES_IN_AN_HOUR * HOURS_IN_A_DAY * days + System.currentTimeMillis();
    }
}
