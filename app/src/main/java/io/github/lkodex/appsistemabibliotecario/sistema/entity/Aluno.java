package io.github.lkodex.appsistemabibliotecario.sistema.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.sql.Date;

@Entity
public class Aluno {
    @PrimaryKey
    @NonNull
    public String   rga;
    public String   nome;
    public String   endereco;
    public String   telefone;
    public String   email;
    @ColumnInfo(name = "emprestimo_bloqueado_ate")
    public Date     emprestimoBloqueadoAte;
    public byte[]   foto;
}
