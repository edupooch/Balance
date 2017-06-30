package br.edu.ufcspa.balance.controle.avaliacao;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.xy.SimpleXYSeries;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Avaliacao;
import br.edu.ufcspa.balance.modelo.Calcula;
import br.edu.ufcspa.balance.modelo.Coordenada2D;
import br.edu.ufcspa.balance.modelo.DadoAcelerometro;
import br.edu.ufcspa.balance.modelo.DadoGiroscopio;
import br.edu.ufcspa.balance.modelo.OperacoesBanco;
import br.edu.ufcspa.balance.modelo.Paciente;
import br.edu.ufcspa.balance.modelo.Util;

public class SensoresActivity extends AppCompatActivity implements SensorEventListener {

    private SensoresActivity activity = this;

    private Paciente paciente;
    private int modoPernas;
    private int modoOlhos;
    private int duracao;
    private double altura;

    private SensorManager mSensorManager;

    private ArrayList<DadoGiroscopio> dadosGiroscopio = new ArrayList<DadoGiroscopio>();
    private ArrayList<DadoAcelerometro> dadosAcelerometro = new ArrayList<DadoAcelerometro>();
    private long tempoInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        Intent intent = getIntent();
        paciente = (Paciente) intent.getSerializableExtra("paciente");
        modoOlhos = (int) intent.getSerializableExtra("modoOlhos");
        modoPernas = (int) intent.getSerializableExtra("modoPernas");
        duracao = (int) intent.getSerializableExtra("duracao");
        altura = (double) intent.getSerializableExtra("altura");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tempoInicio = System.currentTimeMillis();
        Sensor giroscopio = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor acelerometro = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, giroscopio, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);

        final TextView txtTimer = (TextView) findViewById(R.id.text_timer);

        new CountDownTimer((duracao) * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTimer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 1000);
                terminar();
            }
        }.start();

        /*Desabilita o bloqueio da tela por timeout*/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//
//        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
//            float x = event.values[0];
//            float y = event.values[1];
//            float z = event.values[2];
//
//            dadosGiroscopio.add(new DadoGiroscopio(System.currentTimeMillis() - tempoInicio, x, y, z));
//        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            /*Troca do Y pelo Z pela inversão do celular*/

            float ax = event.values[0];
            float ay = event.values[2]; // antigo 1
            float az = event.values[1]; // antigo 2


            try {
            /*Caso o dado obtido seja diferente do anterior*/
                if (ax != dadosAcelerometro.get(dadosAcelerometro.size()).getX() &&
                        ay != dadosAcelerometro.get(dadosAcelerometro.size()).getY()) {

                    dadosAcelerometro.add(new DadoAcelerometro(System.currentTimeMillis() - tempoInicio, ax, ay, az));
                }
            } catch (Exception e) {
                dadosAcelerometro.add(new DadoAcelerometro(System.currentTimeMillis() - tempoInicio, ax, ay, az));
            }
        }

    }

    public void terminar() {

        mSensorManager.unregisterListener(this);
//        Log.d("GIROSCOPE", "GIROSCOPIO:");
//
//        for (DadoGiroscopio d : dadosGiroscopio) {
//
//            Log.d("GIROSCOPE", "            X:" + String.valueOf(d.getX()));
//            Log.d("GIROSCOPE", "            Y:" + String.valueOf(d.getY()));
//            Log.d("GIROSCOPE", "            Z:" + String.valueOf(d.getZ()));
//        }

        Log.d("ACELEROMETER", "ACELEROMETRO:");


        for (DadoAcelerometro d : dadosAcelerometro) {
            Log.d("ACELEROMETER", "            x:" + String.valueOf(d.getX()));
            Log.d("ACELEROMETER", "            y:" + String.valueOf(d.getY()));
            Log.d("ACELEROMETER", "            z:" + String.valueOf(d.getZ()));
            Log.d("ACELEROMETER", "            time:" + String.valueOf(d.getTempo()));


        }


        //Salva nova avaliação
        final Avaliacao avaliacao = new Avaliacao();
        avaliacao.setIdPaciente(paciente.id);
        avaliacao.setData(new Date(System.currentTimeMillis()));
        avaliacao.setPernas(modoPernas);
        avaliacao.setOlhos(modoOlhos);
        avaliacao.setDuracao(duracao);
        avaliacao.setFrequencia(100);
        avaliacao.setAltura(altura);

        /*Salva os dados dos sensores como JSON*/
        Gson gson = new Gson();
        avaliacao.setDadosAcelerometro(gson.toJson(dadosAcelerometro));
//        avaliacao.setDadosGiroscopio(gson.toJson(dadosGiroscopio));

        avaliacao.setVelocidade(calculaVelocidade(dadosAcelerometro,
                avaliacao.getAltura(), avaliacao.getDuracao()));

        /*Escreve no log do aparelho*/
//        Log.d("-- -- -- GIROSCOPIO", ":" + String.valueOf(Arrays.deepToString(dadosGiroscopio.toArray())));
        Log.d("-- -- -- ACELEROMETRO", ":" + String.valueOf(Arrays.deepToString(dadosAcelerometro.toArray())));

        avaliacao.save(); // Salva a avaliação no banco

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sucesso!");
        builder.setMessage("Avaliação salva com sucesso!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    /*Abre activity com o resultado*/
                                    abrirResultado(avaliacao);
                                } catch (Exception e) {
                                    e.printStackTrace();

                                    final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                    builder.setMessage("Não foi possível abrir a avaliação");

                                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.dismiss();
                                                }
                                            }
                                    );

                                    builder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.dismiss();
                                                }
                                            }
                                    );

                                    // Create the AlertDialog object and show it
                                    builder.create().show();
                                }

                            }
                        }
                );
        // Create the AlertDialog object and show it
        builder.setCancelable(false);
        builder.create().show();

    }

    private Double calculaVelocidade(ArrayList<DadoAcelerometro> dadosAcelerometro,
                                     Double alturaDoAparelho, int duracao) {
        SimpleXYSeries pontos = new SimpleXYSeries("Gráfico");
        for (DadoAcelerometro dado : dadosAcelerometro) {
            Coordenada2D ponto = Calcula.coordenada2D(dado, alturaDoAparelho.floatValue());
            if (ponto.isValido()) {
                pontos.addLast(ponto.getX(), ponto.getY());
            }
        }

        float distancia = Calcula.distanciaTotal(pontos);
        return (double) (distancia / duracao);
    }

    private void abrirResultado(Avaliacao avaliacao) {
        Intent intentResultado = new Intent(SensoresActivity.this, ResultadoActivity.class);
        intentResultado.putExtra("avaliação", avaliacao);
        startActivity(intentResultado);
        finish();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }

    @Override
    public void onBackPressed() {

    }
}
