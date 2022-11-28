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

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityDevolucoesBinding;
import io.github.lkodex.appsistemabibliotecario.sistema.LibraryFacade;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Devolucao;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Emprestimo;

public class DevolucoesActivity extends AppCompatActivity {
    public static LibraryFacade facade;
    private ActivityDevolucoesBinding binding;
    private Devolucao devolucao;
    private List<Emprestimo> emprestimosAbertos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDevolucoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        facade = DevolucoesListagemActivity.facade;

        emprestimosAbertos = facade.listaDeEmprestimosAbertos();
        ArrayAdapter<Emprestimo> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                emprestimosAbertos
        );
        binding.spinnerDevolucoesEmprestimosAbertos.setAdapter(adapter);

        String devolucaoStringUUID = getIntent().getStringExtra("SELECTED_UUID");
        UUID devolucaoUUID = null;
        if (devolucaoStringUUID != null) devolucaoUUID = UUID.fromString(devolucaoStringUUID);
        if (devolucaoUUID != null) devolucao = facade.getDevolucao(devolucaoUUID);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (devolucao == null) binding.btnDevolucoesExcluir.setVisibility(View.GONE);
        else {
            //binding.edtTxtDevolucoesUUID.setText(devolucao.emprestimo.toString());
            for (int i = 0; i < emprestimosAbertos.size(); i++){
                if (emprestimosAbertos.get(i).uuid == devolucao.emprestimo){
                    binding.spinnerDevolucoesEmprestimosAbertos.setSelection(i);
                    break;
                }
            }
            binding.edtTxtDevolucoesBibliotecario.setText(devolucao.bibliotecario);
            binding.edtTxtDevolucoesDataDevolucao.setText(new SimpleDateFormat("dd/MM/yyyy").format(devolucao.dataDevolucao));
        }
    }

    public void btnVoltar(View view){ finish(); }

    public void btnExcluir(View view){
        new AlertDialog.Builder(this)
                .setTitle("Excluir Devolução")
                .setMessage("Você deseja excluir essa devolução?")
                .setPositiveButton("Sim", (dialogInterface, i) -> {
                    try {
                        facade.getDatabase().devolucaoDAO().delete(devolucao);
                        finish();
                    } catch (SQLiteConstraintException e) {
                        Toast.makeText(
                                DevolucoesActivity.this,
                                "Não é possível excluir essa devolução",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void btnSalvar(View view){
        // TODO: Validar tudo :D

        Boolean novaDevolucao = devolucao == null;
        String toastSuccessMessage = "Devolução atualizado com sucesso!";
        if (novaDevolucao) {
            devolucao = new Devolucao();
            toastSuccessMessage = "Devolução inserida com sucesso!";
        }
        Integer selectedEmprestimoUUID = binding.spinnerDevolucoesEmprestimosAbertos.getSelectedItemPosition();
        devolucao.emprestimo = emprestimosAbertos.get(selectedEmprestimoUUID).uuid;
        devolucao.bibliotecario = facade.getBibliotecario().cpf;
        devolucao.dataDevolucao = new Date(System.currentTimeMillis());
        if (novaDevolucao) facade.getDatabase().devolucaoDAO().insertAll(devolucao);
        else facade.getDatabase().devolucaoDAO().update(devolucao);
        Toast.makeText(
                getApplicationContext(),
                toastSuccessMessage,
                Toast.LENGTH_SHORT
        ).show();
        finish();
    }
}
