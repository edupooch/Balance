package br.edu.ufcspa.balance.modelo;

/**
 * Created by Rodrigo on 07/05/2017.
 */

public class DadoAcelerometro {

    private Long tempo;
    private float dado;

    public Long getTempo() {
        return tempo;
    }

    public void setTempo(Long tempo) {
        this.tempo = tempo;
    }

    public float getDado() {
        return dado;
    }

    public void setDado(float dado) {
        this.dado = dado;
    }

    public DadoAcelerometro(Long tempo, float dado) {
        this.tempo = tempo;
        this.dado = dado;
    }
}
