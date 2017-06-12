package br.edu.ufcspa.balance.controle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Avaliacao;
import br.edu.ufcspa.balance.modelo.Util;


/**
 * Created by edupooch on 14/05/16.
 *
 * Classe Adapter criada para fazer uma lista de paciente mais interativa e com mais informações.
 */

public class AvaliacoesAdapter extends BaseAdapter {

    private final List<Avaliacao> avaliacoes;
    private final Context context;

    public AvaliacoesAdapter(Context context, List<Avaliacao> avaliacoes) {
        this.context = context;
        this.avaliacoes = avaliacoes;
    }

    @Override
    public int getCount() {
        return avaliacoes.size();
    }

    @Override
    public Object getItem(int position) {
        return avaliacoes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return avaliacoes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Avaliacao avaliacao = avaliacoes.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;
        if (view == null) { //usa-se o convertView para otimizar o carregamento de listas muito grandes
            view = inflater.inflate(R.layout.list_item_avaliacao, parent, false);
        }

        //ID
        TextView txtId = (TextView) view.findViewById(R.id.text_id_avaliacao);
        txtId.setText(String.valueOf(avaliacao.id));

        //DATA
        TextView txtData = (TextView) view.findViewById(R.id.text_data);
        txtData.setText(Util.converterDate(avaliacao.getData()));

        //AREA
        TextView txtArea = (TextView) view.findViewById(R.id.text_area);
        txtArea.setText(String.valueOf(avaliacao.getArea()) + "cm^2");

        //DURAÇÃO
        TextView txtDuracao = (TextView) view.findViewById(R.id.text_duracao);
        txtDuracao.setText(String.valueOf(avaliacao.getDuracao()) + " segundos");

        //POSIÇÃO
        TextView txtPosicao = (TextView) view.findViewById(R.id.text_posicao);

        if(avaliacao.getPernas() == Avaliacao.UMA_PERNA)
            txtPosicao.setText(" Uma perna");

        if(avaliacao.getPernas() == Avaliacao.DUAS_PERNAS)
            txtPosicao.setText(" Duas pernas");

        //OLHOS
        TextView txtOlhos = (TextView) view.findViewById(R.id.text_olhos);

        if(avaliacao.getOlhos() == Avaliacao.OLHOS_ABERTOS)
            txtOlhos.setText(" Abertos");

        if(avaliacao.getOlhos() == Avaliacao.OLHOS_FECHADOS)
            txtOlhos.setText(" Fechados");

        return view;
    }

}
