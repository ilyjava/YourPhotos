package com.ilyjava.android.yourphotos.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import org.w3c.dom.Attr;

/**
 * Created by Никита on 05.05.2018.
 */

public class SquareLayout extends RelativeLayout {
    public SquareLayout(Context context){
        super(context);
    }
    public SquareLayout (Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }
    @TargetApi (Build.VERSION_CODES.LOLLIPOP)
    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
