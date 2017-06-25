package br.edu.ufcspa.balance.modelo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


/**
 * Created by edupooch on 20/06/2017.
 */

public class PermissoesHandler {

    public static final int CODIGO_ARQUIVOS = 1;
    private Activity activity;

    public PermissoesHandler(Activity activity) {
        this.activity = activity;
    }

    public boolean verificaPermissaoArquivos() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void pedePermissãoArquivos() {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                CODIGO_ARQUIVOS);
    }



}
