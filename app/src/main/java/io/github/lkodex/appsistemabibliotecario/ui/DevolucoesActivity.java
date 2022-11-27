package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.UUID;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityDevolucoesBinding;
import io.github.lkodex.appsistemabibliotecario.sistema.LibraryFacade;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Devolucao;

public class DevolucoesActivity extends AppCompatActivity {
    public static LibraryFacade facade;
    private ActivityDevolucoesBinding binding;
    private Devolucao devolucao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDevolucoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        facade = DevolucoesListagemActivity.facade;

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
}