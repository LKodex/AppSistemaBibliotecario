package io.github.lkodex.appsistemabibliotecario.sistema;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

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
    protected Boolean userAuthenticated = false;

    public Authentication(AppDatabase database){
        this.database = database;
    }

    public Authentication(AppDatabase database, Bibliotecario usuario) throws UserNotFoundException, InvalidAttributeValueException {
        this(database);
        this.user = usuario;
        validateData(user);
        authenticateUser();
    }

    public void register(Bibliotecario bibliotecario) throws InvalidAttributeValueException {
        try {
            bibliotecario.senha = SHA.hash(bibliotecario.senha);
            BibliotecarioDAO dao = database.bibliotecarioDAO();
            dao.insertAll(bibliotecario);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void authenticateUser() throws UserNotFoundException, InvalidAttributeValueException {
        /* Validar CPF e Senha
         * Existe usuários cadastrados
         *	C1, C2	Procurar usuário por CPF
         *	C2	Retornar erro
         *	C1	Comparar hash da senha e senha do BD
         *	C3	Cadastrar usuário no Banco de Dados
         * Atualizar variável autenticado
         */
        int registerCount = database.bibliotecarioDAO().count();
        if (registerCount <= 0) register(user);
        Bibliotecario userDB;
        try {
            userDB = database.bibliotecarioDAO().loadAllByIds(new String[]{user.cpf}).get(0);
            userAuthenticated = SHA.compareHash(user.senha, userDB.senha);
        }
        catch (Exception e) { throw new UserNotFoundException(); }
    }

    protected void validateData(Bibliotecario bibliotecario) throws InvalidAttributeValueException {
        if(bibliotecario.cpf == null){ throw new InvalidAttributeValueException("CPF field is null"); }

        char[] originalCpf = bibliotecario.cpf.toCharArray();
        char[] validatedCpf = bibliotecario.cpf.toCharArray();

        if(originalCpf.length != 11){ throw new InvalidAttributeValueException("CPF length is not equal 11"); }

        int validatingSum = 0;
        for (int i = 10; i >= 2; i--) { validatingSum += (originalCpf[10 - i] - 48) * i; }
        validatedCpf[9] = (char)(validatingSum * 10 % 11 + 48);

        validatingSum = 0;
        for (int i = 11; i >= 2; i--) { validatingSum += (validatedCpf[11 - i] - 48) * i; }
        validatedCpf[10] = (char)(validatingSum * 10 % 11 + 48);

        if (originalCpf.toString().equals(validatedCpf.toString())) throw new InvalidAttributeValueException("CPF is not valid");
        if (bibliotecario.senhaString.isEmpty()) throw new InvalidAttributeValueException("Password cannot be empty");
        try {
            bibliotecario.senha = SHA.hash(bibliotecario.senhaString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Boolean isAuthenticated() throws UserNotFoundException, InvalidAttributeValueException {
        authenticateUser();
        return userAuthenticated;
    }
    public Bibliotecario getUser() { return user; }
}
