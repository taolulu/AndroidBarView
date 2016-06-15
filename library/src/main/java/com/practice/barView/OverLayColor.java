package com.practice.barView;

import android.graphics.Color;

/**
 * Created by taXer on 16/6/15.
 */
public class OverLayColor {
    private int color;
    private float marginLeft = 0;
    private float marginRight = 0;
    private float marginTop = 0;
    private float marginBottom = 0;

    public OverLayColor(int color, float marginLeft, float marginTop, float marginRight, float marginBottom) {
        this.color = color;
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        this.marginTop = marginTop;
        this.marginBottom = marginBottom;
    }

    public OverLayColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(float marginLeft) {
        this.marginLeft = marginLeft;
    }

    public float getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(float marginRight) {
        this.marginRight = marginRight;
    }

    public float getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(float marginTop) {
        this.marginTop = marginTop;
    }

    public float getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(float marginBottom) {
        this.marginBottom = marginBottom;
    }
}
