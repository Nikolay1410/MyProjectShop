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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddShop extends AppCompatActivity {
    private TextView textViewCopi;
    //    private RelativeLayout layoutAddShop;
    private static final int RC_GET_IMAGE = 100;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference reference;
    private EditText shopName;
    private EditText personShop;
    private Spinner spinnerTextStill;
    private int stillShopColor;
    private String stillShopText = "comic";
    private Button buttonDownloadImageShop;
    private Button buttonAddShop;
    private Button buttonAddMarker;
    private Button buttonAddColor;
    private ImageView imageViewAddMap;
    private ImageView imageViewDownloadImage;
    private Boolean addImage = false;
    private Boolean addMap = false;
    private Double latitudeInfo;
    private Double longitudeInfo;
    private String urlImageTitle;
    private String dellImage;
    private String dellOldImage;
    private String userId;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        textViewCopi = findViewById(R.id.textViewCopi);
//        layoutAddShop = findViewById(R.id.layoutAddShop);
        Intent intentInfo = getIntent();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        reference = storage.getReference();
        shopName = findViewById(R.id.editTextTextShopName);
        personShop = findViewById(R.id.editTextPersonNameShop);
        buttonAddMarker = findViewById(R.id.buttonAddMarker);
        buttonAddShop = findViewById(R.id.buttonAddPrice);
        buttonAddColor = findViewById(R.id.buttonAddColor);
        buttonDownloadImageShop = findViewById(R.id.buttonDownloadImagePrice);
        imageViewAddMap = findViewById(R.id.imageViewAddMap);
        imageViewDownloadImage = findViewById(R.id.imageViewDownloadImage);
        spinnerTextStill = findViewById(R.id.spinnerTextStill);
        spinnerTextStill.setSelection(0,false);
        stillShopText = spinnerTextStill.getSelectedItem().toString();
        spinnerTextStill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.text_of_still);
                Toast.makeText(AddShop.this, choose[position], Toast.LENGTH_SHORT).show();
                stillShopText = choose[position];
                switch (choose[position]) {
                    case  ("Ariali"):
                        textViewCopi.setTextAppearance(R.style.ThemesSpinnerAriali);
                        break;
                    case ("Bahnschrift"):
                        textViewCopi.setTextAppearance(R.style.ThemesSpinnerBahnschrift);
                        break;
                    case ("Comic"):
                        textViewCopi.setTextAppearance(R.style.ThemesSpinnerComic);
                        break;
                    case ("Deja"):
                        textViewCopi.setTextAppearance(R.style.ThemesSpinnerDeja);
                        break;
                    case ("Italic"):
                        textViewCopi.setTextAppearance(R.style.ThemesSpinnerItalic);
                        break;
                    case ("Segoeprb"):
                        textViewCopi.setTextAppearance(R.style.ThemesSpinnerSegoeprb);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Log.i("ghfghh", ""+stillShopColor);
        user = mAuth.getCurrentUser();
        if (user != null) {
            userId = user.getUid();
            if (intentInfo.hasExtra("dellOldImage")){
                dellOldImage = intentInfo.getStringExtra("dellOldImage");
            }
            if (intentInfo.hasExtra("changShop")){
                DocumentReference docShop = db.collection(userId).document("shopNameBases");
                docShop.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(AddShop.this, "Error: "+e.toString(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (snapshot != null && snapshot.exists()) {
                            Map<String, Object> user = snapshot.getData();
                            dellOldImage = user.get("dellImage").toString();
                            shopName.setText(user.get("shopName").toString());
                            personShop.setText(user.get("personShop").toString());
                            buttonAddMarker.setText("Изменить");
                            buttonDownloadImageShop.setText("Изменить");
                        } else {
                            Toast.makeText(AddShop.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            if (intentInfo.hasExtra("shopName") && intentInfo.hasExtra("personNameShop") && intentInfo.hasExtra("latitude") && intentInfo.hasExtra("longitude")) {
                String shopNameInfo = intentInfo.getStringExtra("shopName");
                String personNameShopInfo = intentInfo.getStringExtra("personNameShop");
                latitudeInfo = intentInfo.getDoubleExtra("latitude", 0);
                longitudeInfo = intentInfo.getDoubleExtra("longitude", 0);
                shopName.setText(shopNameInfo);
                personShop.setText(personNameShopInfo);
                imageViewAddMap.setVisibility(View.VISIBLE);
                addMap = true;
            }
            buttonAddColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openColorPicker();
                }
            });

            buttonAddMarker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String myShopName = shopName.getText().toString();
                    String myPersonShop = personShop.getText().toString();
                    if (shopName.length() > 0 && personShop.length() > 0) {
                        if (shopName.length() < 20 && personShop.length() < 50) {
                            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                            if (intentInfo.hasExtra("changShop")){
                                intent.putExtra("changShop", "changShop");
                                intent.putExtra("dellOldImage", dellOldImage);
                            }
                            intent.putExtra("myShopName", myShopName);
                            intent.putExtra("myPersonShop", myPersonShop);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AddShop.this, "Имя или лозунг слишком длинные.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddShop.this, "Поля: имя и лозунг должны быть заполены.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            buttonDownloadImageShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shopName.length() > 0 && personShop.length() > 0 && addMap) {
                        Intent content = new Intent(Intent.ACTION_GET_CONTENT);
                        content.setType("image/jpeg");
                        content.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                        startActivityForResult(content, RC_GET_IMAGE);
                    } else {
                        Toast.makeText(AddShop.this, "Сначала заполнете остальные поля", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            buttonAddShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    assert user != null;
                    String name = shopName.getText().toString();
                    String personName = personShop.getText().toString();
                    if (name != null && personName != null && addImage && addMap) {
                        Map<String, Object> shopNameBase = new HashMap<>();
                        shopNameBase.put("shopName", name);
                        shopNameBase.put("personShop", personName);
                        shopNameBase.put("idShop", user.getUid());
                        shopNameBase.put("textStill", stillShopText);
                        shopNameBase.put("textColor", stillShopColor);
                        shopNameBase.put("latitudeInfo", latitudeInfo);
                        shopNameBase.put("longitudeInfo", longitudeInfo);
                        shopNameBase.put("urlImageTitle", urlImageTitle);
                        shopNameBase.put("dellImage", dellImage);

                        if (intentInfo.hasExtra("changShop2") && !dellOldImage.equals(dellImage)) {
                            DocumentReference docSection = db.collection(userId).document("shopNameBases");
                            docSection
                                    .update("dellImage", dellImage, "idShop", user.getUid(), "shopName", name, "personShop", personName, "urlImageTitle", urlImageTitle, "latitudeInfo", latitudeInfo, "longitudeInfo", longitudeInfo)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            StorageReference referenceDellOldImages = reference.child("images/title/" + dellOldImage);
                                            referenceDellOldImages.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {
                                                    Toast.makeText(AddShop.this, "Error", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            Intent menu = new Intent(getApplicationContext(), MenuShopActivity.class);
                                            startActivity(menu);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AddShop.this, "Error: "+e, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        if (!intentInfo.hasExtra("changShop2")) {
                            db.collection(user.getUid()).document("shopNameBases")
                                    .set(shopNameBase)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent menu = new Intent(getApplicationContext(), MenuShopActivity.class);
                                            startActivity(menu);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AddShop.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                }
            });
        }
    }

    //Загружаем картинку в хранилише
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GET_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                dellImage = "images/title/" + uri.getLastPathSegment();
                if (uri != null) {
                    StorageReference referenceToImages = reference.child("images/"+userId+"/title/" + uri.getLastPathSegment());
                    referenceToImages.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(AddShop.this, "Error1", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(AddShop.this, "Error: "+exception.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                Uri downloadUri = task.getResult();
                                urlImageTitle = downloadUri.toString();
                                SharedPreferences prefImage = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                prefImage.edit().putString("image", urlImageTitle).apply();
                                imageViewDownloadImage.setVisibility(View.VISIBLE);
                                addImage = true;
                            } else {
                                addImage = false;
                            }
                        }
                    });
                }
            }
        }
    }
    //Вибираем цвет
    public  void openColorPicker(){
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, stillShopColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(AddShop.this, "000", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                stillShopColor = color;
                textViewCopi.setTextColor(color);
                Log.i("fgffgfgf", ""+color);
                Toast.makeText(AddShop.this, ""+color, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}