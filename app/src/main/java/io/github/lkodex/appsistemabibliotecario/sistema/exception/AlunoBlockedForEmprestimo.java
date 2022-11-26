package io.github.lkodex.appsistemabibliotecario.sistema.exception;

public class AlunoBlockedForEmprestimo extends Exception {
    public AlunoBlockedForEmprestimo(String errorMessage){
        super(errorMessage);
    }
}
