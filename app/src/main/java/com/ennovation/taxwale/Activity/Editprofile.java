package com.ennovation.taxwale.Activity;

import static com.ennovation.taxwale.Utils.Constants.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ennovation.taxwale.MainActivity;
import com.ennovation.taxwale.R;
import com.ennovation.taxwale.Utils.GPSTracker;
import com.ennovation.taxwale.Utils.GpsUtils;
import com.ennovation.taxwale.Utils.YourPreference;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Editprofile extends AppCompatActivity {


    ProgressBar progresbar;
    EditText ed_name, ed_email, ed_mobile, ed_panCard, ed_aadharCart, ed_gstNumber, ed_location;
    TextView txt_Ok, txt_upload;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);


        ed_name = findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_email);
        ed_mobile = findViewById(R.id.ed_mobile);
        ed_panCard = findViewById(R.id.ed_panCard);
        ed_aadharCart = findViewById(R.id.ed_aadharCart);
        ed_gstNumber = findViewById(R.id.ed_gstNumber);
        ed_location = findViewById(R.id.ed_location);
        progresbar = findViewById(R.id.progresbar);
        txt_upload = findViewById(R.id.txt_upload);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String mobile = intent.getStringExtra("mobile");
        String email = intent.getStringExtra("email");
        String pan = intent.getStringExtra("pan");
        String aadhar = intent.getStringExtra("aadhar");
        String gst = intent.getStringExtra("gst");
        String location = intent.getStringExtra("location");

        ed_name.setText(name);
        ed_mobile.setText(mobile);
        ed_email.setText(email);
        ed_panCard.setText(pan);
        ed_aadharCart.setText(aadhar);
        ed_gstNumber.setText(gst);
        ed_location.setText(location);


        if (name.equalsIgnoreCase("") || name.equalsIgnoreCase("null")) {
        } else {
            ed_name.setText(name);
        }

        if (mobile.equalsIgnoreCase("") || mobile.equalsIgnoreCase("null")) {
        } else {
            ed_mobile.setText(mobile);
        }

        if (email.equalsIgnoreCase("") || email.equalsIgnoreCase("null")) {
        } else {
            ed_email.setText(email);
        }

        if (pan.equalsIgnoreCase("") || pan.equalsIgnoreCase("null")) {
        } else {
            ed_panCard.setText(pan);
        }

        if (aadhar.equalsIgnoreCase("") || aadhar.equalsIgnoreCase("null")) {
        } else {
            ed_aadharCart.setText(aadhar);
        }

        if (gst.equalsIgnoreCase("") || gst.equalsIgnoreCase("null")) {
        } else {
            ed_gstNumber.setText(gst);
        }

        if (location.equalsIgnoreCase("") || location.equalsIgnoreCase("null")) {
        } else {
            ed_location.setText(location);
        }

        txt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_name.getText().toString();
                String mobile = ed_mobile.getText().toString();
                String email = ed_email.getText().toString();
                String pan = ed_panCard.getText().toString();
                String aadhar = ed_aadharCart.getText().toString();
                String gst = ed_gstNumber.getText().toString();
                String location = ed_location.getText().toString();

                if (name.equalsIgnoreCase("") || name.equalsIgnoreCase("null")) {
                    ed_name.setError("*required");
                } else if (email.equalsIgnoreCase("") || email.equalsIgnoreCase("null")) {
                    ed_email.setError("*required");
                } else if (!email.matches(emailPattern)) {
                    ed_email.setError("Please Enter Valid Email");
                } else if (pan.equalsIgnoreCase("") || pan.equalsIgnoreCase("null")) {
                    ed_panCard.setError("*required");
                } else if (aadhar.equalsIgnoreCase("") || aadhar.equalsIgnoreCase("null")) {
                    ed_aadharCart.setError("*required");
                } else if (gst.equalsIgnoreCase("") || gst.equalsIgnoreCase("null")) {
                    ed_gstNumber.setError("*required");
                } else if (location.equalsIgnoreCase("") || location.equalsIgnoreCase("null")) {
                    ed_location.setError("*required");
                } else {
                    updateProfileDetails(name, mobile, email, pan,aadhar,gst,location);
                }
            }
        });


    }

    private void updateProfileDetails(String name, String mobile, String email, String pan, String aadhar, String gst, String location) {
        String url = BASE_URL + "update_profile";
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        String id = yourPrefrence.getData("User_id");
        RequestQueue requestQueue = Volley.newRequestQueue(Editprofile.this);
        Map<String, String> params = new HashMap();
        params.put("user_id", id);
        params.put("name", name);
        params.put("email", email);
        params.put("pan_number", pan);
        params.put("aadhar_number", aadhar);
        params.put("gst_number", gst);
        params.put("location", location);
        progresbar.setVisibility(View.VISIBLE);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progresbar.setVisibility(View.GONE);
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String r_code = obj.getString("status");
                    if (r_code.equalsIgnoreCase("1")) {
                        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
                        yourPrefrence.saveData("name", name);
                        yourPrefrence.saveData("email", email);
                        yourPrefrence.saveData("mobile", mobile);

                        Intent intent = new Intent(Editprofile.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progresbar.setVisibility(View.GONE);
            }
        }) {

        };
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

    }

    private void turngpspon() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
            redirectionScreen();
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 3000);
        } else {
            if (!hasGPSDevice(this)) {
                finish();
            }
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
                new GpsUtils(this).turnGPSOn(new GpsUtils.OnGpsListener() {
                    @Override
                    public void gpsStatus(boolean isGPSEnable) {
                        if (isGPSEnable) {
                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    redirectionScreen();
                                }
                            }, 3000);

                        } else {

                        }
                    }
                });
            }
        }
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void redirectionScreen() {
        GPSTracker mGPS = new GPSTracker(getApplicationContext());
        Double lat, lng;
        if (mGPS.canGetLocation) {
            mGPS.getLocation();
            lat = mGPS.getLatitude();
            lng = mGPS.getLongitude();
            getCompleteAddressString(lat, lng);
        } else {
            Toast.makeText(mGPS, "location not Available", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            String addresstr = addresses.get(0).getAddressLine(0);
            progresbar.setVisibility(View.GONE);
            ed_location.setText(addresstr);
            progresbar.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to fetch location", Toast.LENGTH_SHORT).show();
        }
    }

    public void Getlocation(View view) {
        progresbar.setVisibility(View.VISIBLE);
        turngpspon();

    }

    private void verifygst() {
        final Dialog dialog = new Dialog(Editprofile.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.taxverify_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        txt_Ok = dialog.findViewById(R.id.txt_Ok);
        txt_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public void verify(View view) {
        verifygst();
    }

    public void back(View view) {
        onBackPressed();
    }
}