package com.imooc.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/1/11.
 */
public class CustomCameraActivity extends Activity implements SurfaceHolder.Callback {

    private Camera mCamera;
    private SurfaceView mPreView;
    private SurfaceHolder mSurfaceHolder;
    //这个回调的数据会保存我们的一个完整的数据
    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
                //这里的data数据是一个完全完整的图片
            File temFile = new File("/sdcard/tem.png");
            if(!temFile.exists()){

                temFile.mkdir();

            }

            try {

                FileOutputStream fileOutputStream = new FileOutputStream(temFile);
                fileOutputStream.write(data);
                fileOutputStream.close();

                Intent intent = new Intent(CustomCameraActivity.this,ResultActivity.class);
                intent.putExtra("picPath",temFile.getAbsolutePath());
                startActivity(intent);
                CustomCameraActivity.this.finish(); //拍照结束后保存文件将文件路径传递到下一个Activity中并同时关闭当前的Activity

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom);
        mPreView = (SurfaceView) findViewById(R.id.preview);
        mSurfaceHolder = mPreView.getHolder();
        mSurfaceHolder.addCallback(this);
        mPreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //点击屏幕的时候让它自动对焦
                mCamera.autoFocus(null);

                Toast.makeText(CustomCameraActivity.this,"自动对焦..",Toast.LENGTH_SHORT).show();

            }
        });


        findViewById(R.id.btn_camera_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照方法实现
                Camera.Parameters parameters = mCamera.getParameters();
                //保存图片格式
                parameters.setPictureFormat(ImageFormat.JPEG);
                //分辨率
                parameters.setPreviewSize(800, 400);
                //自动对焦
                parameters.setFocusMode(Camera.Parameters.FLASH_MODE_AUTO);
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        //success代表是否对焦准确
                        if(success){
                            mCamera.takePicture(null,null,pictureCallback);


                        }


                    }
                });




            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            //这样的写法我们就将Activity的生命周期和camera的生命周期绑定在了一起，确保了资源能够顺利的被初始化和被释放
            mCamera = getCamera();
            if (mSurfaceHolder != null) {
                setStartPreView(mCamera, mSurfaceHolder);

            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();


    }

    /**
     * 获取系统初始化完毕的Camera对象
     *
     * @return
     */
    private Camera getCamera() {
        Camera camera;
        try {
            camera = Camera.open(0);
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }

        return camera;
    }

    /**
     * 开始预览相机内容
     */
    private void setStartPreView(Camera camera, SurfaceHolder holder) {
        try {
            //绑定holder
            camera.setPreviewDisplay(holder);
            //因为系统Camera预览角度默认是横屏的,我们需要将其转到90度,把它转成竖屏
            camera.setDisplayOrientation(90);
            camera.startPreview();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 释放相机
     */
    private void releaseCamera() {
        if (mCamera != null) {
            //先将相机的回调置空
            mCamera.setPreviewCallback(null);
            //先把相机停止预览
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        setStartPreView(mCamera, mSurfaceHolder);


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        //发生改变的时候,我们需要重启整个功能,首先停止预览
        mCamera.stopPreview();
        //重启预览功能
        setStartPreView(mCamera, mSurfaceHolder);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //销毁的时候调用释放相机的操作
        releaseCamera();
    }
}
