package com.ennovation.taxwale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.an.biometric.BiometricCallback;
import com.an.biometric.BiometricManager;
import com.ennovation.taxwale.Activity.ConsultantQueryActivity;
import com.ennovation.taxwale.Activity.Document;
import com.ennovation.taxwale.Activity.Feed;
import com.ennovation.taxwale.Activity.GSTActivity;
import com.ennovation.taxwale.Activity.GSTFile;
import com.ennovation.taxwale.Activity.Help;
import com.ennovation.taxwale.Activity.ITRFile;
import com.ennovation.taxwale.Activity.History;
import com.ennovation.taxwale.Activity.ITRActivity;
import com.ennovation.taxwale.Activity.Notification;
import com.ennovation.taxwale.Activity.PayTax;
import com.ennovation.taxwale.Activity.Profile;
import com.ennovation.taxwale.Adapter.ServiceAdapter;
import com.ennovation.taxwale.Model.ServiceResponse;
import com.ennovation.taxwale.Utils.GpsUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BiometricCallback {

    ImageView img_taxwale,img_GSTFile,img_ITRFile,img_consultation,menudrawer;
    LinearLayout feedLayout,notificationLayout,historyLayout,documentLayout;
    RecyclerView service_recyclerView;
    TextView marque_Text,txt_payTax;
    RelativeLayout gst_Layout,itr_Layout;
    boolean doubleBackToExitPressedOnce = false;
    BiometricManager mBiometricManager;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        marque_Text = findViewById(R.id.text);
        marque_Text.setSelected(true);
        img_taxwale = findViewById(R.id.img_taxwale);
        menudrawer = findViewById(R.id.menudrawer);
        feedLayout = findViewById(R.id.feedLayout);
        notificationLayout = findViewById(R.id.notificationLayout);
        historyLayout = findViewById(R.id.historyLayout);
        documentLayout = findViewById(R.id.documentLayout);
        gst_Layout = findViewById(R.id.gst_Layout);
        itr_Layout = findViewById(R.id.itr_Layout);
        service_recyclerView = findViewById(R.id.service_recyclerView);
        img_GSTFile = findViewById(R.id.img_GSTFile);
        img_ITRFile = findViewById(R.id.img_ITRFile);
        img_consultation = findViewById(R.id.img_consultation);
        txt_payTax = findViewById(R.id.txt_payTax);

//        Dexter.withContext(this)
//                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                .withListener(new PermissionListener() {
//                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
//                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
//                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
//                }).check();
//
//        Dexter.withContext(this)
//                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                .withListener(new PermissionListener() {
//                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
//
//
//                        turngpspon();
//
//                    }
//                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
//
//
//
//                    }
//                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
//                }).check();

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE

                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
               if (report.areAllPermissionsGranted())
               {
                   turngpspon();
               }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();

//        try {
//            mBiometricManager = new BiometricManager.BiometricBuilder(MainActivity.this)
//                    .setTitle(getString(R.string.biometric_title))
//
//                    .setSubtitle(getString(R.string.biometric_subtitle))
//                    .setDescription(getString(R.string.biometric_description))
//                    .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
//                    .build();
//
//            //start authentication
//            mBiometricManager.authenticate(MainActivity.this);
//        }catch (NullPointerException e)
//        {
//            e.printStackTrace();
//            Toast.makeText(this, "Something went wrong ", Toast.LENGTH_SHORT).show();
//        }

        txt_payTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PayTax.class);
                startActivity(intent);
            }
        });

        img_consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConsultantQueryActivity.class);
                startActivity(intent);
            }
        });

        menudrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        feedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Feed.class));
                overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                finish();
            }
        });

        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Notification.class));
                overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                finish();
            }
        });

        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), History.class));
                overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                finish();
            }
        });

        documentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Document.class));
                overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                finish();
            }
        });

        gst_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GSTActivity.class);
                startActivity(intent);
            }
        });

        img_ITRFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ITRFile.class);
                startActivity(intent);
            }
        });

        img_GSTFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GSTFile.class);
                startActivity(intent);
            }
        });

        itr_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ITRActivity.class);
                startActivity(intent);
            }
        });

        ServiceResponse[] myListData = new ServiceResponse[] {
                new ServiceResponse(R.string.accounting, R.drawable.ic_accounting),
                new ServiceResponse(R.string.trademark , R.drawable.ic_trademark),
                new ServiceResponse(R.string.tax , R.drawable.ic_tax),
                new ServiceResponse(R.string.dsc , R.drawable.ic_digital_signature),
                new ServiceResponse(R.string.consulting , R.drawable.ic_consulting),
                new ServiceResponse(R.string.Status, R.drawable.ic_status),
        };

        ServiceAdapter adapter = new ServiceAdapter(myListData);
        service_recyclerView.setHasFixedSize(true);
        service_recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        service_recyclerView.setAdapter(adapter);
    }

    private void turngpspon() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getApplicationContext(), "Gps already enabled", Toast.LENGTH_SHORT).show();
                    //Do something after 100ms
                }
            }, 3000);

        } else {
            if (!hasGPSDevice(this)) {
                //   Toast.makeText(getApplicationContext(), "Gps not Supported", Toast.LENGTH_SHORT).show();
                finish();
            }
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
                //     Toast.makeText(getApplicationContext(), "Gps not enabled", Toast.LENGTH_SHORT).show();
                new GpsUtils(this).turnGPSOn(new GpsUtils.OnGpsListener() {
                    @Override
                    public void gpsStatus(boolean isGPSEnable) {
                        if (isGPSEnable) {

                            pd = new ProgressDialog(MainActivity.this);
                            pd.setMessage("Enabling GPS...");
                            pd.show();
                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    pd.dismiss();

                                    Toast.makeText(getApplicationContext(), "Gps  enabled", Toast.LENGTH_SHORT).show();
                                    //Do something after 100ms
                                }
                            }, 3000);

                        }
                    }
                });
            }
        }
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to Close Taxwale ", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void help(View view) {
        Intent intent = new Intent(MainActivity.this, Help.class);
        startActivity(intent);

    }

    @Override
    public void onSdkVersionNotSupported() {
        Toast.makeText(MainActivity.this, "SDK Not Supported", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        Toast.makeText(MainActivity.this, "Authentication Not Supported", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Toast.makeText(MainActivity.this, "Authentication Not Available", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(MainActivity.this, "Permission Not Granted", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(MainActivity.this, "Internal error", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAuthenticationCancelled() {
        Toast.makeText(MainActivity.this, "Authentciation Cancled", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAuthenticationSuccessful() {
        Toast.makeText(MainActivity.this, "Authentication Success", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        Toast.makeText(MainActivity.this, "Help "+helpString, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        Toast.makeText(MainActivity.this, "Error :  "+errString, Toast.LENGTH_SHORT).show();

    }
}