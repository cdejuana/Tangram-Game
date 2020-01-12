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

    private int anchoVista;
    private int altoVista;
    private float touchX;
    private float touchY;
    private boolean tocando = false;

    private Drawable pieza1;
    private int anchoPieza1;
    private int altoPieza1;
    private int xPosicionPieza1;
    private int yPosicionPieza1;
    private Point posicionPieza1;
    private Drawable punto1;
    private Point posicionPunto1;

    private Drawable punto2;
    private Point posicionPunto2;

    private Drawable punto3;
    private Point posicionPunto3;


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
        pieza1 = getResources().getDrawable(R.drawable.p1);
        punto1 = getResources().getDrawable(R.drawable.p3);
        punto2 = getResources().getDrawable(R.drawable.p3);
        punto3 = getResources().getDrawable(R.drawable.p3);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // guardar tamaño vista y posición de drawables
        anchoVista = getWidth();
        altoVista = getHeight();

        //xPosicionPieza1 = getWidth()/2;
        //yPosicionPieza1 = getHeight()/2;
        posicionPieza1 = new Point(getWidth()/2, getHeight()/2);

        posicionPunto1 = new Point(165,428);
        posicionPunto2 = new Point(753, 428);
        posicionPunto3 = new Point(165, 1016);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // guardar tamaño drawables
        anchoPieza1 = pieza1.getIntrinsicWidth()/2;
        altoPieza1 = pieza1.getIntrinsicHeight()/2;
        // establecer marco
        pieza1.setBounds(
                anchoVista/2 - anchoPieza1/2, altoVista/2 - altoPieza1/2,
                anchoVista/2 + anchoPieza1/2, altoVista/2 + altoPieza1/2
        );
        //dibujar drawable
        pieza1.draw(canvas);

        //dibujo punto
        int anchoPunto1 = 10;
        int altoPunto1 = 10;
        int anchoPunto2 = 10;
        int altoPunto2 = 10;
        int anchoPunto3 = 10;
        int altoPunto3 = 10;

        punto1.setBounds(posicionPunto1.x - anchoPunto1/2, posicionPunto1.x + altoPunto1/2,
                posicionPunto1.y - anchoPunto1/2, posicionPunto1.y + altoPunto1/2);
        punto1.draw(canvas);

        punto2.setBounds(posicionPunto2.x - anchoPunto2/2, posicionPunto2.x + altoPunto2/2,
                posicionPunto2.y - anchoPunto2/2, posicionPunto2.y + altoPunto2/2);
        punto2.draw(canvas);

        punto3.setBounds(posicionPunto3.x - anchoPunto3/2, posicionPunto3.x + altoPunto3/2,
                posicionPunto3.y - anchoPunto3/2, posicionPunto3.y + altoPunto3/2);
        punto3.draw(canvas);
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

        //decidir que hacer segun lo que ocurra
        switch (action) {
            case (MotionEvent.ACTION_DOWN) :
                tocando = detectaArea(event);
                return true;
            case (MotionEvent.ACTION_MOVE) :
                if (tocando){
                    //actualizarPosicionNave();
                    postInvalidate();
                }
                break;
        }
        return true;
    }

    public boolean detectaArea(MotionEvent event){
        float touchX = event.getX();
        float touchY = event.getY();
        float areaXmin = xPosicionPieza1 - anchoPieza1/2;
        float areaXmax = xPosicionPieza1 + anchoPieza1/2;
        float areaYmin = yPosicionPieza1 - altoPieza1/2;
        float areaYmax = yPosicionPieza1 + altoPieza1/2;
        boolean estaDentro = false;

        // detectamos si está dentro con la formula:
        //https://www.geeksforgeeks.org/how-to-check-if-a-given-point-lies-inside-a-polygon/

        DetectorDeClicks miDetector = new DetectorDeClicks();

        Point[] polygon1 = {new Point(165, 428),
                new Point(753, 428),
                new Point(165, 1016)};

        int n = polygon1.length;
        Point p = new Point(20, 20);

        if (miDetector.isInside(polygon1, n, p)){
            Toast.makeText(getContext(), "SI", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(getContext(), "NO", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Log.d(CLICKTAG, "X:" + Integer.toString(x) + ", Y:" + Integer.toString(y));
        //Log.d(CLICKTAG, "X min:" + areaXmin + ", X max:" + areaXmax);
        //Log.d(CLICKTAG, "Y min:" + areaYmin + ", Y max:" + areaYmax);
    }

    protected void actualizarPosicionNave() {
        xPosicionPieza1 = (int) touchX;
        yPosicionPieza1 = (int) touchY;
    }

}