package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityLivrosListagemBinding;
import io.github.lkodex.appsistemabibliotecario.sistema.LibraryFacade;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Livro;

public class LivrosListagemActivity extends AppCompatActivity {
    public static LibraryFacade facade;
    private ActivityLivrosListagemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLivrosListagemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        facade = LoginActivity.facade;

        binding.btnLivrosListagemVoltar.setOnClickListener(view -> finish());

        binding.btnLivrosListagemNovo.setOnClickListener(view -> {
            Intent intent = new Intent(LivrosListagemActivity.this, LivrosActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Livro> livros = facade.getDatabase().livroDAO().getLivrosDisponiveis();
        ArrayAdapter<Livro> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                livros
        );
        binding.listViewLivrosListagem.setAdapter(adapter);
        binding.listViewLivrosListagem.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(LivrosListagemActivity.this, LivrosActivity.class);
            intent.putExtra("SELECTED_UUID", livros.get(position).uuid);
            startActivity(intent);
        });
    }
}