//package com.ennovation.taxwale.Utils;
//
//
//import android.Manifest;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//
//import androidx.core.app.ActivityCompat;
//import androidx.fragment.app.Fragment;
//import androidx.appcompat.app.AlertDialog;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.basgeekball.awesomevalidation.AwesomeValidation;
//import com.basgeekball.awesomevalidation.ValidationStyle;
//import com.google.gson.Gson;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.InputStream;
//import java.util.ArrayList;
//
//import rememberme.com.app.rememberme.R;
//import rememberme.com.app.rememberme.Utils.CameraUtils;
//import rememberme.com.app.rememberme.Utils.UtilMethods;
//
//import static android.Manifest.permission.CAMERA;
//import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
//import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
//import static android.app.Activity.RESULT_OK;
//import static android.content.ContentValues.TAG;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class MemoryFr extends Fragment implements View.OnClickListener {
//
//    View view;
//    Button iv_AddImage, iv_AddVideo;
//    Button Send;
//    ListView rv_recipientlist;
//    ArrayList<String> imagename = new ArrayList<>();
//    ArrayList<String> reciepent_emailList = new ArrayList<>();
//    ArrayList<String> reciepent_delete = new ArrayList<>();
//    ArrayAdapter<String> itemsAdapter;
//    AwesomeValidation mAwesomeValidation;
//    private static final int PICK_IMAGE_REQUEST = 100;
//    private static final int PICK_VIDEO_REQUEST = 101;
//    SharedPreferences sp;
//    SharedPreferences.Editor editor;
//    ImageView back_iv;
//    String fragment;
//    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
//    private final int PICK_VIDEO_CAMERA = 3, PICK_VIDEO_GALLERY = 4;
//    LinearLayout ll_bottomHeader;
//    String imageStoragePath;
//    public static final int BITMAP_SAMPLE_SIZE = 4;
//    public static final int MEDIA_TYPE_IMAGE = 1;
//
//    public MemoryFr() {
//        // Required empty public constructor
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        sp = getActivity().getSharedPreferences("MyPref", 0);
//        editor=sp.edit();
//        editor.putString("pause", "true").apply();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_memory, container, false);
//        UtilMethods.INSTANCE.setDashboardPref(getActivity(), false);
//        sp = getActivity().getSharedPreferences("MyPref", 0);
//        editor=sp.edit();
//        fragment = sp.getString("fragment", "");
//        mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
//        iv_AddImage = view.findViewById(R.id.iv_AddImage);
//        iv_AddVideo = view.findViewById(R.id.iv_AddVideo);
//        Send = view.findViewById(R.id.Send);
//        back_iv = view.findViewById(R.id.back_iv);
//        rv_recipientlist = view.findViewById(R.id.rv_recipientlist);
//        try {
//            ll_bottomHeader = getActivity().findViewById(R.id.bottomHeader);
//            ll_bottomHeader.setVisibility(View.GONE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        iv_AddImage.setOnClickListener(this);
//        iv_AddVideo.setOnClickListener(this);
//        Send.setOnClickListener(this);
//        back_iv.setOnClickListener(this);
//        itemsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_recipent_view, R.id.label, imagename);
//        rv_recipientlist.setAdapter(itemsAdapter);
//        rv_recipientlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                View view1 = itemsAdapter.getView(position, view, null);
//                ImageView icon = view1.findViewById(R.id.icon);
//                icon.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new AlertDialog.Builder(getActivity()).setTitle(getContext().getString(R.string.do_you_want_to_del))
//                                .setPositiveButton(getContext().getString(R.string.yes), new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        imagename.remove(position);
//                                        reciepent_emailList.remove(position);
//                                        itemsAdapter.notifyDataSetChanged();
//                                        dialog.dismiss();
//                                    }
//                                }).setNegativeButton(getContext().getString(R.string.no), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).show();
//                    }
//                });
//            }
//        });
//        return view;
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v == iv_AddImage) {
//            editor.putString("pause", "false").apply();
//            selectImage();
//        } else if (v == iv_AddVideo) {
//            editor.putString("pause", "false").apply();
//            selectVideo();
//        } else if (v == Send) {
//            if (reciepent_emailList.size() > 0) {
//                Bundle bundle = new Bundle();
//                // bundle.putString("titleDataMemory", AddTitleDesData);
//                bundle.putString("imageDataMemory", new Gson().toJson(reciepent_emailList));
//                bundle.putString("Imagenamememory", new Gson().toJson(imagename));
//                //bundle.putStringArrayList("ImageDataMemory",reciepent_emailList);
//                AddLastStageFr addLastStageFr = new AddLastStageFr();
//                addLastStageFr.setArguments(bundle);
//                UtilMethods.INSTANCE.ChangeFragmemt(addLastStageFr, getActivity().getSupportFragmentManager(), false);
//            } else {
//                UtilMethods.INSTANCE.Error(getContext(), getContext().getString(R.string.insert_image_video));
//            }
//
//        } else if (v == back_iv) {
//            getActivity().onBackPressed();
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        String path;
//        switch (requestCode) {
//            case PICK_IMAGE_GALLERY:
//                if (resultCode == RESULT_OK) {
//                    Uri selectedImage = data.getData();
//                    File file = new File(selectedImage.getLastPathSegment());
//
//
//                    long sizeInBytes = file.length();
//                    long sizeInMb = sizeInBytes / (1024 * 1024);
//
//
//                    if (sizeInMb > 30) {
//                        Toast.makeText(getContext(), getContext().getString(R.string.file_size_alert), Toast.LENGTH_SHORT).show();
//                    } else {
//                        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//                        if (isKitKat) {
//                            // path = getImagePath(selectedImage);
//
//                            try {
//                                path = getImagePath(selectedImage);
//                            } catch (Exception e) {
//
//                                path = selectedImage.getPath();
//                            }
//                        } else {
//                            path = getRealPathFromURI(getActivity(), selectedImage);
//
//                        }
//
//                        try {
//                            String[] str = path.split("/");
//                            int b = str.length;
//                            imagename.add(str[b - 1]);
//                            reciepent_emailList.add(path);
//                            itemsAdapter.notifyDataSetChanged();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//
//                }
//                break;
//            case PICK_IMAGE_CAMERA:
//                if (resultCode == RESULT_OK) {
//                    CameraUtils.refreshGallery(getContext().getApplicationContext(), imageStoragePath);
//                    //Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
//                    Bitmap bitmap = BitmapFactory.decodeFile(imageStoragePath);
//                    Uri tempUri = getImageUri(getActivity(), bitmap);
//                    File finalFile = new File(getRealPathFromURI(getContext(), tempUri));
//                    long sizeInBytes = finalFile.length();
//                    long sizeInMb = sizeInBytes / (1024 * 1024);
//
//                    if (sizeInMb > 30) {
//                        Toast.makeText(getContext(), getContext().getString(R.string.file_size_alert), Toast.LENGTH_SHORT).show();
//                    } else {
//                        path = finalFile.getPath();
//
//                        try {
//                            String[] str = path.split("/");
//                            int b = str.length;
//                            imagename.add(str[b - 1]);
//                            reciepent_emailList.add(path);
//                            itemsAdapter.notifyDataSetChanged();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                break;
//
//            case PICK_VIDEO_REQUEST:
//                if (resultCode == RESULT_OK) {
//                    Uri selectedVideoUri = data.getData();
//                    File file = new File(selectedVideoUri.getLastPathSegment());
//                    long sizeInBytes = file.length();
//                    long sizeInMb = sizeInBytes / (1024 * 1024);
//                    if (sizeInMb > 30) {
//                        Toast.makeText(getContext(), getContext().getString(R.string.file_size_alert) + sizeInMb + "", Toast.LENGTH_SHORT).show();
//                    } else {
//                        path = getRealPathFromURI(getActivity(), selectedVideoUri);
//                        try {
//                            String[] str = path.split("/");
//                            int b = str.length;
//                            imagename.add(str[b - 1]);
//                            reciepent_emailList.add(path);
//                            itemsAdapter.notifyDataSetChanged();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//
//                break;
//            case PICK_VIDEO_CAMERA:
//                if (resultCode == RESULT_OK) {
//                    Uri selectedVideoUri = data.getData();
//                    File file = new File(selectedVideoUri.getLastPathSegment());
//
//
//                    long sizeInBytes = file.length();
//                    long sizeInMb = sizeInBytes / (1024 * 1024);
//
//                    if (sizeInMb > 30) {
//                        Toast.makeText(getContext(), getContext().getString(R.string.file_size_alert), Toast.LENGTH_SHORT).show();
//                    } else {
//                        path = getRealPathFromURI(getActivity(), selectedVideoUri);
//                        try {
//                            String[] str = path.split("/");
//                            int b = str.length;
//                            imagename.add(str[b - 1]);
//                            reciepent_emailList.add(path);
//                            itemsAdapter.notifyDataSetChanged();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//
//                }
//                break;
//
//        }
//
//
//    }
//
//    private String getRealPathFromURI(Context context, Uri contentUri) {
//        Cursor cursor = null;
//        try {
//            String[] proj = {MediaStore.Images.Media.DATA};
//            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } catch (Exception e) {
//            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
//            String filename = contentUri.getPath();
//            return filename;
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }
//
//    public String getImagePath(Uri uri) {
//        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        String document_id = cursor.getString(0);
//        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
//        cursor.close();
//
//        cursor = getActivity().getContentResolver().query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
//        cursor.moveToFirst();
//        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//        cursor.close();
//
//        return path;
//    }
//
//    private void selectImage() {
//        try {
//            PackageManager pm = getActivity().getPackageManager();
//            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getActivity().getPackageName());
//            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
//                final CharSequence[] options = {getString(R.string.take_photo), getContext().getString(R.string.choose_from_gallery)};
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                //builder.setTitle("Select Option");
//                builder.setTitle(getContext().getResources().getString(R.string.select_option));
//                builder.setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int item) {
//                        if (options[item].equals(getContext().getResources().getString(R.string.take_photo))) {
//                            dialog.dismiss();
//                            /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            startActivityForResult(intent, PICK_IMAGE_CAMERA);*/
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
//                            if (file != null) {
//                                imageStoragePath = file.getAbsolutePath();
//                            }
//                            Uri fileUri = CameraUtils.getOutputMediaFileUri(getContext().getApplicationContext(), file);
//                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                            intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 31457280L);// 30*1024*1024 = 30MB
//
//                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
//                        } else if (options[item].equals(getContext().getResources().getString(R.string.choose_from_gallery))) {
//                            dialog.dismiss();
//                            Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
//                            pickPhoto.setType("image/*");
//                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
//                        }
//                    }
//                });
//                builder.show();
//            } else
//                requestPermission();
//        } catch (Exception e) {
//            requestPermission();
//            e.printStackTrace();
//        }
//    }
//
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
//
//    private void requestPermission() {
//        ActivityCompat.requestPermissions(getActivity(), new
//                String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE}, 101);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case 101:
//                if (grantResults.length > 0) {
//                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//                    if (StoragePermission && RecordPermission) {
//                        Toast.makeText(getContext(), getContext().getString(R.string.Permission_Granted), Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(getContext(), getContext().getString(R.string.Permission_Denied), Toast.LENGTH_LONG).show();
//                    }
//                }
//             break;
//        }
//    }
//
//    private void selectVideo() {
//        try {
//            PackageManager pm = getActivity().getPackageManager();
//            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getActivity().getPackageName());
//            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
//                final CharSequence[] options = {getContext().getResources().getString(R.string.take_video), getContext().getResources().getString(R.string.choose_from_gallery)};
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle(getContext().getResources().getString(R.string.select_option));
//                builder.setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int item) {
//                        if (options[item].equals(getContext().getResources().getString(R.string.take_video))) {
//                            dialog.dismiss();
//                            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//                            intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 31457280L);// 30*1024*1024 = 30MB
//                            startActivityForResult(intent, PICK_VIDEO_CAMERA);
//                        } else if (options[item].equals(getContext().getResources().getString(R.string.choose_from_gallery))) {
//                            dialog.dismiss();
//                            Intent intentVideo = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//                            intentVideo.setType("video/*");
//                            startActivityForResult(Intent.createChooser(intentVideo, getContext().getResources().getString(R.string.Select_Video)), PICK_VIDEO_REQUEST);
//                        }
//                    }
//                });
//                builder.show();
//            } else
//                requestPermission();
//        } catch (Exception e) {
//            requestPermission();
//            e.printStackTrace();
//        }
//    }
//}
