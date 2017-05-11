package br.edu.ufcspa.balance.Testes;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.DadoAcelerometro;
import br.edu.ufcspa.balance.modelo.DadoGiroscopio;
import br.edu.ufcspa.balance.modelo.Paciente;

public class SensorsActivity extends AppCompatActivity implements SensorEventListener{

    private Paciente paciente;

    private SensorManager mSensorManager;
    private Sensor giroscopio;
    private Sensor acelerometro;

    private TextView txtx;
    private TextView txty;
    private TextView txtz;

    private TextView txtAx;
    private TextView txtAy;
    private TextView txtAz;

    private ArrayList<DadoGiroscopio> dadosGiroscopio = new ArrayList<DadoGiroscopio>();
    private ArrayList<DadoAcelerometro> dadosAcelerometro = new ArrayList<DadoAcelerometro>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        paciente = (Paciente) intent.getSerializableExtra("paciente");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        giroscopio = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        acelerometro = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, giroscopio, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);


        txtx = (TextView) findViewById(R.id.txt_x);
        txty = (TextView) findViewById(R.id.txt_y);
        txtz = (TextView) findViewById(R.id.txt_z);

        txtAx = (TextView) findViewById(R.id.txt_ax);
        txtAy = (TextView) findViewById(R.id.txt_ay);
        txtAz = (TextView) findViewById(R.id.txt_az);



    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            txtx.setText("X: "+String.valueOf(Math.floor(x * 100) / 100));
            txty.setText("Y: "+String.valueOf(Math.floor(y * 100) / 100));
            txtz.setText("Z: "+String.valueOf(Math.floor(z * 100) / 100));

            dadosGiroscopio.add(new DadoGiroscopio(System.currentTimeMillis(),x,y,z));
        }

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        }
            float ax = event.values[0];
            float ay = event.values[1];
            float az = event.values[2];


        txtAx.setText("X: "+String.valueOf(Math.floor(ax * 100) / 100));
        txtAy.setText("Y: "+String.valueOf(Math.floor(ay * 100) / 100));
        txtAz.setText("Z: "+String.valueOf(Math.floor(az * 100) / 100));


        dadosAcelerometro.add(new DadoAcelerometro(System.currentTimeMillis(),ax,ay,az));

        }

    public void btn_Terminar_Click(View view){

        mSensorManager.unregisterListener(this);
        Log.d("GIROSCOPE", "GIROSCOPIO:");

        for (DadoGiroscopio d :dadosGiroscopio) {

            Log.d("GIROSCOPE", "            X:"+String.valueOf(d.getX()));
            Log.d("GIROSCOPE", "            Y:"+String.valueOf(d.getY()));
            Log.d("GIROSCOPE", "            Z:"+String.valueOf(d.getZ()));
        }

        Log.d("ACELEROMETER", "ACELEROMETRO:");


        for (DadoAcelerometro d : dadosAcelerometro){
            Log.d("ACELEROMETER", "            x:"+String.valueOf(d.getX()));
            Log.d("ACELEROMETER", "            y:"+String.valueOf(d.getY()));
            Log.d("ACELEROMETER", "            z:"+String.valueOf(d.getZ()));


        }

        finish();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
