package com.example.tcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tcc.Model.Agenda;
import com.example.tcc.Model.Animal;
import com.example.tcc.Model.Usuario;
import com.example.tcc.ModelDAO.UsuarioDAO;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static int idUsuario = -1;
    public static int idAnimal = -1;
    public static int idAgenda = -1;
    public static String nomeUsuario = "";
    public static String nomePet = "";
    public static String imagemPet = "";
    public static String resumo = "";
    public static String pesquisa = "";
    public static ArrayList<Animal> pets = new ArrayList<>(); //Importante preencher essa lista, ela quem vai carregar os animais no menu, esta sendo preenchida no login
    public static ImageView user_profile_photo;
    ImageView drop_down_option_menu;
    public static TextView idPetName;
    public static TextView idPetShortBio;
    TextView idDono;
    EditText edtPesquisar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(idUsuario < 0){
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }else{
            //Esse trecho ficava logo depois do super no começo da pagina, foi trazida para ca para nao carregar a main antes do login
            setContentView(R.layout.activity_main);

            idPetName = findViewById(R.id.idPetName);
            idPetShortBio = findViewById(R.id.idPetShortBio);

            idDono = findViewById(R.id.idDono);

            user_profile_photo = findViewById(R.id.user_profile_photo);

            if(idAnimal > 0){

                Animal animal = new Animal();
                Usuario usuario = new Usuario();

                animal.setIdAnimal(idAnimal);
                animal.setNome(nomePet);
                animal.setImagemPet(imagemPet);
                animal.setResumo(resumo);
                usuario.setAnimal(animal);

                changePet(usuario);
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameTopo, new menu_topo());
            fragmentTransaction.commit();
        }
    }

    public void changePet(Usuario usuario) {
        try{

            String imagem = usuario.getAnimal().getImagemPet();
            idPetName.setText(usuario.getAnimal().getNome());
            idPetShortBio.setText(usuario.getAnimal().getResumo());

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            URL url = new URL(usuario.getAnimal().getImagemPet());

            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(imagem).getContent());

            user_profile_photo.setImageBitmap(bitmap);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onClick(View view){
        TextView clickedTextView = (TextView) view;
//        Toast.makeText(getApplicationContext(), "Clicou em :"+clickedTextView.getText().toString(), Toast.LENGTH_LONG).show();

        Bundle bundle = new Bundle();
        bundle.putString("agenda", clickedTextView.getText().toString());
        bundle.putInt("idUsuario", MainActivity.idUsuario);

        Animal animal = new Animal();
        animal.setIdAnimal(MainActivity.idAnimal);

        Agenda agenda = new Agenda();
        agenda.setServico(bundle.getString("agenda"));

        Usuario usuario = new Usuario(
                MainActivity.idUsuario,
                animal,
                agenda
        );

        UsuarioDAO usuarioDao = new UsuarioDAO("usuario");
        usuarioDao.listaAgenda(getApplicationContext(), "listaAgenda", usuario);

    }

}