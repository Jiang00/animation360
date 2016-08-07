package com.example.administrator.learncomponents;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class SingleChoose extends AppCompatActivity {

    private Button btnSubmit;

    private RadioButton radioButtonA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_choose);

        btnSubmit = (Button) findViewById(R.id.btn_submit);

        radioButtonA = (RadioButton) findViewById(R.id.rbA);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonA.isChecked()) {
                    Toast.makeText(SingleChoose.this, "恭喜你！所选是正确的", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(SingleChoose.this, "很遗憾！所选是错误的", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }
}
