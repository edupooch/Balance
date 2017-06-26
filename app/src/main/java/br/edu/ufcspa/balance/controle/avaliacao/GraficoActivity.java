package br.edu.ufcspa.balance.controle.avaliacao;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Calcula;
import br.edu.ufcspa.balance.modelo.Coordenada2D;
import br.edu.ufcspa.balance.modelo.DadoAcelerometro;

public class GraficoActivity extends AppCompatActivity {

    List<DadoAcelerometro> listaDadosAcelerometro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Gráfico de Dispersão");


        /*Busca a lista de dados do acelerômetro em Json*/
        Intent intent = getIntent();
        String jsonDadosAcelerometro = intent.getStringExtra("listaDadosAcelerometro");
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<DadoAcelerometro>>() {
        }.getType();
        listaDadosAcelerometro = gson.fromJson(jsonDadosAcelerometro, listType);
        criaGrafico();
    }

    private void criaGrafico() {
        float alturaDoAparelho = 1.20f;
        float maiorX = 0;
        float maiorY = 0;
        SimpleXYSeries pontos = new SimpleXYSeries("Gráfico");
        Coordenada2D origem = getOrigem(alturaDoAparelho);

        for (DadoAcelerometro dado : listaDadosAcelerometro) {
            Coordenada2D ponto = Calcula.coordenada2D(dado, alturaDoAparelho);
            ponto = Coordenada2D.diminui(ponto, origem);
            if (ponto.isValido()) {
                pontos.addLast(ponto.getX(), ponto.getY());

                if (Math.abs(ponto.getX()) > maiorX)
                    maiorX = Math.abs(ponto.getX());
                if (Math.abs(ponto.getY()) > maiorY)
                    maiorY = Math.abs(ponto.getY());
            }
        }

        XYPlot plot = (XYPlot) findViewById(R.id.android_plot);
        plot.setDomainBoundaries(-maiorX * 1.1, maiorX * 1.1, BoundaryMode.FIXED);
        plot.setRangeBoundaries(-maiorY * 1.1, maiorY * 1.1, BoundaryMode.FIXED);

        LineAndPointFormatter layoutPontos =
                new LineAndPointFormatter(this, R.xml.point_formatter);
        plot.addSeries(pontos, layoutPontos);

        PanZoom.attach(plot, PanZoom.Pan.BOTH, PanZoom.Zoom.SCALE, PanZoom.ZoomLimit.OUTER);
    }


    /**
     * Método para retornar o primeiro ponto para diminuir os outros e manter o gráfico com origem
     * em (0,0)
     *
     * @param alturaDoAparelho em m
     * @return o primeiro valor de coordenada válido na lista.
     */
    private Coordenada2D getOrigem(float alturaDoAparelho) {
        for (DadoAcelerometro dado : listaDadosAcelerometro) {
            Coordenada2D coordenada2D = Calcula.coordenada2D(dado, alturaDoAparelho);
            if (coordenada2D.isValido()) {
                return coordenada2D;
            }
        }
        return new Coordenada2D(0, 0);
    }

}
