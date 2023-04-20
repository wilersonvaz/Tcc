package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tcc.AsyncTasks.CadastraAnimalTask;
import com.example.tcc.AsyncTasks.CadastraUsuarioTask;
import com.example.tcc.Model.Animal;
import com.example.tcc.Model.Mask;
import com.example.tcc.Model.Usuario;
import com.example.tcc.Model.WebService;
import com.example.tcc.ModelDAO.AnimalDAO;
import com.example.tcc.ModelDAO.UsuarioDAO;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class CadastrarAnimal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText edtNome, edtDataNascimento, edtDataAdocao, edtPeso, edtCor, edtNotas;
    Spinner edtSexo, edtRaca, edtEspecie;
    String[] arrayEspecie = {"Canino", "Felino","Réptil", "Suíno"};
    String[] arraySexoAnimal = {"Masculino","Feminino"};
    String[] arrayRacaCanina = {"Vira-latas","Poodle", "Beagle", "Chihuahua"};
    Button btnCadastrar;
    ImageView edtImagemPet;
    private Bitmap bitmap;

    String sexo = "Masculino";
    String especie = "Canino";
    String arquivo = "";
    String raca = "Vira-latas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_animal);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameTopo, new menu_topo());
        fragmentTransaction.commit();

        edtImagemPet = findViewById(R.id.edtImagemPet);
        edtEspecie = findViewById(R.id.edtEspecie);
        edtNome = findViewById(R.id.edtNome);
        edtDataNascimento = findViewById(R.id.edtDataNascimento);
        edtDataAdocao = findViewById(R.id.edtDataAdocao);
        edtRaca = findViewById(R.id.edtRaca);
        edtPeso = findViewById(R.id.edtPeso);
        edtCor = findViewById(R.id.edtCor);
        edtRaca = findViewById(R.id.edtRaca);
        btnCadastrar = findViewById(R.id.btnCadastrar);


        edtDataNascimento.addTextChangedListener(Mask.insert("##/##/####", edtDataNascimento));
        edtDataAdocao.addTextChangedListener(Mask.insert("##/##/####", edtDataAdocao));

        edtNotas = findViewById(R.id.edtNotas);
        edtSexo = (Spinner) findViewById(R.id.edtSexo);

        ArrayAdapter adpAnimal = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySexoAnimal);

        adpAnimal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        edtSexo.setAdapter(adpAnimal);

        edtSexo.setOnItemSelectedListener(this);

        ArrayAdapter adpEspecie = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayEspecie);

        adpEspecie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        edtEspecie.setAdapter(adpEspecie);

        edtEspecie.setOnItemSelectedListener(this);

        ArrayAdapter adpRaca = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayRacaCanina);

        edtRaca.setAdapter(adpRaca);

        edtRaca.setOnItemSelectedListener(this);

        edtDataNascimento.setText("01/01/2016");
        edtDataAdocao.setText("02/01/2016");
        edtPeso.setText("6");
        edtCor.setText("Branca");

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    Animal animal = new Animal(
                            imgToString(bitmap),
                            edtNome.getText().toString(),
                            especie,
                            edtDataNascimento.getText().toString(),
                            edtDataAdocao.getText().toString(),
                            edtPeso.getText().toString(),
                            edtCor.getText().toString(),
                            sexo,
                            edtNotas.getText().toString(),
                            raca,
                            "cadastrarAnimal"
                    );

                    Usuario usuario = new Usuario(
                            MainActivity.idUsuario,
                            animal
                    );

                    AnimalDAO animalDAO = new AnimalDAO("animal");
                    animalDAO.CadastrarAnimal(getApplicationContext(), "cadastrarAnimal", usuario);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private String imgToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgBytes, Base64.DEFAULT);

    }

    public void openFileChooser(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, requestcode);
    }

    int requestcode = 1;

    public void onActivityResult(int requestcode, int resultCode, Intent data){
        super.onActivityResult(requestcode, resultCode, data);


        Context context = getApplicationContext();
        if(requestcode == requestcode && resultCode == Activity.RESULT_OK){
            if(data == null){
                return;
            }

            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    //Verifica permisos para android 6.0+
                    int permissionCheck = ContextCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
                    }
                }

                Uri uri = data.getData();

                String filePath = getFilePath(context,uri);

                String fileName = getFileName(uri);

                arquivo = filePath +"/"+fileName;

                bitmap = BitmapFactory.decodeFile(arquivo);

                edtImagemPet.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    public static String getFilePath(Context context, Uri uri) {

        String result = null;
        try{

            Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);

            result = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();


        }catch(Exception e){
            e.printStackTrace();
        }

        return result;

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.edtRaca:
                raca = arrayRacaCanina[i];
                break;
            case R.id.edtSexo:
                sexo = arraySexoAnimal[i];
                break;
            case R.id.edtEspecie:
                especie = arrayEspecie[i];
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}