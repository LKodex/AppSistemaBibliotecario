package io.github.lkodex.appsistemabibliotecario.sistema.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Livro {
    @PrimaryKey
    @NonNull
    public UUID   uuid;
    public String titulo;
    public String editora;
    @ColumnInfo(name = "ano_publicacao")
    public Integer anoPublicacao;

    @Ignore
    public String toString(){
        return String.format("Titulo: %s\nEditora: %s\nAno: %d", titulo, editora, anoPublicacao);
    }
}
