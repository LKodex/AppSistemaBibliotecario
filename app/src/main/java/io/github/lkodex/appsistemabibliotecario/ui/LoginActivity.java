package io.github.lkodex.appsistemabibliotecario.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import io.github.lkodex.appsistemabibliotecario.databinding.ActivityLoginBinding;
import io.github.lkodex.appsistemabibliotecario.sistema.LibraryFacade;
import io.github.lkodex.appsistemabibliotecario.sistema.entity.Bibliotecario;
import io.github.lkodex.appsistemabibliotecario.sistema.exception.InvalidAttributeValueException;
import io.github.lkodex.appsistemabibliotecario.sistema.exception.UnauthenticatedUserException;
import io.github.lkodex.appsistemabibliotecario.sistema.exception.UserNotFoundException;

public class LoginActivity extends AppCompatActivity {
    public static LibraryFacade facade;
    private ActivityLoginBinding binding;
    private Bibliotecario userLoggingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLoginEntrar.setOnClickListener(view -> {
            userLoggingIn = new Bibliotecario();
            userLoggingIn.cpf = binding.edtTextLoginCPF.getText().toString();
            userLoggingIn.senhaString = binding.edtTextLoginSenha.getText().toString();

            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);

            try {
                facade = new LibraryFacade(getApplicationContext(), userLoggingIn);
                if (facade.bibliotecarioEstaAutenticado()) startActivity(intent);
                else Toast.makeText(
                        getApplicationContext(),
                        "Senha errada",
                        Toast.LENGTH_SHORT
                ).show();
            } catch (UserNotFoundException | UnauthenticatedUserException | InvalidAttributeValueException e) {
                Toast.makeText(
                        getApplicationContext(),
                        e.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            } catch (Exception e) {
                // Exce????o n??o tratada :D
                // Prov??velmente o m??todo .registrarBibliotecario ir?? levantar uma exception
                // para usu??rios j?? cadastrados no banco de dados que est??o apenas logando
                e.printStackTrace();
            }
        });
    }
}