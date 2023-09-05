package com.example.myprojectshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myprojectshop.adapters.PriceAdapter;
import com.example.myprojectshop.adapters.SectionAdapter;
import com.example.myprojectshop.data.PricesContract;
import com.example.myprojectshop.data.PricesDBHelper;
import com.example.myprojectshop.data.SectionsContract;
import com.example.myprojectshop.data.SectionsDBHelper;
import com.example.myprojectshop.lists.Price;
import com.example.myprojectshop.lists.Section;
import com.firebase.ui.auth.AuthUI;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MenuShopActivity extends AppCompatActivity {
    private PriceDatabase priceDatabase;
    private SectionsDatabase sectionsDatabase;
    private String stillShop;
    private int stillColorShop;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private TextView textViewTitleShop;
    private TextView textViewLozhop;
    private ImageView imageViewTitleShop;
    private String userId;
    private int countSections = 0;
    private TextView textViewSectionTitle;
    private ImageView imageViewSection;
    private Button buttonChengShop;
    private Button buttonAddSection;
    private RecyclerView recyclerViewSectionShop;
    private SectionAdapter sectionAdapter;
    private PriceAdapter priceAdapter;
    private SectionsDBHelper sectionsDBHelper;
    private PricesDBHelper pricesDBHelper;
    private SQLiteDatabase sectionDB;
    private SQLiteDatabase priceDB;
    private ArrayList<Section> sections;
    private ArrayList<Price> prices;
    private ArrayList<String> pces = new ArrayList();
    private Object LOCK;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_authentication, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemSighOut) {
            signOut();
            mAuth.signOut();
        }
        if (item.getItemId() == R.id.out) {
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }

    private int getColumn() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int witch = (int) (displayMetrics.widthPixels);
        int count = (witch / 2) - 4;
        return (witch / 2) - 4;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_shop);
        priceDatabase = PriceDatabase.getInstance(this);
        sectionsDatabase = SectionsDatabase.getInstance(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        textViewTitleShop = findViewById(R.id.textViewTitleShop);
        textViewLozhop = findViewById(R.id.textViewLozhop);
        imageViewTitleShop = findViewById(R.id.imageViewTitleShop);
        textViewSectionTitle = findViewById(R.id.textViewSectionTitle);
        imageViewSection = findViewById(R.id.imageViewSection);
        buttonChengShop = findViewById(R.id.buttonChengShope);
        buttonAddSection = findViewById(R.id.buttonAddSection);
        recyclerViewSectionShop = findViewById(R.id.recyclerViewSectionShop);
        sections = new ArrayList<>();
        prices = new ArrayList<>();
        Log.i("lllllllll", "ggg" + sections.size());
        sectionAdapter = new SectionAdapter();
        user = mAuth.getCurrentUser();
        sectionsDBHelper = new SectionsDBHelper(this);
        pricesDBHelper = new PricesDBHelper(this);
        sectionDB = sectionsDBHelper.getWritableDatabase();
        priceDB = pricesDBHelper.getWritableDatabase();

        if (user != null) {
            userId = user.getUid();
            DocumentReference docShop = db.collection(userId).document("shopNameBases");
            docShop.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Toast.makeText(MenuShopActivity.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Map<String, Object> user = snapshot.getData();
                        textViewTitleShop.setText(user.get("shopName").toString());
                        textViewLozhop.setText(user.get("personShop").toString());
                        stillShop = user.get("textStill").toString();
                        stillColorShop = -user.get("textColor").hashCode();
                        Picasso.get().load(user.get("urlImageTitle").toString()).into(imageViewTitleShop);

                        getDescription();
//                        getData();

                        Toast.makeText(MenuShopActivity.this, "nnnnn", Toast.LENGTH_SHORT).show();
                        sectionAdapter.setSections(sections, prices, getColumn(), stillShop, stillColorShop);
                        recyclerViewSectionShop.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerViewSectionShop.setAdapter(sectionAdapter);

                        switch (stillShop) {
                            case "Ariali":
                                textViewLozhop.setTextAppearance(R.style.ThemesSpinnerAriali);
                                break;
                            case "Bahnschrift":
                                buttonAddSection.setTextAppearance(R.style.ThemesSpinnerBahnschrift);
                                buttonChengShop.setTextAppearance(R.style.ThemesSpinnerBahnschrift);
                                textViewLozhop.setTextAppearance(R.style.ThemesSpinnerBahnschrift);
                                textViewTitleShop.setTextAppearance(R.style.ThemesSpinnerBahnschrift);
                                break;
                            case "Comic":
                                buttonAddSection.setTextAppearance(R.style.ThemesSpinnerComic);
                                buttonChengShop.setTextAppearance(R.style.ThemesSpinnerComic);
                                textViewLozhop.setTextAppearance(R.style.ThemesSpinnerComic);
                                textViewTitleShop.setTextAppearance(R.style.ThemesSpinnerComic);
                                break;
                            case "Deja":
                                buttonAddSection.setTextAppearance(R.style.ThemesSpinnerDeja);
                                buttonChengShop.setTextAppearance(R.style.ThemesSpinnerDeja);
                                textViewLozhop.setTextAppearance(R.style.ThemesSpinnerDeja);
                                textViewTitleShop.setTextAppearance(R.style.ThemesSpinnerDeja);
                                break;
                            case "Italic":
                                buttonAddSection.setTextAppearance(R.style.ThemesSpinnerItalic);
                                buttonChengShop.setTextAppearance(R.style.ThemesSpinnerItalic);
                                textViewLozhop.setTextAppearance(R.style.ThemesSpinnerItalic);
                                textViewTitleShop.setTextAppearance(R.style.ThemesSpinnerItalic);
                                break;
                            case "Segoeprb":
                                buttonAddSection.setTextAppearance(R.style.ThemesSpinnerSegoeprb);
                                buttonChengShop.setTextAppearance(R.style.ThemesSpinnerSegoeprb);
                                textViewLozhop.setTextAppearance(R.style.ThemesSpinnerSegoeprb);
                                textViewTitleShop.setTextAppearance(R.style.ThemesSpinnerSegoeprb);
                                break;
                            default:
                                buttonAddSection.setTextAppearance(R.style.ThemesSpinnerComic);
                                buttonChengShop.setTextAppearance(R.style.ThemesSpinnerComic);
                                textViewLozhop.setTextAppearance(R.style.ThemesSpinnerComic);
                                textViewTitleShop.setTextAppearance(R.style.ThemesSpinnerComic);
                                break;
                        }
                        buttonAddSection.setTextColor(stillColorShop);
                        buttonChengShop.setTextColor(stillColorShop);
                        textViewLozhop.setTextColor(stillColorShop);
                        textViewTitleShop.setTextColor(stillColorShop);
                        buttonAddSection.setTextColor(stillColorShop);
                        buttonChengShop.setTextColor(stillColorShop);

                        Log.i("ddbshd", user.get("urlImageTitle").toString());
                    } else {
                        Toast.makeText(MenuShopActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });



            sectionAdapter.setOnPriceAddClickListener(new SectionAdapter.OnPriceAddClickListener() {
                @Override
                public void OnPriceAddClick(String section) {
                    Intent addPriceIntent = new Intent(getApplicationContext(), AddPrice.class);
                    addPriceIntent.putExtra("idSectionAddPrice", section);
                    sections.clear();
                    prices.clear();
                    startActivity(addPriceIntent);
                    finish();
                }
            });
            sectionAdapter.setOnSectionDeleteClickListener(new SectionAdapter.OnSectionDeleteClickListener() {
                @Override
                public void OnSectionDelete(int position, String sectionId) {
                    DocumentReference docSection = db.collection(userId).document("section").collection("sectionsName").document(sectionId);
                    docSection.delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MenuShopActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                                    removeSection(position);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MenuShopActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
            sectionAdapter.setOnSectionChangeClickListener(new SectionAdapter.OnSectionChangeClickListener() {
                @Override
                public void OnSectionChange(int position, String sectionId) {
                    Intent intentChangeSection = new Intent(getApplicationContext(), AddSection.class);
                    intentChangeSection.putExtra("sectionId", sectionId);
                    startActivity(intentChangeSection);
                }
            });
            sectionAdapter.setOnDeletePriceListener(new SectionAdapter.OnDeletePriceListener() {
                @Override
                public void OnDeletePrice(int position, String sectionId, String idPrice) {
                    DocumentReference docPrice = db.collection(userId).document("section").collection("sectionsName").document(sectionId).collection("price").document(idPrice);
                    docPrice.delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    sections.clear();
                                    prices.clear();


                                    getData();
                                    sectionAdapter.setSections(sections, prices, getColumn(), stillShop, stillColorShop);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MenuShopActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
            sectionAdapter.setOnChangePriceListener(new SectionAdapter.OnChangePriceListener() {
                @Override
                public void OnChangePrice(int position, String sectionId, String idPrice) {
                    Intent chengPrice = new Intent(getApplicationContext(), AddPrice.class);
                    chengPrice.putExtra("sectionId", sectionId);
                    chengPrice.putExtra("idPrice", idPrice);
                    startActivity(chengPrice);
                }
            });

        }
        buttonAddSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddSection = new Intent(getApplicationContext(), AddSection.class);
                startActivity(intentAddSection);
            }
        });
        buttonChengShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangeShop = new Intent(getApplicationContext(), AddShop.class);
                intentChangeShop.putExtra("changShop", "changShop");
                startActivity(intentChangeShop);
            }
        });
    }

    private void signOut(){
        AuthUI.getInstance().signOut(this).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build());

