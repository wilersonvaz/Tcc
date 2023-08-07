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
import com.example.tcc.CadastrarAgenda;
import com.example.tcc.ListaAgenda;
import com.example.tcc.MainActivity;
import com.example.tcc.Model.Agenda;
import com.example.tcc.Model.Animal;
import com.example.tcc.Model.Usuario;
import com.example.tcc.Model.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UsuarioDAO extends WebService {

    public UsuarioDAO(String index) {
        super(index);
    }

    public void login(Context ctx, String acao, Usuario usuario) {

        RequestQueue queue = Volley.newRequestQueue(ctx);

        String url = this.urlWebService();

//        System.out.println(url);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {

                    try {
//                        System.out.println("Response :"+response);
                        if(!response.equals("")){
                            JSONArray jsonArray = new JSONArray(response);

                            ArrayList<String> pets = new ArrayList<>();
                            Usuario usu = new Usuario();

                            int idUsuario = -1;
                            int idAnimal = -1;
                            String nomeUsuario = "";
                            String nomePet = "";
                            String imagemPet = "";
                            String sexo = "";
                            String resumo = "";
                            String dataNascimentoPet = "";

                            for(int i =0; i < jsonArray.length(); i++){
                                Animal animal = new Animal();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                idUsuario = Integer.parseInt(jsonObject.getString("idUsuario"));
                                nomeUsuario = jsonObject.getString("nomeUsuario");
                                if(!jsonObject.getString("idAnimal").equals("null")){idAnimal = Integer.parseInt( jsonObject.getString("idAnimal"));
                                    nomePet = jsonObject.getString("nomePet");
                                    imagemPet = jsonObject.getString("imagem");
                                    resumo = jsonObject.getString("resumo");
                                    dataNascimentoPet = jsonObject.getString("dataNascimento");

                                    //Importante preencher essa lista, ela quem vai carregar os animais no menu

                                    MainActivity.pets.add(new Animal(idAnimal, nomePet, imagemPet, sexo, dataNascimentoPet, resumo));
                                }else{
                                    MainActivity.pets.add((new Animal("Nenhum animal cadastrado!")));
                                }
                            }

                            MainActivity.idUsuario = idUsuario;
                            MainActivity.idAnimal = idAnimal;
                            MainActivity.nomeUsuario = nomeUsuario;
                            MainActivity.nomePet = nomePet;
                            MainActivity.imagemPet = imagemPet;
                            MainActivity.resumo = resumo;

                            Intent intent = new Intent(ctx, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);
                        }else{
                            Toast.makeText(ctx, "Senha ou usuário errado!", Toast.LENGTH_LONG).show();
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
                    System.out.println("Error.Response: "+ String.valueOf(error));
//                    Log.d("Error.Response", String.valueOf(error));
                }
            }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                try {
                    params.put("acao", acao);
                    params.put("email", usuario.getEmail());
                    params.put("senha", usuario.getSenha());
                    params.put("caminhoImagem", WebService.servidor);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void loadPetInfo(Context ctx, String acao, Usuario usuario) {
        RequestQueue queue = Volley.newRequestQueue(ctx);

        String url = this.urlWebService();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            System.out.println("Response na loadPetInfo: "+response);

                            JSONArray jsonArray = new JSONArray(response);

                            Usuario usu = new Usuario();

                            int idUsuario = -1;
                            int idAnimal = -1;
                            String nomeUsuario = "";
                            String nomePet = "";
                            String imagemPet = "";
                            String resumo = "";

                            ArrayList<Animal> an = new ArrayList<>();

                            for(int i =0; i < jsonArray.length(); i++){
                                Animal animal = new Animal();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                idUsuario = Integer.parseInt(jsonObject.getString("idUsuario"));
                                nomeUsuario = jsonObject.getString("nomeUsuario");
                                nomePet = jsonObject.getString("nomePet");
                                imagemPet = jsonObject.getString("imagem");
                                resumo = jsonObject.getString("resumo");

                                if(!jsonObject.getString("idAnimal").equals("null")){
                                    if (idAnimal != Integer.parseInt( jsonObject.getString("idAnimal")) ){
                                        idAnimal = Integer.parseInt( jsonObject.getString("idAnimal"));
                                    }
                                }
                            }

                            MainActivity.idUsuario = idUsuario;
                            MainActivity.idAnimal = idAnimal;
                            MainActivity.nomeUsuario = nomeUsuario;
                            MainActivity.nomePet = nomePet;
                            MainActivity.imagemPet = imagemPet;
                            MainActivity.resumo = resumo;

                            Intent intent = new Intent(ctx, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);

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
//                    if(acao.equals("login")){
//                        params.put("email", usuario.getEmail());
//                        params.put("senha", usuario.getSenha());
//                    }else if(acao.equals("loadPetInfo")){
                        params.put("idUsuario", String.valueOf(usuario.getIdUsuario()));
                        params.put("idAnimal", String.valueOf(usuario.getAnimal().getIdAnimal()));
//                    }else if(acao.equals("listaAgenda")){
//                        params.put("idUsuario", String.valueOf(usuario.getIdUsuario()));
//                        params.put("idAnimal", String.valueOf(usuario.getAnimal().getIdAnimal()));
//                        params.put("servico", String.valueOf(usuario.getAgenda().getServico()));
////                        System.out.println("Na LoadMainfo recebendo:"+String.valueOf(usuario.getIdUsuario())+" - "+String.valueOf(usuario.getAnimal().getIdAnimal())+" - "+usuario.getAgenda().getServico());
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void listaAgenda(Context ctx, String acao, Usuario usuario) {
        RequestQueue queue = Volley.newRequestQueue(ctx);

        String url = this.urlWebService();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("Response: "+response);

                            Bundle bundle = new Bundle();
                            bundle.putString("servico", usuario.getAgenda().getServico());

                            if(!response.equals("")){
                                JSONArray jsonArray = new JSONArray(response);

                                Usuario usu = new Usuario();

                                int idUsuario = -1;
                                int idAnimal = -1;
                                int idAgenda = -1;
                                String nomeUsuario = "";
                                String nomePet = "";
                                String imagemPet = "";
                                String resumo = "";

                                ArrayList<Animal> an = new ArrayList<>();

                                ListaAgenda.listaAgenda.clear();

                                String servico = "";

                                for(int i =0; i < jsonArray.length(); i++){
                                    Animal animal = new Animal();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    idUsuario = Integer.parseInt(jsonObject.getString("idUsuario"));
                                    idAnimal = Integer.parseInt(jsonObject.getString("idAnimal"));
                                    nomePet = jsonObject.getString("nomePet");

                                    if(!jsonObject.getString("idAgenda").equals("null")){
                                        if(idAgenda != Integer.parseInt(jsonObject.getString("idAgenda"))){
                                            idAgenda = Integer.parseInt(jsonObject.getString("idAgenda"));
                                            servico = jsonObject.get("servico").toString();
                                            String titulo = jsonObject.getString("titulo").toString();
                                            String observacao = jsonObject.getString("observacao").toString();
                                            String dataAgenda = jsonObject.getString("dataAgenda").toString();
                                            String horario = jsonObject.getString("horario").toString();
                                            Integer status = Integer.parseInt(jsonObject.getString("status"));
                                            animal.setIdAnimal(idAnimal);
                                            animal.setNome(jsonObject.getString("nomePet").toString());

                                            ListaAgenda.listaAgenda.add(new Agenda(idAgenda, servico, titulo, observacao, dataAgenda, horario, status, animal));
                                        }
                                    }
                                }

                                if(ListaAgenda.listaAgenda.size() > 0){
                                    Intent intent = new Intent(ctx, ListaAgenda.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtras(bundle);
                                    ctx.startActivity(intent);
                                }
                            }else{
                                Intent intent = new Intent(ctx, CadastrarAgenda.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtras(bundle);
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
                System.out.println("Passando "+acao+" na listaAgenda");
                Map<String, String>  params = new HashMap<String, String>();
                try {
                    params.put("acao", acao);
                        params.put("idUsuario", String.valueOf(MainActivity.idUsuario));
                        params.put("idAnimal", String.valueOf(MainActivity.idAnimal));
                        params.put("servico", String.valueOf(usuario.getAgenda().getServico()));
////                        System.out.println("Na LoadMainfo recebendo:"+String.valueOf(usuario.getIdUsuario())+" - "+String.valueOf(usuario.getAnimal().getIdAnimal())+" - "+usuario.getAgenda().getServico());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void loadMainInfos(Context ctx, String acao, Usuario usuario) {
        RequestQueue queue = Volley.newRequestQueue(ctx);

        String url = this.urlWebService();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("Response: "+response);
//                            ProgressDialog progressDialog;
//                            progressDialog = new ProgressDialog(ctx);
//                            progressDialog.setMessage("Por favor espere!");
//                            //    	progressDialog.setCancelable(false);
//                            progressDialog.show();

                            JSONArray jsonArray = new JSONArray(response);

                            Usuario usu = new Usuario();

                            int idUsuario = -1;
                            int idAnimal = -1;
                            int idAgenda = -1;
                            String nomeUsuario = "";
                            String nomePet = "";
                            String imagemPet = "";
//                            String sexoPet = "";
//                            String dataNascimentoPet = "";
                            String resumo = "";

                            ArrayList<Animal> an = new ArrayList<>();

                            for(int i =0; i < jsonArray.length(); i++){
                                Animal animal = new Animal();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                idUsuario = Integer.parseInt(jsonObject.getString("idUsuario"));
                                nomeUsuario = jsonObject.getString("nomeUsuario");

                                nomePet = jsonObject.getString("nomePet");
                                imagemPet = jsonObject.getString("imagem");
//                                sexoPet = jsonObject.getString("sexo");
//                                dataNascimentoPet = jsonObject.getString("dataNascimento");
                                resumo = jsonObject.getString("resumo");

//                                System.out.println(jsonObject.getString("sql"));

//                                if(!jsonObject.getString("idAgenda").equals("null") && acao.equals("listaAgenda")){
//                                    if(idAgenda != Integer.parseInt(jsonObject.getString("idAgenda"))){
//                                        idAgenda = Integer.parseInt(jsonObject.getString("idAgenda"));
//                                        String servico = jsonObject.get("servico").toString();
//                                        String titulo = jsonObject.getString("titulo").toString();
//                                        String observacao = jsonObject.getString("observacao").toString();
//                                        String dataAgenda = jsonObject.getString("dataAgenda").toString();
//                                        String horario = jsonObject.getString("horario").toString();
//                                        animal.setIdAnimal(idAnimal);
//                                        animal.setNome(jsonObject.getString("nomePet").toString());
//
//                                        System.out.println("Servico no adapter: "+servico);
//
//                                        ListaAgenda.listaAgenda.add(new Agenda(idAgenda, servico, jsonObject.get("titulo").toString(), observacao, dataAgenda, horario, animal));
//                                    }
//                                }

                                if(!jsonObject.getString("idAnimal").equals("null")){
                                    if (idAnimal != Integer.parseInt( jsonObject.getString("idAnimal")) ){
                                        idAnimal = Integer.parseInt( jsonObject.getString("idAnimal"));
//                                        if(!acao.equals("loadPetInfo")){
                                            MainActivity.pets.add(new Animal(idAnimal, nomePet, imagemPet, resumo));
//                                        }else{
//                                            //Esse else vai popular a lista para quando for ver a lista no menu (Ver Pets Cadastrados
//                                            MainActivity.pets.add(new Animal(idAnimal, nomePet, imagemPet, sexoPet, dataNascimentoPet, resumo));
//                                        }
                                    }
                                }
                            }

                            Bundle bundle = new Bundle();
                            bundle.putString("nomeUsuario", nomeUsuario);
                            bundle.putString("nomePet", nomePet);
                            bundle.putString("imagemPet", imagemPet);
                            bundle.putString("resumo", resumo);

                            MainActivity.idUsuario = idUsuario;
                            MainActivity.idAnimal = idAnimal;
                            MainActivity.nomeUsuario = nomeUsuario;
                            MainActivity.nomePet = nomePet;
                            MainActivity.imagemPet = imagemPet;
                            MainActivity.resumo = resumo;

//                            if(acao != "listaAgenda"){
                                Intent intent = new Intent(ctx, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtras(bundle);
                                ctx.startActivity(intent);
//                            }



//                            progressDialog.dismiss();




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
                System.out.println("PAssando "+acao+" na LoadMainInfo");
                Map<String, String>  params = new HashMap<String, String>();
                try {
                    params.put("acao", acao);
//                    if(acao.equals("login")){
//                        params.put("email", usuario.getEmail());
//                        params.put("senha", usuario.getSenha());
//                    }else if(acao.equals("loadPetInfo")){
                        params.put("idUsuario", String.valueOf(usuario.getIdUsuario()));
                        params.put("idAnimal", String.valueOf(usuario.getAnimal().getIdAnimal()));
//                    }else if(acao.equals("listaAgenda")){
//                        params.put("idUsuario", String.valueOf(usuario.getIdUsuario()));
//                        params.put("idAnimal", String.valueOf(usuario.getAnimal().getIdAnimal()));
//                        params.put("servico", String.valueOf(usuario.getAgenda().getServico()));
////                        System.out.println("Na LoadMainfo recebendo:"+String.valueOf(usuario.getIdUsuario())+" - "+String.valueOf(usuario.getAnimal().getIdAnimal())+" - "+usuario.getAgenda().getServico());
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void pesquisaAgenda(Context ctx, String acao, Usuario usuario) {
        RequestQueue queue = Volley.newRequestQueue(ctx);

        String url = this.urlWebService();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("Response: "+response);

                            Bundle bundle = new Bundle();
                            bundle.putString("servico", usuario.getAgenda().getServico());

                            if(!response.equals("")){
                                JSONArray jsonArray = new JSONArray(response);

                                Usuario usu = new Usuario();

                                int idUsuario = -1;
                                int idAnimal = -1;
                                int idAgenda = -1;
                                String nomeUsuario = "";
                                String nomePet = "";
                                String imagemPet = "";
                                String resumo = "";

                                ArrayList<Animal> an = new ArrayList<>();

                                ListaAgenda.listaAgenda.clear();

                                String servico = "";

                                for(int i =0; i < jsonArray.length(); i++){
                                    Animal animal = new Animal();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    idUsuario = Integer.parseInt(jsonObject.getString("idUsuario"));
                                    idAnimal = Integer.parseInt(jsonObject.getString("idAnimal"));
                                    nomePet = jsonObject.getString("nomePet");

                                    if(!jsonObject.getString("idAgenda").equals("null")){
                                        if(idAgenda != Integer.parseInt(jsonObject.getString("idAgenda"))){
                                            idAgenda = Integer.parseInt(jsonObject.getString("idAgenda"));
                                            servico = jsonObject.get("servico").toString();
                                            String titulo = jsonObject.getString("titulo").toString();
                                            String observacao = jsonObject.getString("observacao").toString();
                                            String dataAgenda = jsonObject.getString("dataAgenda").toString();
                                            String horario = jsonObject.getString("horario").toString();
                                            Integer status = Integer.parseInt(jsonObject.getString("status"));
                                            animal.setIdAnimal(idAnimal);
                                            animal.setNome(jsonObject.getString("nomePet").toString());

                                            ListaAgenda.listaAgenda.add(new Agenda(idAgenda, servico, titulo, observacao, dataAgenda, horario, status, animal));
                                        }
                                    }
                                }

                                if(ListaAgenda.listaAgenda.size() > 0){
                                    Intent intent = new Intent(ctx, ListaAgenda.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtras(bundle);
                                    ctx.startActivity(intent);
                                }
                            }else{
                                Toast.makeText(ctx, "Sua pesquisa nao retornou resultados!", Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(ctx, CadastrarAgenda.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                intent.putExtras(bundle);
//                                ctx.startActivity(intent);
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
                    params.put("idUsuario", String.valueOf(MainActivity.idUsuario));
                    params.put("idAnimal", String.valueOf(MainActivity.idAnimal));
                    params.put("servico", String.valueOf(usuario.getAgenda().getServico()));
                    params.put("pesquisa", usuario.getAgenda().getPesquisa());
////                        System.out.println("Na LoadMainfo recebendo:"+String.valueOf(usuario.getIdUsuario())+" - "+String.valueOf(usuario.getAnimal().getIdAnimal())+" - "+usuario.getAgenda().getServico());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        queue.add(postRequest);
    }
}
