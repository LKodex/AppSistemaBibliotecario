package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityLivrosListagemBinding;

public class LivrosListagemActivity extends AppCompatActivity {
    private ActivityLivrosListagemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLivrosListagemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}