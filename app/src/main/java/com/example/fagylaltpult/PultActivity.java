package com.example.fagylaltpult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class PultActivity extends AppCompatActivity implements PultAdapter.OnNoteListener {
    private static final String LOG_TAG = PultActivity.class.getName();
    private FirebaseUser user;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    public NotificationHandler mNotificationHandler;

    private RecyclerView mRecyclerView;
    private ArrayList<Pult> mItemList;
    private PultAdapter mAdapter;

    private int gridNumber = 1;
    private boolean viewRow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pult);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            Log.d(LOG_TAG, "Authenticated user.");
        }else{
            Log.d(LOG_TAG, "Unauthenticated user.");
            finish();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mItemList = new ArrayList<>();

        mAdapter = new PultAdapter(this, mItemList, this);
        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Pult");


        queryData();
        mNotificationHandler = new NotificationHandler(this);
        mNotificationHandler.send("Várjuk szeretettel lelőhelyeinkken!");
    }

    private void queryData() {
        mItemList.clear();

        mItems.orderBy("fagyiNeve").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                Pult fagyi = document.toObject(Pult.class);
                mItemList.add(fagyi);
            }

            if(mItemList.size() == 0){
                Context context = getApplicationContext();
                CharSequence text = "Sajnos jelenleg NINCS fagylalt a pultban.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.fagylalt_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {return false;}

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG, s);
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutButton:
                Log.d(LOG_TAG, "Logout is clicked!");
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.setting_button:
                Log.d(LOG_TAG, "Setting is clicked!");
                return true;
            case R.id.view_selector:
                Log.d(LOG_TAG, "ViewChanger is clicked!");
                if(viewRow){
                    changeSpanCount(item, R.drawable.ic_view_grid, 1);
                }else{
                    changeSpanCount(item, R.drawable.ic_view_row, 2);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeSpanCount(MenuItem item, int drawableId, int spanCount) {
        viewRow = !viewRow;
        item.setIcon(drawableId);
        GridLayoutManager layoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        layoutManager.setSpanCount(spanCount);
    }

    @Override
    public void onNoteClick(int position) {
        //Log.d(LOG_TAG, "onNoteClicked!!!!!!! ");
        //mItemList.get(position);
        //Toast.makeText(this, "Megnyomtad", Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(this, DatasOfFagylaltActivity.class);
        //startActivity(intent);
    }
}