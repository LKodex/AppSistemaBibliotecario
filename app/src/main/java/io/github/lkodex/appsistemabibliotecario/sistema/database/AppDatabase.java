package io.github.lkodex.appsistemabibliotecario.sistema.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import io.github.lkodex.appsistemabibliotecario.sistema.entity.Aluno;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Bibliotecario;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Devolucao;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Emprestimo;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Livro;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.dao.AlunoDAO;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.dao.BibliotecarioDAO;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.dao.DevolucaoDAO;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.dao.EmprestimoDAO;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.dao.LivroDAO;

@Database(
        entities = {
                Aluno.class,
                Bibliotecario.class,
                Devolucao.class,
                Emprestimo.class,
                Livro.class
        },
        version = 1
)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract AlunoDAO         alunoDAO();
    public abstract BibliotecarioDAO bibliotecarioDAO();
    public abstract DevolucaoDAO     devolucaoDAO();
    public abstract EmprestimoDAO    emprestimoDAO();
    public abstract LivroDAO         livroDAO();
}
