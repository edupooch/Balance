package br.edu.ufcspa.balance.controle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.util.List;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Avaliacao;
import br.edu.ufcspa.balance.modelo.Banco;
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
        Long nAvaliacoes = Banco.countAvaliacoes(paciente);
        textNumeroDeAvaliacoes.setText(String.valueOf(nAvaliacoes));//nTestes
        TextView textAvaliacoes = (TextView) view.findViewById(R.id.text_avaliacoes);
        if (nAvaliacoes == 1) textAvaliacoes.setText(R.string.avaliacao_singular);

        ImageView campoFoto = (ImageView) view.findViewById(R.id.imagem_item);
        String caminhoFoto = paciente.getCaminhoFoto();
        if (caminhoFoto != null) {
            configuraFoto(campoFoto, caminhoFoto);
        }

        return view;
    }

    private void configuraFoto(ImageView campoFoto, String caminhoFoto) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bm = BitmapFactory.decodeFile(caminhoFoto, options);
        if (bm.getWidth() > bm.getHeight()) {
            bm = Bitmap.createScaledBitmap(bm, 150, 100, false);
            //foto vertical
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            FileOutputStream out = null;

        } else {
            //horizontal
            bm = Bitmap.createScaledBitmap(bm, 150, 100, false);
        }


        campoFoto.setImageBitmap(bm);
        campoFoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
    }
