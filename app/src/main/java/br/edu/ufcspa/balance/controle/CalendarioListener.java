package br.edu.ufcspa.balance.controle;

import android.app.DatePickerDialog;
import android.view.View;

import java.util.Calendar;

/**
 * Created by edupooch on 06/05/2017.
 */

public class CalendarioListener implements View.OnClickListener {

    private final CadastroActivity cadastroActivity;

    CalendarioListener(CadastroActivity cadastroActivity){
        this.cadastroActivity =cadastroActivity;
    }

    @Override
    public void onClick(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(cadastroActivity, cadastroActivity, now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        dpd.show();

    }

}
