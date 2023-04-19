package com.mimikri.adslite.format;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.mimikri.adslite.util.Constant;
import com.mimikri.adslite.R;

import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.BannerListener;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class BannerAd {

    public static class Builder {

        private static final String TAG = "AdNetwork";
        private final Activity activity;

        FrameLayout ironSourceBannerView;
        private RelativeLayout pangleBannerView;
        private IronSourceBannerLayout ironSourceBannerLayout;
      //  private PAGBannerAd mPAGBannerAd;
        BannerView bannerview;

        private String adStatus = "";
        private String adNetwork = "";
        private String backupAdNetwork = "";
        private String fanBannerId = "";
        private String appLovinBannerId = "";
        private String appLovinBannerZoneId = "";
        private String unityBannerId = "";
        private String pangleBannerId = "";
        private String ironSourceBannerId = "";
        private int placementStatus = 1;
        private boolean darkTheme = false;
        private boolean legacyGDPR = false;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder build() {
            loadBannerAd();
            return this;
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

        public Builder setFanBannerId(String fanBannerId) {
            this.fanBannerId = fanBannerId;
            return this;
        }

        public Builder setAppLovinBannerId(String appLovinBannerId) {
            this.appLovinBannerId = appLovinBannerId;
            return this;
        }

        public Builder setAppLovinBannerZoneId(String appLovinBannerZoneId) {
            this.appLovinBannerZoneId = appLovinBannerZoneId;
            return this;
        }
        public Builder setUnityBannerId(String unityBannerId) {
            this.unityBannerId = unityBannerId;
            return this;
        }
        public Builder setPangleBannerId(String pangleBannerId) {
            this.pangleBannerId = pangleBannerId;
            return this;
        }

        public Builder setIronSourceBannerId(String ironSourceBannerId) {
            this.ironSourceBannerId = ironSourceBannerId;
            return this;
        }

        public Builder setPlacementStatus(int placementStatus) {
            this.placementStatus = placementStatus;
            return this;
        }

        public Builder setDarkTheme(boolean darkTheme) {
            this.darkTheme = darkTheme;
            return this;
        }

        public Builder setLegacyGDPR(boolean legacyGDPR) {
            this.legacyGDPR = legacyGDPR;
            return this;
        }

        public void loadBannerAd() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                switch (adNetwork) {
                    case Constant.STARTAPP:
                        RelativeLayout startAppAdView = activity.findViewById(R.id.startapp_banner_view_container);
                        Banner banner = new Banner(activity, new BannerListener() {
                            @Override
                            public void onReceiveAd(View banner) {
                                if (adNetwork.equals(Constant.STARTAPP)) {
                                    startAppAdView.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onFailedToReceiveAd(View banner) {
                                startAppAdView.setVisibility(View.GONE);
                                loadBackupBannerAd();
                                Log.d(TAG, adNetwork + " failed load startapp banner ad : ");
                            }

                            @Override
                            public void onImpression(View view) {

                            }

                            @Override
                            public void onClick(View banner) {
                            }
                        });
                        startAppAdView.addView(banner);
                        break;




                    case Constant.IRONSOURCE:
                        ironSourceBannerView = activity.findViewById(R.id.ironsource_banner_view_container);
                        ISBannerSize size = ISBannerSize.BANNER;
                        ironSourceBannerLayout = IronSource.createBanner(activity, size);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                        ironSourceBannerView.addView(ironSourceBannerLayout, 0, layoutParams);
                        if (ironSourceBannerLayout != null) {
                            ironSourceBannerLayout.setBannerListener(new com.ironsource.mediationsdk.sdk.BannerListener() {
                                @Override
                                public void onBannerAdLoaded() {
                                    Log.d(TAG, "onBannerAdLoaded");
                                    ironSourceBannerView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onBannerAdLoadFailed(IronSourceError error) {
                                    Log.d(TAG, "onBannerAdLoadFailed" + " " + error);
                                    loadBackupBannerAd();
                                }

                                @Override
                                public void onBannerAdClicked() {
                                    Log.d(TAG, "onBannerAdClicked");
                                }

                                @Override
                                public void onBannerAdScreenPresented() {
                                    Log.d(TAG, "onBannerAdScreenPresented");
                                }

                                @Override
                                public void onBannerAdScreenDismissed() {
                                    Log.d(TAG, "onBannerAdScreenDismissed");
                                }

                                @Override
                                public void onBannerAdLeftApplication() {
                                    Log.d(TAG, "onBannerAdLeftApplication");
                                }
                            });
                            IronSource.loadBanner(ironSourceBannerLayout, ironSourceBannerId);
                        } else {
                            Log.d(TAG, "IronSource.createBanner returned null");
                        }
                        break;

                    case Constant.UNITY:
                        RelativeLayout unitylyt = activity.findViewById(R.id.unity_banner_view_container);
                        BannerView.IListener bannerListener = new BannerView.IListener() {
                            @Override
                            public void onBannerLoaded(BannerView bannerAdView) {
                                Log.v(TAG, "onBannerLoaded: " + bannerAdView.getPlacementId());
                                unitylyt.setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
                                Log.e(TAG, "Unity Ads failed to load banner for " + bannerAdView.getPlacementId() + " with error: [" + errorInfo.errorCode + "] " + errorInfo.errorMessage);
                                unitylyt.setVisibility(View.GONE);
                                loadBackupBannerAd();
                            }
                            @Override
                            public void onBannerClick(BannerView bannerAdView) {
                                Log.v(TAG, "onBannerClick: " + bannerAdView.getPlacementId());
                            }
                            @Override
                            public void onBannerLeftApplication(BannerView bannerAdView) {
                                Log.v(TAG, "onBannerLeftApplication: " + bannerAdView.getPlacementId());}};
                        bannerview = new BannerView(activity, unityBannerId, new UnityBannerSize(320, 50));
                        bannerview.setListener(bannerListener);
                        bannerview.load();
                        unitylyt.addView(bannerview);

                        break;


                    case Constant.NONE:
                        //do nothing
                        break;
                }
                Log.d(TAG, "Banner Ad is enabled");
            } else {
                Log.d(TAG, "Banner Ad is disabled");
            }
        }

        public void loadBackupBannerAd() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                switch (backupAdNetwork) {
                    case Constant.STARTAPP:
                        RelativeLayout startAppAdView = activity.findViewById(R.id.startapp_banner_view_container);
                        Banner banner = new Banner(activity, new BannerListener() {
                            @Override
                            public void onReceiveAd(View banner) {
                                startAppAdView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailedToReceiveAd(View banner) {
                                startAppAdView.setVisibility(View.GONE);
                                Log.d(TAG, adNetwork + " failed load startapp banner ad : ");
                            }

                            @Override
                            public void onImpression(View view) {

                            }

                            @Override
                            public void onClick(View banner) {
                            }
                        });
                        startAppAdView.addView(banner);
                        break;


                    case Constant.IRONSOURCE:
                        ironSourceBannerView = activity.findViewById(R.id.ironsource_banner_view_container);
                        ISBannerSize size = ISBannerSize.BANNER;
                        ironSourceBannerLayout = IronSource.createBanner(activity, size);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                        ironSourceBannerView.addView(ironSourceBannerLayout, 0, layoutParams);
                        if (ironSourceBannerLayout != null) {
                            ironSourceBannerLayout.setBannerListener(new com.ironsource.mediationsdk.sdk.BannerListener() {
                                @Override
                                public void onBannerAdLoaded() {
                                    Log.d(TAG, "onBannerAdLoaded");
                                    ironSourceBannerView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onBannerAdLoadFailed(IronSourceError error) {
                                    Log.d(TAG, "onBannerAdLoadFailed" + " " + error);
                                }

                                @Override
                                public void onBannerAdClicked() {
                                    Log.d(TAG, "onBannerAdClicked");
                                }

                                @Override
                                public void onBannerAdScreenPresented() {
                                    Log.d(TAG, "onBannerAdScreenPresented");
                                }

                                @Override
                                public void onBannerAdScreenDismissed() {
                                    Log.d(TAG, "onBannerAdScreenDismissed");
                                }

                                @Override
                                public void onBannerAdLeftApplication() {
                                    Log.d(TAG, "onBannerAdLeftApplication");
                                }
                            });
                            IronSource.loadBanner(ironSourceBannerLayout, ironSourceBannerId);
                        } else {
                            Log.d(TAG, "IronSource.createBanner returned null");
                        }
                        break;


                    case Constant.UNITY:
                        RelativeLayout unitylyt = activity.findViewById(R.id.unity_banner_view_container);
                        BannerView.IListener bannerListener = new BannerView.IListener() {
                            @Override
                            public void onBannerLoaded(BannerView bannerAdView) {
                                Log.v(TAG, "onBannerLoaded: " + bannerAdView.getPlacementId());
                                unitylyt.setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
                                Log.e(TAG, "Unity Ads failed to load banner for " + bannerAdView.getPlacementId() + " with error: [" + errorInfo.errorCode + "] " + errorInfo.errorMessage);
                                unitylyt.setVisibility(View.GONE);
                            }
                            @Override
                            public void onBannerClick(BannerView bannerAdView) {
                                Log.v(TAG, "onBannerClick: " + bannerAdView.getPlacementId());
                            }
                            @Override
                            public void onBannerLeftApplication(BannerView bannerAdView) {
                                Log.v(TAG, "onBannerLeftApplication: " + bannerAdView.getPlacementId());}};
                        bannerview = new BannerView(activity, unityBannerId, new UnityBannerSize(320, 50));
                        bannerview.setListener(bannerListener);
                        bannerview.load();
                        unitylyt.addView(bannerview);

                        break;

                }
                Log.d(TAG, "Banner Ad is enabled");
            } else {
                Log.d(TAG, "Banner Ad is disabled");
            }
        }

        public void destroyAndDetachBanner() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {

                if (adNetwork.equals(Constant.IRONSOURCE) || backupAdNetwork.equals(Constant.IRONSOURCE)) {
                    if (ironSourceBannerView != null) {
                        Log.d(TAG, "ironSource banner is not null, ready to destroy");
                        IronSource.destroyBanner(ironSourceBannerLayout);
                        ironSourceBannerView.removeView(ironSourceBannerLayout);
                    } else {
                        Log.d(TAG, "ironSource banner is null");
                    }
                }
            }
        }

    }

}
