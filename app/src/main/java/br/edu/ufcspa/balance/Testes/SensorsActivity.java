package br.edu.ufcspa.balance.Testes;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.DadoGiroscopio;

public class SensorsActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor giroscope;

    private TextView txtx;
    private TextView txty;
    private TextView txtz;

    private ArrayList<DadoGiroscopio> dadosGiroscopio = new ArrayList<DadoGiroscopio>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        giroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, giroscope, SensorManager.SENSOR_DELAY_NORMAL);


        txtx = (TextView) findViewById(R.id.txt_x);
        txty = (TextView) findViewById(R.id.txt_y);
        txtz = (TextView) findViewById(R.id.txt_z);

    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        txtx.setText("X: "+String.valueOf(Math.floor(x * 100) / 100));
        txty.setText("Y: "+String.valueOf(Math.floor(y * 100) / 100));
        txtz.setText("Z: "+String.valueOf(Math.floor(z * 100) / 100));

        dadosGiroscopio.add(new DadoGiroscopio(System.currentTimeMillis(),x,y,z));

    }

    public void btn_Terminar_Click(View view){

        mSensorManager.unregisterListener(this);
        Log.d("GIROSCOPE", "GIROSCOPIO:");

        for (DadoGiroscopio d :dadosGiroscopio
             ) {

            Log.d("GIROSCOPE", "            X:"+String.valueOf(d.getX()));
            Log.d("GIROSCOPE", "            Y:"+String.valueOf(d.getY()));
            Log.d("GIROSCOPE", "            Z:"+String.valueOf(d.getZ()));
        }

        finish();


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
