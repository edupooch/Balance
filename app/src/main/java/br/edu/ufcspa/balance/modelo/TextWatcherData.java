package br.edu.ufcspa.balance.modelo;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by edupooch on 05/05/2017.
 */

class TextWatcherData implements TextWatcher {
    private final EditText editText;
    private String current = "";
    private Calendar cal = Calendar.getInstance();

    TextWatcherData(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals(current)) {
            String clean = s.toString().replaceAll("[^\\d.]", "");
            String cleanC = current.replaceAll("[^\\d.]", "");

            int cl = clean.length();
            int sel = cl;
            for (int i = 2; i <= cl && i < 6; i += 2) {
                sel++;
            }
            //Fix for pressing delete next to a forward slash
            if (clean.equals(cleanC)) sel--;

            if (clean.length() < 8) {
                String ddmmyyyy = "DDMMAAAA";
                clean = clean + ddmmyyyy.substring(clean.length());
            } else {
                //This part makes sure that when we finish entering numbers
                //the editText is correct, fixing it otherwise
                int day = Integer.parseInt(clean.substring(0, 2));
                int mon = Integer.parseInt(clean.substring(2, 4));
                int year = Integer.parseInt(clean.substring(4, 8));

                if (mon > 12) mon = 12;
                cal.set(Calendar.MONTH, mon - 1);
                year = (year < 1900) ? 1900 : (year > Calendar.getInstance().get(Calendar.YEAR)) ? Calendar.getInstance().get(Calendar.YEAR) : year;
                cal.set(Calendar.YEAR, year);
                // ^ first set year for the line below to work correctly
                //with leap years - otherwise, editText e.g. 29/02/2012
                //would be automatically corrected to 28/02/2012

                day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                clean = String.format(Locale.getDefault(), "%02d%02d%02d", day, mon, year);
            }

            clean = String.format("%s/%s/%s", clean.substring(0, 2),
                    clean.substring(2, 4),
                    clean.substring(4, 8));

            sel = sel < 0 ? 0 : sel;
            current = clean;
            editText.setText(current);
            editText.setSelection(sel < current.length() ? sel : current.length());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
};

