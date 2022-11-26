package io.github.lkodex.appsistemabibliotecario.sistema.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import java.util.UUID;

import io.github.lkodex.appsistemabibliotecario.sistema.entity.Devolucao;

@Dao
public interface DevolucaoDAO {
    @Query("SELECT * FROM devolucao")
    List<Devolucao> getAll();

    @Query("SELECT * FROM devolucao WHERE emprestimo IN (:devolucaoIds)")
    List<Devolucao> loadAllByIds(UUID[] devolucaoIds);

    @Insert
    void insertAll(Devolucao... devolucoes);

    @Delete
    void delete(Devolucao devolucao);
}
