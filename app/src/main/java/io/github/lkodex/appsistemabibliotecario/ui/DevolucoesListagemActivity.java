package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityDevolucoesListagemBinding;
import io.github.lkodex.appsistemabibliotecario.sistema.LibraryFacade;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Devolucao;

public class DevolucoesListagemActivity extends AppCompatActivity {
    public static LibraryFacade facade;
    private ActivityDevolucoesListagemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDevolucoesListagemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        facade = LoginActivity.facade;

        binding.btnDevolucoesListagemVoltar.setOnClickListener(view -> finish());

        binding.btnDevolucoesListagemNovo.setOnClickListener(view -> {
            Intent intent = new Intent(DevolucoesListagemActivity.this, DevolucoesActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        List<Devolucao> devolucoes = facade.getDatabase().devolucaoDAO().getAll();
        ArrayAdapter<Devolucao> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                devolucoes
        );
        binding.listViewDevolucoesListagem.setAdapter(adapter);
        binding.listViewDevolucoesListagem.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(DevolucoesListagemActivity.this, DevolucoesActivity.class);
            intent.putExtra("SELECTED_UUID", devolucoes.get(position).emprestimo);
            startActivity(intent);
        });
    }
}