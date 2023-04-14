package com.mimikri.adslite.format;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mimikri.adslite.util.Constant;
import com.mimikri.adslite.R;

import com.startapp.sdk.ads.nativead.NativeAdDetails;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import java.util.ArrayList;

public class NativeAdViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "AdNetwork";
    LinearLayout nativeAdViewContainer;



    //StartApp
    View startappNativeAd;
    ImageView startappNativeImage;
    ImageView startappNativeIcon;
    TextView startappNativeTitle;
    TextView startappNativeDescription;
    Button startappNativeButton;
    LinearLayout startappNativeBackground;



    public NativeAdViewHolder(View view) {
        super(view);

        nativeAdViewContainer = view.findViewById(R.id.native_ad_view_container);





        //StartApp
        startappNativeAd = view.findViewById(R.id.startapp_native_ad_container);
        startappNativeImage = view.findViewById(R.id.startapp_native_image);
        startappNativeIcon = view.findViewById(R.id.startapp_native_icon);
        startappNativeTitle = view.findViewById(R.id.startapp_native_title);
        startappNativeDescription = view.findViewById(R.id.startapp_native_description);
        startappNativeButton = view.findViewById(R.id.startapp_native_button);
        startappNativeButton.setOnClickListener(v1 -> itemView.performClick());
        startappNativeBackground = view.findViewById(R.id.startapp_native_background);



    }

    public void loadNativeAd(Context context, String adStatus, int placementStatus, String adNetwork, String backupAdNetwork, String fanNativeId, String appLovinNativeId, boolean darkTheme, boolean legacyGDPR, String nativeAdStyle) {
        if (adStatus.equals(Constant.AD_STATUS_ON)) {
            if (placementStatus != 0) {
                switch (adNetwork) {

                    case Constant.STARTAPP:
                        if (startappNativeAd.getVisibility() != View.VISIBLE) {
                            StartAppNativeAd startAppNativeAd = new StartAppNativeAd(context);
                            NativeAdPreferences nativePrefs = new NativeAdPreferences()
                                    .setAdsNumber(3)
                                    .setAutoBitmapDownload(true)
                                    .setPrimaryImageSize(Constant.STARTAPP_IMAGE_MEDIUM);
                            AdEventListener adListener = new AdEventListener() {
                                @Override
                                public void onReceiveAd(@NonNull Ad arg0) {
                                    Log.d("STARTAPP_ADS", "ad loaded");
                                    startappNativeAd.setVisibility(View.VISIBLE);
                                    nativeAdViewContainer.setVisibility(View.VISIBLE);
                                    //noinspection rawtypes
                                    ArrayList ads = startAppNativeAd.getNativeAds(); // get NativeAds list

                                    // Print all ads details to log
                                    for (Object ad : ads) {
                                        Log.d("STARTAPP_ADS", ad.toString());
                                    }

                                    NativeAdDetails ad = (NativeAdDetails) ads.get(0);
                                    if (ad != null) {
                                        startappNativeImage.setImageBitmap(ad.getImageBitmap());
                                        startappNativeIcon.setImageBitmap(ad.getSecondaryImageBitmap());
                                        startappNativeTitle.setText(ad.getTitle());
                                        startappNativeDescription.setText(ad.getDescription());
                                        startappNativeButton.setText(ad.isApp() ? "Install" : "Open");
                                        ad.registerViewForInteraction(itemView);
                                    }

                                    if (darkTheme) {
                                        startappNativeBackground.setBackgroundResource(R.color.colorBackgroundDark);
                                    } else {
                                        startappNativeBackground.setBackgroundResource(R.color.colorBackgroundLight);
                                    }

                                }

                                @Override
                                public void onFailedToReceiveAd(Ad arg0) {
                                    //startapp_native_ad.setVisibility(View.GONE);
                                    //native_ad_view_container.setVisibility(View.GONE);
                                    loadBackupNativeAd(context, adStatus, placementStatus, backupAdNetwork, fanNativeId, appLovinNativeId, darkTheme, legacyGDPR, nativeAdStyle);
                                    Log.d(TAG, "ad failed");
                                }
                            };
                            startAppNativeAd.loadAd(nativePrefs, adListener);
                        } else {
                            Log.d(TAG, "StartApp native ads has been loaded");
                        }
                        break;


                }
            }
        }
    }

    public void loadBackupNativeAd(Context context, String adStatus, int placementStatus, String backupAdNetwork, String fanNativeId, String appLovinNativeId, boolean darkTheme, boolean legacyGDPR, String nativeAdStyle) {
        if (adStatus.equals(Constant.AD_STATUS_ON)) {
            if (placementStatus != 0) {
                switch (backupAdNetwork) {


                    case Constant.STARTAPP:
                        if (startappNativeAd.getVisibility() != View.VISIBLE) {
                            StartAppNativeAd startAppNativeAd = new StartAppNativeAd(context);
                            NativeAdPreferences nativePrefs = new NativeAdPreferences()
                                    .setAdsNumber(3)
                                    .setAutoBitmapDownload(true)
                                    .setPrimaryImageSize(Constant.STARTAPP_IMAGE_MEDIUM);
                            AdEventListener adListener = new AdEventListener() {
                                @Override
                                public void onReceiveAd(@NonNull Ad arg0) {
                                    Log.d("STARTAPP_ADS", "ad loaded");
                                    startappNativeAd.setVisibility(View.VISIBLE);
                                    nativeAdViewContainer.setVisibility(View.VISIBLE);
                                    //noinspection rawtypes
                                    ArrayList ads = startAppNativeAd.getNativeAds(); // get NativeAds list

                                    // Print all ads details to log
                                    for (Object ad : ads) {
                                        Log.d("STARTAPP_ADS", ad.toString());
                                    }

                                    NativeAdDetails ad = (NativeAdDetails) ads.get(0);
                                    if (ad != null) {
                                        startappNativeImage.setImageBitmap(ad.getImageBitmap());
                                        startappNativeIcon.setImageBitmap(ad.getSecondaryImageBitmap());
                                        startappNativeTitle.setText(ad.getTitle());
                                        startappNativeDescription.setText(ad.getDescription());
                                        startappNativeButton.setText(ad.isApp() ? "Install" : "Open");
                                        ad.registerViewForInteraction(itemView);
                                    }

                                    if (darkTheme) {
                                        startappNativeBackground.setBackgroundResource(R.color.colorBackgroundDark);
                                    } else {
                                        startappNativeBackground.setBackgroundResource(R.color.colorBackgroundLight);
                                    }

                                }

                                @Override
                                public void onFailedToReceiveAd(Ad arg0) {
                                    startappNativeAd.setVisibility(View.GONE);
                                    nativeAdViewContainer.setVisibility(View.GONE);
                                    Log.d(TAG, "ad failed");
                                }
                            };
                            startAppNativeAd.loadAd(nativePrefs, adListener);
                        } else {
                            Log.d(TAG, "StartApp native ads has been loaded");
                        }
                        break;


                    case Constant.NONE:
                        nativeAdViewContainer.setVisibility(View.GONE);
                        break;

                }
            }
        }
    }

    public void setNativeAdPadding(int left, int top, int right, int bottom) {
        nativeAdViewContainer.setPadding(left, top, right, bottom);
    }

}
