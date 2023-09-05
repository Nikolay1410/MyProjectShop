package com.example.myprojectshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddSection extends AppCompatActivity {
    private EditText editTextNameSection;
    private Button buttonAddImageSection;
    private Button buttonSectionAdd;
    private ImageView downloadImageSection;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference reference;
    private String userId;
    private String dellImage;
    private String urlImageTitle;
    private Boolean imageTrue = false;
    private static final int RC_GET_IMAGE = 101;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_section);
        editTextNameSection = findViewById(R.id.editTextNameSection);
        buttonAddImageSection = findViewById(R.id.buttonAddImageSection);
        buttonSectionAdd = findViewById(R.id.buttonSectionAdd);
        downloadImageSection = findViewById(R.id.imageViewDownloadImageSection);
        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        reference = storage.getReference();
        user = mAuth.getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        }
        if (intent.hasExtra("sectionId")){
            buttonSectionAdd.setText("Изменить");
        }
        if (editTextNameSection!=null){
            buttonAddImageSection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent content = new Intent(Intent.ACTION_GET_CONTENT);
                    content.setType("image/jpeg");
                    content.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(content, RC_GET_IMAGE);
                }
            });
        }
        buttonSectionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent.hasExtra("sectionId")&&imageTrue && editTextNameSection != null) {
                    Toast.makeText(AddSection.this, "bbbb", Toast.LENGTH_SHORT).show();
                    String id = intent.getStringExtra("sectionId");
                    Log.i("dfdfdfd", id);
                    Log.i("dfccdfdfd", dellImage);
                    Log.i("dfccdfdfd", urlImageTitle);
                    Log.i("dfccdfdfd", editTextNameSection.getText().toString());
                    DocumentReference sectionId = db.collection(userId).document("section").collection("sectionsName").document(id);
                    sectionId
                            .update("dellImage", dellImage, "image", urlImageTitle, "title", editTextNameSection.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intentMenu = new Intent(getApplicationContext(), MenuShopActivity.class);
                                    startActivity(intentMenu);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddSection.this, "Error: "+e, Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    if (imageTrue && editTextNameSection != null) {
                        Map<String, Object> section = new HashMap<>();
                        section.put("id", "1");
                        section.put("title", editTextNameSection.getText().toString());
                        section.put("image", urlImageTitle);
                        section.put("dellImage", dellImage);
                        Toast.makeText(AddSection.this, userId, Toast.LENGTH_SHORT).show();
                        db.collection(userId).document("section").collection("sectionsName")
                                .add(section)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        String sectionId = documentReference.getId();
                                        documentReference.update("id", sectionId);
                                        Intent intentMenu = new Intent(getApplicationContext(), MenuShopActivity.class);
                                        startActivity(intentMenu);
                                    }
                                });
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GET_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                dellImage = "images/title/" + uri.getLastPathSegment();
                if (uri != null) {
                    StorageReference referenceToImages = reference.child("images/"+userId+"/section/" + uri.getLastPathSegment());
                    referenceToImages.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(AddSection.this, "Error1", Toast.LENGTH_SHORT).show();
                            }

                            // Continue with the task to get the download URL
                            return referenceToImages.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                SharedPreferences dell = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String dellIm  = dell.getString("dellImage", null);
                                if (dellIm!=null){
                                    StorageReference storageRef = storage.getReference();
                                    StorageReference desertRef = storageRef.child(dellIm);
                                    desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            Toast.makeText(AddSection.this, "Error: "+exception.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                Uri downloadUri = task.getResult();
                                urlImageTitle = downloadUri.toString();
                                SharedPreferences prefImage = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                prefImage.edit().putString("image", urlImageTitle).apply();
                                downloadImageSection.setVisibility(View.VISIBLE);
                                imageTrue = true;
                            } else {
                                Toast.makeText(AddSection.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }
    }
}