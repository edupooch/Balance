package br.edu.ufcspa.balance.controle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rodrigo on 06/05/2017.
 */

public class Util {


    public static String converterDate(Date date){

        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");

        return simpleDate.format(date);
    }
}
