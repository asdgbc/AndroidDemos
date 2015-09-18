package com.chenxx.library.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chenxx on 15-9-14.
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSizeWithPadding = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int heightSizeWithPadding = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int maxWidth = 0;
        int maxHeight = 0;

        int lineMaxWidth = 0;
        int lineMaxHeight = 0;

        MarginLayoutParams lp = null;

        for (int i = 0; i < getChildCount(); i++) {
            measureChildWithMargins(getChildAt(i), widthMeasureSpec, 0, heightMeasureSpec, 0);
            lp = (MarginLayoutParams) getChildAt(i).getLayoutParams();
            int childWidthWithMargin = getChildAt(i).getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeightWithMargin = getChildAt(i).getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineMaxWidth == 0 && lineMaxHeight == 0 && childWidthWithMargin >= widthSizeWithPadding) {
                maxWidth = widthSizeWithPadding;
                maxHeight += childHeightWithMargin;
                lineMaxHeight = 0;
                lineMaxWidth = 0;
            } else if (lineMaxWidth + childWidthWithMargin > widthSizeWithPadding) {
                if (lineMaxHeight != 0)
                    maxHeight += lineMaxHeight;
                maxWidth = Math.max(maxWidth, lineMaxWidth);//maxWidth > lineMaxWidth ? maxWidth : lineMaxWidth;

                lineMaxHeight = childHeightWithMargin;
                lineMaxWidth = childWidthWithMargin;
            } else {
                lineMaxWidth += childWidthWithMargin;
                lineMaxHeight = Math.max(lineMaxHeight, childHeightWithMargin);//lineMaxHeight > childHeighWithMargin ? lineMaxHeight : childHeighWithMargin;
            }

        }
        if (lineMaxHeight != 0)
            maxHeight += lineMaxHeight;

        widthSizeWithPadding += getPaddingLeft() + getPaddingRight();
        heightSizeWithPadding += getPaddingBottom() + getPaddingTop();

        LayoutParams lp1 = getLayoutParams();
        setMeasuredDimension(resolveSize(widthMode, lp1.width, widthSizeWithPadding, maxWidth), resolveSize(heightMode, lp1.height, heightSizeWithPadding, maxHeight));
    }

    private int resolveSize(int heightMode, int childSize, int size, int maxSize) {
        int measuredSize = 0;
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                if (childSize > 0) {
                    measuredSize = childSize;
                } else if (childSize == LayoutParams.WRAP_CONTENT) {
                    measuredSize = maxSize > size ? size : maxSize;
                } else if (childSize == LayoutParams.MATCH_PARENT) {
                    measuredSize = size;
                }
                break;
            case MeasureSpec.UNSPECIFIED:
                if (childSize > 0) {
                    measuredSize = childSize;
                } else if (childSize == LayoutParams.WRAP_CONTENT || childSize == LayoutParams.MATCH_PARENT) {
                    measuredSize = maxSize;
                }
                break;
        }
        return measuredSize;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int lw = r - l;
        int lh = b - t;

        int lx = getPaddingLeft();
        int ly = getPaddingTop();
        int line = 0;

        int singleLineMaxHeight = 0;

        View v = null;
        MarginLayoutParams lp = null;
        int cl = 0;
        int ct = 0;
        int cr = 0;
        int cb = 0;

        for (int i = 0; i < getChildCount(); i++) {
            v = getChildAt(i);
            lp = (MarginLayoutParams) v.getLayoutParams();

            if (lx == getPaddingLeft() && (v.getMeasuredWidth() + lp.leftMargin + lp.rightMargin + lx + getPaddingRight()) >= lw) {
                cl = lx + lp.leftMargin;
                ct = ly + lp.topMargin;

                cr = Math.min(cl + v.getMeasuredWidth(), lw - getPaddingRight());//(cl + v.getMeasuredWidth()) > (lw - getPaddingRight()) ? (lw - getPaddingRight()) : (cl + v.getMeasuredWidth());
                cb = Math.min(ct + v.getMeasuredHeight(), lh - getPaddingBottom());//(ct + v.getMeasuredHeight()) > (lh - getPaddingBottom()) ? (lh - getPaddingBottom()) : (ct + v.getMeasuredHeight());

                v.layout(cl, ct, cr, cb);
                ly += lp.topMargin + lp.bottomMargin + v.getMeasuredHeight();
                lx = getPaddingLeft();
                singleLineMaxHeight = 0;
            } else if (lx + v.getMeasuredWidth() + lp.leftMargin + lp.rightMargin + getPaddingRight() > lw) {
                line++;
                if (singleLineMaxHeight != 0)
                    ly += singleLineMaxHeight;
                singleLineMaxHeight = v.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                lx = getPaddingLeft();
                cl = lx + lp.leftMargin;
                ct = ly + lp.topMargin;

                cr = Math.min(cl + v.getMeasuredWidth(), lw - getPaddingRight());
                cb = Math.min(ct + v.getMeasuredHeight(), lh - getPaddingBottom());
                v.layout(cl, ct, cr, cb);
                lx = getPaddingLeft() + v.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            } else {
                cl = lx + lp.leftMargin;
                ct = ly + lp.topMargin;
                cr = Math.min(cl + v.getMeasuredWidth(), lw - getPaddingRight());
                cb = Math.min(ct + v.getMeasuredHeight(), lh - getPaddingBottom());

                v.layout(cl, ct, cr, cb);
                lx += lp.leftMargin + lp.rightMargin + v.getMeasuredWidth();
                singleLineMaxHeight = singleLineMaxHeight > (lp.topMargin + lp.bottomMargin + v.getMeasuredHeight()) ? singleLineMaxHeight : (lp.topMargin + lp.bottomMargin + v.getMeasuredHeight());

            }

        }
    }
}
