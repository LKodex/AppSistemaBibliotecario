package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityAlunosBinding;
import io.github.lkodex.appsistemabibliotecario.sistema.LibraryFacade;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Aluno;

public class AlunosActivity extends AppCompatActivity {
    private static LibraryFacade facade;
    private ActivityAlunosBinding binding;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlunosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        facade = AlunosListagemActivity.facade;

        String alunoRga = getIntent().getStringExtra("SELECTED_RGA");
        if (alunoRga != null) aluno = facade.getAluno(alunoRga);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aluno == null) binding.btnAlunosExcluir.setVisibility(View.GONE);
        else {
            binding.edtTxtAlunosRGA.setText(aluno.rga);
            binding.edtTxtAlunosNome.setText(aluno.nome);
            binding.edtTxtAlunosEmail.setText(aluno.email);
            binding.edtTxtAlunosTelefone.setText(aluno.telefone);
            binding.edtTxtAlunosEndereco.setText(aluno.endereco);
        }
    }

    public void btnVoltar(View view) { finish(); }

    public void btnExcluir(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir Aluno")
                .setMessage("Você deseja excluir esse aluno?")
                .setPositiveButton("Sim", (dialogInterface, i) -> {
                    try {
                        facade.getDatabase().alunoDAO().delete(aluno);
                        finish();
                    } catch (SQLiteConstraintException e) {
                        Toast.makeText(
                                AlunosActivity.this,
                                "Não é possível excluir esse aluno",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void btnSalvar(View view) {
        if (binding.edtTxtAlunosRGA.getText().toString().isEmpty()) Toast.makeText(
                getApplicationContext(),
                "RGA não pode estar vazio",
                Toast.LENGTH_SHORT
        ).show();

        String toastSuccessMessage = "Aluno atualizado com sucesso!";
        Boolean novoAluno = aluno == null;
        if (novoAluno) {
            aluno = new Aluno();
            toastSuccessMessage = "Aluno inserido com sucesso!";
        }
        aluno.rga = binding.edtTxtAlunosRGA.getText().toString();
        aluno.nome = binding.edtTxtAlunosNome.getText().toString();
        aluno.email = binding.edtTxtAlunosEmail.getText().toString();
        aluno.telefone = binding.edtTxtAlunosTelefone.getText().toString();
        aluno.endereco = binding.edtTxtAlunosEndereco.getText().toString();

        if (novoAluno) facade.getDatabase().alunoDAO().insertAll(aluno);
        else facade.getDatabase().alunoDAO().update(aluno);
        Toast.makeText(
                getApplicationContext(),
                toastSuccessMessage,
                Toast.LENGTH_SHORT
        ).show();
        finish();
    }
}