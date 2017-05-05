package br.edu.ufcspa.balance.controle;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import br.edu.ufcspa.balance.dao.PacienteDAO;
import br.edu.ufcspa.balance.modelo.Paciente;
import br.edu.ufcspa.balance.R;

public class FormularioActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    public static final int REQUEST_CODE_CAMERA = 123;
    private FormularioHelper helper;
    private Long idPaciente;
    private EditText date;
    private String caminhoFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        iniciaComponentes();
    }

    private void iniciaComponentes() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        idPaciente = null;
        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Paciente paciente = (Paciente) intent.getSerializableExtra("paciente");

        if (paciente != null) {
            helper.preencheFormulário(paciente);
            idPaciente = paciente.getId();
        }

        date = (EditText) findViewById(R.id.edTextDataNascimento);
        //Text Watcher para aceitar apenas datas no formato dd/mm/aaaa
        TextWatcher watcherData = new TextWatcherData(date);
        date.addTextChangedListener(watcherData);

        ImageButton btData = (ImageButton) findViewById(R.id.btData);
        btData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(FormularioActivity.this, FormularioActivity.this, now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));

                dpd.show();

            }
        });

        //INICIA O BOTÃO DA CAMERA
        ImageButton btFoto = (ImageButton) findViewById(R.id.btFoto);
        btFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, REQUEST_CODE_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //se a ação nao foi cancelada
            if (requestCode == REQUEST_CODE_CAMERA) {
                //Abre a foto tirada
                helper.carregaImagem(caminhoFoto);
            }
        }
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

                    Paciente paciente = helper.pegaPacienteFromFields(idPaciente);
                    PacienteDAO dao = new PacienteDAO(this);

                    if (idPaciente == null) {
                        dao.insere(paciente);
                        Toast.makeText(getApplicationContext(), "Paciente " + paciente.getNome() + " adicionado!", Toast.LENGTH_LONG).show();

                    } else {
                        dao.altera(paciente);
                        Intent intentVaiProPerfil = new Intent(FormularioActivity.this, PerfilActivity.class);
                        intentVaiProPerfil.putExtra("paciente", paciente);
                        startActivity(intentVaiProPerfil);
                        Toast.makeText(getApplicationContext(), "Paciente " + paciente.getNome() + " alterado!", Toast.LENGTH_LONG).show();
                    }
                    dao.close();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("caminho_foto", caminhoFoto);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        caminhoFoto = savedInstanceState.getString("caminho_foto");
        if (caminhoFoto != null) {
            System.out.println("foto" + savedInstanceState.getString("caminho_foto"));
            helper.carregaImagem(caminhoFoto);
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        EditText edTextData = (EditText) findViewById(R.id.edTextDataNascimento);
        String strData = String.format(Locale.getDefault(), "%02d", dayOfMonth) +
                "/" + String.format(Locale.getDefault(), "%02d", monthOfYear + 1) +
                "/" + String.format(Locale.getDefault(), "%04d", year);

        edTextData.setText(strData);
    }
}
