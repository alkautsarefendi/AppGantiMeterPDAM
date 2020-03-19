package com.project.fortuna.gantimeterpdam.review;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.project.fortuna.gantimeterpdam.R;

public class LihatDatapelangganActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnBelum, btnSudah;
    private SharedPreferences pref;
    private Boolean viewData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_pelanggan);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu Review");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pref = getApplicationContext().getSharedPreferences("GantiMeterApp", 0);

        initComponent();
    }

    private void initComponent() {

        btnBelum = (Button) findViewById(R.id.btnBelum);
        btnBelum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewBelum = new Intent(LihatDatapelangganActivity.this, LihatDataBelumActivity.class);
                reviewBelum.putExtra("isBelum", viewData);
                startActivity(reviewBelum);
            }
        });

        btnSudah = (Button) findViewById(R.id.btnSudah);
        btnSudah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewSudah = new Intent(LihatDatapelangganActivity.this, LihatDataSudahActivity.class);
                reviewSudah.putExtra("isSukses", viewData);
                startActivity(reviewSudah);
            }
        });

    }
}
