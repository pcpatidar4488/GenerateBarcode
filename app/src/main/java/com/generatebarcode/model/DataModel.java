package com.generatebarcode.model;

import android.graphics.Bitmap;

/**
 * Created by anupamchugh on 11/02/17.
 */

public class DataModel {


    public String description;
    public Bitmap bitmap;
    public String code_type;

    public DataModel(String t, Bitmap d, String c )
    {
        description =t;
        bitmap =d;
        code_type =c;
    }
}
