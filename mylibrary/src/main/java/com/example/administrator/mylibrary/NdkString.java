package com.example.administrator.mylibrary;

/**
 * Created by Administrator on 2016/6/5.
 */
public class NdkString {

    static{
        System.loadLibrary("mylibrary");


    }

    public static native String getFromC();

}
