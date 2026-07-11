package com.cricketmanager.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class MainActivity extends Activity {
    private LinearLayout bannerContainer;
    private BannerView bannerView;
    
    // ⚠️ Yahan apni Unity Dashboard se mili 7-digit Game ID daalein (Testing ke liye koi bhi dummy ID nahi chalegi)
    private String unityGameId = "800084181"; 
    private String bannerPlacementId = "Banner_Android"; // Unity ka default Banner ID
    private boolean testMode = true; // Testing ke waqt ise true rakhein, real ads ke liye false karein

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Main vertical layout banana
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        // 2. WebView (Game) setup karna
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        // WebView poori screen lega (ad ko chhodkar)
        LinearLayout.LayoutParams webViewParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        webView.setLayoutParams(webViewParams);
        mainLayout.addView(webView);

        // 3. Unity Banner ke liye niche ek khali Container banana
        bannerContainer = new LinearLayout(this);
        LinearLayout.LayoutParams bannerParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bannerContainer.setLayoutParams(bannerParams);
        mainLayout.addView(bannerContainer);

        setContentView(mainLayout);

        // Game load karna
        webView.loadUrl("file:///android_asset/index.html");

        // 4. Unity Ads ko Initialize karna
        UnityAds.initialize(getApplicationContext(), unityGameId, testMode, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                // SDK chalu hote hi banner load karein
                loadUnityBanner();
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                // Agar init fail ho jaye
            }
        });
    }

    // Banner load karne ka function
    private void loadUnityBanner() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerView = new BannerView(MainActivity.this, bannerPlacementId, new UnityBannerSize(320, 50));
                bannerContainer.addView(bannerView);
                bannerView.load();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (bannerView != null) {
            bannerView.destroy();
        }
        super.onDestroy();
    }
}
