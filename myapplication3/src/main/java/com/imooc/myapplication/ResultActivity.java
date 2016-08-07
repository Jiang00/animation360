package com.imooc.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2016/1/17.
 */
public class ResultActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        Intent intent =  getIntent();
        if(null != intent){
            String path = intent.getStringExtra("picPath");
            ImageView imageView = (ImageView) findViewById(R.id.iv_pic);

            try {
                FileInputStream inputStream = new FileInputStream(path);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Matrix matrix = new Matrix();
                matrix.setRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),matrix,true);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            //  Bitmap bitmap = BitmapFactory.decodeFile(path);
          //  imageView.setImageBitmap(bitmap);









        }

    }
}
