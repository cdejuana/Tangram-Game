package com.dawmi2.tangramv6;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Originally Created by AnderWeb (Gustavo Claramunt) on 7/10/14.
 */
public class PolygonalDrawable extends Drawable {

    private int numberOfSides = 3;
    private Path polygon = new Path();
    private Path temporal = new Path();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PolygonalDrawable(int color, int sides) {
        paint.setColor(color);
        polygon.setFillType(Path.FillType.WINDING);
        this.numberOfSides = sides;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(polygon, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
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

        final int width = bounds.width();
        final int height = bounds.height();
        final int size = Math.min(width, height);
        final int centerX = bounds.left + (width / 2);
        final int centerY = bounds.top + (height / 2);

        polygon.reset();
        polygon.addPath(createHexagon(size, centerX, centerY));
        polygon.addPath(createHexagon((int) (size * .8f), centerX, centerY));
    }

    private Path createHexagon(int size, int centerX, int centerY) {
        final float section = (float) (2.0 * Math.PI / numberOfSides);
        int radius = size / 2;
        Path polygonPath = temporal;
        polygonPath.reset();
        polygonPath.moveTo((centerX + radius * (float)Math.cos(0)), (centerY + radius
                * (float)Math.sin(0)));

        for (int i = 1; i < numberOfSides; i++) {
            polygonPath.lineTo((centerX + radius * (float)Math.cos(section * i)),
                    (centerY + radius * (float)Math.sin(section * i)));
        }

        polygonPath.close();
        return polygonPath;
    }
}