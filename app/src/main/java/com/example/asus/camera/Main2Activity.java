package com.example.asus.camera;



import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



public class Main2Activity extends AppCompatActivity {


    ImageView imageView2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2.animate().rotation(90);
        Button buttonBack = (Button) findViewById(R.id.btnBack);

        Intent intent = getIntent();



        Uri uri = (Uri) intent.getExtras().get("photoCamera");
        imageView2.setImageURI(uri);




        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}
