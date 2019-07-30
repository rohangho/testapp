package com.example.androidlobby.Worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.androidlobby.UplaodDocument;
import com.example.androidlobby.model.VisitorDetail;

public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        String name=getInputData().getString("nameToUpload");
        String phone=getInputData().getString("phoneToUpload");
        String mail=getInputData().getString("emailToUpload");
        String company=getInputData().getString("companyToUpload");
        String role=getInputData().getString("roleToUpload");

        VisitorDetail visitorDetail=new VisitorDetail(UplaodDocument.image,name,role,phone,mail,company);


        return null;
    }
}
