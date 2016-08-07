package com.example.administrator.learnrv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    /**
     * 代替ListView的新控件
     */
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rv = new RecyclerView(this);


        setContentView(rv);

        //分成四列
        rv.setLayoutManager(new GridLayoutManager(this,4));
       // rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        //给RecyclerView填充内容
        rv.setAdapter(new MyAdapter());


    }

}
