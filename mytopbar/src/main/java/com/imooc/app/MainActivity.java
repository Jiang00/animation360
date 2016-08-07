package com.imooc.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Topbar topbar = (Topbar) findViewById(R.id.topBar);

        topbar.setTopBarClickListener(new Topbar.TopbarClickListener() {
            @Override
            public void leftClick() {
                Toast.makeText(MainActivity.this,"IMOOC LEFT",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {

                Toast.makeText(MainActivity.this,"IMOOC RIGHT",Toast.LENGTH_SHORT).show();
             }
        });

        // topbar.setLeftIsvisable(true);




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
