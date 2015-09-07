package com.practice.barView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 用来静态显示百分比的柱形图
 */
public class BarView extends View {

    private int progressColor;
    private Paint progressPaint;
    private RectF progressRect;
    private boolean isInit = true;
    private AccelerateDecelerateInterpolator defaultInterpolator;
    private final int RefreshView = 1;
    private final int UpdateView = 2;
    private int defaultFps = 60;
    private int animDuration = 500;
    private int animRefreshCount ;
    private int animRefreshInterval;
    private RefreshThread refreshThread;
    private int contentWidth;
    private int contentHeight;
    private int currentPercent;
    private float currentPercentWidth;
    private float lastPercentWidth;

    private float cornerRadius;
    private int mBackgroundColor;

    private RectF backgroundRect;

    private Paint backgroundPaint;

    private Path roundRectPath;

    private boolean innerCorner;//进度条是否圆角

    private boolean isProgressMode = false;//进度条的刷新模式是基于当前值更新还是从0开始

    private boolean withAnimation = false;//动画效果


    public BarView(Context context) {
        super(context);
        init(null, 0);
    }

    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.BarView, defStyle, 0);
        int defaultProgressColor = getResources().getColor(android.R.color.holo_orange_light);


        progressColor = a.getColor(R.styleable.BarView_barColor, defaultProgressColor);

        currentPercent = a.getInt(R.styleable.BarView_percent, 0);

        animDuration = a.getInt(R.styleable.BarView_animDuration, 500);

        cornerRadius = a.getDimension(R.styleable.BarView_cornerRadius, 0);

        mBackgroundColor = a.getColor(R.styleable.BarView_backgroundColor, getResources().getColor(android.R.color.darker_gray));

        innerCorner = a.getBoolean(R.styleable.BarView_innerCorner, false);

        a.recycle();
        setBackgroundColor(Color.TRANSPARENT);
        initValues();
    }

    private void initValues(){
        int oneSecond = 1000;
        animRefreshInterval = oneSecond/defaultFps;//60帧每秒时，刷新的时间间隔
        animRefreshCount = animDuration/animRefreshInterval + 1;//刷新的总次数

        progressPaint = new Paint();
        progressPaint.setColor(progressColor);
        progressPaint.setAntiAlias(true);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(mBackgroundColor);
        backgroundPaint.setAntiAlias(true);

        defaultInterpolator = new AccelerateDecelerateInterpolator();

        refreshThread = new RefreshThread();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        if(isInit){
            contentWidth = getWidth() - paddingLeft - paddingRight;
            contentHeight = getHeight() - paddingTop - paddingBottom;
            currentPercentWidth = (float) contentWidth * (float) currentPercent/100f;
            progressRect = new RectF(0,0,0, contentHeight);//初始化时宽度为0
            backgroundRect = new RectF(0,0,contentWidth, contentHeight);
            roundRectPath = new Path();
            roundRectPath.reset();
            roundRectPath.addRoundRect(backgroundRect, cornerRadius, cornerRadius, Path.Direction.CCW);

        }
        //裁切显示空间
        if(cornerRadius != 0){
            Path mPath = new Path();
            mPath.reset();
            canvas.clipPath(mPath);
            canvas.clipPath(roundRectPath, Region.Op.UNION);
        }
        //绘制背景和进度条，此时进度条宽度为0
        canvas.drawRoundRect(backgroundRect, cornerRadius, cornerRadius, backgroundPaint);
        if(innerCorner){
            canvas.drawRoundRect(progressRect, cornerRadius, cornerRadius,progressPaint);
        }else {
            canvas.drawRect(progressRect, progressPaint);
        }
        //将进度条绘制为初始值，仅初始化时有效
        if(isInit){
            paintBar();
            isInit = false;
        }


    }


    Handler refreshHandler = new Handler() {
        public void handleMessage(Message msg) {
            float ratio = (float) msg.arg1/(float)(animRefreshCount - 1);//每次刷新的百分比
            switch (msg.what) {
                case RefreshView:
                    progressRect.right = currentPercentWidth * defaultInterpolator.getInterpolation(ratio) ;
                    BarView.this.invalidate();
                    break;

                case UpdateView:
                    progressRect.right = (currentPercentWidth - lastPercentWidth) * defaultInterpolator.getInterpolation(ratio) + lastPercentWidth;
                    BarView.this.invalidate();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    class RefreshThread implements Runnable {
        public void run() {
            try {
                for(int i = 0; i < animRefreshCount; i ++ ){
                    Message message = new Message();
                    message.what = isProgressMode? UpdateView: RefreshView;
                    message.arg1 = i;
                    BarView.this.refreshHandler.sendMessage(message);
                    Thread.sleep(animRefreshInterval);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 基于已有的值更新
     * @param percent 百分比0-100
     * */
    public void updatePercent(int percent){
        withAnimation = true;
        isProgressMode = true;
        setProgressPercent(percent);
    }

    /**
     * 直接显示设置的百分比
     * @param percent 百分比0-100
     * */
    public void resetPercent(int percent){
        withAnimation = false;
        isProgressMode = false;
        setProgressPercent(percent);

    }

    /**
     * 设置百分比，带动画效果
     * @param percent 百分比0-100
     * */
    public void resetPercentSmoothly(int percent){
        withAnimation = true;
        isProgressMode = false;
        setProgressPercent(percent);

    }


    private void setProgressPercent(int percent){
        currentPercent = percent;
        if(!isInit){
            paintBar();
        }
    }


    public void setProgressColor(int color){
        progressColor = color;
        progressPaint.setColor(progressColor);
    }

    private void paintBar(){
        lastPercentWidth = currentPercentWidth;
        currentPercentWidth = (float) contentWidth * (float) currentPercent/100f;
        if(withAnimation){
            new Thread(refreshThread).start();
        }
        else {
            progressRect.right = currentPercentWidth ;
            BarView.this.invalidate();
        }

        withAnimation = false;
    }




}
