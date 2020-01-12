package com.dawmi2.tangramv6;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

class CreadorDePiezaArrastrada extends View.DragShadowBuilder{
    private static Drawable shadow;

    public CreadorDePiezaArrastrada(View view) {
        super(view);
        shadow = new ColorDrawable(Color.LTGRAY);
        Bitmap hotspots = Bitmap.createBitmap(view.getDrawingCache());
    }

    @Override
    public void onProvideShadowMetrics (Point size, Point touch){
        int width, height;

        width = getView().getWidth() /2;
        height = getView().getHeight() /2;
        shadow.setBounds(0,0, width, height);
        size.set(width, height);
        touch.set(width/2, height/2);
    }

    @Override
    public void onDrawShadow(Canvas canvas){
        shadow.draw(canvas);

    }
}
