package com.dawmi2.tangramv6;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FormaPersonalizada extends Drawable {

    private int vector;
    private Path forma = new Path();
    private Path temporal = new Path();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public FormaPersonalizada(Path vector, int color) {
        paint.setColor(color);
        forma.reset();
        forma.addPath(vector);
        forma.setFillType(Path.FillType.WINDING);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawPath(forma, paint);
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
        //computeHex(bounds);
        invalidateSelf();
    }
}
