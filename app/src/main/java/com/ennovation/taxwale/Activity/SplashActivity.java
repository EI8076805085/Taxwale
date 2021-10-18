package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ennovation.taxwale.MainActivity;
import com.ennovation.taxwale.R;
import com.ennovation.taxwale.Utils.YourPreference;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;

public class SplashActivity extends AppCompatActivity {
    ImageView app_logo;
    int time = 3;
    public static String TAG="Keyhash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        String language = yourPrefrence.getData("language");
        hashFromSHA1("23:63:F0:A5:38:DF:30:3E:29:B1:62:1B:18:EF:B5:9C:AD:59:9C:BC");
        if (language!=null||!language.equalsIgnoreCase("")) {
            updateResources(SplashActivity.this,language);
        } else {
            updateResources(SplashActivity.this, "en");
        }

        app_logo = findViewById(R.id.app_logo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);
        app_logo.startAnimation(animation);
        Glide.with(this).load(R.drawable.ic_taxwale_logo_splash).into(app_logo);
        Completable.timer(time, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(SplashActivity.this::intentServiceFire);
    }

    private void intentServiceFire() {
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        String id = yourPrefrence.getData("USERNAME");
        if (id.equalsIgnoreCase("")||id.equalsIgnoreCase(null)) {
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    private static boolean updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return true;
    }

    public void hashFromSHA1(String sha1) {
        String[] arr = sha1.split(":");
        byte[] byteArr = new  byte[arr.length];

        for (int i = 0; i< arr.length; i++) {
            byteArr[i] = Integer.decode("0x" + arr[i]).byteValue();
        }
        Log.i(TAG, "printHashKey() Hash Key: " + Base64.encodeToString(byteArr, Base64.NO_WRAP));
        Log.e("hash : ", Base64.encodeToString(byteArr, Base64.NO_WRAP));
    }
}