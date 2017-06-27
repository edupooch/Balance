package br.edu.ufcspa.balance.modelo;

import com.androidplot.xy.SimpleXYSeries;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;


/**
 * Classe que realiza cálculos como o de idade a partir da data de nascimento e o cálculo da
 * projeção no chão a partir dos valores do acelerometro
 * <p>
 * Created by edupooch on 12/06/2016.
 */

public class Calcula {

    private static final int AO_QUADRADO = 2;
    private static final int M_PARA_CM = 100;
    private static final int CM_PARA_M = 100;
    public static final int INVERTER = -1;

    /**
     * Calcula a idadeEmAnos em anos
     *
     * @param dataNasc recebe a data de nascimento de paciente.getData()
     * @return idadeEmAnos em anos
     */
    public static String idadeEmAnos(String dataNasc) {
        if (dataNasc.isEmpty()) {
            return null;
        }
        final DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");

        LocalDate birthdate = dtf.parseLocalDate(dataNasc);
        LocalDate now = new LocalDate();

        Period period = new Period(birthdate, now, PeriodType.yearMonthDay());
        String anos = period.getYears() + " anos";
        if (period.getYears() == 1) {
            anos = period.getYears() + " ano";
        }
        return anos;
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

        float dx = resultanteD * cosAlfa * M_PARA_CM * INVERTER;
        float dy = resultanteD * cosBeta * M_PARA_CM;

        return new Coordenada2D(dx, dy);
    }

    public static float distanciaTotal(SimpleXYSeries pontos) {
        float distTotal = 0;
        for (int i = 1; i < pontos.size(); i++) {
            distTotal += distanciaEuclidiana(
                    pontos.getX(i).floatValue(), pontos.getX(i - 1).floatValue(),
                    pontos.getY(i).floatValue(), pontos.getY(i - 1).floatValue());
        }
        return distTotal;
    }

    private static double distanciaEuclidiana(float x1, float x2, float y1, float y2) {
        float dx = x1 / CM_PARA_M - x2 / CM_PARA_M;
        float dy = y1 / CM_PARA_M - y2 / CM_PARA_M;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * Método para retornar o primeiro ponto para diminuir os outros e manter o gráfico com origem
     * em (0,0)
     *
     * @param alturaDoAparelho em m
     * @return o primeiro valor de coordenada válido na lista.
     */
    public static Coordenada2D getOrigem(List<DadoAcelerometro> listaDadosAcelerometro, float alturaDoAparelho) {
        for (DadoAcelerometro dado : listaDadosAcelerometro) {
            Coordenada2D coordenada2D = Calcula.coordenada2D(dado, alturaDoAparelho);
            if (coordenada2D.isValido()) {
                return coordenada2D;
            }
        }
        return new Coordenada2D(0, 0);

    }
}
