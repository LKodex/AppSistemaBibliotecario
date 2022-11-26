package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityAlunosBinding;

public class AlunosActivity extends AppCompatActivity {
    private ActivityAlunosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlunosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}