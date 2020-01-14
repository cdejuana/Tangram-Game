package com.dawmi2.tangramv6;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;

/**
 * TODO: document your custom view class.
 */
public class VistaTangram extends View {

    private static final String CLICKTAG = "CLICK";
    private float touchX;
    private float touchY;
    static String colorViewClickada, colorViewSoltada;
    //private Pieza pieza1;
    private Drawable pieza1;
    private int xPosicionPieza1;
    private int yPosicionPieza1;
    private int anchoPieza1;
    private int altoPieza1;
    private int anchoVista;
    private int altoVista;


    public VistaTangram(Context context) {
        super(context);
        init(null, 0);
    }

    public VistaTangram(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public VistaTangram(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // sacar resources para drawables
        pieza1 = getResources().getDrawable(R.drawable.p1);
        //pieza1 = new Pieza(getResources().getDrawable(R.drawable.p1),
                //0, 0, 0, 10, 10, false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // guardar tamaño vista y posición de drawables
        anchoVista = getWidth();
        altoVista = getHeight();

        xPosicionPieza1 = anchoVista/2;
        yPosicionPieza1 = altoVista/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // guardar tamaño drawables
        anchoPieza1 = 100;
        altoPieza1 = 100;
        // establecer marco
        pieza1.setBounds(xPosicionPieza1 - anchoPieza1/2, yPosicionPieza1 - altoPieza1/2,
                xPosicionPieza1 + anchoPieza1/2, yPosicionPieza1 + altoPieza1/2);
        //dibujar drawable
        pieza1.draw(canvas);



    }

    //método para hacer cosas cuando toca la pantalla
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*return super.onTouchEvent(event);*/

        //el punto de la pantalla donde tocamos:
        touchX = event.getX();
        touchY = event.getY();

        //coger accion del evento
        int action = event.getAction();

        return true;
    }



    protected void actualizarPosicionPieza() {
        //xPosicionPieza1 = (int) touchX;
        //yPosicionPieza1 = (int) touchY;
    }

}