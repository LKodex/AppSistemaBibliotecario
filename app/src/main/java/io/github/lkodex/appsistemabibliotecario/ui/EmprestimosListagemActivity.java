package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityBibliotecariosListagemBinding;
import io.github.lkodex.appsistemabibliotecario.sistema.LibraryFacade;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Emprestimo;

public class EmprestimosListagemActivity extends AppCompatActivity {
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
            Intent intent = new Intent(EmprestimosListagemActivity.this, EmprestimosActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        List<Emprestimo> emprestimos = facade.getDatabase().emprestimoDAO().carregarEmprestimosNaoDevolvidos();
        ArrayAdapter<Emprestimo> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                emprestimos
        );
        binding.listViewBibliotecariosListagem.setAdapter(adapter);
        binding.listViewBibliotecariosListagem.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(EmprestimosListagemActivity.this, EmprestimosActivity.class);
            intent.putExtra("SELECTED_UUID", emprestimos.get(position).uuid.toString());
            startActivity(intent);
        });
    }
}