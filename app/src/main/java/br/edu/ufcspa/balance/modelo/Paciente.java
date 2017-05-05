package br.edu.ufcspa.balance.modelo;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by edupooch on 21/12/15.
 */
public class Paciente implements Serializable{

    private Long id = null;
    private String nome;
    private String telefone;
    private String email;
    private double massa;
    private double estatura;
    private Date dataNascimento;
    private int genero;
    private String obs;
    private String caminhoFoto;


    public Paciente() {
    }

    /*
    public JSONObject toJSon() {
        JSONObject paciente = new JSONObject();

        String encodedImage = " ";

        if (foto!= null){
            BitmapFactory.Options options0 = new BitmapFactory.Options();
            options0.inSampleSize = 2;
            //options.inJustDecodeBounds = true;
            options0.inScaled = false;
            options0.inDither = false;
            options0.inPreferredConfig = Bitmap.Config.ARGB_8888;

            Bitmap bmp = BitmapFactory.decodeFile(getFoto());

            ByteArrayOutputStream baos0 = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.JPEG,100,baos0);
            byte[] imageBytes0 = baos0.toByteArray();

            encodedImage = Base64.encodeToString(imageBytes0, Base64.DEFAULT);
        }




        try {
            paciente.put("nome",getNome());
            paciente.put("telefone",getTelefone());
            paciente.put("email", getEmail());
            paciente.put("massa", getMassa());
            paciente.put("estatura", getEstatura());
            paciente.put("nascimento", getDataNascimento());



            if(!encodedImage.isEmpty()){ // se nao tiver vazia adicionar a foto do paciente
                paciente.put("foto",encodedImage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paciente;
    }

*/

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getMassa() {
        return massa;
    }

    public void setMassa(double massa) {
        this.massa = massa;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return getNome();
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

}