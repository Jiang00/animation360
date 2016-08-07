package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {

    private ImageView mPhoto;

    private EditText editText;

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhoto = (ImageView) findViewById(R.id.iv_photo);
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.i(TAG, "onClick: is call");

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 100);

                Log.d(TAG, "onClick: ssss1");
                Log.d(TAG, "onClick: ssss2");
                Log.d(TAG, "onClick: ssss3");


                sayHello();


            }
        });

        editText = (EditText) findViewById(R.id.et_word);
        editText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));

        Button shareButton = (Button) findViewById(R.id.btn_share);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  test();
                Log.i(TAG, "onClick: ");

              //  shareButton.setText(Ndk);

            }
        });




    }

    public void test() {
        for (int i = 0; i < 9; i++) {

            Log.i(TAG, "test: i=="+i);


        }

    }

    public void test(int i){
        if(9 == i){

            Log.i(TAG, "test: i=9");
        }


    }



    private void sayHello() {

        Log.i(TAG, "sayHellow: ");

        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();


    }


    public void sayHello(String content, String content2) {


    }


    public void sayPersons(List<String> list) {

        for (String person : list) {
            Log.d(TAG, "sayPersons: persion=" + person);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            if (null != data) {
                mPhoto.setImageURI(data.getData());

            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();


    }
}
