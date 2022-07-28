package com.example.cookit;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable recipeImage ;
    private String titleStr ;
    private String descStr ;

    public void setIcon(Drawable icon) {
        recipeImage = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }

    public Drawable getIcon() {
        return this.recipeImage ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
}