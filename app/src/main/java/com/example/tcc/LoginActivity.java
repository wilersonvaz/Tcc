package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tcc.AsyncTasks.LoginTask;
import com.example.tcc.Model.Usuario;
import com.example.tcc.Model.WebService;
import com.example.tcc.ModelDAO.UsuarioDAO;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail, edtSenha;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);

        edtEmail.setNextFocusDownId(R.id.edtSenha);

        btnLogin = findViewById(R.id.btnLogin);

//        edtEmail.setText("wilersonalves@gmail.com");
//        edtSenha.setText("123");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    Usuario usuario = new Usuario(
                            edtEmail.getText().toString(),
                            edtSenha.getText().toString()
                    );

                    UsuarioDAO usuarioDao = new UsuarioDAO("usuario");
                    usuarioDao.login(getApplicationContext(), "login", usuario);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnCadastrar:
                Intent intent = new Intent(this, CadastrarUsuario.class);
                startActivity(intent);
                break;
        }
    }
}