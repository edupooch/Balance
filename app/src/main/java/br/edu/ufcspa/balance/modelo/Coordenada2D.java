package br.edu.ufcspa.balance.modelo;

/**
 * Created by edupooch on 15/05/2017.
 */

public class Coordenada2D {
    private double x;
    private double y;

    public Coordenada2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")\n";
    }
}
