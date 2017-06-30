package br.edu.ufcspa.balance.controle.avaliacao;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Avaliacao;
import br.edu.ufcspa.balance.modelo.Paciente;

public class TimerActivity extends AppCompatActivity {

    TimerActivity This = this;

    Paciente paciente;

    int modoPernas;
    int modoOlhos;
    int duracao;
    int segundosTimer;
    double altura;

    int backCounter = 0;


    TextView txtTimer;
    TextView txtPrepare;

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        //TODO default
        segundosTimer = 10;

        txtTimer = (TextView) findViewById(R.id.text_timer);
        txtPrepare = (TextView) findViewById(R.id.text_prepare);

        Intent intent = getIntent();
        paciente = (Paciente) intent.getSerializableExtra("paciente");
        modoPernas = intent.getIntExtra("modoPernas", Avaliacao.DUAS_PERNAS);
        modoOlhos = intent.getIntExtra("modoOlhos", Avaliacao.OLHOS_ABERTOS);
        duracao = intent.getIntExtra("duracao", 15);
        altura = intent.getDoubleExtra("altura", 1);

        mostrarDialogTimer();

    }

    private void mostrarDialogTimer() {

        final Dialog dialog = new Dialog(This);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.timer_dialog);

        final View btnIniciarAvaliacao = dialog.findViewById(R.id.text_iniciar_avaliacao);
        final TextView pickerTimer = (TextView) dialog.findViewById(R.id.picker_timer);
        TextView btnMenos = (TextView) dialog.findViewById(R.id.btn_menos);
        TextView btnMais = (TextView) dialog.findViewById(R.id.btn_mais);

        /* Diminui timer*/
        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = Integer.parseInt(pickerTimer.getText().toString());
                if (time >= 10) {
                    time -= 5;
                    pickerTimer.setText(time + "");
                }
            }
        });

        /* Aumenta timer*/
        btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = Integer.parseInt(pickerTimer.getText().toString());
                if (time <= 55) {
                    time += 5;
                    pickerTimer.setText(time + "");
                }
            }
        });


        btnIniciarAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                segundosTimer = Integer.valueOf(pickerTimer.getText().toString());
                dialog.dismiss();
                iniciarTimer();
            }
        });

        dialog.show();
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mostrarDialogVoltar();
                }
                return true;
            }
        });

    }


    public void iniciarTimer() {

        /*Desabilita o bloqueio da tela por timeout*/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        txtTimer.setVisibility(View.VISIBLE);
        txtPrepare.setVisibility(View.VISIBLE);

        timer = new CountDownTimer((segundosTimer) * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTimer.setText("" + millisUntilFinished / 1000);

                if (millisUntilFinished / 1000 <= 3) {
                    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                    toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                }

            }

            public void onFinish() {
                iniciaAvaliação();
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 1000);
                This.finish();
            }
        }.start();
    }


    private void iniciaAvaliação() {
        Intent intentVaiPraListaDeAvaliacoes = new Intent(TimerActivity.this, SensoresActivity.class);
        intentVaiPraListaDeAvaliacoes.putExtra("paciente", paciente);
        intentVaiPraListaDeAvaliacoes.putExtra("modoPernas", modoPernas);
        intentVaiPraListaDeAvaliacoes.putExtra("modoOlhos", modoOlhos);
        intentVaiPraListaDeAvaliacoes.putExtra("duracao", duracao);
        intentVaiPraListaDeAvaliacoes.putExtra("altura", altura);
        startActivity(intentVaiPraListaDeAvaliacoes);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (timer != null)
            timer.cancel();
    }

    private void mostrarDialogVoltar() {

        if (backCounter >= 1)
            return;

        backCounter++;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Voltar");
        builder.setMessage("Deseja cancelar esta avaliação?");
        builder.setPositiveButton(getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (timer != null)
                    timer.cancel();

                This.finish();
            }
        });
        builder.setNegativeButton(getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                backCounter = 0;
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
