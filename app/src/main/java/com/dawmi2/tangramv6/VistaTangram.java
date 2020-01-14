package com.dawmi2.tangramv6;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    private float touchX;
    private float touchY;
    private Point touchPoint;
    static String colorViewClickada, colorViewSoltada;

    private int anchoVista;
    private int altoVista;
    private String tocando;

    //private Pieza pieza1;
    private Drawable pieza1;
    private int xPosicionPieza1;
    private int yPosicionPieza1;
    private int anchoPieza1;
    private int altoPieza1;

    private Drawable pieza2;
    private int xPosicionPieza2;
    private int yPosicionPieza2;
    private int anchoPieza2;
    private int altoPieza2;


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
        pieza2 = getResources().getDrawable(R.drawable.p2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // guardar tamaño vista y posición de drawables
        anchoVista = getWidth();
        altoVista = getHeight();

        xPosicionPieza1 = anchoVista/8;
        yPosicionPieza1 = (altoVista -= altoVista/3);

        xPosicionPieza2 = anchoVista/6;
        yPosicionPieza2 = (altoVista -= altoVista/3);
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

        anchoPieza2 = 50;
        altoPieza2 = 100;
        // establecer marco
        pieza2.setBounds(xPosicionPieza2 - anchoPieza2/2, yPosicionPieza2 - altoPieza2/2,
                xPosicionPieza2 + anchoPieza2/2, yPosicionPieza2 + altoPieza2/2);
        pieza2.draw(canvas);
    }

    //método para hacer cosas cuando toca la pantalla
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*return super.onTouchEvent(event);*/

        //el punto de la pantalla donde tocamos:
        touchX = event.getX();
        touchY = event.getY();
        touchPoint = new Point((int)touchX, (int)touchY);

        //coger accion del evento
        int action = event.getAction();

        //decidir que hacer segun lo que ocurra
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                tocando = detectaArea();
                return true;
            case (MotionEvent.ACTION_MOVE):
                actualizarPosicionPieza(tocando);
                postInvalidate();
                break;
        }
        return true;
    }


    private String detectaArea() {
        float xMinP1 = xPosicionPieza1 - anchoPieza1/2;
        float xMaxP1 = xPosicionPieza1 + anchoPieza1/2;
        float yMinP1 = yPosicionPieza1 - altoPieza1/2;
        float yMaxP1 = yPosicionPieza1 + altoPieza1/2;

        float xMinP2 = xPosicionPieza2 - anchoPieza2/2;
        float xMaxP2 = xPosicionPieza2 + anchoPieza2/2;
        float yMinP2 = yPosicionPieza2 - altoPieza2/2;
        float yMaxP2 = yPosicionPieza2 + altoPieza2/2;

        if (((touchX > xMinP1) && (touchX < xMaxP1)) &&
                ((touchY > yMinP1) && (touchY < yMaxP1))) {
            return "pieza1";
        } else if (((touchX > xMinP2) && (touchX < xMaxP2)) &&
                ((touchY > yMinP2) && (touchY < yMaxP2))){
            return "pieza2";
        } else {
            return "";
        }
    }

    protected void actualizarPosicionPieza(String tocando) {
        switch (tocando) {
            case "pieza1":
                xPosicionPieza1 = (int) touchX;
                yPosicionPieza1 = (int) touchY;
                break;
            case "pieza2":
                xPosicionPieza2 = (int) touchX;
                yPosicionPieza2 = (int) touchY;
                break;
        }
    }

}

