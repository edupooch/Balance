package br.edu.ufcspa.balance.controle.perfil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.controle.avaliacao.ResultadoActivity;
import br.edu.ufcspa.balance.modelo.Avaliacao;
import br.edu.ufcspa.balance.modelo.OperacoesBanco;
import br.edu.ufcspa.balance.modelo.Paciente;

public class ListaAvaliacoesActivity extends AppCompatActivity {

    private ListaAvaliacoesActivity This = this;

    private ArrayList<Avaliacao> avaliacoes;
    private ListView listaAvaliacoes;
    private Paciente paciente;

    private final static int TAMANHO_ESPACO_ENTRE_ITENS = 4;
    private final static int TAMANHO_ITEM_AVALIACAO = 102 + TAMANHO_ESPACO_ENTRE_ITENS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_avaliacoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            Intent intent = getIntent();
            avaliacoes = (ArrayList<Avaliacao>) intent.getSerializableExtra("avaliacoes");
            paciente = (Paciente) intent.getSerializableExtra("paciente");

        TextView txtNomePaciente = (TextView) findViewById(R.id.text_nome_paciente);
        txtNomePaciente.setText("Avaliações de "+paciente.getNome());

        carregaListaAvaliacoes(this.avaliacoes);

    }

    private void carregaListaAvaliacoes(List<Avaliacao> avaliacoes){

        listaAvaliacoes = (ListView) findViewById(R.id.lista_avaliacoes);

        AvaliacoesAdapter adapter = new AvaliacoesAdapter(this, avaliacoes);
        listaAvaliacoes.setAdapter(adapter);

        //Coloca o tamanho da lista em dp de acordo com o numero de pacientes
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) listaAvaliacoes.getLayoutParams();
        lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TAMANHO_ITEM_AVALIACAO * avaliacoes.size(), getResources().getDisplayMetrics());;
        listaAvaliacoes.setLayoutParams(lp);

        listaAvaliacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Avaliacao avaliacao = null;
                try {
                    avaliacao = (Avaliacao) listaAvaliacoes.getItemAtPosition(position);
                    Intent intentResultado = new Intent(ListaAvaliacoesActivity.this,ResultadoActivity.class);
                    intentResultado.putExtra("avaliação", avaliacao);
                    startActivity(intentResultado);
                } catch (Exception e) {
                    e.printStackTrace();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(This);
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
                ;

            }
        });

    }

    public void btnDeletarAvaliacao_Click(View view){

        view = (View) view.getParent().getParent().getParent();
        TextView txtAvaliacao = (TextView) view.findViewById(R.id.text_id_avaliacao);
        final Long idAvalicao = Long.parseLong(txtAvaliacao.getText().toString());



        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja excluir esta avaliação?");

        builder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Avaliacao avaliacao = Avaliacao.findById(Avaliacao.class, idAvalicao);
                        avaliacao.delete();

                        This.avaliacoes = OperacoesBanco.getAvaliacoes(paciente);
                        This.carregaListaAvaliacoes(This.avaliacoes);
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
