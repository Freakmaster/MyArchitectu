package com.marklei.myarchitectu.ui_advanced.waveview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.marklei.myarchitectu.R;

/**
 * Created by leihao on 2017/9/26.
 */

public class WaveView extends View {

    private Paint mPaint;

    private int mWidth;
    private int mHeight;

    // 振幅
    private int mWaveHeight;
    // 波长
    private int mWaveLength;
    private int mLevelHeight;

    private float mAnimWaveProgress;
    private float mAnimShipProgress;
    private ValueAnimator animatorWave;
    private ValueAnimator animatorShip;
    private Path mPath;
    private Bitmap mBitmap;
    private PathMeasure measure;
    private Matrix matrix;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.RED);

        mPath = new Path();
        matrix = new Matrix();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg, options);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasureSize(300, widthMeasureSpec);
        int height = getMeasureSize(300, heightMeasureSpec);
        setMeasuredDimension(width, height);
        // 赋值view的宽和高
        mWidth = width > 0 ? width : 0;
        mHeight = height > 0 ? height : 0;

        mWaveHeight = mHeight / 5;
        mWaveLength = mWidth * 3 / 2;
        mLevelHeight = mHeight / 2;
    }

    /**
     * 测量布局的宽高
     *
     * @param defaultSize 测量的默认值大小
     * @param measureSpc  测量参数
     * @return 测量后的width or height
     */
    private int getMeasureSize(int defaultSize, int measureSpc) {
        int resultSize = 0;
        int model = MeasureSpec.getMode(measureSpc);
        int size = MeasureSpec.getSize(measureSpc);
        switch (model) {
            case MeasureSpec.AT_MOST:
                resultSize = defaultSize;
                break;
            case MeasureSpec.EXACTLY:
                resultSize = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                resultSize = size;
                break;
        }
        return resultSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(mWaveLength * (mAnimWaveProgress - 1), mLevelHeight);
        for (float i = -mWaveLength; i < mWidth + mWaveLength; i += mWaveLength) {
            mPath.rQuadTo(mWaveLength / 4, -mWaveHeight, mWaveLength / 2, 0);
            mPath.rQuadTo(mWaveLength / 4, mWaveHeight, mWaveLength / 2, 0);
        }
        measure = new PathMeasure(mPath, false);
        float length = measure.getLength();

        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();

        canvas.drawPath(mPath, mPaint);

        matrix.reset();
        measure.getMatrix(length * mAnimShipProgress, matrix, PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        matrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight());
        canvas.drawBitmap(mBitmap, matrix, mPaint);
    }

    public void startAnimWave() {
        reset();
        animatorWave = ValueAnimator.ofFloat(0, 1);
        animatorWave.setDuration(1000);
        animatorWave.setInterpolator(new LinearInterpolator());
        animatorWave.setRepeatCount(ValueAnimator.INFINITE);
        animatorWave.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimWaveProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animatorWave.start();
    }

    public void startAnimShip() {
        reset();
        animatorShip = ValueAnimator.ofFloat(0, 1);
        animatorShip.setDuration(7000);
        animatorShip.setInterpolator(new LinearInterpolator());
        animatorShip.setRepeatCount(ValueAnimator.INFINITE);
        animatorShip.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimShipProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animatorShip.start();
    }

    public void reset() {
        if (animatorWave != null && animatorWave.isRunning()) {
            animatorWave.cancel();
        }
        mAnimWaveProgress = 0;
        if (animatorShip != null && animatorShip.isRunning()) {
            animatorShip.cancel();
        }
        mAnimShipProgress = 0;
        invalidate();
    }
}
