package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

import com.optc.optcdbmobile.R;

public class RepeatableDrawableWidget extends View {

    byte count;
    Drawable generalDrawable;


    int width, height;


    public RepeatableDrawableWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RepeatableDrawableWidget);
        generalDrawable = array.getDrawable(R.styleable.RepeatableDrawableWidget_drawable);

        if (generalDrawable != null) {
            width = generalDrawable.getIntrinsicWidth();
            height = generalDrawable.getIntrinsicHeight();
        }

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (width == 0 || height == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int minW = getPaddingLeft() + getPaddingRight() + width * count;
        int minH = getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom() + height;


        int W = View.resolveSize(minW, widthMeasureSpec);
        int H = View.resolveSize(minH, heightMeasureSpec);


        setMeasuredDimension(W, H);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (byte i = 0; i < count; i++) {
            generalDrawable.setBounds(
                    i * width, 0,
                    i * width + width,
                    height
            );

            generalDrawable.draw(canvas);
        }
    }


    public void setCount(Byte b) {
        this.count = b;
        invalidate();
        requestLayout();
    }

    public void setCount(Integer i) {
        setCount(i.byteValue());
    }

    public void setCount(Float f) {
        setCount(f.byteValue());
    }

    public void setGeneralDrawable(@DrawableRes int generalDrawable) {
        this.generalDrawable = ResourcesCompat.getDrawable(getResources(), generalDrawable, null);
        width = this.generalDrawable.getIntrinsicWidth();
        height = this.generalDrawable.getIntrinsicHeight();

        invalidate();
        requestLayout();
    }
}
