package com.example.androidlobby;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class DetailofMe extends AppCompatActivity {

    static String roleOfUser;
    static String name;
    static String company;
    static String phone;
    static String email;
    EditText nameOfUser;
    EditText companyOfUser;
    EditText phoneOfUser;
    EditText mailOfUser;
    LottieAnimationView submitDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailof_me);


        Intent intent = getIntent();
        roleOfUser = intent.getStringExtra("role");

        nameOfUser = findViewById(R.id.name);
        companyOfUser = findViewById(R.id.companyName);
        phoneOfUser = findViewById(R.id.phone);
        mailOfUser = findViewById(R.id.email);

        submitDetail = findViewById(R.id.lottieAnimationViewofSubmit);
        submitDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!roleOfUser.equals("") && !nameOfUser.getText().toString().equals("") &&
                        !companyOfUser.getText().toString().equals("") && !phoneOfUser.getText().toString().equals("") &&
                        !mailOfUser.getText().toString().equals("")) {
                    name = nameOfUser.getText().toString();
                    company = companyOfUser.getText().toString();
                    phone = phoneOfUser.getText().toString();
                    email = mailOfUser.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), Authentication.class);
                    intent.putExtra("phone", phoneOfUser.getText().toString());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }
            }
        });


    }


}
