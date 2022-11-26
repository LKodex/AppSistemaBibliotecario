package io.github.lkodex.appsistemabibliotecario.sistema.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.util.UUID;

@Entity
public class Livro {
    @PrimaryKey
    @NonNull
    public UUID   uuid;
    public String titulo;
    public String editora;
    @ColumnInfo(name = "ano_publicacao")
    public Date   anoPublicacao;
}
