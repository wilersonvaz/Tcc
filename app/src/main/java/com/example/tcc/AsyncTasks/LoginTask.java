package com.example.tcc.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.tcc.MainActivity;
import com.example.tcc.Model.Usuario;
import com.example.tcc.Model.WebService;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginTask extends AsyncTask<Usuario, Void, String> {
    Context ctx;
    ProgressDialog progressDialog;
    String stringJson;

    public LoginTask(Context applicationContext) {
        this.ctx = applicationContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Por favor espere!");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Usuario... usuarios) {

        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("acao", usuarios[0].getAcao());
            jsonObject.put("email", usuarios[0].getEmail());
            jsonObject.put("senha", usuarios[0].getSenha());


            WebService webService = new WebService("login");
//            String url = webService.urlWebService();
            webService.setJsonObject(jsonObject);
            stringJson = webService.stringJson();

//            URL obj = new URL(url);
//            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//            con.setRequestMethod("POST");
//
//            OutputStream out = new BufferedOutputStream(con.getOutputStream());
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
//                    out,"UTF-8"
//            ));
////
//            writer.write(jsonObject.toString());
//            writer.flush();
//
//            int responseCode = con.getResponseCode();
////            System.out.println("POST Response Code :: " + responseCode);
//
//            if (responseCode == HttpURLConnection.HTTP_OK) { //success
//                StringBuffer response = new StringBuffer();
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        con.getInputStream()));
//                String inputLine;
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//                stringJson = response.toString();
//
//            } else {
//                System.out.println("POST request not worked");
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {

            if(isJSONValid(stringJson)){
            JSONObject jsonObject = new JSONObject(stringJson);
//            if(jsonObject.length() > 1 ){

                MainActivity.idUsuario = Integer.parseInt(jsonObject.getString("idUsuario"));
                MainActivity.nomeUsuario = jsonObject.getString("nomeUsuario");

                Intent intent = new Intent(ctx, MainActivity.class);
                ctx.startActivity(intent);

            }else{
                Toast.makeText(ctx, "Usuário não encontrado!", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.dismiss();
    }
}
