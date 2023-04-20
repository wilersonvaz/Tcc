package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tcc.AsyncTasks.CadastraUsuarioTask;
import com.example.tcc.Model.Mask;
import com.example.tcc.Model.Usuario;
import com.example.tcc.Model.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CadastrarUsuario extends AppCompatActivity {
    EditText edtNome, edtSobreNome, edtCpf, edtCelular, edtEmail,  edtDataNascimento, edtSenha;
    Button btnCadastrar;
    TextView idVoltarParaLogin;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        btnCadastrar = findViewById(R.id.btnCadastrar);

        edtNome = findViewById(R.id.edtNome);
        edtSobreNome = findViewById(R.id.edtSobreNome);
        edtCelular = findViewById(R.id.edtCelular);
        edtEmail = findViewById(R.id.edtEmail);
        edtCpf = findViewById(R.id.edtCpf);
        edtDataNascimento = findViewById(R.id.edtDataNascimento);
        edtSenha = findViewById(R.id.edtSenha);
        idVoltarParaLogin = findViewById(R.id.idVoltarParaLogin);

        edtCpf.addTextChangedListener(Mask.insert("###.###.###-##", edtCpf));
        edtCelular.addTextChangedListener(Mask.insert("(##)#####-####", edtCelular));
        edtDataNascimento.addTextChangedListener(Mask.insert("##/##/####", edtDataNascimento));

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Usuario usuario = new Usuario(
                            edtNome.getText().toString(),
                            edtSobreNome.getText().toString(),
                            edtCelular.getText().toString(),
                            edtEmail.getText().toString(),
                            edtCpf.getText().toString(),
                            edtDataNascimento.getText().toString(),
                            edtSenha.getText().toString(),
                            "cadastrarUsuario"
                    );

                    CadastrarUsuario(getApplicationContext(), "cadastrarUsuario", usuario);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        idVoltarParaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            }
        });
    }

    public void CadastrarUsuario(Context ctx, String acao, Usuario usuario){
        try{
            RequestQueue queue = Volley.newRequestQueue(ctx);

            WebService webService = new WebService("usuario");

            String url = webService.urlWebService();

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
//                            System.out.println("Response: "+ response);

                            try {
                                if(webService.isJSONValid(response)){
                                    JSONObject jsonResponse = new JSONObject(response);

                                    Intent intent = new Intent(ctx, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    ctx.startActivity(intent);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            System.out.println("Error.Response"+ String.valueOf(error));
                            Log.d("Error.Response", String.valueOf(error));
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    try {
                        params.put("acao", acao);
                        params.put("nome", usuario.getNome());
                        params.put("sobreNome", usuario.getSobreNome());
                        params.put("cpf", usuario.getCpf());
                        params.put("celular", usuario.getCelular());
                        params.put("dataNascimento", usuario.getDataNascimento());
                        params.put("email", usuario.getEmail());
                        params.put("senha", usuario.getSenha());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return params;
                }
            };
            queue.add(postRequest);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}