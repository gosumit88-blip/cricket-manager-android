package com.cricketmanager.game;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        WebView webView = new WebView(this);
        
        // Settings chalu karne ke liye
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        
        // Yeh line JavaScript ko automatic popups kholne ki permission deti hai
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        
        // WebViewClient normal page loading handle karta hai
        webView.setWebViewClient(new WebViewClient());
        
        // WebChromeClient popups, alerts aur dialogues ko handle karta hai
        webView.setWebChromeClient(new WebChromeClient());
        
        setContentView(webView);
        webView.loadUrl("file:///android_asset/index.html");
    }
}
