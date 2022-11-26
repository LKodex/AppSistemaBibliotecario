package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityDevolucoesListagemBinding;

public class DevolucoesListagemActivity extends AppCompatActivity {
    private ActivityDevolucoesListagemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDevolucoesListagemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}