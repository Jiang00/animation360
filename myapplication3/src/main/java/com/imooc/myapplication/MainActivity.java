//<editor-fold desc="Description">
package com.imooc.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivity extends Activity {

    private static final int REQ_1 = 1;
    private static final int REQ_2 = 2;
    private static final String TAG = MainActivity.class.getName();

    private ImageView mImageView;
    private String mFilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.iv_image);
        mFilePath = Environment.getExternalStorageDirectory().getPath();
        mFilePath = mFilePath + "/" + "tem.png";


        findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //启动手机系统相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, REQ_1);


            }
        });


        findViewById(R.id.btn_camera2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //启动手机系统相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri phototUri = Uri.fromFile(new File(mFilePath));
                //这个方法的作用是将系统拍照后的存储路径进行修改,修改指定到我们设置的一个Uri上,这样一来,我们拍照完成之后就不需要去获取Intent中的信息,而是直接读取这个地址下存储的拍照的原图
                intent.putExtra(MediaStore.EXTRA_OUTPUT, phototUri);
                startActivityForResult(intent, REQ_2);

            }
        });


        findViewById(R.id.btn_customcamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, CustomCameraActivity.class));

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //我们重写该方法,接收从另外一个Activity所返回的一个数据

        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_1) { //判断是否是我们的Intent发出过去的请求
                Log.i(TAG, "into REQ_1");
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                mImageView.setImageBitmap(bitmap);

            } else if (requestCode == REQ_2) {

                FileInputStream fileInputStream = null;
                try {
                    fileInputStream = new FileInputStream(mFilePath);
                    Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                    if (null != bitmap) {
                        mImageView.setImageBitmap(bitmap);

                    }

                } catch (FileNotFoundException e) {

                    Log.e(TAG, "FileNotFoundException", e);

                } finally {

                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        Log.e(TAG, "IOException", e);
                    }

                }


            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
//</editor-fold>
