package com.example.administrator.learncomponents;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class ChooseADate extends AppCompatActivity {

    private Button btnChooseDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_adate);

        btnChooseDate = (Button) findViewById(R.id.btnChooseDate);

        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new DatePickerDialog(ChooseADate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Toast.makeText(ChooseADate.this, String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth), Toast.LENGTH_SHORT).show();
                        String theDay = String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth);
                        btnChooseDate.setText(theDay);

                    }
                }, 2015, 3, 30).show();


            }
        });

    }
}
