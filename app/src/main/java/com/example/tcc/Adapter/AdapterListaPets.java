package com.example.tcc.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc.Model.Animal;
import com.example.tcc.Model.Usuario;
import com.example.tcc.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterListaPets extends RecyclerView.Adapter<AdapterListaPets.ListaPets> {
    ArrayList<Animal> listaPets;
    public AdapterListaPets(ArrayList<Animal> listaPets) {this.listaPets = listaPets; }

    @NonNull
    @Override
    public ListaPets onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detalhes_animal, null, false);
        // view.setOnClickListener(this);
        return new ListaPets(view);
        // return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaPets holder, int position) {
        try {
            holder.nomePet.setText(listaPets.get(position).getNome());
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            Bitmap bitmap = null;

            bitmap = BitmapFactory.decodeStream((InputStream) new URL(listaPets.get(position).getImagemPet()).getContent());

            holder.imagemPet.setImageBitmap(bitmap);

            holder.dataNascimento.setText(listaPets.get(position).getDataNascimento());

//            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
//            String inputDateStr = listaPets.get(position).getDataNascimento();
//            Date date = inputFormat.parse(inputDateStr);
//            String outputDateStr = outputFormat.format(date);
//
//            if(listaPets.get(position).getSexo().equals("Masculino")){
//                holder.dataNascimento.setText("Data de nascimento: "+outputDateStr);
//            }else{
//                holder.dataNascimento.setText("Data de nascimento: "+outputDateStr);
//            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listaPets.size();
    }

    public class ListaPets extends RecyclerView.ViewHolder{
        TextView nomePet;
        ImageView imagemPet;
        TextView dataNascimento;
        public ListaPets(@NonNull View itemView) {
            super(itemView);
            nomePet = itemView.findViewById(R.id.txtNomePet);
            imagemPet = itemView.findViewById(R.id.imagemPet);
            dataNascimento = itemView.findViewById(R.id.txtDataNascimento);
        }
    }
}
