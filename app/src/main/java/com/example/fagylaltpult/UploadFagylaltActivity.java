package com.example.fagylaltpult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.api.LogDescriptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UploadFagylaltActivity extends AppCompatActivity {

    private static final String LOG_TAG = UploadFagylaltActivity.class.getName();
    private FirebaseUser user;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    EditText nev;
    EditText hozzavalok;
    RadioButton tej;
    RadioButton viz;
    CheckBox cukormentes;
    CheckBox laktozmentes;
    CheckBox glutenmentes;
    CheckBox gyumolcsfagyi;
    EditText imgRes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_fagylalt);

        user = FirebaseAuth.getInstance().getCurrentUser();

        Bundle bundle = getIntent().getExtras();
        int secret_key = bundle.getInt("SECRET_KEY", 0);
        if(secret_key != 99){
            finish();
        }

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");
    }

    public void upload(View view) {
        nev = findViewById(R.id.editTextName);
        hozzavalok = findViewById(R.id.editTextHozzavalok);
        tej = findViewById(R.id.TejradioButton);
        viz = findViewById(R.id.VizradioButton);
        cukormentes = findViewById(R.id.cukormMentesCheckBox);
        laktozmentes = findViewById(R.id.LaktozMentescheckBox);
        glutenmentes = findViewById(R.id.glutenMentesCheckBox);
        gyumolcsfagyi = findViewById(R.id.gyumolcsCheckBox);
        imgRes = findViewById(R.id.editTextImgRes);

        String alap = tej.isSelected()?"tej":"viz";

        if(nev.getText().toString().isEmpty()){
            nev.setError("Kérem adja meg a fagylalt nevét!");
        }else if(hozzavalok.getText().toString().isEmpty()){
            hozzavalok.setError("Kérem adja meg a hozzávalókat!");
        }
        initData(
                nev.getText().toString(),
                cukormentes.isChecked(),
                laktozmentes.isChecked(),
                glutenmentes.isChecked(),
                gyumolcsfagyi.isChecked(),
                alap,
                hozzavalok.getText().toString(),
                Integer.parseInt(imgRes.getText().toString()));
        finish();
    }

    public void cancel(View view) {finish();}

    private void initData(
            String nev,
            boolean cukormentes,
            boolean laktozmentes,
            boolean glutenmentes,
            boolean gyumolcsfagylalt,
            String alap,
            String hozzavalok,
            int imgRes){

        mItems.add(new Fagylalt(
                nev,
                cukormentes,
                laktozmentes,
                glutenmentes,
                gyumolcsfagylalt,
                alap,
                hozzavalok,
                imgRes));
    }
}