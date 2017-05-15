package br.edu.ufcspa.balance.modelo;

import com.google.gson.Gson;

/**
 * Created by Rodrigo on 07/05/2017.
 */

public class DadoAcelerometro {

    private Long tempo;
    private float x;
    private float y;
    private float z;


    public DadoAcelerometro(Long tempo, float x, float y, float z) {
        this.tempo = tempo;
        this.x = x;
        this.y = y;
        this.z = z;
    }



    public Long getTempo() {
        return tempo;
    }

    public void setTempo(Long tempo) {
        this.tempo = tempo;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public String toString() {

    Gson gson = new Gson();
        return gson.toJson(this);
    }
}
