package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityBibliotecariosListagemBinding;
import io.github.lkodex.appsistemabibliotecario.sistema.LibraryFacade;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Bibliotecario;

public class BibliotecariosListagemActivity extends AppCompatActivity {
    public static LibraryFacade facade;
    private ActivityBibliotecariosListagemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBibliotecariosListagemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        facade = LoginActivity.facade;

        binding.btnBibliotecariosListagemVoltar.setOnClickListener(view -> finish());

        binding.btnBibliotecariosListagemNovo.setOnClickListener(view -> {
            Intent intent = new Intent(BibliotecariosListagemActivity.this, BibliotecariosActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        List<Bibliotecario> bibliotecarios = facade.getDatabase().bibliotecarioDAO().getAll();
        ArrayAdapter<Bibliotecario> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                bibliotecarios
        );
        binding.listViewBibliotecariosListagem.setAdapter(adapter);
        binding.listViewBibliotecariosListagem.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(BibliotecariosListagemActivity.this, BibliotecariosActivity.class);
            intent.putExtra("SELECTED_CPF", bibliotecarios.get(position).cpf);
            startActivity(intent);
        });
    }
}