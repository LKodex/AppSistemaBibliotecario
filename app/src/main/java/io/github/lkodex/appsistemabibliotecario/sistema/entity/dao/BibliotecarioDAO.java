package io.github.lkodex.appsistemabibliotecario.sistema.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.github.lkodex.appsistemabibliotecario.sistema.entity.Bibliotecario;

@Dao
public interface BibliotecarioDAO {
    @Query("SELECT * FROM bibliotecario")
    List<Bibliotecario> getAll();

    @Query("SELECT * FROM bibliotecario WHERE cpf IN (:bibliotecarioIds)")
    List<Bibliotecario> loadAllByIds(String[] bibliotecarioIds);

    @Query("SELECT COUNT(cpf) FROM bibliotecario")
    Integer count();

    @Insert
    void insertAll(Bibliotecario... bibliotecarios);

    @Delete
    void delete(Bibliotecario bibliotecario);
}
