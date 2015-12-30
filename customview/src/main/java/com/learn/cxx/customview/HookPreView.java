package com.learn.cxx.customview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * @author hzchenxuexing
 * @date 2015年12月29日
 * <p/>
 * Copyright 2015 NetEase. All rights reserved.
 */
public class HookPreView extends View {

    public final static int STATE_INIT = 0;//状态：初始化
    public final static int STATE_LOADING = 1;//状态：正在加载。。
    public final static int STATE_COMPLETE = 2;//状态：完成

    public final static float PRE_LOADING_DURING = 700;

    public final static float BG_GREY_SCALE_DURING = 600;
    public final static float BG_GREY_OPACITY_DURING = 400;

    public final static float BG_WHITE_SCALE_OFFSET = 200;
    public final static float BG_WHITE_SCALE_DURING = 500;

    public final static float LOADING_DURING = 900;
    public final static float LOADING_OFFSET = BG_WHITE_SCALE_OFFSET;

    public final static float COMPLETE_DURING = 1100;
    public final static float HOOK_OFFSET = BG_WHITE_SCALE_DURING;
    private final static float DEF_STROKE_WIDTH = 4;//绘制圆形的线的厚度


    public int state = STATE_INIT;

    private Point center;
    private float radius;
    private RectF cirArea;
    private DecelerateInterpolator interpolator = new DecelerateInterpolator();
    private DecelerateInterpolator deepInterpolator = new DecelerateInterpolator(4);
    private float degreeOffset = -90f;
    /**
     * Loading相关变量
     */
    private AnimatorSet loadingAnim;
    private float preProgress;

    private Paint greyPaint;//圆的画笔
    private float strokeWidth = DEF_STROKE_WIDTH;

    private Paint whitePaint;

    private Paint progressPaint;//进度的画壁
    private float sDegree;
    private float eDegree;

    /**
     * Complete相关变量
     */
    private AnimatorSet completeAnim;
    //    private ValueAnimator completeAnim;
    private Point rotatedStartPoint;
    private Point rotatedTurnPoint;
    private float hookMaxLength;
    private float cirProgress;
    private float hookProgress;

    /**
     * Text相关
     */
    private float textSize;
    private String loadingText;
    private float loadingTextDimenWidth;
    private float loadingTextProgress;
    private String completeText;
    private float completeTextDimenWidth;
    private float completeTextProgress;
    private Paint textPaint;

    private float textOffset = 0;
    private float sPos = 100;
    private float ePos = 0;


    private OnAnimatorCompleteListener animCompleteListener;

    public HookPreView(Context context) {
        super(context);
        init(context, null);
    }

