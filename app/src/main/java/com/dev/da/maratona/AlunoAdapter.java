package com.dev.da.maratona;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/*
 * Created by Ramon on 16/10/2017.
 */

public class AlunoAdapter extends ArrayAdapter<Aluno> {

    private Context context;
    private ArrayList<Aluno> lista;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private String url = "http://www.unicap.br/pergamum3/Pergamum/biblioteca_s/meu_pergamum/getImg.php?cod_pessoa=";

    private String formataMatricula(String matricula){
        String[] separa = matricula.split("-");
        return separa[0] + separa[1];
    }

    public AlunoAdapter(Context context, ArrayList<Aluno> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Aluno alunoPosicao = this.lista.get(position);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.aluno_item, null);
        StorageReference storage = storageReference.child("Fotos/" + alunoPosicao.getImagem());

        ImageView foto = convertView.findViewById(R.id.img_aluno);
        TextView nome = convertView.findViewById(R.id.nome_item);
        TextView pontos = convertView.findViewById(R.id.matricula_item);
        TextView pos = convertView.findViewById(R.id.posicao_item);

        nome.setText(alunoPosicao.getPrimeiroNome() + " " + alunoPosicao.getUltimoNome());
        pontos.setText(String.valueOf(alunoPosicao.getPontuacao()));
        pos.setText(String.valueOf(position + 1) + ".");
        if(alunoPosicao.getImagem().equals("0")) {
            Picasso.with(convertView.getContext()).load(url + formataMatricula(alunoPosicao.getMatricula())).error(R.drawable.usuario).into(foto);

        }
        else {
            Glide.with(getContext())
                    .using(new FirebaseImageLoader())
                    .load(storage)
                    .into(foto);
        }
        return convertView;
    }
}
