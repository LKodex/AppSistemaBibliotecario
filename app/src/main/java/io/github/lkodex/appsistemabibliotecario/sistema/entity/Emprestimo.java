package io.github.lkodex.appsistemabibliotecario.sistema.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.util.UUID;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = Bibliotecario.class,
                        parentColumns = "cpf",
                        childColumns = "bibliotecario",
                        onDelete = ForeignKey.NO_ACTION
                ),
                @ForeignKey(
                        entity = Aluno.class,
                        parentColumns = "rga",
                        childColumns = "aluno",
                        onDelete = ForeignKey.NO_ACTION
                ),
                @ForeignKey(
                        entity = Livro.class,
                        parentColumns = "uuid",
                        childColumns = "livro",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Emprestimo {
    @PrimaryKey
    @NonNull
    public UUID     uuid;
    @NonNull
    public String   bibliotecario;
    @NonNull
    public String   aluno;
    @NonNull
    public UUID     livro;
    @NonNull
    @ColumnInfo(name = "data_emprestimo")
    public Date     dataEmprestimo;
    @NonNull
    @ColumnInfo(name = "data_devolucao_prevista")
    public Date     dataDevolucaoPrevista;
    public byte[]   foto;

    @Ignore
    public String toString(){
        return String.format("Livro UUID: %s\nAluno RGA: %s\nEmpréstimo em: %s\nBibliotecario: %s", livro, aluno, dataEmprestimo, bibliotecario);
    }
}
