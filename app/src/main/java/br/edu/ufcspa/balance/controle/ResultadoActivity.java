package br.edu.ufcspa.balance.controle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.RectRegion;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Avaliacao;
import br.edu.ufcspa.balance.modelo.Calcula;
import br.edu.ufcspa.balance.modelo.Coordenada2D;
import br.edu.ufcspa.balance.modelo.DadoAcelerometro;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        Avaliacao avaliacao = (Avaliacao) getIntent().getSerializableExtra("avaliação");

        Gson gson = new Gson();
        String jsonArray = avaliacao.getDadosAcelerometro();
        Type listType = new TypeToken<ArrayList<DadoAcelerometro>>() {
        }.getType();
        List<DadoAcelerometro> listaDadosAcelerometro = gson.fromJson(jsonArray, listType);

        float alturaDoAparelho = 1.20f;
        float maiorX = 0;
        float maiorY = 0;
        SimpleXYSeries series = new SimpleXYSeries("Gráfico");
        for (DadoAcelerometro dado : listaDadosAcelerometro) {
            Coordenada2D coordenada2D = Calcula.coordenada2D(dado, alturaDoAparelho);
            if (!(Double.isInfinite(coordenada2D.getX()) || Double.isInfinite(coordenada2D.getY()) ||
                    Double.isNaN(coordenada2D.getX()) || Double.isNaN(coordenada2D.getY()))) {

                series.addLast(coordenada2D.getX(), coordenada2D.getY());
                Log.d("dados", "x: " + coordenada2D.getX() + " y: " + coordenada2D.getY());

                //Salva os limites
                if (Math.abs(coordenada2D.getX()) > maiorX)
                    maiorX = Math.abs(coordenada2D.getX());
                if (Math.abs(coordenada2D.getY()) > maiorY)
                    maiorY = Math.abs(coordenada2D.getY());
            }

        }

        // ANDROIDPLOT
        XYPlot plot = (XYPlot) findViewById(R.id.android_plot);
        plot.setDomainBoundaries(-maiorX - 2, maiorX + 2, BoundaryMode.FIXED);
        plot.setRangeBoundaries(-maiorY - 2, maiorY + 2, BoundaryMode.FIXED);

        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        LineAndPointFormatter series1Format =
                new LineAndPointFormatter(this, R.xml.point_formatter);

        // add each series to the xyplot:
        plot.addSeries(series, series1Format);
        // reduce the number of range labels
        plot.setLinesPerRangeLabel(3);
    }

}
