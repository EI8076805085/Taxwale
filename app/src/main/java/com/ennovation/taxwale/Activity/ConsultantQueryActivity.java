package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ennovation.taxwale.Adapter.ITRFileadapter;
import com.ennovation.taxwale.Adapter.QueryAdapter;
import com.ennovation.taxwale.Model.ITRFileResponse;
import com.ennovation.taxwale.Model.QueryListRisponse;
import com.ennovation.taxwale.R;
import com.ennovation.taxwale.Utils.Helper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ConsultantQueryActivity extends AppCompatActivity {

    TextView txt_minWords,txt_imageName,txt_recordTimer,txt_cancel,txt_recoredFile;
    RecyclerView queryRecyclerView;
    LinearLayout query_imgLayout,query_recordLayout,main_sendLayout,record_audioLayout;
    ImageView img_attachImage,img_attachRecording,img_deleteSelectedImage,img_viewImage,img_sendRecording,img_playRecording,img_pouseRecording,img_deleteRecording;
    String convertedimage = "";
    Bitmap selectedImage;
    String outputFile = null;
    Timer startTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_query);

        txt_minWords = findViewById(R.id.txt_minWords);
        queryRecyclerView = findViewById(R.id.queryRecyclerView);
        txt_imageName = findViewById(R.id.txt_imageName);
        query_imgLayout = findViewById(R.id.query_imgLayout);
        query_recordLayout = findViewById(R.id.query_recordLayout);
        img_attachImage = findViewById(R.id.img_attachImage);
        img_attachRecording = findViewById(R.id.img_attachRecording);
        img_deleteSelectedImage = findViewById(R.id.img_deleteSelectedImage);
        img_viewImage = findViewById(R.id.img_viewImage);
        txt_recordTimer = findViewById(R.id.txt_recordTimer);
        main_sendLayout = findViewById(R.id.main_sendLayout);
        record_audioLayout = findViewById(R.id.record_audioLayout);
        img_sendRecording = findViewById(R.id.img_sendRecording);
        img_playRecording = findViewById(R.id.img_playRecording);
        txt_cancel = findViewById(R.id.txt_cancel);
        img_pouseRecording = findViewById(R.id.img_pouseRecording);
        img_deleteRecording = findViewById(R.id.img_deleteRecording);

        img_viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowalertSelfiPhoto();
            }
        });

        img_attachRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_sendLayout.setVisibility(View.GONE);
                record_audioLayout.setVisibility(View.VISIBLE);
                selectAudio();
            }
        });

        img_sendRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.stop();
                recorder.release();
                txt_recordTimer.setText("");
                startTimer.cancel();

                if (main_sendLayout.getVisibility() == View.GONE)
                    main_sendLayout.setVisibility(View.VISIBLE);

                if (query_recordLayout.getVisibility() == View.GONE)
                    query_recordLayout.setVisibility(View.VISIBLE);

                if (record_audioLayout.getVisibility() == View.VISIBLE)
                    record_audioLayout.setVisibility(View.GONE);

            }
        });

        img_playRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startPlayingAudio(outputFile);
                    img_playRecording.setVisibility(View.GONE);
                    img_pouseRecording.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        img_pouseRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pouse();
                img_pouseRecording.setVisibility(View.GONE);
                img_playRecording.setVisibility(View.VISIBLE);
            }
        });

        img_deleteSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertedimage = "";
                query_imgLayout.setVisibility(View.GONE);
            }
        });

        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.stop();
                recorder.release();
                startTimer.cancel();

                txt_recordTimer.setText("");
                if (main_sendLayout.getVisibility() == View.GONE)
                    main_sendLayout.setVisibility(View.VISIBLE);

                if (record_audioLayout.getVisibility() == View.VISIBLE)
                    record_audioLayout.setVisibility(View.GONE);

            }
        });

        img_deleteRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputFile = null;
                query_recordLayout.setVisibility(View.GONE);
            }
        });

