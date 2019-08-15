package com.agungsubastian.indosatdigitalcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import de.hdodenhof.circleimageview.CircleImageView;
import me.biubiubiu.justifytext.library.JustifyTextView;

public class DetailCandiActivity extends AppCompatActivity {

    CircleImageView image;
    TextView nama,asal;
    JustifyTextView deskripsi;
    String i,n,a,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_candi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        image = findViewById(R.id.photo);
        nama = findViewById(R.id.nama);
        asal = findViewById(R.id.asal);
        deskripsi = findViewById(R.id.deskripsi);

        i = getIntent().getStringExtra("image");
        n = getIntent().getStringExtra("nama");
        a = getIntent().getStringExtra("asal");
        d = getIntent().getStringExtra("deskripsi");



        getSupportActionBar().setTitle(n);

        Glide.with(this)
                .load(i)
                .apply(new RequestOptions().override(300, 300).error(R.drawable.icon).centerCrop())
                .into(image);
        nama.setText(n);
        asal.setText(a);
        deskripsi.setText(d);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
