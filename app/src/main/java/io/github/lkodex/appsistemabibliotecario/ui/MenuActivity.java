package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {
    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnMenuVoltar.setOnClickListener(view -> finish());

        binding.btnMenuAlunos.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, AlunosListagemActivity.class);
            startActivity(intent);
        });

        binding.btnMenuBibliotecarios.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, BibliotecariosListagemActivity.class);
            startActivity(intent);
        });

        binding.btnMenuLivros.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, LivrosListagemActivity.class);
            startActivity(intent);
        });

        binding.btnMenuEmprestimos.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, EmprestimosListagemActivity.class);
            startActivity(intent);
        });

        binding.btnMenuDevolucoes.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, DevolucoesListagemActivity.class);
            startActivity(intent);
        });
    }
}