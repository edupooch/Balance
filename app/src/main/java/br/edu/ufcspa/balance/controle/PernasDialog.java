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

public class PernasDialog {

    public static final int UMA_PERNA = 1;
    public static final int DUAS_PERNAS = 2;

    private int modoEscolhido;
    private Context context;

    View btnUmaPerna;
    View btnDuasPernas;

    public PernasDialog(Context context) {
        this.context = context;
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.pernas_dialog);

        btnUmaPerna = dialog.findViewById(R.id.layout_uma_perna);
        btnDuasPernas = dialog.findViewById(R.id.layout_duas_pernas);

        btnUmaPerna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_uma_perna);
                imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
                modoEscolhido = Avaliacao.UMA_PERNA;
                dialog.dismiss();
            }
        });

        btnDuasPernas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_duas_pernas);
                imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
                modoEscolhido = Avaliacao.DUAS_PERNAS;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public int getModoEscolhido() {
        return modoEscolhido;
    }
}