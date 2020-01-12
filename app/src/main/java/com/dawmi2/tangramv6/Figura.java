package com.dawmi2.tangramv6;

public class Figura {
    private String nombreFigura;
    private int figuraSilueta;
    private int figuraColores;
    private int pieza1;
    private int pieza2;
    private int pieza3;
    private int pieza4;
    private int pieza5;
    private int pieza6;
    private int pieza7;

    public Figura(String nombreFigura, int figuraSilueta, int figuraColores, int pieza1, int pieza2, int pieza3, int pieza4, int pieza5, int pieza6, int pieza7) {
        this.nombreFigura = nombreFigura;
        this.figuraSilueta = figuraSilueta;
        this.figuraColores = figuraColores;
        this.pieza1 = pieza1;
        this.pieza2 = pieza2;
        this.pieza3 = pieza3;
        this.pieza4 = pieza4;
        this.pieza5 = pieza5;
        this.pieza6 = pieza6;
        this.pieza7 = pieza7;
    }

    public String getNombreFigura() {
        return nombreFigura;
    }

    public int getFiguraSilueta() {
        return figuraSilueta;
    }

    public int getFiguraColores() {
        return figuraColores;
    }

    public int getPieza1() {
        return pieza1;
    }

    public int getPieza2() {
        return pieza2;
    }

    public int getPieza3() {
        return pieza3;
    }

    public int getPieza4() {
        return pieza4;
    }

    public int getPieza5() {
        return pieza5;
    }

    public int getPieza6() {
        return pieza6;
    }

    public int getPieza7() {
        return pieza7;
    }
}
