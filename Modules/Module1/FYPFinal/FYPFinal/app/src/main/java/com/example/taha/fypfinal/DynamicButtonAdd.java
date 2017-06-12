package com.example.taha.fypfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.security.AccessController.getContext;

public class DynamicButtonAdd extends AppCompatActivity {


   public ArrayList<String> Str=new ArrayList<>();
    static public ArrayList<String> Str1=new ArrayList<>();

    Button b;
    ScrollView scrollview;
    /** Called when the activity is first created. */

    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        Str.add("false");
        Str.add("false");
        Str.add("false");
        Str.add("false");
        Str.add("false");
        Str.add("false");
        Str.add("false");
        Str.add("false");





        Str1.add("Headlines");
        Str1.add("Pakistan News");
        Str1.add("Business News");
        Str1.add("Sports News");
        Str1.add("Entertainment News");
        Str1.add("Health News");
        Str1.add("Amazing News");
        Str1.add("Education");



        scrollview = new ScrollView(this);
        LinearLayout linearlayout = new LinearLayout(this);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        scrollview.addView(linearlayout);

        for(int i = 0; i<9;i++)
        {
            if(i==8)
            {
                LinearLayout linear1 = new LinearLayout(this);
                linear1.setOrientation(LinearLayout.HORIZONTAL);
                linearlayout.addView(linear1);
                b = new Button(this);
                b.setText("Done");
                b.setId(i);
                b.setTextSize(10);
                b.setPadding(8, 3, 8, 3);

                b.setTypeface(Typeface.SERIF,Typeface.BOLD_ITALIC);
                b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
                linear1.addView(b);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                 /////



                        //////



                        int arr[]=new int[8];


                           int kk=0;
                        for(int i=0;i<Str.size();i++)
                        {
                            if(i==8)
                            {
                                break;
                            }

                            if(Str.get(i)=="true")
                            {
                                arr[kk]=1;
                                kk++;
                            }
                            else
                            {
                                arr[kk]=0;
                                kk++;
                            }

                        }


                        for(int i=0;i<Str.size();i++)
                        {
                            Log.d("Simple",Str.get(i));
                        }




                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(), "Done..", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(v.getContext(),
                                CustomizeButton2.class);
                            Str=null;
                        myIntent.putExtra("numbers", arr);

                        startActivityForResult(myIntent, 0);
                        finish();
                    }
                });
            }
            else
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
                b.setLayoutParams(new LayoutParams(200,50));

                linear1.addView(b);

                CheckBox checkbox = new CheckBox(this);
                checkbox.setId(i);
                checkbox.setText("Wow..");
                linear1.addView(checkbox);

                checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                        // TODO Auto-generated method stub
                        if(arg1==true)
                        {
                            Str.set(arg0.getId(),"true");
                            Toast.makeText(getApplicationContext(), "Checked.."+ arg0.getId(), Toast.LENGTH_SHORT).show();
                            for(int i=0;i<Str.size();i++)
                            {
                                Log.d("myaray",Str.get(i));
                            }

                        }
                        else
                        {
                            Str.set(arg0.getId(),"false");
                            Toast.makeText(getApplicationContext(), "unChecked.."+ arg0.getId(), Toast.LENGTH_SHORT).show();
                            for(int i=0;i<Str.size();i++)
                            {
                                Log.d("myaray",Str.get(i));
                            }

                        }
                        //Toast.makeText(getApplicationContext(), "Checked.."+ arg0.getId(), Toast.LENGTH_SHORT).show();
                    }
                });

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







    }

































}
