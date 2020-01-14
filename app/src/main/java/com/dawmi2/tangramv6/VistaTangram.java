package com.dawmi2.tangramv6;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;

/**
 * TODO: document your custom view class.
 */
public class VistaTangram extends View {

    private static final String CLICKTAG = "CLICK";
    private float touchX;
    private float touchY;
    private int xPosicionPieza1;
    private int yPosicionPieza1;

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
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // guardar tamaño vista y posición de drawables

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // guardar tamaño drawables

        // establecer marco

        //dibujar drawable


        //dibujo punto

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
            case (MotionEvent.ACTION_DOWN):
                //Toast.makeText(getContext(), R.id.iv_tangram_colores +"", Toast.LENGTH_SHORT).show();
                gestionaClick();
                return true;
        }
        return true;
    }

    private void gestionaClick() {
        int touchColor = muestraDeColor(R.id.iv_tangram_colores, (int) touchX, (int) touchY);
        Bitmap pieza = null;
        boolean colocada = false;

        // la herramientaColor compara la muestra de color obtenida de la imagen, con un test de color RGB (compruebaMuestra())
        // el test de color contempla una tolerancia de 25, por las posibles variaciones de color causadas al escalar y variar la densidad de píxeles.
        HerramientaColor ct = new HerramientaColor();

        // EXTRAYENDO EL COLOR DONDE SE HA PULSADO, GUARDAMOS EL COLOR DE LA PIEZA ARRASTRADA
        if (ct.compruebaMuestra(Color.MAGENTA, touchColor)){
            Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
            //TangramActivity.colorViewClickada="MAGENTA";
            // si la pieza está ya colocada, no se ejecuta el código del evento onTouch (ni drag and drop, ver mas abajo)
            /*if (!TangramActivity.listaPiezasColocadas.contains("MAGENTA")){
                TangramActivity.piezaTangram1.setDrawingCacheEnabled(true);
                pieza = Bitmap.createBitmap(TangramActivity.piezaTangram1.getDrawingCache());
            } else {
                colocada = true;
            }*/
        }
    }


    protected void actualizarPosicionPieza() {
        xPosicionPieza1 = (int) touchX;
        yPosicionPieza1 = (int) touchY;
    }

    private int muestraDeColor(int hotspotId, int x, int y) {
        ImageView img = findViewById(hotspotId);
        if (img == null) {
            Log.d ("ImageAreasActivity", "Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d ("ImageAreasActivity", "Hot spot bitmap was not created");
                return 0;
            } else {
                if (x>hotspots.getWidth()){
                    x = hotspots.getWidth() - 1;
                } else if (x < 0){
                    x = 0;
                }
                if (y>hotspots.getHeight()){
                    y = hotspots.getHeight() - 1;
                } else if (y < 0){
                    y = 0;
                }
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }

}