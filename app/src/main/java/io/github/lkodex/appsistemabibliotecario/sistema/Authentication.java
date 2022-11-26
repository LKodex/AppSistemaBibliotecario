package io.github.lkodex.appsistemabibliotecario.sistema;

import java.security.NoSuchAlgorithmException;

import io.github.lkodex.appsistemabibliotecario.sistema.database.AppDatabase;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Bibliotecario;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.dao.BibliotecarioDAO;
import io.github.lkodex.appsistemabibliotecario.sistema.exception.InvalidAttributeValueException;
import io.github.lkodex.appsistemabibliotecario.sistema.exception.UnauthenticatedUserException;
import io.github.lkodex.appsistemabibliotecario.sistema.exception.UserNotFoundException;
import io.github.lkodex.appsistemabibliotecario.sistema.util.SHA;

public class Authentication {
    protected AppDatabase database;
    protected Bibliotecario user;
    protected Boolean userAuthenticated;

    public Authentication(AppDatabase database){
        this.database = database;
    }

    public Authentication(AppDatabase database, Bibliotecario usuario) throws UserNotFoundException, InvalidAttributeValueException {
        this(database);
        this.user = usuario;
        int registerCount = database.bibliotecarioDAO().count();
        if (registerCount > 0) authenticateUser();
        else register(user);
    }

    public void register(Bibliotecario bibliotecario) throws InvalidAttributeValueException {
        validateData(bibliotecario);
        System.out.println(bibliotecario.senha);
        BibliotecarioDAO dao = database.bibliotecarioDAO();
        dao.insertAll(bibliotecario);
    }

    public void authenticateUser() throws UserNotFoundException {
        Bibliotecario userDB;
        try { userDB = database.bibliotecarioDAO().loadAllByIds(new String[]{ user.cpf }).get(0);}
        catch (Exception e) { throw new UserNotFoundException(); }
        System.out.println(user.senha);
        System.out.println(userDB.senha);
        userAuthenticated = SHA.compareHash(user.senha, userDB.senha);
    }

    protected void validateData(Bibliotecario bibliotecario) throws InvalidAttributeValueException {
        if(bibliotecario.cpf == null){ throw new InvalidAttributeValueException("CPF field is null"); }

        char[] originalCpf = bibliotecario.cpf.toCharArray();
        char[] validatedCpf = bibliotecario.cpf.toCharArray();
        int validatingSum = 0;

        if(originalCpf.length != 11){ throw new InvalidAttributeValueException("CPF length is not equal 11"); }

        for (int i = 10; i >= 2; i--) { validatingSum += (originalCpf[10 - i] - 48) * i; }
        validatedCpf[9] = (char)(validatingSum * 10 % 11 + 48);
        validatingSum = 0;

        for (int i = 11; i >= 2; i--) { validatingSum += (validatedCpf[11 - i] - 48) * i; }
        validatedCpf[10] = (char)(validatingSum * 10 % 11 + 48);

        System.out.println(originalCpf);
        System.out.println(validatedCpf);
        if (originalCpf.toString().equals(validatedCpf.toString())) throw new InvalidAttributeValueException("CPF is not valid");

        if (bibliotecario.senha.isEmpty()) throw new InvalidAttributeValueException("Password cannot be empty");
    }

    public Boolean isAuthenticated() { return userAuthenticated; }
    public Bibliotecario getUser() { return user; }
}
