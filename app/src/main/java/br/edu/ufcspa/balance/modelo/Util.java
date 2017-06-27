package br.edu.ufcspa.balance.modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classe com operações comum em diversas classes
 *
 * Created by Rodrigo on 06/05/2017.
 */

public class Util {

    public static String converterDateComHoras(Date date) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault());
        return simpleDate.format(date);
    }

    public static String converterDateApenasData(Date date) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return simpleDate.format(date);
    }
}
