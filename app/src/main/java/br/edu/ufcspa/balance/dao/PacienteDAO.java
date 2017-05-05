package br.edu.ufcspa.balance.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufcspa.balance.modelo.Paciente;


/**
 * Created by edupooch on 17/02/16.
 */
public class PacienteDAO extends SQLiteOpenHelper {


    public PacienteDAO(Context context) {
        super(context, "Agenda", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Pacientes (id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "data TEXT NOT NULL, " +
                "peso FLOAT NOT NULL, " +
                "altura TEXT NOT NULL, " +
                "telefone TEXT, " +
                "email TEXT, " +
                "obs TEXT," +
                "genero INTEGER," +
                "caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Esquema para controle de versões do banco:
        String sql;
        switch (oldVersion) {
            case 1:
            case 2:
                sql = "ALTER TABLE Pacientes ADD COLUMN genero INTEGER;";
                db.execSQL(sql);
        }

    }


    public void insere(Paciente paciente) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getContentValuesPaciente(paciente);
        db.insert("Pacientes", null, dados);
    }

    @NonNull
    private ContentValues getContentValuesPaciente(Paciente paciente) {
        ContentValues dados = new ContentValues();
        dados.put("nome", paciente.getNome());
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
        dados.put("data", dtFormat.format(paciente.getDataNascimento()));
        dados.put("peso", paciente.getMassa());
        dados.put("altura", paciente.getEstatura());
        dados.put("telefone", paciente.getTelefone());
        dados.put("email", paciente.getEmail());
        dados.put("obs", paciente.getObs());
        dados.put("genero", paciente.getGenero());
        dados.put("caminhoFoto", paciente.getCaminhoFoto());
        return dados;
    }


    public List<Paciente> buscaPacientes() {
        String sql = "SELECT * FROM Pacientes;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Paciente> pacientes = new ArrayList<Paciente>();

        while (c.moveToNext()) {
            Paciente paciente = new Paciente();

            paciente.setId(c.getLong(c.getColumnIndex("id")));
            paciente.setNome(c.getString(c.getColumnIndex("nome")));
            paciente.setDataNascimento(java.sql.Date.valueOf(c.getString(c.getColumnIndex("data"))));
            paciente.setMassa(c.getDouble(c.getColumnIndex("peso")));
            paciente.setEstatura(c.getDouble(c.getColumnIndex("altura")));
            paciente.setTelefone(c.getString(c.getColumnIndex("telefone")));
            paciente.setEmail(c.getString(c.getColumnIndex("email")));
            paciente.setObs(c.getString(c.getColumnIndex("obs")));
            paciente.setGenero(c.getInt(c.getColumnIndex("genero")));
            paciente.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            pacientes.add(paciente);

        }
        c.close();

        return pacientes;
    }

    public void deleta(Paciente paciente) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parametros = {paciente.getId().toString()};
        db.delete("Pacientes", "id = ?", parametros);

    }

    public void altera(Paciente paciente) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados2 = getContentValuesPaciente(paciente);
        String[] param = {paciente.getId().toString()};
        db.update("Pacientes", dados2, "id = ?", param);
    }
}
