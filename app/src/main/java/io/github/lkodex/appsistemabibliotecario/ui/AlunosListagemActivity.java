package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityAlunosListagemBinding;

public class AlunosListagemActivity extends AppCompatActivity {
    private ActivityAlunosListagemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlunosListagemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}