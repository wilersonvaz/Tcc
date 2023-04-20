package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tcc.Model.Agenda;
import com.example.tcc.Model.Animal;
import com.example.tcc.Model.Mask;
import com.example.tcc.Model.WebService;
import com.example.tcc.ModelDAO.AgendaDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CadastrarAgenda extends AppCompatActivity {
    TextView tituloAgenda, txtDifIntervalo, txtQtdeAgenda;
    EditText edtTituloAgenda, edtObservacao, edtQtdeAgenda, edtDataAgenda, edtHorario, edtDifIntervalo;
    String[] arrayIntervalo = {"Horas", "Dias", "Semana"};
//    Spinner  edtIntervalo;
    Button btnCadastrar;
    Integer intervalo;
    RadioButton edtUnica, edtHoras, edtDias, edtSemanas;
    RadioGroup edtIntervalo;
    String servico = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_agenda);

        tituloAgenda = findViewById(R.id.tituloAgenda);
        edtIntervalo = findViewById(R.id.edtIntervalo);
        edtUnica = findViewById(R.id.edtUnica);
        edtHoras = findViewById(R.id.edtHoras);
        edtDias = findViewById(R.id.edtDias);
        edtSemanas = findViewById(R.id.edtSemanas);
        edtDataAgenda = findViewById(R.id.edtDataAgenda);
        edtHorario = findViewById(R.id.edtHorario);
        edtTituloAgenda = findViewById(R.id.edtTituloAgenda);
        edtObservacao = findViewById(R.id.edtObservaçao);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        edtDifIntervalo = findViewById(R.id.edtDifIntervalo);
        txtDifIntervalo = findViewById((R.id.txtDifIntervalo));
        edtQtdeAgenda = findViewById(R.id.edtQtdeAgenda);
        txtQtdeAgenda = findViewById((R.id.txtQtdeAgenda));

        edtDataAgenda.addTextChangedListener(Mask.insert("##/##/####", edtDataAgenda));
//        edtHorario.addTextChangedListener(Mask.insert("##:##", edtHorario));

        if (getIntent() != null && getIntent().hasExtra("servico")) {
            Bundle bundle = getIntent().getExtras();
            tituloAgenda.setText("Cadastrar "+bundle.getString("servico"));
            servico = bundle.getString("servico");

            btnCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Animal animal = new Animal(MainActivity.idAnimal);
                        if(intervalo == 1){
                            Agenda agenda = new Agenda(
                                    bundle.getString("servico").toString(),
                                    edtTituloAgenda.getText().toString(),
                                    edtObservacao.getText().toString(),
                                    intervalo,
                                    edtDataAgenda.getText().toString(),
                                    edtHorario.getText().toString(),
                                    animal
                            );
                            AgendaDAO agendaDAO = new AgendaDAO("agenda");
                            agendaDAO.cadastrarAgenda(getApplicationContext(), "cadastrarAgenda", agenda);
                        }else{
                            Agenda agenda = new Agenda(
                                    bundle.getString("servico").toString(),
                                    edtTituloAgenda.getText().toString(),
                                    edtObservacao.getText().toString(),
                                    intervalo,
                                    Integer.parseInt(edtDifIntervalo.getText().toString()),
                                    Integer.parseInt(edtQtdeAgenda.getText().toString()),
                                    edtDataAgenda.getText().toString(),
                                    edtHorario.getText().toString(),
                                    animal
                            );
                            AgendaDAO agendaDAO = new AgendaDAO("agenda");
                            agendaDAO.cadastrarAgenda(getApplicationContext(), "cadastrarAgenda", agenda);
                        }

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameTopo, new menu_topo());
        fragmentTransaction.commit();
    }

    public void onClick(View v) {
        try{

            if(edtUnica.isChecked()){
                intervalo = 1;
                txtDifIntervalo.setVisibility(View.GONE);
                edtDifIntervalo.setVisibility(View.GONE);
                txtQtdeAgenda.setVisibility(View.GONE);
                edtQtdeAgenda.setVisibility(View.GONE);
            }else{
                if(edtHoras.isChecked()){
                    intervalo = 2;
                    txtDifIntervalo.setText("Diferença de horas entre as atividades");
                    txtQtdeAgenda.setText("Será repetido por quantos dias");
//                    edtQtdeAgenda.setHint("Digite o numero de dias que voce repetira essa atividade com o seu pet");
                }

                if(edtDias.isChecked()){
                    intervalo = 3;
                    txtDifIntervalo.setText("Diferença de dias entre as atividades");
//                    edtDifIntervalo.setHint("Digite o numero de dias entre as atividades com o seu pet");
                    txtQtdeAgenda.setText("Será repetido por quantos dias");
//                    edtQtdeAgenda.setHint("Digite o numero de dias que voce repetira essa atividade com o seu pet");
                }

                if(edtSemanas.isChecked()){
                    intervalo = 4;
                    txtDifIntervalo.setText("Diferença entre as semanas");
//                    edtDifIntervalo.setHint("Digite o numero de semanas entre as atividades com o seu pet");
                    txtQtdeAgenda.setText("Repetido em quantas semanas");
//                    edtQtdeAgenda.setHint("Digite o numero de semanas que voce repetira essa atividade com o seu pet");
                }
                txtDifIntervalo.setVisibility(View.VISIBLE);
                edtDifIntervalo.setVisibility(View.VISIBLE);
                txtQtdeAgenda.setVisibility(View.VISIBLE);
                edtQtdeAgenda.setVisibility(View.VISIBLE);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}