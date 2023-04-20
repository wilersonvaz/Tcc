package com.example.tcc.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.tcc.CadastrarAnimal;
import com.example.tcc.Model.Animal;
import com.example.tcc.Model.WebService;

import org.json.JSONException;
import org.json.JSONObject;

public class CadastraAnimalTask extends AsyncTask<Animal, Void, String> {
    Context ctx;
    ProgressDialog progressDialog;
    String stringJson;

    public CadastraAnimalTask(Context ctx) {
        this.ctx = ctx;
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
    protected String doInBackground(Animal... animal) {

        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("acao", "cadastrarAnimal");
            jsonObject.put("nome", animal[0].getNome());
            jsonObject.put("dataNascimento", animal[0].getDataNascimento());
            jsonObject.put("dataAdocao", animal[0].getDataAdocao());
            jsonObject.put("peso", animal[0].getPeso());
            jsonObject.put("cor", animal[0].getCor());
            jsonObject.put("sexo", animal[0].getSexo());
            jsonObject.put("notas", animal[0].getNotas());
            jsonObject.put("raca_idRaca", 1);


            WebService webService = new WebService("cadastrarAnimal");
            webService.setJsonObject(jsonObject);
            stringJson = webService.stringJson();

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
                Toast.makeText(ctx , jsonObject.getString("resultado"), Toast.LENGTH_LONG).show();
                //Aqui vai mudar o pet na pagina inicial
//                MainActivity.idUsuario = Integer.parseInt(jsonObject.getString("idUsuario"));
//                MainActivity.nomeUsuario = jsonObject.getString("nomeUsuario");
//
//                Intent intent = new Intent(ctx, MainActivity.class);
//                ctx.startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.dismiss();
    }
}
