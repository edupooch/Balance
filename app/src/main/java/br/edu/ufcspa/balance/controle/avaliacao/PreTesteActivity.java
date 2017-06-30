package br.edu.ufcspa.balance.controle.avaliacao;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Avaliacao;
import br.edu.ufcspa.balance.modelo.Paciente;

public class PreTesteActivity extends AppCompatActivity {

    private static final int MIN_ALTURA = 20;
    private static final int MAX_ALTURA = 250;


    int modoPernas;
    int modoOlhos;
    int duracao;
    double altura;
    Paciente paciente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_teste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        paciente = (Paciente) intent.getSerializableExtra("paciente");

        final EditText txtAltura = (EditText) findViewById(R.id.text_altura_celular);
        ////////
        View btnUmaPerna = findViewById(R.id.layout_uma_perna);
        View btnDuasPernas = findViewById(R.id.layout_duas_pernas);
        final int corAtiva = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        final int corInativa = ContextCompat.getColor(getApplicationContext(), R.color.textColorFraca);

        setPadroes();

        btnUmaPerna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_uma_perna);
                imageView.setColorFilter(corAtiva);
                modoPernas = Avaliacao.UMA_PERNA;

                ImageView outra = (ImageView) findViewById(R.id.icon_duas_pernas);
                outra.setColorFilter(corInativa);
            }
        });

        btnDuasPernas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_duas_pernas);
                imageView.setColorFilter(corAtiva);
                modoPernas = Avaliacao.DUAS_PERNAS;

                ImageView outra = (ImageView) findViewById(R.id.icon_uma_perna);
                outra.setColorFilter(corInativa);
            }
        });

        /////////////
        final TextView btn60 = (TextView) findViewById(R.id.txt60);
        final TextView btn45 = (TextView) findViewById(R.id.txt45);
        final TextView btn30 = (TextView) findViewById(R.id.txt30);
        final TextView btn15 = (TextView) findViewById(R.id.txt15);

        btn60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duracao = 60;
                btn60.setTextColor(corInativa);
                btn45.setTextColor(corInativa);
                btn30.setTextColor(corInativa);
                btn15.setTextColor(corInativa);
                ((TextView) v).setTextColor(corAtiva);
            }
        });

        btn45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duracao = 45;
                btn60.setTextColor(corInativa);
                btn45.setTextColor(corInativa);
                btn30.setTextColor(corInativa);
                btn15.setTextColor(corInativa);
                ((TextView) v).setTextColor(corAtiva);

            }
        });

        btn30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duracao = 30;
                btn60.setTextColor(corInativa);
                btn45.setTextColor(corInativa);
                btn30.setTextColor(corInativa);
                btn15.setTextColor(corInativa);
                ((TextView) v).setTextColor(corAtiva);

            }
        });

        btn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duracao = 15;
                btn60.setTextColor(corInativa);
                btn45.setTextColor(corInativa);
                btn30.setTextColor(corInativa);
                btn15.setTextColor(corInativa);
                ((TextView) v).setTextColor(corAtiva);

            }
        });

        ///////////////

        View btnOlhosAbertos = findViewById(R.id.layout_olhos_abertos);
        View btnOlhosFechados = findViewById(R.id.layout_olhos_fechados);

        btnOlhosAbertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_olhos_abertos);
                imageView.setColorFilter(corAtiva);
                modoOlhos = Avaliacao.OLHOS_ABERTOS;

                ImageView outra = (ImageView) findViewById(R.id.icon_olhos_fechados);
                outra.setColorFilter(corInativa);
            }
        });

        btnOlhosFechados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v.findViewById(R.id.icon_olhos_fechados);
                imageView.setColorFilter(corAtiva);
                modoOlhos = Avaliacao.OLHOS_FECHADOS;

                ImageView outra = (ImageView) findViewById(R.id.icon_olhos_abertos);
                outra.setColorFilter(corInativa);
            }
        });


        final View btConfirmar = findViewById(R.id.bt_confirmar);
        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificaAltura(txtAltura)) {
                    altura = Double.parseDouble(txtAltura.getText().toString());

                    Intent intentVaiProTimer = new Intent(PreTesteActivity.this, TimerActivity.class);
                    intentVaiProTimer.putExtra("paciente", paciente);
                    intentVaiProTimer.putExtra("modoPernas", modoPernas);
                    intentVaiProTimer.putExtra("modoOlhos", modoOlhos);
                    intentVaiProTimer.putExtra("duracao", duracao);
                    intentVaiProTimer.putExtra("altura", altura/100);
                    startActivity(intentVaiProTimer);
                    finish();
                }

            }
        });

    }

    private void setPadroes() {
        int corAtiva = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);

        ImageView pernas = (ImageView) findViewById(R.id.icon_duas_pernas);
        pernas.setColorFilter(corAtiva);
        modoPernas = Avaliacao.DUAS_PERNAS;

        ImageView olhos = (ImageView) findViewById(R.id.icon_olhos_abertos);
        olhos.setColorFilter(corAtiva);
        modoOlhos = Avaliacao.OLHOS_ABERTOS;

        final TextView btn60 = (TextView) findViewById(R.id.txt60);
        btn60.setTextColor(corAtiva);
        duracao = 60;

    }

    private boolean verificaAltura(EditText txtAltura) {
        if (txtAltura.getText().toString().isEmpty()) {
            txtAltura.setError("Preencha com o valor da altura");
            return false;
        }
        try {
            int valorAltura = Integer.valueOf(txtAltura.getText().toString());
            if (valorAltura >= MIN_ALTURA && valorAltura <= MAX_ALTURA) {
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "" + valorAltura, Toast.LENGTH_SHORT).show();
                txtAltura.setError("Não é um número válido");
                return false;
            }
        } catch (Exception e) {
            txtAltura.setError("Não é um número válido");
            return false;
        }
    }


    @Override
    public void onBackPressed() {
        mostrarDialogVoltar();
    }

    private void mostrarDialogVoltar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Voltar");
        builder.setMessage("Deseja cancelar esta avaliação?");
        builder.setPositiveButton(getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
