package io.github.lkodex.appsistemabibliotecario.sistema;

import android.content.Context;

import androidx.room.Room;

import java.util.List;
import java.util.UUID;

import io.github.lkodex.appsistemabibliotecario.sistema.database.AppDatabase;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Aluno;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Bibliotecario;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Devolucao;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Emprestimo;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Livro;
import io.github.lkodex.appsistemabibliotecario.sistema.exception.AlunoBlockedForEmprestimo;
import io.github.lkodex.appsistemabibliotecario.sistema.exception.InvalidAttributeValueException;
import io.github.lkodex.appsistemabibliotecario.sistema.exception.UnauthenticatedUserException;
import io.github.lkodex.appsistemabibliotecario.sistema.exception.UserNotFoundException;

public class LibraryFacade {
    protected AppDatabase database;
    protected Authentication authentication;
    protected LibraryController libraryController;

    /**
     * Fornece uma interface única de todos os componentes para ser usado pela IU
     *
     * @param context       Necessário para construir o banco de dados
     * @param bibliotecario Usuário do sistema
     * @throws UserNotFoundException        É lançado caso exista usuários cadastrados e não tenha sido possível
     *                                      encontrar um usuário com o respectivo login cadastrado
     * @throws UnauthenticatedUserException É lançado caso exista um usuário cadastrado com o respectivo login
     *                                      mas não tenha sido possível autenticar o usuário com a respectiva senha
     */
    public LibraryFacade(Context context, Bibliotecario bibliotecario) throws UserNotFoundException, UnauthenticatedUserException, InvalidAttributeValueException {
        database = Room.databaseBuilder(context, AppDatabase.class, "app_sistema_bibliotecario").allowMainThreadQueries().build();
        authentication = new Authentication(database, bibliotecario);
        libraryController = new LibraryController(database, authentication);
    }

    public void registrarBibliotecario(Bibliotecario bibliotecario) throws UserNotFoundException, UnauthenticatedUserException, InvalidAttributeValueException {
        authentication.register(bibliotecario);
    }

    public void autenticarBibliotecario() throws UserNotFoundException, UnauthenticatedUserException, InvalidAttributeValueException {
        authentication.authenticateUser();
    }

    public Boolean bibliotecarioEstaAutenticado() {
        try { return authentication.isAuthenticated(); }
        catch (Exception e) { return false; }
    }

    public Bibliotecario getBibliotecario() {
        return authentication.getUser();
    }

    public Bibliotecario getBibliotecario(String cpf){
        return database.bibliotecarioDAO().loadAllByIds(new String[]{ cpf }).get(0);
    }

    public void registrarNovoLivro(Livro... livros) throws InvalidAttributeValueException {
        libraryController.registerNewBook(livros);
    }

    public void deletarLivroExistente(Livro... livros) throws InvalidAttributeValueException {
        libraryController.deleteExistentBook(livros);
    }

    public List<Livro> getTodosLivros() {
        return libraryController.getAllLivros();
    }

    public void registrarEmprestimo(Aluno aluno, Livro... livros) throws AlunoBlockedForEmprestimo {
        libraryController.registerEmprestimo(aluno, livros);
    }

    public void registarEmprestimo(Aluno aluno, Integer diasParaDevolucao, Livro... livros) throws AlunoBlockedForEmprestimo {
        libraryController.registerEmprestimo(aluno, diasParaDevolucao, livros);
    }

    public void registrarDevolucao(Emprestimo... emprestimos) {
        libraryController.registerDevolucao();
    }

    public List<Emprestimo> listaDeEmprestimosAbertos(){
        return database.emprestimoDAO().carregarEmprestimosNaoDevolvidos();
    }

    public Aluno getAluno(String... rgas){
        return database.alunoDAO().loadAllByIds(rgas).get(0);
    }

    public void cadastrarAluno(Aluno... alunos) throws UnauthenticatedUserException {
        try {
            if (authentication.isAuthenticated())
            database.alunoDAO().insertAll(alunos);
            else throw new UnauthenticatedUserException();
        } catch (Exception e) {
            throw new UnauthenticatedUserException();
        }
    }

    public AppDatabase getDatabase(){ return database; }

    public Devolucao getDevolucao(UUID devolucaoUUID) {
        return database.devolucaoDAO().loadAllByIds(new UUID[] { devolucaoUUID }).get(0);
    }
}