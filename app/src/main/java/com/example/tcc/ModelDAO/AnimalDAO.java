package com.example.tcc.ModelDAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tcc.CadastrarAnimal;
import com.example.tcc.MainActivity;
import com.example.tcc.Model.Animal;
import com.example.tcc.Model.Usuario;
import com.example.tcc.Model.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnimalDAO extends WebService {

    public AnimalDAO(String index) {
        super(index);
    }

    public void CadastrarAnimal(Context ctx, String acao, Usuario usuario){
        try{
            RequestQueue queue = Volley.newRequestQueue(ctx);

            String url = this.urlWebService();

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            try{
//                                System.out.println("Response: "+response);
                                if(isJSONValid(response)) {
                                    JSONObject jsonResponse = new JSONObject(response);

//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("nomeUsuario", usuario.getNome());
//                                    bundle.putString("nomePet", usuario.getAnimal().getNome());
//                                    bundle.putString("imagemPet", usuario.getAnimal().getImagemPet());
//                                    bundle.putString("resumo", usuario.getAnimal().getNotas());

//                                    Intent intent = new Intent(ctx, MainActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                                    intent.putExtras(bundle);
//                                    ctx.startActivity(intent);

                                    int idAnimal = Integer.parseInt( jsonResponse.getString("response") ) ;
                                    if(idAnimal > 0){
                                        Animal animal = new Animal(idAnimal);
                                        Usuario usu = new Usuario(MainActivity.idUsuario, animal);
                                        UsuarioDAO usuarioDao = new UsuarioDAO("usuario");
                                        for( int i = 0; i < MainActivity.pets.size(); i++){
                                            System.out.println(MainActivity.pets.get(i).getNome());
                                            if(MainActivity.pets.get(i).getNome().equals("Nenhum animal cadastrado!")){
                                                MainActivity.pets.remove(i);
                                            }
                                        }


                                        MainActivity.idAnimal = idAnimal;
                                        MainActivity.pets.add(new Animal(idAnimal, usuario.getAnimal().getNome(), usuario.getAnimal().getImagemPet(), usuario.getAnimal().getResumo()));
//                                        usuarioDao.loadMainInfos(ctx, "loadPetInfo", usu);
                                        MainActivity.idAnimal = idAnimal;
                                        MainActivity.nomePet = usuario.getAnimal().getNome();
                                        MainActivity.imagemPet = usuario.getAnimal().getImagemPet();
                                        MainActivity.resumo = usuario.getAnimal().getResumo();

                                        Toast.makeText(ctx, " Cadastrado com sucesso", Toast.LENGTH_LONG).show();

//                                        Intent intent = new Intent(ctx, MainActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        ctx.startActivity(intent);
                                    }else{
                                        Toast.makeText(ctx, "Ocorreu um erro ao cadastrar o pet!", Toast.LENGTH_LONG).show();
                                    }
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    },new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            System.out.println("Error.Response"+ String.valueOf(error));
                            Log.d("Error.Response", String.valueOf(error));
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    try {

                        params.put("acao", acao);
                        params.put("idUsuario", String.valueOf(MainActivity.idUsuario));
                        params.put("especie", usuario.getAnimal().getEspecie());
                        params.put("nome", usuario.getAnimal().getNome());
                        params.put("dataNascimento", usuario.getAnimal().getDataNascimento());
                        params.put("dataAdocao", usuario.getAnimal().getDataAdocao());
                        params.put("peso", usuario.getAnimal().getPeso());
                        params.put("cor", usuario.getAnimal().getCor());
                        params.put("sexo", usuario.getAnimal().getSexo());
                        params.put("notas", usuario.getAnimal().getNotas());
                        params.put("imagem", usuario.getAnimal().getImagemPet());
                        params.put("raca", usuario.getAnimal().getRaca());

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


    public void CarregaUpdate(Context ctx, View.OnClickListener onClickListener, String acao, Animal animal){
        try{
            RequestQueue queue = Volley.newRequestQueue(ctx);

            String url = this.urlWebService();

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            try{
                                if(!response.equals("")){
//                                    JSONObject jsonResponse = new JSONObject(response);
                                    JSONArray jsonArray = new JSONArray(response);

                                    int idAnimal = -1;
                                    String imagem = "";
                                    String nome = "";
                                    String especie = "";
                                    String dataNascimento = "";
                                    String dataAdocao = "";
                                    String peso = "";
                                    String sexo = "";
                                    String notas = "";
                                    String raca = "";


                                    if(jsonArray.length() > 0){
                                        for(int i =0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            idAnimal = Integer.parseInt(jsonObject.getString("idAnimal"));

                                            imagem = jsonObject.getString("imagem");
                                            nome = jsonObject.getString("nome");
                                            especie = jsonObject.getString("especie");
                                            dataNascimento = jsonObject.getString("dataNascimento");
                                            dataAdocao = jsonObject.getString("dataAdocao");
                                            peso = jsonObject.getString("peso");
                                            sexo = jsonObject.getString("sexo");
                                            notas = jsonObject.getString("notas");
                                            raca = jsonObject.getString("raca");
                                        }

                                        Bundle bundle = new Bundle();
                                        bundle.putInt("idAnimal", idAnimal);
                                        bundle.putString("imagem", imagem);
                                        bundle.putString("nome", nome);
                                        bundle.putString("especie", especie);
                                        bundle.putString("dataNascimento", dataNascimento);
                                        bundle.putString("dataAdocao", dataAdocao);
                                        bundle.putString("peso", peso);
                                        bundle.putString("sexo", sexo);
                                        bundle.putString("notas", notas);
                                        bundle.putString("raca", raca);


                                        CadastrarAnimal.idAnimal = idAnimal;
                                        Intent intent = new Intent(ctx, CadastrarAnimal.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtras(bundle);

                                        ctx.startActivity(intent);

                                    }else{
                                        Toast.makeText(ctx, "Nenhum animal encontrado com esse id!", Toast.LENGTH_LONG).show();
                                    }
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    },new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                    System.out.println("Error.Response"+ String.valueOf(error));
                    Log.d("Error.Response", String.valueOf(error));
                }
            }
            ){
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    try {
                        params.put("acao", acao);
                        params.put("idAnimal", String.valueOf(animal.getIdAnimal()));
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
