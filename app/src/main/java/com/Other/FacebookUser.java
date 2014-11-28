package com.Other;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.BaseAdapter;

/**
 * Created by debarg_a on 28/11/2014.
 */
public class FacebookUser {

    private Drawable picture;
    private String name;
    private String score;
    private String id;
    private BaseAdapter adapter;
    //protected abstract View.OnClickListener getOnClickListener();

    public FacebookUser(Drawable picture, String name, String score, String id) {
        this.picture = picture;
        this.name = name;
        this.score = score;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public String getScore() {
        return score;

    }

    public void setScore(String score) {
        this.score = score;

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(Drawable picture) {
        this.picture = picture;
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }
}
