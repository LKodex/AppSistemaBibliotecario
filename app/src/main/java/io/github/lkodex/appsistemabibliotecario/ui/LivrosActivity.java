package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.UUID;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityLivrosBinding;
import io.github.lkodex.appsistemabibliotecario.sistema.LibraryFacade;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Livro;

public class LivrosActivity extends AppCompatActivity {
    public static LibraryFacade facade;
    private ActivityLivrosBinding binding;
    private Livro livro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLivrosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        facade = LivrosListagemActivity.facade;

        String livroStringUUID = getIntent().getStringExtra("SELECTED_UUID");
        UUID livroUUID = null;
        if (livroStringUUID != null) livroUUID = UUID.fromString(livroStringUUID);
        if (livroUUID != null) livro = facade.getDatabase().livroDAO().loadAllById(new UUID[] { livroUUID }).get(0);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (livro == null) binding.btnLivrosExcluir.setVisibility(View.GONE);
        else {
            binding.edtTxtLivrosUUID.setText(livro.uuid.toString());
            binding.edtTxtLivrosTitulo.setText(livro.titulo);
            binding.edtTxtLivrosEditora.setText(livro.editora);
            binding.edtTxtLivrosAnoPublicacao.setText(livro.anoPublicacao.toString());
        }
    }

    public void btnVoltar(View view){ finish(); }

    public void btnExcluir(View view){
        new AlertDialog.Builder(this)
                .setTitle("Excluir Livro")
                .setMessage("Você deseja excluir esse livro?")
                .setPositiveButton("Sim", (dialogInterface, i) -> {
                    try {
                        facade.getDatabase().livroDAO().delete(livro);
                        finish();
                    } catch (SQLiteConstraintException e) {
                        Toast.makeText(
                                LivrosActivity.this,
                                "Não é possível excluir esse livro",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void btnSalvar(View view){
        // TODO: Validar como sempre

        Boolean novoLivro = livro == null;
        String toastSuccessMessage = "Livro atualizado com sucesso!";
        if (novoLivro) {
            livro = new Livro();
            toastSuccessMessage = "Livro inserido com sucesso!";
        }
        String livroStringUUID = binding.edtTxtLivrosUUID.getText().toString();
        if (livroStringUUID.isEmpty()) livro.uuid = UUID.randomUUID();
        else livro.uuid = UUID.fromString(livroStringUUID);
        livro.titulo = binding.edtTxtLivrosTitulo.getText().toString();
        livro.editora = binding.edtTxtLivrosEditora.getText().toString();
        livro.anoPublicacao = Integer.parseInt(binding.edtTxtLivrosAnoPublicacao.getText().toString());
        if (novoLivro) facade.getDatabase().livroDAO().insertAll(livro);
        else facade.getDatabase().livroDAO().update(livro);
        Toast.makeText(
                getApplicationContext(),
                toastSuccessMessage,
                Toast.LENGTH_SHORT
        ).show();
        finish();
    }
}