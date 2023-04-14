package com.mimikri.adslite.format;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.InterstitialListener;

import com.mimikri.adslite.util.Constant;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

public class InterstitialAd {

    public static class Builder {

        private static final String TAG = "AdNetwork";
        private final Activity activity;

        private StartAppAd startAppAd;

        private int retryAttempt;
        private int counter = 1;

        private String adStatus = "";
        private String adNetwork = "";
        private String backupAdNetwork = "";
        private String fanInterstitialId = "";
        private String appLovinInterstitialId = "";
        private String appLovinInterstitialZoneId = "";
        private String unityInterstitialId = "";
        private String pangleInterstitialId = "";
        private String ironSourceInterstitialId = "";
        private int placementStatus = 1;
        private int interval = 3;

        private boolean legacyGDPR = false;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder build() {
            loadInterstitialAd();
            return this;
        }

        public void show() {
            showInterstitialAd();
        }

        public Builder setAdStatus(String adStatus) {
            this.adStatus = adStatus;
            return this;
        }

        public Builder setAdNetwork(String adNetwork) {
            this.adNetwork = adNetwork;
            return this;
        }

        public Builder setBackupAdNetwork(String backupAdNetwork) {
            this.backupAdNetwork = backupAdNetwork;
            return this;
        }



        public Builder setFanInterstitialId(String fanInterstitialId) {
            this.fanInterstitialId = fanInterstitialId;
            return this;
        }


        public Builder setAppLovinInterstitialId(String appLovinInterstitialId) {
            this.appLovinInterstitialId = appLovinInterstitialId;
            return this;
        }

        public Builder setAppLovinInterstitialZoneId(String appLovinInterstitialZoneId) {
            this.appLovinInterstitialZoneId = appLovinInterstitialZoneId;
            return this;
        }
        public Builder setUnityInterstitialId(String unityInterstitialId) {
            this.unityInterstitialId = unityInterstitialId;
            return this;
        }
        public Builder setPangleInterstitialId(String pangleInterstitialId) {
            this.pangleInterstitialId = pangleInterstitialId;
            return this;
        }

        public Builder setIronSourceInterstitialId(String ironSourceInterstitialId) {
            this.ironSourceInterstitialId = ironSourceInterstitialId;
            return this;
        }

        public Builder setPlacementStatus(int placementStatus) {
            this.placementStatus = placementStatus;
            return this;
        }

        public Builder setInterval(int interval) {
            this.interval = interval;
            return this;
        }

        public Builder setLegacyGDPR(boolean legacyGDPR) {
            this.legacyGDPR = legacyGDPR;
            return this;
        }

        public void loadInterstitialAd() {
            Log.d(TAG, "loadInterstitialAd");
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                Log.d(TAG, "loadInterstitialAd OK");
                switch (adNetwork) {
                    case Constant.STARTAPP:
                        startAppAd = new StartAppAd(activity);
                        startAppAd.loadAd(new AdEventListener() {
                            @Override
                            public void onReceiveAd(@NonNull Ad ad) {
                                Log.d(TAG, "Startapp Interstitial Ad loaded");
                            }

                            @Override
                            public void onFailedToReceiveAd(@Nullable Ad ad) {
                                Log.d(TAG, "Failed to load Startapp Interstitial Ad");
                                loadBackupInterstitialAd();
                            }
                        });
                        break;


                    case Constant.IRONSOURCE:
                        IronSource.setInterstitialListener(new InterstitialListener() {
                            @Override
                            public void onInterstitialAdReady() {
                                Log.d(TAG, "onInterstitialAdReady");
                            }

                            @Override
                            public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
                                Log.d(TAG, "onInterstitialAdLoadFailed" + " " + ironSourceError);
                                loadBackupInterstitialAd();
                            }

                            @Override
                            public void onInterstitialAdOpened() {
                                Log.d(TAG, "onInterstitialAdOpened");
                            }

                            @Override
                            public void onInterstitialAdClosed() {
                                Log.d(TAG, "onInterstitialAdClosed");
                                loadInterstitialAd();
                            }

                            @Override
                            public void onInterstitialAdShowSucceeded() {
                                Log.d(TAG, "onInterstitialAdShowSucceeded");
                            }

                            @Override
                            public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
                                Log.d(TAG, "onInterstitialAdShowFailed" + " " + ironSourceError);
                                loadBackupInterstitialAd();
                            }

                            @Override
                            public void onInterstitialAdClicked() {
                                Log.d(TAG, "onInterstitialAdClicked");
                            }
                        });
                        IronSource.loadInterstitial();
                        break;
                }
            }
        }

        public void loadBackupInterstitialAd() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                switch (backupAdNetwork) {

                    case Constant.STARTAPP:
                        startAppAd = new StartAppAd(activity);
                        startAppAd.loadAd(new AdEventListener() {
                            @Override
                            public void onReceiveAd(@NonNull Ad ad) {
                                Log.d(TAG, "Startapp Interstitial Ad loaded");
                            }

                            @Override
                            public void onFailedToReceiveAd(@Nullable Ad ad) {
                                Log.d(TAG, "Failed to load Startapp Interstitial Ad");
                            }
                        });
                        Log.d(TAG, "load StartApp as backup Ad");
                        break;



                    case Constant.IRONSOURCE:
                        IronSource.setInterstitialListener(new InterstitialListener() {
                            @Override
                            public void onInterstitialAdReady() {
                                Log.d(TAG, "onInterstitialAdReady");
                            }

                            @Override
                            public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
                                Log.d(TAG, "onInterstitialAdLoadFailed" + " " + ironSourceError);
                            }

                            @Override
                            public void onInterstitialAdOpened() {
                                Log.d(TAG, "onInterstitialAdOpened");
                            }

                            @Override
                            public void onInterstitialAdClosed() {
                                Log.d(TAG, "onInterstitialAdClosed");
                                loadInterstitialAd();
                            }

                            @Override
                            public void onInterstitialAdShowSucceeded() {
                                Log.d(TAG, "onInterstitialAdShowSucceeded");
                            }

                            @Override
                            public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
                                Log.d(TAG, "onInterstitialAdShowFailed" + " " + ironSourceError);
                            }

                            @Override
                            public void onInterstitialAdClicked() {
                                Log.d(TAG, "onInterstitialAdClicked");
                            }
                        });
                        IronSource.loadInterstitial();
                        break;

                    case Constant.NONE:
                        //do nothing
                        break;
                }
            }
        }

        public void showInterstitialAd() {
            Log.d(TAG, "showInterstitialAd");
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                if (counter == interval) {
                    switch (adNetwork) {


                        case Constant.STARTAPP:
                            if (startAppAd != null) {
                                startAppAd.showAd();
                                Log.d(TAG, "startapp interstitial not null [counter] : " + counter);
                            } else {
                                showBackupInterstitialAd();
                                Log.d(TAG, "startapp interstitial null");
                            }
                            break;



                    }
                    counter = 1;
                } else {
                    counter++;
                }
                Log.d(TAG, "Current counter : " + counter);
            }
        }

        public void showBackupInterstitialAd() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                Log.d(TAG, "Show Backup Interstitial Ad [" + backupAdNetwork.toUpperCase() + "]");
                switch (backupAdNetwork) {

                    case Constant.STARTAPP:
                        if (startAppAd != null) {
                            startAppAd.showAd();
                        }
                        break;


                    case Constant.IRONSOURCE:
                        if (IronSource.isInterstitialReady()) {
                            IronSource.showInterstitial(ironSourceInterstitialId);
                        }
                        break;

                    case Constant.NONE:
                        //do nothing
                        break;
                }
            }
        }

    }

}
