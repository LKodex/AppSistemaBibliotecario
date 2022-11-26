package io.github.lkodex.appsistemabibliotecario.sistema.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(){
        super("No user with the specified CPF was found!");
    }
    public UserNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
