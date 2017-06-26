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

    public boolean isValido() {
        return !(Double.isInfinite(getX()) || Double.isInfinite(getY()) ||
                Double.isNaN(getX()) || Double.isNaN(getY()));
    }

    public static Coordenada2D diminui(Coordenada2D p1, Coordenada2D p2) {
        float p2x = (float) (p2.getX() * 0.99999);
        float p2y = (float) (p2.getY() * 0.99999);
        return new Coordenada2D(p1.getX() - p2x, p1.getY() - p2y);
    }
}
