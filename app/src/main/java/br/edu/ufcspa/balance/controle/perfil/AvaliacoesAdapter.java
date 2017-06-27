package br.edu.ufcspa.balance.controle.perfil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Avaliacao;
import br.edu.ufcspa.balance.modelo.Util;


/**
 * Classe adapter para a lista de avaliações utilizada no perfil do paciente
 */

class AvaliacoesAdapter extends BaseAdapter {

    private final List<Avaliacao> avaliacoes;
    private final Context context;

    AvaliacoesAdapter(Context context, List<Avaliacao> avaliacoes) {
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
        //TITULO
        TextView textTitulo = (TextView) view.findViewById(R.id.text_titulo_avaliacao);
        textTitulo.setText("Avaliação " + String.valueOf(getCount() - position));
        //DATA
        TextView txtData = (TextView) view.findViewById(R.id.text_data_avaliacao);
        txtData.setText(Util.converterDateComHoras(avaliacao.getData()));
        //vm
        TextView txtVelocidadeMedia = (TextView) view.findViewById(R.id.text_velocidade_media);
        txtVelocidadeMedia.setText((String.format(Locale.getDefault(), "%.2f m/s", avaliacao.getVelocidade())));

        return view;
    }

}
