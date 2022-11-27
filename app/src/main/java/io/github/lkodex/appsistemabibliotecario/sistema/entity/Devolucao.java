package io.github.lkodex.appsistemabibliotecario.sistema.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = Emprestimo.class,
                        parentColumns = "uuid",
                        childColumns = "emprestimo",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Bibliotecario.class,
                        parentColumns = "cpf",
                        childColumns = "bibliotecario",
                        onDelete = ForeignKey.NO_ACTION
                )
        }
)
public class Devolucao {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "emprestimo")
    public UUID     emprestimo;
    @NonNull
    public String   bibliotecario;
    @NonNull
    @ColumnInfo(name = "data_devolucao")
    public Date     dataDevolucao;
    public byte[]   foto;

    @Ignore
    public String toString(){
        return String.format("%s\tDevolvido em: %s", emprestimo, new SimpleDateFormat("dd/MM/yyyy").format(dataDevolucao));
    }
}
