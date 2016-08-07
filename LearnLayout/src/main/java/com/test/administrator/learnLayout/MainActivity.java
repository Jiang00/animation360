package com.test.administrator.learnLayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout root;
    private Button btnClickme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);  //设置垂直方向布局
        setContentView(root);


        for (int i = 0; i < 10; i++) {

            btnClickme = new Button(this);
            btnClickme.setText("Click me" + i);
            btnClickme.setOnClickListener(this);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            lp.weight = 1;

            root.addView(btnClickme, lp);

            //  root.addView(btnClickme);

            //   root.addView(btnClickme,300,200);

            //   root.addView(btnClickme,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

            //布局参数


        }


    }

    @Override
    public void onClick(View v) {

        root.removeView(v);

    }
}
