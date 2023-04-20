package com.example.tcc.ModelDAO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tcc.LoginActivity;
import com.example.tcc.MainActivity;
import com.example.tcc.Model.Agenda;
import com.example.tcc.Model.Animal;
import com.example.tcc.Model.Usuario;
import com.example.tcc.Model.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AgendaDAO extends WebService {
    public AgendaDAO(String index) {
        super(index);
    }

    public void cadastrarAgenda(Context ctx, String acao, Agenda agenda) {
        try{
            RequestQueue queue = Volley.newRequestQueue(ctx);

            WebService webService = new WebService("agenda");

            String url = webService.urlWebService();

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            System.out.println("Response: "+ response);

                            try {
                                if(webService.isJSONValid(response)){
                                    JSONObject jsonResponse = new JSONObject(response);

//                                    int idAgenda = Integer.parseInt( jsonResponse.getString("response") ) ;
//                                    if(idAgenda > 0){
//                                        Toast.makeText(ctx, "Dados inseridos com sucesso!", Toast.LENGTH_LONG).show();
//                                    }

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
                        params.put("servico", agenda.getServico());
                        params.put("titulo", agenda.getTitulo());
                        params.put("observacao", agenda.getObservacao());
                        params.put("intervalo", String.valueOf(agenda.getIntervalo()));
                        params.put("diferenca", String.valueOf(agenda.getDiferencaAgenda()));
                        params.put("qtdeDias", String.valueOf(agenda.getQtdeAgenda()));
                        params.put("dataAgenda", agenda.getDataAgenda());
                        params.put("horario", agenda.getHorarioAgenda());
                        params.put("idAnimal", String.valueOf( agenda.getAnimal().getIdAnimal()) );
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

    public void concluirCompromisso(Context ctx, String acao, Agenda agenda) {
        try{
            RequestQueue queue = Volley.newRequestQueue(ctx);

            WebService webService = new WebService("agenda");

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

                                    int resp = Integer.parseInt( jsonResponse.getString("response") ) ;
                                    if(resp > 0){
                                        Toast.makeText(ctx, "Compromisso concluido com sucesso!", Toast.LENGTH_LONG).show();
                                    }

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
                        params.put("idAgenda", String.valueOf( agenda.getIdAgenda()) );
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

    public void excluirCompromisso(Context ctx, String acao, Agenda agenda) {
        try{
            RequestQueue queue = Volley.newRequestQueue(ctx);

            WebService webService = new WebService("agenda");

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

                                    int resp = Integer.parseInt( jsonResponse.getString("response") ) ;
                                    if(resp > 0){
                                        Toast.makeText(ctx, "Compromisso excluido com sucesso!", Toast.LENGTH_LONG).show();
                                    }

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
                        params.put("idAgenda", String.valueOf( agenda.getIdAgenda()) );
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
