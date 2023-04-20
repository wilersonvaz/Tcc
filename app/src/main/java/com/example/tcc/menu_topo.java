package com.example.tcc;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tcc.Model.Animal;
import com.example.tcc.Model.Usuario;
import com.example.tcc.ModelDAO.UsuarioDAO;

public class menu_topo extends Fragment {

    ImageView change_pet, drop_down_option_menu;
    TextView idDono;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menu_topo, container, false);

        drop_down_option_menu = rootView.findViewById(R.id.drop_down_option_menu);
        change_pet = rootView.findViewById(R.id.change_pet);
        idDono = rootView.findViewById(R.id.idDono);

        idDono.setText("Olá, "+MainActivity.nomeUsuario+"!");

        drop_down_option_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(getContext(), view);
                menu.getMenu().add("Home");
//                menu.getMenu().add("Meu perfil");
//                MenuCompat.setGroupDividerEnabled(menu, true);
//                menu.getMenu().add("------------------------------").setEnabled(false);
                menu.getMenu().add("Ver pets cadastrados");
                menu.getMenu().add("Cadastrar novo pet");
                menu.getMenu().add("Sair"); // menus items
                menu.show();

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if(menuItem.getTitle().toString().equals("Home")) {
                            Bundle bundle = new Bundle();
//                            bundle.putInt("idUsuario", MainActivity.idUsuario);
                            bundle.putString("nomeUsuario", MainActivity.nomeUsuario);
                            bundle.putString("nomePet", MainActivity.nomePet);
                            bundle.putString("imagemPet", MainActivity.imagemPet);
                            bundle.putString("resumo", MainActivity.resumo);

                            Intent home = new Intent(getContext(), MainActivity.class);
                            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            home.putExtras(bundle);
                            startActivity(home);
                        }else if(menuItem.getTitle().toString().equals("Ver pets cadastrados")){
                            Intent intent = new Intent(getContext(), ListaPets.class);
                            startActivity(intent);
                        }else if(menuItem.getTitle().toString().equals("Cadastrar novo pet")) {
                            Intent cadastrarPet = new Intent(getContext(), CadastrarAnimal.class);
                            startActivity(cadastrarPet);
                        }else if(menuItem.getTitle().toString().equals("Sair")) {
                            MainActivity.idUsuario = -1;
                            MainActivity.nomeUsuario = "";
                            MainActivity.pets.clear();
                            ListaAgenda.listaAgenda.clear();
                            Intent login = new Intent(getContext(), LoginActivity.class);
                            startActivity(login);
                        }
                        return false;
                    }
                });
            }
        });

        change_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu menu = new PopupMenu(getContext(), view);

                for(int i = 0; i < MainActivity.pets.size(); i++){
//                    menu.getMenu().removeItem(i);
                    menu.getMenu().add(1, MainActivity.pets.get(i).getIdAnimal(), i, MainActivity.pets.get(i).getNome().toString());
                }

                menu.show();

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Animal animal = new Animal(menuItem.getItemId());
                        Usuario usuario = new Usuario(MainActivity.idUsuario, animal);
                        UsuarioDAO usuarioDAO = new UsuarioDAO("usuario");
                        usuarioDAO.loadPetInfo(getContext(),"loadPetInfo", usuario);
                        return false;
                    }
                });
            }
        });

        return rootView;
    }
}