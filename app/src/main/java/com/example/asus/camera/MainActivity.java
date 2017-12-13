package com.example.asus.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final int CAMERAPERMISSIONID = 33;
    private final int GALLERYPERMISSIONID = 44;



    RelativeLayout relativeLayout;

    Uri mOutputFileUri;


    Button buttonGallery;
    Button buttonCamera;
    Button buttonNextActivity;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        buttonGallery = (Button) findViewById(R.id.buttonGallery);

        imageView = (ImageView) findViewById(R.id.imageView);

        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERYPERMISSIONID);
                    }else { ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERYPERMISSIONID);}

                }

                else {Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,1);}

            }
        });

        buttonCamera = (Button) findViewById(R.id.buttonCamera);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){


                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA},CAMERAPERMISSIONID);
                }else { ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},CAMERAPERMISSIONID);}

            }

            else {
                    saveFullImage();

                }
            }
        });

        buttonNextActivity = (Button) findViewById(R.id.buttonNextActivity);
        buttonNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("photoCamera",mOutputFileUri);
                startActivity(intent);
            }
        });

    }

   @Override
   public  void onRequestPermissionsResult(int requestCode, String[]permissions,int[] grautResalt){
       if (requestCode==CAMERAPERMISSIONID){
           Log.d("Log2","8887877");
           if (grautResalt.length > 0
                   && grautResalt[0] == PackageManager.PERMISSION_GRANTED){
               Log.d("Log2","OGOGo");
                    saveFullImage();
//               Intent intent22 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//               startActivityForResult(intent22, 2);

           }else Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
       } else super.onRequestPermissionsResult(requestCode, permissions, grautResalt);

       if (requestCode==GALLERYPERMISSIONID){
           if (grautResalt.length > 0
                   && grautResalt[0] == PackageManager.PERMISSION_GRANTED){


               Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(intent,1);
           }else Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();

       }


   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==1){
                    Uri uri = data.getData();
                    String[] projection={MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri,projection,null,null,null);

                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                    Drawable drawable = new BitmapDrawable(selectedImage);

                    imageView.setBackground(drawable);

        }
    }

    private void saveFullImage() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(),
                "test.jpg");
        mOutputFileUri = Uri.fromFile(file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputFileUri);
        startActivityForResult(intent, 2);


    }

}
