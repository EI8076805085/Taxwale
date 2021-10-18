package com.ennovation.taxwale.Activity;

import static android.content.Intent.EXTRA_ALLOW_MULTIPLE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ennovation.taxwale.Adapter.GstDocumentAdapter;
import com.ennovation.taxwale.Adapter.MonthAdapter;
import com.ennovation.taxwale.Interfaces.MonthListner;
import com.ennovation.taxwale.Model.DocumentuploadModel;
import com.ennovation.taxwale.R;
import com.ennovation.taxwale.Utils.CameraUtils;
import com.ennovation.taxwale.Utils.Multipart;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import tech.mingxi.mediapicker.MXMediaPicker;
import tech.mingxi.mediapicker.models.PickerConfig;
import tech.mingxi.mediapicker.models.ResultItem;

public class UploadGST extends AppCompatActivity implements MonthListner {
    TextView txt_currentMonth, txt_previousMonth, txt_serviceName, txt_Month, txt_year, txt_addBill, txt_count, txt_Ok;
    String serviceName;
    ImageView  img_selectMonth, delete_row;
    List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    int numberOfMonth, rowCount = 0;
    RecyclerView rv_simple_list;
    LinearLayout layout_list;
    RecyclerView recycler_view;
    LinearLayout done;
    ImageView image;
    List<ResultItem> selectedItems;
    private String imageStoragePath;
    private String path;

    ArrayList<DocumentuploadModel> document = new ArrayList<DocumentuploadModel>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_g_s_t);
        txt_currentMonth = findViewById(R.id.txt_currentMonth);
        txt_previousMonth = findViewById(R.id.txt_previousMonth);
        txt_serviceName = findViewById(R.id.txt_serviceName);

        txt_Month = findViewById(R.id.txt_Month);
        txt_year = findViewById(R.id.txt_year);
        layout_list = findViewById(R.id.layout_list);
        txt_addBill = findViewById(R.id.txt_addBill);
        img_selectMonth = findViewById(R.id.img_selectMonth);
        recycler_view = findViewById(R.id.recycler_view);
        done = findViewById(R.id.done);
        image = findViewById(R.id.image);

        notificationPop();


        serviceName = getIntent().getStringExtra("name");

        txt_serviceName.setText(serviceName);

        txt_currentMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_currentMonth.setBackground(UploadGST.this.getResources().getDrawable(R.drawable.btn_orenge_corner));
                txt_previousMonth.setBackground(UploadGST.this.getResources().getDrawable(R.drawable.btn_grey_corner));
                img_selectMonth.setVisibility(View.GONE);
                DateFormat dateFormat = new SimpleDateFormat("MMMM");
                Date date = new Date();
                txt_Month.setText(dateFormat.format(date));

            }
        });

        txt_currentMonth.setBackground(UploadGST.this.getResources().getDrawable(R.drawable.btn_grey_corner));
        txt_previousMonth.setBackground(UploadGST.this.getResources().getDrawable(R.drawable.btn_orenge_corner));
        img_selectMonth.setVisibility(View.VISIBLE);


        DateFormat dateFormat = new SimpleDateFormat("MMMM");
        Date date = new Date();
        txt_Month.setText(dateFormat.format(date));

        DateFormat dateFormatYear = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dateFormatYear = new SimpleDateFormat("YYYY");
        }
        Date date1 = new Date();
        txt_year.setText(dateFormatYear.format(date1));

        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
        Date allMonths = new Date();

        numberOfMonth = Integer.parseInt(formatMonth.format(allMonths));
        String num = numberOfMonth + "";
        String firstLetter = String.valueOf(num.charAt(0));
        if (firstLetter == "0" && num.length() == 2) {
            numberOfMonth = Integer.parseInt(String.valueOf(num.charAt(0)));
        }
        List<String> monthsList = new ArrayList<String>();
        for (int i = 0; i < numberOfMonth; i++) {
            monthsList.add(months.get(i));
        }

        img_selectMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMonthsDialog(monthsList);
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (document.size() < 1) {
                    Toast.makeText(UploadGST.this, "Upload atleast 1 document", Toast.LENGTH_SHORT).show();
                } else {
                    if (selectedItems != null) {
                        for (int i = 0; i < document.size(); i++) {
                            new Multipart().uploadMedia(UploadGST.this, document.get(i).getPath(), i, selectedItems.size(),txt_Month.getText().toString(),txt_year.getText().toString());
                        }
                    }
                }

            }
        });

        txt_addBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.cameragalary_layout, null);
                LinearLayout camera = customView.findViewById(R.id.camera);
                LinearLayout gallery = customView.findViewById(R.id.gallery);
                LinearLayout document = customView.findViewById(R.id.document);

                BottomDialog bottomDialog = new BottomDialog.Builder(UploadGST.this)
                        .setCustomView(customView)
                        .setTitle("Upload your Bill")
                        // You can also show the custom view with some padding in DP (left, top, right, bottom)
                        //  .setCustomView(customView, 0, 0, 0, 40)
                        .show();

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomDialog.dismiss();

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
                        if (file != null) {
                            imageStoragePath = file.getAbsolutePath();
                        }
                        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 31457280L);// 30*1024*1024 = 30MB
                        startActivityForResult(intent, 2);
                    }
                });


                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomDialog.dismiss();
                        MXMediaPicker.init(getApplicationContext());
                        MXMediaPicker picker = MXMediaPicker.getInstance();
                        PickerConfig pickerConfig = new PickerConfig();
                        pickerConfig.setFileType(MXMediaPicker.FILE_TYPE_VIDEO_AND_IMAGE);
                        pickerConfig.setAllowCamera(false);
                        pickerConfig.setFolderMode(MXMediaPicker.FOLDER_MODE_ONLY_PARENT);
                        pickerConfig.setMultiSelect(true);
                        pickerConfig.setMultiSelectMaxCount(10);
                        picker.setPickerConfig(pickerConfig);
                        picker.chooseImage(UploadGST.this, 1);


                    }
                });

                document.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        bottomDialog.dismiss();

