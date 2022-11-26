package io.github.lkodex.appsistemabibliotecario.sistema.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.github.lkodex.appsistemabibliotecario.sistema.entity.Aluno;

@Dao
public interface AlunoDAO {
    @Query("SELECT * FROM aluno")
    List<Aluno> getAll();

    @Query("SELECT * FROM aluno WHERE rga IN (:alunoIds)")
    List<Aluno> loadAllByIds(String[] alunoIds);

    @Insert
    void insertAll(Aluno... alunos);

    @Delete
    void delete(Aluno aluno);
}
