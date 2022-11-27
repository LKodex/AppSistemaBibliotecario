package io.github.lkodex.appsistemabibliotecario.sistema.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

import io.github.lkodex.appsistemabibliotecario.sistema.entity.Emprestimo;

@Dao
public interface EmprestimoDAO {
    @Query("SELECT * FROM emprestimo")
    List<Emprestimo> getAll();

    @Query("SELECT * FROM emprestimo WHERE uuid IN (:emprestimoIds)")
    List<Emprestimo> loadAllByIds(UUID[] emprestimoIds);

    @Query("SELECT e.* FROM emprestimo AS e WHERE e.livro IN (SELECT (uuid) FROM livro WHERE livro.uuid = e.livro) AND e.uuid NOT IN (SELECT (uuid) FROM devolucao WHERE devolucao.emprestimo = e.uuid)")
    List<Emprestimo> carregarEmprestimosNaoDevolvidos();

    @Update
    void update(Emprestimo emprestimo);

    @Insert
    void insertAll(Emprestimo... emprestimos);

    @Delete
    void delete(Emprestimo emprestimo);
}
