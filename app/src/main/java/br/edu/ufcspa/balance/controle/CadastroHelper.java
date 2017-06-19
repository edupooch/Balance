package br.edu.ufcspa.balance.controle;

import android.widget.EditText;
import android.widget.RadioButton;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Paciente;

/**
 * Created by edupooch on 17/02/16.
 */

class CadastroHelper {



    private EditText edTextNomePaciente;
    private EditText edTextDataNascimento;
    private RadioButton btnMasculino;
    private RadioButton btnFeminino;
    private EditText edTextPeso;
    private EditText edTextAltura;
    private EditText edTextObs;
    private EditText edTextDataAnamnese;
    private EditText edTextDiagnostico;
    private EditText edTextDataDiagnostico;
    private EditText edTextCurrentDisease;
    private EditText edTextPreviousDisease;
    private EditText edTextProcedimentos;
    private EditText edTextTelefone;
    private EditText edTextEmail;

    CadastroHelper(final CadastroActivity activity) {
        edTextNomePaciente = (EditText) activity.findViewById(R.id.edTextNomePaciente);
        edTextDataNascimento = (EditText) activity.findViewById(R.id.edTextDataNascimento);
        btnMasculino = (RadioButton) activity.findViewById(R.id.btnMasculino);
        btnFeminino = (RadioButton) activity.findViewById(R.id.btnFeminino);
        edTextPeso = (EditText) activity.findViewById(R.id.edTextPeso);
        edTextAltura = (EditText) activity.findViewById(R.id.edTextAltura);
        edTextObs = (EditText) activity.findViewById(R.id.edTextObs);
        edTextDataAnamnese = (EditText) activity.findViewById(R.id.edtext_data_anamnese);
        edTextDiagnostico = (EditText) activity.findViewById(R.id.edTextDiagnostico);
        edTextDataDiagnostico = (EditText) activity.findViewById(R.id.edtext_data_diagnostico);
        edTextCurrentDisease = (EditText) activity.findViewById(R.id.edTextCurrentDisease);
        edTextPreviousDisease = (EditText) activity.findViewById(R.id.edTextPreviousDisease);
        edTextProcedimentos = (EditText) activity.findViewById(R.id.edtext_procedimentos);
        edTextTelefone = (EditText) activity.findViewById(R.id.edTextTelefone);
        edTextEmail = (EditText) activity.findViewById(R.id.edTextEmail);
    }


    Paciente pegaInfoDosCampos(Long id) {
        Paciente paciente = new Paciente();

        if (id != null) paciente.setId(id);
        paciente.setNome(edTextNomePaciente.getText().toString());
        paciente.setDataNascimento(edTextDataNascimento.getText().toString());

        if (!edTextAltura.getText().toString().isEmpty())
            paciente.setEstatura(Double.valueOf(edTextAltura.getText().toString()));
        if (!edTextPeso.getText().toString().isEmpty())
            paciente.setMassa(Double.valueOf(edTextPeso.getText().toString()));

        paciente.setTelefone(edTextTelefone.getText().toString());
        paciente.setEmail(edTextEmail.getText().toString());
        paciente.setObs(edTextObs.getText().toString());

        paciente.setAnamnesisDate(edTextDataAnamnese.getText().toString());
        paciente.setDiagnostico(edTextDiagnostico.getText().toString());
        paciente.setDataDiagnostico(edTextDataDiagnostico.getText().toString());

        paciente.setHistoricoDoencaAtual(edTextCurrentDisease.getText().toString());
        paciente.setHistoricoDoencasAnteriores(edTextPreviousDisease.getText().toString());
        paciente.setProcedimentosTerapeuticos(edTextProcedimentos.getText().toString());

        if (btnMasculino.isChecked()) {
            paciente.setGenero(Paciente.MASCULINO);
        } else if (btnFeminino.isChecked()) {
            paciente.setGenero(Paciente.FEMININO);
        }
        return paciente;
    }


    void preencheFormulário(Paciente paciente) {
        edTextNomePaciente.setText(paciente.getNome());
        edTextDataNascimento.setText(paciente.getDataNascimento());

        if (paciente.getGenero() == 0) {
            btnFeminino.setChecked(true);
        } else {
            btnMasculino.setChecked(true);
        }

        edTextPeso.setText(String.valueOf(paciente.getMassa()));
        edTextAltura.setText(String.valueOf(paciente.getAltura()));
        edTextTelefone.setText(paciente.getTelefone());

        edTextEmail.setText(paciente.getEmail());
        edTextObs.setText(paciente.getObs());

        edTextDataAnamnese.setText(paciente.getDataAnamnese());
        edTextDiagnostico.setText(paciente.getDiagnostico());
        edTextDataDiagnostico.setText(paciente.getDataDiagnostico());
        edTextCurrentDisease.setText(paciente.getHistoricoDoencaAtual());
        edTextPreviousDisease.setText(paciente.getHistoricoDoencasAnteriores());
        edTextProcedimentos.setText(paciente.getProcedimentosTerapeuticos());
    }

    boolean validateFields() {
        return (!edTextNomePaciente.getText().toString().isEmpty());
    }

}

