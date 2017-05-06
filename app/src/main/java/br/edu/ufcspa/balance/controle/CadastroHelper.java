package br.edu.ufcspa.balance.controle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import br.edu.ufcspa.balance.R;
import br.edu.ufcspa.balance.modelo.Paciente;

/**
 * Created by edupooch on 17/02/16.
 */
public class CadastroHelper {

    private final EditText campoNome;
    private final EditText campoData;
    private final EditText campoAltura;
    private final EditText campoPeso;
    private final EditText campoEmail;
    private final EditText campoTelefone;
    private final EditText campoObs;
    private final ImageButton btFoto;
    private RadioButton btnMasculino;
    private RadioButton btnFeminino;

    public CadastroHelper(final CadastroActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.edTextNomePaciente);
        campoData = (EditText) activity.findViewById(R.id.edTextDataNascimento);
        btnFeminino = (RadioButton) activity.findViewById(R.id.btnFeminino);
        btnMasculino = (RadioButton) activity.findViewById(R.id.btnMasculino);
        campoAltura = (EditText) activity.findViewById(R.id.edTextAltura);
        campoPeso = (EditText) activity.findViewById(R.id.edTextPeso);
        campoEmail = (EditText) activity.findViewById(R.id.edTextEmail);
        campoTelefone = (EditText) activity.findViewById(R.id.edTextTelefone);
        campoTelefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        campoObs = (EditText) activity.findViewById(R.id.edTextObs);
        btFoto = (ImageButton) activity.findViewById(R.id.btFoto);


    }


    public Paciente pegaInfoDosCampos(Long id) {
        Paciente paciente = new Paciente();

        if (id != null) paciente.setId(id);
        paciente.setNome(campoNome.getText().toString());

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            paciente.setDataNascimento(new java.sql.Date(format.parse(campoData.getText().toString()).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        paciente.setEstatura(Double.valueOf(campoAltura.getText().toString()));
        paciente.setMassa(Double.valueOf(campoPeso.getText().toString()));
        paciente.setTelefone(campoTelefone.getText().toString());
        paciente.setEmail(campoEmail.getText().toString());
        paciente.setObs(campoObs.getText().toString());
        paciente.setCaminhoFoto((String) btFoto.getTag());
        if (btnMasculino.isChecked()) {
            paciente.setGenero(1);
        } else if (btnFeminino.isChecked()) {
            paciente.setGenero(0);
        }
        return paciente;
    }

    public void preencheFormulário(Paciente paciente) {
        campoNome.setText(paciente.getNome());
        Log.d("tag", paciente.getDataNascimento().toString());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strData = df.format(paciente.getDataNascimento());
        campoData.setText(strData);
        campoData.setFocusable(false);
        campoData.setEnabled(false);
        if (paciente.getGenero() == 0) {
            btnFeminino.setChecked(true);
        } else {
            btnMasculino.setChecked(true);
        }
        btnMasculino.setEnabled(false);
        btnFeminino.setEnabled(false);

        campoPeso.setText(String.valueOf(paciente.getMassa()));
        campoAltura.setText(String.valueOf(paciente.getEstatura()));
        campoTelefone.setText(paciente.getTelefone());

        campoEmail.setText(paciente.getEmail());
        campoObs.setText(paciente.getObs());
        carregaImagem(paciente.getCaminhoFoto());
    }

    public boolean validateFields() {

        return (!campoNome.getText().toString().isEmpty());
    }

    /**
     * Método que carrega a imagem no espaço do ícone
     *
     * @param caminhoFoto
     */
    public void carregaImagem(String caminhoFoto) {
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
                FileOutputStream out = null;

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
