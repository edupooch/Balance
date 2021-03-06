package br.edu.ufcspa.balance.controle.avaliacao;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Avaliacao;
import br.edu.ufcspa.balance.modelo.Paciente;

public class OldTimerActivity extends AppCompatActivity {

    OldTimerActivity This = this;

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

    private static final int  MIN_ALTURA= 20;
    private static final int  MAX_ALTURA= 250;
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

        mostrarDialogPernas();

    }

    public void mostrarDialogPernas(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.pernas_dialog);

        View btnUmaPerna = dialog.findViewById(R.id.layout_uma_perna);
        View btnDuasPernas = dialog.findViewById(R.id.layout_duas_pernas);

        btnUmaPerna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_uma_perna);
                imageView.setColorFilter(ContextCompat.getColor(This, R.color.colorPrimary));
                modoPernas = Avaliacao.UMA_PERNA;
                dialog.dismiss();
                mostrarDialogOlhos();
            }
        });

        btnDuasPernas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_duas_pernas);
                imageView.setColorFilter(ContextCompat.getColor(This, R.color.colorPrimary));
                modoPernas = Avaliacao.DUAS_PERNAS;
                dialog.dismiss();
                mostrarDialogOlhos();
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

    public void mostrarDialogDuracao(){
        final Dialog dialog = new Dialog(This);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.duracao_dialog);

        View btn60 = dialog.findViewById(R.id.txt60);
        View btn45 = dialog.findViewById(R.id.txt45);
        View btn30 = dialog.findViewById(R.id.txt30);
        View btn15 = dialog.findViewById(R.id.txt15);

        btn60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duracao = 60;
                dialog.dismiss();
                mostrarDialogAltura();
            }
        });

        btn45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duracao = 45;
                dialog.dismiss();
                mostrarDialogAltura();
            }
        });

        btn30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duracao = 30;
                dialog.dismiss();
                mostrarDialogAltura();
            }
        });

        btn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duracao = 15;
                dialog.dismiss();
                mostrarDialogAltura();
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

    public void mostrarDialogOlhos(){
        final Dialog dialog = new Dialog(This);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.olhos_dialog);

        View btnOlhosAbertos = dialog.findViewById(R.id.layout_olhos_abertos);
        View btnOlhosFechados = dialog.findViewById(R.id.layout_olhos_fechados);

        btnOlhosAbertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_olhos_abertos);
                imageView.setColorFilter(ContextCompat.getColor(This, R.color.colorPrimary));
                modoOlhos = Avaliacao.OLHOS_ABERTOS;
                dialog.dismiss();
                mostrarDialogDuracao();
            }
        });

        btnOlhosFechados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_olhos_fechados);
                imageView.setColorFilter(ContextCompat.getColor(This, R.color.colorPrimary));
                modoOlhos = Avaliacao.OLHOS_FECHADOS;
                dialog.dismiss();
                mostrarDialogDuracao();
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

    private void mostrarDialogTimer(){

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
                if(time >= 10){
                    time -= 5;
                    pickerTimer.setText(time+"");
                }
            }
        });

        /* Aumenta timer*/
        btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = Integer.parseInt(pickerTimer.getText().toString());
                if(time <=55){
                    time+=5;
                    pickerTimer.setText(time+"");
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

    private void mostrarDialogAltura(){

        final Dialog dialog = new Dialog(This);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.altura_dialog);

        final View btnConfirmar = dialog.findViewById(R.id.btn_confirmar);
        LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.layout_edit_text);
        final EditText txtAltura = new EditText(this);
        txtAltura.setWidth(200);

        layout.addView(txtAltura);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    double altura = Long.parseLong(txtAltura.getText().toString());

                    if(altura >= MIN_ALTURA && altura <= MAX_ALTURA){
                        This.altura = altura;
                        dialog.dismiss();
                        mostrarDialogTimer();
                    }
                }catch (Exception e){

                }
            }
        });

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

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


    public void iniciarTimer(){

        /*Desabilita o bloqueio da tela por timeout*/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        txtTimer.setVisibility(View.VISIBLE);
        txtPrepare.setVisibility(View.VISIBLE);

         timer = new CountDownTimer((segundosTimer)*1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    txtTimer.setText(""+millisUntilFinished / 1000);

                    if(millisUntilFinished / 1000 <=3){
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
        Intent intentVaiPraListaDeAvaliacoes = new Intent(OldTimerActivity.this, SensoresActivity.class);
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

        if(timer != null)
            timer.cancel();
    }

    private void mostrarDialogVoltar() {

        if(backCounter >= 1)
            return;

        backCounter++;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Voltar");
        builder.setMessage("Deseja cancelar esta avaliação?");
        builder.setPositiveButton(getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(timer != null)
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
