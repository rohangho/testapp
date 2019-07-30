package com.example.androidlobby;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.androidlobby.Worker.MyWorker;
import com.example.androidlobby.model.VisitorDetail;

public class UplaodDocument extends AppCompatActivity {

   public static byte[] image;
    ImageView img;
    TextView name;
    TextView mobile;
    TextView email;
    LottieAnimationView submmit;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplaod_document);

        image = getIntent().getByteArrayExtra("bitmap");
        img = (ImageView) findViewById(R.id.avatar);
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        img.setImageBitmap(bmp);

        name = (TextView) findViewById(R.id.textViewName);
        mobile = (TextView) findViewById(R.id.textViewPhone);
        email = (TextView) findViewById(R.id.textViewEmail);

        name.setText(DetailofMe.name);
        mobile.setText(DetailofMe.phone);
        email.setText(DetailofMe.email);

        final VisitorDetail visitorDetail = new VisitorDetail(image, DetailofMe.name, DetailofMe.roleOfUser,
                DetailofMe.phone, DetailofMe.email, DetailofMe.company);

        Data.Builder builder=new Data.Builder();
        builder.putString("nameToUpload",DetailofMe.name);
        builder.putString("roleToUpload",DetailofMe.roleOfUser);
        builder.putString("phoneToUpload",DetailofMe.phone);
        builder.putString("companyToUpload",DetailofMe.company);
        builder.putString("emailToUpload",DetailofMe.email);
        final Data data=builder.build();

        submmit = (LottieAnimationView) findViewById(R.id.lottieAnimationViewofSubmit);
        final OneTimeWorkRequest request=new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data).build();
        submmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WorkManager.getInstance(getApplicationContext()).enqueue(request);

            }
        });

    }


}
