package com.dawmi2.tangramv6;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class Pieza extends Drawable {
    private Drawable pieza;
    public Point posicion;
    private int rotacion;
    private int ancho;
    private int alto;
    private boolean visible;

    public Pieza(Drawable pieza, int x, int y, int rotacion, int ancho, int alto, boolean visible) {
        super();
        this.pieza = pieza;
        posicion = new Point(x,y);
        this.rotacion = rotacion;
        this.ancho = ancho;
        this.alto = alto;
        this.visible = visible;
    }

    public Drawable getPieza() {
        return pieza;
    }

    public void setPieza(Drawable pieza) {
        this.pieza = pieza;
    }

    public Point getPosicion() {
        return posicion;
    }

    public void setPosicion(Point posicion) {
        this.posicion = posicion;
    }

    public int getRotacion() {
        return rotacion;
    }

    public void setRotacion(int rotacion) {
        this.rotacion = rotacion;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

/*    public boolean isVisible() {
        return visible;
    }*/

    @Override
    public int getOpacity() {
        return 0;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
