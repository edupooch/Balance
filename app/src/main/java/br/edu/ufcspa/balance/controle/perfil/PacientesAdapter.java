package br.edu.ufcspa.balance.controle.perfil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.OperacoesBanco;
import br.edu.ufcspa.balance.modelo.Paciente;


/**
 * Created by edupooch on 14/05/16.
 * <p>
 * Classe Adapter criada para fazer uma lista de paciente mais interativa e com mais informações.
 */

public class PacientesAdapter extends BaseAdapter {

    private final List<Paciente> pacientes;
    private final Context context;

    public PacientesAdapter(Context context, List<Paciente> pacientes) {
        this.context = context;
        this.pacientes = pacientes;
    }

    @Override
    public int getCount() {
        return pacientes.size();
    }

    @Override
    public Object getItem(int position) {
        return pacientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pacientes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Paciente paciente = pacientes.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;
        if (view == null) { //convertView otimiza o carregamento de listas muito grandes
            view = inflater.inflate(R.layout.list_item_pacientes, parent, false);
        }

        TextView textNome = (TextView) view.findViewById(R.id.textNomePacienteLista);
        textNome.setText(paciente.getNome());

        TextView textNumeroDeAvaliacoes = (TextView) view.findViewById(R.id.textNumeroDeAvaliacoes);
        Long nAvaliacoes = OperacoesBanco.countAvaliacoes(paciente);
        textNumeroDeAvaliacoes.setText(String.valueOf(nAvaliacoes));
        TextView textAvaliacoes = (TextView) view.findViewById(R.id.text_avaliacoes);
        if (nAvaliacoes == 1) textAvaliacoes.setText(R.string.avaliacao_singular);

        return view;
    }

}
