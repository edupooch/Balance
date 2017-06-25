package br.edu.ufcspa.balance.controle.cadastro;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.controle.perfil.PerfilActivity;
import br.edu.ufcspa.balance.modelo.Paciente;

public class CadastroActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private CadastroHelper helper;
    private Long idPaciente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        iniciaComponentes();
    }

    private void iniciaComponentes() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idPaciente = null;
        helper = new CadastroHelper(this);

        Intent intent = getIntent();
        Paciente paciente = (Paciente) intent.getSerializableExtra("paciente");

        if (paciente != null) {
            helper.preencheFormulário(paciente);
            idPaciente = paciente.getId();
        }

        configuraCampoDatas();
    }

    private void configuraCampoDatas() {
        EditText edTextDataNascimento = (EditText) findViewById(R.id.edTextDataNascimento);
        edTextDataNascimento.addTextChangedListener(new TextWatcherData(edTextDataNascimento));
        EditText edTextDataAnamnese = (EditText) findViewById(R.id.edtext_data_anamnese);
        edTextDataAnamnese.addTextChangedListener(new TextWatcherData(edTextDataAnamnese));
        EditText edTextDataDiagnostico = (EditText) findViewById(R.id.edtext_data_diagnostico);
        edTextDataDiagnostico.addTextChangedListener(new TextWatcherData(edTextDataDiagnostico));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario:
                if (helper.validateFields()) {
                    Paciente paciente = helper.pegaInfoDosCampos(idPaciente);

                    if (idPaciente == null) {
                        paciente.save();
                        Toast.makeText(getApplicationContext(), "Paciente " + paciente.getNome() + " adicionado!", Toast.LENGTH_LONG).show();

                    } else {
                        atualizaDados(paciente);
                        Intent intentVaiProPerfil = new Intent(CadastroActivity.this, PerfilActivity.class);
                        intentVaiProPerfil.putExtra("paciente", paciente);
                        startActivity(intentVaiProPerfil);
                        Toast.makeText(getApplicationContext(), "Paciente " + paciente.getNome() + " alterado!", Toast.LENGTH_LONG).show();
                    }
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Insira o nome do paciente", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void atualizaDados(Paciente pacienteAlterado) {
        Paciente pacienteBanco = Paciente.findById(Paciente.class, pacienteAlterado.getId());
        pacienteBanco.copy(pacienteAlterado);
        pacienteBanco.save();
    }



    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        EditText edTextData = (EditText) findViewById(R.id.edTextDataNascimento);
        String strData = String.format(Locale.getDefault(), "%02d", dayOfMonth) +
                "/" + String.format(Locale.getDefault(), "%02d", monthOfYear + 1) +
                "/" + String.format(Locale.getDefault(), "%04d", year);

        edTextData.setText(strData);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.abandonar_cadastro);
        builder.setMessage(getString(R.string.dialog_abandonar_cadastro));
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
