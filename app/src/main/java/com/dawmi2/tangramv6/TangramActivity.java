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
    
    

    // botones y textos
    private ImageButton ib_info;
    private Button bt_siguienteFigura;
    private TextView tv_puntuacion;
    private TextView tv_nivel;
    private TextView tv_ultimaFigura;
    private TextView tv_siguienteFigura;

    // sonidos
    static MediaPlayer sonidoTap;
    static MediaPlayer sonidoPiezaBien;
    static MediaPlayer sonidoPiezaMal;
    static MediaPlayer sonidoFiguraBien;
    static MediaPlayer sonidoCambiaSiguiente;

    // detector de gestos
    private GestureDetectorCompat detectorGestos;
    
    // sobras??
    //private ImageView tangramColores;
    //private ImageView figuraColores;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tangram);

        //instanciamos detector de gestos
        detectorGestos = new GestureDetectorCompat(this, this);
        detectorGestos.setOnDoubleTapListener(this);

        //inflamos botones y textos

        ib_info = findViewById(R.id.ib_info);
        //bt_siguienteFigura = findViewById(R.id.bt_siguiente);
        //bt_siguienteFigura.setVisibility(View.INVISIBLE);

        //textoFigura = findViewById(R.id.tv_texto_figura);

        tv_puntuacion = findViewById(R.id.tv_n_puntos2);
        tv_nivel = findViewById(R.id.tv_n_nivel2);
        tv_ultimaFigura = findViewById(R.id.tv_ultima_figura);
        tv_ultimaFigura.setVisibility(View.INVISIBLE);
        tv_siguienteFigura = findViewById(R.id.tv_siguiente);
        tv_siguienteFigura.setVisibility(View.INVISIBLE);


        // SONIDOS
        sonidoTap = MediaPlayer.create(TangramActivity.this, R.raw.ui_tap_variant_01);
        sonidoPiezaBien = MediaPlayer.create(TangramActivity.this, R.raw.hero_simple_celebration_03);
        sonidoPiezaMal = MediaPlayer.create(TangramActivity.this, R.raw.alert_error_01);
        sonidoFiguraBien = MediaPlayer.create(TangramActivity.this, R.raw.hero_decorative_celebration_02);
        sonidoCambiaSiguiente = MediaPlayer.create(TangramActivity.this, R.raw.navigation_forward_selection);


        //
        //PREPARAMOS ESCUCHADORES DE EVENTOS:
        //

        //escuchador click boton DE ACTIVITY INFO
        ib_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInfo = new Intent(TangramActivity.this, Info.class);
                intentInfo.putExtra("PUNTUACION", VistaTangram.puntuacion);
                intentInfo.putExtra("NIVEL", VistaTangram.nivel);
                intentInfo.putExtra("COMPLETADAS", VistaTangram.figurasCompletadas);
                startActivity(intentInfo);
            }
        });

        /*//escuchador drag PIEZAS TANGRAM
        MiEscuchadorDrag escuchadorDrag = new MiEscuchadorDrag();
        tangramColores.setOnDragListener(escuchadorDrag);

        //escuchador drop PIEZAS FIGURA
        MiEscuchadorDrop escuchadorDrop = new MiEscuchadorDrop();
        figuraColores.setOnDragListener(escuchadorDrop);*/

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
        if (VistaTangram.piezasColocadas == 7){
            VistaTangram.indiceFiguras++;
            VistaTangram.cambiarFigura();
        }
        return false;
    }

    //QUÉ OCURRE CUANDO SE ARRASTRA UNA PIEZA
    /*
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

    }*/


}
