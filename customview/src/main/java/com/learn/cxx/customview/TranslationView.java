package com.learn.cxx.customview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author hzchenxuexing
 * @date 2016年01月04日
 * <p>
 * Copyright 2015 NetEase. All rights reserved.
 */
public class TranslationView extends View {

    private AnimatorSet animSet;

    private Paint paint;

    private float radius;
    private PointF center;
    private RectF drawArea;
    private RectF virtualCircle = new RectF();
    private float maxAnimDis;
    private float strokeWidth;

    // 从正方形开始到圆形
    private float transProgress1;
    private float rotateProgress1;
    private float scaleProgress1;
    private boolean rect2circle = true;

    // 圆到正方形
    private float transProgress2 = 1;
    private float rotateProgress2 = 90;
    private float scaleProgress2 = 1;
    private boolean circle2rect = true;

    private boolean isLoading;

    public TranslationView(Context context) {
        super(context);
        init(context, null);
    }

    public TranslationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TranslationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public TranslationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context c, AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        strokeWidth = 2;
        paint.setStrokeWidth(strokeWidth);
    }

    public void start() {
        initAnimIfNeeded();
        if (!animSet.isRunning())
            animSet.start();
        isLoading = true;
    }

    public void stop() {
        if (animSet.isRunning())
            animSet.cancel();
        isLoading = false;
    }

    public boolean isLoading() {
        return isLoading;
    }

    private void initAnimIfNeeded() {
        if (animSet != null)
            return;
        animSet = new AnimatorSet();
        ValueAnimator transAnim = ValueAnimator.ofFloat(0.0f, 1200.0f);
        transAnim.setDuration(1200);
        transAnim.setRepeatCount(ValueAnimator.INFINITE);
        transAnim.setRepeatMode(ValueAnimator.RESTART);
        transAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                transProgress1 = (float) animation.getAnimatedValue();

                float value = (float) animation.getAnimatedValue();
                if (value > 0 && value < 800)
                    transProgress1 = value / 800;
                else if (value <= 0)
                    transProgress1 = 0;
                else if (value >= 800)
                    transProgress1 = 1;

                transProgress2 = 1 - transProgress1;

                invalidate();
            }
        });
        transAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.w("repeat", "repeat");
                rect2circle = !rect2circle;
                circle2rect = !circle2rect;
            }
        });
        final ValueAnimator rotateAnim = ValueAnimator.ofFloat(0.0f, 1.0f);
        rotateAnim.setDuration(1200);
        rotateAnim.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnim.setRepeatMode(ValueAnimator.RESTART);
        rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rotateProgress1 = (float) animation.getAnimatedValue() * 90;
                scaleProgress1 = (float) animation.getAnimatedValue();

                rotateProgress2 = rotateProgress1 + 90;
                scaleProgress2 = 1 - scaleProgress1;

                invalidate();
            }
        });
        animSet.playTogether(transAnim, rotateAnim);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (center == null)
            center = new PointF();
        center.set(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        if (drawArea == null)
            drawArea = new RectF();
        radius = Math.min(center.x, center.y) - strokeWidth / 2;
        drawArea.left = center.x - radius;
        drawArea.top = center.y - radius;
        drawArea.right = center.x + radius;
        drawArea.bottom = center.y + radius;
        maxAnimDis = (float) (radius - radius / Math.sqrt(2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画第一个
        int savecount = canvas.save();
        canvas.rotate(rotateProgress1 + 45, center.x, center.y);
        float dis = 0;
        if (rect2circle) {
            dis = transProgress1 * maxAnimDis;
            canvas.scale((float) (0.5 + 0.5 * scaleProgress1), (float) (0.5 + 0.5 * scaleProgress1), center.x, center.y);
        } else {
            dis = (1 - transProgress1) * maxAnimDis;
            canvas.scale((float) (1 - 0.5 * scaleProgress1), (float) (1 - 0.5 * scaleProgress1), center.x, center.y);
        }
        double d = Math.toDegrees(Math.atan(dis / (radius / Math.sqrt(2))));
        float virtualRadius = (float) (radius / Math.sqrt(2) / Math.cos(Math.toRadians(90 - d - d)));
//
        float tmp = (float) (radius / Math.sqrt(2));
        if (virtualRadius > Short.MAX_VALUE / 2) {
            canvas.drawRect(center.x - tmp, center.y - tmp, center.x + tmp, center.y + tmp, paint);
        } else {
            float sAngle = (float) Math.toDegrees(Math.acos((virtualRadius - dis) / virtualRadius));
            virtualCircle.left = center.x - virtualRadius;
            virtualCircle.top = center.y - tmp - dis;
            virtualCircle.right = center.x + virtualRadius;
            virtualCircle.bottom = center.y + 2 * virtualRadius - dis - tmp;
            canvas.drawArc(virtualCircle, -sAngle - 90, 2 * sAngle, false, paint);

            canvas.rotate(90, center.x, center.y);
            canvas.drawArc(virtualCircle, -sAngle - 90, 2 * sAngle, false, paint);
            canvas.rotate(90, center.x, center.y);
            canvas.drawArc(virtualCircle, -sAngle - 90, 2 * sAngle, false, paint);
            canvas.rotate(90, center.x, center.y);
            canvas.drawArc(virtualCircle, -sAngle - 90, 2 * sAngle, false, paint);
        }
        canvas.rotate(rotateProgress1, center.x, center.y);
        canvas.restoreToCount(savecount);


        // 画第二个
        savecount = canvas.save();
        canvas.rotate(rotateProgress2 + 45, center.x, center.y);
        if (circle2rect) {
            dis = transProgress2 * maxAnimDis;
            canvas.scale((float) (0.5 + 0.5 * scaleProgress2), (float) (0.5 + 0.5 * scaleProgress2), center.x, center.y);
        } else {
            dis = (1 - transProgress2) * maxAnimDis;
            canvas.scale((float) (1 - 0.5 * scaleProgress2), (float) (1 - 0.5 * scaleProgress2), center.x, center.y);
        }
        d = Math.toDegrees(Math.atan(dis / (radius / Math.sqrt(2))));
        virtualRadius = (float) (radius / Math.sqrt(2) / Math.cos(Math.toRadians(90 - d - d)));

        tmp = (float) (radius / Math.sqrt(2));
        if (virtualRadius > Short.MAX_VALUE / 2) {
            canvas.drawRect(center.x - tmp, center.y - tmp, center.x + tmp, center.y + tmp, paint);
        } else {
            float sAngle = (float) Math.toDegrees(Math.acos((virtualRadius - dis) / virtualRadius));
            virtualCircle.left = center.x - virtualRadius;
            virtualCircle.top = center.y - tmp - dis;
            virtualCircle.right = center.x + virtualRadius;
            virtualCircle.bottom = center.y + 2 * virtualRadius - dis - tmp;
            canvas.drawArc(virtualCircle, -sAngle - 90, 2 * sAngle, false, paint);

            canvas.rotate(90, center.x, center.y);
            canvas.drawArc(virtualCircle, -sAngle - 90, 2 * sAngle, false, paint);
            canvas.rotate(90, center.x, center.y);
            canvas.drawArc(virtualCircle, -sAngle - 90, 2 * sAngle, false, paint);
            canvas.rotate(90, center.x, center.y);
            canvas.drawArc(virtualCircle, -sAngle - 90, 2 * sAngle, false, paint);
        }
        canvas.rotate(rotateProgress2, center.x, center.y);
        canvas.restoreToCount(savecount);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isLoading && animSet != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                animSet.resume();
            else
                animSet.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isLoading && animSet != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                animSet.pause();
            else
                animSet.cancel();
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
//        Log.w("onWindowVisibilityChanged",visibility+"");
    }
}
