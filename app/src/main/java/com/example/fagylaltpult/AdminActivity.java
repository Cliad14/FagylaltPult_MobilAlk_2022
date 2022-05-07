package com.example.fagylaltpult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity {
    private static final String LOG_TAG = AdminActivity.class.getName();
    private static final int SECRET_KEY = 99;
    private FirebaseUser user;


    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    private CollectionReference fagylaltok;

    private ArrayList<Fagylalt> fagyik;
    private ArrayList<Pult> pultAktual;

    Spinner spinner;
    Button betesz;
    Button kivesz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null && Objects.equals(user.getEmail(), "pultos@damniczki.admin")){
            Log.d(LOG_TAG, "Authenticated user.");
        }else{
            Log.d(LOG_TAG, "Unauthenticated user.");
            finish();
        }

        spinner = findViewById(R.id.fagyikSpinner);
        betesz = findViewById(R.id.buttonBetesz);
        kivesz = findViewById(R.id.buttonKivesz);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");
        fagylaltok = mFirestore.collection("Pult");
        fagyik = new ArrayList<>();
        pultAktual = new ArrayList<>();

        setTheOptions();
    }

    private void setTheOptions(){
        mItems.orderBy("nev").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                Fagylalt item = document.toObject(Fagylalt.class);
                Log.d(LOG_TAG, item.getNev());
                fagyik.add(item);
            }
            spinnerTolto();
            fillPult();
        });
    }

    private void fillPult(){
        fagylaltok.orderBy("fagyiNeve").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                Pult item = document.toObject(Pult.class);
                item.setId(document.getId());
                Log.d(LOG_TAG, item.getFagyiNeve()+"+++++++++++++"+item._getId());
                pultAktual.add(item);
            }
        });
    }

    private void spinnerTolto(){
        List<String> array = new ArrayList<>();
        for(Fagylalt fagyi : fagyik){
            array.add(fagyi.getNev());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void uploadFagylalt(View view) {
        Intent intent = new Intent(this, UploadFagylaltActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    public void kivesz(View view) {
        Log.d(LOG_TAG, pultAktual.size()+"<-----------");
        for(Pult item : pultAktual) {
            if(item.getFagyiNeve().equals(spinner.getSelectedItem().toString())) {
                DocumentReference ref = fagylaltok.document(item._getId());
                ref.delete().addOnSuccessListener(succes -> {
                    Log.d(LOG_TAG, "OTT VAN BAZDMEG " + item._getId());
                }).addOnFailureListener(failure -> {
                    Toast.makeText(this, "NEM LEHET TOROLNI", Toast.LENGTH_SHORT).show();
                });
            }
        }
        Toast.makeText(AdminActivity.this, "Fagyi kivéve a pultból.",Toast.LENGTH_SHORT).show();
    }

    public void betesz(View view) {
        Pult pult = new Pult(spinner.getSelectedItem().toString());
        fagylaltok.add(pult);
        Toast.makeText(AdminActivity.this, "Fagyi hozzáadva a pulthoz.",Toast.LENGTH_SHORT).show();
    }

    public void pult(View view) {
        Intent intent = new Intent(this, PultActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        List<String> array = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        spinnerTolto();
    }
}