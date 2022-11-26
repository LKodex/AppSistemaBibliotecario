package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityBibliotecariosBinding;

public class BibliotecariosActivity extends AppCompatActivity {
    private ActivityBibliotecariosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBibliotecariosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}