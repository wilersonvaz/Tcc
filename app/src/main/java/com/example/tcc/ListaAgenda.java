package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tcc.Adapter.AdapterListaAgenda;
import com.example.tcc.Model.Agenda;
import com.example.tcc.Model.Animal;
import com.example.tcc.Model.Usuario;
import com.example.tcc.ModelDAO.UsuarioDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaAgenda extends AppCompatActivity {
    public static ArrayList<Agenda> listaAgenda = new ArrayList<>(); //Importante preencher essa lista, ela quem vai carregar os animais no menu, esta sendo preenchida no login
    RecyclerView idListaAgenda;
    EditText idTxtPesquisaAgenda;
    ImageView idPesquisaAgenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_agenda);

        idTxtPesquisaAgenda = findViewById(R.id.idTxtPesquisaAgenda);

        idPesquisaAgenda = findViewById(R.id.idPesquisaAgenda);

        if (getIntent() != null && getIntent().hasExtra("servico")) {

            Bundle bundle = getIntent().getExtras();

            idListaAgenda = findViewById(R.id.idListaAgenda);

            idTxtPesquisaAgenda.setText("");

            if(!MainActivity.pesquisa.equals("")){
                idTxtPesquisaAgenda.setText(MainActivity.pesquisa);
            }

            AdapterListaAgenda adapterListaAgenda = new AdapterListaAgenda(ListaAgenda.listaAgenda, getApplicationContext());

            idListaAgenda.setAdapter(adapterListaAgenda);
            idListaAgenda.setLayoutManager(new LinearLayoutManager(this));

            idPesquisaAgenda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MainActivity.pesquisa = idTxtPesquisaAgenda.getText().toString();

                    Animal animal = new Animal();
                    animal.setIdAnimal(MainActivity.idAnimal);

                    Agenda agenda = new Agenda();
                    agenda.setServico(bundle.getString("servico"));
                    agenda.setPesquisa(idTxtPesquisaAgenda.getText().toString());

                    Usuario usuario = new Usuario(
                            MainActivity.idUsuario,
                            animal,
                            agenda
                    );

//                    ListaAgenda.listaAgenda.clear();

                    UsuarioDAO usuarioDao = new UsuarioDAO("usuario");
                    usuarioDao.pesquisaAgenda(getApplicationContext(), "pesquisaAgenda", usuario);

                    AdapterListaAgenda adapterListaAgenda = new AdapterListaAgenda(ListaAgenda.listaAgenda, getApplicationContext());

                }
            });


//            idPesquisaAgenda.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//
//                }
//            });




            FloatingActionButton idAdicionaItemAgenda = findViewById(R.id.idAdicionaItemAgenda);
            idAdicionaItemAgenda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bundle.putString("servico", bundle.getString("servico"));
                    bundle.putInt("idUsuario", bundle.getInt("idUsuario"));
                    bundle.putInt("idAnimal", bundle.getInt("idAnimal"));
                    Intent intent = new Intent(getApplicationContext(), CadastrarAgenda.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameTopo, new menu_topo());
        fragmentTransaction.commit();
    }
}