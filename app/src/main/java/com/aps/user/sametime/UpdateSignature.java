package com.aps.user.sametime;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UpdateSignature extends AppCompatActivity {

    String id;
    int total;
    ImageView image;
    TextView add;
    Button update;
    ProgressBar progress;
    private Toolbar toolbar;
    Bitmap bmp;
    private final int PICK_IMAGE_REQUEST = 2;
    String mCurrentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_signature);


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitle("Update Signature");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progress = (ProgressBar)findViewById(R.id.progress);
        image = (ImageView)findViewById(R.id.image);
        add = (TextView)findViewById(R.id.add_image);
        update = (Button)findViewById(R.id.update);

        id = getIntent().getStringExtra("id");
        total = getIntent().getIntExtra("total",0);
Log.d("aaaaa",""+id);
        User b = (User) getApplicationContext();


        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent();

                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateImage();

            }
        });

    }


    private void updateImage()
    {
        if (mCurrentPhotoPath.length()>0)
        {

            progress.setVisibility(View.VISIBLE);

            File file = new File(mCurrentPhotoPath);



            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body = MultipartBody.Part.createFormData("signature", file.getName(), reqFile);

Log.d("bodyyyy", ""+body);
            Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final FranchiseAPI request = retro.create(FranchiseAPI.class);

            User b = (User)getApplicationContext();

            Call<UpdateSigBean> call = request.updateSig(b.id , body , id);

            call.enqueue(new Callback<UpdateSigBean>() {
                @Override
                public void onResponse(Call<UpdateSigBean> call, Response<UpdateSigBean> response) {

                    Log.d("aaaaid", id);
                    progress.setVisibility(View.GONE);
                    finish();

                }

                @Override
                public void onFailure(Call<UpdateSigBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext() , "Please select a file" , Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(String.valueOf(data.getData())));

                image.setImageBitmap(bmp);

                Uri selectedImageUri = data.getData();

                mCurrentPhotoPath = getPath(getApplicationContext() , selectedImageUri);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    private static String getPath(final Context context, final Uri uri)
    {
        final boolean isKitKatOrAbove = Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
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
                    final String[] selectionArgs = new String[] {
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
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
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}
