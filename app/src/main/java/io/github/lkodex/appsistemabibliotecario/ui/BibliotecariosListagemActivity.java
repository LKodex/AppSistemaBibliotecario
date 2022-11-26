package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityBibliotecariosListagemBinding;

public class BibliotecariosListagemActivity extends AppCompatActivity {
    private ActivityBibliotecariosListagemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBibliotecariosListagemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}