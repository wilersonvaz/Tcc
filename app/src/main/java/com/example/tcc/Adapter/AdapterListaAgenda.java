package com.example.tcc.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc.ListaAgenda;
import com.example.tcc.MainActivity;
import com.example.tcc.Model.Agenda;
import com.example.tcc.Model.Animal;
import com.example.tcc.Model.Usuario;
import com.example.tcc.ModelDAO.AgendaDAO;
import com.example.tcc.ModelDAO.UsuarioDAO;
import com.example.tcc.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class AdapterListaAgenda extends RecyclerView.Adapter<AdapterListaAgenda.ListaAgenda> {
    ArrayList<Agenda> listaAgenda;
    Context ctx;

    public AdapterListaAgenda(ArrayList<Agenda> listaAgenda, Context ctx) {
        this.listaAgenda = listaAgenda;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ListaAgenda onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detalhes_agenda, null, false);
        // view.setOnClickListener(this);
        return new ListaAgenda(view);
        // return null;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ListaAgenda holder, @SuppressLint("RecyclerView") int position) {
//        if (listaAgenda.get(position).getAnimal().getIdAnimal() == MainActivity.idAnimal && listaAgenda.get(position).getServico().toString().equals(servico)){
        holder.txtServico.setText(listaAgenda.get(position).getServico()+" - "+listaAgenda.get(position).getAnimal().getNome());
//        holder.txtNomeAnimal.setText(listaAgenda.get(position).getAnimal().getNome());
        holder.txtTitulo.setText(listaAgenda.get(position).getTitulo());
        holder.txtObservacao.setText(listaAgenda.get(position).getObservacao());

        SimpleDateFormat in= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");

        if(listaAgenda.get(position).getStatus() == 0){
            holder.txtStatus.setText("Pendente");
            holder.txtStatus.setTextColor(R.color.azul);
            holder.txtFaixaDecorativa.setBackgroundResource(R.color.azul);
            holder.calendario.setImageResource(R.drawable.calendario_azul);
            holder.relogio.setImageResource(R.drawable.relogio_64_azul);

        }else{
            holder.txtStatus.setText("Finalizado");
            holder.txtStatus.setTextColor(R.color.vermelho);
            holder.txtFaixaDecorativa.setBackgroundResource(R.color.vermelho);
            holder.calendario.setImageResource(R.drawable.calendario_64_vermelho);
            holder.relogio.setImageResource(R.drawable.relogio_64_vermelho);
        }

        try {
            String result = out.format(in.parse(listaAgenda.get(position).getDataAgenda().toString()));
            holder.txtData.setText("Data\n"+result);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.txtHorario.setText("Horario\n"+listaAgenda.get(position).getHorarioAgenda());

        holder.concluirCompromisso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MainActivity.pesquisa.equals("")){
                    Agenda agenda = new Agenda(listaAgenda.get(position).getIdAgenda(), listaAgenda.get(position).getServico());
                    AgendaDAO agendaDAO = new AgendaDAO("agenda");
                    agendaDAO.concluirCompromisso(view.getContext(), "concluirCompromisso", agenda);

                    Animal animal = new Animal();
                    animal.setIdAnimal(MainActivity.idAnimal);
                    Usuario usuario = new Usuario(
                            MainActivity.idUsuario,
                            animal,
                            agenda
                    );

                    UsuarioDAO usuarioDao = new UsuarioDAO("usuario");
                    usuarioDao.listaAgenda(ctx, "listaAgenda", usuario);
                }else{

                    Agenda agenda = new Agenda(listaAgenda.get(position).getIdAgenda(), listaAgenda.get(position).getServico(), MainActivity.pesquisa);
                    AgendaDAO agendaDAO = new AgendaDAO("agenda");
                    agendaDAO.concluirCompromisso(view.getContext(), "concluirCompromisso", agenda);

                    Animal animal = new Animal();
                    animal.setIdAnimal(MainActivity.idAnimal);
                    Usuario usuario = new Usuario(
                            MainActivity.idUsuario,
                            animal,
                            agenda
                    );

                    UsuarioDAO usuarioDao = new UsuarioDAO("usuario");
                    usuarioDao.pesquisaAgenda(ctx, "listaAgenda", usuario);
                }

            }
        });

        holder.excluirCompromisso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Agenda agenda = new Agenda(listaAgenda.get(position).getIdAgenda(), listaAgenda.get(position).getServico());
                AgendaDAO agendaDAO = new AgendaDAO("agenda");
                agendaDAO.excluirCompromisso(view.getContext(), "excluirCompromisso", agenda);

                Animal animal = new Animal();
                animal.setIdAnimal(MainActivity.idAnimal);
                Usuario usuario = new Usuario(
                        MainActivity.idUsuario,
                        animal,
                        agenda
                );

                UsuarioDAO usuarioDao = new UsuarioDAO("usuario");
                usuarioDao.listaAgenda(ctx, "listaAgenda", usuario);
            }
        });

//        }
    }

    @Override
    public int getItemCount() {
        return listaAgenda.size();
    }

    public class ListaAgenda extends RecyclerView.ViewHolder {
        TextView txtNomeAnimal;
        TextView txtTitulo;
        TextView txtServico;
        TextView txtData;
        TextView txtHorario;
        TextView txtObservacao;
        TextView txtStatus;
        TextView txtFaixaDecorativa;
        TextView concluirCompromisso;
        TextView excluirCompromisso;

        ImageView calendario, relogio;
        public ListaAgenda(@NonNull View itemView) {
            super(itemView);
//            txtNomeAnimal = itemView.findViewById(R.id.txtNomeAnimal);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtServico = itemView.findViewById(R.id.txtServico);
            txtData = itemView.findViewById(R.id.txtData);
            txtHorario = itemView.findViewById(R.id.txtHorario);
            txtObservacao = itemView.findViewById(R.id.txtObservacao);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtFaixaDecorativa = itemView.findViewById(R.id.txtFaixaDecorativa);
            calendario = itemView.findViewById(R.id.calendario);
            relogio = itemView.findViewById(R.id.relogio);
            concluirCompromisso = itemView.findViewById(R.id.concluirCompromisso);
            excluirCompromisso = itemView.findViewById(R.id.excluirCompromisso);
        }
    }
}
