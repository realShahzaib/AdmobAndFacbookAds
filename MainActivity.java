package com.example.admob_facbookads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.admob_facbookads.AdmobAds.Admobe_Activity;
import com.example.admob_facbookads.FacebookAds.Facebook_Activity;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button fb_interstitial, admob_interstitial;
    Intent intent;
    // admob interstitial & banner ad objects
    InterstitialAd admobInterstitialAd;
    AdView admobBannerAd;

    // facebook Interstitial & banner ad objectes
    com.facebook.ads.InterstitialAd fbInterstitialAd;
    com.facebook.ads.AdView fbBannerAdVeiw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ini widgets here
        iniWidgets();

        // admobe ads initialize
        MobileAds.initialize(this, getString(R.string.admobApp_id));
        admobBannerAd();
        admobeInterstitialad();

        //facebook Ad initialize
        AudienceNetworkAds.initialize(this);
        // for test facebook ads you must use this line of code
        AdSettings.addTestDevice("");

        fbBannerAd();
        fbInterstitialAd();

    }

    private void iniWidgets(){

        fb_interstitial = findViewById(R.id.fb_interstitial);
        admob_interstitial = findViewById(R.id.admob_interstitial);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){

            case R.id.admob_interstitial:
                if (admobInterstitialAd.isLoaded()){
                    admobInterstitialAd.show();
                    admobInterstitialAd.setAdListener(new AdListener()
                    {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();

                            admobeInterstitialad();
                            openAdmobActivity();
                        }
                    });
                }
                else {
                    admobeInterstitialad();
                    openAdmobActivity();
                }

                break;

            case R.id.fb_interstitial:
                if (fbInterstitialAd.isAdLoaded()){
                    fbInterstitialAd.show();
                    fbInterstitialAd.setAdListener(new InterstitialAdListener() {
                        @Override
                        public void onInterstitialDisplayed(Ad ad) {

                        }

                        @Override
                        public void onInterstitialDismissed(Ad ad) {
                            openFacbookActivity();
                        }

                        @Override
                        public void onError(Ad ad, AdError adError) {
                            openFacbookActivity();
                        }

                        @Override
                        public void onAdLoaded(Ad ad) {

                        }

                        @Override
                        public void onAdClicked(Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {

                        }
                    });
                }
                else {
                    openFacbookActivity();
                }

                break;
        }
    }

    private void openAdmobActivity(){

        intent = new Intent(MainActivity.this, Admobe_Activity.class);
        startActivity(intent);
    }

    private void openFacbookActivity(){

        intent = new Intent(MainActivity.this, Facebook_Activity.class);
        startActivity(intent);
    }

    private void admobeInterstitialad() {

            admobInterstitialAd = new InterstitialAd(MainActivity.this);
        admobInterstitialAd.setAdUnitId(getString(R.string.admobInterstitial_id));
            AdRequest admobAdRequest = new AdRequest.Builder().build();
        admobInterstitialAd.loadAd(admobAdRequest);
        }

    private void admobBannerAd(){
        admobBannerAd = (AdView) findViewById(R.id.admobBanner_id);
        AdRequest adRequest = new AdRequest.Builder().build();
        admobBannerAd.loadAd(adRequest);

    }

    private void fbInterstitialAd(){

            fbInterstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.facebookApp_id));
            // AdSettings.addTestDevice("9b49532803c24baeb2bb_328f53337c66");
            fbInterstitialAd.loadAd();
    }

    private void fbBannerAd(){

        fbBannerAdVeiw = new com.facebook.ads.AdView(this, getString(R.string.fbBanner_id), AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer =  findViewById(R.id.fb_banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(fbBannerAdVeiw);

        //load fb banner ad
        fbBannerAdVeiw.loadAd();

    }

}