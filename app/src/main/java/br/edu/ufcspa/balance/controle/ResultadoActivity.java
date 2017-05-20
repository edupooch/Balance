package br.edu.ufcspa.balance.controle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
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

        ScatterChart scatterChart = (ScatterChart) findViewById(R.id.grafico_dispersao);

        ArrayList<Entry> entries = new ArrayList<>(listaDadosAcelerometro.size());
        float alturaDoAparelho = 1.20f;
        for (DadoAcelerometro dado : listaDadosAcelerometro) {
            Coordenada2D coordenada2D = Calcula.coordenada2D(dado, alturaDoAparelho);
            entries.add(new Entry(coordenada2D.getX(), coordenada2D.getY()));
        }

        ScatterDataSet dataset = new ScatterDataSet(entries, "# of Calls");

        ScatterData data = new ScatterData(dataset);
        scatterChart.setData(data);

        Description description = new Description();
        description.setText("Dispersão");
        scatterChart.setDescription(description);

        dataset.setColors(ColorTemplate.PASTEL_COLORS); //
        dataset.setScatterShapeSize(20);
        dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);

    }
}
