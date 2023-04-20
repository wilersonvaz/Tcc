package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tcc.Adapter.AdapterListaPets;
import com.example.tcc.Model.Animal;
import com.example.tcc.Model.Usuario;
import com.example.tcc.ModelDAO.UsuarioDAO;

import java.util.ArrayList;

public class ListaPets extends AppCompatActivity {
    RecyclerView idListaPets;
    public static ArrayList<Usuario> listaPets = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pets);

        idListaPets = findViewById(R.id.idListaPets);

        Usuario usuario = new Usuario(
              MainActivity.idUsuario
        );

        System.out.println("MainActivity.pets: "+String.valueOf(MainActivity.pets.size()));
        AdapterListaPets adapterListaPets = new AdapterListaPets(MainActivity.pets);

        idListaPets.setAdapter(adapterListaPets);
        idListaPets.setLayoutManager(new LinearLayoutManager(this));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameTopo, new menu_topo());
        fragmentTransaction.commit();
    }
}