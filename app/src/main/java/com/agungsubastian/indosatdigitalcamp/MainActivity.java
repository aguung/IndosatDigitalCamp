package com.agungsubastian.indosatdigitalcamp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CandiAdapter.CandiAdapterListener {

    private RecyclerView rvCandi;
    private ArrayList<Candi> list = new ArrayList<>();
    private CandiAdapter candiAdapter;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Data Candi");

        rvCandi = findViewById(R.id.rv_candi);
        rvCandi.setHasFixedSize(true);

        list.addAll(CandiData.getListData());
        showRecyclerList(1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                candiAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                candiAdapter.getFilter().filter(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }
    public void setMode(int selectedMode) {
        switch (selectedMode) {
            case R.id.search:
                break;
            case R.id.list:
                showRecyclerList(1);
                break;
            case R.id.grid:
                showRecyclerList(2);
                break;
            case R.id.profil:
                startActivity(new Intent(this,ProfilActivity.class));
                break;
        }
    }

    private void showRecyclerList(int tipe){
        list.clear();
        list.addAll(CandiData.getListData());
        if(tipe == 1){
            rvCandi.setLayoutManager(new LinearLayoutManager(this));
        }else{
            rvCandi.setLayoutManager(new GridLayoutManager(this,2));
        }
        candiAdapter = new CandiAdapter(list,tipe,this);
        rvCandi.setAdapter(candiAdapter);
        candiAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCandiSelected(Candi data) {
        Intent intent = new Intent(this,DetailCandiActivity.class);
        intent.putExtra("image",data.getPhoto());
        intent.putExtra("nama",data.getNama());
        intent.putExtra("asal",data.getAsal());
        intent.putExtra("deskripsi",data.getDeskripsi());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Please click BACK again to exit.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }
}
