package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityDevolucoesBinding;

public class DevolucoesActivity extends AppCompatActivity {
    private ActivityDevolucoesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDevolucoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}