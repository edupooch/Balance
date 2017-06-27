package br.edu.ufcspa.balance.controle.perfil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.controle.avaliacao.PreTesteActivity;
import br.edu.ufcspa.balance.controle.avaliacao.ResultadoActivity;
import br.edu.ufcspa.balance.controle.cadastro.CadastroActivity;
import br.edu.ufcspa.balance.modelo.Avaliacao;
import br.edu.ufcspa.balance.modelo.OperacoesBanco;
import br.edu.ufcspa.balance.modelo.Calcula;
import br.edu.ufcspa.balance.modelo.Paciente;


public class PerfilActivity extends AppCompatActivity {

    private Paciente paciente;
    private ArrayList<Avaliacao> avaliacoes;
    private static int TAMANHO_ESPACO_ENTRE_ITENS = 4;
    private static int TAMANHO_ITEM_AVALIACAO = 75 + TAMANHO_ESPACO_ENTRE_ITENS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        escreveDados();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaListaAvaliacoes();
    }

    private void escreveDados() {
        Intent intent = getIntent();
        paciente = (Paciente) intent.getSerializableExtra("paciente");
        setTitle(paciente.getNome());

        escreveDadosCadastrais();
        escreveDadosContato();
        escreveInfoClinicas();
    }

    private void escreveDadosCadastrais() {
        boolean temDadosPessoais = preparaCampo(paciente.getMassa(), R.id.layout_peso, R.id.text_peso) |
                preparaCampo(paciente.getAltura(), R.id.layout_altura, R.id.text_altura) |
                preparaCampo(paciente.getObs(), R.id.layout_observacoes, R.id.text_obs) |
                preparaCampo(Calcula.idadeEmAnos(paciente.getDataNascimento()), R.id.layout_idade, R.id.text_idade);

        if (paciente.getGenero() == null) {
            findViewById(R.id.layout_genero).setVisibility(View.GONE);
        } else if (paciente.getGenero() == Paciente.FEMININO) {
            TextView textoGenero = (TextView) findViewById(R.id.text_genero);
            textoGenero.setText(R.string.feminino);
            temDadosPessoais = true;
        } else if (paciente.getGenero() == Paciente.MASCULINO) {
            TextView textoGenero = (TextView) findViewById(R.id.text_genero);
            textoGenero.setText(R.string.masculino);
            temDadosPessoais = true;
        }
        if (!temDadosPessoais) {
            findViewById(R.id.titulo_dados_cadastrais).setVisibility(View.GONE);
        }
    }

    private void escreveDadosContato() {
        boolean temContato = preparaCampo(paciente.getEmail(), R.id.layout_email, R.id.text_email) |
                preparaCampo(paciente.getTelefone(), R.id.layout_telefone, R.id.text_telefone);
        if (!temContato) {
            findViewById(R.id.titulo_contato).setVisibility(View.GONE);
        }
    }

    private void escreveInfoClinicas() {
        boolean temInfoClinicas = preparaCampo(paciente.getDataAnamnese(), R.id.layout_data_anamnese,
                R.id.text_data_anamnese) |
                preparaCampo(paciente.getDataDiagnostico(), R.id.layout_data_diagnostico,
                        R.id.text_data_diagnostico) |
                preparaCampo(paciente.getDiagnostico(), R.id.layout_diagnostico,
                        R.id.text_diagnostico) |
                preparaCampo(paciente.getHistoricoDoencaAtual(), R.id.layout_historico_atual,
                        R.id.text_doenca_atual) |
                preparaCampo(paciente.getHistoricoDoencasAnteriores(),
                        R.id.layout_historico_anteriores, R.id.text_doencas_anteriores) |
                preparaCampo(paciente.getProcedimentosTerapeuticos(), R.id.layout_procedimentos,
                        R.id.text_proc_terapeuticos);
        if (!temInfoClinicas) {
            findViewById(R.id.titulo_info_clinicas).setVisibility(View.GONE);
        }
    }


    private boolean preparaCampo(String texto, int resIdLayout, int resIdTextView) {
        if (texto == null || texto.isEmpty()) {
            findViewById(resIdLayout).setVisibility(View.GONE);
            return false;
        } else {
            Log.d("texto", texto);
            TextView textView = (TextView) findViewById(resIdTextView);
            String textoBase = textView.getText().toString();
            textView.setText(textoBase + texto);
            return true;
        }
    }

    private boolean preparaCampo(Double numero, int resIdLayout, int resIdTextView) {
        if (numero == null || numero == 0) {
            findViewById(resIdLayout).setVisibility(View.GONE);
            return false;
        } else {
            TextView textView = (TextView) findViewById(resIdTextView);
            String formato;
            if (resIdTextView == R.id.text_altura) {
                formato = "%.0f cm";
            } else {
                formato = "%.0f kg";
            }
            textView.setText(String.format(Locale.US, formato, numero));
            return true;
        }
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
                builder.setMessage(getString(R.string.dialog_deletar_paciente));
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

        avaliacoes = OperacoesBanco.getAvaliacoes(paciente);

        if (!avaliacoes.isEmpty()) {
            popularListaAvaliacoes(avaliacoes);
            TextView txtMsgVazio = (TextView) findViewById(R.id.text_sem_avaliacoes);
            txtMsgVazio.setText(R.string.clique_na_avaliacao_para_mais_detalhes);
        } else {
            popularListaAvaliacoes(avaliacoes);
            TextView txtMsgVazio = (TextView) findViewById(R.id.text_sem_avaliacoes);
            txtMsgVazio.setText(R.string.nenhuma_avaliacao_realizada_ainda);
            TextView txtVerMais = (TextView) findViewById(R.id.text_ver_mais);
            txtVerMais.setVisibility(View.GONE);
        }

    }

    private void popularListaAvaliacoes(List<Avaliacao> avaliacoes) {
        final ListView listaAvaliacoes = (ListView) findViewById(R.id.lista_avaliacoes_anteriores);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) listaAvaliacoes.getLayoutParams();

        if (avaliacoes.size() <= 3) {
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

                Avaliacao avaliacao;
                try {
                    avaliacao = (Avaliacao) listaAvaliacoes.getItemAtPosition(position);
                    Intent intentResultado = new Intent(PerfilActivity.this, ResultadoActivity.class);
                    intentResultado.putExtra("avaliação", avaliacao);
                    startActivity(intentResultado);
                } catch (Exception e) {
                    e.printStackTrace();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
                    builder.setMessage("Não foi possível abrir a avaliação");

                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
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
        Intent intentVaiPraListaDeAvaliacoes = new Intent(PerfilActivity.this, PreTesteActivity.class);
        intentVaiPraListaDeAvaliacoes.putExtra("paciente", paciente);
        startActivity(intentVaiPraListaDeAvaliacoes);
    }


}
