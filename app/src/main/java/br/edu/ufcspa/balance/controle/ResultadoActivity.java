package br.edu.ufcspa.balance.controle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Avaliacao;
import br.edu.ufcspa.balance.modelo.Calcula;
import br.edu.ufcspa.balance.modelo.Coordenada2D;
import br.edu.ufcspa.balance.modelo.DadoAcelerometro;
import br.edu.ufcspa.balance.modelo.Paciente;

public class ResultadoActivity extends AppCompatActivity {

    private TextView textNomePaciente;
    private TextView textIdadePaciente;
    private TextView textArea;
    private TextView textDuracao;
    private TextView textPernas;
    private TextView textOlhos;
    private Avaliacao avaliacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        avaliacao = (Avaliacao) getIntent().getSerializableExtra("avaliação");

        criaGrafico();
        findViews();
        escreveTextos();

    }

    private void escreveTextos() {
        Paciente paciente = Paciente.findById(Paciente.class, avaliacao.getIdPaciente());
        textNomePaciente.setText(paciente.getNome());
        textIdadePaciente.setText(Calcula.idadeEmAnos(paciente.getDataNascimento()));

    }

    private void criaGrafico() {
        Gson gson = new Gson();
        String jsonArray = avaliacao.getDadosAcelerometro();
        Type listType = new TypeToken<ArrayList<DadoAcelerometro>>() {
        }.getType();
        List<DadoAcelerometro> listaDadosAcelerometro = gson.fromJson(jsonArray, listType);

        float alturaDoAparelho = 1.20f;
        float maiorX = 0;
        float maiorY = 0;
        SimpleXYSeries pontos = new SimpleXYSeries("Gráfico");
        for (DadoAcelerometro dado : listaDadosAcelerometro) {
            Coordenada2D coordenada2D = Calcula.coordenada2D(dado, alturaDoAparelho);
            if (coordenada2D.isValido()) {
                pontos.addLast(coordenada2D.getX(), coordenada2D.getY());

                if (Math.abs(coordenada2D.getX()) > maiorX)
                    maiorX = Math.abs(coordenada2D.getX());
                if (Math.abs(coordenada2D.getY()) > maiorY)
                    maiorY = Math.abs(coordenada2D.getY());
            }

        }

        XYPlot plot = (XYPlot) findViewById(R.id.android_plot);
        plot.setDomainBoundaries(-maiorX * 1.1, maiorX * 1.1, BoundaryMode.FIXED);
        plot.setRangeBoundaries(-maiorY * 1.1, maiorY * 1.1, BoundaryMode.FIXED);

        LineAndPointFormatter layoutPontos =
                new LineAndPointFormatter(this, R.xml.point_formatter);
        plot.addSeries(pontos, layoutPontos);

        PanZoom.attach(plot, PanZoom.Pan.BOTH, PanZoom.Zoom.STRETCH_BOTH, PanZoom.ZoomLimit.OUTER);
    }

    private void findViews() {
        textNomePaciente = (TextView) findViewById(R.id.text_nome_paciente);
        textIdadePaciente = (TextView) findViewById(R.id.text_idade_paciente);
        textArea = (TextView) findViewById(R.id.text_area);
        textDuracao = (TextView) findViewById(R.id.text_duracao);
        textPernas = (TextView) findViewById(R.id.text_pernas);
        textOlhos = (TextView) findViewById(R.id.text_olhos);
    }

}
