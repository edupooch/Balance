package br.edu.ufcspa.balance.controle;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.Testes.SensorsActivity;
import br.edu.ufcspa.balance.modelo.Avaliacao;
import br.edu.ufcspa.balance.modelo.Calcula;
import br.edu.ufcspa.balance.modelo.Paciente;


public class PerfilActivity extends AppCompatActivity {

    private PerfilActivity activity = this;
    private Paciente paciente;
    private ArrayList<Avaliacao> avaliacoes;
    private static int TAMANHO_ITEM_AVALIACAO = 92+1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        iniciaComponentes();
    }



    private void iniciaComponentes() {
        escreveDados();
        carregaListaAvaliacoes();

//        FloatingActionButton btIniciarTeste = (FloatingActionButton) findViewById(R.id.btnComecarAvaliacao);
//        btIniciarTeste.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentVaiProPreTeste = new Intent(PerfilActivity.this, ListaPacientesActivity.class);
//                //intentVaiProPreTeste.putExtra("paciente", paciente);
//                startActivity(intentVaiProPreTeste);
//            }
//        });


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
        //TextView textoIMC = (TextView) findViewById(R.id.text_imc);
        TextView textoTelefone = (TextView) findViewById(R.id.text_telefone);
        TextView textoEmail = (TextView) findViewById(R.id.text_email);
        TextView textoIdade = (TextView) findViewById(R.id.text_idade);
        TextView textoGenero = (TextView) findViewById(R.id.text_genero);
        TextView textoObs = (TextView) findViewById(R.id.text_obs);
        TextView textoDataAnamnsese = (TextView) findViewById(R.id.text_dtanamnese);
        TextView textoDataDiagnostico = (TextView) findViewById(R.id.text_dtdiagnostico);
        TextView textoDiagClinico = (TextView) findViewById(R.id.text_diag_clin);
        TextView textoHistDoencaAtual = (TextView) findViewById(R.id.text_doenca_atual);
        TextView textoHistDoencaAnterior = (TextView) findViewById(R.id.text_doencas_anteriores);
        TextView textoProcedimentosTerapeuticos = (TextView) findViewById(R.id.text_proc_terapeuticos);

        activity.setTitle(paciente.getNome());
        textoPeso.setText(String.format(Locale.US, "%.2f kg", paciente.getMassa()));
        textoAltura.setText(String.format(Locale.US, "%.0f cm", paciente.getEstatura()));
        //textoIMC.setText(String.format(Locale.US, "IMC %.2f", Calcula.imc(paciente.getMassa(), paciente.getEstatura())));
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


        textoIdade.setText(Calcula.idadeEmAnos(paciente.getDataNascimento()));

        if (paciente.getGenero() == Paciente.FEMININO) {
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

    private void carregaListaAvaliacoes() {

        try {
            avaliacoes = (ArrayList<Avaliacao>) Avaliacao.find(Avaliacao.class, "id_Paciente = ?", String.valueOf(this.paciente.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            avaliacoes = new ArrayList<Avaliacao>();
        }

        if (!avaliacoes.isEmpty()) {
            popularListaAvaliacoes(avaliacoes);
            TextView txtMsgVazio = (TextView) findViewById(R.id.text_sem_avaliacoes);
            txtMsgVazio.setText("Clique na Avaliação para mais detalhes.");
        } else {
            TextView txtMsgVazio = (TextView) findViewById(R.id.text_sem_avaliacoes);
            txtMsgVazio.setText("Nenhuma avaliação realizada ainda.");
            TextView txtVerMais = (TextView) findViewById(R.id.text_ver_mais);
            txtVerMais.setVisibility(View.GONE);
        }
    }

    private void popularListaAvaliacoes(List<Avaliacao> avaliacoes) {
        final ListView listaAvaliacoes = (ListView) findViewById(R.id.lista_avaliacoes_anteriores);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) listaAvaliacoes.getLayoutParams();

        if (avaliacoes.size() < 3) {
            lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TAMANHO_ITEM_AVALIACAO * avaliacoes.size(), getResources().getDisplayMetrics());
            listaAvaliacoes.setLayoutParams(lp);
            TextView txtVerMais = (TextView) findViewById(R.id.text_ver_mais);
            txtVerMais.setVisibility(View.GONE);
        } else {
            lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TAMANHO_ITEM_AVALIACAO * 3, getResources().getDisplayMetrics());
            listaAvaliacoes.setLayoutParams(lp);
            TextView txtVerMais = (TextView) findViewById(R.id.text_ver_mais);
            txtVerMais.setVisibility(View.VISIBLE);
        }

        AvaliacoesAdapter adapter = new AvaliacoesAdapter(this, avaliacoes);
        listaAvaliacoes.setAdapter(adapter);

        listaAvaliacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Avaliacao avaliacao = (Avaliacao) listaAvaliacoes.getItemAtPosition(position);
                Intent intentResultado = new Intent(PerfilActivity.this,ResultadoActivity.class);
                intentResultado.putExtra("avaliação", avaliacao);
                startActivity(intentResultado);

            }
        });


    }

    public void txtVerMais_Click(View view) {

        Intent intentVaiPraListaDeAvaliacoes = new Intent(PerfilActivity.this, ListaAvaliacoesActivity.class);
        intentVaiPraListaDeAvaliacoes.putExtra("avaliacoes", avaliacoes);
        intentVaiPraListaDeAvaliacoes.putExtra("paciente", paciente);
        startActivity(intentVaiPraListaDeAvaliacoes);

    }

    public void btnComecarAvaliacao_Click(View view) {

        Intent intentVaiPraListaDeAvaliacoes = new Intent(PerfilActivity.this, SensorsActivity.class);
        intentVaiPraListaDeAvaliacoes.putExtra("paciente", paciente);
        startActivity(intentVaiPraListaDeAvaliacoes);

    }

    public void btnDeletarAvaliacao_Click(View view) {

        view = (View) view.getParent().getParent().getParent();
        TextView txtAvaliacao = (TextView) view.findViewById(R.id.text_id_avaliacao);
        final Long idAvalicao = Long.parseLong(txtAvaliacao.getText().toString());


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja excluir esta avaliação?");

        builder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Avaliacao avaliacao = Avaliacao.findById(Avaliacao.class, idAvalicao);
                        avaliacao.delete();
                        carregaListaAvaliacoes();
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


//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
//        MenuItem deletar = menu.add("Deletar Avaliacao");
//        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//                Avaliacao teste  = (Avaliacao) listaTestes.getItemAtPosition(info.position);
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
        carregaListaAvaliacoes();
    }
}
