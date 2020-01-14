package com.dawmi2.tangramv6;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class VistaTangram extends View {

    // variables de vista
    //private static final String CLICKTAG = "CLICK";
    private float touchX;
    private float touchY;
    private int anchoPantalla;
    private int altoPantalla;

    // imagenes
    private Drawable piezaTangram1, piezaTangram2,piezaTangram3, piezaTangram4, piezaTangram5, piezaTangram6, piezaTangram7,
        piezaFigura1, piezaFigura2, piezaFigura3, piezaFigura4, piezaFigura5, piezaFigura6, piezaFigura7,
        tangramColores, figuraSilueta, figuraColores;

    private Pieza
            pieza1 = new Pieza(getResources().getDrawable(R.drawable.p1),
            100, 500, 0, 100, 100, true),
            pieza2 = new Pieza(getResources().getDrawable(R.drawable.p2),
                    100, 500, 0, 100, 100, true),
            pieza3 = new Pieza(getResources().getDrawable(R.drawable.p3),
                    100, 500, 0, 100, 100, true),
            pieza4 = new Pieza(getResources().getDrawable(R.drawable.p4),
                    100, 500, 0, 100, 100, true),
            pieza5 = new Pieza(getResources().getDrawable(R.drawable.p5),
                    100, 500, 0, 100, 100, true),
            pieza6 = new Pieza(getResources().getDrawable(R.drawable.p6),
                    100, 500, 0, 100, 100, true),
            pieza7 = new Pieza(getResources().getDrawable(R.drawable.p7),
                    100, 500, 0, 100, 100, true);

    private Figura
        figuraCasa = new Figura("Casa",
            R.drawable.ic_figcasasilueta, R.drawable.ic_figcasacolores, R.drawable.casa_pieza1300px,
            R.drawable.casa_pieza2300px, R.drawable.casa_pieza3300px, R.drawable.casa_pieza4300px,
            R.drawable.casa_pieza5300px, R.drawable.casa_pieza6300px, R.drawable.casa_pieza7300px),
        figuraAvion =  new Figura("Avión",
            R.drawable.ic_figavionsilueta, R.drawable.ic_figavioncolores, R.drawable.fig_avion_p1300px,
            R.drawable.fig_avion_p2300px, R.drawable.fig_avion_p3300px,  R.drawable.fig_avion_p4300px,
            R.drawable.fig_avion_p5300px,  R.drawable.fig_avion_p6300px,  R.drawable.fig_avion_p7300px),
        figuraVela = new Figura("Vela",
            R.drawable.ic_figvelasilueta, R.drawable.ic_figvelacolores, R.drawable.fig_vela_p1,
            R.drawable.fig_vela_p2, R.drawable.fig_vela_p3, R.drawable.fig_vela_p4, R.drawable.fig_vela_p5,
            R.drawable.fig_vela_p6, R.drawable.fig_vela_p7),
        figuraHelicoptero = new Figura( "Helicóptero",
            R.drawable.ic_fighelicopsilueta, R.drawable.ic_fighelicopcolores, R.drawable.fig_helicoptero_p1,
            R.drawable.fig_helicoptero_p2, R.drawable.fig_helicoptero_p3, R.drawable.fig_helicoptero_p4,
            R.drawable.fig_helicoptero_p5, R.drawable.fig_helicoptero_p6, R.drawable.fig_helicoptero_p7),
        figuraCohete = new Figura("Cohete",
            R.drawable.ic_figcohetesilueta, R.drawable.ic_figcohetecolores, R.drawable.fig_cohete_p1,
            R.drawable.fig_cohete_p2,  R.drawable.fig_cohete_p3, R.drawable.fig_cohete_p4,
            R.drawable.fig_cohete_p5, R.drawable.fig_cohete_p6, R.drawable.fig_cohete_p7),
        figuraBarco = new Figura("Barco",
            R.drawable.ic_figbarcosilueta, R.drawable.ic_figbarcocolores, R.drawable.fig_barco_p1,
            R.drawable.fig_barco_p2, R.drawable.fig_barco_p3, R.drawable.fig_barco_p4,
            R.drawable.fig_barco_p5, R.drawable.fig_barco_p6, R.drawable.fig_barco_p7);

    // arrayLists
    private ArrayList<Figura> listaFiguras = new ArrayList<>();
    private ArrayList<Pieza> listaPiezas = new ArrayList<>();

    // posiciones imagenes
    private Point posicionTangramColores, posicionFiguraSilueta, posicionFiguraColores,
        posicionPiezaTangram1, posicionPiezaTangram2, posicionPiezaTangram3,
        posicionPiezaTangram4, posicionPiezaTangram5, posicionPiezaTangram6, posicionPiezaTangram7,
        posicionPiezaFigura1, posicionPiezaFigura2, posicionPiezaFigura3, posicionPiezaFigura4,
        posicionPiezaFigura5, posicionPiezaFigura6, posicionPiezaFigura7,
        xPosicionPieza1, xPosicionPieza2, xPosicionPieza3, xPosicionPieza4, xPosicionPieza5,
        xPosicionPieza6, xPosicionPieza7;

    // variables funcionales, acumuladores y contadores
    private TextView textoFigura;
    private String colorViewClickada, colorViewSoltada;
    static int indiceFiguras = 0;
    static int piezasColocadas = 0;
    static int figurasCompletadas = 0;
    static int puntuacion = 0;
    static int nivel = 1;
    private ArrayList<String> listaPiezasColocadas =  new ArrayList<>();


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
        tangramColores = getResources().getDrawable(R.drawable.ic_tangramcolores);
        figuraSilueta = getResources().getDrawable(R.drawable.casa_silueta300px);
        figuraColores = getResources().getDrawable(R.drawable.casa_colores300px);

        // añadir imagenes a arrays
        listaPiezas.add(pieza1);
        listaPiezas.add(pieza2);
        listaPiezas.add(pieza3);
        listaPiezas.add(pieza4);
        listaPiezas.add(pieza5);
        listaPiezas.add(pieza6);
        listaPiezas.add(pieza7);
        listaFiguras.add(figuraCohete);
        listaFiguras.add(figuraAvion);
        listaFiguras.add(figuraBarco);
        listaFiguras.add(figuraCasa);
        listaFiguras.add(figuraHelicoptero);
        listaFiguras.add(figuraVela);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // guardar tamaño vista y posición de drawables
        anchoPantalla = getWidth();
        altoPantalla = getHeight();
        posicionTangramColores = new Point(getWidth()/2, getHeight() - 300);
        posicionFiguraSilueta = new Point(getWidth()/2, getHeight() + 300);

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

        TangramActivity.sonidoTap.start();
        int touchColor = muestraDeColor(R.id.iv_tangram_colores, (int) touchX, (int) touchY);

        // código para determinar en qué pieza se hace click:
        boolean colocada = false;
        HerramientaColor ct = new HerramientaColor();
        String colorMatch = "";
        if (ct.compruebaMuestra(Color.MAGENTA, touchColor)){
            colorViewClickada="MAGENTA";
            if (listaPiezasColocadas.contains("MAGENTA")){
                colocada = true;
            }
        }
        else if (ct.compruebaMuestra(Color.CYAN, touchColor)){
            colorViewClickada="CYAN";
            if (listaPiezasColocadas.contains("CYAN")){
                colocada = true;
            }
        }
        else if (ct.compruebaMuestra(Color.RED, touchColor)){
            colorViewClickada="RED";
            if (listaPiezasColocadas.contains("RED")){
                colocada = true;
            }
        }
        else if (ct.compruebaMuestra(Color.BLACK, touchColor)){
            colorViewClickada="BLACK";
            if (listaPiezasColocadas.contains("BLACK")){
                colocada = true;
            }
        }
        else if (ct.compruebaMuestra(Color.BLUE, touchColor)){
            colorViewClickada="BLUE";
            if (listaPiezasColocadas.contains("BLUE")){
                colocada = true;
            }
        }
        else if (ct.compruebaMuestra(Color.GREEN, touchColor)){
            colorViewClickada="GREEN";
            if (listaPiezasColocadas.contains("GREEN")){
                colocada = true;
            }
        }
        else if (ct.compruebaMuestra(Color.YELLOW, touchColor)){
            colorViewClickada="YELLOW";
            if (listaPiezasColocadas.contains("YELLOW")){
                colocada = true;
            }
        } else {
            return true;
        }

        // si la pieza está ya colocada, no se ejecuta el código del drag and drop???
        if (!colocada){

        }
        return true;
    }


    protected void actualizarPosicionPieza() {
        //xPosicionPieza1 = (int) touchX;
        //yPosicionPieza1 = (int) touchY;
    }

    private int muestraDeColor(int hotspotId, int x, int y) {
        ImageView img = findViewById (hotspotId);
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

    static void cambiarFigura() {
        /*// SONIDO
        sonidoCambiaSiguiente.start();
        listaPiezasColocadas.clear();
        piezasColocadas = 0;
        puntuacion += 50;
        tv_puntuacion.setText(Integer.toString(puntuacion));
        if (figurasCompletadas == 3){
            nivel++;
            tv_nivel.setText(Integer.toString(nivel));
        }

        textoFigura.setText(listaFiguras.get(indiceFiguras).getNombreFigura());
        figuraColores.setImageResource(listaFiguras.get(indiceFiguras).getFiguraColores());
        figuraSilueta.setImageResource(listaFiguras.get(indiceFiguras).getFiguraSilueta());
        tv_siguienteFigura.setVisibility(View.INVISIBLE);
        //bt_siguienteFigura.setVisibility(View.INVISIBLE);

        piezaFigura1.setVisibility(View.INVISIBLE);
        piezaFigura2.setVisibility(View.INVISIBLE);
        piezaFigura3.setVisibility(View.INVISIBLE);
        piezaFigura4.setVisibility(View.INVISIBLE);
        piezaFigura5.setVisibility(View.INVISIBLE);
        piezaFigura6.setVisibility(View.INVISIBLE);
        piezaFigura7.setVisibility(View.INVISIBLE);

        piezaFigura1.setImageResource(listaFiguras.get(indiceFiguras).getPieza1());
        piezaFigura2.setImageResource(listaFiguras.get(indiceFiguras).getPieza2());
        piezaFigura3.setImageResource(listaFiguras.get(indiceFiguras).getPieza3());
        piezaFigura4.setImageResource(listaFiguras.get(indiceFiguras).getPieza4());
        piezaFigura5.setImageResource(listaFiguras.get(indiceFiguras).getPieza5());
        piezaFigura6.setImageResource(listaFiguras.get(indiceFiguras).getPieza6());
        piezaFigura7.setImageResource(listaFiguras.get(indiceFiguras).getPieza7());

        piezaTangram1.setVisibility(View.VISIBLE);
        piezaTangram2.setVisibility(View.VISIBLE);
        piezaTangram3.setVisibility(View.VISIBLE);
        piezaTangram4.setVisibility(View.VISIBLE);
        piezaTangram5.setVisibility(View.VISIBLE);
        piezaTangram6.setVisibility(View.VISIBLE);
        piezaTangram7.setVisibility(View.VISIBLE);*/
    }
}