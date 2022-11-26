package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityLivrosBinding;

public class LivrosActivity extends AppCompatActivity {
    private ActivityLivrosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLivrosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}