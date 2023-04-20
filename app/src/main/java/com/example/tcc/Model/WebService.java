package com.example.tcc.Model;

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
import com.example.tcc.CadastrarAnimal;
import com.example.tcc.LoginActivity;
import com.example.tcc.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WebService {
    private String url;
    private String index;
    private JSONObject jsonObject;
    private Usuario usuario;
    private Animal animal;
    public static String servidor = "http://192.168.57.1";
//        String servidor = "http://10.0.2.2";


    public WebService(String index) {
        this.index = index;
    }

    public WebService(String index, Usuario usuario) {
        this.index = index;
        this.usuario = usuario;
    }

    public WebService(String index, Animal animal) {
        this.index = index;
        this.animal = animal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String urlWebService(){


        Map<String, String> url = new HashMap<>();
        try{
            url.put("usuario", servidor+"/tcc/ClassController/ControllerUsuario.php");
            url.put("animal", servidor+"/tcc/ClassController/ControllerAnimal.php");
            url.put("agenda", servidor+"/tcc/ClassController/ControllerAgenda.php");
            url.put("cadastrarUsuario", servidor+"/tcc/ClassController/ControllerUsuario.php");

        }catch (Exception e){
            e.printStackTrace();
        }
        return url.get(this.getIndex());
    }


    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public String stringJson(){

        String stringJson = "";

        try{
            String url = this.urlWebService();
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

            OutputStream out = new BufferedOutputStream(con.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out,"UTF-8"
            ));
//
            writer.write(this.getJsonObject().toString());
            writer.flush();

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                StringBuffer response = new StringBuffer();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                stringJson = response.toString();

            } else {
                System.out.println("POST request not worked");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return stringJson;
    }

}
