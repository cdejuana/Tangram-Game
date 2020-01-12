package com.dawmi2.tangramv6;

import android.graphics.Color;


class HerramientaColor {
    private int tolerancia = 25;
    public boolean compruebaMuestra(int color1, int color2) {
        if ((int) Math.abs (Color.red (color1) - Color.red (color2)) > tolerancia ) return false;
        if ((int) Math.abs (Color.green (color1) - Color.green (color2)) > tolerancia ) return false;
        if ((int) Math.abs (Color.blue (color1) - Color.blue (color2)) > tolerancia ) return false;
        return true;
    } // end match
}
