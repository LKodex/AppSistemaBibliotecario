package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityAlunosListagemBinding;
import io.github.lkodex.appsistemabibliotecario.sistema.LibraryFacade;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Aluno;

public class AlunosListagemActivity extends AppCompatActivity {
    public static LibraryFacade facade;
    private ActivityAlunosListagemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlunosListagemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        facade = LoginActivity.facade;

        binding.btnAlunosListagemVoltar.setOnClickListener(view -> finish());

        binding.btnAlunosListagemNovo.setOnClickListener(view -> {
            Intent intent = new Intent(AlunosListagemActivity.this, AlunosActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        List<Aluno> alunos = facade.getDatabase().alunoDAO().getAll();
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                alunos
        );
        binding.listViewAlunosListagem.setAdapter(adapter);
        binding.listViewAlunosListagem.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(AlunosListagemActivity.this, AlunosActivity.class);
            intent.putExtra("SELECTED_RGA", alunos.get(position).rga);
            startActivity(intent);
        });
    }
}