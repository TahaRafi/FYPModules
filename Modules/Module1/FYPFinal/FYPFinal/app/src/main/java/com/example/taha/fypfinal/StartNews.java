package com.example.taha.fypfinal;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class StartNews extends AppCompatActivity {
    Button b,b1;
    ScrollView scrollview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_news);


        scrollview = new ScrollView(this);
        LinearLayout linearlayout = new LinearLayout(this);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        scrollview.addView(linearlayout);

        LinearLayout linear1 = new LinearLayout(this);
        linear1.setOrientation(LinearLayout.HORIZONTAL);
        linearlayout.addView(linear1);
        b = new Button(this);
        b1=new Button(this);
        b.setText("General News");
        b1.setText("Customize News");
        b.setId(0);
        b1.setId(1);
        b.setTextSize(20);
        b1.setTextSize(20);


        b.setPadding(20, 22, 20, 22);
        b1.setPadding(20, 22, 20, 22);
        b.setTypeface(Typeface.SERIF,Typeface.BOLD_ITALIC);
        b1.setTypeface(Typeface.SERIF,Typeface.BOLD_ITALIC);
        b.setLayoutParams(new ViewGroup.LayoutParams(200,200));
        b1.setLayoutParams(new ViewGroup.LayoutParams(220,200));
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(v.getContext(),
                        MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),
                        DynamicButtonAdd.class);
                startActivityForResult(myIntent, 0);
            }
        });


        linear1.addView(b);
        linear1.addView(b1);
        this.setContentView(scrollview);
    }





}
