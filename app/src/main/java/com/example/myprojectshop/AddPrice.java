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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddPrice extends AppCompatActivity {
    private EditText editTextTextShopName;
    private EditText editTextCostAdd;
    private EditText editTextCostSailAdd;
    private EditText editTextUnitAdd;
    private ImageView imageViewAddPrice;
    private Button buttonDownloadImagePrice;
    private Button buttonAddPrice;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference reference;
    private String userId;
    private String dellImage;
    private String idSectionAddPrice;
    private String idPrice;

    private String urlImageTitle;
    private String urlImageOldDell;
    private Boolean addImage = false;
    private static final int RC_GET_IMAGE = 101;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_price);
        editTextTextShopName = findViewById(R.id.editTextTextShopName);
        editTextCostAdd = findViewById(R.id.editTextCostAdd);
        editTextCostSailAdd = findViewById(R.id.editTextCostSailAdd);
        editTextUnitAdd = findViewById(R.id.editTextUnitAdd);
        imageViewAddPrice = findViewById(R.id.imageViewAddPrice);
        buttonDownloadImagePrice = findViewById(R.id.buttonDownloadImagePrice);
        buttonAddPrice = findViewById(R.id.buttonAddPrice);
        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        reference = storage.getReference();
        user = mAuth.getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        }
        if (intent.hasExtra("sectionId")&&intent.hasExtra("idPrice")){
            idPrice = intent.getStringExtra("idPrice").toString();
            idSectionAddPrice = intent.getStringExtra("sectionId").toString();
            DocumentReference docPrice = db.collection(userId).document("section").collection("sectionsName").document(idSectionAddPrice).collection("price").document(idPrice);
            docPrice.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Map<String, Object> price = document.getData();
                            urlImageOldDell = price.get("dellImage").toString();
                            editTextTextShopName.setText(price.get("name").toString());
                            double cost = Double.parseDouble(price.get("cost").toString());
                            editTextCostAdd.setText(Double.toString(cost));
                            double costSail = Double.parseDouble(price.get("costSail").toString());
                            editTextCostSailAdd.setText(Double.toString(costSail));
                            editTextUnitAdd.setText(price.get("unit").toString());
                        } else {
                            Toast.makeText(AddPrice.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddPrice.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            buttonAddPrice.setText("Изменить");
        }
        if (intent.hasExtra("idSectionAddPrice")){
            idSectionAddPrice = intent.getStringExtra("idSectionAddPrice").toString();
        }
        buttonDownloadImagePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent content = new Intent(Intent.ACTION_GET_CONTENT);
                content.setType("image/jpeg");
                content.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(content, RC_GET_IMAGE);
            }
        });

        buttonAddPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user!=null) {
                    String priceName = editTextTextShopName.getText().toString();
                    String cost = editTextCostAdd.getText().toString();
                    String costSail = editTextCostSailAdd.getText().toString();
                    String unit = editTextUnitAdd.getText().toString();
                    Double costD = Double.parseDouble(cost);
                    Double costSailD;
                    if (costSail!= null && costSail.length()>0) {
                        Log.i("kfvhjfvfj", "mmmmm");
                        costSailD = Double.parseDouble(costSail);
                    }else {
                        Log.i("kfvhjfvfj", "vvvvv");
                        costSailD = 0.0;
                    }
                    if (addImage){
                        if (intent.hasExtra("sectionId") && intent.hasExtra("idPrice")) {
                            if (priceName.length() > 0 && unit.length() > 0) {
                                if (priceName.length() < 20) {
                                    if (cost.contains(".") && costSail.contains(".")) {
                                        DocumentReference docPriceChange = db.collection(userId).document("section").collection("sectionsName").document(idSectionAddPrice).collection("price").document(idPrice);
                                        docPriceChange
                                                .update("name", priceName, "image", urlImageTitle, "dellImage", dellImage, "cost", costD, "costSail", costSailD, "unit", unit)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Intent success = new Intent(getApplicationContext(), MenuShopActivity.class);
                                                        startActivity(success);
                                                        StorageReference desertRef = reference.child("images/" + userId + "/price/" + urlImageOldDell);
                                                        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception exception) {
                                                                Toast.makeText(AddPrice.this, "Error", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(AddPrice.this, "Error", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    } else {
                                        Toast.makeText(AddPrice.this, "Введите цены в формате: (00.00)", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(AddPrice.this, "Название слишком длиное.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AddPrice.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else {
                        Toast.makeText(AddPrice.this, "Добавте изображение", Toast.LENGTH_SHORT).show();
                    }
                    if (addImage && !intent.hasExtra("sectionId") && !intent.hasExtra("idPrice")){
                        if (priceName.length() > 0 && unit.length() > 0) {
                            if (priceName.length() < 20) {
                                if (cost.contains(".") && (costSail.contains(".") || costSail.length() == 0)) {
                                    Map<String, Object> price = new HashMap<>();
                                    price.put("id", 1);
                                    price.put("idSection", idSectionAddPrice);
                                    price.put("name", priceName);
                                    price.put("image", urlImageTitle);
                                    price.put("dellImage", dellImage);
                                    price.put("cost", costD);
                                    price.put("costSail", costSailD);
                                    price.put("unit", unit);
                                    DocumentReference docPrice = db.collection(userId).document("section").collection("sectionsName").document(idSectionAddPrice);
                                    docPrice.collection("price")
                                            .add(price)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    String priceId = documentReference.getId();
                                                    Log.i("hrgnbjhmgnb", priceId);
                                                    documentReference.update("id", priceId);
                                                    Intent intentAddPrice = new Intent(getApplicationContext(), MenuShopActivity.class);
                                                    startActivity(intentAddPrice);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(AddPrice.this, "Error: " + e, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(AddPrice.this, "Введите цены в формате: (00.00)", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AddPrice.this, "Название слишком длиное.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddPrice.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AddPrice.this, "Добавте изображение", Toast.LENGTH_SHORT).show();
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
                if (!dellImage.equals(urlImageOldDell)){
                    if (uri != null) {
                        StorageReference referenceToImages = reference.child("images/" + userId + "/price/" + uri.getLastPathSegment());
                        referenceToImages.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(AddPrice.this, "Error1", Toast.LENGTH_SHORT).show();
                                }
                                return referenceToImages.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    SharedPreferences dell = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    String dellIm = dell.getString("dellImage", null);
                                    if (dellIm != null) {
                                        StorageReference storageRef = storage.getReference();
                                        StorageReference desertRef = storageRef.child(dellIm);
                                        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                Toast.makeText(AddPrice.this, "Error: " + exception.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    Uri downloadUri = task.getResult();
                                    urlImageTitle = downloadUri.toString();
                                    SharedPreferences prefImage = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    prefImage.edit().putString("image", urlImageTitle).apply();
                                    imageViewAddPrice.setVisibility(View.VISIBLE);
                                    addImage = true;
                                } else {
                                    addImage = false;
                                }
                            }
                        });
                    }
                }else {
                    Toast.makeText(this, "Фаил с таким названием уже сушествует", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}