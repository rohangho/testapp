package com.example.androidlobby;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.androidlobby.Worker.MyWorker;

public class UplaodDocument extends AppCompatActivity {

    public static byte[] image;
    ImageView img;
    TextView name;
    TextView mobile;
    TextView email;
    LottieAnimationView submmit;
    String url = null;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplaod_document);

        image = getIntent().getByteArrayExtra("bitmap");

        img = findViewById(R.id.avatar);
        Bitmap bmp = null;
        if (image != null) {
            bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            img.setImageBitmap(bmp);
        } else {
            url = getIntent().getStringExtra("Url");
            Glide.with(this).load(url).into(img);

        }
        name = findViewById(R.id.textViewName);
        mobile = findViewById(R.id.textViewPhone);
        email = findViewById(R.id.textViewEmail);

        name.setText(DetailofMe.name);
        mobile.setText(DetailofMe.phone);
        email.setText(DetailofMe.email);


        Data.Builder builder = new Data.Builder();
        builder.putString("nameToUpload", DetailofMe.name);
        builder.putString("roleToUpload", DetailofMe.roleOfUser);
        builder.putString("phoneToUpload", DetailofMe.phone);
        builder.putString("companyToUpload", DetailofMe.company);
        builder.putString("emailToUpload", DetailofMe.email);
        final Data data = builder.build();

        submmit = findViewById(R.id.lottieAnimationViewofSubmit);
        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).build();
        submmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (url.equals(null))
                    WorkManager.getInstance(getApplicationContext()).enqueue(request);

                Toast.makeText(getApplicationContext(), "Printing your Ticket", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

    }


}