//                        Intent intent = new Intent();
//                        intent.setType("*/*");
//                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
//                        startActivityForResult(Intent.createChooser(intent, "Select File"), 3);


                        Intent filesIntent;
                        filesIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        filesIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        filesIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        filesIntent.setType("*/*");  //use image/* for photos, etc.
                        startActivityForResult(filesIntent, 3);
                    }
                });
            }
        });

    }

    private void notificationPop() {
        final Dialog dialog = new Dialog(UploadGST.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.simple_notification_popup);
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

//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if (resultCode == Activity.RESULT_OK) {
//                if (data.getClipData() != null) {
//                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
//
//
//                    for (int i = 0; i < count; i++) {
//                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                        String filepath = getRealPathFromURI(imageUri);
//                        pathlist.add(filepath);
//
//                    }
//
//                    done.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            for (int i = 0; i < count; i++) {
//
//                                new Multipart().uploadMedia(UploadGST.this, pathlist.get(i), i, count);
//
//                            }
//
//                        }
//                    });
//                    GstDocumentAdapter adapter = new GstDocumentAdapter(pathlist);
//                    recycler_view.setHasFixedSize(true);
//                    recycler_view.setLayoutManager(new LinearLayoutManager(UploadGST.this));
//                    recycler_view.setAdapter(adapter);
//
//
//                    //do something with the image (save it to some directory or whatever you need to do with it here)
//                }
//            } else if (data.getData() != null) {
//                String imagePath = data.getData().getPath();
//                //do something with the image (save it to some directory or whatever you need to do with it here)
//            }
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            selectedItems = MXMediaPicker.getInstance().getSelectedItems(resultCode, data);
            for (int i = 0; i < selectedItems.size(); i++) {
                DocumentuploadModel documentuploadModel = new DocumentuploadModel();
                documentuploadModel.setPath(selectedItems.get(i).getPath());
                documentuploadModel.setType("Gallery");
                document.add(documentuploadModel);

            }
            if (selectedItems != null) {
                int index = 0;
                GstDocumentAdapter adapter = new GstDocumentAdapter(document);
                recycler_view.setHasFixedSize(true);
                recycler_view.setLayoutManager(new LinearLayoutManager(UploadGST.this));
                recycler_view.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                for (ResultItem item : selectedItems) {
                    String uri = item.getUri();
                    String path = item.getPath();

                    index++;

                    Log.i("URI", "" + uri);
                    Log.i("path", path);
                    //Please read data from uri. Path is only used to get file name and extension.
                }
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);
                Bitmap bitmap = BitmapFactory.decodeFile(imageStoragePath);
                Uri tempUri = getImageUri(UploadGST.this, bitmap);
                File finalFile = new File(getRealPathFromURI(UploadGST.this, tempUri));
                long sizeInBytes = finalFile.length();
                long sizeInMb = sizeInBytes / (1024 * 1024);
                if (sizeInMb > 30) {
                    Toast.makeText(this, "File size is too large", Toast.LENGTH_SHORT).show();
                } else {
                    path = finalFile.getPath();
                    DocumentuploadModel documentuploadModel = new DocumentuploadModel();
                    documentuploadModel.setPath(path);
                    documentuploadModel.setType("Camera");
                    document.add(documentuploadModel);

                    GstDocumentAdapter adapter = new GstDocumentAdapter(document);
                    recycler_view.setHasFixedSize(true);
                    recycler_view.setLayoutManager(new LinearLayoutManager(UploadGST.this));
                    recycler_view.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    try {
                        String[] str = path.split("/");
                        int b = str.length;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (data.getClipData() != null) {

                for (int index = 0; index < data.getClipData().getItemCount(); index++) {
                    Uri uri = data.getClipData().getItemAt(index).getUri();
                    File file = new File(uri.getPath());
                    final String[] split = file.getPath().split(":");//split the path.
                    String filePath = split[1];
                    Log.i("filePath", "" + filePath);

                    DocumentuploadModel documentuploadModel = new DocumentuploadModel();
                    documentuploadModel.setPath(filePath);
                    documentuploadModel.setType("document");
                    document.add(documentuploadModel);
                }
            } else {
                Uri uri = data.getData();
                File file = new File(uri.getPath());
                final String[] split = file.getPath().split(":");//split the path.
                String filePath = split[1];
                Log.i("filePath", "" + filePath);

                DocumentuploadModel documentuploadModel = new DocumentuploadModel();
                documentuploadModel.setPath(filePath);
                documentuploadModel.setType("document");
                document.add(documentuploadModel);

            }

            GstDocumentAdapter adapter = new GstDocumentAdapter(document);
            recycler_view.setHasFixedSize(true);
            recycler_view.setLayoutManager(new LinearLayoutManager(UploadGST.this));
            recycler_view.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getImagePath(Uri uri) {
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getApplicationContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e("TAG", "getRealPathFromURI Exception : " + e.toString());
            String filename = contentUri.getPath();
            return filename;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void addView() {
        final View gstlistView = getLayoutInflater().inflate(R.layout.row_add_gstbill, null, false);
        delete_row = gstlistView.findViewById(R.id.img_delete);
        txt_count = gstlistView.findViewById(R.id.txt_count);
        rowCount = rowCount + 1;
        txt_count.setText(rowCount + "");

        delete_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(gstlistView);
            }
        });


        layout_list.addView(gstlistView);
    }

    private void removeView(View view) {
        layout_list.removeView(view);
        rowCount = rowCount - 1;
        txt_count.setText(rowCount + "");
    }

    private void openMonthsDialog(List<String> monthsList) {
        final Dialog dialog = new Dialog(UploadGST.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.simple_list_data);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        rv_simple_list = dialog.findViewById(R.id.rv_simple_list);

        MonthAdapter adapter = new MonthAdapter(monthsList, UploadGST.this, this, dialog, numberOfMonth);
        rv_simple_list.setHasFixedSize(true);
        rv_simple_list.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        rv_simple_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }

    @Override
    public void monthClickListner(String name) {
        txt_Month.setText(name);
    }



    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void back(View view) {
        onBackPressed();
    }
}