    public HookPreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HookPreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public HookPreView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HookPreView);
            if (ta.hasValue(R.styleable.HookPreView_loadingText))
                loadingText = ta.getString(R.styleable.HookPreView_loadingText);
            if (ta.hasValue(R.styleable.HookPreView_completeText))
                completeText = ta.getString(R.styleable.HookPreView_completeText);

            textSize = ta.getDimension(R.styleable.HookPreView_textSize, 20);
            ta.recycle();
        }

        greyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greyPaint.setColor(Color.parseColor("#dedede"));
        greyPaint.setStyle(Style.FILL);

        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Style.FILL);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(Color.parseColor("#49b56a"));
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStyle(Style.STROKE);

        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.BLACK);

        // 计算文字像素宽度
        if (!TextUtils.isEmpty(loadingText))
            loadingTextDimenWidth = textPaint.measureText(loadingText);
        if (!TextUtils.isEmpty(completeText))
            completeTextDimenWidth = textPaint.measureText(completeText);

        final ValueAnimator preAnim = ValueAnimator.ofFloat(0.0f, PRE_LOADING_DURING);
        preAnim.setInterpolator(new DecelerateInterpolator(4));
        preAnim.setDuration((long) PRE_LOADING_DURING);
        preAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                preProgress = (float) animation.getAnimatedValue();

                invalidate();
            }
        });
        ValueAnimator loading = ValueAnimator.ofFloat(0.0f, LOADING_DURING);
        loading.setRepeatMode(ValueAnimator.RESTART);
        loading.setRepeatCount(ValueAnimator.INFINITE);
        loading.setStartDelay((long) PRE_LOADING_DURING + 200);
        loading.setDuration((long) LOADING_DURING);
        loading.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if ((float) animation.getAnimatedValue() < 700f)
                    sDegree = interpolator.getInterpolation((float) animation.getAnimatedValue() / 700) * 360;
                else
                    sDegree = 360;

                if ((float) animation.getAnimatedValue() >= LOADING_OFFSET)
                    eDegree = interpolator.getInterpolation(((float) animation.getAnimatedValue() - LOADING_OFFSET) / 700) * 360;
                else
                    eDegree = 0;
                invalidate();
            }
        });
        ValueAnimator textAnim = null;
        if (!TextUtils.isEmpty(loadingText) || !TextUtils.isEmpty(completeText)) {
            textAnim = ValueAnimator.ofFloat(0.0f, 1.0f);
            textAnim.setDuration(700l);
            textAnim.setInterpolator(new DecelerateInterpolator(4));
            textAnim.setStartDelay(200);
            textAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    loadingTextProgress = animation.getAnimatedFraction();
                    invalidate();
                }
            });
        }
        loadingAnim = new AnimatorSet();
        if (textAnim != null)
            loadingAnim.playTogether(preAnim, loading, textAnim);
        else
            loadingAnim.playTogether(preAnim, loading);


    }

    public void startLoading() {
        stopLoading();
        loadingAnim.start();
        state = STATE_LOADING;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (state == STATE_LOADING) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                loadingAnim.resume();
            else
                loadingAnim.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (state == STATE_LOADING) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                loadingAnim.pause();
            else
                loadingAnim.cancel();
        }
    }

    public void stopLoading() {
        if (loadingAnim.isRunning()) {
            loadingAnim.cancel();
        }
        preProgress = 0;
        sDegree = 0;
        eDegree = 0;
        loadingTextProgress = 0;
        completeTextProgress = 0;
        state = STATE_INIT;
    }

    public void complete() {
        stopLoading();
        state = STATE_COMPLETE;
        hookProgress = 0;
        loadingTextProgress = 0;
        completeTextProgress = 0;
        if (completeAnim == null) {
            completeAnim = new AnimatorSet();
            ValueAnimator hookAnim = ValueAnimator.ofFloat(0.0f, COMPLETE_DURING);
            hookAnim.setDuration((long) COMPLETE_DURING);
            hookAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animValue = (float) animation.getAnimatedValue();
                    if (animValue < 700)
                        cirProgress = interpolator.getInterpolation(animValue / 700);
                    else
                        cirProgress = 1;

                    if (animValue >= HOOK_OFFSET)
                        hookProgress = (animValue - HOOK_OFFSET) / (COMPLETE_DURING - HOOK_OFFSET);

                    if (animValue < 100)
                        loadingTextProgress = 0;
                    else if (animValue >= 100 && animValue < 800)
                        loadingTextProgress = deepInterpolator.getInterpolation((animValue - 100) / 700);
                    else if (animValue > 800)
                        loadingTextProgress = 1f;

                    if (animValue > 500)
                        completeTextProgress = interpolator.getInterpolation((animValue - 500) / 600);

                    invalidate();
                }
            });
            hookAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (animCompleteListener != null)
                        animCompleteListener.onAnimatorComplete();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            completeAnim.play(hookAnim);
        }
        completeAnim.start();
    }

    public boolean isLoading() {
        return loadingAnim.isRunning();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (center == null)
            center = new Point();
        center.set(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        radius = Math.min(center.x, center.y);
        if (cirArea == null)
            cirArea = new RectF();
        cirArea.left = center.x - radius;
        cirArea.top = center.y - radius;
        cirArea.right = center.x + radius;
        cirArea.bottom = center.y + radius;
        cirArea.inset(strokeWidth / 2, strokeWidth / 2);
        double sqrt2 = Math.sqrt(2);
        hookMaxLength = (float) ((17 * sqrt2) * radius / 15 / 0.73);
        if (rotatedTurnPoint == null)
            rotatedTurnPoint = new Point();

        rotatedTurnPoint.set((int) (sqrt2 * radius + (11 * radius / 60 / sqrt2)), (int) (-35 * radius / 60 / sqrt2));
        if (rotatedStartPoint == null)
            rotatedStartPoint = new Point();
        rotatedStartPoint.set((int) (sqrt2 * radius - Math.sqrt(239.0f / 288) * radius), rotatedTurnPoint.y);
        textOffset = 2 * radius + 100;

        ePos = center.y - radius;
        sPos = ePos + 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        progressPaint.setStrokeWidth(strokeWidth);
        canvas.drawRect(cirArea, progressPaint);
        if (state == STATE_LOADING) {
            // 设置灰色圆的透明度
            if (preProgress < BG_GREY_OPACITY_DURING)
                greyPaint.setAlpha((int) (255 * preProgress / BG_GREY_OPACITY_DURING));
            else
                greyPaint.setAlpha(255);
            // 绘制灰色圆
            if (preProgress < BG_GREY_SCALE_DURING) {
                canvas.drawCircle(center.x, center.y, radius * (0.7f + 0.3f * (preProgress / BG_GREY_SCALE_DURING)), greyPaint);
            } else
                canvas.drawCircle(center.x, center.y, radius, greyPaint);

            // 绘制白色圆
            if (preProgress >= BG_WHITE_SCALE_OFFSET && preProgress < BG_WHITE_SCALE_OFFSET + BG_WHITE_SCALE_DURING) {
                canvas.drawCircle(center.x, center.y, (radius - strokeWidth) * (preProgress - BG_WHITE_SCALE_OFFSET) / BG_WHITE_SCALE_DURING, whitePaint);
            } else if (preProgress >= BG_WHITE_SCALE_DURING + BG_WHITE_SCALE_OFFSET) {
                canvas.drawCircle(center.x, center.y, radius - strokeWidth, whitePaint);
            }

            // 绘制绿线
            if (sDegree > 0 || eDegree > 0) {
                canvas.drawArc(cirArea, eDegree + degreeOffset, sDegree - eDegree, false, progressPaint);
            }

            // 绘制文字
            if (loadingTextProgress != 0 && !TextUtils.isEmpty(loadingText)) {
                textPaint.setAlpha((int) (255 * loadingTextProgress));
                canvas.drawText(loadingText, center.x - loadingTextDimenWidth / 2, textSize + sPos - (sPos - ePos) * loadingTextProgress, textPaint);
            }

        } else if (state == STATE_COMPLETE) {

            canvas.drawCircle(center.x, center.y, radius, greyPaint);
            canvas.drawCircle(center.x, center.y, radius - strokeWidth, whitePaint);
            canvas.drawArc(cirArea, degreeOffset, cirProgress * 360, false, progressPaint);

            int saveCount = canvas.save();
            if (hookProgress != 0) {
                canvas.rotate(45, center.x - radius, center.y - radius);
                progressPaint.setStrokeWidth(strokeWidth * 2);
                float s = hookMaxLength * interpolator.getInterpolation(hookProgress);
                float e = (float) (0.27 * s);
                if (s - e <= rotatedTurnPoint.x - rotatedStartPoint.x - e)
                    canvas.drawLine(rotatedStartPoint.x + e, -rotatedStartPoint.y, rotatedStartPoint.x + s, -rotatedStartPoint.y, progressPaint);
                else {
                    canvas.drawLine(rotatedStartPoint.x + e, -rotatedStartPoint.y, rotatedTurnPoint.x, -rotatedStartPoint.y, progressPaint);
                    canvas.drawLine(rotatedTurnPoint.x, -rotatedTurnPoint.y + strokeWidth, rotatedTurnPoint.x, -rotatedTurnPoint.y - (s - rotatedTurnPoint.x + rotatedStartPoint.x), progressPaint);
                }
                canvas.restoreToCount(saveCount);
            }

            if (!TextUtils.isEmpty(loadingText)) {
                textPaint.setAlpha((int) ((1 - loadingTextProgress) * 255));
                canvas.drawText(loadingText, center.x - loadingTextDimenWidth / 2, textSize + ePos, textPaint);
            }

            // 绘制文字
            if (completeTextProgress != 0 && !TextUtils.isEmpty(completeText)) {
                textPaint.setAlpha((int) (255 * completeTextProgress));
                canvas.drawText(completeText, center.x - completeTextDimenWidth / 2, textSize + sPos - (sPos - ePos) * completeTextProgress, textPaint);
            }

        }

    }

    public void setOnAnimatorCompleteListener(OnAnimatorCompleteListener listener) {
        animCompleteListener = listener;
    }

    public interface OnAnimatorCompleteListener {
        void onAnimatorComplete();
    }

}
