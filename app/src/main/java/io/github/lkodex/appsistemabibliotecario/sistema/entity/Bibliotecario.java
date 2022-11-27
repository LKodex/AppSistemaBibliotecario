package io.github.lkodex.appsistemabibliotecario.sistema.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Bibliotecario {
    @PrimaryKey
    @NonNull
    public String cpf;
    @NonNull
    public String senha;
    @Ignore
    public String senhaString;
    public String nome;
    public String endereco;
    public String telefone;
    public String email;
    public byte[] foto;

    @Ignore
    public String toString(){
        return String.format("%s\n%s", cpf, nome);
    }
}
