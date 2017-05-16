package br.edu.ufcspa.balance.controle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Arrays;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Avaliacao;
import br.edu.ufcspa.balance.modelo.DadoAcelerometro;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        Avaliacao avaliacao = (Avaliacao) getIntent().getSerializableExtra("avaliação");

        Gson gson = new Gson();
        String jsonAcelerometro = avaliacao.getDadosAcelerometro();
        String arrayAcelerometro = gson.fromJson(jsonAcelerometro, String.class);
        Log.d("acelerometro",arrayAcelerometro);

    }
}
