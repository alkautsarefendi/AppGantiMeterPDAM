package com.project.fortuna.gantimeterpdam.spkp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.project.fortuna.gantimeterpdam.R;

public class ImportMeterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnLocal, btnNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_meter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Import SPKP");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnLocal = (Button)findViewById(R.id.btnLocal);
        btnNetwork = (Button)findViewById(R.id.btnNetwork);

        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImportMeterActivity.this, ImportSPKPLocalActivity.class));
            }
        });

        btnNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImportMeterActivity.this, ImportSPKPNetworkActivity.class));
            }
        });
    }
}
