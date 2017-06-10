package br.edu.ufcspa.balance.controle;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.ufcspa.balance.R;

/**
 * Created by edupooch on 10/06/2017.
 */

public class ExportarDialog {

    public static final int APARELHO = 0;
    public static final int EMAIL = 1;

    private int metodoEscolhido;
    private Context context;

    public ExportarDialog(Context context) {
        this.context = context;
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.email_dialog);

        View btAparelho = dialog.findViewById(R.id.layout_aparelho);
        View btEmail = dialog.findViewById(R.id.layout_email);

        btAparelho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_aparelho);
                imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
                metodoEscolhido = APARELHO;
                dialog.dismiss();
            }
        });

        btEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_email);
                imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
                metodoEscolhido = EMAIL;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public int getMetodoEscolhido() {
        return metodoEscolhido;
    }
}