package br.edu.ufcspa.balance.controle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.controle.cadastro.CadastroActivity;
import br.edu.ufcspa.balance.controle.perfil.PacientesAdapter;
import br.edu.ufcspa.balance.controle.perfil.PerfilActivity;
import br.edu.ufcspa.balance.modelo.Paciente;

/**
 * Classe principal que exibe a lista de pacientes e opção de adicionar novo paciente
 */
public class MainActivity extends AppCompatActivity {

    private ListView listaPacientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciaComponentes();
        registerForContextMenu(listaPacientes);
    }

    private void iniciaComponentes() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carregaLista();
        listaPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Paciente paciente = (Paciente) listaPacientes.getItemAtPosition(position);
                Intent intentVaiProPerfil = new Intent(MainActivity.this, PerfilActivity.class);
                intentVaiProPerfil.putExtra("paciente", paciente);
                startActivity(intentVaiProPerfil);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btAdicionarPaciente);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
            }
        });
    }

    private void carregaLista() {
        listaPacientes = (ListView) findViewById(R.id.lista_pacientes);
        List<Paciente> pacientes;
        try {
            pacientes = Paciente.listAll(Paciente.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
            pacientes = new ArrayList<>();
        }
        Collections.sort(pacientes); // Coloca em ordem alfabética
        PacientesAdapter adapter = new PacientesAdapter(this, pacientes);
        listaPacientes.setAdapter(adapter);

        if (listaPacientes.getCount() == 0) {
            findViewById(R.id.textInicial).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.textInicial).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Paciente paciente = (Paciente) listaPacientes.getItemAtPosition(info.position);


        MenuItem ligar = menu.add("Ligar");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 123);

                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + paciente.getTelefone()));
                    startActivity(intentLigar);

                }
                return false;
            }
        });

        MenuItem enviarSms = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + paciente.getTelefone()));
        enviarSms.setIntent(intentSMS);

        MenuItem enviarEmail = menu.add("Enviar Email");
        enviarEmail.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String[] endereco = {paciente.getEmail()};
                composeEmail(endereco, "Avaliacao da Caminhada de Seis Minutos");
                return false;
            }
        });

    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
