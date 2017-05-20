package br.edu.ufcspa.balance.modelo;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by edupooch on 12/06/2016.
 * Classe para efetuar os cálculos necessários durante o teste, como idadeEmAnos e fórmulas de dp estimada.
 */

public class Calcula {

    public static final int AO_QUADRADO = 2;

    /**
     * Calcula a idadeEmAnos em anos
     *
     * @param dataNasc recebe a data de nascimento de paciente.getData()
     * @return idadeEmAnos em anos
     */
    public static String idadeEmAnos(java.util.Date dataNasc) {
        LocalDate birthdate = new LocalDate(dataNasc);
        LocalDate now = new LocalDate();

        Period period = new Period(birthdate, now, PeriodType.yearMonthDay());
        String anos = period.getYears() + " anos";
        if (period.getYears() == 1) {
            anos = period.getYears() + " ano";
        }
        return anos;
    }

    /**
     * Retorna a idadeEmAnos em anos meses e dias como String para o perfil do paciente
     *
     * @param dataNasc data de nascimento
     * @return Idade em anos meses e dias
     */

    public static String idadeCompleta(java.util.Date dataNasc) {

        LocalDate birthdate = new LocalDate(dataNasc);
        LocalDate now = new LocalDate();

        Period period = new Period(birthdate, now, PeriodType.yearMonthDay());
        String anos = period.getYears() + " anos, ";
        if (period.getYears() == 1) {
            anos = period.getYears() + " ano, ";
        }

        String meses = period.getMonths() + " meses e ";
        if (period.getMonths() == 1) {
            meses = period.getMonths() + "mês  ";
        }

        String dias = period.getDays() + " dias";
        if (period.getDays() == 1) {
            dias = period.getDays() + " dia";
        }

        return anos + meses + dias;

    }

    /**
     * Cálculo do imc do paciente
     *
     * @param massa    em kg
     * @param estatura em cm
     * @return imc
     */

    public static double imc(double massa, double estatura) {
        return massa / Math.pow(estatura / 100, 2);
    }

    /**
     * Cálcula a coordenada da projeção no gráfico de dispersão
     *
     * @param dadoAcelerometro dado de um momento no tempo do acelerometro
     * @param altura           do paciente em m
     * @return coordenada
     */
    public static Coordenada2D coordenada2D(DadoAcelerometro dadoAcelerometro, float altura) {
        float resultanteA = (float) Math.sqrt(
                Math.pow(dadoAcelerometro.getX(), AO_QUADRADO) +
                        Math.pow(dadoAcelerometro.getY(), AO_QUADRADO) +
                        Math.pow(dadoAcelerometro.getZ(), AO_QUADRADO));

        float cosAlfa = dadoAcelerometro.getX() / resultanteA;
        float cosBeta = dadoAcelerometro.getY() / resultanteA;
        float cosGama = dadoAcelerometro.getZ() / resultanteA;

        float resultanteD = -altura / cosGama;

        float dx = resultanteD * cosAlfa;
        float dy = resultanteD * cosBeta;

        return new Coordenada2D(dx, dy);
    }

}
