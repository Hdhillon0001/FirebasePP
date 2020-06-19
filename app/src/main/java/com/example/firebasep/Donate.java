package com.example.firebasep;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Donate extends AppCompatActivity {
    public ImageView InputProductImage;
    private Button Postbutton;
    private EditText InputProductDescription;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String Description, saveCurrentDate, saveCurrentTime;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        loadingBar = new ProgressDialog(this);
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
        InputProductImage = (ImageView) findViewById(R.id.itemimage);
        Postbutton = (Button) findViewById(R.id.postbtn);
        InputProductDescription = (EditText) findViewById(R.id.infotext);


        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenGallery();


            }
        });


        Postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ValidiateProductData();
            }
        });
    }

    private void ValidiateProductData() {
        Description = InputProductDescription.getText().toString();

        if (ImageUri == null) {

            Toast.makeText(this, "Product image is mandatory..", Toast.LENGTH_SHORT).show();


        } else if (TextUtils.isEmpty(Description)) {
            Toast.makeText(this, "Product Description is mandatory..", Toast.LENGTH_SHORT).show();

        } else {
            StoreProductInformation();

        }

    }

    private void StoreProductInformation() {



        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Loading...adding new post");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM,dd,YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss ");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(Donate.this, "Error:", Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                Toast.makeText(Donate.this, "Upload successfull:", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {

                            throw task.getException();

                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {

                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(Donate.this, "Product image Url saved to Databas:", Toast.LENGTH_SHORT).show();

                            SaveProductInfoTODatabse();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoTODatabse() {

        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",productRandomKey);
        productMap.put("time",productRandomKey);
        productMap.put("description",Description);
        productMap.put("image",downloadImageUrl);
        productMap.put("image",downloadImageUrl);

        ProductRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Intent intent = new Intent(Donate.this, Home.class);
                    startActivity(intent);
                    loadingBar.dismiss();

                    Toast.makeText(Donate.this,"Product added successfully",Toast.LENGTH_SHORT).show();

                }

                else{
                    loadingBar.dismiss();

                    String message = task.getException().toString();
                    Toast.makeText(Donate.this,"Error..:",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private void OpenGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);


        }
    }
}
