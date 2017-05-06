package br.edu.ufcspa.balance.controle;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.util.Locale;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.dao.PacienteDAO;
import br.edu.ufcspa.balance.modelo.Calcula;
import br.edu.ufcspa.balance.modelo.Paciente;


public class PerfilActivity extends AppCompatActivity {
    private PerfilActivity activity = this;
    private Paciente paciente;
    private ListView listaTestes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        iniciaComponentes();
    }

    private void iniciaComponentes() {
        escreveDados();
        carregaLista();

        FloatingActionButton btIniciarTeste = (FloatingActionButton) findViewById(R.id.btnPlayTeste);
        btIniciarTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProPreTeste = new Intent(PerfilActivity.this, ListaPacientesActivity.class);
                //intentVaiProPreTeste.putExtra("paciente", paciente);
                startActivity(intentVaiProPreTeste);
            }
        });


        ImageView campoFoto = (ImageView) findViewById(R.id.foto_paciente);
        String caminhoFoto = paciente.getCaminhoFoto();
        if (caminhoFoto != null) {
            findViewById(R.id.icone_user).setVisibility(View.GONE);
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap bm = BitmapFactory.decodeFile(caminhoFoto, options);
            if (bm.getWidth() > bm.getHeight()) {
                bm = Bitmap.createScaledBitmap(bm, 400, 250, false);
                //foto vertical
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
                FileOutputStream out = null;

            } else {
                //horizontal
                bm = Bitmap.createScaledBitmap(bm, 400, 250, false);
            }


            campoFoto.setImageBitmap(bm);
            campoFoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

    }

    private void escreveDados() {

        Intent intent = getIntent();
        paciente = (Paciente) intent.getSerializableExtra("paciente");

        TextView textoPeso = (TextView) findViewById(R.id.text_peso);
        TextView textoAltura = (TextView) findViewById(R.id.text_altura);
        TextView textoIMC = (TextView) findViewById(R.id.text_imc);
        TextView textoTelefone = (TextView) findViewById(R.id.text_telefone);
        TextView textoEmail = (TextView) findViewById(R.id.text_email);
        TextView textoIdade = (TextView) findViewById(R.id.text_idade);
        TextView textoGenero = (TextView) findViewById(R.id.text_genero);
        TextView textoObs = (TextView) findViewById(R.id.text_obs);

        activity.setTitle(paciente.getNome());
        textoPeso.setText(String.format(Locale.US, "%.2f kg", paciente.getMassa()));
        textoAltura.setText(String.format(Locale.US, "%.0f cm", paciente.getEstatura()));
        textoIMC.setText(String.format(Locale.US, "IMC %.2f", Calcula.imc(paciente.getMassa(), paciente.getEstatura())));
        if (paciente.getTelefone().isEmpty()) {
            if (paciente.getEmail().isEmpty()) {//tirar o titulo contato se não tem telefone nem email
                findViewById(R.id.titulo_contato).setVisibility(View.GONE);
                findViewById(R.id.titulo_contato_sublinhado).setVisibility(View.GONE);
            }
            findViewById(R.id.layout_telefone).setVisibility(View.GONE);
        } else {
            textoTelefone.setText(paciente.getTelefone());
        }
        if (paciente.getEmail().isEmpty()) {
            findViewById(R.id.layout_email).setVisibility(View.GONE);
        } else {
            textoEmail.setText(paciente.getEmail());
        }

        textoIdade.setText(Calcula.idadeCompleta(paciente.getDataNascimento()));

        if (paciente.getGenero() == 0) {
            textoGenero.setText(R.string.feminino);
        } else {
            textoGenero.setText(R.string.masculino);
        }

        if (paciente.getObs().isEmpty()) {
            findViewById(R.id.layout_observacoes).setVisibility(View.GONE);
        } else {
            textoObs.setText(paciente.getObs());
        }

//        String[] arrayData = paciente.getDataNascimento().toString().split("-");
//        String strData = arrayData[2] + "/" + arrayData[1] + "/" + arrayData[0];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_perfil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_editar:
                Intent intentVaiProFormulario = new Intent(PerfilActivity.this, CadastroActivity.class);
                intentVaiProFormulario.putExtra("paciente", paciente);
                startActivity(intentVaiProFormulario);
                finish();
                break;

            case R.id.action_deletar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle(getString(R.string.atencao_deletar));
                builder.setMessage(getString(R.string.dialog_deletar));
                builder.setPositiveButton(getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Paciente pacienteBanco = Paciente.findById(Paciente.class, paciente.getId());
                        pacienteBanco.delete();
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
        return super.onOptionsItemSelected(item);
    }

    private void carregaLista() {
//        listaTestes = (ListView) findViewById(R.id.lista_testes);
////        TesteDAO dao = new TesteDAO(this);
////        List<Teste> testes = dao.buscaTestes(paciente);
//
//        //Coloca o tamanho da lista em dp de acordo com o numero de pacientes (75dp por paciente)
//        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) listaTestes.getLayoutParams();
//        lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75 * testes.size(), getResources().getDisplayMetrics());;
//        listaTestes.setLayoutParams(lp);
//
////        registerForContextMenu(listaTestes);
//        if (testes.size() == 0){
//            findViewById(R.id.text_nenhum_teste).setVisibility(View.VISIBLE);
//        } else{
//            findViewById(R.id.text_nenhum_teste).setVisibility(View.GONE);
//        }
//
//        Collections.reverse(testes);
//
////        ArrayAdapter<Teste> adapter =
////                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, testes);
//
//        listaTestes.setAdapter(new TestesAdapter(this,testes));
//
//        listaTestes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
//                Teste teste = (Teste) listaTestes.getItemAtPosition(position);
//                // teste clicado
//
//                Intent intentVaiPraAnalise = new Intent(PerfilActivity.this, AnaliseTesteActivity.class);
//                intentVaiPraAnalise.putExtra("teste", teste);
//                startActivity(intentVaiPraAnalise);
//            }
//        });
//
//        dao.close();
    }
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
//        MenuItem deletar = menu.add("Deletar Teste");
//        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//                Teste teste  = (Teste) listaTestes.getItemAtPosition(info.position);
//                TesteDAO dao = new TesteDAO(PerfilActivity.this);
//
//                dao.deleta(teste);
//                dao.close();
//
//                carregaLista();
//                return false;
//            }
//        });
//    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }
}
