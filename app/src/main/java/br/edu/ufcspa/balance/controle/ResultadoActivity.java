package br.edu.ufcspa.balance.controle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

        List<Coordenada2D> listaCoordenadas = new ArrayList<>(listaDadosAcelerometro.size());

        double alturaDoAparelho = 1.20;
        for (DadoAcelerometro dado : listaDadosAcelerometro) {
            listaCoordenadas.add(Calcula.coordenada2D(dado, alturaDoAparelho));
        }


        Log.d("acelerometro", String.valueOf(listaCoordenadas));

    }
}
