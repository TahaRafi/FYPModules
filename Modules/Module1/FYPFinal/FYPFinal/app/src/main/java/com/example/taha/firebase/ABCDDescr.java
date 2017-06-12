package com.example.taha.firebase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.taha.fypfinal.R;

public class ABCDDescr extends AppCompatActivity {
    TextView Title,Desc,Location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abcddescr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String value=getIntent().getExtras().getString("Link");



        Title= (TextView) findViewById(R.id.title);
        Desc= (TextView) findViewById(R.id.Desc);
        Location=(TextView) findViewById(R.id.Location);




        Title.setText(value);
        Desc.setText("");
        Location.setText("");
    }

}
