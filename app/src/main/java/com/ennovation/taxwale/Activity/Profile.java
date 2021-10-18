package com.ennovation.taxwale.Activity;

import static com.ennovation.taxwale.Utils.Constants.BASE_URL;
import static com.ennovation.taxwale.Utils.Constants.IMAGE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ennovation.taxwale.R;
import com.ennovation.taxwale.Utils.YourPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    TextView txt_name, txt_number, txt_accountManager;
    ProgressBar mainProgressBar;
    ImageView img_profilephoto;
    String email, pan, aadhar, gst, location, account_manager, convertedimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mainProgressBar = findViewById(R.id.mainProgressBar);
        txt_name = findViewById(R.id.txt_name);
        txt_number = findViewById(R.id.txt_number);
        txt_accountManager = findViewById(R.id.txt_accountManager);
        img_profilephoto = findViewById(R.id.profilephoto);
        img_profilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageDialog(0, 95);
            }
        });

    }


    public void addaddress(View view) {

        Intent intent = new Intent(Profile.this, Address.class);
        startActivity(intent);
    }

    public void document(View view) {
        Intent intent = new Intent(Profile.this, Documentverify.class);
        startActivity(intent);
    }

    public void changepass(View view) {
        Intent intent = new Intent(Profile.this, ChangePassword.class);
        startActivity(intent);
    }

    public void editprofile(View view) {
        Intent intent = new Intent(Profile.this, Editprofile.class);
        intent.putExtra("name", txt_name.getText().toString());
        intent.putExtra("mobile", txt_number.getText().toString());
        intent.putExtra("email", email);
        intent.putExtra("pan", pan);
        intent.putExtra("aadhar", aadhar);
        intent.putExtra("gst", gst);
        intent.putExtra("location", location);

        startActivity(intent);
    }

    public void langugae(View view) {
        Intent intent = new Intent(Profile.this, Language.class);
        startActivity(intent);
    }

    public void Accountmanager(View view) {
        Intent intent = new Intent(Profile.this, AccountManager.class);
        startActivity(intent);
    }

    public void signout(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(Profile.this);
        alert.setTitle(R.string.app_name)
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Are you sure want to logout.")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        SharedPreferences preferences = getSharedPreferences("YourCustomNamedPreference", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        finishAffinity();

                    }
                }).setNegativeButton("No", null);

        alert.setCancelable(false);
        alert.show();


    }

    @Override
    public void onResume() {
        super.onResume();
        getUserProfile();

    }

    public void getUserProfile() {
        String url = BASE_URL + "user_profile";
        RequestQueue requestQueue = Volley.newRequestQueue(Profile.this);
        YourPreference yourPrefrence = YourPreference.getInstance(Profile.this);
        String id = yourPrefrence.getData("User_id");
        Map<String, String> params = new HashMap();
        params.put("user_id", id);
        mainProgressBar.setVisibility(View.VISIBLE);

        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mainProgressBar.setVisibility(View.GONE);
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String r_code = obj.getString("status");
                    if (r_code.equalsIgnoreCase("1")) {
                        JSONArray jsonArray = obj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String name = jsonObject2.getString("name");
                            String mobile = jsonObject2.getString("mobile");
                            String profilephoto = jsonObject2.getString("profile_photo");
                            String account_manager = jsonObject2.getString("account_manager");
                            email = jsonObject2.getString("email");
                            pan = jsonObject2.getString("pan_number");
                            aadhar = jsonObject2.getString("aadhar_number");
                            gst = jsonObject2.getString("gst_number");
                            location = jsonObject2.getString("location");

                            txt_name.setText(name);
                            txt_number.setText(mobile);
                            txt_accountManager.setText(account_manager);

                            Glide.with(Profile.this).load(IMAGE_URL + profilephoto)
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.avatar)
                                            .centerCrop()
                                            .error(R.drawable.avatar))
                                    .into(img_profilephoto);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this, ""+error, Toast.LENGTH_SHORT).show();
            }

        }) {

        };
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);
    }

    private void openImageDialog(final int upload, final int take) {

        final AlertDialog alertDialog = new AlertDialog.Builder(Profile.this).create();
        LayoutInflater inflater = getLayoutInflater();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View convertView = inflater.inflate(R.layout.camera, null);
        LinearLayout camera = convertView.findViewById(R.id.camera);
        LinearLayout gallery = convertView.findViewById(R.id.gallery);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, take);
                }
                alertDialog.dismiss();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, upload);
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(convertView);
        alertDialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Uri targetUri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    float aspectRatio = bitmap.getWidth() / (float) bitmap.getHeight();
                    int width = 280;
                    int height = Math.round(width / aspectRatio);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    convertedimage = ConvertBitmapToString(resizedBitmap);

                    updateprofilephoto();


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        if (data != null && requestCode == 95) {
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    float aspectRatio = bitmap.getWidth() /
                            (float) bitmap.getHeight();
                    int width = 280;
                    int height = Math.round(width / aspectRatio);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    convertedimage = ConvertBitmapToString(resizedBitmap);
                    updateprofilephoto();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Profile.this, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String ConvertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        return base64String;
    }

    public void updateprofilephoto() {
        String url = BASE_URL + "user_photo_update";
        YourPreference yourPrefrence = YourPreference.getInstance(Profile.this);
        String id = yourPrefrence.getData("User_id");
        RequestQueue requestQueue = Volley.newRequestQueue(Profile.this);
        Map<String, String> params = new HashMap();
        params.put("user_id", id);
        params.put("profile_photo", convertedimage);

        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String r_code = obj.getString("status");
                    Toast.makeText(Profile.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();
                    if (r_code.equalsIgnoreCase("1")) {
                        YourPreference yourPrefrence = YourPreference.getInstance(Profile.this);
                        yourPrefrence.saveData("profile", obj.getString("profile_photo"));
                        Glide.with(Profile.this).load(IMAGE_URL + obj.getString("profile_photo"))
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.avatar)
                                        .centerCrop()
                                        .error(R.drawable.avatar))
                                .into(img_profilephoto);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);
    }

    public void back(View view) {
        onBackPressed();
    }
}