package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityEmprestimosBinding;
import io.github.lkodex.appsistemabibliotecario.sistema.LibraryController;
import io.github.lkodex.appsistemabibliotecario.sistema.LibraryFacade;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Emprestimo;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Livro;

public class EmprestimosActivity extends AppCompatActivity {
    public static LibraryFacade facade;
    private ActivityEmprestimosBinding binding;
    private Emprestimo emprestimo;
    private List<Livro> livrosDisponiveis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmprestimosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        facade = EmprestimosListagemActivity.facade;

        livrosDisponiveis = facade.getDatabase().livroDAO().getLivrosDisponiveis();
        ArrayAdapter<Livro> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                livrosDisponiveis
        );
        binding.spinnerEmprestimosLivros.setAdapter(adapter);

        String emprestimoStringUUID = getIntent().getStringExtra("SELECTED_UUID");
        UUID emprestimoUUID = null;
        if (emprestimoStringUUID != null) emprestimoUUID = UUID.fromString(emprestimoStringUUID);
        if (emprestimoUUID != null) emprestimo = facade.getDatabase().emprestimoDAO().loadAllByIds(new UUID[] { emprestimoUUID }).get(0);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (emprestimo == null) binding.btnEmprestimosExcluir.setVisibility(View.GONE);
        else {
            for (int i = 0; i < livrosDisponiveis.size(); i++){
                if (livrosDisponiveis.get(i).uuid == emprestimo.livro){
                    binding.spinnerEmprestimosLivros.setSelection(i);
                    break;
                }
            }
            binding.edtTxtEmprestimosUUID.setText(emprestimo.uuid.toString());
            binding.edtTxtEmprestimosAluno.setText(emprestimo.aluno);
            binding.edtTxtEmprestimosBibliotecario.setText(emprestimo.bibliotecario);
            binding.edtTxtEmprestimosDataDeDevolucao.setText(String.format("%s", emprestimo.dataDevolucaoPrevista));
            binding.edtTxtEmprestimosDataDeEmprestimo.setText(String.format("%s", emprestimo.dataEmprestimo));
        }
    }

    public void btnVoltar(View view){ finish(); }

    public void btnExcluir(View view){
        new AlertDialog.Builder(this)
                .setTitle("Excluir Empréstimo")
                .setMessage("Você deseja excluir esse empréstimo?")
                .setPositiveButton("Sim", (dialogInterface, i) -> {
                    try {
                        facade.getDatabase().emprestimoDAO().delete(emprestimo);
                        finish();
                    } catch (SQLiteConstraintException e) {
                        Toast.makeText(
                                EmprestimosActivity.this,
                                "Não é possível excluir esse empréstimo",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void btnSalvar(View view){
        // TODO: Clássica validação que não está implementada

        Boolean novoEmprestimo = emprestimo == null;
        String toastSuccessMessage = "Devolução atualizado com sucesso!";
        if (novoEmprestimo) {
            emprestimo = new Emprestimo();
            toastSuccessMessage = "Devolução inserida com sucesso!";
        }
        Integer spinnerSelectedPosition = binding.spinnerEmprestimosLivros.getSelectedItemPosition();
        emprestimo.livro = livrosDisponiveis.get(spinnerSelectedPosition).uuid;
        String emprestimoStringUUID = binding.edtTxtEmprestimosUUID.getText().toString();
        if (emprestimoStringUUID.isEmpty()) emprestimo.uuid = UUID.randomUUID();
        else emprestimo.uuid = UUID.fromString(emprestimoStringUUID);
        emprestimo.aluno = binding.edtTxtEmprestimosAluno.getText().toString();
        String bibliotecarioCPF = binding.edtTxtEmprestimosBibliotecario.getText().toString();
        if (bibliotecarioCPF.isEmpty()) emprestimo.bibliotecario = facade.getBibliotecario().cpf;
        else emprestimo.bibliotecario = bibliotecarioCPF;
        emprestimo.dataDevolucaoPrevista = new Date(LibraryController.getDateInMilliAfterToday(14));
        emprestimo.dataEmprestimo = new Date(System.currentTimeMillis());
        if (novoEmprestimo) facade.getDatabase().emprestimoDAO().insertAll(emprestimo);
        else facade.getDatabase().emprestimoDAO().update(emprestimo);
        Toast.makeText(
                getApplicationContext(),
                toastSuccessMessage,
                Toast.LENGTH_SHORT
        ).show();
        finish();
    }
}