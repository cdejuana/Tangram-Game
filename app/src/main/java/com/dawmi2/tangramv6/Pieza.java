package com.dawmi2.tangramv6;

class Pieza {
    private int pieza;
    private int posX;
    private int posY;
    private int rotacion;
    private int ancho;
    private int alto;
    private boolean visible;

    public Pieza(int pieza, int posX, int posY, int rotacion, int ancho, int alto, boolean visible) {
        this.pieza = pieza;
        this.posX = posX;
        this.posY = posY;
        this.rotacion = rotacion;
        this.ancho = ancho;
        this.alto = alto;
        this.visible = visible;
    }

    public int getPieza() {
        return pieza;
    }

    public void setPieza(int pieza) {
        this.pieza = pieza;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
