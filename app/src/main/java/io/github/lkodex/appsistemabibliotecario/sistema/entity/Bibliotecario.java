package io.github.lkodex.appsistemabibliotecario.sistema.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bibliotecario {
    @PrimaryKey
    @NonNull
    public String cpf;
    @NonNull
    public String senha;
    public String nome;
    public String endereco;
    public String telefone;
    public String email;
    public byte[] foto;
}
