package io.github.lkodex.appsistemabibliotecario.sistema.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

import io.github.lkodex.appsistemabibliotecario.sistema.entity.Livro;

@Dao
public interface LivroDAO {
    @Query("SELECT * FROM livro")
    List<Livro> getAll();

    @Query("SELECT l.* FROM livro AS l WHERE l.uuid NOT IN (SELECT e.livro FROM emprestimo AS e WHERE e.livro IN (SELECT (uuid) FROM livro WHERE livro.uuid = e.livro) AND e.uuid NOT IN (SELECT (uuid) FROM devolucao WHERE devolucao.emprestimo = e.uuid))")
    List<Livro> getLivrosDisponiveis();

    @Query("SELECT * FROM livro WHERE uuid IN (:livroIds)")
    List<Livro> loadAllById(UUID[] livroIds);

    @Update
    void update(Livro livro);

    @Insert
    void insertAll(Livro... livros);

    @Delete
    void delete(Livro livro);
}
