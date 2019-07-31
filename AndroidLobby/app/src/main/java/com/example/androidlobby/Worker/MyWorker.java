package com.example.androidlobby.Worker;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.androidlobby.UplaodDocument;
import com.example.androidlobby.model.VisitorDetail;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        String name = getInputData().getString("nameToUpload");
        String phone = getInputData().getString("phoneToUpload");
        String mail = getInputData().getString("emailToUpload");
        String company = getInputData().getString("companyToUpload");
        String role = getInputData().getString("roleToUpload");


        VisitorDetail visitorDetail = new VisitorDetail(name, role, phone, mail, company);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Visitors").push();
        myRef.setValue(visitorDetail);


        FirebaseStorage storage;
        StorageReference storageReference;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        StorageReference ref = storageReference.child(phone);
        ref.putBytes(UplaodDocument.image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Unsuccessful Addition", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseAuth.getInstance().signOut();

        return null;
    }
}
