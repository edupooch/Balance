package br.edu.ufcspa.balance.modelo;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by edupooch on 12/06/2016.
 * Classe para efetuar os cálculos necessários durante o teste, como idade e fórmulas de dp estimada.
 */

public class Calcula {

    /**
     * Calcula a idade em anos
     * @param dataNasc recebe a data de nascimento de paciente.getData()
     * @return idade em anos
     */
    public static int idade(java.util.Date dataNasc) {

        Calendar calDataNascimento = new GregorianCalendar();
        calDataNascimento.setTime(dataNasc);
        Calendar hoje = Calendar.getInstance();

        int idadeAnos = hoje.get(Calendar.YEAR) - calDataNascimento.get(Calendar.YEAR);
        calDataNascimento.add(Calendar.YEAR, idadeAnos);
        if (hoje.before(calDataNascimento)) idadeAnos--;

        return idadeAnos;

    }

    /**
     * Retorna a idade em anos meses e dias como String para o perfil do paciente
     * @param dataNasc data de nascimento
     * @return Idade em anos meses e dias
     */

    public static String idadeCompleta(java.util.Date dataNasc) {

        LocalDate birthdate = new LocalDate(dataNasc);
        LocalDate now = new LocalDate();

        Period period = new Period(birthdate, now, PeriodType.yearMonthDay());
        String anos = period.getYears() + " anos, ";
        if (period.getYears() == 1){
            anos = period.getYears() + " ano, ";
        }

        String meses = period.getMonths() + " meses e ";
        if (period.getMonths() == 1){
            meses = period.getMonths() + "mês  ";
        }

        String dias = period.getDays() + " dias";
        if (period.getDays() == 1){
            dias = period.getDays() + " dia";
        }

        return anos + meses + dias;

    }

    /**
     * Cálculo do imc do paciente
     *
     * @param massa em kg
     * @param estatura em cm
     * @return imc
     */

    public static double imc(double massa, double estatura) {
        return massa / Math.pow(estatura / 100, 2);
    }

}