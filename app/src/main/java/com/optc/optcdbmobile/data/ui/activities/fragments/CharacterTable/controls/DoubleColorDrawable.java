package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DoubleColorDrawable extends Drawable {

    private final int OFFSET = 200;
    Path path1, path2;
    Paint paint1, paint2;
    private int width, height;

    public DoubleColorDrawable(int color1, int color2) {


        path1 = new Path();


        path2 = new Path();


        paint1 = new Paint();
        paint1.setColor(color1);
        paint1.setStyle(Paint.Style.FILL);

        paint2 = new Paint();
        paint2.setColor(color2);
        paint2.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onBoundsChange(Rect bounds) {

        width = bounds.width();
        height = bounds.height();


        path1.moveTo(0, 0);
        path1.lineTo(width, 0);
        path1.lineTo(width, height / 2 + OFFSET);
        path1.lineTo(0, height / 2 - OFFSET / 2);
        path1.close();

        path2.moveTo(0, height / 2 - OFFSET / 2);
        path2.lineTo(width, height / 2 + OFFSET);
        path2.lineTo(width, height);
        path2.lineTo(0, height);
        path2.close();
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawPath(path1, paint1);

        canvas.drawPath(path2, paint2);
    }

    @Override
    public void setAlpha(int i) {
        // Nothing
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        // Nothing
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