//        String a = "*Min 200 words";
//        String sourceString = "<font color='#F34008'>" + a +"</font>";
//        txt_minWords.setText(Html.fromHtml(sourceString));

        QueryListRisponse[] myListData = new QueryListRisponse[]{
                new QueryListRisponse("User file File-Name file"),
                new QueryListRisponse("User file File-Name file"),
                new QueryListRisponse("User file File-Name file"),
                new QueryListRisponse("User file File-Name file"),
                new QueryListRisponse("User file File-Name file"),
                new QueryListRisponse("User file File-Name file"),
        };

        QueryAdapter adapter = new QueryAdapter(myListData, this);
        queryRecyclerView.setHasFixedSize(true);
        queryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        queryRecyclerView.setAdapter(adapter);
        img_attachImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopUp();
            }
        });

    }

    private void selectAudio() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                if (checkPermission()) {
                    startTimer();
                    outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Audio.mp3";
                    MediaRecorderReady();
                    try {
                        recorder.prepare();
                        recorder.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    requestPermission();
                }
            } else
                requestPermission();
        } catch (Exception e) {
            requestPermission();
            e.printStackTrace();
        }

    }

    MediaRecorder recorder;

    public void MediaRecorderReady() {
        recorder = new MediaRecorder();
        recorder.reset();
        //recorder.release();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setAudioEncodingBitRate(128000);
        recorder.setAudioSamplingRate(44100);
        recorder.setOutputFile(outputFile);
    }

    MediaPlayer mMediaPlayer;

    private void startPlayingAudio(String outputFile) throws IOException {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDataSource(outputFile);
        mMediaPlayer.prepare();
        mMediaPlayer.start();

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                img_playRecording.setVisibility(View.VISIBLE);
                img_pouseRecording.setVisibility(View.GONE);
            }
        });

    }

    public boolean checkPermission() {
        int result3 = ContextCompat.checkSelfPermission(this,
                READ_EXTERNAL_STORAGE);
        int result = ContextCompat.checkSelfPermission(this,
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(this,
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED &&
                result3 == PackageManager.PERMISSION_GRANTED;
    }


    int sec, min, hr;

    private void startTimer() {
        int locSec = 0;
        int locMin = 0;
        int locHr = 0;
        final DecimalFormat format = new DecimalFormat("00");
        final String formatSecond = format.format(locSec);
        final String formatMinute = format.format(locMin);
        final String formatHour = format.format(locHr);

        sec = Integer.parseInt(formatSecond);
        min = Integer.parseInt(formatMinute);
        hr = Integer.parseInt(formatHour);
        startTimer = new Timer();
        startTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txt_recordTimer.setText(format.format(Double.valueOf(hr)) + ":"
                                + format.format(Double.valueOf(min)) + ":"
                                + format.format(Double.valueOf(sec)));
                        sec++;
                        if (sec > 59) {
                            sec = 0;
                            min = min + 1;
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    private void Pouse() {
        try {
            mMediaPlayer.pause();
            mMediaPlayer.release();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void ShowalertSelfiPhoto() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = getLayoutInflater();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View convertView = inflater.inflate(R.layout.images, null);
        ImageView image = convertView.findViewById(R.id.image);

        image.setImageBitmap(selectedImage);
//
//        Glide.with(this)
//                .load(convertedimage )
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .dontAnimate()
//                .into(image);

        alertDialog.setView(convertView);
        alertDialog.show();
    }

    private void openPopUp() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {

                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                LayoutInflater inflater = getLayoutInflater();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                View convertView = inflater.inflate(R.layout.open_camera, null);
                LinearLayout camera = convertView.findViewById(R.id.camera);
                LinearLayout gallery = convertView.findViewById(R.id.gallery);
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, 105);
                        }
                        alertDialog.dismiss();
                    }
                });

                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 0);
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setView(convertView);
                alertDialog.show();
            } else
                requestPermission();
        } catch (Exception e) {
            requestPermission();
            e.printStackTrace();
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO,CAMERA, READ_EXTERNAL_STORAGE}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(ConsultantQueryActivity.this, ConsultantQueryActivity.this.getString(R.string.Permission_Granted), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ConsultantQueryActivity.this, ConsultantQueryActivity.this.getString(R.string.Permission_Denied), Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //adhar upload
        if (data != null && requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Uri targetUri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory.decodeStream(getApplicationContext().getContentResolver().openInputStream(targetUri));
                    float aspectRatio = bitmap.getWidth() / (float) bitmap.getHeight();
                    int width = 280;
                    int height = Math.round(width / aspectRatio);

                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    convertedimage = ConvertBitmapToString(resizedBitmap);
                    selectedImage = resizedBitmap;
                    if (Helper.INSTANCE.isNetworkAvailable(this)) {
                        query_imgLayout.setVisibility(View.VISIBLE);
                        txt_imageName.setText(convertedimage);

                      //  updateUserProfile(convertedimage);

                    } else {
                        Helper.INSTANCE.Error(this, getString(R.string.NOCONN));
                    }
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        if (data != null && requestCode == 105) {
            if (resultCode == RESULT_OK) {
                // Uri targetUri = data.getData();
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                convertedimage = ConvertBitmapToString(bitmap);
                selectedImage = bitmap;
                if (Helper.INSTANCE.isNetworkAvailable(this)){
                   // updateUserProfile(convertedimage);
                    query_imgLayout.setVisibility(View.VISIBLE);
                    txt_imageName.setText(convertedimage);

                } else {
                    Helper.INSTANCE.Error(this, getString(R.string.NOCONN));
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

    public void back(View view) {
        onBackPressed();
    }

//    private void updateUserProfile(String convertedimage) {
//        YourPreference yourPrefrence = YourPreference.getInstance(getActivity());
//        String userId = yourPrefrence.getData("USERID");
//        HashMap<String, String> map = new HashMap<>();
//        mainProgressbar.setVisibility(View.VISIBLE);
//        map.put("user_id", userId);
//        map.put("profile_photo", convertedimage);
//        ServiceInterface serviceInterface = ApiClient.getClient().create(ServiceInterface.class);
//        Call<UpdateUserImageProfileResponse> call = serviceInterface.updateUserImageProfile(map);
//        call.enqueue(new Callback<UpdateUserImageProfileResponse>() {
//            @Override
//            public void onResponse(Call<UpdateUserImageProfileResponse> call, retrofit2.Response<UpdateUserImageProfileResponse> response) {
//                if (response.isSuccessful()) {
//                    mainProgressbar.setVisibility(View.GONE);
//                    String status = response.body().getStatus().toString();
//                    if (status.equals("1")) {
//                        Glide.with(getActivity()).load(IMAGE +response.body().getProfilePhoto()).into(iv_profile_photo);
//                        selfiphoto = response.body().getProfilePhoto();
//                    }
//                } else {
//                    mainProgressbar.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), "Something is wrong try again later", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UpdateUserImageProfileResponse> call, Throwable t) {
//                Log.d("ff", t.toString());
//                mainProgressbar.setVisibility(View.GONE);
//            }
//        });
//
//
//
//
//    }

}