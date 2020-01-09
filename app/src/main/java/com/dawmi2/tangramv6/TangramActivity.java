package com.dawmi2.tangramv6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TangramActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {
    //prueba poligono
    private ImageView poligono;
    //private Drawable piezaD1;

    // botones y textos
    private ImageButton ib_info;
    private Button bt_siguienteFigura;
    private TextView tv_puntuacion;
    private TextView tv_nivel;
    private TextView tv_ultimaFigura;
    private TextView tv_siguienteFigura;

    // imagenes
    private ImageView piezaTangram1, piezaTangram2,piezaTangram3, piezaTangram4, piezaTangram5, piezaTangram6, piezaTangram7;
    private ImageView piezaFigura1, piezaFigura2, piezaFigura3, piezaFigura4, piezaFigura5, piezaFigura6, piezaFigura7;
    private ImageView tangramColores, figuraSilueta, figuraColores;

    // objetos
    private Figura figura1casa, figura2avion;
    private ArrayList <Figura> listaFiguras = new ArrayList<>();

    // variables funcionales, acumuladores y contadores
    private TextView textoFigura;
    private String colorViewClickada, colorViewSoltada;
    private int indiceFiguras;
    private int piezasColocadas;
    private int figurasCompletadas;
    private int puntuacion;
    private int nivel;
    private ArrayList<String> listaPiezasColocadas =  new ArrayList<>();

    // sonidos
    private MediaPlayer sonidoTap;
    private MediaPlayer sonidoPiezaBien;
    private MediaPlayer sonidoPiezaMal;
    private MediaPlayer sonidoFiguraBien;
    private MediaPlayer sonidoCambiaSiguiente;

    // detector de gestos
    private GestureDetectorCompat detectorGestos;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tangram);

        //prueba poligono
        poligono = findViewById(R.id.triangle);
        poligono.setBackground(new PiezaDrawable(Color.GREEN, 20));
        //p1 = (Path) getResources().getDrawable(R.drawable.p1);

        poligono.setOnTouchListener (new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(TangramActivity.this, "FUNCIONA", Toast.LENGTH_SHORT).show();
                return true;
            }
            });


        // incializamos contadores
        indiceFiguras = 0;
        piezasColocadas = 0;
        figurasCompletadas = 0;
        puntuacion = 0;
        nivel = 1;

        //instanciamos detector de gestos
        detectorGestos = new GestureDetectorCompat(this, this);
        detectorGestos.setOnDoubleTapListener(this);

        //inflamos botones y textos
        ib_info = findViewById(R.id.ib_info);
        //bt_siguienteFigura = findViewById(R.id.bt_siguiente);
        //bt_siguienteFigura.setVisibility(View.INVISIBLE);
        textoFigura = findViewById(R.id.tv_texto_figura);
        tv_puntuacion = findViewById(R.id.tv_n_puntos2);
        tv_nivel = findViewById(R.id.tv_n_nivel2);
        tv_ultimaFigura = findViewById(R.id.tv_ultima_figura);
        tv_ultimaFigura.setVisibility(View.INVISIBLE);
        tv_siguienteFigura = findViewById(R.id.tv_siguiente);
        tv_siguienteFigura.setVisibility(View.INVISIBLE);


        // piezas tangram
        piezaTangram1 = findViewById(R.id.iv_p1);
        piezaTangram2 = findViewById(R.id.iv_p2);
        piezaTangram3 = findViewById(R.id.iv_p3);
        piezaTangram4 = findViewById(R.id.iv_p4);
        piezaTangram5 = findViewById(R.id.iv_p5);
        piezaTangram6 = findViewById(R.id.iv_p6);
        piezaTangram7 = findViewById(R.id.iv_p7);

        //piezas figura
        piezaFigura1 = findViewById(R.id.iv_figura_pieza1);
        piezaFigura2 = findViewById(R.id.iv_figura_pieza2);
        piezaFigura3 = findViewById(R.id.iv_figura_pieza3);
        piezaFigura4 = findViewById(R.id.iv_figura_pieza4);
        piezaFigura5 = findViewById(R.id.iv_figura_pieza5);
        piezaFigura6 = findViewById(R.id.iv_figura_pieza6);
        piezaFigura7 = findViewById(R.id.iv_figura_pieza7);

        //imagenes tangram y figuras
        tangramColores = findViewById(R.id.iv_tangram_colores);
        figuraSilueta = findViewById(R.id.iv_figura_silueta);
        figuraColores = findViewById(R.id.iv_figura_colores);

        // SONIDOS
        sonidoTap = MediaPlayer.create(TangramActivity.this, R.raw.ui_tap_variant_01);
        sonidoPiezaBien = MediaPlayer.create(TangramActivity.this, R.raw.hero_simple_celebration_03);
        sonidoPiezaMal = MediaPlayer.create(TangramActivity.this, R.raw.alert_error_01);
        sonidoFiguraBien = MediaPlayer.create(TangramActivity.this, R.raw.hero_decorative_celebration_02);
        sonidoCambiaSiguiente = MediaPlayer.create(TangramActivity.this, R.raw.navigation_forward_selection);

        // CREAMOS LOS OBJETOS DE LAS DIFERENTES FIGURAS
        // Y LOS AÑADIMOS A SU ARRAY:
        //
        // casa
        listaFiguras.add(new Figura(
                "Casa",
                R.drawable.ic_figcasasilueta,
                R.drawable.ic_figcasacolores,
                R.drawable.casa_pieza1300px,
                R.drawable.casa_pieza2300px,
                R.drawable.casa_pieza3300px,
                R.drawable.casa_pieza4300px,
                R.drawable.casa_pieza5300px,
                R.drawable.casa_pieza6300px,
                R.drawable.casa_pieza7300px));
        // avion
        listaFiguras.add(new Figura(
                "Avión",
                R.drawable.ic_figavionsilueta,
                R.drawable.ic_figavioncolores,
                R.drawable.fig_avion_p1300px,
                R.drawable.fig_avion_p2300px,
                R.drawable.fig_avion_p3300px,
                R.drawable.fig_avion_p4300px,
                R.drawable.fig_avion_p5300px,
                R.drawable.fig_avion_p6300px,
                R.drawable.fig_avion_p7300px));
        // vela
        listaFiguras.add(new Figura(
                "Vela",
                R.drawable.ic_figvelasilueta,
                R.drawable.ic_figvelacolores,
                R.drawable.fig_vela_p1,
                R.drawable.fig_vela_p2,
                R.drawable.fig_vela_p3,
                R.drawable.fig_vela_p4,
                R.drawable.fig_vela_p5,
                R.drawable.fig_vela_p6,
                R.drawable.fig_vela_p7));
        // helicoptero
        listaFiguras.add(new Figura(
                "Helicóptero",
                R.drawable.ic_fighelicopsilueta,
                R.drawable.ic_fighelicopcolores,
                R.drawable.fig_helicoptero_p1,
                R.drawable.fig_helicoptero_p2,
                R.drawable.fig_helicoptero_p3,
                R.drawable.fig_helicoptero_p4,
                R.drawable.fig_helicoptero_p5,
                R.drawable.fig_helicoptero_p6,
                R.drawable.fig_helicoptero_p7));
        // cohete
        listaFiguras.add(new Figura(
                "Cohete",
                R.drawable.ic_figcohetesilueta,
                R.drawable.ic_figcohetecolores,
                R.drawable.fig_cohete_p1,
                R.drawable.fig_cohete_p2,
                R.drawable.fig_cohete_p3,
                R.drawable.fig_cohete_p4,
                R.drawable.fig_cohete_p5,
                R.drawable.fig_cohete_p6,
                R.drawable.fig_cohete_p7));
        // barco
        listaFiguras.add(new Figura(
                "Barco",
                R.drawable.ic_figbarcosilueta,
                R.drawable.ic_figbarcocolores,
                R.drawable.fig_barco_p1,
                R.drawable.fig_barco_p2,
                R.drawable.fig_barco_p3,
                R.drawable.fig_barco_p4,
                R.drawable.fig_barco_p5,
                R.drawable.fig_barco_p6,
                R.drawable.fig_barco_p7));

        //
        //PREPARAMOS ESCUCHADORES DE EVENTOS:
        //

        //escuchador click boton DE ACTIVITY INFO
        ib_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInfo = new Intent(TangramActivity.this, Info.class);
                intentInfo.putExtra("PUNTUACION", puntuacion);
                intentInfo.putExtra("NIVEL", nivel);
                intentInfo.putExtra("COMPLETADAS", figurasCompletadas);
                startActivity(intentInfo);
            }
        });

        //escuchador drag PIEZAS TANGRAM
        MiEscuchadorDrag escuchadorDrag = new MiEscuchadorDrag();
        tangramColores.setOnDragListener(escuchadorDrag);

        //escuchador drop PIEZAS FIGURA
        MiEscuchadorDrop escuchadorDrop = new MiEscuchadorDrop();
        figuraColores.setOnDragListener(escuchadorDrop);

        //escuchador touch TANGRAM
        tangramColores.setOnTouchListener (new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // SONIDO
                sonidoTap.start();
                final int evX = (int) event.getX();
                final int evY = (int) event.getY();
                int touchColor = muestraDeColor(v.getId(), evX, evY);
                Bitmap pieza = null;
                boolean colocada = false;

                // la herramientaColor compara la muestra de color obtenida de la imagen, con un test de color RGB (compruebaMuestra())
                // el test de color contempla una tolerancia de 25, por las posibles variaciones de color causadas al escalar y variar la densidad de píxeles.
                HerramientaColor ct = new HerramientaColor();
                String colorMatch = "";
                // EXTRAYENDO EL COLOR DONDE SE HA PULSADO, GUARDAMOS EL COLOR DE LA PIEZA ARRASTRADA
                if (ct.compruebaMuestra(Color.MAGENTA, touchColor)){
                    colorViewClickada="MAGENTA";
                    // si la pieza está ya colocada, no se ejecuta el código del evento onTouch (ni drag and drop, ver mas abajo)
                    if (!listaPiezasColocadas.contains("MAGENTA")){
                        piezaTangram1.setDrawingCacheEnabled(true);
                        pieza = Bitmap.createBitmap(piezaTangram1.getDrawingCache());
                    } else {
                        colocada = true;
                    }
                }
                else if (ct.compruebaMuestra(Color.CYAN, touchColor)){
                    colorViewClickada="CYAN";
                    if (!listaPiezasColocadas.contains("CYAN")){
                        piezaTangram2.setDrawingCacheEnabled(true);
                        pieza = Bitmap.createBitmap(piezaTangram2.getDrawingCache());
                    } else {
                        colocada = true;
                    }
                }
                else if (ct.compruebaMuestra(Color.RED, touchColor)){
                    colorViewClickada="RED";
                    if (!listaPiezasColocadas.contains("RED")){
                        piezaTangram3.setDrawingCacheEnabled(true);
                        pieza = Bitmap.createBitmap(piezaTangram3.getDrawingCache());
                    } else {
                        colocada = true;
                    }
                }
                else if (ct.compruebaMuestra(Color.BLACK, touchColor)){
                    colorViewClickada="BLACK";
                    if (!listaPiezasColocadas.contains("BLACK")){
                        piezaTangram4.setDrawingCacheEnabled(true);
                        pieza = Bitmap.createBitmap(piezaTangram4.getDrawingCache());
                    } else {
                        colocada = true;
                    }
                }
                else if (ct.compruebaMuestra(Color.BLUE, touchColor)){
                    colorViewClickada="BLUE";
                    if (!listaPiezasColocadas.contains("BLUE")){
                        piezaTangram5.setDrawingCacheEnabled(true);
                        pieza = Bitmap.createBitmap(piezaTangram5.getDrawingCache());
                    } else {
                        colocada = true;
                    }
                }
                else if (ct.compruebaMuestra(Color.GREEN, touchColor)){
                    colorViewClickada="GREEN";
                    if (!listaPiezasColocadas.contains("GREEN")){
                        piezaTangram6.setDrawingCacheEnabled(true);
                        pieza = Bitmap.createBitmap(piezaTangram6.getDrawingCache());
                    } else {
                        colocada = true;
                    }
                }
                else if (ct.compruebaMuestra(Color.YELLOW, touchColor)){
                    colorViewClickada="YELLOW";
                    if (!listaPiezasColocadas.contains("YELLOW")){
                        piezaTangram7.setDrawingCacheEnabled(true);
                        pieza = Bitmap.createBitmap(piezaTangram7.getDrawingCache());
                    } else {
                        colocada = true;
                    }
                } else {
                    return true;
                }
                // si la pieza está ya colocada, no se ejecuta el código del drag and drop
                if (!colocada){
                    ClipData.Item item = new ClipData.Item("Pieza soltada??");
                    ClipData dragData = new ClipData(
                            (CharSequence) v.getTag(),
                            new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN},
                            item);
                    // CUANDO SE PULSA LA PANTALLA PARA ARRASTRAR, SE MUESTRA UNA PIEZA MIENTRAS SE ARRASTRA
                    View.DragShadowBuilder piezaArrastrada = ImageDragShadowBuilder.fromBitmap(TangramActivity.this, pieza);

                    v.startDragAndDrop(dragData, piezaArrastrada, null, 0);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.detectorGestos.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (piezasColocadas == 7){
            indiceFiguras++;
            cambiarFigura();
        }
        return false;
    }

    //QUÉ OCURRE CUANDO SE ARRASTRA UNA PIEZA
    private class MiEscuchadorDrag implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            //recogemos del evento la acción realizada
            final int action = event.getAction();

            //guardamos la posición del puntero
            final int evX = (int) event.getX();
            final int evY = (int) event.getY();

            //dependiendo de la acción...AUNQUE AQUI SOLO TENEMOS ACCION DE ARRASTRAS
            switch (action){
                case DragEvent.ACTION_DRAG_STARTED:
                    // CUANDO COMIENZA LA ACCIÓN DE ARRASTRAR
                    // SE COMPRUEBA QUE HAY CONTENIDO QUE ARRASTRAR
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){

                        // cogemos una muestra del color del pixel del view sobre el que está el puntero
                        // en el momento de iniciar la acción de arrastrar
                        int touchColor = muestraDeColor(v.getId(), evX, evY);

                        // la herramientaColor compara la muestra de color obtenida de la imagen, con un test de color RGB (compruebaMuestra())
                        // el test de color contempla una tolerancia de 25, por las posibles variaciones de color causadas al escalar y variar la densidad de píxeles.
                        HerramientaColor ct = new HerramientaColor();
                        String colorMatch = "";
                        // EXTRAYENDO EL COLOR DONDE SE HA PULSADO, GUARDAMOS EL COLOR DE LA PIEZA ARRASTRADA
                        if (ct.compruebaMuestra(Color.GREEN, touchColor)){ colorViewClickada="GREEN";}
                        else if (ct.compruebaMuestra(Color.CYAN, touchColor)){ colorViewClickada="CYAN";}
                        else if (ct.compruebaMuestra(Color.RED, touchColor)){ colorViewClickada="RED";}
                        else if (ct.compruebaMuestra(Color.BLACK, touchColor)){ colorViewClickada="BLACK";}
                        else if (ct.compruebaMuestra(Color.BLUE, touchColor)){ colorViewClickada="BLUE";}
                        else if (ct.compruebaMuestra(Color.MAGENTA, touchColor)){ colorViewClickada="MAGENTA";}
                        else if (ct.compruebaMuestra(Color.YELLOW, touchColor)){ colorViewClickada="YELLOW";}
                        return true;
                    }
                    return false;
            }
            return false;
        }
    }

    //QUÉ OCURRE CUANDO SE SUELTA UNA PIEZA ARRASTRADA
    private class MiEscuchadorDrop implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            //recogemos del evento la acción realizada
            final int action = event.getAction();

            //guardamos la posición del puntero
            final int evX = (int) event.getX();
            final int evY = (int) event.getY();

            //dependiendo de la acción...
            switch (action){
                // EMPIEZA LA ACCIÓN DE ARRASTRAR
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
                        return true;
                    }
                    return false;
                // SE SUELTA
                case DragEvent.ACTION_DROP:
                    // cogemos una muestra del color del pixel del view sobre el que está el puntero
                    // en el momento de iniciar la acción de SOLTAR
                    int touchColor = muestraDeColor(v.getId(), evX, evY);

                    // COMPARAMOS LA MUESTRA RECOGIDA CON LA HERRAMIENTA COLOR PARA DETERMINAR EL COLOR
                    HerramientaColor ct = new HerramientaColor();

                    // GUARDAMOS EL COLOR SOBRE EL QUE SOLTAMOS
                    if (ct.compruebaMuestra(Color.GREEN, touchColor)){ colorViewSoltada = "GREEN";}
                    else if (ct.compruebaMuestra(Color.CYAN, touchColor)){ colorViewSoltada = "CYAN";}
                    else if (ct.compruebaMuestra(Color.RED, touchColor)){ colorViewSoltada = "RED";}
                    else if (ct.compruebaMuestra(Color.BLACK, touchColor)){ colorViewSoltada = "BLACK";}
                    else if (ct.compruebaMuestra(Color.BLUE, touchColor)){ colorViewSoltada = "BLUE";}
                    else if (ct.compruebaMuestra(Color.MAGENTA, touchColor)){ colorViewSoltada = "MAGENTA";}
                    else if (ct.compruebaMuestra(Color.YELLOW, touchColor)){ colorViewSoltada = "YELLOW";}
                    return true;

                // AL TERMINAR LA ACCIÓN DE SOLTAR...
                case DragEvent.ACTION_DRAG_ENDED:
                    // COMPARAMOS EL COLOR DE LA PIEZA ARRASTRADA CON EL COLOR DE LA PIEZA OCULTA DONDE SE SUELTA
                    if(colorViewClickada == colorViewSoltada){
                        // SONIDO
                        sonidoPiezaBien.start();
                        // SI ES CORRECTO...
                        Toast.makeText(TangramActivity.this, "¡PIEZA COLOCADA CORRECTAMENTE!", Toast.LENGTH_SHORT).show();
                        // Y DEPENDIENDO DEL COLOR, SE OCULA UNA PIEZA Y SE MUESTRA LA OTRA
                        switch (colorViewClickada) {
                            case "MAGENTA":
                                piezaFigura1.setVisibility(View.VISIBLE);
                                piezaTangram1.setVisibility(View.INVISIBLE);
                                listaPiezasColocadas.add("MAGENTA");
                                break;
                            case "CYAN":
                                piezaFigura2.setVisibility(View.VISIBLE);
                                piezaTangram2.setVisibility(View.INVISIBLE);
                                listaPiezasColocadas.add("CYAN");
                                break;
                            case "RED":
                                piezaFigura3.setVisibility(View.VISIBLE);
                                piezaTangram3.setVisibility(View.INVISIBLE);
                                listaPiezasColocadas.add("RED");
                                break;
                            case "BLACK":
                                piezaFigura4.setVisibility(View.VISIBLE);
                                piezaTangram4.setVisibility(View.INVISIBLE);
                                listaPiezasColocadas.add("BLACK");
                                break;
                            case "BLUE":
                                piezaFigura5.setVisibility(View.VISIBLE);
                                piezaTangram5.setVisibility(View.INVISIBLE);
                                listaPiezasColocadas.add("BLUE");
                                break;
                            case "GREEN":
                                piezaFigura6.setVisibility(View.VISIBLE);
                                piezaTangram6.setVisibility(View.INVISIBLE);
                                listaPiezasColocadas.add("GREEN");
                                break;
                            case "YELLOW":
                                piezaFigura7.setVisibility(View.VISIBLE);
                                piezaTangram7.setVisibility(View.INVISIBLE);
                                listaPiezasColocadas.add("YELLOW");
                                break;
                        }
                        // ACTUALIZAMOS PUNTUACIÓN ETC...
                        puntuacion += 10;
                        piezasColocadas++;
                        tv_puntuacion.setText(Integer.toString(puntuacion));
                        // SI LA FIGURA ESTÁ COMPLETA...
                        if (piezasColocadas == 7){
                            // SONIDO
                            sonidoFiguraBien.start();
                            figurasCompletadas++;
                            Toast.makeText(TangramActivity.this, "¡GENIAL! ¡HAS COMPLETADO LA FIGURA!", Toast.LENGTH_LONG).show();
                            // SI NO ESTAMOS EN LA ÚLTIMA FIGURA...
                            if (indiceFiguras < listaFiguras.size()-1){
                                // MOSTRAMOS BOTÓN DE SIGUIENTE
                                tv_siguienteFigura.setVisibility(View.VISIBLE);
                                //bt_siguienteFigura.setVisibility(View.VISIBLE);
                            } else {
                                // SI ESTAMOS EN LA ÚLTIMA... LO MOSTRAMOS
                                tv_ultimaFigura.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        // SI NO COINCIDEN, RESTAMOS PUNTOS Y SUENA MAL...
                        // SONIDO
                        sonidoPiezaMal.start();
                        puntuacion -= 10;
                        tv_puntuacion.setText(Integer.toString(puntuacion));
                        Toast.makeText(TangramActivity.this, "ESE NO ES SU SITIO... ¡PRUEBA OTRA VEZ!", Toast.LENGTH_SHORT).show();
                    }
                    return true;
            }
            return false;
        }

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

    private void cambiarFigura() {
        // SONIDO
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
        piezaTangram7.setVisibility(View.VISIBLE);
    }
}
