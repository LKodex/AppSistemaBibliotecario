package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityEmprestimoBinding;

public class EmprestimoActivity extends AppCompatActivity {
    private ActivityEmprestimoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmprestimoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}