// Create and launch sign-in intent
                Intent signInIntent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build();
                startActivity(signInIntent);
            }
        });
    }
    private void getDescription(){
        DocumentReference docSection = db.collection(userId).document("section");
        docSection.collection("sectionsName").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    sectionDB.delete(SectionsContract.SectionsEntry.TABLE_NAME, null, null);
                    priceDB.delete(PricesContract.PriceEntry.TABLE_NAME, null, null);
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            Log.i("gfgfgfgf", "sss");
                            String docId = document.getId();
                            Map<String, Object> section = document.getData();
                            String id = section.get("id").toString();
                            String title = section.get("title").toString();
                            String image = section.get("image").toString();
                            String dellImage = section.get("dellImage").toString();
                            Log.i("ccfccfcf", "sss"+id);
                            Section sec = new Section(id,title, image,dellImage);
                            sectionsDatabase.sectionDao().insertSection(sec);
//                            SQLiteDatabase sect = sectionsDBHelper.getWritableDatabase();
//                            ContentValues contentValues = new ContentValues();
//                            contentValues.put(SectionsContract.SectionsEntry.COLUMN_ID,id);
//                            contentValues.put(SectionsContract.SectionsEntry.COLUMN_NAME, title);
//                            contentValues.put(SectionsContract.SectionsEntry.COLUMN_IMAGE, image);
//                            contentValues.put(SectionsContract.SectionsEntry.COLUMN_IMAGE_DELETE, dellImage);
//                            sect.insert(SectionsContract.SectionsEntry.TABLE_NAME, null, contentValues);
                            docSection.collection("sectionsName").document(docId).collection("price").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (document.exists()) {
                                                Map<String, Object> price = document.getData();
                                                String id = price.get("id").toString();
                                                Log.i("gfgfgfgf", "lll");
                                                String idSection = price.get("idSection").toString();
                                                String name = price.get("name").toString();
                                                String image = price.get("image").toString();
                                                double cost = Double.parseDouble(price.get("cost").toString());
                                                String costStr = Double.toString(cost);
                                                Log.i("jhjhvvj", costStr);
                                                double costSail = Double.parseDouble(price.get("costSail").toString());
                                                String costSailStr = Double.toString(costSail);
                                                Log.i("jhjhvvj", costSailStr);
                                                String unit = price.get("unit").toString();
                                                Price pri = new Price(id, idSection, name, image, cost, costSail, unit);
//                                                priceDatabase.priceDao().insertPrice(pri);
//                                                SQLiteDatabase pri = pricesDBHelper.getWritableDatabase();
//                                                ContentValues contentValuesPr = new ContentValues();
//                                                contentValuesPr.put(PricesContract.PriceEntry.COLUMN_ID, id);
//                                                contentValuesPr.put(PricesContract.PriceEntry.COLUMN_SECTION_ID, idSection);
//                                                contentValuesPr.put(PricesContract.PriceEntry.COLUMN_NAME, name);
//                                                contentValuesPr.put(PricesContract.PriceEntry.COLUMN_IMAGE, image);
//                                                contentValuesPr.put(PricesContract.PriceEntry.COLUMN_NAME, name);
//                                                contentValuesPr.put(PricesContract.PriceEntry.COLUMN_COST, costStr);
//                                                contentValuesPr.put(PricesContract.PriceEntry.COLUMN_COST_SAIL, costSailStr);
//                                                contentValuesPr.put(PricesContract.PriceEntry.COLUMN_PRICE_UNIT, unit);
//                                                pri.insert(PricesContract.PriceEntry.TABLE_NAME, null, contentValuesPr);
//                                                Log.i("bvbvb","mmm");
//                                                pces.add(name);
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    }
//                    getData();
//                    sectionAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MenuShopActivity.this, "Error: "+e, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void remove(){
        sectionsDatabase.sectionDao().deleteAllSection();
        priceDatabase.priceDao().deleteAllPrice();
        getData();
        sectionAdapter.notifyDataSetChanged();
    }
    private void getData(){
        getDescription();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void removeSection(int position){
        int id = sections.get(position).getDbId();
        Log.i("hdgjfhmgvf", ""+position);
        Log.i("hdgjfhmgvf", ""+id);

        String where = SectionsContract.SectionsEntry._ID+" = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        sectionDB.delete(SectionsContract.SectionsEntry.TABLE_NAME,where,whereArgs);
        getData();
        sectionAdapter.notifyDataSetChanged();
    }

}