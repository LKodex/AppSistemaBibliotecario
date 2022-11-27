package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityBibliotecariosBinding;
import io.github.lkodex.appsistemabibliotecario.sistema.LibraryFacade;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Bibliotecario;

public class BibliotecariosActivity extends AppCompatActivity {
    private static LibraryFacade facade;
    private ActivityBibliotecariosBinding binding;
    private Bibliotecario bibliotecario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBibliotecariosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String bibliotecarioCpf = getIntent().getStringExtra("SELECTED_CPF");
        if (bibliotecarioCpf != null) bibliotecario = facade.getBibliotecario(bibliotecarioCpf);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (bibliotecario == null) binding.btnBibliotecariosExcluir.setVisibility(View.GONE);
        else {
            binding.edtTxtBibliotecariosCPF.setText(bibliotecario.cpf);
            binding.edtTxtBibliotecariosNome.setText(bibliotecario.nome);
            binding.edtTxtBibliotecariosEmail.setText(bibliotecario.email);
            binding.edtTxtBibliotecariosSenha.setText(bibliotecario.senha);
            binding.edtTxtBibliotecariosEndereco.setText(bibliotecario.endereco);
            binding.edtTxtBibliotecariosTelefone.setText(bibliotecario.telefone);
        }
    }

    public void btnVoltar(View view){ finish(); }

    public void btnExcluir(View view){
        new AlertDialog.Builder(this)
                .setTitle("Excluir Bibliotecario")
                .setMessage("Você deseja excluir esse bibliotecario?")
                .setPositiveButton("Sim", (dialogInterface, i) -> {
                    try {
                        facade.getDatabase().bibliotecarioDAO().delete(bibliotecario);
                        finish();
                    } catch (SQLiteConstraintException e) {
                        Toast.makeText(
                                BibliotecariosActivity.this,
                                "Não é possível excluir esse bibliotecario",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void btnSalvar(View view){
        // TODO: Validar os dados

        String toastSuccessMessage = "Bibliotecario atualizado com sucesso!";
        Boolean novoBibliotecario = bibliotecario == null;
        if (novoBibliotecario) {
            bibliotecario = new Bibliotecario();
            toastSuccessMessage = "Bibliotecario atualizado com sucesso!";
        }
        bibliotecario.cpf = binding.edtTxtBibliotecariosCPF.getText().toString();
        bibliotecario.nome = binding.edtTxtBibliotecariosNome.getText().toString();
        bibliotecario.email = binding.edtTxtBibliotecariosEmail.getText().toString();
        bibliotecario.senha = binding.edtTxtBibliotecariosSenha.getText().toString();
        bibliotecario.endereco = binding.edtTxtBibliotecariosEndereco.getText().toString();
        bibliotecario.telefone = binding.edtTxtBibliotecariosTelefone.getText().toString();
        if (novoBibliotecario) facade.getDatabase().bibliotecarioDAO().insertAll(bibliotecario);
        else facade.getDatabase().bibliotecarioDAO().update(bibliotecario);
        Toast.makeText(
                getApplicationContext(),
                toastSuccessMessage,
                Toast.LENGTH_SHORT
        ).show();
        finish();
    }
}