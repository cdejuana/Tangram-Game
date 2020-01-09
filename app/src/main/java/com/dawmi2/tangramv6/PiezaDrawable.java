package com.dawmi2.tangramv6;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PiezaDrawable extends Drawable {

    private int piezaNumero;
    private Path polygon = new Path();
    private Path temporal = new Path();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PiezaDrawable(int color, int piezaNumero) {
        paint.setColor(color);
        this.piezaNumero = piezaNumero;
        polygon.setFillType(Path.FillType.WINDING);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawPath(polygon, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        computeHex(bounds);
        invalidateSelf();
    }

    public void computeHex(Rect bounds) {

        //final int width = bounds.width();
        //final int height = bounds.height();
        //final int size = Math.min(width, height);
        //final int centerX = bounds.left + (width / 2);
        //final int centerY = bounds.top + (height / 2);

        polygon.reset();
        polygon.addPath(crearTrazado(piezaNumero));
        //polygon.addPath(crearTrazado(piezaNumero));
    }

    private Path crearTrazado(int pieza) {
        //final float section = (float) (2.0 * Math.PI / numberOfSides);
        //int radius = size / 2;
        Path polygonPath = temporal;
        polygonPath.reset();
        polygonPath.moveTo((float)0.0,(float)0.0);
        polygonPath.lineTo((float)200.0,(float)0.0);
        polygonPath.lineTo((float)0.0,(float)200.0);
        polygonPath.lineTo((float)0.0,(float)0.0);


        polygonPath.close();
        return polygonPath;
    }
}
