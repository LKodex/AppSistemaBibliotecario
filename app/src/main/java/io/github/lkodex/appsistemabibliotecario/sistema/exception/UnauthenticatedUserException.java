package io.github.lkodex.appsistemabibliotecario.sistema.exception;

public class UnauthenticatedUserException extends Exception {
    public UnauthenticatedUserException(){
        super("The passwords does not match for the specified user CPF!");
    }

    public UnauthenticatedUserException(String errorMessage){
        super(errorMessage);
    }
}
