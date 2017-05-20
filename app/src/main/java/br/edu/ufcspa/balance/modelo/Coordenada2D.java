package br.edu.ufcspa.balance.modelo;

/**
 * Created by edupooch on 15/05/2017.
 */

public class Coordenada2D {
    private float x;
    private float y;

    public Coordenada2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }


    public float getY() {
        return y;
    }


    @Override
    public String toString() {
        return "(" + x + "," + y + ")\n";
    }
}
