package com.example.tcc.ModelDAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
                                        MainActivity.idAnimal = idAnimal;
                                        MainActivity.pets.add(new Animal(idAnimal, usuario.getAnimal().getNome(), usuario.getAnimal().getImagemPet(), usuario.getAnimal().getResumo()));
//                                        usuarioDao.loadMainInfos(ctx, "loadPetInfo", usu);
                                        MainActivity.idAnimal = idAnimal;
                                        MainActivity.nomePet = usuario.getAnimal().getNome();
                                        MainActivity.imagemPet = usuario.getAnimal().getImagemPet();
                                        MainActivity.resumo = usuario.getAnimal().getResumo();

                                        Toast.makeText(ctx, " cadastrado com sucesso", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(ctx, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        ctx.startActivity(intent);
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

}
