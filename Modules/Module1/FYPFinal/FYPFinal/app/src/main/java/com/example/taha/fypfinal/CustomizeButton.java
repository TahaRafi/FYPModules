package com.example.taha.fypfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class CustomizeButton extends AppCompatActivity {
    Button b;
    static public ArrayList<String> Str1=new ArrayList<>();
    ScrollView scrollview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_button);
        Bundle extras = getIntent().getExtras();
      //  int[] arrayB=new int[2];
        int[] arrayB = extras.getIntArray("numbers");
        for(int i=0;i<8;i++)
        {
            Log.d("Simple", String.valueOf(arrayB[i]));
        }


        Str1.add("Headlines");
        Str1.add("Pakistan News");
        Str1.add("Business News");
        Str1.add("Sports News");
        Str1.add("Entertainment News");
        Str1.add("Health News");
        Str1.add("Amazing News");
        Str1.add("Life Style News");
        scrollview = new ScrollView(this);
        LinearLayout linearlayout = new LinearLayout(this);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        scrollview.addView(linearlayout);
        for(int i = 0; i<8;i++) {

            if(arrayB[i]==1)
            {
                LinearLayout linear1 = new LinearLayout(this);
                linear1.setOrientation(LinearLayout.HORIZONTAL);
                linearlayout.addView(linear1);
                b = new Button(this);
                b.setText(Str1.get(i));
                b.setId(i);
                b.setTextSize(10);
                b.setPadding(8, 3, 8, 3);
                b.setTypeface(Typeface.SERIF,Typeface.BOLD_ITALIC);
                b.setLayoutParams(new ViewGroup.LayoutParams(200,50));

                linear1.addView(b);




                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(), "Yipee.."+ v.getId(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

        this.setContentView(scrollview);







































        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),
                        DynamicButtonAdd.class);
                startActivityForResult(myIntent, 0);
                finish();

            }
        });
    }

}
