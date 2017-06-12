package br.edu.ufcspa.balance.controle;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Avaliacao;

/**
 * Created by edupooch on 10/06/2017.
 */

public class OlhosDialog {

    public static final int OLHOS_ABERTOS = 1;
    public static final int OLHOS_FECHADOS = 2;

    private int modoEscolhido;
    private Context context;

    public View btnOlhosAbertos;
    public View btnOlhosFechados;

    public OlhosDialog(Context context) {
        this.context = context;
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.olhos_dialog);

        btnOlhosAbertos = dialog.findViewById(R.id.layout_olhos_abertos);
        btnOlhosFechados = dialog.findViewById(R.id.layout_olhos_fechados);

        btnOlhosAbertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_olhos_abertos);
                imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
                modoEscolhido = Avaliacao.OLHOS_ABERTOS;
                dialog.dismiss();
            }
        });

        btnOlhosFechados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_olhos_fechados);
                imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
                modoEscolhido = Avaliacao.OLHOS_FECHADOS;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public int getModoEscolhido() {
        return modoEscolhido;
    }
}