package br.edu.ufcspa.balance.controle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Paciente;

/**
 * Created by edupooch on 17/02/16.
 */

class CadastroHelper {


    private ImageButton btFoto;
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
        btFoto = (ImageButton) activity.findViewById(R.id.btFoto);
        edTextNomePaciente = (EditText) activity.findViewById(R.id.edTextNomePaciente);
        edTextDataNascimento = (EditText) activity.findViewById(R.id.edTextDataNascimento);
        btnMasculino = (RadioButton) activity.findViewById(R.id.btnMasculino);
        btnFeminino = (RadioButton) activity.findViewById(R.id.btnFeminino);
        edTextPeso = (EditText) activity.findViewById(R.id.edTextPeso);
        edTextAltura = (EditText) activity.findViewById(R.id.edTextAltura);
        edTextObs = (EditText) activity.findViewById(R.id.edTextObs);
        edTextDataAnamnese = (EditText) activity.findViewById(R.id.edTextDataAnamnese);
        edTextDiagnostico = (EditText) activity.findViewById(R.id.edTextDiagnostico);
        edTextDataDiagnostico = (EditText) activity.findViewById(R.id.edTextDataDiagnostico);
        edTextCurrentDisease = (EditText) activity.findViewById(R.id.edTextCurrentDisease);
        edTextPreviousDisease = (EditText) activity.findViewById(R.id.edTextPreviousDisease);
        edTextProcedimentos = (EditText) activity.findViewById(R.id.edTextProcedimentos);
        edTextTelefone = (EditText) activity.findViewById(R.id.edTextTelefone);
        edTextEmail = (EditText) activity.findViewById(R.id.edTextEmail);
    }


    Paciente pegaInfoDosCampos(Long id) {
        Paciente paciente = new Paciente();

        if (id != null) paciente.setId(id);
        paciente.setNome(edTextNomePaciente.getText().toString());
        paciente.setDataNascimento(getDataFromEditText(edTextDataNascimento));

        if (!edTextAltura.getText().toString().isEmpty())
            paciente.setEstatura(Double.valueOf(edTextAltura.getText().toString()));
        if (!edTextPeso.getText().toString().isEmpty())
            paciente.setMassa(Double.valueOf(edTextPeso.getText().toString()));

        paciente.setTelefone(edTextTelefone.getText().toString());
        paciente.setEmail(edTextEmail.getText().toString());
        paciente.setObs(edTextObs.getText().toString());

        paciente.setAnamnesisDate(getDataFromEditText(edTextDataAnamnese));
        paciente.setDiagnostico(edTextDiagnostico.getText().toString());
        paciente.setDataDiagnostico(getDataFromEditText(edTextDataDiagnostico));

        paciente.setHistoricoDoencaAtual(edTextCurrentDisease.getText().toString());
        paciente.setHistoricoDoencasAnteriores(edTextPreviousDisease.getText().toString());
        paciente.setProcedimentosTerapeuticos(edTextProcedimentos.getText().toString());
        paciente.setCaminhoFoto((String) btFoto.getTag());

        if (btnMasculino.isChecked()) {
            paciente.setGenero(Paciente.MASCULINO);
        } else if (btnFeminino.isChecked()) {
            paciente.setGenero(Paciente.FEMININO);
        }
        return paciente;
    }

    @Nullable
    private Date getDataFromEditText(EditText edTextData) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strData;
        if (!edTextData.getText().toString().isEmpty()) {
            strData = edTextData.getText().toString();
        } else {
            strData = Paciente.DATA_NULA; //Se um date for null o framework do banco da erro
        }

        try {
            return (new java.sql.Date(format.parse(strData).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    void preencheFormulário(Paciente paciente) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        edTextNomePaciente.setText(paciente.getNome());
        edTextDataNascimento.setText(dateFormat.format(paciente.getDataNascimento()));
        edTextDataNascimento.setFocusable(false);

        if (paciente.getGenero() == 0) {
            btnFeminino.setChecked(true);
        } else {
            btnMasculino.setChecked(true);
        }

        edTextPeso.setText(String.valueOf(paciente.getMassa()));
        edTextAltura.setText(String.valueOf(paciente.getEstatura()));
        edTextTelefone.setText(paciente.getTelefone());

        edTextEmail.setText(paciente.getEmail());
        edTextObs.setText(paciente.getObs());

        edTextDataAnamnese.setText(dateFormat.format(paciente.getAnamnesisDate()));
        edTextDiagnostico.setText(paciente.getDiagnostico());
        edTextDataAnamnese.setText(dateFormat.format(paciente.getDataDiagnostico()));
        edTextCurrentDisease.setText(paciente.getHistoricoDoencaAtual());
        edTextPreviousDisease.setText(paciente.getHistoricoDoencasAnteriores());
        edTextProcedimentos.setText(paciente.getProcedimentosTerapeuticos());
        carregaImagem(paciente.getCaminhoFoto());
    }

    boolean validateFields() {
        return (!edTextNomePaciente.getText().toString().isEmpty());
    }

    /**
     * Método que carrega a imagem no espaço do ícone
     *
     * @param caminhoFoto diretorio da foto
     */
    void carregaImagem(String caminhoFoto) {
        if (caminhoFoto != null) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap bm = BitmapFactory.decodeFile(caminhoFoto, options);
            if (bm.getWidth() > bm.getHeight()) {
                bm = Bitmap.createScaledBitmap(bm, 250, 200, false);

                //foto vertical
                Matrix matrix = new Matrix();
                matrix.postRotate(180);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);


            } else {
                //horizontal
                bm = Bitmap.createScaledBitmap(bm, 250, 200, false);
            }
            btFoto.setImageBitmap(bm);
            btFoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
            btFoto.setTag(caminhoFoto);
        }
    }
}

