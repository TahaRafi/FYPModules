package com.example.taha.fypfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class NewsDetail extends AppCompatActivity {
WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        webView=(WebView) findViewById(R.id.webview);
        Bundle bundle=getIntent().getExtras();
        webView.loadUrl(bundle.getString("Link"));

    }
}
